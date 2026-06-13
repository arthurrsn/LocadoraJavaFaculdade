package br.com.locadora.dal;
 
import br.com.locadora.model.Filme;
import br.com.locadora.model.Item;
import br.com.locadora.model.Jogo;
import br.com.locadora.model.Locacao;
import br.com.locadora.model.Usuario;
 
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
 
public class LocacaoDAL {
 
    private static final String ARQUIVO = "locacoes.txt";
 
    public void salvar(List<Locacao> locacoes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (Locacao locacao : locacoes) {
                writer.write(serializar(locacao));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar locacoes: " + e.getMessage());
        }
    }
 
    public List<Locacao> carregar() {
        List<Locacao> locacoes = new ArrayList<>();
        File arquivo = new File(ARQUIVO);
 
        if (!arquivo.exists()) return locacoes;
 
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    Locacao locacao = desserializar(linha);
                    if (locacao != null) locacoes.add(locacao);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar locacoes: " + e.getMessage());
        }
 
        return locacoes;
    }
 
    private String serializar(Locacao l) {
        Item item = l.getItem();
        String tipoItem = item instanceof Filme ? "FILME" : "JOGO";
 
        return l.getId() + ";" +
                l.getUsuario().getId() + ";" +
                l.getUsuario().getNome() + ";" +
                l.getUsuario().getEmail() + ";" +
                l.getUsuario().getTelefone() + ";" +
                tipoItem + ";" +
                item.getId() + ";" +
                item.getTitulo() + ";" +
                item.isDisponivel() + ";" +
                l.getDataLocacao() + ";" +
                (l.getDataDevolucao() != null ? l.getDataDevolucao() : "null");
    }
 
    private Locacao desserializar(String linha) {
        try {
            String[] partes = linha.split(";");
 
            // Usuario
            Usuario usuario = new Usuario();
            usuario.setId(Integer.parseInt(partes[1]));
            usuario.setNome(partes[2]);
            usuario.setEmail(partes[3]);
            usuario.setTelefone(partes[4]);
 
            // Item
            String tipoItem = partes[5];
            Item item;
            if ("FILME".equals(tipoItem)) {
                item = new Filme();
            } else {
                item = new Jogo();
            }
            item.setId(Integer.parseInt(partes[6]));
            item.setTitulo(partes[7]);
            item.setDisponivel(Boolean.parseBoolean(partes[8]));
 
            // Locacao
            Locacao locacao = new Locacao();
            locacao.setId(Integer.parseInt(partes[0]));
            locacao.setUsuario(usuario);
            locacao.setItem(item);
            locacao.setDataLocacao(LocalDate.parse(partes[9]));
 
            if (!"null".equals(partes[10])) {
                locacao.setDataDevolucao(LocalDate.parse(partes[10]));
            }
 
            return locacao;
        } catch (Exception e) {
            System.err.println("Erro ao desserializar linha: " + linha + " -> " + e.getMessage());
            return null;
        }
    }
}