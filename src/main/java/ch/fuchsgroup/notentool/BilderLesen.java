/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.fuchsgroup.notentool;

import ch.fuchsgroup.dbMail.MailQuery;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author misch
 */
public class BilderLesen {

    public void leseBilder() {
       // MailQuery mq = new MailQuery();
       // mq.updateMailDBTest();
        File f = new File("C:\\Users\\lucaa\\OneDrive\\Desktop\\Mischa\\NotentoolDateien\\Bild");;
        String[] arrayFile = f.list();
        EntityManagerNotentool em = new EntityManagerNotentool();
        List<Teilnehmer> tList = em.teilnehmerOhneBild();
        if (tList.size() > 0) {
            for (String s : arrayFile) {
                String[] bildSplitted = s.split("\\.");
                String nameVoraname = bildSplitted[0];
                String[] nameVornameSplitted = nameVoraname.split("\\s+");
            
                for(Teilnehmer t : tList){
                    String nameDB = t.getName().toLowerCase();
                    String vornameDB = t.getVorname().toLowerCase();
                   
                    if(uberpruefeName(nameDB, vornameDB, nameVornameSplitted)){
                        t.setFoto(s);
                        em.teilnehmerSpeichern(t);
                      
                    }
                }
            }
        }
    }
    private boolean uberpruefeName(String name, String vorname, String[] s){
        for(String nameS : s){
            
            if(!name.contains(nameS.toLowerCase()) && !vorname.contains(nameS.toLowerCase())){
                return false;
            }
        }
        return true;
    }
}
