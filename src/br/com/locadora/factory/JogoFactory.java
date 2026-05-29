package br.com.locadora.factory;

import br.com.locadora.model.Jogo;

public final class JogoFactory {
    private JogoFactory() {
    }

    public static Jogo criarJogo(int id, String titulo, boolean disponivel, String plataforma, String genero, String estudio) {
        Jogo jogo = new Jogo();
        jogo.setId(id);
        jogo.setTitulo(titulo);
        jogo.setDisponivel(disponivel);
        jogo.setPlataforma(plataforma);
        jogo.setGenero(genero);
        jogo.setEstudio(estudio);
        return jogo;
    }
}

