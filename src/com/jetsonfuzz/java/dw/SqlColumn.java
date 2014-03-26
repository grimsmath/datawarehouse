/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jetsonfuzz.java.dw;

import com.jetsonfuzz.java.main.Util;

/**
 *
 * @author dking
 */
public class SqlColumn {
    private String _originalName = "";
    private String _newName = "";
    private int _dataType = 0;
    private int _columnSize = 0;
    private int _precision = 0;
    private boolean _allowNull = false;
    private boolean _isPrimaryKey = false;
    private boolean _isForeignKey = false;
    private boolean _isUnique = false;
    
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
    
    public int getDataType() {
        return this._dataType;
    }
    
    public void setDataType(int type) {
        this._dataType = type;
    }
    
    public int getColumnSize() {
        return this._columnSize;
    }
    
    public void setColumnSize(int size) {
        this._columnSize = size;
    }
    
    public int getPrecision() {
        return this._precision;
    }
    
    public void setPrecision(int precision) {
        this._precision = precision;
    }
    
    public boolean getAllowNull() {
        return this._allowNull;
    }
    
    public void setAllowNull(boolean allowNull) {
        this._allowNull = allowNull;
    }
    
    public boolean getPrimaryKey() {
        return this._isPrimaryKey;
    }
    
    public void setPrimaryKey(boolean isPrimaryKey) {
        this._isPrimaryKey = isPrimaryKey;
    }
    
    public boolean getForeignKey() {
        return this._isForeignKey;
    }
    
    public void setForeignKey(boolean isForeignKey) {
        this._isForeignKey = isForeignKey;
    }
    
    public boolean getUnique() {
        return this._isUnique;
    }
    
    public void setUnique(boolean isUnique) {
        this._isUnique = isUnique;
    }
    
    @Override
    public String toString() {
        String text = this._newName + " ";
        
        // Get the data type name
        text += Database.getJdbcTypeName(this._dataType) + " ";
        
        // Add size for integer-like data types
        if ((this._dataType == java.sql.Types.CHAR) || 
            (this._dataType == java.sql.Types.VARCHAR) ||
            (this._dataType == java.sql.Types.NCHAR) ||
            (this._dataType == java.sql.Types.NVARCHAR)) 
        {
            text += "(" + this._columnSize + ") ";
        }
        
        // Add size and precision for non-integer numeric types
        if ((this._dataType == java.sql.Types.DECIMAL) ||
            (this._dataType == java.sql.Types.DOUBLE) ||
            (this._dataType == java.sql.Types.FLOAT) ||
            (this._dataType == java.sql.Types.NUMERIC))
        {
            text += "(" + this._columnSize + ", " + this._precision + ") ";
        }
        
        // Nulls
        if (! this._allowNull) {
            text += "NOT NULL ";
        }
        
        // Is the field unique
        if (this._isUnique) {
            text += "UNIQUE ";
        }
        
        // Primary Key
        if (this._isPrimaryKey) {
            text += "PRIMARY KEY ";
        }
        
        return text;
    }
}
