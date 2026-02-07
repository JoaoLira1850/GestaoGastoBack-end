package controleGastos.Gestao.fearures.receita.service;

import controleGastos.Gestao.fearures.pessoa.dto.PessoaDTO;
import controleGastos.Gestao.fearures.pessoa.model.Pessoa;
import controleGastos.Gestao.fearures.planejamento.dto.PlanejamentoDTO;
import controleGastos.Gestao.fearures.planejamento.model.Planejamento;
import controleGastos.Gestao.fearures.planejamento.repository.PlanejamentoRepository;
import controleGastos.Gestao.fearures.receita.dto.ReceitaDTO;
import controleGastos.Gestao.fearures.receita.model.Receita;
import controleGastos.Gestao.fearures.receita.model.TipoReceita;
import controleGastos.Gestao.fearures.receita.repository.ReceitaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
class ReceitaServiceTest {


    @InjectMocks
    private ReceitaService receitaService;


    @Mock
    private PlanejamentoRepository planejamentoRepository;

    @Mock
    private ReceitaRepository receitaRepository;

    @Mock
    private PessoaDTO pessoaDTO;

    private ArgumentCaptor<Long> capturesArguments = ArgumentCaptor.forClass(Long.class);

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private LocalDate date = LocalDate.parse("25/01/2026",formatter);

    @Test
    public void deveriaListaReceita(){

        Planejamento planejamento = new Planejamento();

        Receita receita1 = new Receita(
                date,
                BigDecimal.valueOf(40000),
                TipoReceita.SALARIO,
                planejamento

        );

        Receita receita2 = new Receita(
                date,
                BigDecimal.valueOf(2525.10),
                TipoReceita.RENDA_EXTRA,
                planejamento

        );

        List<Receita> receitaList = new ArrayList<>();
        receitaList.add(receita1);
        receitaList.add(receita2);


        BDDMockito.given(receitaRepository.findAll())
                .willReturn(receitaList);


        var resultado = receitaService.listarReceita();


        Assertions.assertEquals(BigDecimal.valueOf(2525.10),resultado.get(1).valor());
        Assertions.assertEquals(2, resultado.size());
        Assertions.assertEquals(TipoReceita.SALARIO, resultado.get(0).tipoReceita());


    }


    @Test
    void deveriaCriarReceita(){



        PlanejamentoDTO planejamentoDTO = new PlanejamentoDTO(
                2L,
                date,
                "Plan1",
                pessoaDTO
        );


        Long id = 1L;
        ReceitaDTO receita = new ReceitaDTO(
                id,
                date,
                BigDecimal.valueOf(2500),
                TipoReceita.SALARIO,
                planejamentoDTO
        );


        Pessoa pessoa = new Pessoa();

        Planejamento planejamento = new Planejamento(

                "Plan1",
                date,
                pessoa
                );

        BDDMockito.given(planejamentoRepository.findById(receita.planejamento().id()))
                .willReturn(Optional.of(planejamento));

        BDDMockito.given(receitaRepository.save(any(Receita.class)))
                .willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        var resultado = receitaService.criarReceita(receita);

        Assertions.assertEquals(BigDecimal.valueOf(2500), resultado.valor());
        Assertions.assertEquals(TipoReceita.SALARIO, receita.tipoReceita());


        BDDMockito.then(receitaRepository).should().save(any(Receita.class));


    }

    @Test
    void deveriaLancarExcepitionPlanejamentoNaoEncontrado(){



        PlanejamentoDTO planejamentoDTO = new PlanejamentoDTO(
                2L,
                date,
                "Plan1",
                pessoaDTO
        );


        Long id = 1L;
        ReceitaDTO receita = new ReceitaDTO(
                id,
                date,
                BigDecimal.valueOf(2500),
                TipoReceita.SALARIO,
                planejamentoDTO
        );


        Pessoa pessoa = new Pessoa();

        Planejamento planejamento = new Planejamento(

                "Plan1",
                date,
                pessoa
        );

        BDDMockito.given(planejamentoRepository.findById(receita.planejamento().id()))
                .willReturn(Optional.empty());

       // BDDMockito.given(receitaRepository.save(any(Receita.class)))
      //          .willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

      //  var resultado = receitaService.criarReceita(receita);

        Assertions.assertThrows(EntityNotFoundException.class, () -> receitaService.criarReceita(receita));



    }


