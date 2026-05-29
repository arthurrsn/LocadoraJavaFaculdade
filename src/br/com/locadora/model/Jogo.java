package br.com.locadora.model;

public class Jogo extends Item {
    private String plataforma;
    private String genero;
    private String estudio;

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEstudio() {
        return estudio;
    }

    public void setEstudio(String estudio) {
        this.estudio = estudio;
    }

    @Override
    public String toString() {
        return "Jogo{" +
                "id=" + getId() +
                ", titulo='" + getTitulo() + '\'' +
                ", disponivel=" + isDisponivel() +
                ", plataforma='" + plataforma + '\'' +
                ", genero='" + genero + '\'' +
                ", estudio='" + estudio + '\'' +
                '}';
    }
}

