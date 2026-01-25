package controleGastos.Gestao.fearures.sonho.repository;

import controleGastos.Gestao.fearures.sonho.model.Sonho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SonhoRepository extends JpaRepository<Sonho, Long> {



}
