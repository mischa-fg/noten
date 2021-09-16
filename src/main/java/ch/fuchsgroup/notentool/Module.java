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
@Table(name = "module")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Module.findAll", query = "SELECT m FROM Module m")
    , @NamedQuery(name = "Module.findById", query = "SELECT m FROM Module m WHERE m.id = :id")
    , @NamedQuery(name = "Module.findByModulnummer", query = "SELECT m FROM Module m WHERE m.modulnummer = :modulnummer")
    , @NamedQuery(name = "Module.findByBeschreibung", query = "SELECT m FROM Module m WHERE m.beschreibung = :beschreibung")
    , @NamedQuery(name = "Module.findByBezeichnung", query = "SELECT m FROM Module m WHERE m.bezeichnung = :bezeichnung")})
public class Module implements Serializable {

    @OneToMany(mappedBy = "moduleFK")
    private Collection<Rueckmeldung> rueckmeldungCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Short id;
    @Size(max = 15)
    @Column(name = "Modulnummer")
    private String modulnummer;
    @Size(max = 150)
    @Column(name = "Beschreibung")
    private String beschreibung;
    @Size(max = 70)
    @Column(name = "Bezeichnung")
    private String bezeichnung;
    @OneToMany(mappedBy = "moduleFK")
    private Collection<Kurse> kurseCollection;

    public Module() {
    }

    public Module(Short id) {
        this.id = id;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getModulnummer() {
        return modulnummer;
    }

    public void setModulnummer(String modulnummer) {
        this.modulnummer = modulnummer;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
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
        if (!(object instanceof Module)) {
            return false;
        }
        Module other = (Module) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.fuchsgroup.notentool.Module[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<Rueckmeldung> getRueckmeldungCollection() {
        return rueckmeldungCollection;
    }

    public void setRueckmeldungCollection(Collection<Rueckmeldung> rueckmeldungCollection) {
        this.rueckmeldungCollection = rueckmeldungCollection;
    }
    
}
