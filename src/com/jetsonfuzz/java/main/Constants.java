/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jetsonfuzz.java.main;

/**
 *
 * @author dking
 */
public interface Constants {
    // DATABASE PROPERTIES
    public static String DatabaseDriver = "db.driver";
    public static String DatabaseHost = "db.hostname";
    public static String DatabasePort = "db.port";
    public static String DatabaseService = "db.service";
    public static String DatabaseUsername = "db.username";
    public static String DatabasePassword = "db.password";
    
    // SQL PROPERTIES
    public static String NewTablePrefix = "DIM_";
    public static String FactTablePrefix = "FACT_";
}
