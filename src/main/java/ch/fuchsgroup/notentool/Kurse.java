/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.fuchsgroup.notentool;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author misch
 */
@Entity
@Table(name = "kurse")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kurse.findAll", query = "SELECT k FROM Kurse k")
    , @NamedQuery(name = "Kurse.findById", query = "SELECT k FROM Kurse k WHERE k.id = :id")
    , @NamedQuery(name = "Kurse.findByDatumvon", query = "SELECT k FROM Kurse k WHERE k.datumvon = :datumvon")
    , @NamedQuery(name = "Kurse.findByDatumbis", query = "SELECT k FROM Kurse k WHERE k.datumbis = :datumbis")})
public class Kurse implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "Datum_von")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumvon;
    @Column(name = "Datum_bis")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumbis;
    @JoinColumn(name = "Module_FK", referencedColumnName = "ID")
    @ManyToOne
    private Module moduleFK;
    @JoinColumn(name = "Kursleiter_FK", referencedColumnName = "ID")
    @ManyToOne
    private Kursleiter kursleiterFK;
    @JoinColumn(name = "Klasse_FK", referencedColumnName = "ID")
    @ManyToOne
    private Klasse klasseFK;

    public Kurse() {
    }

    public Kurse(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDatumvon() {
        return datumvon;
    }

    public void setDatumvon(Date datumvon) {
        this.datumvon = datumvon;
    }

    public Date getDatumbis() {
        return datumbis;
    }

    public void setDatumbis(Date datumbis) {
        this.datumbis = datumbis;
    }

    public Module getModuleFK() {
        return moduleFK;
    }

    public void setModuleFK(Module moduleFK) {
        this.moduleFK = moduleFK;
    }

    public Kursleiter getKursleiterFK() {
        return kursleiterFK;
    }

    public void setKursleiterFK(Kursleiter kursleiterFK) {
        this.kursleiterFK = kursleiterFK;
    }

    public Klasse getKlasseFK() {
        return klasseFK;
    }

    public void setKlasseFK(Klasse klasseFK) {
        this.klasseFK = klasseFK;
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
        if (!(object instanceof Kurse)) {
            return false;
        }
        Kurse other = (Kurse) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.fuchsgroup.notentool.Kurse[ id=" + id + " ]";
    }
    
}
