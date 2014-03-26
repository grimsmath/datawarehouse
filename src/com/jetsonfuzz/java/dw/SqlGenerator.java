/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jetsonfuzz.java.dw;

import java.util.ArrayList;

/**
 *
 * @author dking
 */
public class SqlGenerator {
    public String createCommand() {
        String text = "";
        
        return text;
    }

    public String alterCommand() {
        String text = "";
        
        return text;
    }
    
    public String dropCommand() {
        String text = "";
        
        return text;
    }   
    
    public String insertCommand(String table, 
                                ArrayList<SqlColumn> columns, 
                                ArrayList<String> values) {
        
        String text = "INSERT INTO " + table + " (";
        
        for (int i = 0; i <= columns.size(); i++) {
            String colname = columns.get(i).getNewName();
            
            if (i < columns.size()) {
                text += colname + ", ";
            } else {
                text += colname;
            }
        }
        
        text += ") VALUES (";
        
        
        return text;
    }
    
    public String deleteCommand(String table) {
        String text = "";
        
        return text;
    }
    
    public String updateCommand(String table) {
        String text = "";
        
        return text;
    }
}