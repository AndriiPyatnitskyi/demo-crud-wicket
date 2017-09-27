
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_CONNECTION = "jdbc:postgresql://localhost:5432/test1";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "Everestpro2000";
    private static String TableName;

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
        TableName = "USERS";


        String createUserTable = "CREATE TABLE " + TableName + "(\n" +
                "   ID       INT     NOT NULL PRIMARY KEY ,\n" +
                "   NAME     TEXT    NOT NULL,\n" +
                "   SURNAME  TEXT    NOT NULL\n" +
                ");";

        String insertUser1 = "INSERT INTO USERS (ID,NAME,SURNAME) VALUES (1, 'John', 'Dow');";
        String insertUser2 = "INSERT INTO USERS (ID,NAME,SURNAME) VALUES (2, 'Peter', 'Smith');";

        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();

            // execute the SQL stetement
            statement.execute(createUserTable);

            System.out.println("Table " + TableName +  "is created!");

            statement.execute(insertUser1);
            statement.execute(insertUser2);

            System.out.println("Users were inserted");



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