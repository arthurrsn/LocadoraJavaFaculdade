package br.com.locadora.dal;

import br.com.locadora.factory.UsuarioFactory;
import br.com.locadora.model.Usuario;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAL {
    private static final String ARQUIVO = "usuarios.txt";
    private static final String SEPARADOR = ";";

    public List<Usuario> listar() {
        List<Usuario> usuarios = new ArrayList<>();

        try (BufferedReader leitor = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                if (linha.trim().isEmpty()) {
                    continue;
                }
                Usuario usuario = converterLinhaParaUsuario(linha);
                if (usuario != null) {
                    usuarios.add(usuario);
                }
            }
        } catch (IOException e) {
        }

        return usuarios;
    }

    public Usuario buscarPorId(int id) {
        for (Usuario usuario : listar()) {
            if (usuario.getId() == id) {
                return usuario;
            }
        }
        return null;
    }

    public void inserir(Usuario usuario) {
        List<Usuario> usuarios = listar();
        usuarios.add(usuario);
        gravarTodos(usuarios);
    }

    public boolean atualizar(Usuario usuarioAtualizado) {
        List<Usuario> usuarios = listar();

        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId() == usuarioAtualizado.getId()) {
                usuarios.set(i, usuarioAtualizado);
                gravarTodos(usuarios);
                return true;
            }
        }

        return false;
    }

    public boolean deletar(int id) {
        List<Usuario> usuarios = listar();

        boolean removeu = usuarios.removeIf(usuario -> usuario.getId() == id);
        if (removeu) {
            gravarTodos(usuarios);
        }

        return removeu;
    }

    private void gravarTodos(List<Usuario> usuarios) {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (Usuario usuario : usuarios) {
                escritor.write(converterUsuarioParaLinha(usuario));
                escritor.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao gravar o arquivo de usuarios: " + e.getMessage());
        }
    }

    private String converterUsuarioParaLinha(Usuario usuario) {
        return usuario.getId() + SEPARADOR
                + usuario.getNome() + SEPARADOR
                + usuario.getEmail() + SEPARADOR
                + usuario.getTelefone();
    }

    private Usuario converterLinhaParaUsuario(String linha) {
        String[] partes = linha.split(SEPARADOR, -1);
        if (partes.length < 4) {
            return null;
        }

        try {
            int id = Integer.parseInt(partes[0].trim());
            String nome = partes[1];
            String email = partes[2];
            String telefone = partes[3];
            return UsuarioFactory.criarUsuario(id, nome, email, telefone);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
