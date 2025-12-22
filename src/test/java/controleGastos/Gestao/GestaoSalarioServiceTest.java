package controleGastos.Gestao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

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

    @Test
    void listarSalarioTest(){

        GestaSalarioDTO salarioDTO = new GestaSalarioDTO();
        DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        LocalDate dataFormatada = LocalDate.parse("10-20-2001", formatadorData);
        salarioDTO.setData(dataFormatada);
        salarioDTO.setFuncao("Analista de dados");
        salarioDTO.setSalario(3500f);


        GestaSalarioDTO salarioDTO2 = new GestaSalarioDTO();
        DateTimeFormatter formatDt = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        LocalDate dataFormata = LocalDate.parse("07-31-2007", formatDt); 
        salarioDTO2.setData(dataFormata);
        salarioDTO2.setFuncao("Recepicionista");
        salarioDTO2.setSalario(1516f);

        GestaoSalarioEntity salarioEntity1 = new GestaoSalarioEntity(salarioDTO);
        GestaoSalarioEntity salarioEntity2 = new GestaoSalarioEntity(salarioDTO2);

        List<GestaoSalarioEntity> salarioList = List.of(salarioEntity1, salarioEntity2);

        when(gestaoSalarioRepository.findAll()).thenReturn(salarioList);

        var resultado = gestaSalarioService.listarSalario();

        assertNotNull(resultado);

        assertEquals(3500f, resultado.get(0).getSalario());
        assertEquals(1516f, resultado.get(1).getSalario());


    }
    
    @Test
    void alterarSalario(){

        Long id = 1L;

        GestaSalarioDTO salarioDTO1 = new GestaSalarioDTO();
        DateTimeFormatter form = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        LocalDate dataForm = LocalDate.parse("10-20-2001", form);
        salarioDTO1.setId(id);
        salarioDTO1.setData(dataForm);
        salarioDTO1.setFuncao("DevBackEnd");
        salarioDTO1.setSalario(3500f);

        GestaSalarioDTO salarioDTO2 = new GestaSalarioDTO();
        LocalDate datform2 = LocalDate.parse("07-31-2007",form);
        salarioDTO2.setData(datform2);
        salarioDTO2.setFuncao("Recepicionista");
        salarioDTO2.setSalario(1518f);


        GestaoSalarioEntity salario1Entity = new GestaoSalarioEntity(salarioDTO1);
   

        when(gestaoSalarioRepository.findById(id)).thenReturn(Optional.of(salario1Entity));

        when(gestaoSalarioRepository.save(any(GestaoSalarioEntity.class))).thenReturn(salario1Entity);

        var resultado = gestaSalarioService.alterarSalario(salarioDTO2, id);

        assertNotNull(resultado);
        assertEquals(1518f, resultado.get().getSalario());
        assertEquals("Recepicionista", resultado.get().getFuncao());

    }

    @Test
    void deletarSalarioTest(){

        Long id = 1L;
        GestaSalarioDTO salarioDTO1 = new GestaSalarioDTO();
        DateTimeFormatter form = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        LocalDate dataForm = LocalDate.parse("10-20-2001",form);
        salarioDTO1.setId(id);
        salarioDTO1.setData(dataForm);
        salarioDTO1.setFuncao("desenvolvedorSenior");
        salarioDTO1.setSalario(3500f);

        GestaoSalarioEntity salarioEntity = new GestaoSalarioEntity(salarioDTO1);
    
        when(gestaoSalarioRepository.findById(id)).thenReturn(Optional.of(salarioEntity));

        gestaSalarioService.deletarSalario(id);

        verify(gestaoSalarioRepository, times(1)).delete(salarioEntity);



    }

}
