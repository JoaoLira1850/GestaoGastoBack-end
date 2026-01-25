package controleGastos.Gestao.fearures.sonho.model;


import controleGastos.Gestao.fearures.pessoa.model.Pessoa;
import controleGastos.Gestao.fearures.sonho.dto.SonhoDTO;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Sonho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate data;

    @Enumerated(EnumType.STRING)
    private TipoSonho tipoSonho;

    private BigDecimal valorSonho;

    @Enumerated(EnumType.STRING)
    private StatusSonho status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pessoa")
    private Pessoa pessoa;


    public Sonho(LocalDate data, TipoSonho tipoSonho, BigDecimal valorSonho, StatusSonho status, Pessoa pessoa) {
        this.data = data;
        this.tipoSonho = tipoSonho;
        this.valorSonho = valorSonho;
        this.status = status;
        this.pessoa = pessoa;
    }


    public Sonho(Sonho sonho){
       this(sonho.getData(), sonho.getTipoSonho(), sonho.getValorSonho(), sonho.getStatus(), sonho.getPessoa());
    }

    public Sonho(){

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

    public TipoSonho getTipoSonho() {
        return tipoSonho;
    }

    public void setTipoSonho(TipoSonho tipoSonho) {
        this.tipoSonho = tipoSonho;
    }

    public BigDecimal getValorSonho() {
        return valorSonho;
    }

    public void setValorSonho(BigDecimal valorSonho) {
        this.valorSonho = valorSonho;
    }

    public StatusSonho getStatus() {
        return status;
    }

    public void setStatus(StatusSonho status) {
        this.status = status;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
}
