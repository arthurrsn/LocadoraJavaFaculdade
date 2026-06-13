package br.com.locadora.view;

import br.com.locadora.controller.UsuarioController;
import br.com.locadora.model.Usuario;

import java.util.List;
import java.util.Scanner;

public class UsuarioView {
    private final UsuarioController controller = new UsuarioController();
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new UsuarioView().exibirMenu();
    }

    public void exibirMenu() {
        int opcao;

        do {
            System.out.println();
            System.out.println("===== CRUD DE USUARIOS =====");
            System.out.println("1 - Cadastrar usuario");
            System.out.println("2 - Listar usuarios");
            System.out.println("3 - Buscar usuario por id");
            System.out.println("4 - Atualizar usuario");
            System.out.println("5 - Deletar usuario");
            System.out.println("0 - Sair");
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
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opcao invalida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private void cadastrar() {
        System.out.println("--- Cadastrar usuario ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        try {
            Usuario usuario = controller.cadastrar(nome, email, telefone);
            System.out.println("Usuario cadastrado com sucesso! Id gerado: " + usuario.getId());
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listar() {
        System.out.println("--- Lista de usuarios ---");
        List<Usuario> usuarios = controller.listar();

        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuario cadastrado.");
            return;
        }

        for (Usuario usuario : usuarios) {
            System.out.println(usuario);
        }
    }

    private void buscarPorId() {
        System.out.println("--- Buscar usuario por id ---");
        System.out.print("Informe o id: ");
        int id = lerInteiro();

        Usuario usuario = controller.buscarPorId(id);
        if (usuario == null) {
            System.out.println("Usuario nao encontrado.");
        } else {
            System.out.println(usuario);
        }
    }

    private void atualizar() {
        System.out.println("--- Atualizar usuario ---");
        System.out.print("Informe o id do usuario: ");
        int id = lerInteiro();

        if (controller.buscarPorId(id) == null) {
            System.out.println("Usuario nao encontrado.");
            return;
        }

        System.out.print("Novo nome: ");
        String nome = scanner.nextLine();
        System.out.print("Novo email: ");
        String email = scanner.nextLine();
        System.out.print("Novo telefone: ");
        String telefone = scanner.nextLine();

        try {
            boolean atualizou = controller.atualizar(id, nome, email, telefone);
            if (atualizou) {
                System.out.println("Usuario atualizado com sucesso!");
            } else {
                System.out.println("Nao foi possivel atualizar o usuario.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void deletar() {
        System.out.println("--- Deletar usuario ---");
        System.out.print("Informe o id do usuario: ");
        int id = lerInteiro();

        boolean deletou = controller.deletar(id);
        if (deletou) {
            System.out.println("Usuario deletado com sucesso!");
        } else {
            System.out.println("Usuario nao encontrado.");
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
