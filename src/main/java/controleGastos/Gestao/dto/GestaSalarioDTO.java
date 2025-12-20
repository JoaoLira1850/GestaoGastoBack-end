package controleGastos.Gestao.dto;

import java.time.LocalDate;

import org.springframework.beans.BeanUtils;

import controleGastos.Gestao.model.GestaoSalarioEntity;

public class GestaSalarioDTO {

    private Long id;
    private LocalDate data;
    private String funcao;
    private float salario;

    public GestaSalarioDTO() {
    }

    public GestaSalarioDTO( GestaoSalarioEntity gestaoSalarioEntity) {

        BeanUtils.copyProperties(gestaoSalarioEntity, this);
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

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public float  getSalario() {
        return salario;
    }

    public void setSalario(float salario) {
        this.salario = salario;
    }





    

}
