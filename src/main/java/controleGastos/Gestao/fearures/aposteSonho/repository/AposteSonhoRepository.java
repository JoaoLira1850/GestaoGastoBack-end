package controleGastos.Gestao.fearures.aposteSonho.repository;


import controleGastos.Gestao.fearures.aposteSonho.model.AposteSonho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AposteSonhoRepository extends JpaRepository<AposteSonho, Long> {
}
