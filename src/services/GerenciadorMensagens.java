package services;

import documentos.Consulta;
import java.util.List; 

/**
 * Classe responsável pelo gerenciamento de mensagens do sistema.
 * * O gerenciador envia lembretes automáticos de consultas
 * para pacientes cadastrados.
 */
public class GerenciadorMensagens {
    
    /**
     * Dispara lembretes de consultas para os pacientes.
     * 
     * O método verifica:
     * - se a consulta pertence à data informada
     * - se o paciente possui telefone ou email cadastrado
     */
    public void dispararLembretesDeConsulta(List<Consulta> consultas, String data) {
        System.out.println("Enviando mensagens...\n");
        //Percorre todas as consultas do banco
        for (Consulta c : consultas) {
            // Verifica se a consulta é da data informada
            // e se o paciente possui telefone ou email (Só coloquei pra verificar se o paciente é nulo ou nao)
            if (c.getData().equals(data) && c.getP() != null && (c.getP().getTelefone() != null || c.getP().getEmail() != null)) {
                //adicionei isso pra que nao dê erro na hora de pegar o nome no banco
                //ja que se retornar um null, a funcao nao vai retornar nada
                String nomeMedico = "(Medico nao informado)";
                if (c.getM() != null) {
                    nomeMedico = c.getM().getNome();
                } 
                String horario = "(Horario a definir)";
                if (c.getHorario() != null && !c.getHorario().isEmpty()) {
                    horario = c.getHorario();
                }
                // exibe o lembrete
                System.out.println("Ola " + c.getP().getNome() +
                        ", lembrete da sua consulta amanha (" + c.getData() + ") as " +
                        horario + " com o " + nomeMedico + "\n");
            }
        }
    }
}