package controleGastos.Gestao.fearures.despesas.model;


import controleGastos.Gestao.fearures.planejamento.dto.PlanejamentoDTO;
import controleGastos.Gestao.fearures.planejamento.model.Planejamento;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Despesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate data;

    private BigDecimal valor;

    @ManyToOne
    private Planejamento planejamento;

    @Enumerated(EnumType.STRING)
    private CategoriaDespesa categoriaDespesa;

    private Float quantidade;

    public Despesa( LocalDate data, BigDecimal valor, Planejamento planejamento, CategoriaDespesa categoriaDespesa, Float quantidade) {
        this.data = data;
        this.valor = valor;
        this.planejamento = planejamento;
        this.categoriaDespesa = categoriaDespesa;
        this.quantidade = quantidade;
    }

    public Despesa() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Planejamento getPlanejamento() {
        return planejamento;
    }

    public void setPlanejamento(Planejamento planejamento) {
        this.planejamento = planejamento;
    }

    public CategoriaDespesa getCategoriaDespesa() {
        return categoriaDespesa;
    }

    public void setCategoriaDespesa(CategoriaDespesa categoriaDespesa) {
        this.categoriaDespesa = categoriaDespesa;
    }

    public Float getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Float quantidade) {
        this.quantidade = quantidade;
    }
}
