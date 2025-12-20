package controleGastos.Gestao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import controleGastos.Gestao.dto.GestaSalarioDTO;
import controleGastos.Gestao.model.GestaoSalarioEntity;
import controleGastos.Gestao.repository.GestaoSalarioRepository;
import controleGastos.Gestao.service.GestaSalarioService;

@ExtendWith(MockitoExtension.class)
public class GestaoSalarioServiceTest {

    @Mock
    private GestaoSalarioRepository gestaoSalarioRepository;

    @InjectMocks
    private GestaSalarioService gestaSalarioService;


    @Test
    void salvarSalarioSucesso(){

        GestaoSalarioEntity gestaoSalarioEntity = new GestaoSalarioEntity();
        DateTimeFormatter formatador =  DateTimeFormatter.ofPattern("MM-dd-yyyy");
        LocalDate dataCoonvertida = LocalDate.parse("10-20-2001",formatador);
        gestaoSalarioEntity.setData(dataCoonvertida);
        gestaoSalarioEntity.setFuncao("Analista de Dados");
        gestaoSalarioEntity.setSalario(3500);

        when(gestaoSalarioRepository.save(any(GestaoSalarioEntity.class))).thenReturn(gestaoSalarioEntity);

       GestaSalarioDTO gestaoSalarioDTO = new GestaSalarioDTO(gestaoSalarioEntity);

        var resultado = gestaSalarioService.criarSalario(gestaoSalarioDTO);

        assertNotNull(resultado);

        verify(gestaoSalarioRepository, times(1)).save(any(GestaoSalarioEntity.class));

        assertEquals(3500,resultado.getSalario());

        assertEquals("Analista de Dados", resultado.getFuncao());

    }

}
