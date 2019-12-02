/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

/**
 * Modelo estrutural de Sessao.
 * Gera objetos modelos para comunicação com BD ou utilização na GUI.
 * @author Evandro Alessi
 * @author Eric Ueta
 * @see Sessao
 */
public class Sessao {
    private int sessaoID;
    private int filmeID;
    private int salaID;
    private int ingressos;
    private LocalDateTime data;
    private double valorIngresso;
    private Filme filme;
    private Sala sala;

    public Sessao(int sessaoID, int filmeID, int salaID, int ingressos, LocalDateTime data, double valorIngresso, Filme filme, Sala sala) {
        this.sessaoID = sessaoID;
        this.filmeID = filmeID;
        this.salaID = salaID;
        this.ingressos = ingressos;
        this.data = data;
        this.valorIngresso = valorIngresso;
        this.filme = filme;
        this.sala = sala;
    }

    public Sessao() {
    }

    public int getSessaoID() {
        return sessaoID;
    }

    public void setSessaoID(int sessaoID) {
        this.sessaoID = sessaoID;
    }

    public int getFilmeID() {
        return filmeID;
    }

    public void setFilmeID(int filmeID) {
        this.filmeID = filmeID;
    }

    public int getSalaID() {
        return salaID;
    }

    public void setSalaID(int salaID) {
        this.salaID = salaID;
    }

    public int getIngressos() {
        return ingressos;
    }

    public void setIngressos(int ingressos) {
        this.ingressos = ingressos;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public double getValorIngresso() {
        return valorIngresso;
    }

    public void setValorIngresso(double valorIngresso) {
        this.valorIngresso = valorIngresso;
    }

    public Filme getFilme() {
        return filme;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }
    

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public String getDateF(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dataF = sdf.format(Timestamp.valueOf(getData()));
        
        return dataF;
    }

    @Override
    public String toString() {
        return this.filme.getTitulo();
    }
    
    
}
