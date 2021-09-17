/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.fuchsgroup.rueckmeldung;

import ch.fuchsgroup.notentool.Klasse;
import ch.fuchsgroup.notentool.Kursleiter;
import ch.fuchsgroup.notentool.Module;
import ch.fuchsgroup.notentool.Teilnehmer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author misch
 */
public class RueckmeldungBearbeiten {

    public String rueckmeldungLesen(String file) {
        String fehler = "";
        String[] str = file.split("\r\n");
        System.out.println(str.length);
        String[] newstr = str[0].split("\",\"");
        List<Integer> platzFrage = new ArrayList();
        List<Frage> fragen = new ArrayList();
        int lernendePlatz = 0;
        int modulPlatz = 0;
        int dozentPlatz = 0;
        int klassePlatz = 0;
        int abgeschlossen = 0;
        EntityManagerRueckmeldung emr = new EntityManagerRueckmeldung();
        for (int i = 0; i < newstr.length; i++) {
            if (newstr[i].equals("Vorname Name ___ ")) {
                lernendePlatz = i;
            } else if (newstr[i].equals("Modul / Ausbildung ___ ")) {
                modulPlatz = i;
            } else if (newstr[i].equals("Dozent ___ ")) {
                dozentPlatz = i;
            } else if (newstr[i].equals("Kanal")) {
                klassePlatz = i;
            } else if (newstr[i].equals("Abgeschlossen")) {
                abgeschlossen = i;
            } else if (newstr[i].length() > 8) {
                Frage f = emr.getFrage(newstr[i]);
                if (f != null) {
                    platzFrage.add(i);
                    fragen.add(f);
                }
            }
            //wo die Frage ist herausfinden ==> nummer in einer Liste speichern und die Frage auch
        }
        for (int i = 1; i < str.length; i++) {
            Rueckmeldung r = new Rueckmeldung();
            boolean korrekt = true;
            String[] zeile = str[i].split("\",\"");
            //Lehrername überprüfen --> Pflichfeld
            Kursleiter kl = emr.getLeiter(zeile[dozentPlatz]);
            if (kl != null) {
                r.setKursleiterFK(kl);
            } else {
                korrekt = false;
                fehler += "Auf zeile " + (i + 1) + " wurde der Dozent nicht gefunden<br>";
            }
            //Modul überprüfen --> Pflichfeld
            Module m = emr.getModul(zeile[modulPlatz]);
            if (m != null) {
                r.setModuleFK(m);
            } else {
                korrekt = false;
                fehler += "Auf zeile " + (i + 1) + " wurde das Modul nicht gefunden <br>";
            }
            //Klasse überprüfen --> Pflichtfeld
            Klasse k = emr.getKlasse(zeile[klassePlatz]);
            if (k != null) {
                r.setKlasseFK(k);
            } else {
                korrekt = false;
                fehler += "Auf zeile " + (i + 1) + " wurde die Klasse nicht gefunden <br>";
            }
            //Schülername überprüfuen
            Teilnehmer t = emr.getTeilnehmer(zeile[lernendePlatz]);
            if (t != null) {
                r.setTeilnehmerFK(t);
            }
            //Datum später

            String datum = zeile[abgeschlossen];
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //https://stackoverflow.com/questions/48594916/convert-2018-02-02t065457-744z-string-to-date-in-android
            Date d = null;
            try {
                d = formatter.parse(datum);

                r.setDatumAbgeschlossen(d);
            } catch (ParseException ex) {
                korrekt = false;
                fehler += "Zeit konnte nicht formatiert werden";
                Logger.getLogger(RueckmeldungBearbeiten.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (korrekt) {
                if (emr.rueckmeldungUeberpruefen(r)) {
                    //Rueckmeldungspeichern und Antworten zu den Fragen zuweisen
                    Rueckmeldung rDB = emr.rueckmeldungSpeichern(r);
                    //System.out.println(rDB.getId());
                    antwortZuFrage(rDB, platzFrage, fragen, zeile);
                    //System.out.println(rDB.getKursleiterFK());
                    
                }
            }
        }
        SingletonEntityManager.getInstance().close();
        System.out.println("Fragen länge: " + fragen.size());
        System.out.println(platzFrage.size());
        return fehler;
    }

    public void antwortZuFrage(Rueckmeldung r, List<Integer> platzFragen, List<Frage> fragen, String[] zeile) {
        for (int i = 0; i < fragen.size(); i++) {
            int startFrage = platzFragen.get(i);
            Frage f = fragen.get(i);
            Rueckmeldung2frage rf = new Rueckmeldung2frage();
            rf.setFrageFK(f);
            rf.setRueckmeldungFK(r);
            if (f.getMultiple()) {
                int antwort = 10;
                if(zeile[(startFrage+1)].equals("1")){
                    //Speichern der Antwort zur frage
                    //System.out.println(0);
                    rf.setAntwortZahl(antwort);
                }
                for (int j = (startFrage + 2); j < (startFrage + 12); j++) {
                    //System.out.println(zeile[j]);
                    if(zeile[j].equals("1")){
                        //Speichern der Antwort zur Frage
                        //System.out.println(antwort);
                        rf.setAntwortZahl(antwort);
                        break;
                    }else{
                        antwort--;
                    }
                }
            }else{
                String antwort = zeile[startFrage].replace("\"", "");
               // System.out.println(antwort);
                rf.setAntwortText(antwort);
            }
            if(rf.getAntwortText() != null || rf.getAntwortZahl() != null){
                //rf speichern
                EntityManagerRueckmeldung emr = new EntityManagerRueckmeldung();
                emr.rueckmeldung2frageSpeichern(rf);
            }
        }
    }
}
