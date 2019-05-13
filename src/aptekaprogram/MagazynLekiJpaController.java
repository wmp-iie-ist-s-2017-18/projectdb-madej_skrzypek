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
public class MagazynLekiJpaController implements Serializable {

    public MagazynLekiJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MagazynLeki magazynLeki) {
        if (magazynLeki.getReceptaCollection() == null) {
            magazynLeki.setReceptaCollection(new ArrayList<Recepta>());
        }
        if (magazynLeki.getDostawaCollection() == null) {
            magazynLeki.setDostawaCollection(new ArrayList<Dostawa>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Recepta> attachedReceptaCollection = new ArrayList<Recepta>();
            for (Recepta receptaCollectionReceptaToAttach : magazynLeki.getReceptaCollection()) {
                receptaCollectionReceptaToAttach = em.getReference(receptaCollectionReceptaToAttach.getClass(), receptaCollectionReceptaToAttach.getIDrecepty());
                attachedReceptaCollection.add(receptaCollectionReceptaToAttach);
            }
            magazynLeki.setReceptaCollection(attachedReceptaCollection);
            Collection<Dostawa> attachedDostawaCollection = new ArrayList<Dostawa>();
            for (Dostawa dostawaCollectionDostawaToAttach : magazynLeki.getDostawaCollection()) {
                dostawaCollectionDostawaToAttach = em.getReference(dostawaCollectionDostawaToAttach.getClass(), dostawaCollectionDostawaToAttach.getIDdostawy());
                attachedDostawaCollection.add(dostawaCollectionDostawaToAttach);
            }
            magazynLeki.setDostawaCollection(attachedDostawaCollection);
            em.persist(magazynLeki);
            for (Recepta receptaCollectionRecepta : magazynLeki.getReceptaCollection()) {
                receptaCollectionRecepta.getMagazynLekiCollection().add(magazynLeki);
                receptaCollectionRecepta = em.merge(receptaCollectionRecepta);
            }
            for (Dostawa dostawaCollectionDostawa : magazynLeki.getDostawaCollection()) {
                dostawaCollectionDostawa.getMagazynLekiCollection().add(magazynLeki);
                dostawaCollectionDostawa = em.merge(dostawaCollectionDostawa);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MagazynLeki magazynLeki) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MagazynLeki persistentMagazynLeki = em.find(MagazynLeki.class, magazynLeki.getIDleku());
            Collection<Recepta> receptaCollectionOld = persistentMagazynLeki.getReceptaCollection();
            Collection<Recepta> receptaCollectionNew = magazynLeki.getReceptaCollection();
            Collection<Dostawa> dostawaCollectionOld = persistentMagazynLeki.getDostawaCollection();
            Collection<Dostawa> dostawaCollectionNew = magazynLeki.getDostawaCollection();
            Collection<Recepta> attachedReceptaCollectionNew = new ArrayList<Recepta>();
            for (Recepta receptaCollectionNewReceptaToAttach : receptaCollectionNew) {
                receptaCollectionNewReceptaToAttach = em.getReference(receptaCollectionNewReceptaToAttach.getClass(), receptaCollectionNewReceptaToAttach.getIDrecepty());
                attachedReceptaCollectionNew.add(receptaCollectionNewReceptaToAttach);
            }
            receptaCollectionNew = attachedReceptaCollectionNew;
            magazynLeki.setReceptaCollection(receptaCollectionNew);
            Collection<Dostawa> attachedDostawaCollectionNew = new ArrayList<Dostawa>();
            for (Dostawa dostawaCollectionNewDostawaToAttach : dostawaCollectionNew) {
                dostawaCollectionNewDostawaToAttach = em.getReference(dostawaCollectionNewDostawaToAttach.getClass(), dostawaCollectionNewDostawaToAttach.getIDdostawy());
                attachedDostawaCollectionNew.add(dostawaCollectionNewDostawaToAttach);
            }
            dostawaCollectionNew = attachedDostawaCollectionNew;
            magazynLeki.setDostawaCollection(dostawaCollectionNew);
            magazynLeki = em.merge(magazynLeki);
            for (Recepta receptaCollectionOldRecepta : receptaCollectionOld) {
                if (!receptaCollectionNew.contains(receptaCollectionOldRecepta)) {
                    receptaCollectionOldRecepta.getMagazynLekiCollection().remove(magazynLeki);
                    receptaCollectionOldRecepta = em.merge(receptaCollectionOldRecepta);
                }
            }
            for (Recepta receptaCollectionNewRecepta : receptaCollectionNew) {
                if (!receptaCollectionOld.contains(receptaCollectionNewRecepta)) {
                    receptaCollectionNewRecepta.getMagazynLekiCollection().add(magazynLeki);
                    receptaCollectionNewRecepta = em.merge(receptaCollectionNewRecepta);
                }
            }
            for (Dostawa dostawaCollectionOldDostawa : dostawaCollectionOld) {
                if (!dostawaCollectionNew.contains(dostawaCollectionOldDostawa)) {
                    dostawaCollectionOldDostawa.getMagazynLekiCollection().remove(magazynLeki);
                    dostawaCollectionOldDostawa = em.merge(dostawaCollectionOldDostawa);
                }
            }
            for (Dostawa dostawaCollectionNewDostawa : dostawaCollectionNew) {
                if (!dostawaCollectionOld.contains(dostawaCollectionNewDostawa)) {
                    dostawaCollectionNewDostawa.getMagazynLekiCollection().add(magazynLeki);
                    dostawaCollectionNewDostawa = em.merge(dostawaCollectionNewDostawa);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = magazynLeki.getIDleku();
                if (findMagazynLeki(id) == null) {
                    throw new NonexistentEntityException("The magazynLeki with id " + id + " no longer exists.");
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
            MagazynLeki magazynLeki;
            try {
                magazynLeki = em.getReference(MagazynLeki.class, id);
                magazynLeki.getIDleku();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The magazynLeki with id " + id + " no longer exists.", enfe);
            }
            Collection<Recepta> receptaCollection = magazynLeki.getReceptaCollection();
            for (Recepta receptaCollectionRecepta : receptaCollection) {
                receptaCollectionRecepta.getMagazynLekiCollection().remove(magazynLeki);
                receptaCollectionRecepta = em.merge(receptaCollectionRecepta);
            }
            Collection<Dostawa> dostawaCollection = magazynLeki.getDostawaCollection();
            for (Dostawa dostawaCollectionDostawa : dostawaCollection) {
                dostawaCollectionDostawa.getMagazynLekiCollection().remove(magazynLeki);
                dostawaCollectionDostawa = em.merge(dostawaCollectionDostawa);
            }
            em.remove(magazynLeki);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MagazynLeki> findMagazynLekiEntities() {
        return findMagazynLekiEntities(true, -1, -1);
    }

    public List<MagazynLeki> findMagazynLekiEntities(int maxResults, int firstResult) {
        return findMagazynLekiEntities(false, maxResults, firstResult);
    }

    private List<MagazynLeki> findMagazynLekiEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MagazynLeki.class));
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

    public MagazynLeki findMagazynLeki(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MagazynLeki.class, id);
        } finally {
            em.close();
        }
    }

    public int getMagazynLekiCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MagazynLeki> rt = cq.from(MagazynLeki.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
