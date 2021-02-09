package com.rd.projetointegrador.rdservicesapi.repository;

import com.rd.projetointegrador.rdservicesapi.entity.AtendimentoEntity;
import com.rd.projetointegrador.rdservicesapi.entity.ProntuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;
import java.util.List;

@Repository
public interface ProntuarioRepository extends JpaRepository<ProntuarioEntity, BigInteger> {
    List<ProntuarioEntity> findByIdProntuario(BigInteger IdProntuario);

}
