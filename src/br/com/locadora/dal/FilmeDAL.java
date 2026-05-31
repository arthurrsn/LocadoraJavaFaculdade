package br.com.locadora.dal;

import br.com.locadora.factory.FilmeFactory;
import br.com.locadora.model.Filme;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FilmeDAL {

    private static final String ARQUIVO = "filmes.txt";
    private static final String SEPARADOR = ";";

    public List<Filme> listar() {
        List<Filme> filmes = new ArrayList<>();

        try (BufferedReader leitor = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                Filme filme = converterLinhaParaFilme(linha);
                if (filme != null) filmes.add(filme);
            }
        } catch (IOException e) {
        }

        return filmes;
    }

    public Filme buscarPorId(int id) {
        for (Filme filme : listar()) {
            if (filme.getId() == id) return filme;
        }
        return null;
    }

    public void inserir(Filme filme) {
        List<Filme> filmes = listar();
        filmes.add(filme);
        gravarTodos(filmes);
    }

    public boolean atualizar(Filme filmeAtualizado) {
        List<Filme> filmes = listar();

        for (int i = 0; i < filmes.size(); i++) {
            if (filmes.get(i).getId() == filmeAtualizado.getId()) {
                filmes.set(i, filmeAtualizado);
                gravarTodos(filmes);
                return true;
            }
        }

        return false;
    }

    public boolean deletar(int id) {
        List<Filme> filmes = listar();
        boolean removeu = filmes.removeIf(filme -> filme.getId() == id);
        if (removeu) gravarTodos(filmes);
        return removeu;
    }

    private void gravarTodos(List<Filme> filmes) {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (Filme filme : filmes) {
                escritor.write(converterFilmeParaLinha(filme));
                escritor.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao gravar o arquivo de filmes: " + e.getMessage());
        }
    }

    private String converterFilmeParaLinha(Filme filme) {
        return filme.getId() + SEPARADOR
                + filme.getTitulo() + SEPARADOR
                + filme.isDisponivel() + SEPARADOR
                + filme.getDiretor() + SEPARADOR
                + filme.getGenero() + SEPARADOR
                + filme.getDuracao();
    }

    private Filme converterLinhaParaFilme(String linha) {
        String[] partes = linha.split(SEPARADOR, -1);
        if (partes.length < 6) return null;

        try {
            int id              = Integer.parseInt(partes[0].trim());
            String titulo       = partes[1];
            boolean disponivel  = Boolean.parseBoolean(partes[2]);
            String diretor      = partes[3];
            String genero       = partes[4];
            int duracao         = Integer.parseInt(partes[5].trim());

            return FilmeFactory.criarFilme(id, titulo, disponivel, diretor, genero, duracao);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}