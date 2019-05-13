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
public class ReceptaJpaController implements Serializable {

    public ReceptaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Recepta recepta) {
        if (recepta.getMagazynLekiCollection() == null) {
            recepta.setMagazynLekiCollection(new ArrayList<MagazynLeki>());
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
            Collection<MagazynLeki> attachedMagazynLekiCollection = new ArrayList<MagazynLeki>();
            for (MagazynLeki magazynLekiCollectionMagazynLekiToAttach : recepta.getMagazynLekiCollection()) {
                magazynLekiCollectionMagazynLekiToAttach = em.getReference(magazynLekiCollectionMagazynLekiToAttach.getClass(), magazynLekiCollectionMagazynLekiToAttach.getIDleku());
                attachedMagazynLekiCollection.add(magazynLekiCollectionMagazynLekiToAttach);
            }
            recepta.setMagazynLekiCollection(attachedMagazynLekiCollection);
            em.persist(recepta);
            if (klientID != null) {
                klientID.getReceptaCollection().add(recepta);
                klientID = em.merge(klientID);
            }
            for (MagazynLeki magazynLekiCollectionMagazynLeki : recepta.getMagazynLekiCollection()) {
                magazynLekiCollectionMagazynLeki.getReceptaCollection().add(recepta);
                magazynLekiCollectionMagazynLeki = em.merge(magazynLekiCollectionMagazynLeki);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Recepta recepta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Recepta persistentRecepta = em.find(Recepta.class, recepta.getIDrecepty());
            Klient klientIDOld = persistentRecepta.getKlientID();
            Klient klientIDNew = recepta.getKlientID();
            Collection<MagazynLeki> magazynLekiCollectionOld = persistentRecepta.getMagazynLekiCollection();
            Collection<MagazynLeki> magazynLekiCollectionNew = recepta.getMagazynLekiCollection();
            if (klientIDNew != null) {
                klientIDNew = em.getReference(klientIDNew.getClass(), klientIDNew.getIDKlienta());
                recepta.setKlientID(klientIDNew);
            }
            Collection<MagazynLeki> attachedMagazynLekiCollectionNew = new ArrayList<MagazynLeki>();
            for (MagazynLeki magazynLekiCollectionNewMagazynLekiToAttach : magazynLekiCollectionNew) {
                magazynLekiCollectionNewMagazynLekiToAttach = em.getReference(magazynLekiCollectionNewMagazynLekiToAttach.getClass(), magazynLekiCollectionNewMagazynLekiToAttach.getIDleku());
                attachedMagazynLekiCollectionNew.add(magazynLekiCollectionNewMagazynLekiToAttach);
            }
            magazynLekiCollectionNew = attachedMagazynLekiCollectionNew;
            recepta.setMagazynLekiCollection(magazynLekiCollectionNew);
            recepta = em.merge(recepta);
            if (klientIDOld != null && !klientIDOld.equals(klientIDNew)) {
                klientIDOld.getReceptaCollection().remove(recepta);
                klientIDOld = em.merge(klientIDOld);
            }
            if (klientIDNew != null && !klientIDNew.equals(klientIDOld)) {
                klientIDNew.getReceptaCollection().add(recepta);
                klientIDNew = em.merge(klientIDNew);
            }
            for (MagazynLeki magazynLekiCollectionOldMagazynLeki : magazynLekiCollectionOld) {
                if (!magazynLekiCollectionNew.contains(magazynLekiCollectionOldMagazynLeki)) {
                    magazynLekiCollectionOldMagazynLeki.getReceptaCollection().remove(recepta);
                    magazynLekiCollectionOldMagazynLeki = em.merge(magazynLekiCollectionOldMagazynLeki);
                }
            }
            for (MagazynLeki magazynLekiCollectionNewMagazynLeki : magazynLekiCollectionNew) {
                if (!magazynLekiCollectionOld.contains(magazynLekiCollectionNewMagazynLeki)) {
                    magazynLekiCollectionNewMagazynLeki.getReceptaCollection().add(recepta);
                    magazynLekiCollectionNewMagazynLeki = em.merge(magazynLekiCollectionNewMagazynLeki);
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

    public void destroy(Integer id) throws NonexistentEntityException {
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
            Klient klientID = recepta.getKlientID();
            if (klientID != null) {
                klientID.getReceptaCollection().remove(recepta);
                klientID = em.merge(klientID);
            }
            Collection<MagazynLeki> magazynLekiCollection = recepta.getMagazynLekiCollection();
            for (MagazynLeki magazynLekiCollectionMagazynLeki : magazynLekiCollection) {
                magazynLekiCollectionMagazynLeki.getReceptaCollection().remove(recepta);
                magazynLekiCollectionMagazynLeki = em.merge(magazynLekiCollectionMagazynLeki);
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
    
}
