/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Cliente;
import modelo.Fproforma;
import modelo.Produto;
import modelo.Venda;
import modelo.Vendedor;

/**
 *
 * @author Ussimane
 */
public class FproformaJpaController implements Serializable {

    public FproformaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Fproforma fproforma) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente idcliente = fproforma.getIdcliente();
            if (idcliente != null) {
                idcliente = em.getReference(idcliente.getClass(), idcliente.getIdcliente());
                fproforma.setIdcliente(idcliente);
            }
            Produto idproduto = fproforma.getIdproduto();
            if (idproduto != null) {
                idproduto = em.getReference(idproduto.getClass(), idproduto.getIdproduto());
                fproforma.setIdproduto(idproduto);
            }
            Vendedor idvendedor = fproforma.getIdvendedor();
            if (idvendedor != null) {
                idvendedor = em.getReference(idvendedor.getClass(), idvendedor.getIdvendedor());
                fproforma.setIdvendedor(idvendedor);
            }
            em.persist(fproforma);
            if (idcliente != null) {
                idcliente.getFproformaList().add(fproforma);
                idcliente = em.merge(idcliente);
            }
            if (idproduto != null) {
                idproduto.getFproformaList().add(fproforma);
                idproduto = em.merge(idproduto);
            }
            if (idvendedor != null) {
                idvendedor.getFproformaList().add(fproforma);
                idvendedor = em.merge(idvendedor);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Fproforma fproforma) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Fproforma persistentFproforma = em.find(Fproforma.class, fproforma.getIdvenda());
            Cliente idclienteOld = persistentFproforma.getIdcliente();
            Cliente idclienteNew = fproforma.getIdcliente();
            Produto idprodutoOld = persistentFproforma.getIdproduto();
            Produto idprodutoNew = fproforma.getIdproduto();
            Vendedor idvendedorOld = persistentFproforma.getIdvendedor();
            Vendedor idvendedorNew = fproforma.getIdvendedor();
            if (idclienteNew != null) {
                idclienteNew = em.getReference(idclienteNew.getClass(), idclienteNew.getIdcliente());
                fproforma.setIdcliente(idclienteNew);
            }
            if (idprodutoNew != null) {
                idprodutoNew = em.getReference(idprodutoNew.getClass(), idprodutoNew.getIdproduto());
                fproforma.setIdproduto(idprodutoNew);
            }
            if (idvendedorNew != null) {
                idvendedorNew = em.getReference(idvendedorNew.getClass(), idvendedorNew.getIdvendedor());
                fproforma.setIdvendedor(idvendedorNew);
            }
            fproforma = em.merge(fproforma);
            if (idclienteOld != null && !idclienteOld.equals(idclienteNew)) {
                idclienteOld.getFproformaList().remove(fproforma);
                idclienteOld = em.merge(idclienteOld);
            }
            if (idclienteNew != null && !idclienteNew.equals(idclienteOld)) {
                idclienteNew.getFproformaList().add(fproforma);
                idclienteNew = em.merge(idclienteNew);
            }
            if (idprodutoOld != null && !idprodutoOld.equals(idprodutoNew)) {
                idprodutoOld.getFproformaList().remove(fproforma);
                idprodutoOld = em.merge(idprodutoOld);
            }
            if (idprodutoNew != null && !idprodutoNew.equals(idprodutoOld)) {
                idprodutoNew.getFproformaList().add(fproforma);
                idprodutoNew = em.merge(idprodutoNew);
            }
            if (idvendedorOld != null && !idvendedorOld.equals(idvendedorNew)) {
                idvendedorOld.getFproformaList().remove(fproforma);
                idvendedorOld = em.merge(idvendedorOld);
            }
            if (idvendedorNew != null && !idvendedorNew.equals(idvendedorOld)) {
                idvendedorNew.getFproformaList().add(fproforma);
                idvendedorNew = em.merge(idvendedorNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = fproforma.getIdvenda();
                if (findFproforma(id) == null) {
                    throw new NonexistentEntityException("The fproforma with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Fproforma fproforma;
            try {
                fproforma = em.getReference(Fproforma.class, id);
                fproforma.getIdvenda();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fproforma with id " + id + " no longer exists.", enfe);
            }
            Cliente idcliente = fproforma.getIdcliente();
            if (idcliente != null) {
                idcliente.getFproformaList().remove(fproforma);
                idcliente = em.merge(idcliente);
            }
            Produto idproduto = fproforma.getIdproduto();
            if (idproduto != null) {
                idproduto.getFproformaList().remove(fproforma);
                idproduto = em.merge(idproduto);
            }
            Vendedor idvendedor = fproforma.getIdvendedor();
            if (idvendedor != null) {
                idvendedor.getFproformaList().remove(fproforma);
                idvendedor = em.merge(idvendedor);
            }
            em.remove(fproforma);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Fproforma> findFproformaEntities() {
        return findFproformaEntities(true, -1, -1);
    }

    public List<Fproforma> findFproformaEntities(int maxResults, int firstResult) {
        return findFproformaEntities(false, maxResults, firstResult);
    }

    private List<Fproforma> findFproformaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Fproforma.class));
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

    public Fproforma findFproforma(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Fproforma.class, id);
        } finally {
            em.close();
        }
    }

    public int getFproformaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Fproforma> rt = cq.from(Fproforma.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Fproforma> getProformaPeriodo(Date i, Date f, Object ve) {
        EntityManager em = getEntityManager();
        Query q = null;
        try {
            if (ve.equals("Todos")) {
                if (f == null) {
                    q = em.createQuery("from Fproforma v where v.datavenda >= :i");
                    i.setHours(1);
                    q.setParameter("i", i);
                } else {
                    q = em.createQuery("from Fproforma v where v.datavenda >= :i and v.datavenda <= :f");
                    i.setHours(1);
                    f.setHours(23);
                    q.setParameter("i", i);
                    q.setParameter("f", f);
                }
            } else {
                if (f == null) {
                    q = em.createQuery("from Fproforma v where v.datavenda >= :i and v.idvendedor = :ve");
                    i.setHours(1);
                    q.setParameter("i", i);
                    q.setParameter("ve", ve);
                } else {
                    q = em.createQuery("from Fproforma v where v.datavenda >= :i and v.datavenda <= :f and v.idvendedor = :ve");
                    i.setHours(1);
                    f.setHours(23);
                    q.setParameter("i", i);
                    q.setParameter("f", f);
                    q.setParameter("ve", ve);
                }
            }
            q.setHint("eclipselink.refresh", true);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
    
    public List<Fproforma> getFacturaLike(String n) {
        String nn = "%" + n.toLowerCase() + "%";
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("from Fproforma p where p.idvenda = :i or lower(p.nome) like :n "
                    + "order by p.nome asc");
            q.setParameter("n", nn);
            q.setParameter("i", n);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Fproforma> getProforma(Date i) {
        EntityManager em = getEntityManager();
        Query q;
        try {
            q = em.createQuery("from Fproforma v where v.datavenda = :i order by v.datae asc");
            q.setParameter("i", i);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
}
