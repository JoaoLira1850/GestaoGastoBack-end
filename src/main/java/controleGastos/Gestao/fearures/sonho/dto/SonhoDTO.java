package controleGastos.Gestao.fearures.sonho.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import controleGastos.Gestao.fearures.pessoa.dto.PessoaDTO;
import controleGastos.Gestao.fearures.pessoa.model.Pessoa;
import controleGastos.Gestao.fearures.sonho.model.Sonho;
import controleGastos.Gestao.fearures.sonho.model.StatusSonho;
import controleGastos.Gestao.fearures.sonho.model.TipoSonho;
import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.time.LocalDate;

@JsonIgnoreProperties
public record SonhoDTO (

        Long id,
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate data,
        TipoSonho sonho,
        BigDecimal valorSonho,
        StatusSonho status,
        PessoaDTO pessoa

){

    public SonhoDTO(Sonho sonho){
        this(sonho.getId(), sonho.getData(), sonho.getTipoSonho(), sonho.getValorSonho(), sonho.getStatus(),  new PessoaDTO(sonho.getPessoa()

        ));
    }
}
