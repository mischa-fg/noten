/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.fuchsgroup.excel;

import ch.fuchsgroup.notentool.Teilnehmer;
import java.io.Serializable;
import java.math.BigDecimal;
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
 * @author misch
 */
@Entity
@Table(name = "schulfach_gesamtnote")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SchulfachGesamtnote.findAll", query = "SELECT s FROM SchulfachGesamtnote s")
    , @NamedQuery(name = "SchulfachGesamtnote.findById", query = "SELECT s FROM SchulfachGesamtnote s WHERE s.id = :id")
    , @NamedQuery(name = "SchulfachGesamtnote.findByNote", query = "SELECT s FROM SchulfachGesamtnote s WHERE s.note = :note")})
public class SchulfachGesamtnote implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Short id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Note")
    private BigDecimal note;
    @JoinColumn(name = "Schulfach_FK", referencedColumnName = "ID")
    @ManyToOne
    private Schulfach schulfachFK;
    @JoinColumn(name = "Teilnehmer_FK", referencedColumnName = "ID")
    @ManyToOne
    private Teilnehmer teilnehmerFK;

    public SchulfachGesamtnote() {
    }

    public SchulfachGesamtnote(Short id) {
        this.id = id;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public BigDecimal getNote() {
        return note;
    }

    public void setNote(BigDecimal note) {
        this.note = note;
    }

    public Schulfach getSchulfachFK() {
        return schulfachFK;
    }

    public void setSchulfachFK(Schulfach schulfachFK) {
        this.schulfachFK = schulfachFK;
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
        if (!(object instanceof SchulfachGesamtnote)) {
            return false;
        }
        SchulfachGesamtnote other = (SchulfachGesamtnote) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.fuchsgroup.excel.SchulfachGesamtnote[ id=" + id + " ]";
    }
    
}
