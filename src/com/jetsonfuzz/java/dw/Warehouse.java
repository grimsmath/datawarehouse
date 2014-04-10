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
    private ArrayList<SqlTable> _originalTables = null;
    private ArrayList<SqlTable> _newTables = null;
    private ArrayList<SqlTable> _factTables = null;
    
    public Warehouse(Properties props, Database db) {
        this._props = props;
        this._db = db;
        this._originalTables = new ArrayList<>();
        this._newTables = new ArrayList<>();
        this._factTables = new ArrayList<>();
        
        // Load the schema into memory
        loadSchema();
    }
    
    public ArrayList<SqlTable> getOriginalTables() {
        return this._originalTables;
    }
    
    public void setOriginalTables(ArrayList<SqlTable> originalTables) {
        this._originalTables = originalTables;
    }
    
    public ArrayList<SqlTable> getNewTables() {
        return this._newTables;
    }
    
    public void setNewTables(ArrayList<SqlTable> newTables) {
        this._newTables = newTables;
    }
    
    public ArrayList<SqlTable> getFactTables() {
        return this._factTables;
    }
    
    public void setFactTables(ArrayList<SqlTable> factTables) {
        this._factTables = factTables;
    }
    
    public void loadSchema() {
        // Retrieve the existing tables in the schema
        // We will use this to complete all the form loading later
        // in the application
        this._originalTables = this._db.getSqlTables();
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
