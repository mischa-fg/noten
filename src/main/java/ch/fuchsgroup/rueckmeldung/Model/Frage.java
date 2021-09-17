/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.fuchsgroup.rueckmeldung.Model;

import ch.fuchsgroup.rueckmeldung.Model.Rueckmeldung2frage;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "frage")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Frage.findAll", query = "SELECT f FROM Frage f")
    , @NamedQuery(name = "Frage.findById", query = "SELECT f FROM Frage f WHERE f.id = :id")
    , @NamedQuery(name = "Frage.findByMultiple", query = "SELECT f FROM Frage f WHERE f.multiple = :multiple")})
public class Frage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Short id;
    @Lob
    @Size(max = 65535)
    @Column(name = "Frage")
    private String frage;
    @Column(name = "multiple")
    private Boolean multiple;
    @OneToMany(mappedBy = "frageFK")
    private Collection<Rueckmeldung2frage> rueckmeldung2frageCollection;

    public Frage() {
    }

    public Frage(Short id) {
        this.id = id;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getFrage() {
        return frage;
    }

    public void setFrage(String frage) {
        this.frage = frage;
    }

    public Boolean getMultiple() {
        return multiple;
    }

    public void setMultiple(Boolean multiple) {
        this.multiple = multiple;
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
        if (!(object instanceof Frage)) {
            return false;
        }
        Frage other = (Frage) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.fuchsgroup.rueckmeldung.Frage[ id=" + id + " ]";
    }
    
}
