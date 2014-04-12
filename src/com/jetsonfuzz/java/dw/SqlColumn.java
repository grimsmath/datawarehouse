/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jetsonfuzz.java.dw;

import com.jetsonfuzz.java.main.Util;
import java.util.Map;

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

    // We need to know the original table this column originated from
    // in order to copy the data from the original table into this one
    private SqlTable _originalTable = null;
    
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
    
    public SqlTable getOriginalTable() {
        return this._originalTable;
    }
    
    public void setOriginalTable(SqlTable originalTable) {
        this._originalTable = originalTable;
    }
    
    @Override
    public String toString() {
        String text = this._newName;
        Map types = Database.getJdbcTypeName();
        
        text += " " + types.get(this._dataType);
        
        if (isNumericData(this._dataType)) {
            text += "(" + this._columnSize + ", " + this._precision + ")";
        } else if (isNotDateTime(this._dataType)) {
            text += "(" + this._columnSize + ")";
        } else {
            // Do nothing else
        }
        
        if (! this._allowNull) {
            text += " NOT NULL";
        }
        
//        if (this._isPrimaryKey) {
//            text += " PRIMARY KEY";
//        }
        
        return text;
    }
    
    public boolean isNumericData(int dataType) {
        boolean bReturn = false;
        
        if ((dataType == java.sql.Types.DECIMAL) ||
            (dataType == java.sql.Types.DOUBLE) ||
            (dataType == java.sql.Types.FLOAT) ||
            (dataType == java.sql.Types.NUMERIC))
        {
            bReturn = true;
        }
        
        return bReturn;
    }
    
    public boolean isNotDateTime(int dataType) {
        boolean bReturn = false;
        
        if ((dataType == java.sql.Types.DATE) ||
            (dataType == java.sql.Types.TIME) ||
            (dataType == java.sql.Types.TIMESTAMP))
        {
            Util.log("Found a date/time datatype");
        } else {
            bReturn = true;            
        }
        
        return bReturn;        
    }
}
