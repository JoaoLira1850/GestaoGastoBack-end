package controleGastos.Gestao.fearures.receita.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import controleGastos.Gestao.fearures.planejamento.dto.PlanejamentoDTO;
import controleGastos.Gestao.fearures.planejamento.model.Planejamento;
import controleGastos.Gestao.fearures.receita.model.Receita;
import controleGastos.Gestao.fearures.receita.model.TipoReceita;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ReceitaDTO(
        Long id,
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate data,

        @NotNull
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
