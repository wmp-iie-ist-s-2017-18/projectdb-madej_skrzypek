/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Apteka;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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

/**
 *
 * @author Sebcio
 */
@Entity
@Table(name = "dostawa")
@NamedQueries({
    @NamedQuery(name = "Dostawa.findAll", query = "SELECT d FROM Dostawa d")
    , @NamedQuery(name = "Dostawa.findByIDdostawy", query = "SELECT d FROM Dostawa d WHERE d.iDdostawy = :iDdostawy")
    , @NamedQuery(name = "Dostawa.findByDatadostarczenia", query = "SELECT d FROM Dostawa d WHERE d.datadostarczenia = :datadostarczenia")
    , @NamedQuery(name = "Dostawa.findByNazwahurtowni", query = "SELECT d FROM Dostawa d WHERE d.nazwahurtowni = :nazwahurtowni")
    , @NamedQuery(name = "Dostawa.findByIlosc", query = "SELECT d FROM Dostawa d WHERE d.ilosc = :ilosc")})
public class Dostawa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_dostawy")
    private Integer iDdostawy;
    @Basic(optional = false)
    @Column(name = "Data_dostarczenia")
    @Temporal(TemporalType.DATE)
    private Date datadostarczenia;
    @Basic(optional = false)
    @Column(name = "Nazwa_hurtowni")
    private String nazwahurtowni;
    @Basic(optional = false)
    @Column(name = "Ilosc")
    private int ilosc;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDdostawy")
    private Collection<DostawaMagazyn> dostawaMagazynCollection;

    public Dostawa() {
    }

    public Dostawa(Integer iDdostawy) {
        this.iDdostawy = iDdostawy;
    }

    public Dostawa(Integer iDdostawy, Date datadostarczenia, String nazwahurtowni, int ilosc) {
        this.iDdostawy = iDdostawy;
        this.datadostarczenia = datadostarczenia;
        this.nazwahurtowni = nazwahurtowni;
        this.ilosc = ilosc;
    }

    public Integer getIDdostawy() {
        return iDdostawy;
    }

    public void setIDdostawy(Integer iDdostawy) {
        this.iDdostawy = iDdostawy;
    }

    public Date getDatadostarczenia() {
        return datadostarczenia;
    }

    public void setDatadostarczenia(Date datadostarczenia) {
        this.datadostarczenia = datadostarczenia;
    }

    public String getNazwahurtowni() {
        return nazwahurtowni;
    }

    public void setNazwahurtowni(String nazwahurtowni) {
        this.nazwahurtowni = nazwahurtowni;
    }

    public int getIlosc() {
        return ilosc;
    }

    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }

    public Collection<DostawaMagazyn> getDostawaMagazynCollection() {
        return dostawaMagazynCollection;
    }

    public void setDostawaMagazynCollection(Collection<DostawaMagazyn> dostawaMagazynCollection) {
        this.dostawaMagazynCollection = dostawaMagazynCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDdostawy != null ? iDdostawy.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dostawa)) {
            return false;
        }
        Dostawa other = (Dostawa) object;
        if ((this.iDdostawy == null && other.iDdostawy != null) || (this.iDdostawy != null && !this.iDdostawy.equals(other.iDdostawy))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nazwahurtowni;
    }
    
}
