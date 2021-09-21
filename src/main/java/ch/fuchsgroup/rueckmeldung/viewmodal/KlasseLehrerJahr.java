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
public class KlasseLehrerJahr {
    private int monat;
    private BigDecimal durchschnitt;

    public int getMonat() {
        return monat;
    }

    public void setMonat(int monat) {
        this.monat = monat;
    }

    public BigDecimal getDurchschnitt() {
        return durchschnitt;
    }

    public void setDurchschnitt(BigDecimal durchschnitt) {
        this.durchschnitt = durchschnitt;
    }

    
}
