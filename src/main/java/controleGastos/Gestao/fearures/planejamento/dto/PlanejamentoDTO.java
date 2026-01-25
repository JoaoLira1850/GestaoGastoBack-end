package controleGastos.Gestao.fearures.planejamento.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import controleGastos.Gestao.fearures.pessoa.dto.PessoaDTO;
import controleGastos.Gestao.fearures.planejamento.model.Planejamento;

import java.time.LocalDate;

public record PlanejamentoDTO(
        Long id,
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate data,
        String nome,
        PessoaDTO pessoaDTO
) {


        public static PlanejamentoDTO fromEntiy(Planejamento p){
                if (p == null) return null;

                return new PlanejamentoDTO(
                        p.getId(),
                        p.getData(),
                        p.getNome(),
                        PessoaDTO.fromEntiy(p.getPessoa())
                );

        }
}
