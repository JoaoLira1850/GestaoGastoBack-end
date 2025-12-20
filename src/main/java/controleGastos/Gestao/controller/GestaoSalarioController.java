package controleGastos.Gestao.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import controleGastos.Gestao.dto.GestaSalarioDTO;
import controleGastos.Gestao.service.GestaSalarioService;

@RestController
@RequestMapping("/salario")
public class GestaoSalarioController {


    @Autowired
    private GestaSalarioService gestaSalarioService;

    @GetMapping()
    public List<GestaSalarioDTO> buscarSalarioDTO(){

        return gestaSalarioService.listarSalario();

    

    }

    @PostMapping
    public GestaSalarioDTO criarSalario( @RequestBody GestaSalarioDTO gestaSalarioDTO){

        return gestaSalarioService.criarSalario(gestaSalarioDTO);

    }

    @PutMapping("/{id}")
    public Optional<GestaSalarioDTO> atualizarSalario( @PathVariable Long id, @RequestBody GestaSalarioDTO gestaSalarioDTO){

        return gestaSalarioService.alterarSalario(gestaSalarioDTO, id);

    }

    @DeleteMapping("/{id}")
    public Optional<GestaSalarioDTO> deletarSalario(@PathVariable Long id){

        return gestaSalarioService.deletarSalario(id);

    }

}

