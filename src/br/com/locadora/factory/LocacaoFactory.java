package br.com.locadora.factory;

import br.com.locadora.model.Item;
import br.com.locadora.model.Locacao;
import br.com.locadora.model.Usuario;

import java.time.LocalDate;

public final class LocacaoFactory {
    private LocacaoFactory() {
    }

    public static Locacao criarLocacao(int id, Usuario usuario, Item item, LocalDate dataLocacao, LocalDate dataDevolucao) {
        Locacao locacao = new Locacao();
        locacao.setId(id);
        locacao.setUsuario(usuario);
        locacao.setItem(item);
        locacao.setDataLocacao(dataLocacao);
        locacao.setDataDevolucao(dataDevolucao);
        return locacao;
    }
}

