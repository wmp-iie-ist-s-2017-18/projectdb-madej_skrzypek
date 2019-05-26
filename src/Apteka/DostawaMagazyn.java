/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Apteka;

import java.io.Serializable;
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

/**
 *
 * @author Sebcio
 */
@Entity
@Table(name = "dostawa_magazyn")
@NamedQueries({
    @NamedQuery(name = "DostawaMagazyn.findAll", query = "SELECT d FROM DostawaMagazyn d")
    , @NamedQuery(name = "DostawaMagazyn.findByIDdostawamagazyn", query = "SELECT d FROM DostawaMagazyn d WHERE d.iDdostawamagazyn = :iDdostawamagazyn")})
public class DostawaMagazyn implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_dostawa_magazyn")
    private Integer iDdostawamagazyn;
    @JoinColumn(name = "ID_dostawy", referencedColumnName = "ID_dostawy")
    @ManyToOne(optional = false)
    private Dostawa iDdostawy;
    @JoinColumn(name = "ID_leku", referencedColumnName = "ID_leku")
    @ManyToOne(optional = false)
    private MagazynLeki iDleku;

    public DostawaMagazyn() {
    }

    public DostawaMagazyn(Integer iDdostawamagazyn) {
        this.iDdostawamagazyn = iDdostawamagazyn;
    }

    public Integer getIDdostawamagazyn() {
        return iDdostawamagazyn;
    }

    public void setIDdostawamagazyn(Integer iDdostawamagazyn) {
        this.iDdostawamagazyn = iDdostawamagazyn;
    }

    public Dostawa getIDdostawy() {
        return iDdostawy;
    }

    public void setIDdostawy(Dostawa iDdostawy) {
        this.iDdostawy = iDdostawy;
    }

    public MagazynLeki getIDleku() {
        return iDleku;
    }

    public void setIDleku(MagazynLeki iDleku) {
        this.iDleku = iDleku;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDdostawamagazyn != null ? iDdostawamagazyn.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DostawaMagazyn)) {
            return false;
        }
        DostawaMagazyn other = (DostawaMagazyn) object;
        if ((this.iDdostawamagazyn == null && other.iDdostawamagazyn != null) || (this.iDdostawamagazyn != null && !this.iDdostawamagazyn.equals(other.iDdostawamagazyn))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Apteka.DostawaMagazyn[ iDdostawamagazyn=" + iDdostawamagazyn + " ]";
    }
    
}
