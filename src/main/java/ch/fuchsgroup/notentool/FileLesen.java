/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.fuchsgroup.notentool;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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
public class FileLesen {

    public String leseFile(String file) throws IOException {
        System.out.println(file);
        String strFormat = file.replace(",", ";");
        String[] str = strFormat.split("\r\n");
        System.out.println(str.length);
        String[] newstr = str[0].split(";");

        if (newstr.length == 6) {
            if (newstr[0].equals("Anrede") && newstr[1].equals("OE Bezeichnung") && newstr[2].equals("Name") && newstr[3].equals("Vorname") && newstr[4].equals("UBS E-Mail") && newstr[5].equals("Geburtsdatum")) {
                String ausgabe = leseTeilnehmerFile(str);
                return ausgabe;
            } else {
                return "Die Spalten müssen Anrede, OE Bezeichnung, Name, Vorname, UBS E-Mail, Geburtsdatum sein in dieser Reihenfolge";
            }
        } else if (newstr.length == 10) {
            if (newstr[0].equals("Datum") && newstr[2].equals("Klasse") && newstr[5].equals("Modul") && newstr[7].equals("Trainer")) {
                String ausgabe = leseKurseFile(str);
                return ausgabe;
            }
            return "Die Spalten müssen Datum,Raum,Klasse,Ausbildungsstart,Kurzzeichen,Modul,Kürzel,Trainer,Code,Bemerkung sein in dieser Reihenfolge";
        } else {
            return "Die Spalten müssen Datum,Raum,Klasse,Ausbildungsstart,Kurzzeichen,Modul,Kürzel,Trainer,Code,Bemerkung sein in dieser Reihenfolge. <br/>"
                    + " Oder die Spalten müssen Anrede, OE Bezeichnung, Name, Vorname, UBS E-Mail, Geburtsdatum sein in dieser Reihenfolge";
        }
    }

