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
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToMany;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Sebcio
 */
@Entity
@Table(name = "klient")
@NamedQueries({
    @NamedQuery(name = "Klient.findAll", query = "SELECT k FROM Klient k")
    , @NamedQuery(name = "Klient.findByIDKlienta", query = "SELECT k FROM Klient k WHERE k.iDKlienta = :iDKlienta")
    , @NamedQuery(name = "Klient.findByImie", query = "SELECT k FROM Klient k WHERE k.imie = :imie")
    , @NamedQuery(name = "Klient.findByNazwisko", query = "SELECT k FROM Klient k WHERE k.nazwisko = :nazwisko")
    , @NamedQuery(name = "Klient.findByPesel", query = "SELECT k FROM Klient k WHERE k.pesel = :pesel")
    , @NamedQuery(name = "Klient.findByTelefon", query = "SELECT k FROM Klient k WHERE k.telefon = :telefon")})
@NamedStoredProcedureQuery(
        name = "dodaj_klienta",
        procedureName = "dodaj_klienta",
        parameters = {  @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "imie"),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "nazwisko"),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "pesel"),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "telefon") })
public class Klient implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_Klienta")
    private Integer iDKlienta;
    @Basic(optional = false)
    @Column(name = "Imie")
    private String imie;
    @Basic(optional = false)
    @Column(name = "Nazwisko")
    private String nazwisko;
    @Basic(optional = false)
    @Column(name = "PESEL")
    private String pesel;
    @Basic(optional = false)
    @Column(name = "Telefon")
    private String telefon;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "klientID")
    private Collection<Recepta> receptaCollection;

    public Klient() {
    }

    public Klient(Integer iDKlienta) {
        this.iDKlienta = iDKlienta;
    }

    public Klient(Integer iDKlienta, String imie, String nazwisko, String pesel, String telefon) {
        this.iDKlienta = iDKlienta;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pesel = pesel;
        this.telefon = telefon;
    }

    public Integer getIDKlienta() {
        return iDKlienta;
    }

    public void setIDKlienta(Integer iDKlienta) {
        Integer oldIDKlienta = this.iDKlienta;
        this.iDKlienta = iDKlienta;
        changeSupport.firePropertyChange("IDKlienta", oldIDKlienta, iDKlienta);
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        String oldImie = this.imie;
        this.imie = imie;
        changeSupport.firePropertyChange("imie", oldImie, imie);
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        String oldNazwisko = this.nazwisko;
        this.nazwisko = nazwisko;
        changeSupport.firePropertyChange("nazwisko", oldNazwisko, nazwisko);
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        String oldPesel = this.pesel;
        this.pesel = pesel;
        changeSupport.firePropertyChange("pesel", oldPesel, pesel);
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        String oldTelefon = this.telefon;
        this.telefon = telefon;
        changeSupport.firePropertyChange("telefon", oldTelefon, telefon);
    }

    public Collection<Recepta> getReceptaCollection() {
        return receptaCollection;
    }

    public void setReceptaCollection(Collection<Recepta> receptaCollection) {
        this.receptaCollection = receptaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDKlienta != null ? iDKlienta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Klient)) {
            return false;
        }
        Klient other = (Klient) object;
        if ((this.iDKlienta == null && other.iDKlienta != null) || (this.iDKlienta != null && !this.iDKlienta.equals(other.iDKlienta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return imie + " " + nazwisko;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
