package documentos;

import entidades.Medico;
import entidades.Paciente;
import java.util.Scanner;
import javax.persistence.*; 

/**
 *
 * Classe responsável por representar uma consulta médica no sistema.
 * 
 * Cada consulta possui:
 * - paciente
 * - médico
 * - data
 * - horário
 * - tipo da consulta
 * 
 * O tipo pode representar:
 * consulta normal ou retorno.
 */
@Entity
@Table(name = "consultas") 
public class Consulta {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
    
    @ManyToOne //pra poder ter mais de uma consulta pro mesmo paciente
    private Paciente p;
    
    private String data;
    private String horario;
    
    @ManyToOne //pra poder ter mais de uma consulta pro mesmo médico
    private Medico m;
    
    private String tipo; //(consulta normal: duração de 1 hora, retorno: duração de 30 minutos.)

    
    public Consulta() {
    }

    /**
     * Construtor da classe Consulta.
     * * @param p paciente da consulta
     * @param data data da consulta
     * @param horario horário da consulta
     * @param m médico responsável
     * @param tipo tipo da consulta
     */
    public Consulta(Paciente p, String data, String horario, Medico m, String tipo) {
        this.p = p;
        this.data = data;
        this.horario = horario;
        this.m = m;
        this.tipo = tipo;
    }
    // metodos gets e sets dos atributos da classe
    public String getHorario (){
        return horario;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Consulta -> Data: " + data + " | Horário: " + horario + " | Paciente: " + p.getNome() + " | Médico: " + m.getNome();
    }
    //  Define ou altera a data da consulta.
    public void setData(String data) {
        this.data = data;
    }

    public Paciente getP() {
        return p;
    }

    public void setP(Paciente p) {
        this.p = p;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Medico getM() {
        return m;
    }

    public void setM(Medico m) {
        this.m = m;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}