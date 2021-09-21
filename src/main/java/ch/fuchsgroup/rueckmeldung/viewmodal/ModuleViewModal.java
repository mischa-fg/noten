/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.fuchsgroup.rueckmeldung.viewmodal;

/**
 *
 * @author misch
 */
public class ModuleViewModal {
    private int id;
   
    private String modulnummer;
  
    private String beschreibung;
    
    private String bezeichnung;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModulnummer() {
        return modulnummer;
    }

    public void setModulnummer(String modulnummer) {
        this.modulnummer = modulnummer;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }
    
    
}
