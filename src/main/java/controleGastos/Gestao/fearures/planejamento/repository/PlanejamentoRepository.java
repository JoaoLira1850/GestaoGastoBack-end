package controleGastos.Gestao.fearures.planejamento.repository;


import controleGastos.Gestao.fearures.planejamento.model.Planejamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface PlanejamentoRepository extends JpaRepository<Planejamento,Long> {


}
