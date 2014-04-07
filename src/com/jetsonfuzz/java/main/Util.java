/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jetsonfuzz.java.main;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author dking
 */
public class Util {
    public static void log(String message) {
        System.out.println(message);
    }
    
    public static String readFile(String filename) {
        String content = null;
        File file = new File(filename); //for ex foo.txt
        try {
            FileReader reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            content = new String(chars);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
    
    public static void showInfoBox(String title, String message) {
        JOptionPane.showMessageDialog(null,
            message,
            title,
            JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showErrorBox(String title, String message) {
        JOptionPane.showMessageDialog(null,
            message,
            title,
            JOptionPane.ERROR_MESSAGE);
    }
}
