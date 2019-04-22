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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "Movimento", catalog = "vendas", schema = "vendas")
@XmlRootElement
@NamedQueries({})
public class Movimento implements Serializable {

    @Id
    @Basic(optional = false)
    @Column(name = "data", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @Column(name = "tipomov", length = 2147483647)
    private String tipomov;
    @Column(name = "qtd")
    private Integer qtd;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "preco", precision = 8, scale = 8)
    private Float preco;
    @Column(name = "stock")
    private Integer stock;
    @Column(name = "balcao")
    private Integer balcao;
    @Column(name = "estado", length = 2147483647)
    private String estado;
    @Column(name = "lot")
    private String lot;
    @Column(name = "qa")
    private Integer qa;
    @Column(name = "qb")
    private Integer qb;
    @Column(name = "qc")
    private Integer qc;
    @Column(name = "pb")
    private Integer pb;
    @Column(name = "pc")
    private Integer pc;

    public Integer getIdd() {
        return idd;
    }

    public void setIdd(Integer idd) {
        this.idd = idd;
    }
    @Column(name = "iva")
    private Integer iva;
    @Column(name = "idd")
    private Integer idd;

    public Movimento() {
    }

    public Movimento(Date data) {
        this.data = data;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getTipomov() {
        return tipomov;
    }

    public void setTipomov(String tipomov) {
        this.tipomov = tipomov;
    }

    public Integer getQtd() {
        return qtd;
    }

    public void setQtd(Integer qtd) {
        this.qtd = qtd;
    }

    public Float getPreco() {
        return preco;
    }

    public void setPreco(Float preco) {
        this.preco = preco;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getBalcao() {
        return balcao;
    }

    public void setBalcao(Integer balcao) {
        this.balcao = balcao;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
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

    public Integer getPb() {
        return pb;
    }

    public void setPb(Integer pb) {
        this.pb = pb;
    }

    public Integer getPc() {
        return pc;
    }

    public void setPc(Integer pc) {
        this.pc = pc;
    }

    public Integer getIva() {
        return iva;
    }

    public void setIva(Integer iva) {
        this.iva = iva;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (data != null ? data.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Movimento)) {
            return false;
        }
        Movimento other = (Movimento) object;
        if ((this.data == null && other.data != null) || (this.data != null && !this.data.equals(other.data))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Movimento[ data=" + data + " ]";
    }

}
