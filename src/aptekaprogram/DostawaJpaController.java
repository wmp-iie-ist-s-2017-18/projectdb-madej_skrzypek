/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aptekaprogram;

import aptekaprogram.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Sebcio
 */
public class DostawaJpaController implements Serializable {

    public DostawaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Dostawa dostawa) {
        if (dostawa.getMagazynLekiCollection() == null) {
            dostawa.setMagazynLekiCollection(new ArrayList<MagazynLeki>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<MagazynLeki> attachedMagazynLekiCollection = new ArrayList<MagazynLeki>();
            for (MagazynLeki magazynLekiCollectionMagazynLekiToAttach : dostawa.getMagazynLekiCollection()) {
                magazynLekiCollectionMagazynLekiToAttach = em.getReference(magazynLekiCollectionMagazynLekiToAttach.getClass(), magazynLekiCollectionMagazynLekiToAttach.getIDleku());
                attachedMagazynLekiCollection.add(magazynLekiCollectionMagazynLekiToAttach);
            }
            dostawa.setMagazynLekiCollection(attachedMagazynLekiCollection);
            em.persist(dostawa);
            for (MagazynLeki magazynLekiCollectionMagazynLeki : dostawa.getMagazynLekiCollection()) {
                magazynLekiCollectionMagazynLeki.getDostawaCollection().add(dostawa);
                magazynLekiCollectionMagazynLeki = em.merge(magazynLekiCollectionMagazynLeki);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Dostawa dostawa) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Dostawa persistentDostawa = em.find(Dostawa.class, dostawa.getIDdostawy());
            Collection<MagazynLeki> magazynLekiCollectionOld = persistentDostawa.getMagazynLekiCollection();
            Collection<MagazynLeki> magazynLekiCollectionNew = dostawa.getMagazynLekiCollection();
            Collection<MagazynLeki> attachedMagazynLekiCollectionNew = new ArrayList<MagazynLeki>();
            for (MagazynLeki magazynLekiCollectionNewMagazynLekiToAttach : magazynLekiCollectionNew) {
                magazynLekiCollectionNewMagazynLekiToAttach = em.getReference(magazynLekiCollectionNewMagazynLekiToAttach.getClass(), magazynLekiCollectionNewMagazynLekiToAttach.getIDleku());
                attachedMagazynLekiCollectionNew.add(magazynLekiCollectionNewMagazynLekiToAttach);
            }
            magazynLekiCollectionNew = attachedMagazynLekiCollectionNew;
            dostawa.setMagazynLekiCollection(magazynLekiCollectionNew);
            dostawa = em.merge(dostawa);
            for (MagazynLeki magazynLekiCollectionOldMagazynLeki : magazynLekiCollectionOld) {
                if (!magazynLekiCollectionNew.contains(magazynLekiCollectionOldMagazynLeki)) {
                    magazynLekiCollectionOldMagazynLeki.getDostawaCollection().remove(dostawa);
                    magazynLekiCollectionOldMagazynLeki = em.merge(magazynLekiCollectionOldMagazynLeki);
                }
            }
            for (MagazynLeki magazynLekiCollectionNewMagazynLeki : magazynLekiCollectionNew) {
                if (!magazynLekiCollectionOld.contains(magazynLekiCollectionNewMagazynLeki)) {
                    magazynLekiCollectionNewMagazynLeki.getDostawaCollection().add(dostawa);
                    magazynLekiCollectionNewMagazynLeki = em.merge(magazynLekiCollectionNewMagazynLeki);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = dostawa.getIDdostawy();
                if (findDostawa(id) == null) {
                    throw new NonexistentEntityException("The dostawa with id " + id + " no longer exists.");
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
            Dostawa dostawa;
            try {
                dostawa = em.getReference(Dostawa.class, id);
                dostawa.getIDdostawy();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The dostawa with id " + id + " no longer exists.", enfe);
            }
            Collection<MagazynLeki> magazynLekiCollection = dostawa.getMagazynLekiCollection();
            for (MagazynLeki magazynLekiCollectionMagazynLeki : magazynLekiCollection) {
                magazynLekiCollectionMagazynLeki.getDostawaCollection().remove(dostawa);
                magazynLekiCollectionMagazynLeki = em.merge(magazynLekiCollectionMagazynLeki);
            }
            em.remove(dostawa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Dostawa> findDostawaEntities() {
        return findDostawaEntities(true, -1, -1);
    }

    public List<Dostawa> findDostawaEntities(int maxResults, int firstResult) {
        return findDostawaEntities(false, maxResults, firstResult);
    }

    private List<Dostawa> findDostawaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Dostawa.class));
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

    public Dostawa findDostawa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Dostawa.class, id);
        } finally {
            em.close();
        }
    }

    public int getDostawaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Dostawa> rt = cq.from(Dostawa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
