/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aptekaprogram;

import aptekaprogram.exceptions.IllegalOrphanException;
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
public class KlientJpaController implements Serializable {

    public KlientJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Klient klient) {
        if (klient.getReceptaCollection() == null) {
            klient.setReceptaCollection(new ArrayList<Recepta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Recepta> attachedReceptaCollection = new ArrayList<Recepta>();
            for (Recepta receptaCollectionReceptaToAttach : klient.getReceptaCollection()) {
                receptaCollectionReceptaToAttach = em.getReference(receptaCollectionReceptaToAttach.getClass(), receptaCollectionReceptaToAttach.getIDrecepty());
                attachedReceptaCollection.add(receptaCollectionReceptaToAttach);
            }
            klient.setReceptaCollection(attachedReceptaCollection);
            em.persist(klient);
            for (Recepta receptaCollectionRecepta : klient.getReceptaCollection()) {
                Klient oldKlientIDOfReceptaCollectionRecepta = receptaCollectionRecepta.getKlientID();
                receptaCollectionRecepta.setKlientID(klient);
                receptaCollectionRecepta = em.merge(receptaCollectionRecepta);
                if (oldKlientIDOfReceptaCollectionRecepta != null) {
                    oldKlientIDOfReceptaCollectionRecepta.getReceptaCollection().remove(receptaCollectionRecepta);
                    oldKlientIDOfReceptaCollectionRecepta = em.merge(oldKlientIDOfReceptaCollectionRecepta);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Klient klient) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Klient persistentKlient = em.find(Klient.class, klient.getIDKlienta());
            Collection<Recepta> receptaCollectionOld = persistentKlient.getReceptaCollection();
            Collection<Recepta> receptaCollectionNew = klient.getReceptaCollection();
            List<String> illegalOrphanMessages = null;
            for (Recepta receptaCollectionOldRecepta : receptaCollectionOld) {
                if (!receptaCollectionNew.contains(receptaCollectionOldRecepta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Recepta " + receptaCollectionOldRecepta + " since its klientID field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Recepta> attachedReceptaCollectionNew = new ArrayList<Recepta>();
            for (Recepta receptaCollectionNewReceptaToAttach : receptaCollectionNew) {
                receptaCollectionNewReceptaToAttach = em.getReference(receptaCollectionNewReceptaToAttach.getClass(), receptaCollectionNewReceptaToAttach.getIDrecepty());
                attachedReceptaCollectionNew.add(receptaCollectionNewReceptaToAttach);
            }
            receptaCollectionNew = attachedReceptaCollectionNew;
            klient.setReceptaCollection(receptaCollectionNew);
            klient = em.merge(klient);
            for (Recepta receptaCollectionNewRecepta : receptaCollectionNew) {
                if (!receptaCollectionOld.contains(receptaCollectionNewRecepta)) {
                    Klient oldKlientIDOfReceptaCollectionNewRecepta = receptaCollectionNewRecepta.getKlientID();
                    receptaCollectionNewRecepta.setKlientID(klient);
                    receptaCollectionNewRecepta = em.merge(receptaCollectionNewRecepta);
                    if (oldKlientIDOfReceptaCollectionNewRecepta != null && !oldKlientIDOfReceptaCollectionNewRecepta.equals(klient)) {
                        oldKlientIDOfReceptaCollectionNewRecepta.getReceptaCollection().remove(receptaCollectionNewRecepta);
                        oldKlientIDOfReceptaCollectionNewRecepta = em.merge(oldKlientIDOfReceptaCollectionNewRecepta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = klient.getIDKlienta();
                if (findKlient(id) == null) {
                    throw new NonexistentEntityException("The klient with id " + id + " no longer exists.");
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
            Klient klient;
            try {
                klient = em.getReference(Klient.class, id);
                klient.getIDKlienta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The klient with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Recepta> receptaCollectionOrphanCheck = klient.getReceptaCollection();
            for (Recepta receptaCollectionOrphanCheckRecepta : receptaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Klient (" + klient + ") cannot be destroyed since the Recepta " + receptaCollectionOrphanCheckRecepta + " in its receptaCollection field has a non-nullable klientID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(klient);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Klient> findKlientEntities() {
        return findKlientEntities(true, -1, -1);
    }

    public List<Klient> findKlientEntities(int maxResults, int firstResult) {
        return findKlientEntities(false, maxResults, firstResult);
    }

    private List<Klient> findKlientEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Klient.class));
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

    public Klient findKlient(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Klient.class, id);
        } finally {
            em.close();
        }
    }

    public int getKlientCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Klient> rt = cq.from(Klient.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
