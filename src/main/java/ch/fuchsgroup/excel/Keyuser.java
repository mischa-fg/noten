/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.fuchsgroup.excel;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author misch
 */
@Entity
@Table(name = "keyuser")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Keyuser.findAll", query = "SELECT k FROM Keyuser k")
    , @NamedQuery(name = "Keyuser.findById", query = "SELECT k FROM Keyuser k WHERE k.id = :id")
    , @NamedQuery(name = "Keyuser.findByUsername", query = "SELECT k FROM Keyuser k WHERE k.username = :username")
    , @NamedQuery(name = "Keyuser.findByPasswort", query = "SELECT k FROM Keyuser k WHERE k.passwort = :passwort")
    , @NamedQuery(name = "Keyuser.findByApikey", query = "SELECT k FROM Keyuser k WHERE k.apikey = :apikey")})
public class Keyuser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 100)
    @Column(name = "Username")
    private String username;
    @Size(max = 100)
    @Column(name = "Passwort")
    private String passwort;
    @Size(max = 100)
    @Column(name = "Apikey")
    private String apikey;

    public Keyuser() {
    }

    public Keyuser(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
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
        if (!(object instanceof Keyuser)) {
            return false;
        }
        Keyuser other = (Keyuser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.fuchsgroup.excel.Keyuser[ id=" + id + " ]";
    }
    
}
