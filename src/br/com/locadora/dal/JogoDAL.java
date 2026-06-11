package br.com.locadora.dal;

import br.com.locadora.factory.JogoFactory;
import br.com.locadora.model.Jogo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JogoDAL {

    private static final String ARQUIVO = "jogos.txt";
    private static final String SEPARADOR = ";";

    public List<Jogo> listar() {
        List<Jogo> jogos = new ArrayList<>();

        try (BufferedReader leitor = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                Jogo jogo = converterLinhaParaJogo(linha);
                if (jogo != null) jogos.add(jogo);
            }
        } catch (IOException e) {
        }

        return jogos;
    }

    public Jogo buscarPorId(int id) {
        for (Jogo jogo : listar()) {
            if (jogo.getId() == id) return jogo;
        }
        return null;
    }

    public void inserir(Jogo jogo) {
        if (jogo == null) {
            throw new IllegalArgumentException("Jogo nao pode ser nulo.");
        }
        List<Jogo> jogos = listar();
        jogos.add(jogo);
        gravarTodos(jogos);
    }

    public boolean atualizar(Jogo jogoAtualizado) {
        List<Jogo> jogos = listar();

        for (int i = 0; i < jogos.size(); i++) {
            if (jogos.get(i).getId() == jogoAtualizado.getId()) {
                jogos.set(i, jogoAtualizado);
                gravarTodos(jogos);
                return true;
            }
        }

        return false;
    }

    public boolean deletar(int id) {
        List<Jogo> jogos = listar();
        boolean removeu = jogos.removeIf(jogo -> jogo.getId() == id);
        if (removeu) gravarTodos(jogos);
        return removeu;
    }

    private void gravarTodos(List<Jogo> jogos) {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (Jogo jogo : jogos) {
                escritor.write(converterJogoParaLinha(jogo));
                escritor.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao gravar o arquivo de jogos: " + e.getMessage());
        }
    }

    private String converterJogoParaLinha(Jogo jogo) {
        return jogo.getId() + SEPARADOR
                + jogo.getTitulo() + SEPARADOR
                + jogo.isDisponivel() + SEPARADOR
                + jogo.getPlataforma() + SEPARADOR
                + jogo.getGenero() + SEPARADOR
                + jogo.getEstudio();
    }

    private Jogo converterLinhaParaJogo(String linha) {
        String[] partes = linha.split(SEPARADOR, -1);
        if (partes.length < 6) return null;

        try {
            int id             = Integer.parseInt(partes[0].trim());
            String titulo      = partes[1];
            boolean disponivel = Boolean.parseBoolean(partes[2]);
            String plataforma  = partes[3];
            String genero      = partes[4];
            String estudio     = partes[5];

            return JogoFactory.criarJogo(id, titulo, disponivel, plataforma, genero, estudio);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}