/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.fuchsgroup.notentool;

import ch.fuchsgroup.rueckmeldung.Rueckmeldung;
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
@Table(name = "kursleiter")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kursleiter.findAll", query = "SELECT k FROM Kursleiter k")
    , @NamedQuery(name = "Kursleiter.findById", query = "SELECT k FROM Kursleiter k WHERE k.id = :id")
    , @NamedQuery(name = "Kursleiter.findByName", query = "SELECT k FROM Kursleiter k WHERE k.name = :name")
    , @NamedQuery(name = "Kursleiter.findByVorname", query = "SELECT k FROM Kursleiter k WHERE k.vorname = :vorname")})
public class Kursleiter implements Serializable {

    @OneToMany(mappedBy = "kursleiterFK")
    private Collection<Rueckmeldung> rueckmeldungCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Short id;
    @Size(max = 25)
    @Column(name = "Name")
    private String name;
    @Size(max = 25)
    @Column(name = "Vorname")
    private String vorname;
    @OneToMany(mappedBy = "kursleiterFK")
    private Collection<Kurse> kurseCollection;

    public Kursleiter() {
    }

    public Kursleiter(Short id) {
        this.id = id;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

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

    @XmlTransient
    public Collection<Kurse> getKurseCollection() {
        return kurseCollection;
    }

    public void setKurseCollection(Collection<Kurse> kurseCollection) {
        this.kurseCollection = kurseCollection;
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
        if (!(object instanceof Kursleiter)) {
            return false;
        }
        Kursleiter other = (Kursleiter) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.fuchsgroup.notentool.Kursleiter[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<Rueckmeldung> getRueckmeldungCollection() {
        return rueckmeldungCollection;
    }

    public void setRueckmeldungCollection(Collection<Rueckmeldung> rueckmeldungCollection) {
        this.rueckmeldungCollection = rueckmeldungCollection;
    }
    
}
