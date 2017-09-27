
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_CONNECTION = "jdbc:postgresql://localhost:5432/test1";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "Everestpro2000";

    public static void main(String[] argv) {
        try {
            createDbUserTable();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createDbUserTable() throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;

        String createUserTable = "CREATE TABLE USERS(\n" +
                "   ID       INT     NOT NULL PRIMARY KEY ,\n" +
                "   NAME     TEXT    NOT NULL,\n" +
                "   SURNAME  TEXT    NOT NULL\n" +
                ");";

        String insertUser1 = "INSERT INTO USERS (ID,NAME,SURNAME) VALUES (1, 'John', 'Dow');";
        String insertUser2 = "INSERT INTO USERS (ID,NAME,SURNAME) VALUES (2, 'Peter', 'Smith');";
        String getAllUsers = "SELECT * FROM USERS;";


        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();

            createTable(statement, createUserTable, insertUser1, insertUser2);
            retrieveData(statement, getAllUsers);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

    private static void retrieveData(Statement statement, String getAllUsers) throws SQLException {
        ResultSet resultSet = statement.executeQuery(getAllUsers);
        List<User> userList = new ArrayList<>();
        while(resultSet.next()){
            userList.add(new User(resultSet.getString("name"), resultSet.getString("surname")));

        }
        for(User user : userList){
            System.out.println(user);
        }
    }

    private static void createTable(Statement statement, String createUserTable, String insertUser1, String insertUser2) throws SQLException {
        // execute the SQL statement
        statement.execute(createUserTable);

        System.out.println("Table USERS was created!");

        statement.execute(insertUser1);
        statement.execute(insertUser2);

        System.out.println("Users were inserted");
    }

    private static Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            dbConnection = DriverManager.getConnection(
                    DB_CONNECTION, DB_USER,DB_PASSWORD);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return dbConnection;
    }
}