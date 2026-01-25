package controleGastos.Gestao.fearures.pessoa.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import controleGastos.Gestao.fearures.pessoa.model.AreaProfissonal;
import controleGastos.Gestao.fearures.pessoa.model.Pessoa;
import controleGastos.Gestao.fearures.pessoa.model.Sexo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PessoaDTO(Long id,

                        @NotBlank
                        String nome,

                        @NotNull
                        Sexo sexo,

                        @NotNull
                        @JsonFormat(pattern = "dd/MM/yyyy")
                        LocalDate dataNascimento,

                        @NotBlank
                        String profissao,

                        @NotNull
                        AreaProfissonal area) {


    public PessoaDTO(Pessoa pessoa){

        this(pessoa.getId(),
                pessoa.getNome(),
                pessoa.getSexo(),
                pessoa.getDataNacimento(),
                pessoa.getProfissao(),
                pessoa.getArea());

    }

    public static PessoaDTO fromEntiy(Pessoa p){

        if (p == null) return null;

        return new PessoaDTO(
                p.getId(),
                p.getNome(),
                p.getSexo(),
                p.getDataNacimento(),
                p.getProfissao(),
                p.getArea()
        );

    }





}
