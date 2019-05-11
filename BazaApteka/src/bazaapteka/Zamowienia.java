/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazaapteka;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Sebcio
 */
@Entity
@Table(name = "zamowienia")
@NamedQueries({
    @NamedQuery(name = "Zamowienia.findAll", query = "SELECT z FROM Zamowienia z")
    , @NamedQuery(name = "Zamowienia.findByIdZamowienia", query = "SELECT z FROM Zamowienia z WHERE z.idZamowienia = :idZamowienia")
    , @NamedQuery(name = "Zamowienia.findByDatadostarczenia", query = "SELECT z FROM Zamowienia z WHERE z.datadostarczenia = :datadostarczenia")
    , @NamedQuery(name = "Zamowienia.findByNazwahurtowni", query = "SELECT z FROM Zamowienia z WHERE z.nazwahurtowni = :nazwahurtowni")})
public class Zamowienia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_zamowienia")
    private Integer idZamowienia;
    @Lob
    @Column(name = "Lista_lekarstw")
    private String listalekarstw;
    @Column(name = "Data_dostarczenia")
    @Temporal(TemporalType.DATE)
    private Date datadostarczenia;
    @Column(name = "Nazwa_hurtowni")
    private String nazwahurtowni;
    @OneToMany(mappedBy = "idZamowienia")
    private Collection<Lekarstwa> lekarstwaCollection;

    public Zamowienia() {
    }

    public Zamowienia(Integer idZamowienia) {
        this.idZamowienia = idZamowienia;
    }

    public Integer getIdZamowienia() {
        return idZamowienia;
    }

    public void setIdZamowienia(Integer idZamowienia) {
        this.idZamowienia = idZamowienia;
    }

    public String getListalekarstw() {
        return listalekarstw;
    }

    public void setListalekarstw(String listalekarstw) {
        this.listalekarstw = listalekarstw;
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

    public Collection<Lekarstwa> getLekarstwaCollection() {
        return lekarstwaCollection;
    }

    public void setLekarstwaCollection(Collection<Lekarstwa> lekarstwaCollection) {
        this.lekarstwaCollection = lekarstwaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idZamowienia != null ? idZamowienia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zamowienia)) {
            return false;
        }
        Zamowienia other = (Zamowienia) object;
        if ((this.idZamowienia == null && other.idZamowienia != null) || (this.idZamowienia != null && !this.idZamowienia.equals(other.idZamowienia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bazaapteka.Zamowienia[ idZamowienia=" + idZamowienia + " ]";
    }
    
}
