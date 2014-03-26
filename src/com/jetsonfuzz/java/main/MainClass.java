/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jetsonfuzz.java.main;

import com.jetsonfuzz.java.gui.SimpleFrame;

/**
 *
 * @author dking
 */
public class MainClass {
    public static void main(String[] args) {
        Properties props = new Properties();
        if (props.loadProperties()) {
            new SimpleFrame(props).setVisible(true);
        } else {
            Util.log("Failed to load properties file, exiting...");
        }
    }
}
