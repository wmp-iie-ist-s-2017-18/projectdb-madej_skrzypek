/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Apteka;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToMany;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Sebcio
 */
@Entity
@Table(name = "magazyn_leki")
@NamedQueries({
    @NamedQuery(name = "MagazynLeki.findAll", query = "SELECT m FROM MagazynLeki m")
    , @NamedQuery(name = "MagazynLeki.findByIDleku", query = "SELECT m FROM MagazynLeki m WHERE m.iDleku = :iDleku")
    , @NamedQuery(name = "MagazynLeki.findByNazwa", query = "SELECT m FROM MagazynLeki m WHERE m.nazwa = :nazwa")
    , @NamedQuery(name = "MagazynLeki.findByCena", query = "SELECT m FROM MagazynLeki m WHERE m.cena = :cena")
    , @NamedQuery(name = "MagazynLeki.findByIlosc", query = "SELECT m FROM MagazynLeki m WHERE m.ilosc = :ilosc")
    , @NamedQuery(name = "MagazynLeki.findByDatawaznosci", query = "SELECT m FROM MagazynLeki m WHERE m.datawaznosci = :datawaznosci")})
@NamedStoredProcedureQuery(
        name = "dodaj_magazyn",
        procedureName = "dodaj_magazyn",
        parameters = {  @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "nazwa"),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = Double.class, name = "cena"),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "ilosc"),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = Date.class, name = "data") })
public class MagazynLeki implements Serializable {

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
    private long cena;
    @Basic(optional = false)
    @Column(name = "Ilosc")
    private int ilosc;
    @Basic(optional = false)
    @Column(name = "Data_waznosci")
    @Temporal(TemporalType.DATE)
    private Date datawaznosci;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDleku")
    private Collection<DostawaMagazyn> dostawaMagazynCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDleku")
    private Collection<ReceptaMagazyn> receptaMagazynCollection;

    public MagazynLeki() {
    }

    public MagazynLeki(Integer iDleku) {
        this.iDleku = iDleku;
    }

    public MagazynLeki(Integer iDleku, String nazwa, long cena, int ilosc, Date datawaznosci) {
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
        this.iDleku = iDleku;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public long getCena() {
        return cena;
    }

    public void setCena(long cena) {
        this.cena = cena;
    }

    public int getIlosc() {
        return ilosc;
    }

    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }

    public Date getDatawaznosci() {
        return datawaznosci;
    }
    
    public String getDatawaznosciAsString() {
        // August 12, 2010
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(datawaznosci);
    }

    public void setDatawaznosci(Date datawaznosci) {
        this.datawaznosci = datawaznosci;
    }

    public Collection<DostawaMagazyn> getDostawaMagazynCollection() {
        return dostawaMagazynCollection;
    }

    public void setDostawaMagazynCollection(Collection<DostawaMagazyn> dostawaMagazynCollection) {
        this.dostawaMagazynCollection = dostawaMagazynCollection;
    }

    public Collection<ReceptaMagazyn> getReceptaMagazynCollection() {
        return receptaMagazynCollection;
    }

    public void setReceptaMagazynCollection(Collection<ReceptaMagazyn> receptaMagazynCollection) {
        this.receptaMagazynCollection = receptaMagazynCollection;
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
        return nazwa;
    }
    
}
