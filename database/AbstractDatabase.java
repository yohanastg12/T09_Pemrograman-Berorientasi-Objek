package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class AbstractDatabase {
    private Connection connection;
    private String url;
 
    
    public AbstractDatabase(String url) {
        this.url = url;
    }

    public void connect() throws SQLException {
        connection = DriverManager.getConnection(url);
    }

    public void disconnect() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    protected Connection getConnection() {
        return connection;
    }

    protected abstract void createTable() throws SQLException;

    protected abstract void seedTables() throws SQLException;
}
