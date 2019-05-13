/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazaapteka;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Sebcio
 */
@Entity
@Table(name = "lekarstwa")
@NamedQueries({
    @NamedQuery(name = "Lekarstwa.findAll", query = "SELECT l FROM Lekarstwa l")
    , @NamedQuery(name = "Lekarstwa.findByIdLekarstwa", query = "SELECT l FROM Lekarstwa l WHERE l.idLekarstwa = :idLekarstwa")
    , @NamedQuery(name = "Lekarstwa.findByCena", query = "SELECT l FROM Lekarstwa l WHERE l.cena = :cena")
    , @NamedQuery(name = "Lekarstwa.findByIlosc", query = "SELECT l FROM Lekarstwa l WHERE l.ilosc = :ilosc")
    , @NamedQuery(name = "Lekarstwa.findByNazwalekarstwa", query = "SELECT l FROM Lekarstwa l WHERE l.nazwalekarstwa = :nazwalekarstwa")
    , @NamedQuery(name = "Lekarstwa.findByDatawaznosci", query = "SELECT l FROM Lekarstwa l WHERE l.datawaznosci = :datawaznosci")})
public class Lekarstwa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_lekarstwa")
    private Integer idLekarstwa;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Cena")
    private Float cena;
    @Basic(optional = false)
    @Column(name = "Ilosc")
    private int ilosc;
    @Column(name = "Nazwa_lekarstwa")
    private String nazwalekarstwa;
    @Column(name = "Data_waznosci")
    @Temporal(TemporalType.DATE)
    private Date datawaznosci;
    @JoinColumn(name = "id_klienta", referencedColumnName = "id_klienta")
    @ManyToOne
    private Klienci idKlienta;
    @JoinColumn(name = "id_zamowienia", referencedColumnName = "id_zamowienia")
    @ManyToOne
    private Zamowienia idZamowienia;

    public Lekarstwa() {
    }

    public Lekarstwa(Integer idLekarstwa) {
        this.idLekarstwa = idLekarstwa;
    }

    public Lekarstwa(Integer idLekarstwa, int ilosc) {
        this.idLekarstwa = idLekarstwa;
        this.ilosc = ilosc;
    }

    public Integer getIdLekarstwa() {
        return idLekarstwa;
    }

    public void setIdLekarstwa(Integer idLekarstwa) {
        this.idLekarstwa = idLekarstwa;
    }

    public Float getCena() {
        return cena;
    }

    public void setCena(Float cena) {
        this.cena = cena;
    }

    public int getIlosc() {
        return ilosc;
    }

    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }

    public String getNazwalekarstwa() {
        return nazwalekarstwa;
    }

    public void setNazwalekarstwa(String nazwalekarstwa) {
        this.nazwalekarstwa = nazwalekarstwa;
    }

    public Date getDatawaznosci() {
        return datawaznosci;
    }

    public void setDatawaznosci(Date datawaznosci) {
        this.datawaznosci = datawaznosci;
    }

    public Klienci getIdKlienta() {
        return idKlienta;
    }

    public void setIdKlienta(Klienci idKlienta) {
        this.idKlienta = idKlienta;
    }

    public Zamowienia getIdZamowienia() {
        return idZamowienia;
    }

    public void setIdZamowienia(Zamowienia idZamowienia) {
        this.idZamowienia = idZamowienia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLekarstwa != null ? idLekarstwa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lekarstwa)) {
            return false;
        }
        Lekarstwa other = (Lekarstwa) object;
        if ((this.idLekarstwa == null && other.idLekarstwa != null) || (this.idLekarstwa != null && !this.idLekarstwa.equals(other.idLekarstwa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bazaapteka.Lekarstwa[ idLekarstwa=" + idLekarstwa + " ]";
    }
    
}
