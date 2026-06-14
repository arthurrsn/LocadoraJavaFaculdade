package br.com.locadora.view;

import br.com.locadora.utils.Logger;

import java.util.Scanner;

public class MenuView {
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new MenuView().exibirMenu();
    }

    public void exibirMenu() {
        Logger.registrar("Sistema iniciado.");

        int opcao;

        do {
            System.out.println();
            System.out.println("===== LOCADORA =====");
            System.out.println("1 - Usuarios");
            System.out.println("2 - Filmes");
            System.out.println("3 - Jogos");
            System.out.println("4 - Locacoes");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opcao: ");

            opcao = lerInteiro();

            switch (opcao) {
                case 1:
                    new UsuarioView().exibirMenu();
                    break;
                case 2:
                    new FilmeView().exibirMenu();
                    break;
                case 3:
                    new JogoView().exibirMenu();
                    break;
                case 4:
                    new LocacaoView().exibirMenu();
                    break;
                case 0:
                    System.out.println("Encerrando o sistema...");
                    Logger.registrar("Sistema encerrado.");
                    break;
                default:
                    System.out.println("Opcao invalida. Tente novamente.");
            }
        } while (opcao != 0);
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
