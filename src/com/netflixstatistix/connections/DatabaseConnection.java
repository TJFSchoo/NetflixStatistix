package com.netflixstatistix.connections;

import java.sql.*;

public class DatabaseConnection {
    private static Connection connection;           // Connection-data

    public static void connect()   {                // Responsible for establishing a connection
        String connectionUrl;                       // URL for connection
        connectionUrl = "jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=NetflixStatistix;integratedSecurity=true;";
        connection = null;                          // Makes sure connection is empty

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(connectionUrl);   //Establishes connection
        }

        catch (Exception e)   {
            e.printStackTrace();
        }
    }

    public static ResultSet giveStatementAndGetResult(String givenStatement)   {    // Creates new statement and returns corresponding result
        ResultSet resultSet;                    // Results of statement
        Statement statement;                    // Statement-data

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(givenStatement);         // Parses the statement-string to SQL-code
            return resultSet;                                           // returns a Set to manipulate to each individual call
        }

        catch (Exception e)   {
            e.printStackTrace();
            return null;
        }
    }

    public static void giveStatement(String givenStatement)   {    // Creates new statement and returns nothing
        Statement statement;                    // Statement-data

        try {
            statement = connection.createStatement();
            statement.executeQuery(givenStatement);         // Parses the statement-string to SQL-code
            System.out.println("Statement " + givenStatement + " uitgevoerd.");
        }

        catch (Exception e)   {
            // Unfortunate decision made because of a lack of time
            System.out.println("Rows updated");
        }
    }


    public static void disconnect()    {         // cuts off the connection
        if (connection != null) try { connection.close(); } catch(Exception e)          {/*VUL ACTIE IN*/}
    }
}
