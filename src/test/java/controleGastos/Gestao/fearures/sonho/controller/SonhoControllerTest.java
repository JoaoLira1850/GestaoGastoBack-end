package controleGastos.Gestao.fearures.sonho.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import controleGastos.Gestao.fearures.pessoa.dto.PessoaDTO;
import controleGastos.Gestao.fearures.pessoa.model.AreaProfissonal;
import controleGastos.Gestao.fearures.pessoa.model.Pessoa;
import controleGastos.Gestao.fearures.pessoa.model.Sexo;
import controleGastos.Gestao.fearures.sonho.dto.SonhoDTO;
import controleGastos.Gestao.fearures.sonho.model.StatusSonho;
import controleGastos.Gestao.fearures.sonho.model.TipoSonho;
import controleGastos.Gestao.fearures.sonho.service.SonhoService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@SpringBootTest
@AutoConfigureMockMvc
class SonhoControllerTest {

    @Autowired
    private MockMvc mockMvc;



    @MockBean
    private SonhoService sonhoService;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private LocalDate data = LocalDate.parse("21/01/2026", formatter);


    @Autowired
    private ObjectMapper mapper;


    @Test
    void deveriaListarSonhoStatusCode200() throws Exception {



       BDDMockito.given(sonhoService.listarSonhos())
               .willReturn(List.of());

       MockHttpServletResponse response =  mockMvc.perform(
                get("/sonho")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();


        Assertions.assertEquals(200, response.getStatus());
        BDDMockito.then(sonhoService).should().listarSonhos();


    }

    @Test
    void deveriaCriarSonho() throws Exception {


        PessoaDTO pessoaDTO = new PessoaDTO(
                1L,
                "Joao vitor",
                Sexo.MASCULINO,
                data,
                "Dev Junior",
                AreaProfissonal.TECNOLOGIA
        );


        SonhoDTO sonhoDTO = new SonhoDTO(
                1L,
                data,
                TipoSonho.CARRO,
                BigDecimal.valueOf(50000),
                StatusSonho.ATIVO,
                pessoaDTO

        );

        String json = mapper.writeValueAsString(sonhoDTO);



        BDDMockito.given(sonhoService.criarSonho(sonhoDTO))
                .willAnswer(invocation -> invocation.getArgument(0));

        MockHttpServletResponse response = mockMvc.perform(
                post("/sonho")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();


        Assertions.assertEquals(201, response.getStatus());

        BDDMockito.then(sonhoService).should().criarSonho(sonhoDTO);

    }

    @Test
    void naoDeveriaCriarSonhoStatus400() throws Exception {



        String json = """
         
                """;

        MockHttpServletResponse response = mockMvc.perform(
                post("/sonho")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());

    }


    @Test
    void deveriaAtualizarSonhoStatus200() throws Exception {

        PessoaDTO pessoaDTO = new PessoaDTO(
                1L,
                "Joao vitor",
                Sexo.MASCULINO,
                data,
                "Dev Junior",
                AreaProfissonal.TECNOLOGIA
        );

        SonhoDTO sonhoDTO = new SonhoDTO(
                1L,
                data,
                TipoSonho.CARRO,
                BigDecimal.valueOf(50000),
                StatusSonho.ATIVO,
                pessoaDTO

        );

        String json = mapper.writeValueAsString(sonhoDTO);


        MockHttpServletResponse response = mockMvc.perform(
                put("/sonho/{is}", sonhoDTO.id())
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());

        BDDMockito.then(sonhoService).should().alterarSonho(sonhoDTO.id(), sonhoDTO);


    }


    @Test
    void naoDeveriaAtualizarSonho404() throws Exception {


        PessoaDTO pessoaDTO = new PessoaDTO(
                1L,
                "Joao vitor",
                Sexo.MASCULINO,
                data,
                "Dev Junior",
                AreaProfissonal.TECNOLOGIA
        );


        Long id = 50L;


        SonhoDTO sonhoDTO = new SonhoDTO(id,
                data,
                TipoSonho.CARRO,
                BigDecimal.valueOf(50000),
                StatusSonho.ATIVO,
                pessoaDTO

        );

        String json = mapper.writeValueAsString(sonhoDTO);

        BDDMockito.given(sonhoService.alterarSonho(id, sonhoDTO))
                .willThrow(EntityNotFoundException.class);

        MockHttpServletResponse response = mockMvc.perform(
                put("/sonho/{id}", id)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();


        Assertions.assertEquals(404, response.getStatus());



    }



    @Test
    void deveriaDeletarSonho() throws Exception {

        Long id = 1L;



        MockHttpServletResponse response = mockMvc.perform(
                delete("/sonho/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();


        Assertions.assertEquals(200, response.getStatus());
        BDDMockito.then(sonhoService).should().deletarSonho(id);


    }





}