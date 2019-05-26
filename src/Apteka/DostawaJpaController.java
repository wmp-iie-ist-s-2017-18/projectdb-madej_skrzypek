/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Apteka;

import Apteka.exceptions.IllegalOrphanException;
import Apteka.exceptions.NonexistentEntityException;
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
        if (dostawa.getDostawaMagazynCollection() == null) {
            dostawa.setDostawaMagazynCollection(new ArrayList<DostawaMagazyn>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<DostawaMagazyn> attachedDostawaMagazynCollection = new ArrayList<DostawaMagazyn>();
            for (DostawaMagazyn dostawaMagazynCollectionDostawaMagazynToAttach : dostawa.getDostawaMagazynCollection()) {
                dostawaMagazynCollectionDostawaMagazynToAttach = em.getReference(dostawaMagazynCollectionDostawaMagazynToAttach.getClass(), dostawaMagazynCollectionDostawaMagazynToAttach.getIDdostawamagazyn());
                attachedDostawaMagazynCollection.add(dostawaMagazynCollectionDostawaMagazynToAttach);
            }
            dostawa.setDostawaMagazynCollection(attachedDostawaMagazynCollection);
            em.persist(dostawa);
            for (DostawaMagazyn dostawaMagazynCollectionDostawaMagazyn : dostawa.getDostawaMagazynCollection()) {
                Dostawa oldIDdostawyOfDostawaMagazynCollectionDostawaMagazyn = dostawaMagazynCollectionDostawaMagazyn.getIDdostawy();
                dostawaMagazynCollectionDostawaMagazyn.setIDdostawy(dostawa);
                dostawaMagazynCollectionDostawaMagazyn = em.merge(dostawaMagazynCollectionDostawaMagazyn);
                if (oldIDdostawyOfDostawaMagazynCollectionDostawaMagazyn != null) {
                    oldIDdostawyOfDostawaMagazynCollectionDostawaMagazyn.getDostawaMagazynCollection().remove(dostawaMagazynCollectionDostawaMagazyn);
                    oldIDdostawyOfDostawaMagazynCollectionDostawaMagazyn = em.merge(oldIDdostawyOfDostawaMagazynCollectionDostawaMagazyn);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Dostawa dostawa) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Dostawa persistentDostawa = em.find(Dostawa.class, dostawa.getIDdostawy());
            Collection<DostawaMagazyn> dostawaMagazynCollectionOld = persistentDostawa.getDostawaMagazynCollection();
            Collection<DostawaMagazyn> dostawaMagazynCollectionNew = dostawa.getDostawaMagazynCollection();
            List<String> illegalOrphanMessages = null;
            for (DostawaMagazyn dostawaMagazynCollectionOldDostawaMagazyn : dostawaMagazynCollectionOld) {
                if (!dostawaMagazynCollectionNew.contains(dostawaMagazynCollectionOldDostawaMagazyn)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DostawaMagazyn " + dostawaMagazynCollectionOldDostawaMagazyn + " since its IDdostawy field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<DostawaMagazyn> attachedDostawaMagazynCollectionNew = new ArrayList<DostawaMagazyn>();
            for (DostawaMagazyn dostawaMagazynCollectionNewDostawaMagazynToAttach : dostawaMagazynCollectionNew) {
                dostawaMagazynCollectionNewDostawaMagazynToAttach = em.getReference(dostawaMagazynCollectionNewDostawaMagazynToAttach.getClass(), dostawaMagazynCollectionNewDostawaMagazynToAttach.getIDdostawamagazyn());
                attachedDostawaMagazynCollectionNew.add(dostawaMagazynCollectionNewDostawaMagazynToAttach);
            }
            dostawaMagazynCollectionNew = attachedDostawaMagazynCollectionNew;
            dostawa.setDostawaMagazynCollection(dostawaMagazynCollectionNew);
            dostawa = em.merge(dostawa);
            for (DostawaMagazyn dostawaMagazynCollectionNewDostawaMagazyn : dostawaMagazynCollectionNew) {
                if (!dostawaMagazynCollectionOld.contains(dostawaMagazynCollectionNewDostawaMagazyn)) {
                    Dostawa oldIDdostawyOfDostawaMagazynCollectionNewDostawaMagazyn = dostawaMagazynCollectionNewDostawaMagazyn.getIDdostawy();
                    dostawaMagazynCollectionNewDostawaMagazyn.setIDdostawy(dostawa);
                    dostawaMagazynCollectionNewDostawaMagazyn = em.merge(dostawaMagazynCollectionNewDostawaMagazyn);
                    if (oldIDdostawyOfDostawaMagazynCollectionNewDostawaMagazyn != null && !oldIDdostawyOfDostawaMagazynCollectionNewDostawaMagazyn.equals(dostawa)) {
                        oldIDdostawyOfDostawaMagazynCollectionNewDostawaMagazyn.getDostawaMagazynCollection().remove(dostawaMagazynCollectionNewDostawaMagazyn);
                        oldIDdostawyOfDostawaMagazynCollectionNewDostawaMagazyn = em.merge(oldIDdostawyOfDostawaMagazynCollectionNewDostawaMagazyn);
                    }
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
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
            List<String> illegalOrphanMessages = null;
            Collection<DostawaMagazyn> dostawaMagazynCollectionOrphanCheck = dostawa.getDostawaMagazynCollection();
            for (DostawaMagazyn dostawaMagazynCollectionOrphanCheckDostawaMagazyn : dostawaMagazynCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Dostawa (" + dostawa + ") cannot be destroyed since the DostawaMagazyn " + dostawaMagazynCollectionOrphanCheckDostawaMagazyn + " in its dostawaMagazynCollection field has a non-nullable IDdostawy field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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
