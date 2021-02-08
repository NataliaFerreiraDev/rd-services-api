package com.rd.projetointegrador.rdservicesapi.controller;

import com.rd.projetointegrador.rdservicesapi.dto.Cartao;
import com.rd.projetointegrador.rdservicesapi.entity.CartaoEntity;

import com.rd.projetointegrador.rdservicesapi.service.CartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.websocket.server.PathParam;
import java.math.BigInteger;
import java.util.List;

@RestController
public class CartaoController {
    //GRUPO1


    @Autowired
    CartaoService service;

    @GetMapping("/cartao")
    public ResponseEntity getCartoes() {
        List<CartaoEntity> cartoes = service.getCartoes();
        return ResponseEntity.status(HttpStatus.OK).body(cartoes);
    }

    @GetMapping ("/cartao/{idCartao}")
    public ResponseEntity getCartao(@PathVariable("idCartao") BigInteger idCartao) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.getCartao(idCartao));
    }

    @PostMapping("/cartao") //CADASTRO
    public ResponseEntity cadastrarCartao(@RequestBody Cartao cartao) {
        return ResponseEntity.status(HttpStatus.OK).body(service.cadastrarCartao(cartao));
    }

    @PutMapping("/cartao/{idCartao}") //ALTERAÇÕES
    public ResponseEntity alterarCartao(@RequestBody Cartao cartao, @PathVariable("idCartao") BigInteger idCartao) {

        return ResponseEntity.status(HttpStatus.OK).body(service.alterarCartao(cartao, idCartao));
    }
    @DeleteMapping("/cartao/{id}") //DELETAR
    public ResponseEntity excluirCartao(@PathVariable("id") BigInteger idCartao) {
        return ResponseEntity.status(HttpStatus.OK).body(service.excluirCartao(idCartao));
    }

}




