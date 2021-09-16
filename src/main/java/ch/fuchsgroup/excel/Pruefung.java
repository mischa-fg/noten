/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.fuchsgroup.excel;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author misch
 */
@Entity
@Table(name = "pruefung")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pruefung.findAll", query = "SELECT p FROM Pruefung p")
    , @NamedQuery(name = "Pruefung.findById", query = "SELECT p FROM Pruefung p WHERE p.id = :id")
    , @NamedQuery(name = "Pruefung.findByTitel", query = "SELECT p FROM Pruefung p WHERE p.titel = :titel")
    , @NamedQuery(name = "Pruefung.findByBms", query = "SELECT p FROM Pruefung p WHERE p.bms = :bms")})
public class Pruefung implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Short id;
    @Size(max = 100)
    @Column(name = "Titel")
    private String titel;
    @Column(name = "BMS")
    private Short bms;
    @OneToMany(mappedBy = "pruefungFK")
    private Collection<Note> noteCollection;
    @JoinColumn(name = "Schulfach_FK", referencedColumnName = "ID")
    @ManyToOne
    private Schulfach schulfachFK;

    public Pruefung() {
    }

    public Pruefung(Short id) {
        this.id = id;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public Short getBms() {
        return bms;
    }

    public void setBms(Short bms) {
        this.bms = bms;
    }

    @XmlTransient
    public Collection<Note> getNoteCollection() {
        return noteCollection;
    }

    public void setNoteCollection(Collection<Note> noteCollection) {
        this.noteCollection = noteCollection;
    }

    public Schulfach getSchulfachFK() {
        return schulfachFK;
    }

    public void setSchulfachFK(Schulfach schulfachFK) {
        this.schulfachFK = schulfachFK;
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
        if (!(object instanceof Pruefung)) {
            return false;
        }
        Pruefung other = (Pruefung) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.fuchsgroup.excel.Pruefung[ id=" + id + " ]";
    }
    
}
