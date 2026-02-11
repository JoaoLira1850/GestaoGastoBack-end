package controleGastos.Gestao.fearures.aposteSonho.service;


import controleGastos.Gestao.fearures.aposteSonho.dto.AposteSonhoDTO;
import controleGastos.Gestao.fearures.aposteSonho.model.AposteSonho;
import controleGastos.Gestao.fearures.aposteSonho.repository.AposteSonhoRepository;
import controleGastos.Gestao.fearures.pessoa.dto.DeleteDTO;
import controleGastos.Gestao.fearures.sonho.model.Sonho;
import controleGastos.Gestao.fearures.sonho.repository.SonhoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AposteSonhoService {


    @Autowired
    private AposteSonhoRepository aposteSonhoRepository;

    @Autowired
    private SonhoRepository sonhoRepository;


    @Transactional
    public List<AposteSonhoDTO> listContribution(){

        List<AposteSonho> sonhoList = aposteSonhoRepository.findAll();

        List<AposteSonhoDTO> aposteSonhoDTOList = sonhoList.stream()
                .map(a -> AposteSonhoDTO.fromEntity(a))
                .toList();
        return aposteSonhoDTOList;
    }


    @Transactional
    public AposteSonhoDTO createContribution(AposteSonhoDTO aposteSonhoDTO){

        Optional<Sonho> sonho =  sonhoRepository.findById(aposteSonhoDTO.sonhoDTO().id());

        if (sonho.isEmpty()){

            throw new EntityNotFoundException("Sonho Não Encontrado");
        }

        AposteSonho aposteSonho = new AposteSonho(
                sonho.get(),
                aposteSonhoDTO.data(),
                aposteSonhoDTO.valor(),
                aposteSonhoDTO.tipoDepositoSonho()

        );

        AposteSonho aposteSonhoSalvo = aposteSonhoRepository.save(aposteSonho);

        AposteSonhoDTO aposteSonhoDTO1 = AposteSonhoDTO.fromEntity(aposteSonhoSalvo);

        return aposteSonhoDTO1;

    }

    @Transactional
    public AposteSonhoDTO updateContribution(Long id, AposteSonhoDTO aposteSonhoDTO){

        Optional<AposteSonho> aposteSonhoAntigo = aposteSonhoRepository.findById(id);

        if (aposteSonhoAntigo.isEmpty()){

            throw new EntityNotFoundException("Aporte Sonho Não Encontrado");

        }

        Optional<Sonho> sonho = sonhoRepository.findById(aposteSonhoDTO.sonhoDTO().id());

        if (sonho.isEmpty()){
            throw new EntityNotFoundException("Id sonho não achado!!");
        }

        AposteSonho aposteSonhoNovo = new AposteSonho();
        aposteSonhoNovo.setSonho(sonho.get());
        aposteSonhoNovo.setData(aposteSonhoDTO.data());
        aposteSonhoNovo.setValor(aposteSonhoDTO.valor());
        aposteSonhoNovo.setTipoDepositoSonho(aposteSonhoDTO.tipoDepositoSonho());

        AposteSonho aposteSonhoSalvo = aposteSonhoRepository.save(aposteSonhoNovo);

        AposteSonhoDTO aposteSonhoDTO1 = AposteSonhoDTO.fromEntity(aposteSonhoSalvo);

        return aposteSonhoDTO1;


    }


    @Transactional
    public DeleteDTO deleteContribution(Long id){


        Optional<AposteSonho> aposteSonho  = aposteSonhoRepository.findById(id);

        if (aposteSonho.isEmpty()){

            throw new EntityNotFoundException("Aporte Sonho Não Encontrado");
        }

       aposteSonhoRepository.delete(aposteSonho.get());

        DeleteDTO deleteDTO = new DeleteDTO(aposteSonho.get().getId(), String.valueOf(aposteSonho.get().getTipoDepositoSonho()));


        return deleteDTO;
    }
}
