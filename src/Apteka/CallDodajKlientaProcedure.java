/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Apteka;

import java.sql.SQLIntegrityConstraintViolationException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import javax.persistence.StoredProcedureQuery;
import javax.swing.JOptionPane;

/**
 *
 * @author Sebcio
 */
public class CallDodajKlientaProcedure {
    
    private EntityManagerFactory emf;
    
    
//    public void init() {
//        emf = Persistence.createEntityManagerFactory("AptekaBazaPU");
//    }
    
//    public void close() {
//        emf.close();
//    }
    
    public boolean dodajKlienta(String imie, String nazwisko, String pesel, String telefon) {
        emf = Persistence.createEntityManagerFactory("AptekaBazaPU");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
        try {
            StoredProcedureQuery query = em.createNamedStoredProcedureQuery("dodaj_klienta");
            query.setParameter("imie", imie);
            query.setParameter("nazwisko", nazwisko);
            query.setParameter("pesel", pesel);
            query.setParameter("telefon", telefon);
            query.execute();
        
            em.getTransaction().commit();
        
        } catch (PersistenceException e) {
            return false;
        } finally {
            em.close();
            emf.close();
        } return true;
    }
    
}
