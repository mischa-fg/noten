/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.fuchsgroup.rueckmeldung.Model;

import ch.fuchsgroup.rueckmeldung.Model.Rueckmeldung;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author misch
 */
@Entity
@Table(name = "rueckmeldung2frage")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rueckmeldung2frage.findAll", query = "SELECT r FROM Rueckmeldung2frage r")
    , @NamedQuery(name = "Rueckmeldung2frage.findById", query = "SELECT r FROM Rueckmeldung2frage r WHERE r.id = :id")
    , @NamedQuery(name = "Rueckmeldung2frage.findByAntwortZahl", query = "SELECT r FROM Rueckmeldung2frage r WHERE r.antwortZahl = :antwortZahl")})
public class Rueckmeldung2frage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Short id;
    @Lob
    @Size(max = 16777215)
    @Column(name = "AntwortText")
    private String antwortText;
    @Column(name = "AntwortZahl")
    private Integer antwortZahl;
    @JoinColumn(name = "Frage_FK", referencedColumnName = "ID")
    @ManyToOne
    private Frage frageFK;
    @JoinColumn(name = "Rueckmeldung_FK", referencedColumnName = "ID")
    @ManyToOne
    private Rueckmeldung rueckmeldungFK;

    public Rueckmeldung2frage() {
    }

    public Rueckmeldung2frage(Short id) {
        this.id = id;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getAntwortText() {
        return antwortText;
    }

    public void setAntwortText(String antwortText) {
        this.antwortText = antwortText;
    }

    public Integer getAntwortZahl() {
        return antwortZahl;
    }

    public void setAntwortZahl(Integer antwortZahl) {
        this.antwortZahl = antwortZahl;
    }

    public Frage getFrageFK() {
        return frageFK;
    }

    public void setFrageFK(Frage frageFK) {
        this.frageFK = frageFK;
    }

    public Rueckmeldung getRueckmeldungFK() {
        return rueckmeldungFK;
    }

    public void setRueckmeldungFK(Rueckmeldung rueckmeldungFK) {
        this.rueckmeldungFK = rueckmeldungFK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rueckmeldung2frage)) {
            return false;
        }
        Rueckmeldung2frage other = (Rueckmeldung2frage) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.fuchsgroup.rueckmeldung.Rueckmeldung2frage[ id=" + id + " ]";
    }
    
}
