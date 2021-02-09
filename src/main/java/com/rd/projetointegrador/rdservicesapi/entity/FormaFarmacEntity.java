package com.rd.projetointegrador.rdservicesapi.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Data
@NoArgsConstructor
@Table(name="TB_FORMA_FARMAC")
public class FormaFarmacEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID_FORMA_FARMAC")
    private BigInteger idFormaFarmac;

    @Column(name="DS_FORMA_FARMAC")
    private String dsFormaFarmac;

}
