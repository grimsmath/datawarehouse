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

                if (this._conn != null) {
                    try {
                        bReturn = ! this._conn.isClosed();            
                    } catch (Exception e) {
                        // Do something with the exception
                        e.printStackTrace();
                    }

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
        } catch (Exception ex) {
            // do something with the exception
        }
    }
    
    public ResultSet executeQuery(String query) throws SQLException {
        ResultSet rs = null;
        
        // Make sure we are connected
        if (this._conn == null) {
            // Not connected, so connect!
            this.connect();
        }
        
        // Create and execute the 
        Statement stmt = this._conn.createStatement();
        rs = stmt.executeQuery(query);
        
        stmt.close();
        this._conn.close();
        
        // Return the resultset back to the caller
        return rs;
    }
    
    public ArrayList<String> getTables() {
        ArrayList<String> tables = new ArrayList<>();
        String[] types = {"TABLE"};
        
        try {
            DatabaseMetaData dmd = this._conn.getMetaData();
            ResultSet rs = dmd.getTables(null, 
                                         this._prop.getProperty(Constants.DatabaseUsername).toUpperCase(), 
                                         "%", 
                                         types);
            while (rs.next()) {
                tables.add(rs.getString("TABLE_NAME"));
            }
            
            rs.close();
        } catch (Exception e) {
            
        }
        
        return tables;
    }
    
    public static String getJdbcTypeName(int jdbcType) {
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

        return map.toString();
    }
}
