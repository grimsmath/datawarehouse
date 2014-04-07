/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jetsonfuzz.java.dw;

import com.jetsonfuzz.java.main.Properties;
import java.util.ArrayList;

/**
 *
 * @author dking
 */
public class SqlTable extends ArrayList<SqlColumn> {
    private String _originalName = "";
    private String _newName = "";
    private Properties _props = null;
    
    public SqlTable(Properties props) {
        super();
        
        this._props = props;
    }
    
    public String getOriginalName() {
        return this._originalName;
    }
    
    public void setOriginalName(String originalName) {
        this._originalName = originalName;
    }
    
    public String getNewName() {
        return this._newName;
    }
    
    public void setNewName(String newName) {
        this._newName = newName;
    }
    
    @Override
    public String toString() {
        String text = "";
        
        // Open the create table command
        text += "CREATE TABLE " + this._newName + " (";
        
        // This counter will be used later to determine
        // if we need to have a comma after the column
        int i = 0;
        
        // Iterate the columns
        for (SqlColumn col : this) {
            if (i <= this.size()) {
                text += col.toString() + ", ";
            } else {
                text += col.toString();
            }

            // Increment the counter
            i++;
        }
        
        // Close the create table command
        text += ")";
        
        return text;
    }
}
