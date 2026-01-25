package controleGastos.Gestao.fearures.receita.repository;


import controleGastos.Gestao.fearures.receita.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita,Long> {
}
