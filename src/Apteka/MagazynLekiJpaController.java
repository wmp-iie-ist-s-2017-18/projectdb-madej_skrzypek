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
public class MagazynLekiJpaController implements Serializable {

    public MagazynLekiJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MagazynLeki magazynLeki) {
        if (magazynLeki.getDostawaMagazynCollection() == null) {
            magazynLeki.setDostawaMagazynCollection(new ArrayList<DostawaMagazyn>());
        }
        if (magazynLeki.getReceptaMagazynCollection() == null) {
            magazynLeki.setReceptaMagazynCollection(new ArrayList<ReceptaMagazyn>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<DostawaMagazyn> attachedDostawaMagazynCollection = new ArrayList<DostawaMagazyn>();
            for (DostawaMagazyn dostawaMagazynCollectionDostawaMagazynToAttach : magazynLeki.getDostawaMagazynCollection()) {
                dostawaMagazynCollectionDostawaMagazynToAttach = em.getReference(dostawaMagazynCollectionDostawaMagazynToAttach.getClass(), dostawaMagazynCollectionDostawaMagazynToAttach.getIDdostawamagazyn());
                attachedDostawaMagazynCollection.add(dostawaMagazynCollectionDostawaMagazynToAttach);
            }
            magazynLeki.setDostawaMagazynCollection(attachedDostawaMagazynCollection);
            Collection<ReceptaMagazyn> attachedReceptaMagazynCollection = new ArrayList<ReceptaMagazyn>();
            for (ReceptaMagazyn receptaMagazynCollectionReceptaMagazynToAttach : magazynLeki.getReceptaMagazynCollection()) {
                receptaMagazynCollectionReceptaMagazynToAttach = em.getReference(receptaMagazynCollectionReceptaMagazynToAttach.getClass(), receptaMagazynCollectionReceptaMagazynToAttach.getIdreceptamagazyn());
                attachedReceptaMagazynCollection.add(receptaMagazynCollectionReceptaMagazynToAttach);
            }
            magazynLeki.setReceptaMagazynCollection(attachedReceptaMagazynCollection);
            em.persist(magazynLeki);
            for (DostawaMagazyn dostawaMagazynCollectionDostawaMagazyn : magazynLeki.getDostawaMagazynCollection()) {
                MagazynLeki oldIDlekuOfDostawaMagazynCollectionDostawaMagazyn = dostawaMagazynCollectionDostawaMagazyn.getIDleku();
                dostawaMagazynCollectionDostawaMagazyn.setIDleku(magazynLeki);
                dostawaMagazynCollectionDostawaMagazyn = em.merge(dostawaMagazynCollectionDostawaMagazyn);
                if (oldIDlekuOfDostawaMagazynCollectionDostawaMagazyn != null) {
                    oldIDlekuOfDostawaMagazynCollectionDostawaMagazyn.getDostawaMagazynCollection().remove(dostawaMagazynCollectionDostawaMagazyn);
                    oldIDlekuOfDostawaMagazynCollectionDostawaMagazyn = em.merge(oldIDlekuOfDostawaMagazynCollectionDostawaMagazyn);
                }
            }
            for (ReceptaMagazyn receptaMagazynCollectionReceptaMagazyn : magazynLeki.getReceptaMagazynCollection()) {
                MagazynLeki oldIDlekuOfReceptaMagazynCollectionReceptaMagazyn = receptaMagazynCollectionReceptaMagazyn.getIDleku();
                receptaMagazynCollectionReceptaMagazyn.setIDleku(magazynLeki);
                receptaMagazynCollectionReceptaMagazyn = em.merge(receptaMagazynCollectionReceptaMagazyn);
                if (oldIDlekuOfReceptaMagazynCollectionReceptaMagazyn != null) {
                    oldIDlekuOfReceptaMagazynCollectionReceptaMagazyn.getReceptaMagazynCollection().remove(receptaMagazynCollectionReceptaMagazyn);
                    oldIDlekuOfReceptaMagazynCollectionReceptaMagazyn = em.merge(oldIDlekuOfReceptaMagazynCollectionReceptaMagazyn);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MagazynLeki magazynLeki) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MagazynLeki persistentMagazynLeki = em.find(MagazynLeki.class, magazynLeki.getIDleku());
            Collection<DostawaMagazyn> dostawaMagazynCollectionOld = persistentMagazynLeki.getDostawaMagazynCollection();
            Collection<DostawaMagazyn> dostawaMagazynCollectionNew = magazynLeki.getDostawaMagazynCollection();
            Collection<ReceptaMagazyn> receptaMagazynCollectionOld = persistentMagazynLeki.getReceptaMagazynCollection();
            Collection<ReceptaMagazyn> receptaMagazynCollectionNew = magazynLeki.getReceptaMagazynCollection();
            List<String> illegalOrphanMessages = null;
            for (DostawaMagazyn dostawaMagazynCollectionOldDostawaMagazyn : dostawaMagazynCollectionOld) {
                if (!dostawaMagazynCollectionNew.contains(dostawaMagazynCollectionOldDostawaMagazyn)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DostawaMagazyn " + dostawaMagazynCollectionOldDostawaMagazyn + " since its IDleku field is not nullable.");
                }
            }
            for (ReceptaMagazyn receptaMagazynCollectionOldReceptaMagazyn : receptaMagazynCollectionOld) {
                if (!receptaMagazynCollectionNew.contains(receptaMagazynCollectionOldReceptaMagazyn)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ReceptaMagazyn " + receptaMagazynCollectionOldReceptaMagazyn + " since its IDleku field is not nullable.");
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
            magazynLeki.setDostawaMagazynCollection(dostawaMagazynCollectionNew);
            Collection<ReceptaMagazyn> attachedReceptaMagazynCollectionNew = new ArrayList<ReceptaMagazyn>();
            for (ReceptaMagazyn receptaMagazynCollectionNewReceptaMagazynToAttach : receptaMagazynCollectionNew) {
                receptaMagazynCollectionNewReceptaMagazynToAttach = em.getReference(receptaMagazynCollectionNewReceptaMagazynToAttach.getClass(), receptaMagazynCollectionNewReceptaMagazynToAttach.getIdreceptamagazyn());
                attachedReceptaMagazynCollectionNew.add(receptaMagazynCollectionNewReceptaMagazynToAttach);
            }
            receptaMagazynCollectionNew = attachedReceptaMagazynCollectionNew;
            magazynLeki.setReceptaMagazynCollection(receptaMagazynCollectionNew);
            magazynLeki = em.merge(magazynLeki);
            for (DostawaMagazyn dostawaMagazynCollectionNewDostawaMagazyn : dostawaMagazynCollectionNew) {
                if (!dostawaMagazynCollectionOld.contains(dostawaMagazynCollectionNewDostawaMagazyn)) {
                    MagazynLeki oldIDlekuOfDostawaMagazynCollectionNewDostawaMagazyn = dostawaMagazynCollectionNewDostawaMagazyn.getIDleku();
                    dostawaMagazynCollectionNewDostawaMagazyn.setIDleku(magazynLeki);
                    dostawaMagazynCollectionNewDostawaMagazyn = em.merge(dostawaMagazynCollectionNewDostawaMagazyn);
                    if (oldIDlekuOfDostawaMagazynCollectionNewDostawaMagazyn != null && !oldIDlekuOfDostawaMagazynCollectionNewDostawaMagazyn.equals(magazynLeki)) {
                        oldIDlekuOfDostawaMagazynCollectionNewDostawaMagazyn.getDostawaMagazynCollection().remove(dostawaMagazynCollectionNewDostawaMagazyn);
                        oldIDlekuOfDostawaMagazynCollectionNewDostawaMagazyn = em.merge(oldIDlekuOfDostawaMagazynCollectionNewDostawaMagazyn);
                    }
                }
            }
            for (ReceptaMagazyn receptaMagazynCollectionNewReceptaMagazyn : receptaMagazynCollectionNew) {
                if (!receptaMagazynCollectionOld.contains(receptaMagazynCollectionNewReceptaMagazyn)) {
                    MagazynLeki oldIDlekuOfReceptaMagazynCollectionNewReceptaMagazyn = receptaMagazynCollectionNewReceptaMagazyn.getIDleku();
                    receptaMagazynCollectionNewReceptaMagazyn.setIDleku(magazynLeki);
                    receptaMagazynCollectionNewReceptaMagazyn = em.merge(receptaMagazynCollectionNewReceptaMagazyn);
                    if (oldIDlekuOfReceptaMagazynCollectionNewReceptaMagazyn != null && !oldIDlekuOfReceptaMagazynCollectionNewReceptaMagazyn.equals(magazynLeki)) {
                        oldIDlekuOfReceptaMagazynCollectionNewReceptaMagazyn.getReceptaMagazynCollection().remove(receptaMagazynCollectionNewReceptaMagazyn);
                        oldIDlekuOfReceptaMagazynCollectionNewReceptaMagazyn = em.merge(oldIDlekuOfReceptaMagazynCollectionNewReceptaMagazyn);
                    }
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
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
            List<String> illegalOrphanMessages = null;
            Collection<DostawaMagazyn> dostawaMagazynCollectionOrphanCheck = magazynLeki.getDostawaMagazynCollection();
            for (DostawaMagazyn dostawaMagazynCollectionOrphanCheckDostawaMagazyn : dostawaMagazynCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This MagazynLeki (" + magazynLeki + ") cannot be destroyed since the DostawaMagazyn " + dostawaMagazynCollectionOrphanCheckDostawaMagazyn + " in its dostawaMagazynCollection field has a non-nullable IDleku field.");
            }
            Collection<ReceptaMagazyn> receptaMagazynCollectionOrphanCheck = magazynLeki.getReceptaMagazynCollection();
            for (ReceptaMagazyn receptaMagazynCollectionOrphanCheckReceptaMagazyn : receptaMagazynCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This MagazynLeki (" + magazynLeki + ") cannot be destroyed since the ReceptaMagazyn " + receptaMagazynCollectionOrphanCheckReceptaMagazyn + " in its receptaMagazynCollection field has a non-nullable IDleku field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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
