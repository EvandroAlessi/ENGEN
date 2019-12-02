/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.time.LocalDate;

/**
 * Modelo estrutural de Filme.
 * Gera objetos modelos para comunicação com BD ou utilização na GUI.
 * @author Evandro Alessi
 * @author Eric Ueta
 */
public class Cliente {
    private int clienteID;
    private String CPF;
    private String nome;
    private LocalDate dataNascimento;
    private int pontuacao;

    public Cliente(String nome, LocalDate dataNascimento, int pontuacao, String cpf) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.pontuacao = pontuacao;
        this.CPF = cpf;
    }
    
    public Cliente(int clienteID, String nome, LocalDate dataNascimento, int pontuacao) {
        this.clienteID = clienteID;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.pontuacao = pontuacao;
    }

    public Cliente() {
    }

    
    public int getClienteID() {
        return clienteID;
    }

    public void setClienteID(int ClienteID) {
        this.clienteID = ClienteID;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }
    
    @Override
    public String toString() {
        return this.CPF + " - " + this.nome;
    }
}
