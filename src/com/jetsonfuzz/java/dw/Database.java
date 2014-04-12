/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jetsonfuzz.java.dw;

import com.jetsonfuzz.java.main.Constants;
import com.jetsonfuzz.java.main.Properties;
import com.jetsonfuzz.java.main.Util;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dking
 */
public class Database {
    private Properties _prop = null;
    private Connection _conn = null;
    private boolean _isConnected = false;
    private static final Logger logger = 
            Logger.getLogger(Database.class.getName());
    
    public Database(Properties props) {
        this._prop = props;
    } // end constructor
    
    public boolean isConnected() {
        return this._isConnected;
    }
    
    public Connection getConnection() {
        return this._conn;
    }
    
    public boolean connect() {
        boolean bReturn = false;
        
        // Load the driver class into memory
        try {
            Class.forName(this._prop.getProperty(Constants.DatabaseDriver));
            // Attempt to make the connection
            try {
                String connString = "jdbc:oracle:thin:@" + 
                        this._prop.getProperty(Constants.DatabaseHost) + ":" +
                        this._prop.getProperty(Constants.DatabasePort) + ":" +
                        this._prop.getProperty(Constants.DatabaseService);

                // Establish and save the database connection
                this._conn = DriverManager.getConnection(connString, 
                                this._prop.getProperty(Constants.DatabaseUsername),
                                this._prop.getProperty(Constants.DatabasePassword));

                if (this._conn.isValid(10)) {
                    bReturn = true;
                    this._isConnected = bReturn;
                    Util.log("Successfully established connection!");
                } else {
                    Util.log("Failed to make connection!");
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Connection failed!");
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "JDBC driver not found.");
            e.printStackTrace();
        }

        return bReturn;
    }
    
    public void disconnect() {
        try {
            if (this._conn != null) {
                this._conn.close();
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public ResultSet executeQuery(String query) {
        ResultSet rs = null;
        
        try {
            // Make sure we are connected
            if (! this.isConnected()) {
                // Not connected, so connect!
                this.connect();
            }

            // Create and execute the 
            Statement stmt = this._conn.createStatement();
            
            // Execute the query
            rs = stmt.executeQuery(query);

            // Close the statement
            //stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Return the resultset back to the caller
        return rs;
    }
    
    public ArrayList<String> getTables() {
        ArrayList<String> tables = new ArrayList<>();
        String[] types = {"TABLE"};
        
        if (! this._isConnected) {
            this.connect();
        }
        
        try {
            DatabaseMetaData dmd = this._conn.getMetaData();
            ResultSet rs = dmd.getTables(null, 
                                         this._prop.getProperty(Constants.DatabaseUsername).toUpperCase(), 
                                         "%", 
                                         types);
            while (rs.next()) {
                tables.add(rs.getString("TABLE_NAME"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return tables;
    }
    
    public ArrayList<SqlTable> getSqlTables() {
        ArrayList<SqlTable> tables = new ArrayList<>();
        String[] types = {"TABLE"};
        
        if (! this._isConnected) {
            this.connect();
        }
        
        try {
            DatabaseMetaData dmd = this._conn.getMetaData();
            ResultSet rs = dmd.getTables(null, 
                                         this._prop.getProperty(Constants.DatabaseUsername).toUpperCase(), 
                                         "%", 
                                         types);
            while (rs.next()) {
                SqlTable table = new SqlTable();
                
                table.setOriginalName(rs.getString("TABLE_NAME"));
                table.setNewName(rs.getString("TABLE_NAME"));
                
                // This is an existing table, so it is not a custom table
                table.setCustomTable(false);

                // Retrieve the table's columns
                table.setColumns(this.getColumns(table.getOriginalName()));
                
                // Retrieve the primary keys
                ArrayList<SqlColumn> keys = this.getPrimaryKeys(table);
                table.setPrimaryKeys(keys);
                
                // Indicate in the main columns arraylist of the table object
                // whether the column in the table is a primary key
                for (SqlColumn col : table.getColumns()) {
                    for (SqlColumn key : keys) {
                        if (col.getOriginalName().compareTo(key.getOriginalName()) == 0) {
                            col.setPrimaryKey(true);
                        }                            
                    }
                }
                                
                // Retrieve the foreign keys
                table.setForeignKeys(this.getForeignKeys(table));
                
                // Save the table for use later
                tables.add(table);
            }
            
            // Don't need the resultset anymore
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return tables;        
    }
    
    public ArrayList<SqlColumn> getPrimaryKeys(SqlTable table) {
        ArrayList<SqlColumn> keys = new ArrayList<>();

        // Make sure we are connected
        if (! this._isConnected) {
            // Not connected, so connect!
            this.connect();
        }
        
        try {
            DatabaseMetaData dmd = this._conn.getMetaData();
            
            // Do we want foreign keys or primary keys
            // Retrieve primary keys only
            ResultSet rs = dmd.getPrimaryKeys(null, 
                    this._prop.getProperty(Constants.DatabaseUsername).toUpperCase(), 
                    table.getOriginalName().toUpperCase());
            
            while (rs.next()) {
                SqlColumn col = new SqlColumn();
                
                // Save the metadata to the object wrapper
                col.setOriginalName(rs.getString("COLUMN_NAME"));
                col.setNewName(rs.getString("COLUMN_NAME"));
                col.setOriginalTable(table);

                keys.add(col);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return keys;
    }

    public ArrayList<SqlColumn> getForeignKeys(SqlTable table) {
        ArrayList<SqlColumn> keys = new ArrayList<>();

        // Make sure we are connected
        if (! this._isConnected) {
            // Not connected, so connect!
            this.connect();
        }
        
        try {
            DatabaseMetaData dmd = this._conn.getMetaData();
            
            // Do we want foreign keys or primary keys
            // Retrieve foreign keys only
            ResultSet rs = dmd.getImportedKeys(null, 
                this._prop.getProperty(Constants.DatabaseUsername).toUpperCase(), 
                table.getOriginalName().toUpperCase());
            
            while (rs.next()) {
                SqlColumn col = new SqlColumn();
                
                // Save the metadata to the object wrapper
                col.setOriginalName(rs.getString("FKCOLUMN_NAME"));
                col.setNewName(rs.getString("FKCOLUMN_NAME"));

                keys.add(col);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return keys;
    }
    
    public ArrayList<SqlColumn> getColumns(String table) {
        ArrayList<SqlColumn> columns = new ArrayList<>();
        
        // Make sure we are connected
        if (! this._isConnected) {
            // Not connected, so connect!
            this.connect();
        }
        
        try {
            String sql = "SELECT * FROM " + table.toUpperCase() + " WHERE 1 = 0";
            ResultSet rset = this.executeQuery(sql);
            ResultSetMetaData md = rset.getMetaData();
            for (int i = 1; i <= md.getColumnCount(); i++) {
                SqlColumn col = new SqlColumn();
                
                // Save the metadata to the object wrapper
                col.setOriginalName(md.getColumnLabel(i));
                col.setNewName(md.getColumnLabel(i));
                col.setDataType(md.getColumnType(i));
                col.setColumnSize(md.getPrecision(i));
                col.setPrecision(md.getScale(i));
                
                if (md.isNullable(i) == DatabaseMetaData.attributeNullable) {
                    col.setAllowNull(true);
                } else {
                    col.setAllowNull(false);
                }
                
                columns.add(col);
            }
            
            rset.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return columns;
    }

    public static Map getJdbcTypeName() {
        Map map = new HashMap();

        // Get all field in java.sql.Types
        Field[] fields = java.sql.Types.class.getFields();
        for (Field field : fields) {
            try {
                String name = field.getName();
                Integer value = (Integer) field.get(null);
                map.put(value, name);
            }catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return map;
    }
}
