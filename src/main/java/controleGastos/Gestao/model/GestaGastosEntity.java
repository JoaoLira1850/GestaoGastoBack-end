package controleGastos.Gestao.model;

import java.time.LocalDate;

import org.springframework.beans.BeanUtils;

import controleGastos.Gestao.dto.GestaGastosDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Gestao")
public class GestaGastosEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String tipoGasto;
    
    private int quant;

    private LocalDate data;

    private float valor;

    public GestaGastosEntity() {

    }

    public GestaGastosEntity(GestaGastosDTO gestaGastosDTO) {

        BeanUtils.copyProperties(gestaGastosDTO, this);
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoGasto() {
        return tipoGasto;
    }

    public void setTipoGasto(String tipoGasto) {
        this.tipoGasto = tipoGasto;
    }

    public int getQuant() {
        return quant;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }



}
