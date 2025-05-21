package com.lucasballonecker.agendamento_notificacao_api.business.mapper;

import com.lucasballonecker.agendamento_notificacao_api.controller.dto.in.AgendamentoDtoIn;
import com.lucasballonecker.agendamento_notificacao_api.controller.dto.out.AgendamentoDtoOut;
import com.lucasballonecker.agendamento_notificacao_api.infrastructure.entity.Agendamento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface IAgendamentoMapper {
    Agendamento paraEntity(AgendamentoDtoIn agendamento);

    AgendamentoDtoOut paraOut(Agendamento agendamento);

    @Mapping(target = "dataHoraModificacao", expression = "java(LocalDateTime.now())")
    @Mapping(target = "statusNotificacao", expression = "java(StatusNotificacao.CANCELADO)")
    Agendamento paraEntityCancelamento(Agendamento agendamento);


}
