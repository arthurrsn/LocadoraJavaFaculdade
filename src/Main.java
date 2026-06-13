import br.com.locadora.controller.LocacaoController;
import br.com.locadora.factory.FilmeFactory;
import br.com.locadora.factory.UsuarioFactory;
import br.com.locadora.model.Filme;
import br.com.locadora.model.Locacao;
import br.com.locadora.model.Usuario;

public class Main {
    public static void main(String[] args) {

        Usuario usuario = UsuarioFactory.criarUsuario(1, "Maria", "maria@email.com", "11999999999");
        Filme filme = FilmeFactory.criarFilme(1, "Interestelar", true, "Christopher Nolan", "Ficcao", 169);

        LocacaoController controller = new LocacaoController();

        Locacao locacao = controller.registrarLocacao(usuario, filme);
        System.out.println("Locacao registrada: " + locacao);

        System.out.println("Ativas: " + controller.listarAtivas());

        controller.registrarDevolucao(locacao.getId());
        System.out.println("Apos devolucao: " + controller.listarTodas());
    }
}