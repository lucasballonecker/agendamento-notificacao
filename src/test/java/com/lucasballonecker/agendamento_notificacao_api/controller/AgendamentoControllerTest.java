package com.lucasballonecker.agendamento_notificacao_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lucasballonecker.agendamento_notificacao_api.business.AgendamentoService;
import com.lucasballonecker.agendamento_notificacao_api.controller.dto.in.AgendamentoDtoIn;
import com.lucasballonecker.agendamento_notificacao_api.controller.dto.out.AgendamentoDtoOut;
import com.lucasballonecker.agendamento_notificacao_api.infrastructure.enums.StatusNotificacao;
import com.lucasballonecker.agendamento_notificacao_api.infrastructure.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class AgendamentoControllerTest {

    @InjectMocks
    AgendamentoController agendamentoController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    AgendamentoService agendamentoService;

    private AgendamentoDtoIn agendamentoDtoIn;
    private AgendamentoDtoOut agendamentoDtoOut;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(agendamentoController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        objectMapper.registerModule(new JavaTimeModule());

        agendamentoDtoIn = new AgendamentoDtoIn(
                "emailtest@gmail.com", "47990253174",
                "teste", LocalDateTime.of(2025, 7, 26, 14, 12, 35));

        agendamentoDtoOut = new AgendamentoDtoOut(1L, "emailtest@gmail.com",
                "47990253174", "teste",
                LocalDateTime.of(2025, 7, 26, 14, 12, 35), StatusNotificacao.AGENDADO);
    }

    @Test
    void deveCriarAgendamentoComSucesso() throws Exception {
        when(agendamentoService.gravarAgendamentos(agendamentoDtoIn)).thenReturn(agendamentoDtoOut);
        mockMvc.perform(post("/agendamento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(agendamentoDtoIn)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(agendamentoDtoOut.id()))
                .andExpect(jsonPath("$.emailDestinatario").value(agendamentoDtoOut.emailDestinatario()))
                .andExpect(jsonPath("$.telefoneDestinatario").value(agendamentoDtoOut.telefoneDestinatario()))
                .andExpect(jsonPath("$.mensagem").value(agendamentoDtoOut.mensagem()))
                .andExpect(jsonPath("$.dataHoraEnvio").value("26-07-2025 14:12:35"))
                .andExpect(jsonPath("$.statusNotificacao").value("AGENDADO"));
        verify(agendamentoService, times(1)).gravarAgendamentos(agendamentoDtoIn);
    }

    @Test
    void deveRetornarAgendamentoPorIdComSucesso() throws Exception {
        when(agendamentoService.buscarAgendamentosPorId(1L)).thenReturn(agendamentoDtoOut);
        mockMvc.perform(get("/agendamento/{id}", agendamentoDtoOut.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(agendamentoDtoOut.id()))
                .andExpect(jsonPath("$.emailDestinatario").value(agendamentoDtoOut.emailDestinatario()))
                .andExpect(jsonPath("$.telefoneDestinatario").value(agendamentoDtoOut.telefoneDestinatario()))
                .andExpect(jsonPath("$.mensagem").value(agendamentoDtoOut.mensagem()))
                .andExpect(jsonPath("$.dataHoraEnvio").value("26-07-2025 14:12:35"))
                .andExpect(jsonPath("$.statusNotificacao").value("AGENDADO"));
        verify(agendamentoService, times(1)).buscarAgendamentosPorId(1L);

    }

    @Test
    void deveRetornarAgendamentoPorIdComNaoSucesso() throws Exception {
        when(agendamentoService.buscarAgendamentosPorId(1000000L))
                .thenThrow(new NotFoundException("Erro: id não encontrado"));

        mockMvc.perform(get("/agendamento/{id}", 1000000L))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Erro: id não encontrado"));
    }

    @Test
    void deveCancelarAgendamentoComSucesso () throws Exception {
        mockMvc.perform(put("/agendamento/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void deveCancelarAgendamentoComNaoSucesso() throws Exception {
       doThrow(new NotFoundException("Erro: id não encontrado"))
               .when(agendamentoService)
                       .cancelarAgendamentosPorId(1000000L);

        mockMvc.perform(put("/agendamento/{id}", 1000000L))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Erro: id não encontrado"));
    }

}
