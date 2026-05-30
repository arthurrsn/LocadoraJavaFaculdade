package br.com.locadora.controller;

import br.com.locadora.dal.UsuarioDAL;
import br.com.locadora.factory.UsuarioFactory;
import br.com.locadora.model.Usuario;

import java.util.List;

public class UsuarioController {
    private final UsuarioDAL usuarioDAL = new UsuarioDAL();

    public Usuario cadastrar(String nome, String email, String telefone) {
        validarDados(nome, email, telefone);

        int novoId = gerarProximoId();
        Usuario usuario = UsuarioFactory.criarUsuario(novoId, nome.trim(), email.trim(), telefone.trim());
        usuarioDAL.inserir(usuario);
        return usuario;
    }

    public List<Usuario> listar() {
        return usuarioDAL.listar();
    }

    public Usuario buscarPorId(int id) {
        return usuarioDAL.buscarPorId(id);
    }

    public boolean atualizar(int id, String nome, String email, String telefone) {
        validarDados(nome, email, telefone);

        if (usuarioDAL.buscarPorId(id) == null) {
            return false;
        }

        Usuario usuario = UsuarioFactory.criarUsuario(id, nome.trim(), email.trim(), telefone.trim());
        return usuarioDAL.atualizar(usuario);
    }

    public boolean deletar(int id) {
        return usuarioDAL.deletar(id);
    }

    private int gerarProximoId() {
        int maiorId = 0;
        for (Usuario usuario : usuarioDAL.listar()) {
            if (usuario.getId() > maiorId) {
                maiorId = usuario.getId();
            }
        }
        return maiorId + 1;
    }

    private void validarDados(String nome, String email, String telefone) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome nao pode ficar em branco.");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("O email nao pode ficar em branco.");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("O email informado e invalido.");
        }
        if (telefone == null || telefone.trim().isEmpty()) {
            throw new IllegalArgumentException("O telefone nao pode ficar em branco.");
        }
    }
}
