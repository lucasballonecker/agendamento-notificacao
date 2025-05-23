package com.lucasballonecker.agendamento_notificacao_api.business;


import com.lucasballonecker.agendamento_notificacao_api.business.mapper.IAgendamentoMapper;
import com.lucasballonecker.agendamento_notificacao_api.controller.dto.in.AgendamentoDtoIn;
import com.lucasballonecker.agendamento_notificacao_api.controller.dto.out.AgendamentoDtoOut;
import com.lucasballonecker.agendamento_notificacao_api.infrastructure.entity.Agendamento;
import com.lucasballonecker.agendamento_notificacao_api.infrastructure.exception.NotFoundException;
import com.lucasballonecker.agendamento_notificacao_api.infrastructure.repository.AgendamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AgendamentoService {
    private final AgendamentoRepository repository;
    private final IAgendamentoMapper agendamentoMapper;

    public AgendamentoDtoOut gravarAgendamentos(AgendamentoDtoIn agendamento) {
        Agendamento agendamentoSalvo = agendamentoMapper.paraEntity(agendamento);
        repository.save(agendamentoSalvo);
        return agendamentoMapper.paraOut(agendamentoSalvo);
    }

    public AgendamentoDtoOut buscarAgendamentosPorId(Long id) {
        Agendamento agendamento = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Erro: id não encontrado"));
        return agendamentoMapper.paraOut(agendamento);
    }

    public void cancelarAgendamentosPorId(Long id) {
        Agendamento agendamento = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Id não encontrado"));
        repository.save(agendamentoMapper.paraEntityCancelamento(agendamento));
    }

}
