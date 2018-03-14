package com.netflixstatistix.userinterface.about;
import javax.swing.*;
import java.awt.*;

public class AboutContentPanel extends JPanel{
    public AboutContentPanel(){

        // Specifications

        Dimension size = getPreferredSize();
        size.width = 250;
        setPreferredSize(size);
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        // UI components

        JLabel accountLabel = new JLabel("");
        JLabel image = new JLabel(new ImageIcon("src\\com.netflixstatistix\\userinterface\\about\\logo.jpg"));
        JTextArea aboutList = new JTextArea("Deze applicatie is gebouwd in opdracht van Avans Hogeschool.");

        gc.anchor = GridBagConstraints.CENTER;
        gc.weightx = 0.5;
        gc.weighty = 0.5;

        gc.gridx = 0;
        gc.gridy = 0;
        image.setSize(60,60);
        add(image,gc );


        gc.gridx = 0;
        gc.gridy = 1;
        add(aboutList,gc);

        aboutList.setVisible(true);
    }
}
