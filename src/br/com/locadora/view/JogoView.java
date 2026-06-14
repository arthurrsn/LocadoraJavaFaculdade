package br.com.locadora.view;

import br.com.locadora.controller.JogoController;
import br.com.locadora.model.Jogo;
import br.com.locadora.utils.Logger;

import java.util.List;
import java.util.Scanner;

public class JogoView {
    private final JogoController controller = new JogoController();
    private final Scanner scanner;

    public JogoView() {
        this(new Scanner(System.in));
    }

    public JogoView(Scanner scanner) {
        this.scanner = scanner;
    }

    public static void main(String[] args) {
        new JogoView().exibirMenu();
    }

    public void exibirMenu() {
        int opcao;

        do {
            System.out.println();
            System.out.println("===== CRUD DE JOGOS =====");
            System.out.println("1 - Cadastrar jogo");
            System.out.println("2 - Listar jogos");
            System.out.println("3 - Buscar jogo por id");
            System.out.println("4 - Atualizar jogo");
            System.out.println("5 - Deletar jogo");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opcao: ");

            opcao = lerInteiro();

            switch (opcao) {
                case 1:
                    cadastrar();
                    break;
                case 2:
                    listar();
                    break;
                case 3:
                    buscarPorId();
                    break;
                case 4:
                    atualizar();
                    break;
                case 5:
                    deletar();
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opcao invalida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private void cadastrar() {
        System.out.println("--- Cadastrar jogo ---");
        System.out.print("Titulo: ");
        String titulo = scanner.nextLine();
        System.out.print("Plataforma: ");
        String plataforma = scanner.nextLine();
        System.out.print("Genero: ");
        String genero = scanner.nextLine();
        System.out.print("Estudio: ");
        String estudio = scanner.nextLine();

        try {
            Jogo jogo = controller.cadastrar(titulo, plataforma, genero, estudio);
            System.out.println("Jogo cadastrado com sucesso! Id gerado: " + jogo.getId());
            Logger.registrar("Jogo cadastrado: id=" + jogo.getId() + ", titulo=" + jogo.getTitulo());
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listar() {
        System.out.println("--- Lista de jogos ---");
        List<Jogo> jogos = controller.listar();

        if (jogos.isEmpty()) {
            System.out.println("Nenhum jogo cadastrado.");
            return;
        }

        for (Jogo jogo : jogos) {
            System.out.println(jogo);
        }
    }

    private void buscarPorId() {
        System.out.println("--- Buscar jogo por id ---");
        System.out.print("Informe o id: ");
        int id = lerInteiro();

        try {
            Jogo jogo = controller.buscarPorId(id);
            if (jogo == null) {
                System.out.println("Jogo nao encontrado.");
            } else {
                System.out.println(jogo);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void atualizar() {
        System.out.println("--- Atualizar jogo ---");
        System.out.print("Informe o id do jogo: ");
        int id = lerInteiro();

        if (controller.buscarPorId(id) == null) {
            System.out.println("Jogo nao encontrado.");
            return;
        }

        System.out.print("Novo titulo: ");
        String titulo = scanner.nextLine();
        System.out.print("Nova plataforma: ");
        String plataforma = scanner.nextLine();
        System.out.print("Novo genero: ");
        String genero = scanner.nextLine();
        System.out.print("Novo estudio: ");
        String estudio = scanner.nextLine();

        try {
            boolean atualizou = controller.atualizar(id, titulo, plataforma, genero, estudio);
            if (atualizou) {
                System.out.println("Jogo atualizado com sucesso!");
                Logger.registrar("Jogo atualizado: id=" + id);
            } else {
                System.out.println("Nao foi possivel atualizar o jogo.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void deletar() {
        System.out.println("--- Deletar jogo ---");
        System.out.print("Informe o id do jogo: ");
        int id = lerInteiro();

        boolean deletou = controller.deletar(id);
        if (deletou) {
            System.out.println("Jogo deletado com sucesso!");
            Logger.registrar("Jogo deletado: id=" + id);
        } else {
            System.out.println("Jogo nao encontrado.");
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
