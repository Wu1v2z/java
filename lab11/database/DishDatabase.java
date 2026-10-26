package lab11.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.h2.tools.Server;

/**
 * Створює локальну файлову базу H2 і запускає TCP-сервер для JDBC-клієнта.
 * Сервер приймає з'єднання лише з цього комп'ютера.
 */
public class DishDatabase implements AutoCloseable {
    private static final String DATABASE_NAME = "dishes";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private final Path databaseDirectory;
    private final int port;
    private Server tcpServer;

    public DishDatabase(Path databaseDirectory, int port) {
        if (databaseDirectory == null) {
            throw new IllegalArgumentException("Каталог бази даних не може бути null.");
        }
        if (port < 1 || port > 65535) {
            throw new IllegalArgumentException("Порт має бути в межах від 1 до 65535.");
        }
        this.databaseDirectory = databaseDirectory.toAbsolutePath().normalize();
        this.port = port;
    }

    /** Створює файл БД, якщо його ще немає, і запускає локальний TCP-сервер. */
    public void start() throws ClassNotFoundException, IOException, SQLException {
        if (tcpServer != null && tcpServer.isRunning(true)) {
            return;
        }

        Class.forName("org.h2.Driver");
        Files.createDirectories(databaseDirectory);

        // H2 безпечніше створювати локально, а не дозволяти віддалене створення БД.
        try (Connection ignored = DriverManager.getConnection(
                buildEmbeddedUrl(), USER, PASSWORD)) {
            // Сам факт з'єднання створює порожню БД, якщо її ще не існує.
        }

        tcpServer = Server.createTcpServer(
                "-tcpPort", String.valueOf(port),
                "-tcpDaemon",
                "-baseDir", databaseDirectory.toString())
                .start();
    }

    /** Відкриває JDBC-з'єднання клієнта саме через TCP-сервер. */
    public Connection openConnection() throws SQLException {
        if (tcpServer == null || !tcpServer.isRunning(true)) {
            throw new SQLException("Сервер бази даних не запущено.");
        }
        return DriverManager.getConnection(getClientUrl(), USER, PASSWORD);
    }

    public String getClientUrl() {
        return "jdbc:h2:tcp://localhost:" + port + "/./" + DATABASE_NAME;
    }

    public Path getDatabaseDirectory() {
        return databaseDirectory;
    }

    public String getServerStatus() {
        return tcpServer == null ? "сервер не запущено" : tcpServer.getStatus();
    }

    @Override
    public void close() {
        if (tcpServer != null) {
            tcpServer.stop();
            tcpServer = null;
        }
    }

    private String buildEmbeddedUrl() {
        String databasePath = databaseDirectory
                .resolve(DATABASE_NAME)
                .toString()
                .replace('\\', '/');
        return "jdbc:h2:file:" + databasePath + ";DB_CLOSE_ON_EXIT=FALSE";
    }
}
