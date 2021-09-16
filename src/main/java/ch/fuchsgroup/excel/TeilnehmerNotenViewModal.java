/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.fuchsgroup.excel;

import java.math.BigDecimal;

/**
 *
 * @author misch
 */
public class TeilnehmerNotenViewModal {
    private String name;
    private String vorname;
    private String titelPruefung;
    private BigDecimal note;
    private String fachname;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getTitelPruefung() {
        return titelPruefung;
    }

    public void setTitelPruefung(String titelPruefung) {
        this.titelPruefung = titelPruefung;
    }

    public BigDecimal getNote() {
        return note;
    }

    public void setNote(BigDecimal note) {
        this.note = note;
    }

    public String getFachname() {
        return fachname;
    }

    public void setFachname(String fachname) {
        this.fachname = fachname;
    }
    
}
