/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ussimane
 */
@Entity
@Table(catalog = "vendas", schema = "vendas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Serieprof.findAll", query = "SELECT s FROM Serieprof s")})
public class Serieprof implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(length = 255)
    private String serieprof;
    @Id
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer idf;

    public Serieprof() {
    }

    public Serieprof(Integer idf) {
        this.idf = idf;
    }

    public String getSerieprof() {
        return serieprof;
    }

    public void setSerieprof(String serieprof) {
        this.serieprof = serieprof;
    }

    public Integer getIdf() {
        return idf;
    }

    public void setIdf(Integer idf) {
        this.idf = idf;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idf != null ? idf.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Serieprof)) {
            return false;
        }
        Serieprof other = (Serieprof) object;
        if ((this.idf == null && other.idf != null) || (this.idf != null && !this.idf.equals(other.idf))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Serieprof[ idf=" + idf + " ]";
    }
    
}
