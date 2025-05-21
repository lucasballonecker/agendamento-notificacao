package com.lucasballonecker.agendamento_notificacao_api.controller.dto.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lucasballonecker.agendamento_notificacao_api.infrastructure.enums.StatusNotificacao;

import java.time.LocalDateTime;

public record AgendamentoDtoOut(Long id,
                                String emailDestinatario,
                                String telefoneDestinatario,
                                String mensagem,
                                @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
                                LocalDateTime dataHoraEnvio,
                                StatusNotificacao statusNotificacao) {
}
