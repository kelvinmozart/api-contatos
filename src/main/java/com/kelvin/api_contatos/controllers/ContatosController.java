package com.kelvin.api_contatos.controllers;

import com.kelvin.api_contatos.entities.Contato;
import com.kelvin.api_contatos.services.ContatosService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/contatos")
public class ContatosController {

    private final ContatosService contatosService;

    public ContatosController(ContatosService contatosService){
        this.contatosService = contatosService;
    }

    @GetMapping
    public ResponseEntity<List<Contato>> listarTodos(){
        List<Contato> lista = contatosService.findAll();
        if(lista.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{email}")
    public ResponseEntity<Contato> buscarPorEmail(@PathVariable String email){
        Contato contato = contatosService.buscarPorEmail(email);
        return ResponseEntity.ok(contato);
    }

    @PostMapping
    public ResponseEntity<Contato> inserir(@Valid @RequestBody Contato contato){
        Contato contatoSalvo = contatosService.inserir(contato);
        URI location = URI.create(String.format("/contatos/%s", contatoSalvo.getEmail()));
        return ResponseEntity.created(location).body(contatoSalvo);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<String> remover(@PathVariable String email){
        contatosService.remover(email);
        return ResponseEntity.ok("Contato excluído com sucesso");
    }

    @PutMapping("/{email}")
    public ResponseEntity<Contato> atualizar(@PathVariable String email,
                                             @Valid @RequestBody Contato contatoAtualizado){
        Contato atualizado = contatosService.atualizar(email, contatoAtualizado);
        return ResponseEntity.ok(atualizado);
    }
}
