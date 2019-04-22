/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metodos;

import java.util.ArrayList;
import modelo.Movimento;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import modelo.Produto;

/**
 *
 * @author Ussimane
 */
public class MoviementoCtr {

    public MoviementoCtr(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Movimento> getMovimento(Date i, Date f, Produto p) {
        EntityManager em = getEntityManager();
        if (p == null) {
            return new ArrayList<>();
        }
        try {
            Query q = em.createNativeQuery("select mov.* from (select e.data as data,'Entrada no Armazem' as tipomov, case when e.qntinicial is null then e.qtd else e.qntinicial end as qtd,e.preco as preco,"
                    + "e.qstock as stock,e.qpac as balcao,'' as estado,e.serie as lot"
                    + ", e.qa as qa, e.qb as qb, e.qc as qc, e.pb as pb, e.pc as pc, 0 as iva, e.utilizador as idd from vendas.entrada e"
                    + " where e.idproduto=? and e.data>=? and e.qtd>0 union"
                    + " select e.data as data,'Novo Preço' as tipomov, -1 as qtd,e.preco as preco,"
                    + "e.qstock as stock,e.qpac as balcao,'' as estado,e.serie as lot"
                    + ", e.qa as qa, e.qb as qb, e.qc as qc, e.pb as pb, e.pc as pc, 0 as iva, e.utilizador as idd from vendas.entrada e"
                    + " where e.idproduto=? and e.data>=? and e.dataref is not null and e.qtd = 0 union"
                    + " select s.data as data, t.descricao as tipomov, s.qtd as qtd,s.preco as preco,s.qstock as stock,s.qpac as balcao,'' as estado,e.serie as lot"
                    + ", s.qa as qa, s.qb as qb, s.qc as qc, s.pb as pb, s.pc as pc, 0 as iva, s.utilizador as idd from vendas.saida s, vendas.tiposaida t, vendas.entrada e "
                    + " where s.datae = e.data and t.idtiposaida = s.tiposaida and s.idproduto = ? and s.data>=? and s.obs is null union"
//                         " where s.idproduto = ? and s.data>=? and s.obs is not null union"
                    + " select v.datavenda as data,'Venda' as tipomov, v.qtd as qtd,v.prec as preco,v.qstock as stock,v.qpac as balcao,'' as estado,e.serie as lot"
                    + ", v.qc as qa, 0 as qb, 0 as qc, 0 as pb, 0 as pc, 0 as iva, v.idvendedor as idd from vendas.venda v, vendas.entrada e "
                    + " where v.datae = e.data and v.idproduto = ? and v.datavenda>=? and v.estado=0 "
                    + "union"
                    + " select v.datavenda as data,'Venda' as tipomov, v.qtd as qtd,v.prec as preco,v.qstock as stock,v.qpac as balcao,'Anulado' as estado,e.serie as lot"
                    + ", v.qc as qa, 0 as qb, 0 as qc, 0 as pb, 0 as pc, 0 as iva, v.idvendedor as idd from vendas.venda v, vendas.entrada e "
                    + " where v.datae = e.data and v.idproduto = ? and v.datavenda>=? and v.estado=2 "
                    + "union"
                    + " select v.datavenda as data,'Venda' as tipomov, v.qtd as qtd,v.prec as preco,v.qstock as stock,v.qpac as balcao,'Restaurado' as estado,e.serie as lot"
                    + ", v.qc as qa, 0 as qb, 0 as qc, 0 as pb, 0 as pc, 0 as iva, v.idvendedor as idd from vendas.venda v, vendas.entrada e "
                    + " where v.datae = e.data and v.idproduto = ? and v.datavenda>=? and v.estado=1 "
                    + "union"
                    + " select vp.dataemprestimo as data,'Venda Credito Não paga' as tipomov, vp.qtprod as qtd,vp.prec as preco,vp.qstock as stock,vp.qpac as balcao,'' as estado,e.serie as lot"
                    + ", vp.qc as qa, 0 as qb, 0 as qc, 0 as pb, 0 as pc, 0 as iva, vp.idvendedor as idd from vendas.emprestimo vp, vendas.entrada e "
                    + " where vp.datae = e.data and vp.idproduto = ? and vp.dataemprestimo>=?"
                    + ") as mov order by data", Movimento.class);
//            q.setParameter("i", i);
            //q.setParameter("p", new Integer(28));
            i.setHours(1);
            q.setParameter(1, p.getIdproduto());
            q.setParameter(2, i);
            q.setParameter(3, p.getIdproduto());
            q.setParameter(4, i);
            q.setParameter(5, p.getIdproduto());
            q.setParameter(6, i);
            q.setParameter(7, p.getIdproduto());
            q.setParameter(8, i);
            q.setParameter(9, p.getIdproduto());
            q.setParameter(10, i);
            q.setParameter(11, p.getIdproduto());
            q.setParameter(12, i);
            q.setParameter(13, p.getIdproduto());
            q.setParameter(14, i);
            q.setParameter(15, p.getIdproduto());
            q.setParameter(16, i);
            q.setParameter(17, p.getIdproduto());
            q.setParameter(18, i);
            q.setHint("eclipselink.refresh", true);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

}
