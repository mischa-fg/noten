/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.fuchsgroup.notentool;

import ch.fuchsgroup.excel.Note;
import ch.fuchsgroup.excel.SchulfachGesamtnote;
import ch.fuchsgroup.rueckmeldung.Model.Rueckmeldung;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author misch
 */
@Entity
@Table(name = "teilnehmer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Teilnehmer.findAll", query = "SELECT t FROM Teilnehmer t")
    , @NamedQuery(name = "Teilnehmer.findById", query = "SELECT t FROM Teilnehmer t WHERE t.id = :id")
    , @NamedQuery(name = "Teilnehmer.findByAnrede", query = "SELECT t FROM Teilnehmer t WHERE t.anrede = :anrede")
    , @NamedQuery(name = "Teilnehmer.findByName", query = "SELECT t FROM Teilnehmer t WHERE t.name = :name")
    , @NamedQuery(name = "Teilnehmer.findByVorname", query = "SELECT t FROM Teilnehmer t WHERE t.vorname = :vorname")
    , @NamedQuery(name = "Teilnehmer.findByGeburtsdatum", query = "SELECT t FROM Teilnehmer t WHERE t.geburtsdatum = :geburtsdatum")
    , @NamedQuery(name = "Teilnehmer.findByEmail", query = "SELECT t FROM Teilnehmer t WHERE t.email = :email")
    , @NamedQuery(name = "Teilnehmer.findByOEBezeichnung", query = "SELECT t FROM Teilnehmer t WHERE t.oEBezeichnung = :oEBezeichnung")})
public class Teilnehmer implements Serializable {

    @OneToMany(mappedBy = "teilnehmerFK")
    private Collection<Rueckmeldung> rueckmeldungCollection;

    @OneToMany(mappedBy = "teilnehmerFK")
    private Collection<Note> noteCollection;
    @OneToMany(mappedBy = "teilnehmerFK")
    private Collection<SchulfachGesamtnote> schulfachGesamtnoteCollection;

    @Size(max = 250)
    @Column(name = "Foto")
    private String foto;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Short id;
    @Size(max = 6)
    @Column(name = "Anrede")
    private String anrede;
    @Size(max = 100)
    @Column(name = "Name")
    private String name;
    @Size(max = 100)
    @Column(name = "Vorname")
    private String vorname;
    @Column(name = "Geburtsdatum")
    @Temporal(TemporalType.DATE)
    private Date geburtsdatum;
    @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?||null", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(name = "Email")
    private String email;
    @Size(max = 50)
    @Column(name = "OEBezeichnung")
    private String oEBezeichnung;

    public Teilnehmer() {
    }

    public Teilnehmer(Short id) {
        this.id = id;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getAnrede() {
        return anrede;
    }

    public void setAnrede(String anrede) {
        this.anrede = anrede;
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

    public Date getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(Date geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOEBezeichnung() {
        return oEBezeichnung;
    }

    public void setOEBezeichnung(String oEBezeichnung) {
        this.oEBezeichnung = oEBezeichnung;
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
        if (!(object instanceof Teilnehmer)) {
            return false;
        }
        Teilnehmer other = (Teilnehmer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.fuchsgroup.notentool.Teilnehmer[ id=" + id + ", name" + name + ", geburtsdatum "+ geburtsdatum +" ]";
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @XmlTransient
    public Collection<Note> getNoteCollection() {
        return noteCollection;
    }

    public void setNoteCollection(Collection<Note> noteCollection) {
        this.noteCollection = noteCollection;
    }

    @XmlTransient
    public Collection<SchulfachGesamtnote> getSchulfachGesamtnoteCollection() {
        return schulfachGesamtnoteCollection;
    }

    public void setSchulfachGesamtnoteCollection(Collection<SchulfachGesamtnote> schulfachGesamtnoteCollection) {
        this.schulfachGesamtnoteCollection = schulfachGesamtnoteCollection;
    }

    @XmlTransient
    public Collection<Rueckmeldung> getRueckmeldungCollection() {
        return rueckmeldungCollection;
    }

    public void setRueckmeldungCollection(Collection<Rueckmeldung> rueckmeldungCollection) {
        this.rueckmeldungCollection = rueckmeldungCollection;
    }
    
}
