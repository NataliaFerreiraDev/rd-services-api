package com.rd.projetointegrador.rdservicesapi.dto;

import com.rd.projetointegrador.rdservicesapi.entity.ProntuarioEntity;

import java.math.BigInteger;
import java.util.Date;

public class Atendimento {

    private BigInteger idAtendimento;

//    private UsuarioEntity paciente;

//    private UsuarioEntity medico;

    private ProntuarioEntity prontuario;

    private Float vlPeso;

    private Float vlAltura;

    private String dsHabitosVicios;

    private String dsAlergiasRestricoes;

    private String dsMedicacaoUsoContinuo;

    private String dsProblemasSaude;

    private Date dtAtendimento;

}
