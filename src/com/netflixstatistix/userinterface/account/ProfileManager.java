package com.netflixstatistix.userinterface.account;

import com.netflixstatistix.connections.DatabaseConnection;
import com.netflixstatistix.connections.DatabaseInterface;
import com.netflixstatistix.session.CurrentSession;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ProfileManager extends CurrentSession implements Runnable{

    private JFrame frame;
    private DatabaseInterface di = new DatabaseInterface();

    public ProfileManager() {
    }

    @Override
    public void run() {
        frame = new JFrame("Wijzig Profielen");
        frame.setPreferredSize(new Dimension(600, 300));
        frame.setResizable(false);

        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        createComponents(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);
    }

    private void createComponents(Container container) {

        DatabaseConnection.connect();
        JPanel basePanel = new JPanel(new GridBagLayout());
        basePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();

        ArrayList<String> profileNames = di.getListOfProfilesFromAccount(session.getAbonneeID());
        int i = 1;

        for (String profielnaam : profileNames) {

            JLabel profielLabel = new JLabel("Profiel " + i);
            gbc.anchor = GridBagConstraints.EAST;
            gbc.gridx = 0;
            gbc.gridy = i;
            basePanel.add(profielLabel, gbc);


            JTextField profileNameField = new JTextField(profielnaam);
            profileNameField.setPreferredSize(new Dimension(100, 20));
            gbc.anchor = GridBagConstraints.WEST;
            gbc.gridx = 1;
            gbc.gridy = i;
            basePanel.add(profileNameField, gbc);


            JTextField profileBirthdayField = new JTextField(di.getDateOfBirthFromProfile(profielnaam, session.getAbonneeID()));
            profileBirthdayField.setPreferredSize(new Dimension(100, 20));
            gbc.anchor = GridBagConstraints.WEST;
            gbc.gridx = 2;
            gbc.gridy = i;
            basePanel.add(profileBirthdayField, gbc);

            JButton editButton = new JButton("Bewerk");
            editButton.setPreferredSize(new Dimension(100, 20));
            gbc.gridx = 3;
            gbc.gridy = i;
            i++;

            editButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DatabaseConnection.connect();

                    boolean dateIsRight = false;
                    try {
                        dateIsRight = new SimpleDateFormat("yyyy-MM-dd").parse(profileBirthdayField.getText()).before(new Date());
                    } catch (ParseException parseException) {
                        parseException.printStackTrace();
                        System.out.println("Incorrect Date");

                    }

                    if ((profileNameField.getText().matches("[A-Z]{1}[a-z]{1,29}")) && profileBirthdayField.getText().matches("([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))") && dateIsRight) {
                        di.changeProfileName(profileNameField.getText(), profielnaam, session.getAbonneeID());
                        di.changeProfileDateOfBirth(profileBirthdayField.getText(), profielnaam, session.getAbonneeID());

                        JOptionPane.showMessageDialog(frame, "Gegevens zijn succesvol aangepast.", "Succes", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Gegevens zijn succesvol aangepast.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } if ((profileNameField.getText().isEmpty()) && (profileBirthdayField.getText().isEmpty())) {
                        di.deleteProfile(profielnaam, session.getAbonneeID());
                        JOptionPane.showMessageDialog(frame, "Profiel is verwijderd.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }

                    else {

                        JOptionPane.showMessageDialog(frame, "De opgegeven naam of geboortedatum voldoet niet aan de vereisten. \n" +
                                                            "Voorbeeld: Bas, 1998-11-24", "Fout!", JOptionPane.ERROR_MESSAGE);
                    }
                    DatabaseConnection.disconnect();
                }
            });

            basePanel.add(editButton, gbc);

        }
        if (i > 0 && i < 6) {
            JLabel newProfileLabel = new JLabel("Nieuw Profiel " + i);
            gbc.anchor = GridBagConstraints.EAST;
            gbc.gridx = 0;
            gbc.gridy = i;
            basePanel.add(newProfileLabel, gbc);


            JTextField newProfileNameField = new JTextField("");
            newProfileNameField.setPreferredSize(new Dimension(100, 20));
            gbc.anchor = GridBagConstraints.WEST;
            gbc.gridx = 1;
            gbc.gridy = i;
            basePanel.add(newProfileNameField, gbc);


            JTextField newProfileBirthdayField = new JTextField("YYYY-MM-DD");
            newProfileBirthdayField.setPreferredSize(new Dimension(100, 20));
            gbc.anchor = GridBagConstraints.WEST;
            gbc.gridx = 2;
            gbc.gridy = i;
            basePanel.add(newProfileBirthdayField, gbc);

            JButton newProfileButton = new JButton("Voeg toe");
            newProfileButton.setPreferredSize(new Dimension(100, 20));
            gbc.gridx = 3;
            gbc.gridy = i;

            basePanel.add(newProfileButton, gbc);
            newProfileButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DatabaseConnection.connect();

                    boolean dateIsRight = false;
                    try {
                        dateIsRight = new SimpleDateFormat("yyyy-MM-dd").parse(newProfileBirthdayField.getText()).before(new Date());
                    } catch (ParseException parseException) {
                        System.out.println("Invalid date");
                    }

                    if ((newProfileNameField.getText().matches("[A-Z]{1}[a-z]{1,29}")) && newProfileBirthdayField.getText().matches("([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))") && dateIsRight) {
                        di.addProfile(newProfileNameField.getText(), newProfileBirthdayField.getText(), session.getAbonneeID());

                        JOptionPane.showMessageDialog(frame, "Gegevens zijn succesvol aangepast.", "Succes", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, "De opgegeven naam of geboortedatum voldoet niet aan de vereisten. \n" +
                                "Voorbeeld: Bas, 1998-11-24", "Fout!", JOptionPane.ERROR_MESSAGE);
                    }
                    DatabaseConnection.disconnect();
                }
            });


        } else {
            gbc.gridx = 0;
            gbc.gridy = 6;
            JLabel errorMessage = new JLabel("<html>Je hebt al 5 profielen.<br/>Geen nieuwe mogelijk.</html>");
            basePanel.add(errorMessage, gbc);
        }

        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 1;
        gbc.gridy = 0;
        frame.add(basePanel);



        DatabaseConnection.disconnect();
    }

    public JFrame getFrame() {
        return frame;
    }



}
