package controleGastos.Gestao.fearures.pessoa.model;


import controleGastos.Gestao.fearures.pessoa.dto.PessoaDTO;
import controleGastos.Gestao.fearures.planejamento.model.Planejamento;
import controleGastos.Gestao.fearures.sonho.model.Sonho;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Sexo sexo;

    private LocalDate dataNacimento;

    private String profissao;

    private AreaProfissonal area;

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Sonho> sonhos = new ArrayList<>();


    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Planejamento> planejamentos = new ArrayList<>();

    public Pessoa(String nome, Sexo sexo, LocalDate dataNacimento, String profissao, AreaProfissonal area) {
        this.nome = nome;
        this.sexo = sexo;
        this.dataNacimento = dataNacimento;
        this.profissao = profissao;
        this.area = area;
    }

    public Pessoa(){

    }

    public Pessoa (PessoaDTO pessoaDTO){

        this.nome = pessoaDTO.nome();
        this.sexo = pessoaDTO.sexo();
        this.dataNacimento = pessoaDTO.dataNascimento();
        this.profissao = pessoaDTO.profissao();
        this.area = pessoaDTO.area();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public LocalDate getDataNacimento() {
        return dataNacimento;
    }

    public void setDataNacimento(LocalDate dataNacimento) {
        this.dataNacimento = dataNacimento;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public AreaProfissonal getArea() {
        return area;
    }

    public void setArea(AreaProfissonal area) {
        this.area = area;
    }
}
