/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.fuchsgroup.dbMail;

import ch.fuchsgroup.notentool.Klasse;
import ch.fuchsgroup.notentool.Teilnehmer;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mischa
 */
@Entity
@Table(name = "klasse2teilnehmer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Klasse2teilnehmer.findAll", query = "SELECT k FROM Klasse2teilnehmer k")
    , @NamedQuery(name = "Klasse2teilnehmer.findById", query = "SELECT k FROM Klasse2teilnehmer k WHERE k.id = :id")})
public class Klasse2teilnehmer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Short id;
    @JoinColumn(name = "Klasse_FK", referencedColumnName = "ID")
    @ManyToOne
    private Klasse klasseFK;
    @JoinColumn(name = "Teilnehmer_FK", referencedColumnName = "ID")
    @ManyToOne
    private Teilnehmer teilnehmerFK;

    public Klasse2teilnehmer() {
    }

    public Klasse2teilnehmer(Short id) {
        this.id = id;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public Klasse getKlasseFK() {
        return klasseFK;
    }

    public void setKlasseFK(Klasse klasseFK) {
        this.klasseFK = klasseFK;
    }

    public Teilnehmer getTeilnehmerFK() {
        return teilnehmerFK;
    }

    public void setTeilnehmerFK(Teilnehmer teilnehmerFK) {
        this.teilnehmerFK = teilnehmerFK;
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
        if (!(object instanceof Klasse2teilnehmer)) {
            return false;
        }
        Klasse2teilnehmer other = (Klasse2teilnehmer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.fuchsgroup.dbMail.Klasse2teilnehmer[ id=" + id + " ]";
    }
    
}
