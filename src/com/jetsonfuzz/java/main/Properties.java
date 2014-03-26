/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jetsonfuzz.java.main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author dking
 */
public class Properties extends java.util.Properties {
    
    public Properties() {
        super();
    }
    
    public boolean loadProperties() {
        boolean bReturn = false;
        
        try {
            // Define the input
            InputStream in = new FileInputStream("application.properties");
            if (in != null) {
                // Load the file
                this.load(in);

                // Success
                bReturn = true;                
            }
        } catch (IOException e) {
            Util.log("Could not load application.properties!");
            e.printStackTrace();
        } // end try_catch
        
        return bReturn;
    }
    
    public boolean saveProperties() {
        boolean bReturn = false;
        
        try {
            // Define the output
            OutputStream out = new FileOutputStream("application.properties");
            
            // save properties to project root folder
            this.store(out, null);
            
            // Success
            bReturn = true;
        } catch (IOException e) {
            Util.log("Could not save application.properties!");
            e.printStackTrace();
        } // end try_catch
        
        return bReturn;
    } // end saveProperties()
}
