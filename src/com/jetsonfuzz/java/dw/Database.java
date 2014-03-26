/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jetsonfuzz.java.dw;

import com.jetsonfuzz.java.main.Util;
import com.jetsonfuzz.java.main.Properties;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author dking
 */
public class Database {
    private Properties _prop = null;
    private Connection _conn = null;
    
    public Database(Properties props) {
        this._prop = props;
    } // end constructor
    
    public Connection getConnection() {
        return this._conn;
    } // end getConnection()
    
    public boolean connect() {
        boolean bReturn = false;
        
        // Load the driver class into memory
        try {
            Class.forName(this._prop.getProperty("driver"));
        } catch (ClassNotFoundException e) {
            Util.log("JDBC driver not found!");
            e.printStackTrace();
        } // end try_catch

        // Attempt to make the connection
        try {
            String connString = "jdbc:oracle:thin:@" + 
                    this._prop.getProperty("server") + ":" +
                    this._prop.getProperty("port") + ":" +
                    this._prop.getProperty("serviceName");

            // Establish and save the database connection
            this._conn = DriverManager.getConnection(connString, 
                            this._prop.getProperty("username"),
                            this._prop.getProperty("password"));

        } catch (SQLException e) {
            Util.log("Connection Failed! Check output console");
            e.printStackTrace();
        } // end try_catch

        if (this._conn != null) {
            bReturn = true;
            Util.log("Successfully established connection!");
        } else {
            Util.log("Failed to make connection!");
        } // end if
        
        return bReturn;
    } // end connect()
    
    public ResultSet executeQuery(String query) throws SQLException {
        ResultSet rs = null;
        
        // Make sure we are connected
        if (this._conn == null) {
            // Not connected, so connect!
            this.connect();
        } // end if
        
        // Create and execute the 
        Statement stmt = this._conn.createStatement();
        rs = stmt.executeQuery(query);
        
        stmt.close();
        
        // Return the resultset back to the caller
        return rs;
    } // end executeQuery()
    
    public ArrayList<String> getTables() {
        ArrayList<String> tables = new ArrayList<String>();
        String[] types = {"TABLE"};
        
        try {
            DatabaseMetaData dmd = this._conn.getMetaData();
            ResultSet rs = dmd.getTables(null, 
                                         this._prop.getProperty("username").toUpperCase(), 
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
