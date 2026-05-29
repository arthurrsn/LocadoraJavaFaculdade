package br.com.locadora.model;

public class Filme extends Item {
    private String diretor;
    private String genero;
    private int duracao;

    public String getDiretor() {
        return diretor;
    }

    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    @Override
    public String toString() {
        return "Filme{" +
                "id=" + getId() +
                ", titulo='" + getTitulo() + '\'' +
                ", disponivel=" + isDisponivel() +
                ", diretor='" + diretor + '\'' +
                ", genero='" + genero + '\'' +
                ", duracao=" + duracao +
                '}';
    }
}

