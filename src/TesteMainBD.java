import entidades.Paciente;
import repositorios.PacienteRepository;
import java.util.List;

public class TesteMainBD {
    
    public static void main(String[] args) {

        Paciente novoPaciente = new Paciente();
        novoPaciente.setNome("Carlos Andre");
        novoPaciente.setTelefone("44998171626");
        novoPaciente.setEndereco("Rua Parana, 123");
        novoPaciente.setNascimento("10/05/1990");

        PacienteRepository repo = new PacienteRepository();
        repo.salvarPaciente(novoPaciente);
        System.out.println("Paciente salvo");

        Paciente achado = repo.buscarPorTelefone("44998171626");
        if (achado != null) {
            System.out.println("Paciente encontrado: " + achado.getNome());
        }
        Paciente errado = repo.buscarPorTelefone("44988887777");
        if (errado != null) {
            System.out.println("Paciente encontrado: " + achado.getNome());
        }
        else {
            System.out.println("Paciente não encontrado.");
        }
        
        List<Paciente> todos = repo.buscarTodos();
        System.out.println("Quantidade de pacientes no banco: " + todos.size());
        
    }
}