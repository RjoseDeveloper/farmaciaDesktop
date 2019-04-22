/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author USSIMANE
 */
@Entity
@Table(name = "emprestimo", catalog = "vendas", schema = "vendas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Emprestimo.findAll", query = "SELECT e FROM Emprestimo e"),
    @NamedQuery(name = "Emprestimo.findByDataemprestimo", query = "SELECT e FROM Emprestimo e WHERE e.dataemprestimo = :dataemprestimo"),
    @NamedQuery(name = "Emprestimo.findByDatapagamento", query = "SELECT e FROM Emprestimo e WHERE e.datapagamento = :datapagamento"),
    @NamedQuery(name = "Emprestimo.findByQtprod", query = "SELECT e FROM Emprestimo e WHERE e.qtprod = :qtprod"),
    @NamedQuery(name = "Emprestimo.findByTotalpagar", query = "SELECT e FROM Emprestimo e WHERE e.totalpagar = :totalpagar"),
    @NamedQuery(name = "Emprestimo.findByDesconto", query = "SELECT e FROM Emprestimo e WHERE e.desconto = :desconto"),
    @NamedQuery(name = "Emprestimo.findByIdemp", query = "SELECT e FROM Emprestimo e WHERE e.idemp = :idemp")})
public class Emprestimo implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    @Column(name = "qc")
    private Integer qc;
    @Column(name = "iva")
    private Integer iva;

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "dataemprestimo", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataemprestimo;
    @Column(name = "datapagamento")
    @Temporal(TemporalType.DATE)
    private Date datapagamento;
    @Column(name = "tdesc", precision = 8, scale = 8)
    private Float tdesc;
    @Column(name = "tiva", precision = 8, scale = 8)
    private Float tiva;
    @Column(name = "qtprod")
    private Integer qtprod;
    @Column(name = "datae")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datae;
    @Column(name = "prec", precision = 8, scale = 8)
    private Float prec;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "totalpagar", precision = 8, scale = 8)
    private Float totalpagar;
    @Column(name = "desconto")
    private Integer desconto;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idemp", nullable = false)
    private Long idemp;
    @JoinColumn(name = "idcliente", referencedColumnName = "idcliente")
    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente idcliente;
    @JoinColumn(name = "idproduto", referencedColumnName = "idproduto")
    @ManyToOne(fetch = FetchType.LAZY)
    private Produto idproduto;
    @JoinColumn(name = "idvendedor", referencedColumnName = "idvendedor")
    @ManyToOne(fetch = FetchType.LAZY)
    private Vendedor idvendedor;
    @Column(name = "qstock")
    private Integer qstock;
    @Column(name = "qpac")
    private Integer qpac;
    

    public Emprestimo() {
    }

    public Emprestimo(Long idemp) {
        this.idemp = idemp;
    }

    public Emprestimo(Long idemp, Date dataemprestimo) {
        this.idemp = idemp;
        this.dataemprestimo = dataemprestimo;
    }

    public Date getDataemprestimo() {
        return dataemprestimo;
    }

    public void setDataemprestimo(Date dataemprestimo) {
        Date oldDataemprestimo = this.dataemprestimo;
        this.dataemprestimo = dataemprestimo;
        changeSupport.firePropertyChange("dataemprestimo", oldDataemprestimo, dataemprestimo);
    }

    public Float getPrec() {
        return prec;
    }

    public void setPrec(Float prec) {
        Float oldPrec = this.prec;
        this.prec = prec;
        changeSupport.firePropertyChange("prec", oldPrec, prec);
    }

    public Date getDatapagamento() {
        return datapagamento;
    }

    public Float getTdesc() {
        return tdesc;
    }

    public void setTdesc(Float tdesc) {
        Float oldTdesc = this.tdesc;
        this.tdesc = tdesc;
        changeSupport.firePropertyChange("tdesc", oldTdesc, tdesc);
    }

    public Float getTiva() {
        return tiva;
    }

    public void setTiva(Float tiva) {
        Float oldTiva = this.tiva;
        this.tiva = tiva;
        changeSupport.firePropertyChange("tiva", oldTiva, tiva);
    }


    
    
    public Date getDatae() {
        return datae;
    }

    public void setDatae(Date datae) {
        Date oldDatae = this.datae;
        this.datae = datae;
        changeSupport.firePropertyChange("datae", oldDatae, datae);
    }


    public void setDatapagamento(Date datapagamento) {
        Date oldDatapagamento = this.datapagamento;
        this.datapagamento = datapagamento;
        changeSupport.firePropertyChange("datapagamento", oldDatapagamento, datapagamento);
    }

    public Integer getQtprod() {
        return qtprod;
    }

    public void setQtprod(Integer qtprod) {
        Integer oldQtprod = this.qtprod;
        this.qtprod = qtprod;
        changeSupport.firePropertyChange("qtprod", oldQtprod, qtprod);
    }

    public Float getTotalpagar() {
        return totalpagar;
    }

    public void setTotalpagar(Float totalpagar) {
        Float oldTotalpagar = this.totalpagar;
        this.totalpagar = totalpagar;
        changeSupport.firePropertyChange("totalpagar", oldTotalpagar, totalpagar);
    }

    public Integer getDesconto() {
        return desconto;
    }

    public void setDesconto(Integer desconto) {
        Integer oldDesconto = this.desconto;
        this.desconto = desconto;
        changeSupport.firePropertyChange("desconto", oldDesconto, desconto);
    }

    public Long getIdemp() {
        return idemp;
    }

    public void setIdemp(Long idemp) {
        Long oldIdemp = this.idemp;
        this.idemp = idemp;
        changeSupport.firePropertyChange("idemp", oldIdemp, idemp);
    }

    public Cliente getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Cliente idcliente) {
        Cliente oldIdcliente = this.idcliente;
        this.idcliente = idcliente;
        changeSupport.firePropertyChange("idcliente", oldIdcliente, idcliente);
    }

    public Produto getIdproduto() {
        return idproduto;
    }

    public void setIdproduto(Produto idproduto) {
        Produto oldIdproduto = this.idproduto;
        this.idproduto = idproduto;
        changeSupport.firePropertyChange("idproduto", oldIdproduto, idproduto);
    }

    public Vendedor getIdvendedor() {
        return idvendedor;
    }

    public void setIdvendedor(Vendedor idvendedor) {
        Vendedor oldIdvendedor = this.idvendedor;
        this.idvendedor = idvendedor;
        changeSupport.firePropertyChange("idvendedor", oldIdvendedor, idvendedor);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idemp != null ? idemp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Emprestimo)) {
            return false;
        }
        Emprestimo other = (Emprestimo) object;
        if ((this.idemp == null && other.idemp != null) || (this.idemp != null && !this.idemp.equals(other.idemp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Emprestimo[ idemp=" + idemp + " ]";
    }

    public Integer getQc() {
        return qc;
    }

    public void setQc(Integer qc) {
        Integer oldQc = this.qc;
        this.qc = qc;
        changeSupport.firePropertyChange("qc", oldQc, qc);
    }

    public Integer getIva() {
        return iva;
    }

    public void setIva(Integer iva) {
        Integer oldIva = this.iva;
        this.iva = iva;
        changeSupport.firePropertyChange("iva", oldIva, iva);
    }

    public Integer getQstock() {
        return qstock;
    }

    public void setQstock(Integer qstock) {
        Integer oldQstock = this.qstock;
        this.qstock = qstock;
        changeSupport.firePropertyChange("qstock", oldQstock, qstock);
    }

    public Integer getQpac() {
        return qpac;
    }

    public void setQpac(Integer qpac) {
        Integer oldQpac = this.qpac;
        this.qpac = qpac;
        changeSupport.firePropertyChange("qpac", oldQpac, qpac);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
    

}
