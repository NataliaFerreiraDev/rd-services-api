package com.rd.projetointegrador.rdservicesapi.service;

import com.rd.projetointegrador.rdservicesapi.dto.Cartao;
import com.rd.projetointegrador.rdservicesapi.dto.Usuario;
import com.rd.projetointegrador.rdservicesapi.repository.CartaoRepository;
import com.rd.projetointegrador.rdservicesapi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rd.projetointegrador.rdservicesapi.entity.CartaoEntity;
import com.rd.projetointegrador.rdservicesapi.entity.UsuarioEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.Optional;
import java.util.Set;


@Service
public class CartaoService {
    //GRUPO1
    @Autowired
    private CartaoRepository repository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioService usuarioService;

    SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");


    //MÉTODO: conversão de DTO para Entity
    public CartaoEntity conversaoCartaoEntity(Cartao cartao, CartaoEntity cartaoEntity) {

        try {
            UsuarioEntity usuarioEntity = usuarioRepository.findById(cartao.getUsuario().getIdUsuario()).get();
            System.out.println("Passou usuário");
            cartaoEntity.setNrCartao(cartao.getNrCartao());
            cartaoEntity.setCodSeguranca(cartao.getCodSeguranca());

            Date dtValidade = SDF.parse(cartao.getDtValidade());
            cartaoEntity.setDtValidade(dtValidade);

            if (!cartao.getDtEmissao().equals("") && cartao.getDtEmissao() != null) {
                Date dtEmissao = SDF.parse(cartao.getDtEmissao());
                cartaoEntity.setDtEmissao(dtEmissao);
            }
            System.out.println("Passou a data de emissão");

            cartaoEntity.setNmNome(cartao.getNmNome());
            cartaoEntity.setUsuario(usuarioEntity);
            return cartaoEntity;
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    //MÉTODO: conversão de Entity para DTO
    public Cartao conversaoCartaoDTO(CartaoEntity cartaoEntity, Cartao cartao) {

        cartao.setIdCartao(cartaoEntity.getIdCartao());
        cartao.setNrCartao(cartaoEntity.getNrCartao());
        cartao.setCodSeguranca(cartaoEntity.getCodSeguranca());

        String dtValidade = SDF.format(cartaoEntity.getDtValidade());
        cartao.setDtValidade(dtValidade);
        if (cartaoEntity.getDtEmissao() != null) {
            String dtEmissao = SDF.format(cartaoEntity.getDtEmissao());
            cartao.setDtEmissao(dtEmissao);
        }
        cartao.setNmNome(cartaoEntity.getNmNome());
        Usuario usuario = new Usuario();
        usuario = usuarioService.conversaoUsuarioDTO(cartaoEntity.getUsuario(), usuario);
        cartao.setUsuario(usuario);
        return cartao;
    }

    //MÉTODOS RETORNANDO A ENTITY
    public CartaoEntity getCartao(BigInteger idCartao) {
        Optional<CartaoEntity> optional = repository.findById(idCartao);
        return optional.get();
    }

    public List<CartaoEntity> getCartoes() {
        return repository.findAll();
    }

    public List<CartaoEntity> getCartaoByUsuario(BigInteger idUsuario) {
        UsuarioEntity usuarioEntity = usuarioRepository.findById(idUsuario).get();
        return repository.findByUsuario(usuarioEntity);
    }


    //Grupo2 - Listar os cartões pela id do Usuario
    public List<Cartao> getCartaobyUsuario(UsuarioEntity usuario) {
        List<CartaoEntity> listaCartoes = repository.findByUsuario(usuario);
        List<Cartao> listaCartaoDTO = new ArrayList<>();
        //Convertendo CartaoEntity em CartaoDTO
        for (CartaoEntity cartaoEntity: listaCartoes){
            Cartao cartaoDTO = new Cartao();
            Cartao cartaoConversao = conversaoCartaoDTO(cartaoEntity, cartaoDTO);
            listaCartaoDTO.add(cartaoConversao);
        }
        return listaCartaoDTO;
   }


    @Transactional
    public String cadastrarCartao(Cartao cartao) {
        CartaoEntity cartaoEntity = new CartaoEntity();

        repository.save(cartaoEntity);
        if (usuarioRepository.existsById(cartao.getUsuario().getIdUsuario())) {
            repository.save(cartaoEntity);
            return "Cartao Cadastrado com sucesso";
        }
        return "Erro ao cadastrar cartão";
    }

    @Transactional
    public String alterarCartao(Cartao cartao, BigInteger idCartao) {
        CartaoEntity cartaoEntity = repository.findById(idCartao).get();
        cartaoEntity = conversaoCartaoEntity(cartao, cartaoEntity);
        repository.save(cartaoEntity);
        return "Alteração  de cartão realizada com sucesso";
    }

    public String excluirCartao(BigInteger idCartao) {
        repository.deleteById(idCartao);
        return "Exclusão do cartão realizada com sucesso";
    }
}