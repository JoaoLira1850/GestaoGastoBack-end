package controleGastos.Gestao.fearures.receita.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import controleGastos.Gestao.fearures.planejamento.dto.PlanejamentoDTO;
import controleGastos.Gestao.fearures.planejamento.model.Planejamento;
import controleGastos.Gestao.fearures.receita.model.Receita;
import controleGastos.Gestao.fearures.receita.model.TipoReceita;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ReceitaDTO(
        Long id,
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate data,
        BigDecimal valor,
        TipoReceita tipoReceita,
        PlanejamentoDTO planejamento
) {

    public static ReceitaDTO fromEntity(Receita r){

        return new ReceitaDTO(
                r.getId(),
                r.getData(),
                r.getValor(),
                r.getTipoReceita(),
                PlanejamentoDTO.fromEntiy(r.getPlanejamento())
        );
    }
}
