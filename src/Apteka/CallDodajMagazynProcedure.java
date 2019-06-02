/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Apteka;

import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;

/**
 *
 * @author Sebcio
 */
public class CallDodajMagazynProcedure {
    
    private EntityManagerFactory emf;
    
    
//    public void init() {
//        emf = Persistence.createEntityManagerFactory("AptekaBazaPU");
//    }
    
//    public void close() {
//        emf.close();
//    }
    
    public void dodajMagazyn(String nazwa, Double cena, Integer ilosc, Date data) {
        emf = Persistence.createEntityManagerFactory("AptekaBazaPU");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
            StoredProcedureQuery query = em.createNamedStoredProcedureQuery("dodaj_magazyn");
            query.setParameter("nazwa", nazwa);
            query.setParameter("cena", cena);
            query.setParameter("ilosc", ilosc);
            query.setParameter("data", data);
            query.execute();
        
            em.getTransaction().commit();
        
            em.close();
            emf.close();
    }
    
}
