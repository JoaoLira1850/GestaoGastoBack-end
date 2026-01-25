package controleGastos.Gestao.fearures.planejamento.service;

import controleGastos.Gestao.fearures.pessoa.dto.PessoaDTO;
import controleGastos.Gestao.fearures.pessoa.model.AreaProfissonal;
import controleGastos.Gestao.fearures.pessoa.model.Pessoa;
import controleGastos.Gestao.fearures.pessoa.model.Sexo;
import controleGastos.Gestao.fearures.pessoa.repository.PessoaRepository;
import controleGastos.Gestao.fearures.planejamento.dto.PlanejamentoDTO;
import controleGastos.Gestao.fearures.planejamento.model.Planejamento;
import controleGastos.Gestao.fearures.planejamento.repository.PlanejamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
class PlanejamentoServiceTest {


    @InjectMocks
    private PlanejamentoService planejamentoService;

    @Mock
    private PlanejamentoRepository planejamentoRepository;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private PlanejamentoDTO planejamentoDTO;


    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    LocalDate data = LocalDate.parse("20/10/2001", formatter);

    ArgumentCaptor<Planejamento> argumentCaptor = ArgumentCaptor.forClass(Planejamento.class);

    @Test
    void deveriaListarPlanejamento(){


        Pessoa pessoa2 = new Pessoa(
                "Maria",
                Sexo.FEMININO,
                data,
                "Medica",
                AreaProfissonal.SAUDE

        );

        Planejamento planejamentoDTO = new Planejamento(
                "Plan1",
                data,
                pessoa2

        );

        List<Planejamento> planlIsta = new ArrayList<>();
        planlIsta.add(planejamentoDTO);

        BDDMockito.given(planejamentoRepository.findAll())
                .willReturn(planlIsta);

        var resultado = planejamentoService.listarPlanejamentos();


        Assertions.assertEquals("Maria", resultado.get(0).pessoaDTO().nome());
        Assertions.assertEquals("Plan1", resultado.get(0).nome());
        Assertions.assertEquals(1, resultado.size());


    }

    @Test
    void deveriaCriarPlanejamento(){

        Long id = 1L;
        Pessoa pessoa2 = new Pessoa(
                "Maria",
                Sexo.FEMININO,
                data,
                "Medica",
                AreaProfissonal.SAUDE

        );

        Planejamento planejamento = new Planejamento(
                "Plan1",
                data,
                pessoa2

        );

        Long id1 = 1L;
        PessoaDTO pessoa22 = new PessoaDTO(
                id1,
                "Maria",
                Sexo.FEMININO,
                data,
                "Medica",
                AreaProfissonal.SAUDE

        );

        PlanejamentoDTO planejamento22 = new PlanejamentoDTO(
                id,
                data,
                "Plan1",
                pessoa22

        );

        BDDMockito.given(pessoaRepository.findById(id))
                .willReturn(Optional.of(pessoa2));
        BDDMockito.given(planejamentoRepository.save(any(Planejamento.class)))
                .willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        var resultado = planejamentoService.criarPlanejemamento(planejamento22);


        Assertions.assertEquals(AreaProfissonal.SAUDE, resultado.pessoaDTO().area());
        Assertions.assertEquals("Medica", resultado.pessoaDTO().profissao());

        BDDMockito.then(pessoaRepository).should().findById(id);
        BDDMockito.then(planejamentoRepository).should().save(argumentCaptor.capture());



    }

