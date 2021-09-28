/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.fuchsgroup.dbMail;

import ch.fuchsgroup.notentool.Kurse;
import ch.fuchsgroup.notentool.Teilnehmer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mischa
 */
public class MailQuery {
    private JDBCConnection jdbc = JDBCConnection.getInstance();
   
    public void test(){
        jdbc.createConnection();
        try {
            jdbc.closeConnection();
            System.out.println("Closed");
        } catch (SQLException ex) {
            Logger.getLogger(MailQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    public void updateMailDBTest(){
        try {
            String query = "Insert into mailTeilnehmer (Name,Vorname,Email,Klasse,Modul,DatumEnde)values (?,?,?,?,?,?)";
            Connection conn = jdbc.createConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, "Fischer");
            ps.setString(2, "Mischa");
            ps.setString(3, "FgNotentool@outlook.com");
            ps.setString(4, "I18");
            ps.setString(5, "226");
            Date d = new Date();
            ps.setDate(6, new java.sql.Date(d.getTime()));
            ps.execute();
            conn.close();
            jdbc.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(MailQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean isKurs2Teilnehmer(Kurse k, Klasse2teilnehmer kt){
        try {
            String query = "Select * from mailTeilnehmer where name = ? and vorname = ? and klasse = ? and modul = ?";
            Connection conn = jdbc.createConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, kt.getTeilnehmerFK().getName());
            ps.setString(2, kt.getTeilnehmerFK().getVorname());
            ps.setString(3, k.getKlasseFK().getKlassenname());
            ps.setString(4, k.getModuleFK().getBezeichnung());
            //Statement stmt = conn.createStatement();
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
            conn.close();
            jdbc.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(MailQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public void insertTeilnehmerMail(Kurse k, Klasse2teilnehmer kt){
        try {
            String query = "Insert into mailTeilnehmer (Name,Vorname,Email,Klasse,Modul,DatumEnde,Anrede)values (?,?,?,?,?,?,?)";
            Connection conn = jdbc.createConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, kt.getTeilnehmerFK().getName());
            ps.setString(2, kt.getTeilnehmerFK().getVorname());
            ps.setString(3, kt.getTeilnehmerFK().getEmail());
            ps.setString(4, kt.getKlasseFK().getKlassenname());
            ps.setString(5, k.getModuleFK().getBezeichnung());
            Date d = new Date();
            ps.setDate(6, new java.sql.Date(k.getDatumbis().getTime()));
            ps.setString(7, kt.getTeilnehmerFK().getAnrede());
            ps.execute();
            conn.close();
            jdbc.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(MailQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateTeilnehmerMail(Kurse k, Klasse2teilnehmer kt){
         try {
            String query = "UPDATE mailTeilnehmer set Email = ?, DatumEnde = ?, Anrede = ? where name = ? and vorname = ? and klasse = ? and modul = ?";
            Connection conn = jdbc.createConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, kt.getTeilnehmerFK().getEmail());
            ps.setDate(2, new java.sql.Date(k.getDatumbis().getTime()));
            ps.setString(3, kt.getTeilnehmerFK().getAnrede());
            ps.setString(4, kt.getTeilnehmerFK().getName());
            ps.setString(5, kt.getTeilnehmerFK().getVorname());
            ps.setString(6, k.getKlasseFK().getKlassenname());
            ps.setString(7, k.getModuleFK().getBezeichnung());
            ps.execute();
            conn.close();
            jdbc.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(MailQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
