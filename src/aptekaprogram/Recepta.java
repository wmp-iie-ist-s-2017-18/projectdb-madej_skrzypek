/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aptekaprogram;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Sebcio
 */
@Entity
@Table(name = "recepta")
@NamedQueries({
    @NamedQuery(name = "Recepta.findAll", query = "SELECT r FROM Recepta r")
    , @NamedQuery(name = "Recepta.findByIDrecepty", query = "SELECT r FROM Recepta r WHERE r.iDrecepty = :iDrecepty")})
public class Recepta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_recepty")
    private Integer iDrecepty;
    @Basic(optional = false)
    @Lob
    @Column(name = "Leki")
    private String leki;
    @JoinTable(name = "recepta-magazyn", joinColumns = {
        @JoinColumn(name = "ID_recepty", referencedColumnName = "ID_recepty")}, inverseJoinColumns = {
        @JoinColumn(name = "ID_leku", referencedColumnName = "ID_leku")})
    @ManyToMany
    private Collection<MagazynLeki> magazynLekiCollection;
    @JoinColumn(name = "Klient_ID", referencedColumnName = "ID_Klienta")
    @ManyToOne(optional = false)
    private Klient klientID;

    public Recepta() {
    }

    public Recepta(Integer iDrecepty) {
        this.iDrecepty = iDrecepty;
    }

    public Recepta(Integer iDrecepty, String leki) {
        this.iDrecepty = iDrecepty;
        this.leki = leki;
    }

    public Integer getIDrecepty() {
        return iDrecepty;
    }

    public void setIDrecepty(Integer iDrecepty) {
        this.iDrecepty = iDrecepty;
    }

    public String getLeki() {
        return leki;
    }

    public void setLeki(String leki) {
        this.leki = leki;
    }

    public Collection<MagazynLeki> getMagazynLekiCollection() {
        return magazynLekiCollection;
    }

    public void setMagazynLekiCollection(Collection<MagazynLeki> magazynLekiCollection) {
        this.magazynLekiCollection = magazynLekiCollection;
    }

    public Klient getKlientID() {
        return klientID;
    }

    public void setKlientID(Klient klientID) {
        this.klientID = klientID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDrecepty != null ? iDrecepty.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recepta)) {
            return false;
        }
        Recepta other = (Recepta) object;
        if ((this.iDrecepty == null && other.iDrecepty != null) || (this.iDrecepty != null && !this.iDrecepty.equals(other.iDrecepty))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "aptekaprogram.Recepta[ iDrecepty=" + iDrecepty + " ]";
    }
    
}
