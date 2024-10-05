package main.java.com.banking.system;

public class DatabaseConfig {
    public static final String USER = "root";
    public static final String PASSWORD = "pass";
    private static final String HOST = "localhost";
    private static final int PORT = 3306;
    private static final String DATABASE = "bankingdb";
    public static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;
}