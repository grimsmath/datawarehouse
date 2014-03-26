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
}
