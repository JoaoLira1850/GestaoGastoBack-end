package controleGastos.Gestao.fearures.receita.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import controleGastos.Gestao.fearures.pessoa.dto.DeleteDTO;
import controleGastos.Gestao.fearures.pessoa.dto.PessoaDTO;
import controleGastos.Gestao.fearures.pessoa.model.AreaProfissonal;
import controleGastos.Gestao.fearures.pessoa.model.Sexo;
import controleGastos.Gestao.fearures.planejamento.dto.PlanejamentoDTO;
import controleGastos.Gestao.fearures.receita.dto.ReceitaDTO;
import controleGastos.Gestao.fearures.receita.model.Receita;
import controleGastos.Gestao.fearures.receita.model.TipoReceita;
import controleGastos.Gestao.fearures.receita.service.ReceitaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureMockMvc
class ReceitaControllerTest {




    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private ReceitaService receitaService;

    @Mock
    private PlanejamentoDTO planejamentoDTO;




    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private LocalDate data = LocalDate.parse("26/01/2026",formatter);


    @Autowired
    private ObjectMapper mapper;

    @Test
    void deveriaListarReceita() throws Exception {


        ReceitaDTO receitaDTO = new ReceitaDTO(
                1L,
                data,
                BigDecimal.valueOf(2500),
                TipoReceita.SALARIO,
                planejamentoDTO
        );

        List<ReceitaDTO> receitaDTOList = new ArrayList<>();
        receitaDTOList.add(receitaDTO);


        BDDMockito.given(receitaService.listarReceita())
                .willReturn(receitaDTOList);

        MockHttpServletResponse response = mockMvc.perform(
                get("/receita")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andReturn().getResponse();


        String json = response.getContentAsString();

        var jsonFromReceita = mapper.readValue(json,
                new TypeReference<List<ReceitaDTO>>() {
                });

        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals(TipoReceita.SALARIO,jsonFromReceita.get(0).tipoReceita() );




    }

    @Test
    void shouldCreateRevenue() throws Exception {

        PessoaDTO pessoaDTO = new PessoaDTO(
                1L,
                "Joao vitor",
                Sexo.MASCULINO,
                data,
                "dev Junior",
                AreaProfissonal.TECNOLOGIA
        );

        PlanejamentoDTO planejamentoDTO1 = new PlanejamentoDTO(
                1L,
                data,
                "Planejameno financiero",
                pessoaDTO

        );

        ReceitaDTO receitaDTO = new ReceitaDTO(
                1L,
                data,
                BigDecimal.valueOf(3500),
                TipoReceita.SALARIO,
                planejamentoDTO1
        );


        String json = mapper.writeValueAsString(receitaDTO);

        BDDMockito.given(receitaService.criarReceita(receitaDTO))
                .willReturn(receitaDTO);

        MockHttpServletResponse response = mockMvc.perform(
                post("/receita")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();


        var resutado = response.getContentAsString();

        ReceitaDTO resultadoReceita = mapper.readValue(resutado, ReceitaDTO.class);

        Assertions.assertEquals(201, response.getStatus());
        Assertions.assertEquals(TipoReceita.SALARIO, resultadoReceita.tipoReceita());


    }


    @Test
    void notShouldCreateRevenueStatus400() throws Exception {


        String jsonInvalido = """
            {
                "descricao": "Receita sem valor",
                "tipoReceita": "SALARIO"
            }
            """;

      //  BDDMockito.given(receitaService.criarReceita(receitaDTO))
     //           .willReturn(receitaDTO);

        MockHttpServletResponse response = mockMvc.perform(
                post("/receita")
                        .content(jsonInvalido)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();


        Assertions.assertEquals(400, response.getStatus());


    }

    @Test
    void shouldUpdateRevenueStatus200() throws Exception {

        Long id = 1L ;

        PessoaDTO pessoaDTO = new PessoaDTO(
                1L,
                "Joao vitor",
                Sexo.MASCULINO,
                data,
                "dev Junior",
                AreaProfissonal.TECNOLOGIA
        );

        PlanejamentoDTO planejamentoDTO1 = new PlanejamentoDTO(
                1L,
                data,
                "Planejameno financiero",
                pessoaDTO

        );

        ReceitaDTO receitaDTO = new ReceitaDTO(
                1L,
                data,
                BigDecimal.valueOf(3500),
                TipoReceita.SALARIO,
                planejamentoDTO1
        );


        String json = mapper.writeValueAsString(receitaDTO);

        BDDMockito.given(receitaService.altualizarReceita(id, receitaDTO))
                .willReturn(receitaDTO);

        MockHttpServletResponse response = mockMvc.perform(
                put("/receita/{id}", id)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();


        ReceitaDTO receitaDTOTransformado = mapper.readValue(response.getContentAsString(), ReceitaDTO.class);

        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals(TipoReceita.SALARIO, receitaDTOTransformado.tipoReceita());


    }


    @Test
    void shouldDeleteRevenue() throws Exception {



        Long id = 1L;


        DeleteDTO deleteDTO = new DeleteDTO(
                id,
                String.valueOf(TipoReceita.SALARIO)
        );

        when(receitaService.deletarReceita(id)).thenReturn(deleteDTO);


        MockHttpServletResponse response = mockMvc.perform(
                delete("/receita/{id}", id)

        ).andReturn().getResponse();


        var returno = response.getContentAsString();

        DeleteDTO deleteDTO1 = mapper.readValue(returno, DeleteDTO.class);


        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals(1L, deleteDTO1.id());

    }

}