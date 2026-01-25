package controleGastos.Gestao.fearures.pessoa.service;


import controleGastos.Gestao.fearures.pessoa.dto.DeleteDTO;
import controleGastos.Gestao.fearures.pessoa.dto.PessoaDTO;
import controleGastos.Gestao.fearures.pessoa.model.Pessoa;
import controleGastos.Gestao.fearures.pessoa.repository.PessoaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {


    @Autowired
    private PessoaRepository pessoaRepository;

    public List<PessoaDTO> listarPessoas(){

        List<Pessoa> pessoas = pessoaRepository.findAll();

       List<PessoaDTO> pessoaDTOList = pessoas.stream()
                .map(PessoaDTO::new)
                .toList();

        return pessoaDTOList;

    }

    @Transactional
    public PessoaDTO criarPessoa(PessoaDTO pessoaDTO){

        Pessoa pessoa = new Pessoa(pessoaDTO);

        Pessoa pessoaSalva = pessoaRepository.save(pessoa);

        PessoaDTO pessoaDTO1 = new PessoaDTO(pessoaSalva);

        return pessoaDTO1;

    }

    @Transactional
    public PessoaDTO alterarPessoa(Long id, PessoaDTO pessoaDTO){

        Optional<Pessoa> pessoa = pessoaRepository.findById(id);

        if (pessoa.isPresent()){
            Pessoa pessoaAtualizar = pessoa.get();
            pessoaAtualizar.setNome(pessoaDTO.nome());
            pessoaAtualizar.setSexo(pessoaDTO.sexo());
            pessoaAtualizar.setProfissao(pessoaDTO.profissao());
            pessoaAtualizar.setArea(pessoaDTO.area());

            Pessoa pessoaAtualizada = pessoaRepository.save(pessoaAtualizar);


            PessoaDTO pessoaDTO1 = new PessoaDTO(pessoaAtualizada);

            return pessoaDTO1;
        }else{
            throw new EntityNotFoundException("Usuario Não encontrato");
        }

    }

    @Transactional
    public DeleteDTO deletarPessoa(Long id){

            Optional<Pessoa> pessoa = pessoaRepository.findById(id);

            if (pessoa.isEmpty()){
                throw new EntityNotFoundException("Usuario Não Encontrado!!");
            }

            pessoaRepository.deleteById(id);

            DeleteDTO deleteDTO = new DeleteDTO(
                    pessoa.get().getId(),
                    pessoa.get().getNome()
            );

        return deleteDTO;



    }

}
