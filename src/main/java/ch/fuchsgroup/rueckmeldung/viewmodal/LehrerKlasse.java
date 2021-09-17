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
public class LehrerKlasse {
    private String klassenname;
    private BigDecimal durchschnitt;

    public String getKlassenname() {
        return klassenname;
    }

    public void setKlassenname(String klassenname) {
        this.klassenname = klassenname;
    }

    public BigDecimal getDurchschnitt() {
        return durchschnitt;
    }

    public void setDurchschnitt(BigDecimal durchschnitt) {
        this.durchschnitt = durchschnitt;
    }

    
    
}
