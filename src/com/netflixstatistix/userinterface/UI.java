package com.netflixstatistix.userinterface;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

import com.netflixstatistix.connections.DatabaseInterface;
import com.netflixstatistix.session.CurrentSession;
import com.netflixstatistix.session.Session;

public class UI extends CurrentSession implements Runnable {

    // Initializing different classes
    public DatabaseInterface di = new DatabaseInterface();
    public UIHandler uih = new UIHandler();

    // Variables for easy configuration
    public Border grey = BorderFactory.createLineBorder(Color.lightGray);

    // Creating JFrame which acts as main container.
    public JFrame frame;
    public Container pane;
    public JPanel westContainer;

    public JPanel createLeftProfileMenu;


    @Override
    public void run() {
        this.frame = new JFrame("Netflix Statistix");
        this.frame.setPreferredSize(new Dimension(1220, 800));
        this.frame.setMinimumSize(new Dimension(1210,700));

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        createComponents(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);
    }

    private void createComponents(Container container) {
        pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());

        frame.setJMenuBar(uih.mainMenu());



        pane.add(uih.createDetails(1001, "Test"), BorderLayout.CENTER);


        westContainer = new JPanel(new BorderLayout());
        westContainer.setBorder(grey);

        // Adding WestContainer

        createLeftProfileMenu = uih.createLeftProfileMenu(session.getAbonneeID(), "Admin", "basvanrooten@me.com");
        westContainer.add(createLeftProfileMenu, BorderLayout.NORTH);
        westContainer.add(uih.createUserMenuButtons(), BorderLayout.SOUTH);
        pane.add(westContainer, BorderLayout.WEST);

        // Credits Footer
        pane.add(uih.createFooter(), BorderLayout.SOUTH);


    }
    public JFrame getFrame() {
        return frame;
    }
}