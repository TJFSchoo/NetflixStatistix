package com.netflixstatistix.session;

import com.netflixstatistix.connections.DatabaseConnection;
import com.netflixstatistix.connections.DatabaseInterface;

import java.util.ArrayList;

public class Session {

    // Required Dependencies
    private DatabaseInterface di = new DatabaseInterface();


    // Initial Variables
    private int abonneeID = 1215426;
    private String abonneeEmail;
    private String abonneeWachtwoord;
    private String abonneeNaam;
    private String abonneeStraat;
    private String abonneeHuisnummer;
    private String abonneePostcode;
    private String abonneeWoonplaats;

    private String[] profielArray;
    private String profielNaam;
    private String profielGeboortedatum;

    // Statistics
    private ArrayList<String> latestVideoTitleArray;
    private String currentVideoTitle;
    private ArrayList<String> accountsWithOneProfile;
    private String longestMovieUnder16;
    private String[] allMoviesArray;
    private String selectedMovie;
    private int selectedMoviePercentage;


    public Session(int subID) {
        DatabaseConnection.connect();
        this.abonneeID = subID;
        this.abonneeEmail = di.getEmailFromAccount(abonneeID);
        this.abonneeWachtwoord = di.getPasswordFromAccount(abonneeID);
        this.abonneeNaam = di.getNameFromAccount(abonneeID);
        this.abonneeStraat = di.getStreetFromAccount(abonneeID);
        this.abonneeHuisnummer = di.getHouseNumberFromAccount(abonneeID);
        this.abonneePostcode = di.getZipCodeFromAccount(abonneeID);
        this.abonneeWoonplaats = di.getCityFromAccount(abonneeID);

        this.profielArray = di.getProfielenFromAbonnee(abonneeID);
        this.profielNaam = profielArray[0];
        this.profielGeboortedatum = di.getDateOfBirthFromProfile(this.profielNaam, this.abonneeID);

        this.latestVideoTitleArray = di.getTopTenLastViewedMoviesAndSeries(this.profielNaam, this.abonneeID);
        this.currentVideoTitle = this.latestVideoTitleArray.get(0);
        this.accountsWithOneProfile = di.getAccountsWithSingleProfile();
        this.longestMovieUnder16 = di.getLongestMovieUnderSixteen();
        this.allMoviesArray = di.getAllMovies();


        DatabaseConnection.disconnect();
    }

    public int getAbonneeID() {
        return abonneeID;
    }

    public void setAbonneeID(int abonneeID) {
        this.abonneeID = abonneeID;
    }

    public String getAbonneeEmail() {
        return abonneeEmail;
    }

    public void setAbonneeEmail(String abonneeEmail) {
        this.abonneeEmail = abonneeEmail;
    }

    public String getAbonneeWachtwoord() {
        return abonneeWachtwoord;
    }

    public void setAbonneeWachtwoord(String abonneeWachtwoord) {
        this.abonneeWachtwoord = abonneeWachtwoord;
    }

    public String getAbonneeNaam() {
        return abonneeNaam;
    }

    public void setAbonneeNaam(String abonneeNaam) {
        this.abonneeNaam = abonneeNaam;
    }

    public String getAbonneeStraat() {
        return abonneeStraat;
    }

    public void setAbonneeStraat(String abonneeStraat) {
        this.abonneeStraat = abonneeStraat;
    }

    public String getAbonneeHuisnummer() {
        return abonneeHuisnummer;
    }

    public void setAbonneeHuisnummer(String abonneeHuisnummer) {
        this.abonneeHuisnummer = abonneeHuisnummer;
    }

    public String getAbonneePostcode() {
        return abonneePostcode;
    }

    public void setAbonneePostcode(String abonneePostcode) {
        this.abonneePostcode = abonneePostcode;
    }

    public String getAbonneeWoonplaats() {
        return abonneeWoonplaats;
    }

    public void setAbonneeWoonplaats(String abonneeWoonplaats) {
        this.abonneeWoonplaats = abonneeWoonplaats;
    }

    public String[] getProfielArray() {
        return profielArray;
    }

    public void setProfielArray(String[] profielArray) {
        this.profielArray = profielArray;
    }

    public String getProfielNaam() {
        return profielNaam;
    }

    public void setProfielNaam(String profielNaam) {
        this.profielNaam = profielNaam;
    }

    public String getProfielGeboortedatum() {
        return profielGeboortedatum;
    }

    public void setProfielGeboortedatum(String profielGeboortedatum) {
        this.profielGeboortedatum = profielGeboortedatum;
    }

    public ArrayList<String> getLatestVideoTitleArray() {
        return latestVideoTitleArray;
    }

    public void setLatestVideoTitleArray(ArrayList<String> latestVideoTitleArray) {
        this.latestVideoTitleArray = latestVideoTitleArray;
    }

    public String getCurrentVideoTitle() {
        return currentVideoTitle;
    }

    public void setCurrentVideoTitle(String currentVideoTitle) {
        this.currentVideoTitle = currentVideoTitle;
    }

    public ArrayList<String> getAccountsWithOneProfile() {
        return accountsWithOneProfile;
    }

    public void setAccountsWithOneProfile(ArrayList<String> accountsWithOneProfile) {
        this.accountsWithOneProfile = accountsWithOneProfile;
    }

    public String getLongestMovieUnder16() {
        return longestMovieUnder16;
    }

    public void setLongestMovieUnder16(String longestMovieUnder16) {
        this.longestMovieUnder16 = longestMovieUnder16;
    }

    public String[] getAllMoviesArray() {
        return allMoviesArray;
    }

    public void setAllMoviesArray(String[] allMoviesArray) {
        this.allMoviesArray = allMoviesArray;
    }

    public int getSelectedMovieDetails() {
        DatabaseConnection.connect();
        this.selectedMoviePercentage = di.getAmountOfPeopleWhoFullyWatchedAMovie(selectedMovie);
        DatabaseConnection.disconnect();
        return selectedMoviePercentage;
    }

    public void setSelectedMovie(String selectedMovie) {
        this.selectedMovie = selectedMovie;
    }
}
