package controleGastos.Gestao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import controleGastos.Gestao.controller.GestaoSalarioController;
import controleGastos.Gestao.dto.GestaSalarioDTO;
import controleGastos.Gestao.service.GestaSalarioService;
import tools.jackson.databind.ObjectMapper;

@WebMvcTest(GestaoSalarioController.class)
public class GestaoSalarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GestaSalarioService gestaSalarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void criarSalarioViaHTTP() throws Exception {

        GestaSalarioDTO salarioDTO = new GestaSalarioDTO();

        DateTimeFormatter dataForm = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        LocalDate dataFormatada = LocalDate.parse("10-20-2001", dataForm);
        salarioDTO.setData(dataFormatada);
        salarioDTO.setFuncao("Analista de dados");
        salarioDTO.setSalario(3500f);

        when(gestaSalarioService.criarSalario(any(GestaSalarioDTO.class))).thenReturn(salarioDTO);

        mockMvc.perform(post("/salario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(salarioDTO)))


                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.salario").value(3500f))
                        .andExpect(jsonPath("$.funcao").value("Analista de dados"));

        


    }

    @Test
    void testarGET() throws Exception {
        GestaSalarioDTO salarioDTO1 = new GestaSalarioDTO();
        DateTimeFormatter form = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        LocalDate dataForm = LocalDate.parse("10-20-2001",form);
        salarioDTO1.setData(dataForm);
        salarioDTO1.setFuncao("Analista");
        salarioDTO1.setSalario(3500f);

        GestaSalarioDTO salarioDTO2 = new GestaSalarioDTO();
        LocalDate dataForm2 = LocalDate.parse("07-31-2007",form);
        salarioDTO2.setData(dataForm2);
        salarioDTO2.setFuncao("Recepicionista");
        salarioDTO2.setSalario(1518f);

       List<GestaSalarioDTO> salarioEntitys = List.of(salarioDTO1,salarioDTO2);

       when(gestaSalarioService.listarSalario()).thenReturn(salarioEntitys);

       mockMvc.perform(get("/salario")
                       .contentType(MediaType.APPLICATION_JSON))

                       .andExpect(status().isOk())
                       .andExpect(jsonPath("$[0].salario").value(3500f))
                       .andExpect(jsonPath("$[0].funcao").value("Analista"));
                       
    }

    @Test
    void testarPUT() throws Exception{
        Long id = 1L;
        GestaSalarioDTO salarioDTO = new GestaSalarioDTO();
        DateTimeFormatter form = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        LocalDate dataForm = LocalDate.parse("10-20-2001",form);
        salarioDTO.setData(dataForm);
        salarioDTO.setFuncao("Analista");
        salarioDTO.setSalario(1500f);

        when(gestaSalarioService.alterarSalario(any(GestaSalarioDTO.class), eq(id))).thenReturn(Optional.of(salarioDTO));

        mockMvc.perform(put("/salario/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(salarioDTO)))

                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.salario").value(1500f));
                        
                        

    }



    

}
