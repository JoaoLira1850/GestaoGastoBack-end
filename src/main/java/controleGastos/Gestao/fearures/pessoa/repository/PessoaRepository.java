package controleGastos.Gestao.fearures.pessoa.repository;

import controleGastos.Gestao.fearures.pessoa.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
