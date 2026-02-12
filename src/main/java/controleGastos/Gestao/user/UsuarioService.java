package controleGastos.Gestao.user;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.TransactionScoped;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {



    @Autowired
    private UsuarioRepository usuarioRepository;


    @Transactional
    public void createUser(UsuarioDTO usuarioDTO){

        Optional<Usuario> usuario = usuarioRepository.findByLogin(usuarioDTO.login());

        if (usuario.isEmpty()){

            throw new EntityNotFoundException("Usuario já cadastrado");
        }


        Usuario usuario1 = new Usuario(usuarioDTO.login(), usuarioDTO.senha());


        Usuario usuarioSalvo = usuarioRepository.save(usuario1);



    }


}
