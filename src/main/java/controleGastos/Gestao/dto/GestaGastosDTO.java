package controleGastos.Gestao.dto;

import java.time.LocalDate;

import org.springframework.beans.BeanUtils;

import controleGastos.Gestao.model.GestaGastosEntity;

public class GestaGastosDTO {

    private Long id;
    
    private String tipoGasto;

    private int quant;

    private LocalDate data;

    private float valor;

    public GestaGastosDTO() {

    }

    public GestaGastosDTO(GestaGastosEntity gestaGastosEntity) {

        BeanUtils.copyProperties(gestaGastosEntity, this);

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
