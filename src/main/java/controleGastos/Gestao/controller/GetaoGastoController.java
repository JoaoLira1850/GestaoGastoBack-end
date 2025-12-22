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

import controleGastos.Gestao.dto.GestaGastosDTO;
import controleGastos.Gestao.service.GestaoGastosService;

@RestController
@RequestMapping("/gastos")
public class GetaoGastoController {

    @Autowired
    GestaoGastosService gestaoGastosService;


    @GetMapping
    public List<GestaGastosDTO> listaGastos(){
        return gestaoGastosService.listaGastos();
    }


    @PostMapping
    public GestaGastosDTO criarGastos(@RequestBody GestaGastosDTO gestaGastosDTO){

        return gestaoGastosService.criarGasto(gestaGastosDTO);

    }

    @PutMapping("/{id}")
    public Optional<GestaGastosDTO> atualizarGasto(@PathVariable Long id, @RequestBody GestaGastosDTO gestaGastosDTO){

        return gestaoGastosService.atualizarGasto(id, gestaGastosDTO);

    }

    @DeleteMapping("/{id}")
    public void deletarGasto(@PathVariable Long id){

        this.gestaoGastosService.deletarGasto(id);

    }

}
