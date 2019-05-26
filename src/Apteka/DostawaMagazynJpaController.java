/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Apteka;

import Apteka.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Sebcio
 */
public class DostawaMagazynJpaController implements Serializable {

    public DostawaMagazynJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DostawaMagazyn dostawaMagazyn) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Dostawa IDdostawy = dostawaMagazyn.getIDdostawy();
            if (IDdostawy != null) {
                IDdostawy = em.getReference(IDdostawy.getClass(), IDdostawy.getIDdostawy());
                dostawaMagazyn.setIDdostawy(IDdostawy);
            }
            MagazynLeki IDleku = dostawaMagazyn.getIDleku();
            if (IDleku != null) {
                IDleku = em.getReference(IDleku.getClass(), IDleku.getIDleku());
                dostawaMagazyn.setIDleku(IDleku);
            }
            em.persist(dostawaMagazyn);
            if (IDdostawy != null) {
                IDdostawy.getDostawaMagazynCollection().add(dostawaMagazyn);
                IDdostawy = em.merge(IDdostawy);
            }
            if (IDleku != null) {
                IDleku.getDostawaMagazynCollection().add(dostawaMagazyn);
                IDleku = em.merge(IDleku);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DostawaMagazyn dostawaMagazyn) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DostawaMagazyn persistentDostawaMagazyn = em.find(DostawaMagazyn.class, dostawaMagazyn.getIDdostawamagazyn());
            Dostawa IDdostawyOld = persistentDostawaMagazyn.getIDdostawy();
            Dostawa IDdostawyNew = dostawaMagazyn.getIDdostawy();
            MagazynLeki IDlekuOld = persistentDostawaMagazyn.getIDleku();
            MagazynLeki IDlekuNew = dostawaMagazyn.getIDleku();
            if (IDdostawyNew != null) {
                IDdostawyNew = em.getReference(IDdostawyNew.getClass(), IDdostawyNew.getIDdostawy());
                dostawaMagazyn.setIDdostawy(IDdostawyNew);
            }
            if (IDlekuNew != null) {
                IDlekuNew = em.getReference(IDlekuNew.getClass(), IDlekuNew.getIDleku());
                dostawaMagazyn.setIDleku(IDlekuNew);
            }
            dostawaMagazyn = em.merge(dostawaMagazyn);
            if (IDdostawyOld != null && !IDdostawyOld.equals(IDdostawyNew)) {
                IDdostawyOld.getDostawaMagazynCollection().remove(dostawaMagazyn);
                IDdostawyOld = em.merge(IDdostawyOld);
            }
            if (IDdostawyNew != null && !IDdostawyNew.equals(IDdostawyOld)) {
                IDdostawyNew.getDostawaMagazynCollection().add(dostawaMagazyn);
                IDdostawyNew = em.merge(IDdostawyNew);
            }
            if (IDlekuOld != null && !IDlekuOld.equals(IDlekuNew)) {
                IDlekuOld.getDostawaMagazynCollection().remove(dostawaMagazyn);
                IDlekuOld = em.merge(IDlekuOld);
            }
            if (IDlekuNew != null && !IDlekuNew.equals(IDlekuOld)) {
                IDlekuNew.getDostawaMagazynCollection().add(dostawaMagazyn);
                IDlekuNew = em.merge(IDlekuNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = dostawaMagazyn.getIDdostawamagazyn();
                if (findDostawaMagazyn(id) == null) {
                    throw new NonexistentEntityException("The dostawaMagazyn with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DostawaMagazyn dostawaMagazyn;
            try {
                dostawaMagazyn = em.getReference(DostawaMagazyn.class, id);
                dostawaMagazyn.getIDdostawamagazyn();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The dostawaMagazyn with id " + id + " no longer exists.", enfe);
            }
            Dostawa IDdostawy = dostawaMagazyn.getIDdostawy();
            if (IDdostawy != null) {
                IDdostawy.getDostawaMagazynCollection().remove(dostawaMagazyn);
                IDdostawy = em.merge(IDdostawy);
            }
            MagazynLeki IDleku = dostawaMagazyn.getIDleku();
            if (IDleku != null) {
                IDleku.getDostawaMagazynCollection().remove(dostawaMagazyn);
                IDleku = em.merge(IDleku);
            }
            em.remove(dostawaMagazyn);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DostawaMagazyn> findDostawaMagazynEntities() {
        return findDostawaMagazynEntities(true, -1, -1);
    }

    public List<DostawaMagazyn> findDostawaMagazynEntities(int maxResults, int firstResult) {
        return findDostawaMagazynEntities(false, maxResults, firstResult);
    }

    private List<DostawaMagazyn> findDostawaMagazynEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DostawaMagazyn.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public DostawaMagazyn findDostawaMagazyn(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DostawaMagazyn.class, id);
        } finally {
            em.close();
        }
    }

    public int getDostawaMagazynCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DostawaMagazyn> rt = cq.from(DostawaMagazyn.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
