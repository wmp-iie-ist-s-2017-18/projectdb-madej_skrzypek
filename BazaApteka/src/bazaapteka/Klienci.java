/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazaapteka;

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

/**
 *
 * @author Sebci
 */
@Entity
@Table(name = "klienci")
@NamedQueries({
    @NamedQuery(name = "Klienci.findAll", query = "SELECT k FROM Klienci k")
    , @NamedQuery(name = "Klienci.findByIdKlienta", query = "SELECT k FROM Klienci k WHERE k.idKlienta = :idKlienta")
    , @NamedQuery(name = "Klienci.findByImie", query = "SELECT k FROM Klienci k WHERE k.imie = :imie")
    , @NamedQuery(name = "Klienci.findByNazwisko", query = "SELECT k FROM Klienci k WHERE k.nazwisko = :nazwisko")
    , @NamedQuery(name = "Klienci.findByPesel", query = "SELECT k FROM Klienci k WHERE k.pesel = :pesel")
    , @NamedQuery(name = "Klienci.findByNrtel", query = "SELECT k FROM Klienci k WHERE k.nrtel = :nrtel")})
public class Klienci implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_klienta")
    private Integer idKlienta;
    @Column(name = "Imie")
    private String imie;
    @Column(name = "Nazwisko")
    private String nazwisko;
    @Column(name = "PESEL")
    private String pesel;
    @Column(name = "Nr_tel")
    private Integer nrtel;
    @Lob
    @Column(name = "recepta")
    private String recepta;
    @OneToMany(mappedBy = "idKlienta")
    private Collection<Lekarstwa> lekarstwaCollection;

    public Klienci() {
    }

    public Klienci(Integer idKlienta) {
        this.idKlienta = idKlienta;
    }

    public Integer getIdKlienta() {
        return idKlienta;
    }

    public void setIdKlienta(Integer idKlienta) {
        this.idKlienta = idKlienta;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public Integer getNrtel() {
        return nrtel;
    }

    public void setNrtel(Integer nrtel) {
        this.nrtel = nrtel;
    }

    public String getRecepta() {
        return recepta;
    }

    public void setRecepta(String recepta) {
        this.recepta = recepta;
    }

    public Collection<Lekarstwa> getLekarstwaCollection() {
        return lekarstwaCollection;
    }

    public void setLekarstwaCollection(Collection<Lekarstwa> lekarstwaCollection) {
        this.lekarstwaCollection = lekarstwaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idKlienta != null ? idKlienta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Klienci)) {
            return false;
        }
        Klienci other = (Klienci) object;
        if ((this.idKlienta == null && other.idKlienta != null) || (this.idKlienta != null && !this.idKlienta.equals(other.idKlienta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bazaapteka.Klienci[ idKlienta=" + idKlienta + " ]";
    }
    
}
