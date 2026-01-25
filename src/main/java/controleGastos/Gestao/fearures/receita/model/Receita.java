package controleGastos.Gestao.fearures.receita.model;


import controleGastos.Gestao.fearures.planejamento.model.Planejamento;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Receita {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate data;

    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    private TipoReceita tipoReceita;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "planejamento_id")
    private Planejamento planejamento;


    public Receita(LocalDate data, BigDecimal valor, TipoReceita tipoReceita, Planejamento planejamento) {
        this.data = data;
        this.valor = valor;
        this.tipoReceita = tipoReceita;
        this.planejamento = planejamento;
    }

    public Receita() {
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

    public TipoReceita getTipoReceita() {
        return tipoReceita;
    }

    public void setTipoReceita(TipoReceita tipoReceita) {
        this.tipoReceita = tipoReceita;
    }

    public Planejamento getPlanejamento() {
        return planejamento;
    }

    public void setPlanejamento(Planejamento planejamento) {
        this.planejamento = planejamento;
    }
}
