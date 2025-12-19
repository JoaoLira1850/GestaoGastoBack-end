package controleGastos.Gestao.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import controleGastos.Gestao.dto.GestaSalarioDTO;
import controleGastos.Gestao.model.GestaoSalarioEntity;
import controleGastos.Gestao.repository.GestaoSalarioRepository;

@Service
public class GestaSalarioService {

    @Autowired
    private GestaoSalarioRepository gestaoSalarioRepository;


    public List<GestaSalarioDTO> listarSalario(){

       List<GestaoSalarioEntity> listaSalario = gestaoSalarioRepository.findAll();
        
        return listaSalario.stream().map(GestaSalarioDTO :: new ).toList();
        
    }


    public GestaSalarioDTO criarSalario (GestaSalarioDTO gestaSalarioDTO){

        GestaoSalarioEntity gestaoSalarioEntity = new GestaoSalarioEntity(gestaSalarioDTO);

        GestaoSalarioEntity criarSalario =  gestaoSalarioRepository.save(gestaoSalarioEntity);

        return new GestaSalarioDTO(criarSalario); 
        



    }


    public Optional<GestaSalarioDTO> deletarSalario( Long id){

        Optional<GestaoSalarioEntity> salarioBuscado = gestaoSalarioRepository.findById(id);

        if(salarioBuscado.isPresent()){
            GestaoSalarioEntity salario = salarioBuscado.get();

            gestaoSalarioRepository.delete(salario);

            

            return Optional.of( new GestaSalarioDTO(salarioBuscado.get()));
            

        }else{
            return  Optional.empty();
        }
    }


    public Optional<GestaSalarioDTO> alterarSalario( GestaSalarioDTO gestaSalarioDTO, Long id){


        Optional<GestaoSalarioEntity> salarioBuscado = gestaoSalarioRepository.findById(id);

        if(salarioBuscado.isPresent()){
            GestaoSalarioEntity salarioAlterar = salarioBuscado.get();

            salarioAlterar.setData(gestaSalarioDTO.getData());
            salarioAlterar.setFuncao(gestaSalarioDTO.getFuncao());
            salarioAlterar.setSalario(gestaSalarioDTO.getSalario());
            
            gestaoSalarioRepository.save(salarioAlterar);

            GestaSalarioDTO salarioDTO = new GestaSalarioDTO(salarioAlterar);

            return Optional.of(salarioDTO);


        }else{
            return Optional.empty();
        }

    }

}
