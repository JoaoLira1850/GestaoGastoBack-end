package controleGastos.Gestao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import controleGastos.Gestao.model.GestaGastosEntity;

@Repository
public interface GestaoGastosRepository extends JpaRepository<GestaGastosEntity, Long> {



}
