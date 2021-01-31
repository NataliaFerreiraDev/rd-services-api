package com.rd.projetointegrador.rdservicesapi.dto;

import com.rd.projetointegrador.rdservicesapi.entity.AtendimentoEntity;

import java.math.BigInteger;

public class Prontuario {

    private BigInteger idProntuario;

    private String ds;

    private String dsObjetivo;

    private String dsAvaliacao;

    private String dsPlano;

    private String dsObservacoes;

    private AtendimentoEntity atendimento;

}