    @Test
    public void deveriaAtualizarPlanejamento(){

        Long id = 1L;
        Pessoa pessoaSAlva = new Pessoa(
                "Joao Vitor",
                Sexo.MASCULINO,
                data,
                "Dev junior",
                AreaProfissonal.TECNOLOGIA
        );

        Planejamento planejamentoSAlvo = new Planejamento(
                "Plan1",
                data,
                pessoaSAlva

        );


        PessoaDTO pessoaAtualizarDTO = new PessoaDTO(
                id,
                "Joao Vitor",
                Sexo.MASCULINO,
                data,
                "Dev junior",
                AreaProfissonal.TECNOLOGIA
        );

        PlanejamentoDTO planejamentoAtualizar = new PlanejamentoDTO(
                1L,
                data,
                "Planejamento MEs tal",
                pessoaAtualizarDTO


        );

        BDDMockito.given(planejamentoRepository.findById(id))
                .willReturn(Optional.of(planejamentoSAlvo));

        BDDMockito.given(pessoaRepository.findById(id))
                .willReturn(Optional.of(pessoaSAlva));


        BDDMockito.given(planejamentoRepository.save(any(Planejamento.class)))
                .willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));


        var resultado = planejamentoService.atualizarPlanejamento(id, planejamentoAtualizar);


        Assertions.assertEquals("Planejamento MEs tal", resultado.nome());
        Assertions.assertEquals("Joao Vitor", resultado.pessoaDTO().nome());
    }




    @Test
    public void deveriaLancarExecaoPlanejamentoNaoEncontrado(){

        Long id = 1L;
        Pessoa pessoaSAlva = new Pessoa(
                "Joao Vitor",
                Sexo.MASCULINO,
                data,
                "Dev junior",
                AreaProfissonal.TECNOLOGIA
        );

        Planejamento planejamentoSAlvo = new Planejamento(
                "Plan1",
                data,
                pessoaSAlva

        );


        PessoaDTO pessoaAtualizarDTO = new PessoaDTO(
                id,
                "Joao Vitor",
                Sexo.MASCULINO,
                data,
                "Dev junior",
                AreaProfissonal.TECNOLOGIA
        );

        PlanejamentoDTO planejamentoAtualizar = new PlanejamentoDTO(
                1L,
                data,
                "Planejamento MEs tal",
                pessoaAtualizarDTO


        );

        BDDMockito.given(planejamentoRepository.findById(id))
                .willReturn(Optional.empty());

       // BDDMockito.given(pessoaRepository.findById(id))
           //     .willReturn(Optional.of(pessoaSAlva));


       // BDDMockito.given(planejamentoRepository.save(any(Planejamento.class)))
       //         .willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));


        //planejamentoService.atualizarPlanejamento(id, planejamentoAtualizar);


        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, ()-> planejamentoService.atualizarPlanejamento(id, planejamentoAtualizar));

        Assertions.assertEquals("Planejamento Não encontrado", exception.getMessage());
    }

    @Test
    public void deveriaLancarExecaoPessoaNaoEncontrado(){

        Long id = 1L;
        Pessoa pessoaSAlva = new Pessoa(
                "Joao Vitor",
                Sexo.MASCULINO,
                data,
                "Dev junior",
                AreaProfissonal.TECNOLOGIA
        );

        Planejamento planejamentoSAlvo = new Planejamento(
                "Plan1",
                data,
                pessoaSAlva

        );


        PessoaDTO pessoaAtualizarDTO = new PessoaDTO(
                1L,
                "Joao Vitor",
                Sexo.MASCULINO,
                data,
                "Dev junior",
                AreaProfissonal.TECNOLOGIA
        );

        PlanejamentoDTO planejamentoAtualizar = new PlanejamentoDTO(
                1L,
                data,
                "Planejamento MEs tal",
                pessoaAtualizarDTO


        );

        BDDMockito.given(planejamentoRepository.findById(id))
                .willReturn(Optional.of(planejamentoSAlvo));

         BDDMockito.given(pessoaRepository.findById(id))
            .willReturn(Optional.empty());


        // BDDMockito.given(planejamentoRepository.save(any(Planejamento.class)))
        //         .willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));


        //planejamentoService.atualizarPlanejamento(id, planejamentoAtualizar);


        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, ()-> planejamentoService.atualizarPlanejamento(id, planejamentoAtualizar));

        Assertions.assertEquals("Pessoa não encontrada", exception.getMessage());
    }


    @Test
    void deveriaDeletarPlanejamento(){

        Long id = 1L;
        Pessoa pessoaSAlva = new Pessoa(
                "Joao Vitor",
                Sexo.MASCULINO,
                data,
                "Dev junior",
                AreaProfissonal.TECNOLOGIA
        );

        Planejamento planejamentoSAlvo = new Planejamento(
                "Plan1",
                data,
                pessoaSAlva

        );


        BDDMockito.given(planejamentoRepository.findById(id))
                .willReturn(Optional.of(planejamentoSAlvo));

        planejamentoService.deletarPlanejamento(id);


        BDDMockito.then(planejamentoRepository).should().deleteById(id);



    }

    @Test
    void naoDeveriaDeletarPlanejamentoLancarException(){

        Long id = 1L;
        Pessoa pessoaSAlva = new Pessoa(
                "Joao Vitor",
                Sexo.MASCULINO,
                data,
                "Dev junior",
                AreaProfissonal.TECNOLOGIA
        );

        Planejamento planejamentoSAlvo = new Planejamento(
                "Plan1",
                data,
                pessoaSAlva

        );


        BDDMockito.given(planejamentoRepository.findById(id))
                .willReturn(Optional.empty());


        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, ()-> planejamentoService.deletarPlanejamento(id));

        Assertions.assertEquals("Pessoa não encontrada", exception.getMessage());



    }




}