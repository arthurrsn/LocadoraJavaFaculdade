package br.com.locadora.controller;

import br.com.locadora.dal.FilmeDAL;
import br.com.locadora.factory.FilmeFactory;
import br.com.locadora.model.Filme;

import java.util.List;

public class FilmeController {

    private final FilmeDAL filmeDAL = new FilmeDAL();

    public Filme cadastrar(String titulo, String diretor, String genero, int duracao) {
        validarDados(titulo, diretor, genero, duracao);

        int novoId = gerarProximoId();
        Filme filme = FilmeFactory.criarFilme(novoId, titulo, true, diretor, genero, duracao);
        filmeDAL.inserir(filme);
        return filme;
    }

    public List<Filme> listar() {
        return filmeDAL.listar();
    }

    public Filme buscarPorId(int id) {
        return filmeDAL.buscarPorId(id);
    }

    public boolean atualizar(int id, String titulo, String diretor, String genero, int duracao) {
        validarDados(titulo, diretor, genero, duracao);

        Filme filmeExistente = filmeDAL.buscarPorId(id);
        if (filmeExistente == null) return false;

        Filme filmeAtualizado = FilmeFactory.criarFilme(id, titulo, filmeExistente.isDisponivel(), diretor, genero, duracao);
        return filmeDAL.atualizar(filmeAtualizado);
    }

    public boolean deletar(int id) {
        return filmeDAL.deletar(id);
    }

    public boolean atualizarDisponibilidade(int id, boolean disponivel) {
        Filme filme = filmeDAL.buscarPorId(id);
        if (filme == null) return false;

        filme.setDisponivel(disponivel);
        return filmeDAL.atualizar(filme);
    }

    private int gerarProximoId() {
        int maiorId = 0;
        for (Filme filme : filmeDAL.listar()) {
            if (filme.getId() > maiorId) {
                maiorId = filme.getId();
            }
        }
        return maiorId + 1;
    }

    private void validarDados(String titulo, String diretor, String genero, int duracao) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("O titulo nao pode ficar em branco.");
        }
        if (diretor == null || diretor.trim().isEmpty()) {
            throw new IllegalArgumentException("O diretor nao pode ficar em branco.");
        }
        if (genero == null || genero.trim().isEmpty()) {
            throw new IllegalArgumentException("O genero nao pode ficar em branco.");
        }
        if (duracao <= 0) {
            throw new IllegalArgumentException("A duracao deve ser maior que zero.");
        }
    }
}