package controleGastos.Gestao.fearures.sonho.service;

import controleGastos.Gestao.fearures.pessoa.dto.DeleteDTO;
import controleGastos.Gestao.fearures.pessoa.model.Pessoa;
import controleGastos.Gestao.fearures.pessoa.repository.PessoaRepository;
import controleGastos.Gestao.fearures.sonho.dto.SonhoDTO;
import controleGastos.Gestao.fearures.sonho.model.Sonho;
import controleGastos.Gestao.fearures.sonho.repository.SonhoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SonhoService {

    @Autowired
    private SonhoRepository sonhoRepository;


    @Autowired
    private PessoaRepository pessoaRepository;


    public List<SonhoDTO> listarSonhos(){

        List<Sonho> sonhoList = sonhoRepository.findAll();

        List<SonhoDTO> sonhoDTOS = sonhoList.stream()
                .map(SonhoDTO::new)
                .toList();

        return sonhoDTOS;

    }

    @Transactional
    public SonhoDTO criarSonho(SonhoDTO sonhoDTO){

        Optional<Pessoa> pessoa = pessoaRepository.findById(sonhoDTO.pessoa().id());


        if (pessoa.isEmpty()) {

            throw new EntityNotFoundException("Não tem sonho cadastrado!!");

        }
        Pessoa pessoa1 = pessoa.get();


        Sonho sonho = new Sonho(
                sonhoDTO.data(),
                sonhoDTO.sonho(),
                sonhoDTO.valorSonho(),
                sonhoDTO.status(),
                pessoa1
        );

        Sonho sonhoSalvo = sonhoRepository.save(sonho);

        SonhoDTO sonhoDTO1 = new SonhoDTO(sonhoSalvo);

        return sonhoDTO1;

    }
    @Transactional
    public SonhoDTO alterarSonho(Long id, SonhoDTO sonhoDTO){

        Optional<Sonho> sonho = sonhoRepository.findById(id);

        if (sonho.isPresent()){

            Sonho sonhoAterar = sonho.get();
            Sonho sonhoAterado = new Sonho(
                    sonhoDTO.data(),
                    sonhoDTO.sonho(),
                    sonhoDTO.valorSonho(),
                    sonhoDTO.status(),
                    new Pessoa(sonhoDTO.pessoa()));
            sonhoAterar = new Sonho(sonhoAterado);

            Sonho sonho1 = sonhoRepository.save(sonhoAterar);

            SonhoDTO sonhoDTO1 = new SonhoDTO(sonho1);

            return sonhoDTO1;

        }else {

            throw new EntityNotFoundException("Sonho não encontrado");


        }

    }

    @Transactional
    public DeleteDTO deletarSonho(Long id ){

        Optional<Sonho>  sonhoOptional= sonhoRepository.findById(id);

        if (sonhoOptional.isPresent()){

            sonhoRepository.deleteById(id);

            DeleteDTO deleteDTO = new DeleteDTO(
                    sonhoOptional.get().getId(),
                    String.valueOf(sonhoOptional.get().getTipoSonho())
            );

            return deleteDTO;
        }else {

            throw new EntityNotFoundException("Sonho não Encontrado");
        }


    }

}
