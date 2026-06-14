package br.com.locadora.model;

public abstract class Item implements Locavel {
    private int id;
    private String titulo;
    private boolean disponivel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", disponivel=" + disponivel +
                '}';
    }
}

