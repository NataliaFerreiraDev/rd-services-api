package com.rd.projetointegrador.rdservicesapi.dto;

import com.rd.projetointegrador.rdservicesapi.dto.Cidade;
import com.rd.projetointegrador.rdservicesapi.dto.OutputMedico;
import com.rd.projetointegrador.rdservicesapi.entity.EspMedEntity;
import com.rd.projetointegrador.rdservicesapi.entity.UfEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerfilMedico {

    private OutputMedico Medico;
    private String dsEmail;
    private List<Cidade> cidades;
    private List<UfEntity> ufs;
    private List<EspMedEntity> Especialidades;
}
