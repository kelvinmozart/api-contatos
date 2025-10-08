package com.kelvin.api_contatos.services;

import com.kelvin.api_contatos.entities.Contato;
import com.kelvin.api_contatos.repositories.ContatosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContatosService {

    private final ContatosRepository contatosRepository;

    public ContatosService(ContatosRepository contatosRepository) { this.contatosRepository = contatosRepository; }

    public List<Contato> findAll(){
        return contatosRepository.findAll();
    }

    public Contato buscarPorEmail(String email){
        return contatosRepository.findById(email)
                .orElseThrow(() -> new IllegalArgumentException("Contato não encontrado: " + email));
    }

    public Contato inserir(Contato contato){
        if(contatosRepository.existsById(contato.getEmail()))
            throw new IllegalArgumentException("Já existe um contato cadastrado com este e-mail: " + contato.getEmail());

        contato.setDataCadastro(LocalDateTime.now());
        return contatosRepository.save(contato);
    }

    public void remover(String email){
        if(!contatosRepository.existsById(email))
            throw new IllegalArgumentException("Contato não encontrado: " + email);

        contatosRepository.deleteById(email);
    }

    public Contato atualizar(String email, Contato contatoAtualizado){
        Contato contatoExistente = contatosRepository.findById(email)
                .orElseThrow(() -> new IllegalArgumentException("Contato não encontrado: " + email));

        if(contatoAtualizado.getNome() != null && !contatoAtualizado.getNome().isBlank())
            contatoExistente.setNome(contatoAtualizado.getNome());

        if(contatoAtualizado.getTelefone() != null && !contatoAtualizado.getTelefone().isBlank())
            contatoExistente.setTelefone(contatoAtualizado.getTelefone());

        if(contatoAtualizado.getCep() != null && !contatoAtualizado.getCep().isBlank())
            contatoExistente.setCep(contatoAtualizado.getCep());

        return contatosRepository.save(contatoExistente);
    }
}