    public String leseTeilnehmerFile(String[] str) {
        String output = "";
        boolean alleDatensätze = true;
        for (int i = 1; i < str.length; i++) {
            String[] newstr = str[i].split(";");
            if (newstr.length == 6) {
                if (newstr[3].length() > 0 && newstr[2].length() > 0 && newstr[0].length() > 0) {
                    Teilnehmer t = new Teilnehmer();
                    t.setAnrede(newstr[0]);
                    t.setOEBezeichnung(newstr[1]);
                    t.setName(newstr[2]);
                    t.setVorname(newstr[3]);
                    t.setEmail(newstr[4]);
                    if (t.getAnrede().equals("Herr") || t.getAnrede().equals("Frau")) {
                        Date date = null;
                        String dateFormat = newstr[5];
                        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

                        try {

                            date = formatter.parse(dateFormat);
                            t.setGeburtsdatum(date);
                            EntityManagerNotentool n = new EntityManagerNotentool();
                            Teilnehmer tAbfrage = n.isTeilnehmer(t);
                            //Wenn Teilnehemer noch nicht in DB den neuen speichern
                            if (n.isTeilnehmer(t) == null) {
                                //Teilnehmer speichern
                                boolean korrekt = n.teilnehmerSpeichern(t);
                                if (!korrekt) {
                                    alleDatensätze = false;
                                    output += "Der Lernende auf Zeile " + (i + 1) + " konnte nicht gespeichert werden, schaue die Email an<br/>";
                                }
                            } else {
                                //Daten überschreiben von Teilnehmer, wenn Teilnehmer schon in DB ist
                               
                                tAbfrage.setEmail(t.getEmail());
                                tAbfrage.setGeburtsdatum(t.getGeburtsdatum());
                                tAbfrage.setOEBezeichnung(t.getOEBezeichnung());
                                tAbfrage.setAnrede(t.getAnrede());
                                boolean korrekt = n.teilnehmerSpeichern(tAbfrage);
                                if (!korrekt) {
                                    alleDatensätze = false;
                                    output += "Der Lernende auf Zeile " + (i + 1) + " konnte nicht gespeichert werden, schaue die Email an<br/>";
                                }
                            }
                        } catch (ParseException ex) {
                            alleDatensätze = false;
                            output += "Auf der Zeile " + (i + 1) + "stimmt das Datumsformat nicht(dd.MM.yyyy)";
                            //Logger.getLogger(FileLesen.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        alleDatensätze = false;
                        output += "Die Andrede auf Zeile " + (i + 1) + " ist falsch. Es muss Herr oder Frau sein.<br/>";
                    }

                } else {
                    output += "Bei der Zeile " + (i + 1) + " wurde nicht alles ausgefühlt <br/>";
                    alleDatensätze = false;
                }
            }
        }
        if (alleDatensätze) {
            output = "Teilnehmer wurden hinzugefügt/updated";
        }
        return output;
    }

    public String leseKurseFile(String[] str) {
        String ersterKurs = "";
        String letzterKurs = "";
        int idZeileZwei = 0;
        String output = "";
        List<Integer> zahlenGebraucht = new ArrayList();
        for (int i = 1; i < str.length; i++) {
            String format = str[i].replace("\"", " ");
            ersterKurs = format;
            letzterKurs = format;
            String[] kursEins = format.split(";");
            List<Integer> kurseZeile = new ArrayList();
            kurseZeile.add(i + 1);
            idZeileZwei = i;
            //Überprüfung ob Kurszeile schon gebraucht wurde
            if (!zahlenGebraucht.contains(i)) {
                zahlenGebraucht.add(i);
                for (int j = i + 1; j < str.length; j++) {
                    String format2 = str[j].replace("\"", " ");
                    String[] kursZwei = format2.split(";");
                    //Wenn Kurs von den Daten übereinstimmt als letzten Kurs speichern und abhaken
                    if (kursEins[2].equals(kursZwei[2]) && kursEins[3].equals(kursZwei[3]) && kursEins[5].equals(kursZwei[5])) {
                        zahlenGebraucht.add(j);
                        kurseZeile.add(j + 1);
                        letzterKurs = format2;
                        idZeileZwei = j;
                    }
                }
                output += speichereKurse(ersterKurs, letzterKurs, i + 1, idZeileZwei + 1, kurseZeile);
            }

        }
        if (output.equals("")) {
            output = "Kurse wurden hinzugefügt/updated";
        }
        return output;
    }

    public String speichereKurse(String ersterKurs, String letzterKurs, int idZeileEins, int idZeileZwei, List<Integer> kurseZeile) {
        String output = "";
        boolean ok = true;
        EntityManagerNotentool em = new EntityManagerNotentool();
        String[] splittedE = ersterKurs.split(";");
        String[] splittedL = letzterKurs.split(";");
        Module idM = em.getModul(splittedE[6]);
        Klasse idKlasse = em.getKlasse(splittedE[4]);
        Kursleiter idLeiter = em.getLeiter(splittedE[7]);
        Date anfang = null;
        Date ende = null;
        String dateA = splittedE[0];
        String dateL = splittedL[0];
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        try {
            anfang = formatter.parse(dateA);
            ende = formatter.parse(dateL);
        } catch (ParseException ex) {
            ok = false;
            output += "Datumformat(dd.MM.yyyy) bei Zeilen ";
            for (int i : kurseZeile) {
                output += ", " + i;
            }
            output += " sind nicht korrekt<br/>";
           
            Logger.getLogger(FileLesen.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (idKlasse == null) {
            ok = false;
            output += "Klasse wurde nicht gefunden auf Zeilen";
            for (int i : kurseZeile) {
                output += ", " + i;
            }
            output += "<br/>";
            
        }
        if (idM == null) {
            ok = false;
            output += "Modul wurde nicht gefunden auf Zeilen";
            for (int i : kurseZeile) {
                output += ", " + i;
            }
            output += "<br/>";
            
        }
        if (ok) {
            
            Kurse k = new Kurse();
            k.setDatumvon(anfang);
            k.setDatumbis(ende);
            k.setKlasseFK(idKlasse);
            k.setKursleiterFK(idLeiter);
            k.setModuleFK(idM);
            Kurse kAbfrage = em.isKurs(k);
            //Wenn Kurs noch nicht in der DB ist
            if (kAbfrage == null) {
                boolean korrekt = em.kursSpeichern(k);
                if (!korrekt) {
                    output += "Kurs der auf der Zeile starte " + idZeileEins + " konnte nicht gespeichert werden <br/>";
                }
            } else {
                //Daten überschreiben von Kurse wenn Kurs schon in der DB ist
                kAbfrage.setDatumvon(k.getDatumvon());
                kAbfrage.setDatumbis(k.getDatumbis());
                kAbfrage.setKursleiterFK(k.getKursleiterFK());
                boolean korrekt = em.kursSpeichern(kAbfrage);
                if (!korrekt) {
                    output += "Kurs der auf der Zeile starte " + idZeileEins + " konnte nicht gespeichert werden <br/>";
                }
            }
            
        }
        return output;

    }
}
