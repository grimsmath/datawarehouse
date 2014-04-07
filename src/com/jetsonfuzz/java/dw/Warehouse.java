/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jetsonfuzz.java.dw;

import com.jetsonfuzz.java.main.Properties;
import java.util.ArrayList;

/**
 *
 * @author dking
 */
public class Warehouse {
    private Properties _props = null;
    private Database _db = null;
    
    public Warehouse(Properties props, Database db) {
        this._props = props;
        this._db = db;
    }
    
    public boolean execute(ArrayList<SqlTable> tables) {
        boolean bReturn = false;
        
        // 1.  Connect to the database (if not already connected)
        
        // 2.  Execute the create table commands for the dimension tables
        
        // 3.  Execute the create table command for the fact table
        
        // 4.  Copy the data from the source tables to the dimension tables
        
        return bReturn;
    }
}
