package com.netflixstatistix.unittest;
import com.netflixstatistix.connections.DatabaseConnection;
import com.netflixstatistix.session.Session;
import org.junit.*;
import java.sql.ResultSet;

public class NetflixTests {







    @Test
    public void databaseConnectionTest() {

        // Connection
        DatabaseConnection.connect();

        // Add expected data to String
        String testString = new String();
        ResultSet rs;
        try{
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT Titel FROM Programma WHERE ProgrammaID = '8002'");
            testString = rs.getString("test");
        } catch(Exception e) {}

        // Drop connection
        DatabaseConnection.disconnect();

        // Test
        String check = "Palp Fiction";
        Boolean isNotTheSame = (check == testString);
        Assert.assertFalse(isNotTheSame);
        System.out.println("Test completed succesfully. 1/2");
    }

    @Test
    public void sessionConnectionTest() {

        // Gather info and set parameter
        Session session = new Session(1215426);

        // Set comparison
        int testAbonneeID = 1215426;

        // Test
        Assert.assertTrue(session.getAbonneeID()==(1215426));
        System.out.println("Test completed succesfully. 2/2");
    }


}