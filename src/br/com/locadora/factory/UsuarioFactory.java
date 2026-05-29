package br.com.locadora.factory;

import br.com.locadora.model.Usuario;

public final class UsuarioFactory {
    private UsuarioFactory() {
    }

    public static Usuario criarUsuario(int id, String nome, String email, String telefone) {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setTelefone(telefone);
        return usuario;
    }
}

