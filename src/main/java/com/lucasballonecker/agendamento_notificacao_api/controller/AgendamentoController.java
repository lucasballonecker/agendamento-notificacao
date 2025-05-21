package com.lucasballonecker.agendamento_notificacao_api.controller;

import com.lucasballonecker.agendamento_notificacao_api.business.AgendamentoService;
import com.lucasballonecker.agendamento_notificacao_api.controller.dto.in.AgendamentoDtoIn;
import com.lucasballonecker.agendamento_notificacao_api.controller.dto.out.AgendamentoDtoOut;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agendamento")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    @PostMapping
    public ResponseEntity<AgendamentoDtoOut> gravarAgendamentos(@RequestBody AgendamentoDtoIn agendamento) {
        AgendamentoDtoOut agendamentoDtoOut = agendamentoService.gravarAgendamento(agendamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(agendamentoDtoOut);
    }
}
