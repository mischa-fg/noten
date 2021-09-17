/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.fuchsgroup.rueckmeldung.viewmodal;

import java.math.BigDecimal;

/**
 *
 * @author misch
 */
public class LehrerModul {
    private String frage;
    private BigDecimal durchschnitt;

    public String getFrage() {
        return frage;
    }

    public void setFrage(String frage) {
        this.frage = frage;
    }

    public BigDecimal getDurchschnitt() {
        return durchschnitt;
    }

    public void setDurchschnitt(BigDecimal durchschnitt) {
        this.durchschnitt = durchschnitt;
    }
    
}
