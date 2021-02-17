package com.rd.projetointegrador.rdservicesapi.controller;

import com.rd.projetointegrador.rdservicesapi.dto.InputMedico;
import com.rd.projetointegrador.rdservicesapi.dto.Usuario;
import com.rd.projetointegrador.rdservicesapi.entity.UsuarioEntity;
import com.rd.projetointegrador.rdservicesapi.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.math.BigInteger;
import java.util.List;
import java.security.NoSuchAlgorithmException;


@RestController
public class UsuarioController {
    //GRUPO1

    @Autowired
    UsuarioService service;

    @GetMapping("/usuario/{idUsuario}") // BUSCA POR ID
    public ResponseEntity getUsuario(@PathVariable("idUsuario") BigInteger idUsuario) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.getUsuarioDTO(idUsuario));
    }

    @GetMapping("/usuario")
    public ResponseEntity getUsuarios(@PathParam("idUsuario") BigInteger idUsuario){
        List<Usuario> usuarios = service.getUsuariosDTO();
        return ResponseEntity.status(HttpStatus.OK).body(usuarios);
    }

    @GetMapping("/usuario/cpf/{cpf}")//Pegar usuário por CPF
    public ResponseEntity getUsuarioPorCpf(@PathVariable("cpf") String cpf){
        return ResponseEntity.status(HttpStatus.OK).body(service.getUsuarioPorCPF(cpf));
    }

    @PostMapping("/usuario") //Cadastrar Novo Usuario
    public ResponseEntity cadastrarUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrarUsuario(usuario));
    }

    @PutMapping("/usuario/{idUsuario}") // Alterar Usuario
    public ResponseEntity alterarUsuario(@RequestBody Usuario usuario, @PathVariable("idUsuario") BigInteger idUsuario){
        String retorno = service.alterarUsuario(usuario, idUsuario);
        return ResponseEntity.ok().body(retorno);

    }

    @DeleteMapping("/usuario/{idUsuario}") //Excluir Usuario
    public ResponseEntity excluirUsuario(@PathVariable("idUsuario") BigInteger idUsuario) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.excluirUsuario(idUsuario));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir usuário");
        }
    }


    //GRUPO4
    //LISTAR TODOS OS MEDICOS

    @GetMapping("/medico/{id}")
    public ResponseEntity getMedico(@PathVariable("id") BigInteger id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getMedico(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar médico");
        }
    }

    @GetMapping("/medicos")
    public ResponseEntity getmedicos(){
        try{
            List<UsuarioEntity> usuarios = service.getMedicos();
            return ResponseEntity.status(HttpStatus.OK).body(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar lista de médicos");
        }
    }

    //EXIBIR TELA DE PERFIL DO MEDICO
    @GetMapping("/perfilMedico/{idMedico}")
    public ResponseEntity mostrarTelaPerfil(@PathVariable("idMedico") BigInteger idMedico,@PathVariable("idUf") BigInteger idUf){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.mostrarTelaPerfil(idMedico));
    }

    //ALTERAR CADASTRO DE PERFIL DO MEDICO
    @PutMapping("/medico/{idUsuario}")
    public ResponseEntity alterarMedico(@RequestBody InputMedico inputMedico, @PathVariable("idUsuario") BigInteger id){
        try{ String retorno = service.alterarMedico(inputMedico, id);
            return ResponseEntity.ok().body(retorno);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao alterar dados");
        }
    }

    //EXIBIR LISTAS DA TELA DE CADASTRO DO MEDICO
    @GetMapping("/cadastroMedico/{idUf}")
    public ResponseEntity mostrarTelaCadastro(@PathVariable("idUf") BigInteger idUf){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.mostrarTelaCadastro(idUf));
    }

    //CADASTRAR MEDICO
    @PostMapping("/medico")
    public ResponseEntity cadastrarMedico(@RequestBody InputMedico inputMedico) throws NoSuchAlgorithmException {
        if (service.consultarPorCpf(inputMedico.getNrCpf()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cpf já cadastrado");
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrarMedico(inputMedico));
        }
    }
}
