package controleGastos.Gestao.fearures.pessoa.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import controleGastos.Gestao.fearures.pessoa.dto.DeleteDTO;
import controleGastos.Gestao.fearures.pessoa.dto.PessoaDTO;
import controleGastos.Gestao.fearures.pessoa.model.AreaProfissonal;
import controleGastos.Gestao.fearures.pessoa.model.Sexo;
import controleGastos.Gestao.fearures.pessoa.service.PessoaService;
import controleGastos.Gestao.infra.excepitonController.ExcepitionDTO;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.mockito.BDDMockito;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@SpringBootTest
@AutoConfigureMockMvc
class PessoaControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PessoaService pessoaService;


    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private LocalDate data = LocalDate.parse("20/10/2004", formatter);

    @Autowired
    private ObjectMapper mapper;


    @Test
    public void deveCriarPessoa() throws Exception {

        PessoaDTO pessoaDTO = new PessoaDTO(
                1L,
                "Joao vitor Lira Silva",
                Sexo.MASCULINO,
                data,
                "Dev Junior",
                AreaProfissonal.TECNOLOGIA
        );

        BDDMockito.given(pessoaService.criarPessoa(Mockito.any()))
              .willReturn(pessoaDTO);

        String json = mapper.writeValueAsString(pessoaDTO);


        MockHttpServletResponse response = mockMvc.perform(
                post("/pessoa")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)

        ).andReturn().getResponse();


        Assertions.assertEquals(201,response.getStatus());


    }

    @Test
    public void naoDeveCriarPessoaErro400() throws Exception {

        String vazio = """
                """;

        PessoaDTO pessoaDTO = new PessoaDTO(
                1L,
                "",
                Sexo.MASCULINO,
                data,
                "",
                AreaProfissonal.TECNOLOGIA
        );

        String json = mapper.writeValueAsString(pessoaDTO);


        MockHttpServletResponse response = mockMvc.perform(
                post("/pessoa")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)

        ).andReturn().getResponse();

        String bodyErro = response.getContentAsString();

        ExcepitionDTO erro = mapper.readValue(bodyErro, ExcepitionDTO.class);

        System.out.println("Convertido" + erro);

        Assertions.assertEquals(400,response.getStatus());
        if (erro.message().equals("JSON inválido. Verifique formato, enums e campos obrigatórios.")){
            Assertions.assertEquals("JSON inválido. Verifique formato, enums e campos obrigatórios.", erro.message());
        }else {
            Assertions.assertEquals("must not be blank", erro.errors().get(0).message());
        }



    }

   @Test
    void deveriaListarPessoas() throws Exception {


        PessoaDTO pessoaDTO = new PessoaDTO(
                1L,
                "Joao Vitor",
                Sexo.MASCULINO,
                data,
                "Dev Junior Java",
                AreaProfissonal.TECNOLOGIA
        );

        PessoaDTO pessoaDTO1 = new PessoaDTO(
                2L,
                "Maria",
                Sexo.FEMININO,
                data,
                "Cardiologista",
                AreaProfissonal.SAUDE
        );

       List<PessoaDTO> listPesssoa = new ArrayList<>();
       listPesssoa.add(pessoaDTO);
       listPesssoa.add(pessoaDTO1);

       BDDMockito.given(pessoaService.listarPessoas()).willReturn(listPesssoa);

        MockHttpServletResponse response = mockMvc.perform(
                get("/pessoa")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());



   }

   @Test
    void deveriaAtualizarPessoa() throws Exception {

        Long id = 1L;

        PessoaDTO pessoaDTO = new PessoaDTO(
                id,
                "Joao Vitor",
                Sexo.MASCULINO,
                data,
                "Dev Junir",
                AreaProfissonal.TECNOLOGIA
        );

       PessoaDTO pessoaAterado= new PessoaDTO(
               id,
               "Maria",
               Sexo.FEMININO,
               data,
               "Dev Junir",
               AreaProfissonal.TECNOLOGIA
       );

        BDDMockito.given(pessoaService.alterarPessoa(id, pessoaDTO))
                .willReturn(pessoaAterado);

        String json = mapper.writeValueAsString(pessoaDTO);

        MockHttpServletResponse response = mockMvc.perform(
                put("/pessoa/{id}", id)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        PessoaDTO pessoaDTO1 = mapper.readValue(response.getContentAsString(), PessoaDTO.class);

       System.out.println("Clasee convertida " + pessoaDTO1);

        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals("Maria", pessoaDTO1.nome());


   }

   @Test
    void deveriaDeletarPessoa() throws Exception {


        Long id  = 1L;

       DeleteDTO deleteDTO = new DeleteDTO(
               id,
               "Deu Certo"
       );


        BDDMockito.given(pessoaService.deletarPessoa(id))
                .willReturn(deleteDTO);


        MockHttpServletResponse response = mockMvc.perform(
                delete("/pessoa/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        DeleteDTO deleteDTO1 = mapper.readValue(response.getContentAsString(), DeleteDTO.class);


        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals("Deu Certo", deleteDTO1.nome());

        BDDMockito.then(pessoaService).should().deletarPessoa(id);


   }



}