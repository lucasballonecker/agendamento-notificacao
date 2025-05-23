package com.lucasballonecker.agendamento_notificacao_api.business;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lucasballonecker.agendamento_notificacao_api.business.mapper.IAgendamentoMapper;
import com.lucasballonecker.agendamento_notificacao_api.controller.dto.in.AgendamentoDtoIn;
import com.lucasballonecker.agendamento_notificacao_api.controller.dto.out.AgendamentoDtoOut;
import com.lucasballonecker.agendamento_notificacao_api.infrastructure.entity.Agendamento;
import com.lucasballonecker.agendamento_notificacao_api.infrastructure.enums.StatusNotificacao;
import com.lucasballonecker.agendamento_notificacao_api.infrastructure.exception.NotFoundException;
import com.lucasballonecker.agendamento_notificacao_api.infrastructure.repository.AgendamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AgendamentoServiceTest {

    @InjectMocks
    AgendamentoService agendamentoService;

    @Mock
    private AgendamentoRepository repository;

    @Mock
    private IAgendamentoMapper mapper;

    private AgendamentoDtoIn agendamentoDtoIn;
    private AgendamentoDtoOut agendamentoDtoOut;
    private Agendamento agendamentoEntity;

    @BeforeEach
    void setUp() {
        agendamentoEntity = new Agendamento(1L, "emailtest@gmail.com",
                "47990253174", "teste",
                LocalDateTime.of(2025, 7, 26, 14, 12, 35),
                LocalDateTime.now(), null, StatusNotificacao.AGENDADO);

        agendamentoDtoIn = new AgendamentoDtoIn(
                "emailtest@gmail.com", "47990253174",
                "teste", LocalDateTime.of(2025, 7, 26, 14, 12, 35));

        agendamentoDtoOut = new AgendamentoDtoOut(1L, "emailtest@gmail.com",
                "47990253174", "teste",
                LocalDateTime.of(2025, 7, 26, 14, 12, 35), StatusNotificacao.AGENDADO);
    }

    @Test
    void deveGravarAgendamentoComSucesso() {
        when(mapper.paraEntity(agendamentoDtoIn)).thenReturn(agendamentoEntity);
        when(repository.save(agendamentoEntity)).thenReturn(agendamentoEntity);
        when(mapper.paraOut(agendamentoEntity)).thenReturn(agendamentoDtoOut);

        AgendamentoDtoOut out = agendamentoService.gravarAgendamentos(agendamentoDtoIn);

        verify(mapper, times(1)).paraEntity(agendamentoDtoIn);
        verify(repository, times(1)).save(agendamentoEntity);
        verify(mapper, times(1)).paraOut(agendamentoEntity);
        assertThat(agendamentoDtoOut).usingRecursiveComparison().isEqualTo(out);

    }

    @Test
    void deveBuscarAgendamentoPorIdComSucesso() {
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(agendamentoEntity));
        when(mapper.paraOut(agendamentoEntity)).thenReturn(agendamentoDtoOut);

        AgendamentoDtoOut out = agendamentoService.buscarAgendamentosPorId(1L);

        verify(repository, times(1)).findById(1L);
        verify(mapper, times(1)).paraOut(agendamentoEntity);

        assertThat(agendamentoDtoOut).usingRecursiveComparison().isEqualTo(out);
    }

    @Test
    void deveBuscarAgendamentoPorIdComNaoSucesso() {
        when(repository.findById(1000L)).thenReturn(Optional.empty());

        NotFoundException e = assertThrows(NotFoundException.class,
                () -> agendamentoService.buscarAgendamentosPorId(1000L));
        assertEquals("Erro: id não encontrado", e.getMessage());

        verify(repository, times(1)).findById(1000L);

    }

    @Test
    void deveCancelarAgendamentoPorIdComSucesso() {
        when(repository.findById(1L)).thenReturn(Optional.of(agendamentoEntity));
        agendamentoService.cancelarAgendamentosPorId(1L);

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1))
                .save(mapper.paraEntityCancelamento(agendamentoEntity));

    }

    @Test
    void deveCancelarAgendamentoPorIdComNaoSucesso() {
        when(repository.findById(1000L)).thenThrow(new NotFoundException("Erro: id não encontrado"));

        NotFoundException e = assertThrows(NotFoundException.class,
                () -> agendamentoService.cancelarAgendamentosPorId(1000L));
        assertEquals("Erro: id não encontrado", e.getMessage());

        verify(repository, times(1)).findById(1000L);

    }

}
