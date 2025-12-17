package controleGastos.Gestao.model;

import java.time.LocalDate;

import org.springframework.beans.BeanUtils;

import controleGastos.Gestao.dto.GestaSalarioDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Salario")
public class GestaoSalarioEntity {

   @Id
   @GeneratedValue(strategy=GenerationType.AUTO)
   private Long id;

   private LocalDate data;

   private String funcao;

   private float salario;

    public GestaoSalarioEntity() {

    }

    public GestaoSalarioEntity( GestaSalarioDTO gestaSalarioDTO) {

        BeanUtils.copyProperties(gestaSalarioDTO, this);

      
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

    public float getSalario() {
        return salario;
    }

    public void setSalario(float salario) {
        this.salario = salario;
    }






}
