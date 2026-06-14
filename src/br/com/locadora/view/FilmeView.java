package br.com.locadora.view;

import br.com.locadora.controller.FilmeController;
import br.com.locadora.model.Filme;
import br.com.locadora.utils.Logger;

import java.util.List;
import java.util.Scanner;

public class FilmeView {
    private final FilmeController controller = new FilmeController();
    private final Scanner scanner;

    public FilmeView() {
        this(new Scanner(System.in));
    }

    public FilmeView(Scanner scanner) {
        this.scanner = scanner;
    }

    public static void main(String[] args) {
        new FilmeView().exibirMenu();
    }

    public void exibirMenu() {
        int opcao;

        do {
            System.out.println();
            System.out.println("===== CRUD DE FILMES =====");
            System.out.println("1 - Cadastrar filme");
            System.out.println("2 - Listar filmes");
            System.out.println("3 - Buscar filme por id");
            System.out.println("4 - Atualizar filme");
            System.out.println("5 - Deletar filme");
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
        System.out.println("--- Cadastrar filme ---");
        System.out.print("Titulo: ");
        String titulo = scanner.nextLine();
        System.out.print("Diretor: ");
        String diretor = scanner.nextLine();
        System.out.print("Genero: ");
        String genero = scanner.nextLine();
        System.out.print("Duracao (minutos): ");
        int duracao = lerInteiro();

        try {
            Filme filme = controller.cadastrar(titulo, diretor, genero, duracao);
            System.out.println("Filme cadastrado com sucesso! Id gerado: " + filme.getId());
            Logger.registrar("Filme cadastrado: id=" + filme.getId() + ", titulo=" + filme.getTitulo());
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listar() {
        System.out.println("--- Lista de filmes ---");
        List<Filme> filmes = controller.listar();

        if (filmes.isEmpty()) {
            System.out.println("Nenhum filme cadastrado.");
            return;
        }

        for (Filme filme : filmes) {
            System.out.println(filme);
        }
    }

    private void buscarPorId() {
        System.out.println("--- Buscar filme por id ---");
        System.out.print("Informe o id: ");
        int id = lerInteiro();

        try {
            Filme filme = controller.buscarPorId(id);
            if (filme == null) {
                System.out.println("Filme nao encontrado.");
            } else {
                System.out.println(filme);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void atualizar() {
        System.out.println("--- Atualizar filme ---");
        System.out.print("Informe o id do filme: ");
        int id = lerInteiro();

        if (controller.buscarPorId(id) == null) {
            System.out.println("Filme nao encontrado.");
            return;
        }

        System.out.print("Novo titulo: ");
        String titulo = scanner.nextLine();
        System.out.print("Novo diretor: ");
        String diretor = scanner.nextLine();
        System.out.print("Novo genero: ");
        String genero = scanner.nextLine();
        System.out.print("Nova duracao (minutos): ");
        int duracao = lerInteiro();

        try {
            boolean atualizou = controller.atualizar(id, titulo, diretor, genero, duracao);
            if (atualizou) {
                System.out.println("Filme atualizado com sucesso!");
                Logger.registrar("Filme atualizado: id=" + id);
            } else {
                System.out.println("Nao foi possivel atualizar o filme.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void deletar() {
        System.out.println("--- Deletar filme ---");
        System.out.print("Informe o id do filme: ");
        int id = lerInteiro();

        boolean deletou = controller.deletar(id);
        if (deletou) {
            System.out.println("Filme deletado com sucesso!");
            Logger.registrar("Filme deletado: id=" + id);
        } else {
            System.out.println("Filme nao encontrado.");
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
