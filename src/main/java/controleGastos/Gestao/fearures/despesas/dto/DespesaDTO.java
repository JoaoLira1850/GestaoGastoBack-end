package controleGastos.Gestao.fearures.despesas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import controleGastos.Gestao.fearures.despesas.model.CategoriaDespesa;
import controleGastos.Gestao.fearures.despesas.model.Despesa;
import controleGastos.Gestao.fearures.planejamento.dto.PlanejamentoDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DespesaDTO(
        Long id,
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate data,
        BigDecimal valor,
        PlanejamentoDTO planejamentoDTO,
        CategoriaDespesa categoriaDespesa,
        Float quantidade
) {


    public static DespesaDTO FromEntity(Despesa despesa){

        return new DespesaDTO(
                despesa.getId(),
                despesa.getData(),
                despesa.getValor(),
                PlanejamentoDTO.fromEntiy(despesa.getPlanejamento()),
                despesa.getCategoriaDespesa(),
                despesa.getQuantidade()
        );
    }


}
