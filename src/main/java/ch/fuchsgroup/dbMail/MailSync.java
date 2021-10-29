/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.fuchsgroup.dbMail;

import ch.fuchsgroup.notentool.Kurse;
import java.util.List;

/**
 *
 * @author mischa
 */
public class MailSync {

    public void syncMail() {
        //Alle Kurse bekommen mit 
        EntityManagerMail em = new EntityManagerMail();
        MailQuery mq = new MailQuery();
        List<Kurse> kl = em.getKurse();
        for (Kurse k : kl) {
            if (!k.getKlasseFK().getKlassenname().contains("P") && !k.getKlasseFK().getKlassenname().contains("R")) {
                //Liste mit allen teilnehmer zu dem Kurs
                List<Klasse2teilnehmer> kTeilnehmer = em.getTeilnehmer2klasse(k.getKlasseFK());
                for (Klasse2teilnehmer kt : kTeilnehmer) {
                    if (!mq.isKurs2Teilnehmer(k, kt)) {
                        //Teilnehmer in db einfügen
                        System.out.println("Eintrag noch nicht in db");
                        mq.insertTeilnehmerMail(k, kt);
                    } else {
                        //Teilnehmer in DB updaten
                        System.out.println("Eintrag in db");
                        mq.updateTeilnehmerMail(k, kt);
                    }
                }
            }

        }
    }
}
