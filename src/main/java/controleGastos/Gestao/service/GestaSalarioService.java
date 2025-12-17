package controleGastos.Gestao.service;

import java.util.List;

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

}
