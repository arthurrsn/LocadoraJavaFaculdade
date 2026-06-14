package br.com.locadora.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Logger {
    private static final String ARQUIVO = "log.txt";
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Logger() {
    }

    public static void registrar(String mensagem) {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(ARQUIVO, true))) {
            escritor.write("[" + LocalDateTime.now().format(FORMATO) + "] " + mensagem);
            escritor.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao gravar no log: " + e.getMessage());
        }
    }
}
