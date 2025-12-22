package controleGastos.Gestao.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import controleGastos.Gestao.dto.GestaGastosDTO;
import controleGastos.Gestao.model.GestaGastosEntity;
import controleGastos.Gestao.repository.GestaoGastosRepository;

@Service
public class GestaoGastosService {

    @Autowired
    GestaoGastosRepository gestaoGastosRepository;


    public List<GestaGastosDTO> listaGastos(){

        List<GestaGastosEntity> gastos =  gestaoGastosRepository.findAll();

        return gastos.stream().map(GestaGastosDTO :: new ).toList();
        
        
    }


    public GestaGastosDTO criarGasto( GestaGastosDTO gestaGastosDTO){

        GestaGastosEntity gastosEntity = new GestaGastosEntity(gestaGastosDTO);

        gestaoGastosRepository.save(gastosEntity);

        GestaGastosDTO gastosDTO = new GestaGastosDTO(gastosEntity);

        return gastosDTO;

    }

    public Optional<GestaGastosDTO> atualizarGasto(Long id, GestaGastosDTO gestaGastosDTO){

        Optional<GestaGastosEntity> gastoEntity = gestaoGastosRepository.findById(id);

        if(gastoEntity.isPresent()){
            GestaGastosEntity gastoAtualizar = gastoEntity.get();
            gastoAtualizar.setData(gestaGastosDTO.getData());
            gastoAtualizar.setQuant(gestaGastosDTO.getQuant());
            gastoAtualizar.setTipoGasto(gestaGastosDTO.getTipoGasto());
            gastoAtualizar.setValor(gestaGastosDTO.getValor());

            gestaoGastosRepository.save(gastoAtualizar);

            GestaGastosDTO gastosDTO = new GestaGastosDTO(gastoAtualizar);

            return Optional.of(gastosDTO);


        }else{
            return Optional.empty();
        }
        
    }


    public void deletarGasto(Long id){

        Optional<GestaGastosEntity> gasto = gestaoGastosRepository.findById(id);

        if(gasto.isPresent()){

            GestaGastosEntity gastosEntity = gasto.get();
            this.gestaoGastosRepository.delete(gastosEntity);
        }else{
            System.out.println("Erro a Deletar o gasto");
        }
    }

}
