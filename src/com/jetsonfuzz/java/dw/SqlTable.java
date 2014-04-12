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
public class SqlTable {
    private String _originalName = "";
    private String _newName = "";
    private ArrayList<SqlColumn> _columns = null;
    private ArrayList<SqlColumn> _primaryKeys = null;
    private ArrayList<SqlColumn> _foreignKeys = null;
    private boolean _isCustomTable = false;
    private boolean _isDimensionTable = false;
        
    public SqlTable() {
        super();
    }

    public SqlTable(String tableName) {
        this._originalName = tableName;
        this._newName = tableName;
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
    
    public boolean isCustomTable() {
        return this._isCustomTable;
    }
    
    public void setCustomTable(boolean isCustom) {
        this._isCustomTable = isCustom;
    }
    
    public ArrayList<SqlColumn> getColumns() {
        return this._columns;
    }
    
    public void setColumns(ArrayList<SqlColumn> columns) {
        this._columns = columns;
    }
    
    public ArrayList<SqlColumn> getPrimaryKeys() {
        return this._primaryKeys;
    }
    
    public void setPrimaryKeys(ArrayList<SqlColumn> keys) {
        this._primaryKeys = keys;
    }
    
    public ArrayList<SqlColumn> getForeignKeys() {
        return this._foreignKeys;
    }
    
    public void setForeignKeys(ArrayList<SqlColumn> keys) {
        this._foreignKeys = keys;
    }
    
    public boolean isDimensionalTable() {
        return this._isDimensionTable;
    }
    
    public void setDimensionalTable(boolean isDimensionalTable) {
        this._isDimensionTable = isDimensionalTable;
    }
    
    public String getCreateCommand() {
        String text = "";
        String tableName = (this._newName.isEmpty()) 
                ? this._originalName 
                : this._newName;
        
        ArrayList<String> keys = new ArrayList<>();
        
        // Open the create table command
        text += "CREATE TABLE " + tableName + " (\n";
        
        // This counter will be used later to determine
        // if we need to have a comma after the column
        int i = 0;
        
        // Iterate the columns
        for (SqlColumn col : this._columns) {
            text += "\t";

            if ((i + 1) < this._columns.size()) {
                text += col.toString() + ",\n";
            } else {
                text += col.toString();
            }
            
            if (col.getPrimaryKey()) {
                keys.add(col.getNewName());
            }
            
            // Increment the counter
            i++;
        }
        
        // Primary Keys
        i = 0;
        text += ",\n\tCONSTRAINT PK_" + tableName + " PRIMARY KEY(";
        for (String key : keys) {
            if ((i + 1) < keys.size()) {
                text += key + ", "; 
            } else {
                text += key;
            }
            i++;
        }
        text += ")";
        
        // Close the create table command
        text += "\n);";
        
        return text;        
    }
    
    @Override
    public String toString() {
        return this._newName;
    }
}
