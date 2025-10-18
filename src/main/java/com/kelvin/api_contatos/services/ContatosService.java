package com.kelvin.api_contatos.services;

import com.kelvin.api_contatos.entities.Contato;
import com.kelvin.api_contatos.repositories.ContatosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ContatosService {

    private final ContatosRepository contatosRepository;

    @Autowired
    private RestTemplate restTemplate;

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

        buscaEndereco(contato);

        contato.setDataCadastro(LocalDateTime.now());
        return contatosRepository.save(contato);
    }

    public void buscaEndereco(Contato contato){

        String url = "https://viacep.com.br/ws/" + contato.getCep() + "/json/";

        try {
            Map<String, String> response = restTemplate.getForObject(url, Map.class);

            if(response == null || response.containsKey("erro")){
                System.out.println("CEP não encontrado: " + contato.getCep());
                return;
            }

            contato.setEndereco(response.get("logradouro"));
            contato.setCidade(response.get("localidade"));
            contato.setUf(response.get("uf"));

        } catch (Exception e){
            System.err.println("Erro ao buscar endereço no ViaCEP: " + e.getMessage());
        }
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

        if(contatoAtualizado.getCep() != null && !contatoAtualizado.getCep().isBlank()) {
            contatoExistente.setCep(contatoAtualizado.getCep());
            buscaEndereco(contatoExistente);
        }

        return contatosRepository.save(contatoExistente);
    }
}
