package controleGastos.Gestao.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cadastrar")
public class UsuarioControllerCadastro {


    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping
    public ResponseEntity createUser(@RequestBody UsuarioDTO usuarioDTO){

        String senhaCritografada = passwordEncoder.encode(usuarioDTO.senha());

        UsuarioDTO usuarioDTOCriptografado = new UsuarioDTO(usuarioDTO.login(), senhaCritografada);

        usuarioService.createUser(usuarioDTOCriptografado);

        return ResponseEntity.ok().build();

    }



}
