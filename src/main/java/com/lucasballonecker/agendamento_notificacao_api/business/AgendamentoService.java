package com.lucasballonecker.agendamento_notificacao_api.business;


import com.lucasballonecker.agendamento_notificacao_api.business.mapper.IAgendamentoMapper;
import com.lucasballonecker.agendamento_notificacao_api.controller.dto.in.AgendamentoDtoIn;
import com.lucasballonecker.agendamento_notificacao_api.controller.dto.out.AgendamentoDtoOut;
import com.lucasballonecker.agendamento_notificacao_api.infrastructure.entity.Agendamento;
import com.lucasballonecker.agendamento_notificacao_api.infrastructure.repository.AgendamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AgendamentoService {
    private final AgendamentoRepository repository;
    private final IAgendamentoMapper agendamentoMapper;

    public AgendamentoDtoOut gravarAgendamento(AgendamentoDtoIn agendamento) {
        Agendamento agendamentoSalvo = agendamentoMapper.paraEntity(agendamento);
        repository.save(agendamentoSalvo);
        return agendamentoMapper.paraOut(agendamentoSalvo);
    }

}
