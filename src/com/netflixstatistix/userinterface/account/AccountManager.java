package com.netflixstatistix.userinterface.account;

import com.netflixstatistix.connections.DatabaseConnection;
import com.netflixstatistix.connections.DatabaseInterface;
import com.netflixstatistix.session.CurrentSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccountManager extends CurrentSession implements Runnable{

    private JFrame frame;
    private DatabaseInterface di = new DatabaseInterface();

    public AccountManager() {
    }

    @Override
    public void run() {
        frame = new JFrame("Wijzig Gebruikersgegevens");
        frame.setPreferredSize(new Dimension(410, 300));
        frame.setResizable(true);

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

        JLabel voornaamLabel = new JLabel("Voornaam");
        JTextField voornaamField = new JTextField(di.getNameFromAccount(session.getAbonneeID()));
        voornaamField.setPreferredSize(new Dimension(140, 20));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        basePanel.add(voornaamLabel, gbc);
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridy = 0;
        gbc.gridx = 1;
        basePanel.add(voornaamField, gbc);

        JLabel streetLabel = new JLabel("Straat en Huisnummer");
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy = 1;
        gbc.gridx = 0;
        basePanel.add(streetLabel, gbc);

        JTextField streetField = new JTextField(di.getStreetFromAccount(session.getAbonneeID()));
        streetField.setPreferredSize(new Dimension(140, 20));
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridy = 1;
        gbc.gridx = 1;
        basePanel.add(streetField, gbc);

        JTextField houseNumberField = new JTextField(di.getHouseNumberFromAccount(session.getAbonneeID()));
        houseNumberField.setPreferredSize(new Dimension(50, 20));
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 2;
        gbc.gridy = 1;
        basePanel.add(houseNumberField, gbc);


        JLabel postalAndCityLabel = new JLabel("Woonplaats en Postcode");
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 2;
        basePanel.add(postalAndCityLabel, gbc);

        JTextField cityField = new JTextField(di.getCityFromAccount(session.getAbonneeID()));
        cityField.setPreferredSize(new Dimension(140, 20));
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 1;
        gbc.gridy = 2;
        basePanel.add(cityField, gbc);

        JTextField zipField = new JTextField(di.getZipCodeFromAccount(session.getAbonneeID()));
        zipField.setPreferredSize(new Dimension(50, 20));
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 2;
        gbc.gridy = 2;
        basePanel.add(zipField, gbc);

        JLabel emailLabel = new JLabel("Emailadres");
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 3;
        basePanel.add(emailLabel, gbc);

        JTextField emailField = new JTextField(di.getEmailFromAccount(session.getAbonneeID()));
        emailField.setPreferredSize(new Dimension(140, 20));
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 1;
        gbc.gridy = 3;
        basePanel.add(emailField, gbc);

        JLabel passwordLabel = new JLabel("Wachtwoord");
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 4;
        basePanel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(di.getPasswordFromAccount(session.getAbonneeID()));
        passwordField.setPreferredSize(new Dimension(140, 20));
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 1;
        gbc.gridy = 4;
        basePanel.add(passwordField, gbc);


        gbc.gridx = 0;
        gbc.gridy = 10;
        JButton button = new JButton("Wijzigingen Opslaan");
        basePanel.add(button, gbc);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseConnection.connect();
                di.changeAccountName(session.getAbonneeID(), voornaamField.getText());
                di.changeAccountStreet(session.getAbonneeID(), streetField.getText());
                di.changeAccountHouseNumber(session.getAbonneeID(), houseNumberField.getText());
                di.changeCity(session.getAbonneeID(), cityField.getText());
                di.changeAccountZIP(session.getAbonneeID(), zipField.getText());
                di.changeAccountEmail(session.getAbonneeID(), emailField.getText());
                String password = String.valueOf(passwordField.getPassword());
                di.changeAccountPassword(session.getAbonneeID(), password);

                JOptionPane.showMessageDialog(frame, "Gegevens zijn succesvol aangepast.", "Succes", JOptionPane.INFORMATION_MESSAGE);

                DatabaseConnection.disconnect();


            }
        });


        frame.add(basePanel);



    DatabaseConnection.disconnect();
    }

    public JFrame getFrame() {
        return frame;
    }



}
