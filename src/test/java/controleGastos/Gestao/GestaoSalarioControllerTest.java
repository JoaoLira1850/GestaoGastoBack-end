package controleGastos.Gestao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    

}
