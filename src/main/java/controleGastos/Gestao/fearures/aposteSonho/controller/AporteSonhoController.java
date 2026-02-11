package controleGastos.Gestao.fearures.aposteSonho.controller;

import controleGastos.Gestao.fearures.aposteSonho.dto.AposteSonhoDTO;
import controleGastos.Gestao.fearures.aposteSonho.service.AposteSonhoService;
import controleGastos.Gestao.fearures.pessoa.dto.DeleteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/aporteSonho")
public class AporteSonhoController {


    @Autowired
    private AposteSonhoService aposteSonhoService;


    @GetMapping
    public ResponseEntity<List<AposteSonhoDTO>> listContribuintion(){


        List<AposteSonhoDTO> aposteSonhoDTO = aposteSonhoService.listContribution();

        return ResponseEntity.ok(aposteSonhoDTO);


    }

    @PostMapping
    public ResponseEntity<AposteSonhoDTO> createContribution(@RequestBody AposteSonhoDTO aposteSonhoDTO, UriComponentsBuilder uri){


        AposteSonhoDTO aposteSonhoDTO1 = aposteSonhoService.createContribution(aposteSonhoDTO);

        var uris = uri.path("/aporteSonho/{id}").buildAndExpand(aposteSonhoDTO1.id()).toUri();

        return ResponseEntity.created(uris).body(aposteSonhoDTO1);



    }

    @PutMapping("/{id}")
    public ResponseEntity<AposteSonhoDTO> updateContribution(@PathVariable Long id, @RequestBody AposteSonhoDTO aposteSonhoDTO){

        AposteSonhoDTO aposteSonhoDTO1 = aposteSonhoService.updateContribution(id, aposteSonhoDTO);


        return ResponseEntity.ok(aposteSonhoDTO);



    }


    @DeleteMapping("/id")
    public ResponseEntity<DeleteDTO> deleteContribution(@PathVariable Long id){

        DeleteDTO deleteDTO = aposteSonhoService.deleteContribution(id);


        return ResponseEntity.ok(deleteDTO);

    }



}
