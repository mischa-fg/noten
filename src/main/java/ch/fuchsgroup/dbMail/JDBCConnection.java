/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.fuchsgroup.dbMail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mischa
 */
public class JDBCConnection {
    private static JDBCConnection instance= null;
    private final String USERNAME= "sa";
    private final String PASSWORD= "Password123!";
    private final String DB_CONNECTION_STRING="jdbc:sqlserver://localhost\\sqlexpress;databaseName=notentoolMail";
    private Connection cn= null;
    private JDBCConnection(){
       
    }
   
    public static JDBCConnection getInstance(){
        if(instance==null){
            instance= new JDBCConnection();
        }
        return instance;
    }
    public Connection createConnection() {
        if(cn==null){           
            try {       
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                cn= DriverManager.getConnection(DB_CONNECTION_STRING,USERNAME,PASSWORD);
                //System.out.println("OK");
            } catch (SQLException ex) {
                Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return cn;
    }
    public void closeConnection() throws SQLException{
        cn.close();
        cn= null;
    }
}
