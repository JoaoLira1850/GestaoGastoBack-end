package controleGastos.Gestao.fearures.despesas.service;


import controleGastos.Gestao.fearures.despesas.dto.DespesaDTO;
import controleGastos.Gestao.fearures.despesas.model.Despesa;
import controleGastos.Gestao.fearures.despesas.repository.DespesaRepository;
import controleGastos.Gestao.fearures.pessoa.dto.DeleteDTO;
import controleGastos.Gestao.fearures.planejamento.model.Planejamento;
import controleGastos.Gestao.fearures.planejamento.repository.PlanejamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DespesaService {

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private PlanejamentoRepository planejamentoRepository;


    public List<DespesaDTO> listDespesas(){

        List<Despesa> despesaList = despesaRepository.findAll();

        List<DespesaDTO> despesaDTOList = despesaList.stream()
                .map(d -> DespesaDTO.FromEntity(d))
                .toList();

        return despesaDTOList;

    }


    public DespesaDTO createDespesa(DespesaDTO despesaDTO){


        Optional<Planejamento> planejamento = planejamentoRepository.findById(despesaDTO.planejamentoDTO().id());

        if (planejamento.isEmpty()){
            throw new EntityNotFoundException("Planejamento não encontrado");
        }


        Despesa despesa = new Despesa(
                despesaDTO.data(),
                despesaDTO.valor(),
                planejamento.get(),
                despesaDTO.categoriaDespesa(),
                despesaDTO.quantidade()
        );

       Despesa despesa1 =  despesaRepository.save(despesa);

       return DespesaDTO.FromEntity(despesa1);


    }


    public DespesaDTO updateDespesa(Long id, DespesaDTO despesaDTO){

        Optional<Planejamento> planejamento = planejamentoRepository.findById(despesaDTO.planejamentoDTO().id());

        if (planejamento.isEmpty()){
            throw new EntityNotFoundException("Planejamento não encontado");
        }

        Optional<Despesa> receita = despesaRepository.findById(id);

        if (receita.isEmpty()){

            throw new EntityNotFoundException("Receita não encontrada");
        }

        Despesa atualizarDespesa = receita.get();
        atualizarDespesa.setCategoriaDespesa(despesaDTO.categoriaDespesa());
        atualizarDespesa.setData(despesaDTO.data());
        atualizarDespesa.setPlanejamento(planejamento.get());
        atualizarDespesa.setValor(despesaDTO.valor());
        atualizarDespesa.setQuantidade(despesaDTO.quantidade());

        Despesa despesaUpdated = despesaRepository.save(atualizarDespesa);

        return DespesaDTO.FromEntity(despesaUpdated);



    }

    public DeleteDTO deleteDespesa(Long id){

        Optional<Despesa> despesa = despesaRepository.findById(id);

        if (despesa.isEmpty()){

            throw new EntityNotFoundException("Despesa não encontrada");
        }

       despesaRepository.deleteById(id);

        DeleteDTO deleteDTO = new DeleteDTO(despesa.get().getId(), String.valueOf(despesa.get().getCategoriaDespesa()));

        return deleteDTO;
    }



}
