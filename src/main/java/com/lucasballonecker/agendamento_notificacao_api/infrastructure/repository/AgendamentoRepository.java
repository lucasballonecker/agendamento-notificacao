package com.lucasballonecker.agendamento_notificacao_api.infrastructure.repository;

import com.lucasballonecker.agendamento_notificacao_api.infrastructure.entity.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

}
