package controleGastos.Gestao.infra.security;


import controleGastos.Gestao.user.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {


    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String tokenJWT = tokenRecuperado(request);


        if (tokenJWT != null){


            String tokenSubjetc = tokenService.verifyTokenJWT(tokenJWT);

            UserDetails usuario = usuarioRepository.findByLogin(tokenSubjetc);


            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(usuario, "",usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);


        }

        filterChain.doFilter(request,response);

    }


    public String tokenRecuperado(HttpServletRequest request){


        String tokenRecuperado = request.getHeader("Autorization");


        if (tokenRecuperado != null){

            return tokenRecuperado.replace("Bearer","").trim();


        }

        return null;
    }
}
