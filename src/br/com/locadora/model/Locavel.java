package br.com.locadora.model;

public interface Locavel {
    int getId();

    String getTitulo();

    boolean isDisponivel();

    void setDisponivel(boolean disponivel);
}
