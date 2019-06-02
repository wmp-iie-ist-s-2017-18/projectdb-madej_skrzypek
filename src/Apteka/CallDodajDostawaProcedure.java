/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Apteka;

import com.mysql.fabric.xmlrpc.base.Data;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.StoredProcedureQuery;

/**
 *
 * @author Sebcio
 */
public class CallDodajDostawaProcedure {
    
    
    private EntityManagerFactory emf;
    
    
//    public void init() {
//        emf = Persistence.createEntityManagerFactory("AptekaBazaPU");
//    }
    
//    public void close() {
//        emf.close();
//    }
    
    public void dodajDostawe(String nazwa, Date data, Integer ilosc) {
        emf = Persistence.createEntityManagerFactory("AptekaBazaPU");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
            StoredProcedureQuery query = em.createNamedStoredProcedureQuery("dodaj_dostawa");
            query.setParameter("nazwa", nazwa);
            query.setParameter("data", data);
            query.setParameter("ilosc", ilosc);
            query.execute();
        
            em.getTransaction().commit();
        
            em.close();
            emf.close();
    }
    
}
