package br.com.locadora.view;

import br.com.locadora.controller.FilmeController;
import br.com.locadora.controller.JogoController;
import br.com.locadora.controller.LocacaoController;
import br.com.locadora.controller.UsuarioController;
import br.com.locadora.model.Filme;
import br.com.locadora.model.Item;
import br.com.locadora.model.Jogo;
import br.com.locadora.model.Locacao;
import br.com.locadora.model.Usuario;
import br.com.locadora.utils.Logger;

import java.util.List;
import java.util.Scanner;

public class LocacaoView {
    private final LocacaoController locacaoController = new LocacaoController();
    private final UsuarioController usuarioController = new UsuarioController();
    private final FilmeController filmeController = new FilmeController();
    private final JogoController jogoController = new JogoController();
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new LocacaoView().exibirMenu();
    }

    public void exibirMenu() {
        int opcao;

        do {
            System.out.println();
            System.out.println("===== LOCACOES =====");
            System.out.println("1 - Registrar locacao");
            System.out.println("2 - Registrar devolucao");
            System.out.println("3 - Listar locacoes");
            System.out.println("4 - Listar locacoes ativas");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opcao: ");

            opcao = lerInteiro();

            switch (opcao) {
                case 1:
                    registrarLocacao();
                    break;
                case 2:
                    registrarDevolucao();
                    break;
                case 3:
                    listarTodas();
                    break;
                case 4:
                    listarAtivas();
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opcao invalida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private void registrarLocacao() {
        System.out.println("--- Registrar locacao ---");

        System.out.print("Informe o id do usuario: ");
        int idUsuario = lerInteiro();
        Usuario usuario = usuarioController.buscarPorId(idUsuario);
        if (usuario == null) {
            System.out.println("Usuario nao encontrado.");
            return;
        }

        System.out.println("Tipo de item: 1 - Filme | 2 - Jogo");
        System.out.print("Escolha o tipo: ");
        int tipo = lerInteiro();

        Item item = selecionarItem(tipo);
        if (item == null) {
            return;
        }

        try {
            Locacao locacao = locacaoController.registrarLocacao(usuario, item);
            atualizarDisponibilidade(item, false);
            System.out.println("Locacao registrada com sucesso! Id da locacao: " + locacao.getId());
            Logger.registrar("Locacao registrada: id=" + locacao.getId()
                    + ", usuario=" + usuario.getId() + ", item=" + item.getTitulo());
        } catch (IllegalStateException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void registrarDevolucao() {
        System.out.println("--- Registrar devolucao ---");
        System.out.print("Informe o id da locacao: ");
        int idLocacao = lerInteiro();

        try {
            Locacao locacao = locacaoController.registrarDevolucao(idLocacao);
            atualizarDisponibilidade(locacao.getItem(), true);
            System.out.println("Devolucao registrada com sucesso!");
            Logger.registrar("Devolucao registrada: locacao=" + locacao.getId()
                    + ", item=" + locacao.getItem().getTitulo());
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listarTodas() {
        System.out.println("--- Lista de locacoes ---");
        List<Locacao> locacoes = locacaoController.listarTodas();

        if (locacoes.isEmpty()) {
            System.out.println("Nenhuma locacao registrada.");
            return;
        }

        for (Locacao locacao : locacoes) {
            System.out.println(locacao);
        }
    }

    private void listarAtivas() {
        System.out.println("--- Locacoes ativas ---");
        List<Locacao> ativas = locacaoController.listarAtivas();

        if (ativas.isEmpty()) {
            System.out.println("Nenhuma locacao ativa.");
            return;
        }

        for (Locacao locacao : ativas) {
            System.out.println(locacao);
        }
    }

    private Item selecionarItem(int tipo) {
        System.out.print("Informe o id do item: ");
        int idItem = lerInteiro();

        if (tipo == 1) {
            Filme filme = filmeController.buscarPorId(idItem);
            if (filme == null) {
                System.out.println("Filme nao encontrado.");
            }
            return filme;
        }

        if (tipo == 2) {
            Jogo jogo = jogoController.buscarPorId(idItem);
            if (jogo == null) {
                System.out.println("Jogo nao encontrado.");
            }
            return jogo;
        }

        System.out.println("Tipo de item invalido.");
        return null;
    }

    private void atualizarDisponibilidade(Item item, boolean disponivel) {
        if (item instanceof Filme) {
            filmeController.atualizarDisponibilidade(item.getId(), disponivel);
        } else if (item instanceof Jogo) {
            jogoController.atualizarDisponibilidade(item.getId(), disponivel);
        }
    }

    private int lerInteiro() {
        while (true) {
            String entrada = scanner.nextLine();
            try {
                return Integer.parseInt(entrada.trim());
            } catch (NumberFormatException e) {
                System.out.print("Valor invalido. Digite um numero: ");
            }
        }
    }
}
