package controleGastos.Gestao.fearures.planejamento.model;


import controleGastos.Gestao.fearures.pessoa.model.Pessoa;
import controleGastos.Gestao.fearures.receita.model.Receita;
import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Planejamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private LocalDate data;

    @ManyToOne(fetch = FetchType.EAGER )
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

    @OneToMany(mappedBy = "planejamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Receita> receita = new ArrayList<>();


    public Planejamento() {
    }

    public Planejamento(String nome, LocalDate data, Pessoa pessoa) {
        this.nome = nome;
        this.data = data;
        this.pessoa = pessoa;
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

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
