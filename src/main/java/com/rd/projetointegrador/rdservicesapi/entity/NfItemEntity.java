package com.rd.projetointegrador.rdservicesapi.entity;



import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

@Entity
@Table(name = "TB_NF_ITEM")
@Data
@NoArgsConstructor

public class NfItemEntity implements Serializable  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID_NF")
    private BigInteger idNf;

    @Column(name="NR_NF_ITEM")
    private Integer nrNfItem;

    @Column(name="ID_AG_PACIENTE")
    private BigInteger idAgPaciente;

    @Column(name="VL_CONSULTA")
    private Float vlConsulta;

    @Column(name="PC_IMPOSTOS")
    private Float pcImpostos;
}
