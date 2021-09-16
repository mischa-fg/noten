/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.fuchsgroup.rueckmeldung;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author misch
 */
public class Statistiken {

    public void getKlassenUebersicht(int klasseId, int jahr) {
        EntityManagerStatistiken ems = new EntityManagerStatistiken();
        String jahrStart = jahr + "-01-01 00:00:00";
        String jahrEnde = (jahr + 1) + "-01-01 00:00:00";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dStart = null;
        Date dEnde = null;
        try {
            dStart = formatter.parse(jahrStart);
            dEnde = formatter.parse(jahrEnde);

        } catch (ParseException ex) {
            Logger.getLogger(RueckmeldungBearbeiten.class.getName()).log(Level.SEVERE, null, ex);
        }
        ems.getKlassenUebersicht(dStart, dEnde, klasseId);
    }
}
