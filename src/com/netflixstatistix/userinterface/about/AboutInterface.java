package com.netflixstatistix.userinterface.about;
import javax.swing.*;
import java.awt.*;

public class AboutInterface extends JFrame{
    private AboutContentPanel contentPanel;

    public AboutInterface(String title){

        super(title);

        this.setSize(500,400);
        this.setVisible(true);


        setLayout(new BorderLayout());                       // Layout
        AboutContentPanel contentPanel = new AboutContentPanel();      // Initiated from Class in same folder

        Container content = getContentPane();                // Container to give content a place in the window
        content.add(contentPanel, BorderLayout.CENTER);

    }
}
