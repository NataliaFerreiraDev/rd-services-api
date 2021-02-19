package com.rd.projetointegrador.rdservicesapi.service;

import com.rd.projetointegrador.rdservicesapi.dto.*;
import com.rd.projetointegrador.rdservicesapi.entity.*;
import com.rd.projetointegrador.rdservicesapi.repository.AgPacienteRepository;
import com.rd.projetointegrador.rdservicesapi.repository.AgendaRepository;
import com.rd.projetointegrador.rdservicesapi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AgPacienteService {

    @Autowired private AgPacienteRepository repository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private AgendaRepository agendaRepository;
    @Autowired private PagamentoService pagamentoService;


    //Grupo2 - Convertendo AgPacienteEntity para DTO
    public AgPaciente conversaoAgPacienteParaDTO (AgPacienteEntity agPacienteEntity, AgPaciente agPaciente){
        agPaciente.setIdAgPaciente(agPacienteEntity.getIdAgPaciente());
        //idAgenda
        Agenda agenda = new Agenda();
        agenda.setIdAgenda(agPacienteEntity.getAgenda().getIdAgenda());
        //nomeMedico
        UsuarioEntity medicoEntity = agPacienteEntity.getAgenda().getMedico();
        OutputMedico medicoDto = new OutputMedico();
        medicoDto.setNome(medicoEntity.getNmNome());
        //especialidade
        EspMedEntity espMedEntity = agPacienteEntity.getAgenda().getMedico().getEspMed();
        EspMed espMedDto = new EspMed();
        espMedDto.setIdEspMed(espMedEntity.getIdEspMed());
        espMedDto.setDsEspMed(espMedEntity.getDsEspMed());
        medicoDto.setEspMed(espMedDto);
        agenda.setMedico(medicoDto);

        //data
        agenda.setData(agPacienteEntity.getAgenda().getData());
        //horaInicial
        PeriodoEntity periodoEntity = agPacienteEntity.getAgenda().getPeriodo();
        Periodo periodo = new Periodo();
        periodo.setIdPeriodo(periodoEntity.getIdPeriodo());
        periodo.setHoraInicial(periodoEntity.getHoraInicial());
        agenda.setPeriodo(periodo);
        //statusConsulta
        StatusConsulta status = new StatusConsulta();
        status.setIdStatusConsulta(agPacienteEntity.getStatusConsulta().getIdStatusConsulta());
        status.setDsStatusConsulta(agPacienteEntity.getStatusConsulta().getDsStatusConsulta());
        agPaciente.setStatusConsulta(status);
        //passando agendaDTO para agPacienteDTO
        agPaciente.setAgenda(agenda);
        return agPaciente;
    }


    //Grupo2 - Get AgPaciente pelo idAgenda
    public AgPaciente getAgPacientePorId(BigInteger idAgPaciente){
        AgPacienteEntity agPacienteEntity= repository.findById(idAgPaciente).get();
        AgPaciente agPacienteDto = new AgPaciente();
        Agenda agendaDTO = new Agenda();
        OutputMedico medico = new OutputMedico();
        EspMed espMed = new EspMed();
        Periodo periodo = new Periodo();
        espMed.setDsEspMed(agPacienteEntity.getAgenda().getMedico().getEspMed().getDsEspMed());
        medico.setNome(agPacienteEntity.getAgenda().getMedico().getNmNome());
        medico.setEspMed(espMed);
        periodo.setHoraInicial(agPacienteEntity.getAgenda().getPeriodo().getHoraInicial());
        agendaDTO.setIdAgenda(agPacienteEntity.getAgenda().getIdAgenda());
        agendaDTO.setData(agPacienteEntity.getAgenda().getData());
        agendaDTO.setMedico(medico);
        agendaDTO.setPeriodo(periodo);
        agPacienteDto.setAgenda(agendaDTO);
        return agPacienteDto;
    }

    //Grupo 2 - Listas as agendas do paciente pela idUsuario
    public List<AgPaciente> getAgPaciente(Optional<UsuarioEntity> usuario){
        List<AgPacienteEntity> listaAgendas = repository.findByPaciente(usuario.get());
        List<AgPaciente> agPacientes = new ArrayList<>();
        AgPaciente agPaciente = new AgPaciente();
        //Convertendo AgPacienteEntity para AgPacienteDTO
        for (AgPacienteEntity agPacienteEntity : listaAgendas){
            conversaoAgPacienteParaDTO(agPacienteEntity,agPaciente);
            agPacientes.add(agPaciente);
        }
        return agPacientes;
    };
    //Grupo2 - Cadastrar nova Agenda de Paciente

    @Transactional
    public OutputAgPacientePagamento setAgPaciente (CadastroAgPacientePagamento cadastroAgPacientePagamento) {
        OutputAgPacientePagamento output = new OutputAgPacientePagamento();
        AgPacienteEntity agPacienteEntity = new AgPacienteEntity();
        StatusConsultaEntity status = new StatusConsultaEntity();
        //mudando status da consulta para agendada
        status.setIdStatusConsulta(BigInteger.valueOf(1));
        LocalDateTime data = LocalDateTime.now();
        agPacienteEntity.setAgenda(agendaRepository.findById(cadastroAgPacientePagamento.getIdAgenda()).get());
        agPacienteEntity.setPaciente(usuarioRepository.findById(cadastroAgPacientePagamento.getIdUsuario()).get());
        agPacienteEntity.setDtSolicitacao(data);
        agPacienteEntity.setStatusConsulta(status);
        //mudando disponibilidade da agenda médica para agendada
        repository.save(agPacienteEntity);
        //convertendo para DTO
        AgPaciente agPaciente = new AgPaciente();
        conversaoAgPacienteParaDTO(agPacienteEntity, agPaciente);
        //passando a AgendaDTO para a DTO de resposta
        output.setAgPaciente(agPaciente);
        //passando o pagamento para a DTO de resposta
//        if (cadastroAgPacientePagamento.getTipoPgto().getIdFormaPagamento().equals(1)){
//            output.setPagamento(pagamentoService.setPagamentoComPlano(agPacienteEntity.getIdAgPaciente()));
//        } else if (cadastroAgPacientePagamento.getTipoPgto().getIdFormaPagamento().equals(2)){
//            output.setPagamento(pagamentoService.setPagamentoComCartao(cadastroAgPacientePagamento.getNrParcelas(),cadastroAgPacientePagamento.getCartao(), agPacienteEntity.getIdAgPaciente()));
//        }
        
        output.setPagamento(pagamentoService.setPagamentoComPlano(agPacienteEntity.getIdAgPaciente()));


        return output;
    }
    //Grupo2 - Mudar a disponibilidade da Agenda Médica para disponível e mudar o status consulta para cancelada quando o paciente cancela a consulta

    @Transactional
    public RespostaString cancelarAgPaciente(BigInteger idAgPaciente){
        StatusConsultaEntity status = new StatusConsultaEntity();
        status.setIdStatusConsulta(BigInteger.valueOf(3));
        AgPacienteEntity agPaciente = repository.findById(idAgPaciente).get();
        agPaciente.setStatusConsulta(status);
        agPaciente.getAgenda().setDisponibilidade(1);
        RespostaString respostaMudarStatus = new RespostaString();
        respostaMudarStatus.setResposta("Consulta cancelada com sucesso");
        return respostaMudarStatus;
    }
}