package br.com.locadora.controller;

import br.com.locadora.dal.JogoDAL;
import br.com.locadora.factory.JogoFactory;
import br.com.locadora.model.Jogo;

import java.util.List;

public class JogoController {

    private final JogoDAL jogoDAL = new JogoDAL();

    public Jogo cadastrar(String titulo, String plataforma, String genero, String estudio) {
        validarDados(titulo, plataforma, genero, estudio);

        for (Jogo j : jogoDAL.listar()) {
            if (j.getTitulo().equalsIgnoreCase(titulo.trim())) {
                throw new IllegalArgumentException("Ja existe um jogo com esse titulo.");
            }
        }

        int novoId = gerarProximoId();
        Jogo jogo = JogoFactory.criarJogo(novoId, titulo, true, plataforma, genero, estudio);
        jogoDAL.inserir(jogo);
        return jogo;
    }

    public List<Jogo> listar() {
        return jogoDAL.listar();
    }

    public Jogo buscarPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("O id deve ser maior que zero.");
        }
        return jogoDAL.buscarPorId(id);
    }

    public boolean atualizar(int id, String titulo, String plataforma, String genero, String estudio) {
        validarDados(titulo, plataforma, genero, estudio);

        Jogo jogoExistente = jogoDAL.buscarPorId(id);
        if (jogoExistente == null) return false;

        Jogo jogoAtualizado = JogoFactory.criarJogo(id, titulo, jogoExistente.isDisponivel(), plataforma, genero, estudio);
        return jogoDAL.atualizar(jogoAtualizado);
    }

    public boolean deletar(int id) {
        return jogoDAL.deletar(id);
    }

    public boolean atualizarDisponibilidade(int id, boolean disponivel) {
        Jogo jogo = jogoDAL.buscarPorId(id);
        if (jogo == null) return false;

        jogo.setDisponivel(disponivel);
        return jogoDAL.atualizar(jogo);
    }

    private int gerarProximoId() {
        int maiorId = 0;
        for (Jogo jogo : jogoDAL.listar()) {
            if (jogo.getId() > maiorId) {
                maiorId = jogo.getId();
            }
        }
        return maiorId + 1;
    }

    private void validarDados(String titulo, String plataforma, String genero, String estudio) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("O titulo nao pode ficar em branco.");
        }
        if (plataforma == null || plataforma.trim().isEmpty()) {
            throw new IllegalArgumentException("A plataforma nao pode ficar em branco.");
        }
        if (genero == null || genero.trim().isEmpty()) {
            throw new IllegalArgumentException("O genero nao pode ficar em branco.");
        }
        if (estudio == null || estudio.trim().isEmpty()) {
            throw new IllegalArgumentException("O estudio nao pode ficar em branco.");
        }
    }
}