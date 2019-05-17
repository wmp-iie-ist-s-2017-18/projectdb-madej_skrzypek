/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Apteka;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Sebcio
 */
@Entity
@Table(name = "magazyn_leki")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MagazynLeki.findAll", query = "SELECT m FROM MagazynLeki m")
    , @NamedQuery(name = "MagazynLeki.findByIDleku", query = "SELECT m FROM MagazynLeki m WHERE m.iDleku = :iDleku")
    , @NamedQuery(name = "MagazynLeki.findByNazwa", query = "SELECT m FROM MagazynLeki m WHERE m.nazwa = :nazwa")
    , @NamedQuery(name = "MagazynLeki.findByCena", query = "SELECT m FROM MagazynLeki m WHERE m.cena = :cena")
    , @NamedQuery(name = "MagazynLeki.findByIlosc", query = "SELECT m FROM MagazynLeki m WHERE m.ilosc = :ilosc")
    , @NamedQuery(name = "MagazynLeki.findByDatawaznosci", query = "SELECT m FROM MagazynLeki m WHERE m.datawaznosci = :datawaznosci")})
public class MagazynLeki implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_leku")
    private Integer iDleku;
    @Basic(optional = false)
    @Column(name = "Nazwa")
    private String nazwa;
    @Basic(optional = false)
    @Column(name = "Cena")
    private int cena;
    @Basic(optional = false)
    @Column(name = "Ilosc")
    private int ilosc;
    @Basic(optional = false)
    @Column(name = "Data_waznosci")
    @Temporal(TemporalType.DATE)
    private Date datawaznosci;
    @ManyToMany(mappedBy = "magazynLekiCollection")
    private Collection<Recepta> receptaCollection;
    @ManyToMany(mappedBy = "magazynLekiCollection")
    private Collection<Dostawa> dostawaCollection;

    public MagazynLeki() {
    }

    public MagazynLeki(Integer iDleku) {
        this.iDleku = iDleku;
    }

    public MagazynLeki(Integer iDleku, String nazwa, int cena, int ilosc, Date datawaznosci) {
        this.iDleku = iDleku;
        this.nazwa = nazwa;
        this.cena = cena;
        this.ilosc = ilosc;
        this.datawaznosci = datawaznosci;
    }

    public Integer getIDleku() {
        return iDleku;
    }

    public void setIDleku(Integer iDleku) {
        Integer oldIDleku = this.iDleku;
        this.iDleku = iDleku;
        changeSupport.firePropertyChange("IDleku", oldIDleku, iDleku);
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        String oldNazwa = this.nazwa;
        this.nazwa = nazwa;
        changeSupport.firePropertyChange("nazwa", oldNazwa, nazwa);
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        int oldCena = this.cena;
        this.cena = cena;
        changeSupport.firePropertyChange("cena", oldCena, cena);
    }

    public int getIlosc() {
        return ilosc;
    }

    public void setIlosc(int ilosc) {
        int oldIlosc = this.ilosc;
        this.ilosc = ilosc;
        changeSupport.firePropertyChange("ilosc", oldIlosc, ilosc);
    }

    public Date getDatawaznosci() {
        return datawaznosci;
    }

    public void setDatawaznosci(Date datawaznosci) {
        Date oldDatawaznosci = this.datawaznosci;
        this.datawaznosci = datawaznosci;
        changeSupport.firePropertyChange("datawaznosci", oldDatawaznosci, datawaznosci);
    }

    @XmlTransient
    public Collection<Recepta> getReceptaCollection() {
        return receptaCollection;
    }

    public void setReceptaCollection(Collection<Recepta> receptaCollection) {
        this.receptaCollection = receptaCollection;
    }

    @XmlTransient
    public Collection<Dostawa> getDostawaCollection() {
        return dostawaCollection;
    }

    public void setDostawaCollection(Collection<Dostawa> dostawaCollection) {
        this.dostawaCollection = dostawaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDleku != null ? iDleku.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MagazynLeki)) {
            return false;
        }
        MagazynLeki other = (MagazynLeki) object;
        if ((this.iDleku == null && other.iDleku != null) || (this.iDleku != null && !this.iDleku.equals(other.iDleku))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Apteka.MagazynLeki[ iDleku=" + iDleku + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
