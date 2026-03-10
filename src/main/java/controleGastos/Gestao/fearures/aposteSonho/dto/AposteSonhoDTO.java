package controleGastos.Gestao.fearures.aposteSonho.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import controleGastos.Gestao.fearures.aposteSonho.model.AposteSonho;
import controleGastos.Gestao.fearures.aposteSonho.model.TipoDepositoSonho;
import controleGastos.Gestao.fearures.sonho.dto.SonhoDTO;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AposteSonhoDTO(
        Long id,
        SonhoDTO sonhoDTO,
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate data,
        BigDecimal valor,
        TipoDepositoSonho tipoDepositoSonho

) {

    public static AposteSonhoDTO fromEntity(AposteSonho aposteSonho){


        return new AposteSonhoDTO(
                aposteSonho.getId(),
                SonhoDTO.fromEntity(aposteSonho.getSonho()),
                aposteSonho.getData(),
                aposteSonho.getValor(),
                aposteSonho.getTipoDepositoSonho()
        );

    }
}
