/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.fuchsgroup.rueckmeldung.viewmodal;

import java.util.List;

/**
 *
 * @author misch
 */
public class LearningViewStatistiken {
    private String frage;
    private List<Integer> wert;
    private List<Long> wertAnzahl;

    public String getFrage() {
        return frage;
    }

    public void setFrage(String frage) {
        this.frage = frage;
    }

    public List<Integer> getWert() {
        return wert;
    }

    public void setWert(List<Integer> wert) {
        this.wert = wert;
    }

    public List<Long> getWertAnzahl() {
        return wertAnzahl;
    }

    public void setWertAnzahl(List<Long> wertAnzahl) {
        this.wertAnzahl = wertAnzahl;
    }
    
    
}
