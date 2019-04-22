/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
 * @author Ussimane
 */
@Entity
@Table(name = "fproforma", catalog = "vendas", schema = "vendas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fproforma.findAll", query = "SELECT f FROM Fproforma f"),
    @NamedQuery(name = "Fproforma.findByDatavenda", query = "SELECT f FROM Fproforma f WHERE f.datavenda = :datavenda"),
    @NamedQuery(name = "Fproforma.findByQtd", query = "SELECT f FROM Fproforma f WHERE f.qtd = :qtd"),
    @NamedQuery(name = "Fproforma.findByValor", query = "SELECT f FROM Fproforma f WHERE f.valor = :valor"),
    @NamedQuery(name = "Fproforma.findBySeriefactura", query = "SELECT f FROM Fproforma f WHERE f.seriefactura = :seriefactura"),
    @NamedQuery(name = "Fproforma.findByDesconto", query = "SELECT f FROM Fproforma f WHERE f.desconto = :desconto"),
    @NamedQuery(name = "Fproforma.findByIdvenda", query = "SELECT f FROM Fproforma f WHERE f.idvenda = :idvenda"),
    @NamedQuery(name = "Fproforma.findByDatae", query = "SELECT f FROM Fproforma f WHERE f.datae = :datae"),
    @NamedQuery(name = "Fproforma.findByQc", query = "SELECT f FROM Fproforma f WHERE f.qc = :qc"),
    @NamedQuery(name = "Fproforma.findByEstado", query = "SELECT f FROM Fproforma f WHERE f.estado = :estado"),
    @NamedQuery(name = "Fproforma.findByPrec", query = "SELECT f FROM Fproforma f WHERE f.prec = :prec"),
    @NamedQuery(name = "Fproforma.findByIva", query = "SELECT f FROM Fproforma f WHERE f.iva = :iva"),
    @NamedQuery(name = "Fproforma.findByTdesc", query = "SELECT f FROM Fproforma f WHERE f.tdesc = :tdesc"),
    @NamedQuery(name = "Fproforma.findByTiva", query = "SELECT f FROM Fproforma f WHERE f.tiva = :tiva"),
    @NamedQuery(name = "Fproforma.findByDatapag", query = "SELECT f FROM Fproforma f WHERE f.datapag = :datapag"),
    @NamedQuery(name = "Fproforma.findByQstock", query = "SELECT f FROM Fproforma f WHERE f.qstock = :qstock"),
    @NamedQuery(name = "Fproforma.findByQpac", query = "SELECT f FROM Fproforma f WHERE f.qpac = :qpac"),
    @NamedQuery(name = "Fproforma.findByCaixa", query = "SELECT f FROM Fproforma f WHERE f.caixa = :caixa"),
    @NamedQuery(name = "Fproforma.findByNome", query = "SELECT f FROM Fproforma f WHERE f.nome = :nome"),
    @NamedQuery(name = "Fproforma.findByNuite", query = "SELECT f FROM Fproforma f WHERE f.nuite = :nuite")})
public class Fproforma implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "datavenda")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datavenda;
    @Basic(optional = false)
    @Column(name = "qtd", nullable = false)
    private int qtd;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor", precision = 8, scale = 8)
    private Float valor;
    @Column(name = "seriefactura", length = 25)
    private String seriefactura;
    @Column(name = "desconto")
    private Integer desconto;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idvenda", nullable = false)
    private Long idvenda;
    @Column(name = "datae")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datae;
    @Column(name = "qc")
    private Integer qc;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "prec", precision = 8, scale = 8)
    private Float prec;
    @Column(name = "iva")
    private Integer iva;
    @Column(name = "tdesc", precision = 8, scale = 8)
    private Float tdesc;
    @Column(name = "tiva", precision = 8, scale = 8)
    private Float tiva;
    @Column(name = "datapag")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datapag;
    @Column(name = "qstock")
    private Integer qstock;
    @Column(name = "qpac")
    private Integer qpac;
    @Column(name = "caixa")
    private Integer caixa;
    @Column(name = "nome", length = 2147483647)
    private String nome;
    @Column(name = "nuite", length = 2147483647)
    private String nuite;
    @JoinColumn(name = "idcliente", referencedColumnName = "idcliente")
    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente idcliente;
    @JoinColumn(name = "idproduto", referencedColumnName = "idproduto")
    @ManyToOne(fetch = FetchType.LAZY)
    private Produto idproduto;
    @JoinColumn(name = "idvendedor", referencedColumnName = "idvendedor")
    @ManyToOne(fetch = FetchType.LAZY)
    private Vendedor idvendedor;

    public Fproforma() {
    }

    public Fproforma(Long idvenda) {
        this.idvenda = idvenda;
    }

    public Fproforma(Long idvenda, int qtd) {
        this.idvenda = idvenda;
        this.qtd = qtd;
    }

    public Date getDatavenda() {
        return datavenda;
    }

    public void setDatavenda(Date datavenda) {
        this.datavenda = datavenda;
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public String getSeriefactura() {
        return seriefactura;
    }

    public void setSeriefactura(String seriefactura) {
        this.seriefactura = seriefactura;
    }

    public Integer getDesconto() {
        return desconto;
    }

    public void setDesconto(Integer desconto) {
        this.desconto = desconto;
    }

    public Long getIdvenda() {
        return idvenda;
    }

    public void setIdvenda(Long idvenda) {
        this.idvenda = idvenda;
    }

    public Date getDatae() {
        return datae;
    }

    public void setDatae(Date datae) {
        this.datae = datae;
    }

    public Integer getQc() {
        return qc;
    }

    public void setQc(Integer qc) {
        this.qc = qc;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Float getPrec() {
        return prec;
    }

    public void setPrec(Float prec) {
        this.prec = prec;
    }

    public Integer getIva() {
        return iva;
    }

    public void setIva(Integer iva) {
        this.iva = iva;
    }

    public Float getTdesc() {
        return tdesc;
    }

    public void setTdesc(Float tdesc) {
        this.tdesc = tdesc;
    }

    public Float getTiva() {
        return tiva;
    }

    public void setTiva(Float tiva) {
        this.tiva = tiva;
    }

    public Date getDatapag() {
        return datapag;
    }

    public void setDatapag(Date datapag) {
        this.datapag = datapag;
    }

    public Integer getQstock() {
        return qstock;
    }

    public void setQstock(Integer qstock) {
        this.qstock = qstock;
    }

    public Integer getQpac() {
        return qpac;
    }

    public void setQpac(Integer qpac) {
        this.qpac = qpac;
    }

    public Integer getCaixa() {
        return caixa;
    }

    public void setCaixa(Integer caixa) {
        this.caixa = caixa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNuite() {
        return nuite;
    }

    public void setNuite(String nuite) {
        this.nuite = nuite;
    }

    public Cliente getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Cliente idcliente) {
        this.idcliente = idcliente;
    }

    public Produto getIdproduto() {
        return idproduto;
    }

    public void setIdproduto(Produto idproduto) {
        this.idproduto = idproduto;
    }

    public Vendedor getIdvendedor() {
        return idvendedor;
    }

    public void setIdvendedor(Vendedor idvendedor) {
        this.idvendedor = idvendedor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idvenda != null ? idvenda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fproforma)) {
            return false;
        }
        Fproforma other = (Fproforma) object;
        if ((this.idvenda == null && other.idvenda != null) || (this.idvenda != null && !this.idvenda.equals(other.idvenda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Fproforma[ idvenda=" + idvenda + " ]";
    }
    
}
