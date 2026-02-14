package controleGastos.Gestao.user;


import controleGastos.Gestao.infra.security.TokenJWTDTO;
import controleGastos.Gestao.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;


    @PostMapping
    public ResponseEntity login( @RequestBody  UsuarioDTO usuarioDTO){

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(usuarioDTO.login(), usuarioDTO.senha());


        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        String tokenJWT = tokenService.createToken((Usuario) authentication.getPrincipal());

        return  ResponseEntity.ok().body(new TokenJWTDTO(tokenJWT));


    }


}
