package controleGastos.Gestao.fearures.receita.service;


import controleGastos.Gestao.fearures.pessoa.dto.DeleteDTO;
import controleGastos.Gestao.fearures.pessoa.dto.PessoaDTO;
import controleGastos.Gestao.fearures.planejamento.dto.PlanejamentoDTO;
import controleGastos.Gestao.fearures.planejamento.model.Planejamento;
import controleGastos.Gestao.fearures.planejamento.repository.PlanejamentoRepository;
import controleGastos.Gestao.fearures.receita.dto.ReceitaDTO;
import controleGastos.Gestao.fearures.receita.model.Receita;
import controleGastos.Gestao.fearures.receita.repository.ReceitaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class ReceitaService {


    @Autowired
    private ReceitaRepository receitaRepository;

    @Autowired
    private PlanejamentoRepository planejamentoRepository;

    public List<ReceitaDTO> listarReceita(){

        List<Receita> receitaList = receitaRepository.findAll();

      List<ReceitaDTO> listReceitaDTO =   receitaList.stream()
                .map(r -> ReceitaDTO.fromEntity(r))
              .toList();

      return listReceitaDTO;


    }


    @Transactional
    public ReceitaDTO criarReceita(ReceitaDTO receitaDTO){

        if (receitaDTO.planejamento() == null) throw new EntityNotFoundException("Planejaento Vazio!!");

        Planejamento planejamento = planejamentoRepository.findById(receitaDTO.planejamento().id())
                .orElseThrow(() -> new EntityNotFoundException("Planejamento Não Encontrado"));

        Receita receita = new Receita();
        receita.setData(receitaDTO.data());
        receita.setValor(receitaDTO.valor());
        receita.setTipoReceita(receitaDTO.tipoReceita());
        receita.setPlanejamento(planejamento);

        Receita receitaSalvo = receitaRepository.save(receita);

        ReceitaDTO receitaDTO1 = ReceitaDTO.fromEntity(receitaSalvo);

        return receitaDTO1;


    }

    @Transactional
    public ReceitaDTO altualizarReceita(Long id, ReceitaDTO receitaDTO){

        Optional<Receita> receitaid = receitaRepository.findById(id);

        if (receitaid.isEmpty()){

            throw new EntityNotFoundException("Receita não encontrada");

        }

        Optional<Planejamento> planejamentoNovo = planejamentoRepository.findById(receitaDTO.planejamento().id());

        if (planejamentoNovo.isEmpty()){

            throw new EntityNotFoundException("Planejamento Não encontrado");
        }



        Receita receita = receitaid.get();
        receita.setData(receitaDTO.data());
        receita.setValor(receitaDTO.valor());
        receita.setPlanejamento(planejamentoNovo.get());

        Receita receitaSalva = receitaRepository.save(receita);

        ReceitaDTO receitaResponse = ReceitaDTO.fromEntity(receitaSalva);

        return receitaResponse;


    }


    @Transactional
    public DeleteDTO deletarReceita(Long id){

        Optional<Receita> receita = receitaRepository.findById(id);

        if (receita.isEmpty()){
            throw new EntityNotFoundException("Receita Não encontrada");
        }

        receitaRepository.deleteById(id);

        DeleteDTO deleteDTO = new DeleteDTO(
                receita.get().getId(),
                String.valueOf(receita.get().getTipoReceita())
        );

        return deleteDTO;


    }

}
