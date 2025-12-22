package controleGastos.Gestao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import controleGastos.Gestao.model.GestaGastosEntity;
import controleGastos.Gestao.repository.GestaoGastosRepository;
import controleGastos.Gestao.service.GestaoGastosService;

@ExtendWith(MockitoExtension.class)
public class GestaoGastosServiceTest {

    @Mock
    private GestaoGastosRepository gestaoGastosRepository;

    @InjectMocks
    private GestaoGastosService gestaGastosService;


    @Test
    public void testarListar(){

        GestaGastosEntity gastosEntity = new GestaGastosEntity();
        DateTimeFormatter form = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        LocalDate dtForm = LocalDate.parse("10-20-2001",form);
        gastosEntity.setData(dtForm);
        gastosEntity.setQuant(3);
        gastosEntity.setValor(2500);
        gastosEntity.setTipoGasto("Alimentacao");

        GestaGastosEntity gastosEntity1 = new GestaGastosEntity();
        DateTimeFormatter form1 = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        LocalDate dtForm1 = LocalDate.parse("10-20-2001",form1);
        gastosEntity1.setData(dtForm1);
        gastosEntity1.setQuant(3);
        gastosEntity1.setValor(2500);
        gastosEntity1.setTipoGasto("Alimentacao");

        var gastos = List.of(gastosEntity, gastosEntity1);

        when(gestaoGastosRepository.findAll()).thenReturn(gastos);

        var resulto = gestaGastosService.listaGastos();

        assertNotNull(resulto);
        assertEquals(2500f, resulto.get(0).getValor());
        assertEquals(2500f, resulto.get(1).getValor());
        
        
    }

}
