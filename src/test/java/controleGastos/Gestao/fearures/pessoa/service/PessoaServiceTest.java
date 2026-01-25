package controleGastos.Gestao.fearures.pessoa.service;

import controleGastos.Gestao.fearures.pessoa.dto.PessoaDTO;
import controleGastos.Gestao.fearures.pessoa.model.AreaProfissonal;
import controleGastos.Gestao.fearures.pessoa.model.Pessoa;
import controleGastos.Gestao.fearures.pessoa.model.Sexo;
import controleGastos.Gestao.fearures.pessoa.repository.PessoaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class PessoaServiceTest {

    @InjectMocks
    private PessoaService pessoaService;

    @Mock
    private PessoaRepository pessoaRepository;


    @Mock
    private ArgumentCaptor<Pessoa> captor = ArgumentCaptor.forClass(Pessoa.class);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate data = LocalDate.parse("20/10/2001", formatter);

    @Test
    public void deveListaPessoas(){

        //ARRANGE
        Pessoa pessoa1 = new Pessoa(
                    "Joao Vitor",
                Sexo.MASCULINO,
                data,
                "Dev",
                AreaProfissonal.TECNOLOGIA

        );

        Pessoa pessoa2 = new Pessoa(
                "Maria",
                Sexo.FEMININO,
                data,
                "Medica",
                AreaProfissonal.SAUDE

        );

        List<Pessoa> pessoaDTOList = new ArrayList<>();
        pessoaDTOList.add(pessoa1);
        pessoaDTOList.add(pessoa2);

        BDDMockito.given(pessoaRepository.findAll()).willReturn(pessoaDTOList);

        //ACT

        var resultado = pessoaService.listarPessoas();


        //ASSERT

        Assertions.assertEquals(Sexo.MASCULINO, resultado.get(0).sexo());
        Assertions.assertEquals("Joao Vitor", resultado.get(0).nome());
        Assertions.assertEquals(2, resultado.size());


        Assertions.assertEquals("Maria", resultado.get(1).nome());
        Assertions.assertEquals(Sexo.FEMININO, resultado.get(1).sexo());


        BDDMockito.then(pessoaRepository).should().findAll();


    }

    @Test
    public void deveriaCriarPessoa(){

        PessoaDTO pessoa2 = new PessoaDTO(
                1L,
                "Maria",
                Sexo.FEMININO,
                data,
                "Medica",
                AreaProfissonal.SAUDE

        );

        Pessoa pessoa = new Pessoa(pessoa2);

        BDDMockito.given(pessoaRepository.save(Mockito.any(Pessoa.class)))
                .willAnswer(invocation -> invocation.getArgument(0));



        //ACT

        var resultado = pessoaService.criarPessoa(pessoa2);

        //ASSERT

        Assertions.assertEquals("Maria",resultado.nome());
        Assertions.assertEquals(Sexo.FEMININO, resultado.sexo());
        Assertions.assertEquals(AreaProfissonal.SAUDE, resultado.area());

        BDDMockito.then(pessoaRepository).should().save(Mockito.any(Pessoa.class));




    }

    @Test
    void deveriaAtualizarPessoa(){

        //ARANGE
        Long id = 1L;

        Pessoa pessoaSalva=new Pessoa(
                "Joao Vitor",
                Sexo.MASCULINO,
                data,
                "Dev Junior",
                AreaProfissonal.TECNOLOGIA
        );

        PessoaDTO pessoaAtualizada =new PessoaDTO(
                id,
                "Maria",
                Sexo.FEMININO,
                data,
                "Dev Senior",
                AreaProfissonal.TECNOLOGIA
        );



        BDDMockito.given(pessoaRepository.findById(id))
                        .willReturn(Optional.of(pessoaSalva));


        BDDMockito.given(pessoaRepository.save(Mockito.any(Pessoa.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        //ACT

        var resultado = pessoaService.alterarPessoa(id, pessoaAtualizada);

        //ASSERT

        Assertions.assertEquals(Sexo.FEMININO, resultado.sexo());

        BDDMockito.then(pessoaRepository).should().save(Mockito.any(Pessoa.class));

    }


    @Test
    void deveriaDeletarPessoa(){

        //ARRAGE
        Long id = 1L;

        Pessoa pessoaSalva=new Pessoa(
                "Joao Vitor",
                Sexo.MASCULINO,
                data,
                "Dev Junior",
                AreaProfissonal.TECNOLOGIA
        );

        BDDMockito.given(pessoaRepository.findById(id))
                .willReturn(Optional.of(pessoaSalva));


        BDDMockito.willDoNothing()
                .given(pessoaRepository)
                .deleteById(id);

        var resultado = pessoaService.deletarPessoa(id);

        Assertions.assertEquals("Joao Vitor", resultado.nome());


        BDDMockito.then(pessoaRepository).should().deleteById(id);
        BDDMockito.then(pessoaRepository).should().findById(id);


    }


}