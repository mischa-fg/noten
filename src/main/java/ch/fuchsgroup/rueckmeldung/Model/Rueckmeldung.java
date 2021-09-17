/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.fuchsgroup.rueckmeldung.Model;

import ch.fuchsgroup.notentool.Klasse;
import ch.fuchsgroup.notentool.Kursleiter;
import ch.fuchsgroup.notentool.Module;
import ch.fuchsgroup.notentool.Teilnehmer;
import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author misch
 */
@Entity
@Table(name = "rueckmeldung")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rueckmeldung.findAll", query = "SELECT r FROM Rueckmeldung r")
    , @NamedQuery(name = "Rueckmeldung.findById", query = "SELECT r FROM Rueckmeldung r WHERE r.id = :id")
    , @NamedQuery(name = "Rueckmeldung.findByDatumAbgeschlossen", query = "SELECT r FROM Rueckmeldung r WHERE r.datumAbgeschlossen = :datumAbgeschlossen")})
public class Rueckmeldung implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Short id;
    @Column(name = "DatumAbgeschlossen")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumAbgeschlossen;
    @JoinColumn(name = "Klasse_FK", referencedColumnName = "ID")
    @ManyToOne
    private Klasse klasseFK;
    @JoinColumn(name = "Module_FK", referencedColumnName = "ID")
    @ManyToOne
    private Module moduleFK;
    @JoinColumn(name = "Kursleiter_FK", referencedColumnName = "ID")
    @ManyToOne
    private Kursleiter kursleiterFK;
    @JoinColumn(name = "Teilnehmer_FK", referencedColumnName = "ID")
    @ManyToOne
    private Teilnehmer teilnehmerFK;
    @OneToMany(mappedBy = "rueckmeldungFK")
    private Collection<Rueckmeldung2frage> rueckmeldung2frageCollection;

    public Rueckmeldung() {
    }

    public Rueckmeldung(Short id) {
        this.id = id;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public Date getDatumAbgeschlossen() {
        return datumAbgeschlossen;
    }

    public void setDatumAbgeschlossen(Date datumAbgeschlossen) {
        this.datumAbgeschlossen = datumAbgeschlossen;
    }

    public Klasse getKlasseFK() {
        return klasseFK;
    }

    public void setKlasseFK(Klasse klasseFK) {
        this.klasseFK = klasseFK;
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

    public Teilnehmer getTeilnehmerFK() {
        return teilnehmerFK;
    }

    public void setTeilnehmerFK(Teilnehmer teilnehmerFK) {
        this.teilnehmerFK = teilnehmerFK;
    }

    @XmlTransient
    public Collection<Rueckmeldung2frage> getRueckmeldung2frageCollection() {
        return rueckmeldung2frageCollection;
    }

    public void setRueckmeldung2frageCollection(Collection<Rueckmeldung2frage> rueckmeldung2frageCollection) {
        this.rueckmeldung2frageCollection = rueckmeldung2frageCollection;
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
        if (!(object instanceof Rueckmeldung)) {
            return false;
        }
        Rueckmeldung other = (Rueckmeldung) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.fuchsgroup.rueckmeldung.Rueckmeldung[ id=" + id + " ]";
    }
    
}
