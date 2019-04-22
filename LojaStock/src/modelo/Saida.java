/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author USSIMANE
 */
@Entity
@Table(name = "saida", catalog = "vendas", schema = "vendas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Saida.findAll", query = "SELECT s FROM Saida s"),
    @NamedQuery(name = "Saida.findByIdproduto", query = "SELECT s FROM Saida s WHERE s.saidaPK.idproduto = :idproduto"),
    @NamedQuery(name = "Saida.findByQtd", query = "SELECT s FROM Saida s WHERE s.qtd = :qtd"),
    @NamedQuery(name = "Saida.findByData", query = "SELECT s FROM Saida s WHERE s.saidaPK.data = :data"),
    @NamedQuery(name = "Saida.findByQpac", query = "SELECT s FROM Saida s WHERE s.qpac = :qpac")})
public class Saida implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SaidaPK saidaPK;
    @Column(name = "qtd")
    private Integer qtd;
    @Column(name = "obs", length = 255)
    private String obs;
    @Column(name = "qpac")
    private Integer qpac;
    @Column(name = "qstock")
    private Integer qstock;
    @Column(name = "datae")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datae;
    @Column(name = "preco", precision = 8, scale = 8)
    private Float preco;
    @JoinColumn(name = "idproduto", referencedColumnName = "idproduto", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Produto produto;
    @JoinColumn(name = "tiposaida", referencedColumnName = "idtiposaida")
    @ManyToOne(fetch = FetchType.LAZY)
    private Tiposaida tiposaida;
    @JoinColumn(name = "utilizador", referencedColumnName = "idvendedor")
    @ManyToOne(fetch = FetchType.LAZY)
    private Vendedor utilizador;
      @Column(name = "na", length = 2147483647)
    private String na;
    @Column(name = "nb", length = 2147483647)
    private String nb;
    @Column(name = "pb", precision = 8, scale = 8)
    private Float pb;
    @Column(name = "nc", length = 2147483647)
    private String nc;
    @Column(name = "pc", precision = 8, scale = 8)
    private Float pc;
    @Column(name = "qa")
    private Integer qa;
    @Column(name = "qb")
    private Integer qb;
    @Column(name = "qc")
    private Integer qc;

    public Saida() {
    }

    public Saida(SaidaPK saidaPK) {
        this.saidaPK = saidaPK;
    }

    public Saida(int idproduto, Date data) {
        this.saidaPK = new SaidaPK(idproduto, data);
    }

    public SaidaPK getSaidaPK() {
        return saidaPK;
    }

    public void setSaidaPK(SaidaPK saidaPK) {
        this.saidaPK = saidaPK;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public Tiposaida getTiposaida() {
        return tiposaida;
    }

    public void setTiposaida(Tiposaida tiposaida) {
        this.tiposaida = tiposaida;
    }

    public Integer getQtd() {
        return qtd;
    }

    public void setQtd(Integer qtd) {
        this.qtd = qtd;
    }

    public Integer getQpac() {
        return qpac;
    }

    public Float getPreco() {
        return preco;
    }

    public void setPreco(Float preco) {
        this.preco = preco;
    }

    public void setQpac(Integer qpac) {
        this.qpac = qpac;
    }

    public Produto getProduto() {
        return produto;
    }

    public Integer getQstock() {
        return qstock;
    }

    public void setQstock(Integer qstock) {
        this.qstock = qstock;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Vendedor getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(Vendedor utilizador) {
        this.utilizador = utilizador;
    }

    public String getNa() {
        return na;
    }

    public void setNa(String na) {
        this.na = na;
    }

    public String getNb() {
        return nb;
    }

    public void setNb(String nb) {
        this.nb = nb;
    }

    public Float getPb() {
        return pb;
    }

    public void setPb(Float pb) {
        this.pb = pb;
    }

    public String getNc() {
        return nc;
    }

    public void setNc(String nc) {
        this.nc = nc;
    }

    public Float getPc() {
        return pc;
    }

    public void setPc(Float pc) {
        this.pc = pc;
    }

    public Integer getQa() {
        return qa;
    }

    public void setQa(Integer qa) {
        this.qa = qa;
    }

    public Integer getQb() {
        return qb;
    }

    public void setQb(Integer qb) {
        this.qb = qb;
    }

    public Integer getQc() {
        return qc;
    }

    public void setQc(Integer qc) {
        this.qc = qc;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (saidaPK != null ? saidaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Saida)) {
            return false;
        }
        Saida other = (Saida) object;
        if ((this.saidaPK == null && other.saidaPK != null) || (this.saidaPK != null && !this.saidaPK.equals(other.saidaPK))) {
            return false;
        }
        return true;
    }

    public Date getDatae() {
        return datae;
    }

    public void setDatae(Date datae) {
        this.datae = datae;
    }

    @Override
    public String toString() {
        return new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(saidaPK.getData());
    }

}
