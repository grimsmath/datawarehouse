/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jetsonfuzz.java.main;

import com.jetsonfuzz.java.gui.Launcher;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author dking
 */
public class MainClass {
    public static void main(String[] args) {
        Properties props = new Properties();
        if (props.loadProperties()) {
            final Launcher launchPanel = new Launcher(props);
            
            launchPanel.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    launchPanel.cleanup();
                    System.exit(0);
                }
                
                public void windowClosed(WindowEvent e) {
                    launchPanel.cleanup();
                    System.exit(0);
                }
            });

            launchPanel.setVisible(true);
        } else {
            Util.log("Failed to load properties file, exiting...");
        }
    }
}
