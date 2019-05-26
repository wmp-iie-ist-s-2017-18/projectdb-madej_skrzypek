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
public class ReceptaMagazynJpaController implements Serializable {

    public ReceptaMagazynJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ReceptaMagazyn receptaMagazyn) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Recepta IDrecepty = receptaMagazyn.getIDrecepty();
            if (IDrecepty != null) {
                IDrecepty = em.getReference(IDrecepty.getClass(), IDrecepty.getIDrecepty());
                receptaMagazyn.setIDrecepty(IDrecepty);
            }
            MagazynLeki IDleku = receptaMagazyn.getIDleku();
            if (IDleku != null) {
                IDleku = em.getReference(IDleku.getClass(), IDleku.getIDleku());
                receptaMagazyn.setIDleku(IDleku);
            }
            em.persist(receptaMagazyn);
            if (IDrecepty != null) {
                IDrecepty.getReceptaMagazynCollection().add(receptaMagazyn);
                IDrecepty = em.merge(IDrecepty);
            }
            if (IDleku != null) {
                IDleku.getReceptaMagazynCollection().add(receptaMagazyn);
                IDleku = em.merge(IDleku);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ReceptaMagazyn receptaMagazyn) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ReceptaMagazyn persistentReceptaMagazyn = em.find(ReceptaMagazyn.class, receptaMagazyn.getIdreceptamagazyn());
            Recepta IDreceptyOld = persistentReceptaMagazyn.getIDrecepty();
            Recepta IDreceptyNew = receptaMagazyn.getIDrecepty();
            MagazynLeki IDlekuOld = persistentReceptaMagazyn.getIDleku();
            MagazynLeki IDlekuNew = receptaMagazyn.getIDleku();
            if (IDreceptyNew != null) {
                IDreceptyNew = em.getReference(IDreceptyNew.getClass(), IDreceptyNew.getIDrecepty());
                receptaMagazyn.setIDrecepty(IDreceptyNew);
            }
            if (IDlekuNew != null) {
                IDlekuNew = em.getReference(IDlekuNew.getClass(), IDlekuNew.getIDleku());
                receptaMagazyn.setIDleku(IDlekuNew);
            }
            receptaMagazyn = em.merge(receptaMagazyn);
            if (IDreceptyOld != null && !IDreceptyOld.equals(IDreceptyNew)) {
                IDreceptyOld.getReceptaMagazynCollection().remove(receptaMagazyn);
                IDreceptyOld = em.merge(IDreceptyOld);
            }
            if (IDreceptyNew != null && !IDreceptyNew.equals(IDreceptyOld)) {
                IDreceptyNew.getReceptaMagazynCollection().add(receptaMagazyn);
                IDreceptyNew = em.merge(IDreceptyNew);
            }
            if (IDlekuOld != null && !IDlekuOld.equals(IDlekuNew)) {
                IDlekuOld.getReceptaMagazynCollection().remove(receptaMagazyn);
                IDlekuOld = em.merge(IDlekuOld);
            }
            if (IDlekuNew != null && !IDlekuNew.equals(IDlekuOld)) {
                IDlekuNew.getReceptaMagazynCollection().add(receptaMagazyn);
                IDlekuNew = em.merge(IDlekuNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = receptaMagazyn.getIdreceptamagazyn();
                if (findReceptaMagazyn(id) == null) {
                    throw new NonexistentEntityException("The receptaMagazyn with id " + id + " no longer exists.");
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
            ReceptaMagazyn receptaMagazyn;
            try {
                receptaMagazyn = em.getReference(ReceptaMagazyn.class, id);
                receptaMagazyn.getIdreceptamagazyn();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The receptaMagazyn with id " + id + " no longer exists.", enfe);
            }
            Recepta IDrecepty = receptaMagazyn.getIDrecepty();
            if (IDrecepty != null) {
                IDrecepty.getReceptaMagazynCollection().remove(receptaMagazyn);
                IDrecepty = em.merge(IDrecepty);
            }
            MagazynLeki IDleku = receptaMagazyn.getIDleku();
            if (IDleku != null) {
                IDleku.getReceptaMagazynCollection().remove(receptaMagazyn);
                IDleku = em.merge(IDleku);
            }
            em.remove(receptaMagazyn);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ReceptaMagazyn> findReceptaMagazynEntities() {
        return findReceptaMagazynEntities(true, -1, -1);
    }

    public List<ReceptaMagazyn> findReceptaMagazynEntities(int maxResults, int firstResult) {
        return findReceptaMagazynEntities(false, maxResults, firstResult);
    }

    private List<ReceptaMagazyn> findReceptaMagazynEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ReceptaMagazyn.class));
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

    public ReceptaMagazyn findReceptaMagazyn(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ReceptaMagazyn.class, id);
        } finally {
            em.close();
        }
    }

    public int getReceptaMagazynCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ReceptaMagazyn> rt = cq.from(ReceptaMagazyn.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
