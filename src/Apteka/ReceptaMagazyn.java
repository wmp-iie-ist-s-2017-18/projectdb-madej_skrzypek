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
@Table(name = "recepta_magazyn")
@NamedQueries({
    @NamedQuery(name = "ReceptaMagazyn.findAll", query = "SELECT r FROM ReceptaMagazyn r")
    , @NamedQuery(name = "ReceptaMagazyn.findByIdreceptamagazyn", query = "SELECT r FROM ReceptaMagazyn r WHERE r.idreceptamagazyn = :idreceptamagazyn")})
public class ReceptaMagazyn implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id_recepta_magazyn")
    private Integer idreceptamagazyn;
    @JoinColumn(name = "ID_recepty", referencedColumnName = "ID_recepty")
    @ManyToOne(optional = false)
    private Recepta iDrecepty;
    @JoinColumn(name = "ID_leku", referencedColumnName = "ID_leku")
    @ManyToOne(optional = false)
    private MagazynLeki iDleku;

    public ReceptaMagazyn() {
    }

    public ReceptaMagazyn(Integer idreceptamagazyn) {
        this.idreceptamagazyn = idreceptamagazyn;
    }

    public Integer getIdreceptamagazyn() {
        return idreceptamagazyn;
    }

    public void setIdreceptamagazyn(Integer idreceptamagazyn) {
        this.idreceptamagazyn = idreceptamagazyn;
    }

    public Recepta getIDrecepty() {
        return iDrecepty;
    }

    public void setIDrecepty(Recepta iDrecepty) {
        this.iDrecepty = iDrecepty;
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
        hash += (idreceptamagazyn != null ? idreceptamagazyn.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReceptaMagazyn)) {
            return false;
        }
        ReceptaMagazyn other = (ReceptaMagazyn) object;
        if ((this.idreceptamagazyn == null && other.idreceptamagazyn != null) || (this.idreceptamagazyn != null && !this.idreceptamagazyn.equals(other.idreceptamagazyn))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Apteka.ReceptaMagazyn[ idreceptamagazyn=" + idreceptamagazyn + " ]";
    }
    
}
