package com.rd.projetointegrador.rdservicesapi.controller;


import com.rd.projetointegrador.rdservicesapi.service.CardapioService;
import com.rd.projetointegrador.rdservicesapi.entity.CardapioEntity;
import com.rd.projetointegrador.rdservicesapi.dto.Cardapio;
import com.rd.projetointegrador.rdservicesapi.service.TipoRefeicaoService;
import com.rd.projetointegrador.rdservicesapi.service.CardapioService;
import com.rd.projetointegrador.rdservicesapi.repository.TipoRefeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.math.BigInteger;
import java.util.List;


@RestController
public class CardapioController {

    @Autowired
    private CardapioService cardapioService;

    @Autowired
    private TipoRefeicaoService tipoRefeicaoService;

    @Autowired
    private TipoRefeRepository tipoRefeRepository;

    @PostMapping("/cardapio")
    public ResponseEntity cadastrarCardapio(@RequestBody Cardapio cardapio) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cardapioService.cadastrarCardapio(cardapio));
    }

    @GetMapping("/cardapio/{idCardapio}")
    public  ResponseEntity buscarCardapio(@PathVariable("idCardapio") BigInteger idCardapio){
        return ResponseEntity.ok(cardapioService.buscarCardapio(idCardapio));
    }

    @GetMapping("/cardapios")
    public ResponseEntity buscarCardapios(@PathParam("idCardapio") BigInteger idCardapio){
        List<CardapioEntity> cardapios = cardapioService.buscarCardapios(idCardapio);
        return ResponseEntity.status(HttpStatus.OK).body(cardapios);
    }

    @DeleteMapping("/cardapio/{idCardapio}")
    public ResponseEntity excluirCardapio(@PathVariable("idCardapio") BigInteger idCardapio){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(cardapioService.excluirCardapio(idCardapio));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir cidade");
        }
    }

}
