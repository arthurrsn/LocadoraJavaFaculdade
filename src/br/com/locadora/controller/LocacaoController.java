package br.com.locadora.controller;

import br.com.locadora.dal.LocacaoDAL;
import br.com.locadora.factory.LocacaoFactory;
import br.com.locadora.model.Item;
import br.com.locadora.model.Locacao;
import br.com.locadora.model.Usuario;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LocacaoController {

    private final LocacaoDAL locacaoDAL;
    private List<Locacao> locacoes;
    private int proximoId;

    public LocacaoController() {
        this.locacaoDAL = new LocacaoDAL();
        this.locacoes = locacaoDAL.carregar();
        this.proximoId = locacoes.stream()
                .mapToInt(Locacao::getId)
                .max()
                .orElse(0) + 1;
    }

    public Locacao registrarLocacao(Usuario usuario, Item item) {
        if (!item.isDisponivel()) {
            throw new IllegalStateException("Item '" + item.getTitulo() + "' nao esta disponivel para locacao.");
        }

        Locacao locacao = LocacaoFactory.criarLocacao(proximoId++, usuario, item, LocalDate.now(), null);

        item.setDisponivel(false);
        locacoes.add(locacao);
        salvar();

        return locacao;
    }

    public Locacao registrarDevolucao(int idLocacao) {
        Locacao locacao = buscarPorId(idLocacao)
                .orElseThrow(() -> new IllegalArgumentException("Locacao com ID " + idLocacao + " nao encontrada."));

        if (!locacao.isAtiva()) {
            throw new IllegalStateException("Locacao ja foi encerrada.");
        }

        locacao.setDataDevolucao(LocalDate.now());
        locacao.getItem().setDisponivel(true);
        salvar();

        return locacao;
    }

    public List<Locacao> listarTodas() {
        return locacoes;
    }

    public List<Locacao> listarAtivas() {
        return locacoes.stream()
                .filter(Locacao::isAtiva)
                .collect(Collectors.toList());
    }

    public Optional<Locacao> buscarPorId(int id) {
        return locacoes.stream()
                .filter(l -> l.getId() == id)
                .findFirst();
    }

    public void alterarDisponibilidadeItem(int idLocacao, boolean disponivel) {
        Locacao locacao = buscarPorId(idLocacao)
                .orElseThrow(() -> new IllegalArgumentException("Locacao com ID " + idLocacao + " nao encontrada."));

        locacao.getItem().setDisponivel(disponivel);
        salvar();
    }

    private void salvar() {
        locacaoDAL.salvar(locacoes);
    }
}