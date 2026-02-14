package controleGastos.Gestao.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {


   UserDetails findByLogin(String login);


   @Query("SELECT u FROM Usuario u WHERE u.login = :l")
   Optional<Usuario> verifyUser(String l);
}
