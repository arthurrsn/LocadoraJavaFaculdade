import br.com.locadora.factory.FilmeFactory;
import br.com.locadora.factory.LocacaoFactory;
import br.com.locadora.factory.UsuarioFactory;
import br.com.locadora.model.Filme;
import br.com.locadora.model.Locacao;
import br.com.locadora.model.Usuario;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Usuario usuario = UsuarioFactory.criarUsuario(1, "Maria", "maria@email.com", "11999999999");
        Filme filme = FilmeFactory.criarFilme(1, "Interestelar", true, "Christopher Nolan", "Ficcao", 169);
        Locacao locacao = LocacaoFactory.criarLocacao(1, usuario, filme, LocalDate.now(), null);

        System.out.println(usuario);
        System.out.println(filme);
        System.out.println(locacao);
    }
}