   @Test
    void deveriaAtualizarReceita(){

        Long id = 1L;

        Planejamento planejamento = new Planejamento();

        Receita receitaAtica = new Receita(
                date,
                BigDecimal.valueOf(2500),
                TipoReceita.SALARIO,
                planejamento
        );


        PlanejamentoDTO planejamentoDTO = new PlanejamentoDTO(
                1L,
                date,
                "Pla1",
                pessoaDTO

        );

        ReceitaDTO receitaDTO = new ReceitaDTO(
                id,
                date,
                BigDecimal.valueOf(3000),
                TipoReceita.SALARIO,
                planejamentoDTO
        );

        BDDMockito.given(receitaRepository.findById(receitaDTO.id()))
                .willReturn(Optional.of(receitaAtica));

        BDDMockito.given(planejamentoRepository.findById(id))
                .willReturn(Optional.of(planejamento));

        BDDMockito.given(receitaRepository.save(any(Receita.class)))
                .willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));


        var resultado = receitaService.altualizarReceita(id, receitaDTO);

        Assertions.assertEquals(BigDecimal.valueOf(3000), resultado.valor());
        Assertions.assertEquals(TipoReceita.SALARIO, resultado.tipoReceita());

        BDDMockito.then(receitaRepository).should().save(any(Receita.class));

   }

    @Test
    void deveriaLancarExeptionReceitaNãoEncontrada(){

        Long id = 1L;

        Planejamento planejamento = new Planejamento();

        Receita receitaAtica = new Receita(
                date,
                BigDecimal.valueOf(2500),
                TipoReceita.SALARIO,
                planejamento
        );


        PlanejamentoDTO planejamentoDTO = new PlanejamentoDTO(
                1L,
                date,
                "Pla1",
                pessoaDTO

        );

        ReceitaDTO receitaDTO = new ReceitaDTO(
                id,
                date,
                BigDecimal.valueOf(3000),
                TipoReceita.SALARIO,
                planejamentoDTO
        );

        BDDMockito.given(receitaRepository.findById(receitaDTO.id()))
                .willReturn(Optional.empty());

     //   BDDMockito.given(planejamentoRepository.findById(id))
      //          .willReturn(Optional.of(planejamento));

      //  BDDMockito.given(receitaRepository.save(any(Receita.class)))
      //          .willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));



        Assertions.assertThrows(EntityNotFoundException.class, () -> receitaService.altualizarReceita(id, receitaDTO));


    }


    @Test
    void deveriaLancarExeptionPlanejamentoNãoEncontrada(){

        Long id = 1L;

        Planejamento planejamento = new Planejamento();

        Receita receitaAtica = new Receita(
                date,
                BigDecimal.valueOf(2500),
                TipoReceita.SALARIO,
                planejamento
        );


        PlanejamentoDTO planejamentoDTO = new PlanejamentoDTO(
                1L,
                date,
                "Pla1",
                pessoaDTO

        );

        ReceitaDTO receitaDTO = new ReceitaDTO(
                id,
                date,
                BigDecimal.valueOf(3000),
                TipoReceita.SALARIO,
                planejamentoDTO
        );

        BDDMockito.given(receitaRepository.findById(receitaDTO.id()))
                .willReturn(Optional.of(receitaAtica));

           BDDMockito.given(planejamentoRepository.findById(id))
                 .willReturn(Optional.empty());

        //  BDDMockito.given(receitaRepository.save(any(Receita.class)))
        //          .willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));



        Assertions.assertThrows(EntityNotFoundException.class, () -> receitaService.altualizarReceita(id, receitaDTO));


    }

    @Test
    void deveriaDeletarReceita(){


        Long id = 1L;

        Planejamento planejamento = new Planejamento();

        Receita receita = new Receita(
                date,
                BigDecimal.valueOf(2500),
                TipoReceita.SALARIO,
                planejamento

        );

        BDDMockito.given(receitaRepository.findById(id))
                .willReturn(Optional.of(receita));


        receitaService.deletarReceita(id);


        BDDMockito.then(receitaRepository).should().deleteById(id);



    }

    @Test
    void deveriaLancarExceptionReceitaNãoEncontrada(){


        Long id = 1L;

        Planejamento planejamento = new Planejamento();

        Receita receita = new Receita(
                date,
                BigDecimal.valueOf(2500),
                TipoReceita.SALARIO,
                planejamento

        );

        BDDMockito.given(receitaRepository.findById(id))
                .willReturn(Optional.empty());


        Assertions.assertThrows(EntityNotFoundException.class, () -> receitaService.deletarReceita(id));


    }







}