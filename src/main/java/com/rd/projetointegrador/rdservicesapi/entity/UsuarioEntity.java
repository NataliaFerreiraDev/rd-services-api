package com.rd.projetointegrador.rdservicesapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor

@Entity
@Data
@Table(name = "TB_USUARIO")
public class UsuarioEntity implements Serializable {
    //GRUPO1
    //TODO: usar as entitdades das outras tabelas ao invés de biginteger.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    BigInteger idUsuario;

    @ManyToOne
    @JoinColumn(name = "ID_GENERO")
    private GeneroEntity genero;

    @ManyToOne
    @JoinColumn(name="ID_ESP_MED")
    private EspMedEntity espMed;

    @ManyToOne
    @JoinColumn(name = "ID_UF_CRM")
    private UfEntity uf;

    @ManyToOne
    @JoinColumn(name = "ID_TIPO_USUARIO")
    private TipoUsuarioEntity tipoUsuario;

    @Column(name = "NM_NOME")
    private String nmNome;

    @Column(name = "DT_NASCIMENTO")
    private Date dtNascimento;

    @Column(name = "NR_CPF")
    private String nrCpf;

    @Column(name = "NR_CRM")
    private String nrCrm;

    @Column(name = "DS_END_IMG")
    private String dsEndImg;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_PRECO")
    private PrecoEntity preco;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "TB_USUARIO_ENDERECO",
            joinColumns = @JoinColumn(name = "ID_USUARIO"),
            inverseJoinColumns = @JoinColumn(name = "ID_ENDERECO"))
    private List<EnderecoEntity> enderecos;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_CONTATO")
    private List<ContatoEntity> contatos;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "TB_LOGIN_USUARIO")
    @JoinColumn(name = "ID_USUARIO")
    @JsonIgnore
    private LoginUsuarioEntity login;


}
