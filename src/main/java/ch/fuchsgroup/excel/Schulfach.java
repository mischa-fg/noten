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
@Table(name = "schulfach")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Schulfach.findAll", query = "SELECT s FROM Schulfach s")
    , @NamedQuery(name = "Schulfach.findById", query = "SELECT s FROM Schulfach s WHERE s.id = :id")
    , @NamedQuery(name = "Schulfach.findByFachname", query = "SELECT s FROM Schulfach s WHERE s.fachname = :fachname")
    , @NamedQuery(name = "Schulfach.findByBms", query = "SELECT s FROM Schulfach s WHERE s.bms = :bms")
    , @NamedQuery(name = "Schulfach.findByInf", query = "SELECT s FROM Schulfach s WHERE s.inf = :inf")})
public class Schulfach implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Short id;
    @Size(max = 45)
    @Column(name = "Fachname")
    private String fachname;
    @Column(name = "BMS")
    private Boolean bms;
    @Column(name = "INF")
    private Boolean inf;
    @OneToMany(mappedBy = "schulfachFK")
    private Collection<Pruefung> pruefungCollection;
    @OneToMany(mappedBy = "schulfachFK")
    private Collection<SchulfachGesamtnote> schulfachGesamtnoteCollection;

    public Schulfach() {
    }

    public Schulfach(Short id) {
        this.id = id;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getFachname() {
        return fachname;
    }

    public void setFachname(String fachname) {
        this.fachname = fachname;
    }

    public Boolean getBms() {
        return bms;
    }

    public void setBms(Boolean bms) {
        this.bms = bms;
    }

    public Boolean getInf() {
        return inf;
    }

    public void setInf(Boolean inf) {
        this.inf = inf;
    }

    @XmlTransient
    public Collection<Pruefung> getPruefungCollection() {
        return pruefungCollection;
    }

    public void setPruefungCollection(Collection<Pruefung> pruefungCollection) {
        this.pruefungCollection = pruefungCollection;
    }

    @XmlTransient
    public Collection<SchulfachGesamtnote> getSchulfachGesamtnoteCollection() {
        return schulfachGesamtnoteCollection;
    }

    public void setSchulfachGesamtnoteCollection(Collection<SchulfachGesamtnote> schulfachGesamtnoteCollection) {
        this.schulfachGesamtnoteCollection = schulfachGesamtnoteCollection;
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
        if (!(object instanceof Schulfach)) {
            return false;
        }
        Schulfach other = (Schulfach) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.fuchsgroup.excel.Schulfach[ id=" + id + " ]";
    }
    
}
