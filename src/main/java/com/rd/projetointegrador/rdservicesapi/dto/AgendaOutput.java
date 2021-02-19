package com.rd.projetointegrador.rdservicesapi.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgendaOutput {

    private BigInteger idAgenda;
    private OutputMedico medico;
    private BigInteger idTipoConsulta;
    private Periodo periodo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale="pt-BR", timezone = "Brazil/East")
    private Date data;
    private Integer disponibilidade;
}