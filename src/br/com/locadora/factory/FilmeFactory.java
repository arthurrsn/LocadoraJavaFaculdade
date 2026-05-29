package br.com.locadora.factory;

import br.com.locadora.model.Filme;

public final class FilmeFactory {
    private FilmeFactory() {
    }

    public static Filme criarFilme(int id, String titulo, boolean disponivel, String diretor, String genero, int duracao) {
        Filme filme = new Filme();
        filme.setId(id);
        filme.setTitulo(titulo);
        filme.setDisponivel(disponivel);
        filme.setDiretor(diretor);
        filme.setGenero(genero);
        filme.setDuracao(duracao);
        return filme;
    }
}

