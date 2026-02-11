package controleGastos.Gestao.fearures.aposteSonho.model;


import controleGastos.Gestao.fearures.sonho.model.Sonho;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class AposteSonho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @ManyToOne(fetch =FetchType.EAGER)
    private Sonho sonho;

    private LocalDate data;

    private BigDecimal valor;

    private TipoDepositoSonho tipoDepositoSonho;


    public AposteSonho() {
    }

    public AposteSonho(Sonho sonho, LocalDate data, BigDecimal valor, TipoDepositoSonho tipoDepositoSonho) {
        this.sonho = sonho;
        this.data = data;
        this.valor = valor;
        this.tipoDepositoSonho = tipoDepositoSonho;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sonho getSonho() {
        return sonho;
    }

    public void setSonho(Sonho sonho) {
        this.sonho = sonho;
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

    public TipoDepositoSonho getTipoDepositoSonho() {
        return tipoDepositoSonho;
    }

    public void setTipoDepositoSonho(TipoDepositoSonho tipoDepositoSonho) {
        this.tipoDepositoSonho = tipoDepositoSonho;
    }
}
