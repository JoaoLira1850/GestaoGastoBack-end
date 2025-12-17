package controleGastos.Gestao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import controleGastos.Gestao.model.GestaoSalarioEntity;

public interface GestaoSalarioRepository extends JpaRepository<GestaoSalarioEntity, Long> {

}
