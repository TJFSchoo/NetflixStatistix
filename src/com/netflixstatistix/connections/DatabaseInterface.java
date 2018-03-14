package com.netflixstatistix.connections;

import java.sql.*;
import java.time.LocalDateTime;//fap
import java.util.ArrayList;
import java.util.Arrays;

public class DatabaseInterface {


    private ResultSet rs;


    // (INT) GET WATCH TIME
    public int getWatchTime(int ProgrammaID, String Profielnaam, int AbonneeID) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT Percentage FROM BekekenProgramma WHERE ProgrammaID = '" + ProgrammaID + "' AND Profielnaam = '" + Profielnaam + "' AND AbonneeID = '" + AbonneeID + "';");
            if (rs.next()) {
                return rs.getInt("Percentage");
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
            return 0;
        }
    }

    // (INT) AccountID from AccountNaam
    public int getAccountIDFromAccountName(String AccountNaam) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT Abonnee.AbonneeID FROM Abonnee WHERE Abonnee.Naam = "+ AccountNaam + "");
            if (rs.next()) {
                return rs.getInt("AbonneeID");
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
            return 0;
        }
    }

    // (INT) GET AMOUNT OF EPISODES PER SERIE AMOUNT
    public int getTotalEpisodesInSerie(String serieNaam) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT COUNT(Serie) AS Total FROM Aflevering WHERE Serie = '" + serieNaam + "';");
            if (rs.next()) {
                return rs.getInt("Total");
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("An Error Occurred.. "  + e.getMessage());
            return 0;
        }
    }

    // (String[]) GET PROFIELEN FROM ABONNEE
    public String[] getProfielenFromAbonnee(int AbonneeID) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT Profielnaam FROM Profiel JOIN Abonnee ON Abonnee.AbonneeID = Profiel.AbonneeID WHERE Abonnee.AbonneeID = '" + AbonneeID + "';");
            ArrayList<String> profielen = new ArrayList<String>();
            while (rs.next()) {
                profielen.add(rs.getString("Profielnaam"));
            }
            String[] array = new String[profielen.size()];
            array = profielen.toArray(array);
            return array;

        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
            String[] array2 = new String[0];
            Arrays.fill(array2, "Error fetching profiles");
            return array2;
        }
    }

    // (STRING) GET LONGEST MOVIE BY MAX AGE -> age
    public String getLongestMovieByMaxAge(int age) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT Titel FROM Film WHERE GeschikteLeeftijd < " + age + " ORDER BY Tijdsduur DESC;");
            if (rs.next()) {
                return rs.getString("Titel");
            } else {
                return "Geen film gevonden";
            }
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
            return "Error fetching movie";
        }
    }

    // (String[]) GET ALL MOVIES VIEWED BY SINGLE ACCOUNT(via AbonneeID)
    public String[] getAllMoviesViewedByAccount(int AbonneeID) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT DISTINCT Film.Titel FROM Film JOIN Programma ON Film.ProgrammaID = Programma.ProgrammaID JOIN BekekenProgramma ON Programma.ProgrammaID = BekekenProgramma.ProgrammaID JOIN Profiel ON Profiel.Profielnaam = BekekenProgramma.Profielnaam JOIN Abonnee ON Abonnee.AbonneeID = Profiel.AbonneeID WHERE Abonnee.AbonneeID = '" + AbonneeID + "';");
            ArrayList<String> moviesViewedByAccount = new ArrayList<String>();
            while (rs.next()) {
                moviesViewedByAccount.add(rs.getString("Titel"));
            }
            String[] array = new String[moviesViewedByAccount.size()];
            array = moviesViewedByAccount.toArray(array);
            return array;

        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
            String[] array2 = new String[0];
            Arrays.fill(array2, "Error fetching list of movies watched by this account ID");
            return array2;
        }
    }

    // (String[]) GET ALL MOVIES VIEWED BY SINGLE ACCOUNT(via Abonnee.Naam)
    public ArrayList<String> getAllMoviesViewedByAccountName(String AbonneeNaam) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT DISTINCT Film.Titel FROM Film JOIN Programma ON Film.ProgrammaID = Programma.ProgrammaID JOIN BekekenProgramma ON Programma.ProgrammaID = BekekenProgramma.ProgrammaID JOIN Profiel ON Profiel.Profielnaam = BekekenProgramma.Profielnaam JOIN Abonnee ON Abonnee.AbonneeID = Profiel.AbonneeID WHERE Abonnee.Naam = '" + AbonneeNaam + "';");
            ArrayList<String> moviesViewedByAccount = new ArrayList<String>();
            while (rs.next()) {
                moviesViewedByAccount.add(rs.getString("Titel"));
            }

            if(moviesViewedByAccount.isEmpty()){
                moviesViewedByAccount.add("Dit account heeft nog geen films bekeken.");
            }
            return moviesViewedByAccount;

        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
            ArrayList<String> moviesViewedByAccountTwo = new ArrayList<String>();
            moviesViewedByAccountTwo.add("Error writing list of movies viewed by account");
            return moviesViewedByAccountTwo;
        }
    }


    // (ArrayList<String>) GET ALL ACCOUNTS WITH ONLY A SINGLE PROFILE
    public ArrayList<String> getAccountsWithSingleProfile () {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT Abonnee.Naam FROM Profiel JOIN Abonnee ON Profiel.AbonneeID = Abonnee.AbonneeID GROUP BY Abonnee.Naam HAVING COUNT(*) = 1;");
            ArrayList<String> accountsWithSingleProfile = new ArrayList<>();
            if (!rs.next()) {
                accountsWithSingleProfile.add("Geen resultaten gevonden");
                return accountsWithSingleProfile;
            }
            while (rs.next()) {
                accountsWithSingleProfile.add(rs.getString("Naam"));
            }
            return accountsWithSingleProfile;
        } catch(Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
            return null;
        }
    }

    // (int) GET TOTAL QTY OF PROFILES ('Profiel' IN DATABASE) WHO VIEWED SPECIFIC MOVIE FOR FULL DURATION (100%)
    public int getHowManyViewersViewedThisMovieCompletely (String movie) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT COUNT(Film.Titel) FROM BekekenProgramma INNER JOIN Film ON Film.ProgrammaID = Bekekenprogramma.ProgrammaID WHERE (BekekenProgramma.Percentage = '100') AND (Film.Titel = '" + movie + "');");

            if (!rs.next()) {
                return 0;
            }

            return  rs.getInt(1);
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
            return 0;
        }
    }

    // ADD ACCOUNT (AbonneeID = PK)
    public void addAccount (int AbonneeID, String Email, String Wachtwoord, String Naam, String Straat, String Huisnummer, String Postcode, String Woonplaats) {
        try{
            rs = DatabaseConnection.giveStatementAndGetResult("INSERT INTO Abonnee (AbonneeID,Email,Wachtwoord,Naam,Straat,Huisnummer,Postcode,Woonplaats) VALUES ('" + AbonneeID + "','" + Email + "','" + Wachtwoord + "','" + Naam + "','" + Straat + "','" + Huisnummer + "','" + Postcode + "','" + Woonplaats + "');");
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
        }
    }

    // DELETE ACCOUNT
    public void deleteAccount (int AbonneeID) {
        try{
            rs = DatabaseConnection.giveStatementAndGetResult("DELETE FROM Abonnee WHERE Abonnee.AbonneeID = '" + AbonneeID + "';");
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
        }
    }

    // ACCOUNT ADJUST EMAIL
    public void changeAccountEmail (int AccountID, String newEmail) {
        try{
            DatabaseConnection.giveStatement("UPDATE Abonnee SET Abonnee.Email = '" + newEmail + "' WHERE  Abonnee.AbonneeID = '" + AccountID + "';");
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
        }
    }

    // ACCOUNT ADJUST PASSWORD
    public void changeAccountPassword (int AccountID, String newPassword) {
        try {
            DatabaseConnection.giveStatement("UPDATE Abonnee SET Abonnee.Wachtwoord = '" + newPassword + "' WHERE  Abonnee.AbonneeID = '" + AccountID + "';");
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
        }
    }

    // ACCOUNT ADJUST NAME
    public void changeAccountName (int AccountID, String newName) {
        try{
            DatabaseConnection.giveStatement("UPDATE Abonnee SET Abonnee.Naam = '" + newName + "' WHERE  Abonnee.AbonneeID = '" + AccountID + "';");
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
        }
    }

    // ACCOUNT ADJUST STREET
    public void changeAccountStreet (int AccountID, String newStreet) {
        try{
            DatabaseConnection.giveStatement("UPDATE Abonnee SET Abonnee.Straat = '" + newStreet + "' WHERE  Abonnee.AbonneeID = '" + AccountID + "';");
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
        }
    }

    // ACCOUNT ADJUST HOUSENUMBER
    public void changeAccountHouseNumber (int AccountID, String newHouseNumber) {
        try{
            DatabaseConnection.giveStatement("UPDATE Abonnee SET Abonnee.Huisnummer = '" + newHouseNumber + "' WHERE  Abonnee.AbonneeID = '" + AccountID + "';");
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
        }
    }

    // ACCOUNT ADJUST ZIP CODE
    public void changeAccountZIP (int AccountID, String newZIP) {
        try{
           DatabaseConnection.giveStatement("UPDATE Abonnee SET Abonnee.Postcode = '" + newZIP + "' WHERE  Abonnee.AbonneeID = '" + AccountID + "';");
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
        }
    }

    // ACCOUNT ADJUST CITY
    public void changeCity (int AccountID, String newCity) {
        try{
            DatabaseConnection.giveStatement("UPDATE Abonnee SET Abonnee.Woonplaats = '" + newCity + "' WHERE  Abonnee.AbonneeID = '" + AccountID + "';");
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
        }
    }

    // ADD PROFILE TO ACCOUNT (Profielnaam + AbonneeID = Composite PK. ProfielID = FK to Abonnee.AbonneeID)
    public void addProfile (String Profielnaam, String Geboortedatum, int AbonneeID) {
        try {
            if(getAmountOfProfilesOnAccount(AbonneeID) < 5){
                rs = DatabaseConnection.giveStatementAndGetResult("INSERT INTO Profiel (Profielnaam,Geboortedatum,AbonneeID) VALUES ('" + Profielnaam + "','" + Geboortedatum + "','" + AbonneeID + "');");
            } else {
                System.out.println("Profile cannot be added. Max number of profiles on account " + AbonneeID + " reached.");
            }
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
        }
    }

    // DELETE PROFILE FROM ACCOUNT
    public void deleteProfile (String Profielnaam, int AbonneeID) {
        try{
            rs = DatabaseConnection.giveStatementAndGetResult("DELETE FROM Profiel WHERE Profiel.Profielnaam = '" + Profielnaam + "' AND Profiel.AbonneeID = '" + AbonneeID + "';");
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
        }
    }

    // PROFILE NAME ADJUST
    public void changeProfileName (String nieuweProfielnaam, String oudeProfielnaam, int AbonneeID) {
        try{
            DatabaseConnection.giveStatement("UPDATE Profiel SET Profielnaam = '" + nieuweProfielnaam + "' WHERE  Profielnaam = '" + oudeProfielnaam + "' AND AbonneeID = '" + AbonneeID + "';");
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
        }
    }

    // PROFILE DATE OF BIRTH ADJUST
    public void changeProfileDateOfBirth (String nieuweGeboortedatum, String Profielnaam, int AbonneeID) {
        try{
            DatabaseConnection.giveStatement("UPDATE Profiel SET Geboortedatum = '" + nieuweGeboortedatum + "' WHERE  Profielnaam = '" + Profielnaam + "' AND AbonneeID = '" + AbonneeID + "';");
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
        }
    }

    // ADD VIEWED PROGRAM
    public void addWatchedProgram (int Percentage, String Profielnaam, int AbonneeID, int ProgrammaID, String Titel) {
        try{
            rs = DatabaseConnection.giveStatementAndGetResult("INSERT INTO BekekenProgramma(Percentage,Profielnaam,AbonneeID,ProgrammaID,Titel,LaatstBekeken) VALUES ('" + Percentage + "','" + Profielnaam + "','" + AbonneeID + "','" + ProgrammaID + "','" + Titel + "','" + LocalDateTime.now() + "');");
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
        }
    }

    // DELETE VIEWED PROGRAM
    public void deleteWatchedProgram (String Profielnaam, int AbonneeID, int ProgrammaID) {
        try{
            rs = DatabaseConnection.giveStatementAndGetResult("DELETE FROM BekekenProgramma WHERE BekekenProgramma.Profielnaam = '" + Profielnaam + "' AND BekekenProgramma.AbonneeID = '" + AbonneeID + " AND BekekenProgramma.ProgrammaID = '" + ProgrammaID + "'';");
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
        }
    }

    // ADJUST PERCENTAGE VIEWED OF VIEWED PROGRAM AND LAST VIEWED MOMENT TO CURRENT TIMESTAMP
    public void adjustWatchedPercentage (int NieuwPercentage, String Profielnaam, int AbonneeID, int ProgrammaID) {
        try{
            rs = DatabaseConnection.giveStatementAndGetResult("UPDATE BekekenProgramma SET BekekenProgramma.Percentage = '" + NieuwPercentage + "' WHERE BekekenProgramma.Profielnaam = '" + Profielnaam + "' AND BekekenProgramma.AbonneeID = '" + AbonneeID + " AND BekekenProgramma.ProgrammaID = '" + ProgrammaID + "''; UPDATE BekekenProgramma SET BekekenProgramma.LaatstBekeken = '" + LocalDateTime.now() + "' WHERE BekekenProgramma.Profielnaam = '" + Profielnaam + "' AND BekekenProgramma.AbonneeID = '" + AbonneeID + " AND BekekenProgramma.ProgrammaID = '" + ProgrammaID + "'';");
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
        }
    }

    // (String) GET TOP 1 ACCOUNT (FOR STARTING REFERENCE)
    public String getTopAccount() {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT TOP 1 Abonnee.Naam FROM Abonnee;");
            if (rs.next()) {
                return rs.getString("Naam");
            } else {
                return "Geen account gevonden";
            }
        } catch (Exception e) {
            System.out.println("Error fetching top account, " + e.getMessage());
            return "Error fetching top account";
        }
    }

    // (ArrayList<String>) GET TOP 10 LAST VIEWED MOVIES AND EPISODES FROM PROFILE
    public ArrayList<String> getTopTenLastViewedMoviesAndSeries(String Profielnaam, int AbonneeID) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT TOP 10 BekekenProgramma.Titel AS LaatstBekeken FROM BekekenProgramma WHERE BekekenProgramma.Profielnaam = '" + Profielnaam + "' AND BekekenProgramma.AbonneeID = '" + AbonneeID + "' ORDER BY BekekenProgramma.LaatstBekeken DESC;");
            ArrayList<String> lastViewedMoviesAndSeries = new ArrayList<String>();
            while (rs.next()) {
                lastViewedMoviesAndSeries.add(rs.getString("LaatstBekeken"));
            }
            return lastViewedMoviesAndSeries;

        } catch (Exception e) {
            System.out.println("Error fetching top 10 last viewed" + e.getMessage());
            ArrayList<String> errorArray = new ArrayList<String>();
            errorArray.add("Error fetching top 10 last viewed");
            return errorArray;
        }
    }

    // (String[]) GET LIST OF ACCOUNT NAMES ARRAY
    public String[] getListOfAccountNames() {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT Abonnee.Naam FROM Abonnee;");
            ArrayList<String> listOfAccountNames = new ArrayList<String>();
            while (rs.next()) {
                listOfAccountNames.add(rs.getString("Naam"));
            }
            String[] array = new String[listOfAccountNames.size()];
            array = listOfAccountNames.toArray(array);
            return array;

        } catch (Exception e) {
            System.out.println("Error fetching list of account names, " + e.getMessage());
            String[] array2 = new String[0];
            Arrays.fill(array2, "Error fetching list of account names");
            return array2;
        }
    }

    // (String[]) GET LIST OF ACCOUNT NAMES ARRAY
    public ArrayList<String> getListOfAccountNamesArrayList() {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT Abonnee.Naam FROM Abonnee;");
            ArrayList<String> listOfAccountNames = new ArrayList<String>();
            while (rs.next()) {
                listOfAccountNames.add(rs.getString("Naam"));
            }
            return listOfAccountNames;

        } catch (Exception e) {
            System.out.println("Error fetching list of account names, " + e.getMessage());
            ArrayList<String> listOfAccountNamesTwo = new ArrayList<String>();
            listOfAccountNamesTwo.add("No accounts found.");
            return listOfAccountNamesTwo;
        }
    }

    // (ArrayList<String>) GET LIST OF PROFILE NAMES FROM ACCOUNT
    public ArrayList<String> getListOfProfilesFromAccount(int AbonneeID) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT Profielnaam FROM Profiel WHERE AbonneeID = '"  + AbonneeID +  "';");
            ArrayList<String> listOfProfilesFromAccount = new ArrayList<String>();
            while (rs.next()) {
                listOfProfilesFromAccount.add(rs.getString("Profielnaam"));
            }

            return listOfProfilesFromAccount;

        } catch (Exception e) {
            System.out.println("Error fetching list of profile names, " + e.getMessage());
            ArrayList<String> listOfProfilesFromAccountTwo = new ArrayList<String>();
            listOfProfilesFromAccountTwo.add("No profiles on account");
            return listOfProfilesFromAccountTwo;
        }
    }

    // (int) GET AMOUNT OF PROFILES ON ACCOUNT
    public int getAmountOfProfilesOnAccount(int AbonneeID) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT COUNT(Profiel.Profielnaam) AS Total FROM Abonnee JOIN Profiel ON Abonnee.AbonneeID = Profiel.AbonneeID WHERE Abonnee.AbonneeID = '" + AbonneeID + "';");
            if (rs.next()) {
                return rs.getInt("Total");
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("An Error Occurred.. "  + e.getMessage());
            return 0;
        }
    }

    // (STRING) GET EMAIL FROM ACCOUNT
    public String getEmailFromAccount(int AbonneeID) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT Email FROM Abonnee WHERE Abonnee.AbonneeID = '" + AbonneeID + "';");
            if (rs.next()) {
                return rs.getString("Email");
            } else {
                return "Geen Email gevonden";
            }
        } catch (Exception e) {
            System.out.println("Error fetching Email from Account, " + e.getMessage());
            return "Error fetching Email from Account";
        }
    }

    // (STRING) GET PASSWORD FROM ACCOUNT
    public String getPasswordFromAccount(int AbonneeID) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT Wachtwoord FROM Abonnee WHERE Abonnee.AbonneeID = '" + AbonneeID + "';");
            if (rs.next()) {
                return rs.getString("Wachtwoord");
            } else {
                return "Geen Wachtwoord gevonden";
            }
        } catch (Exception e) {
            System.out.println("Error fetching Password from Account, " + e.getMessage());
            return "Error fetching Password from Account";
        }
    }

    // (STRING) GET NAME FROM ACCOUNT
    public String getNameFromAccount(int AbonneeID) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT Naam FROM Abonnee WHERE Abonnee.AbonneeID = '" + AbonneeID + "';");
            if (rs.next()) {
                return rs.getString("Naam");
            } else {
                return "Geen Naam gevonden";
            }
        } catch (Exception e) {
            System.out.println("Error fetching Name from Account, " + e.getMessage());
            return "Error fetching Name from Account";
        }
    }

    // (STRING) GET STREET FROM ACCOUNT
    public String getStreetFromAccount(int AbonneeID) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT Straat FROM Abonnee WHERE Abonnee.AbonneeID = '" + AbonneeID + "';");
            if (rs.next()) {
                return rs.getString("Straat");
            } else {
                return "Geen Straat gevonden";
            }
        } catch (Exception e) {
            System.out.println("Error fetching Street from Account, " + e.getMessage());
            return "Error fetching Street from Account";
        }
    }

    // (STRING) GET HOUSENUMBER FROM ACCOUNT
    public String getHouseNumberFromAccount(int AbonneeID) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT Huisnummer FROM Abonnee WHERE Abonnee.AbonneeID = '" + AbonneeID + "';");
            if (rs.next()) {
                return rs.getString("Huisnummer");
            } else {
                return "Geen Huisnummer gevonden";
            }
        } catch (Exception e) {
            System.out.println("Error fetching HouseNumber from Account, " + e.getMessage());
            return "Error fetching HouseNumber from Account";
        }
    }

    // (STRING) GET ZIPCODE FROM ACCOUNT
    public String getZipCodeFromAccount(int AbonneeID) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT Postcode FROM Abonnee WHERE Abonnee.AbonneeID = '" + AbonneeID + "';");
            if (rs.next()) {
                return rs.getString("Postcode");
            } else {
                return "Geen Postcode gevonden";
            }
        } catch (Exception e) {
            System.out.println("Error fetching Zip code from Account, " + e.getMessage());
            return "Error fetching Zip code from Account";
        }
    }

    // (STRING) GET CITY FROM ACCOUNT
    public String getCityFromAccount(int AbonneeID) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT Woonplaats FROM Abonnee WHERE Abonnee.AbonneeID = '" + AbonneeID + "';");
            if (rs.next()) {
                return rs.getString("Woonplaats");
            } else {
                return "Geen Woonplaats gevonden";
            }
        } catch (Exception e) {
            System.out.println("Error fetching City from Account, " + e.getMessage());
            return "Error fetching City from Account";
        }
    }

    // (STRING) GET NAME FROM PROFILE
    public String getNameFromProfile(String Profielnaam, int AbonneeID) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT Profielnaam FROM Profiel WHERE Profielnaam = '" + Profielnaam + "' AND AbonneeID = '" + AbonneeID + "';");
            if (rs.next()) {
                return rs.getString("Profielnaam");
            } else {
                return "Geen Naam gevonden";
            }
        } catch (Exception e) {
            System.out.println("Error fetching Name from Profile, " + e.getMessage());
            return "Error fetching Name from Profile";
        }
    }

    // (STRING) GET DATEOFBIRTH FROM PROFILE
    public String getDateOfBirthFromProfile(String Profielnaam, int AbonneeID) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT Geboortedatum FROM Profiel WHERE Profielnaam = '" + Profielnaam + "' AND AbonneeID = '" + AbonneeID + "';");
            if (rs.next()) {
                return rs.getString("Geboortedatum");
            } else {
                return "Geen Geboortedatum gevonden";
            }
        } catch (Exception e) {
            System.out.println("Error fetching Date of Birth from Profile, " + e.getMessage());
            return "Error fetching Date of Birth from Profile";
        }
    }

    // (STRING) GET ATTACHED ACCOUNT NAME FROM PROFILE
    public String getAttachedAccountNameFromProfile(String Profielnaam, int AbonneeID) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT Abonnee.Naam FROM Profiel JOIN Abonnee ON Abonnee.AbonneeID = Profiel.AbonneeID WHERE Profielnaam = '" + Profielnaam + "' AND AbonneeID = '" + AbonneeID + "';");
            if (rs.next()) {
                return rs.getString("Naam");
            } else {
                return "Geen AbonneeNaam gevonden";
            }
        } catch (Exception e) {
            System.out.println("Error fetching attached AbonneeNaam from Profile, " + e.getMessage());
            return "Error fetching attached AbonneeNaam from Profile";
        }
    }

    // (STRING) GET ATTACHED ACCOUNT ID FROM PROFILE
    public String getAttachedAccountIDFromProfile(String Profielnaam, int AbonneeID) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT Abonnee.AbonneeID FROM Profiel JOIN Abonnee ON Abonnee.AbonneeID = Profiel.AbonneeID WHERE Profielnaam = '" + Profielnaam + "' AND AbonneeID = '" + AbonneeID + "';");
            if (rs.next()) {
                return rs.getString("AbonneeID");
            } else {
                return "Geen AbonneeID gevonden";
            }
        } catch (Exception e) {
            System.out.println("Error fetching attached AbonneeID from Profile, " + e.getMessage());
            return "Error fetching attached AbonneeID from Profile";
        }
    }

    // (STRING) GET LONGEST MOVIE UNDER SIXTEEN YRS
    public String getLongestMovieUnderSixteen() {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT TOP 1 Titel FROM Film WHERE (GeschikteLeeftijd < 16) ORDER BY Tijdsduur DESC");
            if (rs.next()) {
                return rs.getString("Titel");
            } else {
                return "Geen Film gevonden";
            }
        } catch (Exception e) {
            System.out.println("Error fetching longest movie under sixteen, " + e.getMessage());
            return "Error fetching longest movie under sixteen";
        }
    }

    // (STRING[]) GET ALL MOVIES
    public String[] getAllMovies() {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT Titel FROM Film");
            ArrayList<String> listOfMovies = new ArrayList<String>();
            while (rs.next()) {
                listOfMovies.add(rs.getString("Titel"));
            }
            String[] array = new String[listOfMovies.size()];
            array = listOfMovies.toArray(array);
            return array;
        } catch (Exception e) {
            System.out.println("Error fetching all movies, " + e.getMessage());
            return null;
        }
    }

    public int getAmountOfPeopleWhoFullyWatchedAMovie(String movieName) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT COUNT(*) AS Getal FROM BekekenProgramma WHERE Titel = '" + movieName + "' AND Percentage = '100'");
            int output = 0;
            while (rs.next()) {
                output = rs.getInt("Getal");
            }
            return output;
        } catch (Exception e) {
            System.out.println("Error fetching watched amount int by movie" + e.getMessage());
            return 0;
        }
    }

    // ArrayList<String> GET AVG WATCH TIME OF SERIE PER ACCOUNT
    public ArrayList<String> getAvgWatchTimeOfSeriePerAccount(String AbonneeNaam, String SerieNaam) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT CONCAT(Aflevering.SeizoenEnAflevering,' - ', Aflevering.Titel,' - ',AVG(BekekenProgramma.Percentage),'% bekeken') AS Aflevering \n" +
                    "FROM BekekenProgramma\n" +
                    "JOIN Programma ON BekekenProgramma.ProgrammaID = Programma.ProgrammaID\n" +
                    "JOIN Aflevering ON Programma.ProgrammaID = Aflevering.ProgrammaID\n" +
                    "JOIN Serie ON Aflevering.Serie = Serie.SerieNaam\n" +
                    "JOIN Profiel ON BekekenProgramma.AbonneeID = Profiel.AbonneeID\n" +
                    "JOIN Abonnee ON Profiel.AbonneeID = Abonnee.AbonneeID\n" +
                    "WHERE (Serie.SerieNaam = '" + SerieNaam + "') AND (Abonnee.Naam = '" + AbonneeNaam + "')\n" +
                    "GROUP BY Aflevering.SeizoenEnAflevering, Aflevering.Titel, Aflevering.AfleveringID\n" +
                    "ORDER BY Aflevering.AfleveringID;\n");
            ArrayList<String> listOfAvgWatchedEpisodes = new ArrayList<String>();
            while (rs.next()) {
                listOfAvgWatchedEpisodes.add(rs.getString("Aflevering"));
            }

            if(listOfAvgWatchedEpisodes.isEmpty()){
                listOfAvgWatchedEpisodes.add("Geen afleveringen bekeken.");
            }

            return listOfAvgWatchedEpisodes;

        } catch (Exception e) {
            System.out.println("Error fetching average watchtime of episodes, " + e.getMessage());
            ArrayList<String> listOfAccountNamesTwo = new ArrayList<String>();
            listOfAccountNamesTwo.add("Error fetching average watchtime of episodes");
            return listOfAccountNamesTwo;
        }
    }

    // ArrayList<String> GET AVG WATCH TIME OF SERIE
    public ArrayList<String> getAvgWatchTimeOfSerie(String SerieNaam) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT CONCAT(Aflevering.SeizoenEnAflevering,' - ', Aflevering.Titel,' - ',AVG(BekekenProgramma.Percentage),'% bekeken') AS Aflevering, AVG(BekekenProgramma.Percentage) AS AvgBekekenPercentage\n" +
                    "FROM BekekenProgramma\n" +
                    "JOIN Programma ON BekekenProgramma.ProgrammaID = Programma.ProgrammaID\n" +
                    "JOIN Aflevering ON Programma.ProgrammaID = Aflevering.ProgrammaID\n" +
                    "JOIN Serie ON Aflevering.Serie = Serie.SerieNaam\n" +
                    "WHERE Serie.SerieNaam = '" + SerieNaam + "'\n" +
                    "GROUP BY Aflevering.SeizoenEnAflevering, Aflevering.Titel, Aflevering.AfleveringID\n" +
                    "ORDER BY Aflevering.AfleveringID;\n");
            ArrayList<String> listOfAvgWatchedEpisodes = new ArrayList<String>();
            while (rs.next()) {
                listOfAvgWatchedEpisodes.add(rs.getString("Aflevering"));
            }

            if(listOfAvgWatchedEpisodes.isEmpty()){
                listOfAvgWatchedEpisodes.add("Geen afleveringen bekeken.");
            }

            return listOfAvgWatchedEpisodes;

        } catch (Exception e) {
            System.out.println("Error fetching average watchtime of episodes, " + e.getMessage());
            ArrayList<String> listOfAccountNamesTwo = new ArrayList<String>();
            listOfAccountNamesTwo.add("Error fetching average watchtime of episodes");
            return listOfAccountNamesTwo;
        }
    }

    // ArrayList<String> GET ALL SERIES
    public ArrayList<String> getAllSeries() {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT SerieNaam FROM Serie");
            ArrayList<String> allSeries = new ArrayList<String>();
            while (rs.next()) {
                allSeries.add(rs.getString("SerieNaam"));
            }
            return allSeries;

        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
            ArrayList<String> allSeriesTwo = new ArrayList<String>();
            allSeriesTwo.add("No series found.");
            return allSeriesTwo;
        }
    }

    public int getAccountIDByAccountName(String accountName) {
        rs = DatabaseConnection.giveStatementAndGetResult("SELECT AbonneeID FROM Abonnee WHERE Naam = '" + accountName + "';");

        try {
            int output = 0;
            while (rs.next()) {
                output = rs.getInt("AbonneeID");
            }
            return output;
        } catch (Exception e) {
            System.out.println("Error getting AccountID By AccountName");
            return 0;
        }

    }

}

