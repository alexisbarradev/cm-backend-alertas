package com.backendalertas.alertasmain.repository;

import com.backendalertas.alertasmain.model.AlertaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertaRepository extends JpaRepository<AlertaModel, Long> {
}
