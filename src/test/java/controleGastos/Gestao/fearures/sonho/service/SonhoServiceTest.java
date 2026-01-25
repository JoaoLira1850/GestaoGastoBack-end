package controleGastos.Gestao.fearures.sonho.service;

import controleGastos.Gestao.fearures.pessoa.dto.PessoaDTO;
import controleGastos.Gestao.fearures.pessoa.model.AreaProfissonal;
import controleGastos.Gestao.fearures.pessoa.model.Pessoa;
import controleGastos.Gestao.fearures.pessoa.model.Sexo;
import controleGastos.Gestao.fearures.pessoa.repository.PessoaRepository;
import controleGastos.Gestao.fearures.sonho.dto.SonhoDTO;
import controleGastos.Gestao.fearures.sonho.model.Sonho;
import controleGastos.Gestao.fearures.sonho.model.StatusSonho;
import controleGastos.Gestao.fearures.sonho.model.TipoSonho;
import controleGastos.Gestao.fearures.sonho.repository.SonhoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class SonhoServiceTest {

    @InjectMocks
    private SonhoService sonhoService;


    @Mock
    private SonhoRepository sonhoRepository;


    @Mock
    private PessoaRepository pessoaRepository;


    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private LocalDate data = LocalDate.parse("21/01/2026",formatter);

    @Test
    void deveriaListarSonho(){

        Long idSonho = 1L;
        Pessoa pessoa = new Pessoa(
               "Joao Vitor ",
                Sexo.MASCULINO,
                data,
                "Dev junior",
                AreaProfissonal.TECNOLOGIA
        );

        Sonho sonho = new Sonho(
                data,
                TipoSonho.CARRO,
                BigDecimal.valueOf(40000),
                StatusSonho.ATIVO,
                pessoa
        );

        Sonho sonho2 = new Sonho(
                data,
                TipoSonho.MOTO,
                BigDecimal.valueOf(15000),
                StatusSonho.ATIVO,
                pessoa
        );

        List<Sonho> sonhoList = new ArrayList<>();
        sonhoList.add(sonho);
        sonhoList.add(sonho2);


        BDDMockito.given(sonhoRepository.findAll())
                .willReturn(sonhoList);

        var resultado = sonhoService.listarSonhos();


        Assertions.assertEquals(TipoSonho.CARRO, resultado.get(0).sonho());
        Assertions.assertEquals(TipoSonho.MOTO, resultado.get(1).sonho());

    }

    @Test
    void deveriaCriarsonho(){

        Long idSonho = 1L;
        Pessoa pessoa = new Pessoa(
                "Joao Vitor ",
                Sexo.MASCULINO,
                data,
                "Dev junior",
                AreaProfissonal.TECNOLOGIA
        );

        pessoa.setId(idSonho);


        Sonho sonho = new Sonho(
                data,
                TipoSonho.CARRO,
                BigDecimal.valueOf(40000),
                StatusSonho.ATIVO,
                pessoa
        );

        SonhoDTO sonhoDTO = new SonhoDTO(sonho);

        BDDMockito.given(pessoaRepository.findById(idSonho))
                .willAnswer(invocationOnMock ->  Optional.of(pessoa));

        BDDMockito.given(sonhoRepository.save(Mockito.any(Sonho.class)))
                .willAnswer(invocation -> invocation.getArgument(0));


        var resultado = sonhoService.criarSonho(sonhoDTO);

        Assertions.assertEquals(TipoSonho.CARRO, resultado.sonho());

        Assertions.assertEquals(BigDecimal.valueOf(40000), resultado.valorSonho());



    }

    @Test
    void deveriaAtualizarSonho(){

        Long id = 1L;

        Pessoa pessoa = new Pessoa(
                "Joao Vitor ",
                Sexo.MASCULINO,
                data,
                "Dev junior",
                AreaProfissonal.TECNOLOGIA
        );

        Sonho sonhoAtingo = new Sonho(
                data,
                TipoSonho.CARRO,
                BigDecimal.valueOf(40000),
                StatusSonho.ATIVO,
                pessoa
        );

        PessoaDTO pessoaDTO = new PessoaDTO(
                1L,
                "Joao vitor",
                Sexo.MASCULINO,
                data,
                "Dev Junior",
                AreaProfissonal.TECNOLOGIA
        );

        SonhoDTO sonhoAtualizado= new SonhoDTO(
                id,
                data,
                TipoSonho.MOTO,
                BigDecimal.valueOf(15000),
                StatusSonho.ATIVO,
                pessoaDTO
        );


        BDDMockito.given(sonhoRepository.findById(id))
                .willReturn(Optional.of(sonhoAtingo));

        BDDMockito.given(sonhoRepository.save(Mockito.any(Sonho.class)))
                .willAnswer(invocation -> invocation.getArgument(0));


        var resultado = sonhoService.alterarSonho(id, sonhoAtualizado);


        Assertions.assertEquals(TipoSonho.MOTO, resultado.sonho());
        Assertions.assertEquals(BigDecimal.valueOf(15000), resultado.valorSonho());

        BDDMockito.then(sonhoRepository).should().save(Mockito.any(Sonho.class));
        BDDMockito.then(sonhoRepository).should().findById(id);


    }



}