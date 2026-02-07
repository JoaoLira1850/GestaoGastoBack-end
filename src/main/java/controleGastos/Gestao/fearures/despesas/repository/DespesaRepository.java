package controleGastos.Gestao.fearures.despesas.repository;


import controleGastos.Gestao.fearures.despesas.model.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa,Long> {
}
