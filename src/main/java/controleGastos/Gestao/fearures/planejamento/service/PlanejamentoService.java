package controleGastos.Gestao.fearures.planejamento.service;


import controleGastos.Gestao.fearures.pessoa.dto.DeleteDTO;
import controleGastos.Gestao.fearures.pessoa.dto.PessoaDTO;
import controleGastos.Gestao.fearures.pessoa.model.Pessoa;
import controleGastos.Gestao.fearures.pessoa.repository.PessoaRepository;
import controleGastos.Gestao.fearures.planejamento.dto.PlanejamentoDTO;
import controleGastos.Gestao.fearures.planejamento.model.Planejamento;
import controleGastos.Gestao.fearures.planejamento.repository.PlanejamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlanejamentoService {


    @Autowired
    private PlanejamentoRepository planejamentoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    public List<PlanejamentoDTO> listarPlanejamentos() {

        List<Planejamento> plan = planejamentoRepository.findAll();

        List<PlanejamentoDTO> planDTO = plan.stream()
                .map(p -> new PlanejamentoDTO(
                        p.getId(),
                        p.getData(),
                        p.getNome(),
                        new PessoaDTO(
                                p.getPessoa().getId(),
                                p.getPessoa().getNome(),
                                p.getPessoa().getSexo(),
                                p.getPessoa().getDataNacimento(),
                                p.getPessoa().getProfissao(),
                                p.getPessoa().getArea()
                        )
                )).collect(Collectors.toList());

        return planDTO;
    }

    @Transactional
    public PlanejamentoDTO criarPlanejemamento(PlanejamentoDTO planejamentoDTO) {


        Optional<Pessoa> pessoa = pessoaRepository.findById(planejamentoDTO.pessoaDTO().id());

        if (pessoa.isEmpty()){

            throw new EntityNotFoundException("Pessoa não encontrada");
        }

        Planejamento planejamento = new Planejamento(
                planejamentoDTO.nome(),
                planejamentoDTO.data(),
                pessoa.get()
        );


        Planejamento criarPlanejamento = planejamentoRepository.save(planejamento);
        System.out.println("Planejamento criado com sucesso");


       var planDTO = new PessoaDTO(
               pessoa.get().getId(),
               pessoa.get().getNome(),
               pessoa.get().getSexo(),
               pessoa.get().getDataNacimento(),
               pessoa.get().getProfissao(),
               pessoa.get().getArea()
        );
        return new PlanejamentoDTO(
                criarPlanejamento.getId(),
                criarPlanejamento.getData(),
                planejamentoDTO.nome(),
               planDTO
        );


    }

    @Transactional
    public PlanejamentoDTO atualizarPlanejamento(Long id, PlanejamentoDTO planejamentoDTO) {

        Optional<Planejamento> planejamento = planejamentoRepository.findById(id);

        if (planejamento.isEmpty()){
            throw new EntityNotFoundException("Planejamento Não encontrado");

        }


        Optional<Pessoa> pessoa =  pessoaRepository.findById(planejamentoDTO.pessoaDTO().id());

        if (pessoa.isEmpty()){
            throw new EntityNotFoundException("Pessoa não encontrada");
        }

        Planejamento planAtualizado = planejamento.get();
        planAtualizado.setId(planejamentoDTO.id());
        planAtualizado.setData(planejamentoDTO.data());
        planAtualizado.setNome(planejamentoDTO.nome());
        planAtualizado.setPessoa(pessoa.get());


        planejamentoRepository.save(planAtualizado);


        PessoaDTO pessoaDTO = new PessoaDTO(
                pessoa.get().getId(),
                pessoa.get().getNome(),
                pessoa.get().getSexo(),
                pessoa.get().getDataNacimento(),
                pessoa.get().getProfissao(),
                pessoa.get().getArea()
        );

        PlanejamentoDTO planejamentoDTO1 = new PlanejamentoDTO(
                planAtualizado.getId(),
                planAtualizado.getData(),
                planejamentoDTO.nome(),
                pessoaDTO
        );


        return planejamentoDTO1;




    }

    @Transactional
    public DeleteDTO deletarPlanejamento(Long id) {

        Optional<Planejamento> planejamento = planejamentoRepository.findById(id);

        if (planejamento.isEmpty()){
            throw new EntityNotFoundException("Pessoa não encontrada");
        }

        planejamentoRepository.deleteById(id);


        DeleteDTO deleteDTO = new DeleteDTO(
                planejamento.get().getId(),
                planejamento.get().getNome()
        );

        return deleteDTO;

    }



}
