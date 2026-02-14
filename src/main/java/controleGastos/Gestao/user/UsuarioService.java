package controleGastos.Gestao.user;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class UsuarioService {


    @Autowired
    private UsuarioRepository usuarioRepository;


    public void createUser(UsuarioDTO usuarioDTO){

        Optional<Usuario> usuario = usuarioRepository.verifyUser(usuarioDTO.login());
        if (usuario.isPresent()){

            throw new EntityNotFoundException("Usuario Já cadastrado");


        }

        Usuario usuario1 = new Usuario(
                usuarioDTO.login(),
                usuarioDTO.senha()
        );


        this.usuarioRepository.save(usuario1);

    }


}
