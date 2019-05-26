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
public class ReceptaJpaController implements Serializable {

    public ReceptaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Recepta recepta) {
        if (recepta.getReceptaMagazynCollection() == null) {
            recepta.setReceptaMagazynCollection(new ArrayList<ReceptaMagazyn>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Klient klientID = recepta.getKlientID();
            if (klientID != null) {
                klientID = em.getReference(klientID.getClass(), klientID.getIDKlienta());
                recepta.setKlientID(klientID);
            }
            Collection<ReceptaMagazyn> attachedReceptaMagazynCollection = new ArrayList<ReceptaMagazyn>();
            for (ReceptaMagazyn receptaMagazynCollectionReceptaMagazynToAttach : recepta.getReceptaMagazynCollection()) {
                receptaMagazynCollectionReceptaMagazynToAttach = em.getReference(receptaMagazynCollectionReceptaMagazynToAttach.getClass(), receptaMagazynCollectionReceptaMagazynToAttach.getIdreceptamagazyn());
                attachedReceptaMagazynCollection.add(receptaMagazynCollectionReceptaMagazynToAttach);
            }
            recepta.setReceptaMagazynCollection(attachedReceptaMagazynCollection);
            em.persist(recepta);
            if (klientID != null) {
                klientID.getReceptaCollection().add(recepta);
                klientID = em.merge(klientID);
            }
            for (ReceptaMagazyn receptaMagazynCollectionReceptaMagazyn : recepta.getReceptaMagazynCollection()) {
                Recepta oldIDreceptyOfReceptaMagazynCollectionReceptaMagazyn = receptaMagazynCollectionReceptaMagazyn.getIDrecepty();
                receptaMagazynCollectionReceptaMagazyn.setIDrecepty(recepta);
                receptaMagazynCollectionReceptaMagazyn = em.merge(receptaMagazynCollectionReceptaMagazyn);
                if (oldIDreceptyOfReceptaMagazynCollectionReceptaMagazyn != null) {
                    oldIDreceptyOfReceptaMagazynCollectionReceptaMagazyn.getReceptaMagazynCollection().remove(receptaMagazynCollectionReceptaMagazyn);
                    oldIDreceptyOfReceptaMagazynCollectionReceptaMagazyn = em.merge(oldIDreceptyOfReceptaMagazynCollectionReceptaMagazyn);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Recepta recepta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Recepta persistentRecepta = em.find(Recepta.class, recepta.getIDrecepty());
            Klient klientIDOld = persistentRecepta.getKlientID();
            Klient klientIDNew = recepta.getKlientID();
            Collection<ReceptaMagazyn> receptaMagazynCollectionOld = persistentRecepta.getReceptaMagazynCollection();
            Collection<ReceptaMagazyn> receptaMagazynCollectionNew = recepta.getReceptaMagazynCollection();
            List<String> illegalOrphanMessages = null;
            for (ReceptaMagazyn receptaMagazynCollectionOldReceptaMagazyn : receptaMagazynCollectionOld) {
                if (!receptaMagazynCollectionNew.contains(receptaMagazynCollectionOldReceptaMagazyn)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ReceptaMagazyn " + receptaMagazynCollectionOldReceptaMagazyn + " since its IDrecepty field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (klientIDNew != null) {
                klientIDNew = em.getReference(klientIDNew.getClass(), klientIDNew.getIDKlienta());
                recepta.setKlientID(klientIDNew);
            }
            Collection<ReceptaMagazyn> attachedReceptaMagazynCollectionNew = new ArrayList<ReceptaMagazyn>();
            for (ReceptaMagazyn receptaMagazynCollectionNewReceptaMagazynToAttach : receptaMagazynCollectionNew) {
                receptaMagazynCollectionNewReceptaMagazynToAttach = em.getReference(receptaMagazynCollectionNewReceptaMagazynToAttach.getClass(), receptaMagazynCollectionNewReceptaMagazynToAttach.getIdreceptamagazyn());
                attachedReceptaMagazynCollectionNew.add(receptaMagazynCollectionNewReceptaMagazynToAttach);
            }
            receptaMagazynCollectionNew = attachedReceptaMagazynCollectionNew;
            recepta.setReceptaMagazynCollection(receptaMagazynCollectionNew);
            recepta = em.merge(recepta);
            if (klientIDOld != null && !klientIDOld.equals(klientIDNew)) {
                klientIDOld.getReceptaCollection().remove(recepta);
                klientIDOld = em.merge(klientIDOld);
            }
            if (klientIDNew != null && !klientIDNew.equals(klientIDOld)) {
                klientIDNew.getReceptaCollection().add(recepta);
                klientIDNew = em.merge(klientIDNew);
            }
            for (ReceptaMagazyn receptaMagazynCollectionNewReceptaMagazyn : receptaMagazynCollectionNew) {
                if (!receptaMagazynCollectionOld.contains(receptaMagazynCollectionNewReceptaMagazyn)) {
                    Recepta oldIDreceptyOfReceptaMagazynCollectionNewReceptaMagazyn = receptaMagazynCollectionNewReceptaMagazyn.getIDrecepty();
                    receptaMagazynCollectionNewReceptaMagazyn.setIDrecepty(recepta);
                    receptaMagazynCollectionNewReceptaMagazyn = em.merge(receptaMagazynCollectionNewReceptaMagazyn);
                    if (oldIDreceptyOfReceptaMagazynCollectionNewReceptaMagazyn != null && !oldIDreceptyOfReceptaMagazynCollectionNewReceptaMagazyn.equals(recepta)) {
                        oldIDreceptyOfReceptaMagazynCollectionNewReceptaMagazyn.getReceptaMagazynCollection().remove(receptaMagazynCollectionNewReceptaMagazyn);
                        oldIDreceptyOfReceptaMagazynCollectionNewReceptaMagazyn = em.merge(oldIDreceptyOfReceptaMagazynCollectionNewReceptaMagazyn);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = recepta.getIDrecepty();
                if (findRecepta(id) == null) {
                    throw new NonexistentEntityException("The recepta with id " + id + " no longer exists.");
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
            Recepta recepta;
            try {
                recepta = em.getReference(Recepta.class, id);
                recepta.getIDrecepty();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The recepta with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ReceptaMagazyn> receptaMagazynCollectionOrphanCheck = recepta.getReceptaMagazynCollection();
            for (ReceptaMagazyn receptaMagazynCollectionOrphanCheckReceptaMagazyn : receptaMagazynCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Recepta (" + recepta + ") cannot be destroyed since the ReceptaMagazyn " + receptaMagazynCollectionOrphanCheckReceptaMagazyn + " in its receptaMagazynCollection field has a non-nullable IDrecepty field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Klient klientID = recepta.getKlientID();
            if (klientID != null) {
                klientID.getReceptaCollection().remove(recepta);
                klientID = em.merge(klientID);
            }
            em.remove(recepta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Recepta> findReceptaEntities() {
        return findReceptaEntities(true, -1, -1);
    }

    public List<Recepta> findReceptaEntities(int maxResults, int firstResult) {
        return findReceptaEntities(false, maxResults, firstResult);
    }

    private List<Recepta> findReceptaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Recepta.class));
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

    public Recepta findRecepta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Recepta.class, id);
        } finally {
            em.close();
        }
    }

    public int getReceptaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Recepta> rt = cq.from(Recepta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Recepta> getReceptaByKlient(Klient klient){
        EntityManager em = getEntityManager();
        Query q1 = em.createQuery("SELECT b FROM Recepta b WHERE b.klientID = :klient");
        q1.setParameter("klient", klient);
        return q1.getResultList();
    }
}
