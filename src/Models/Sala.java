/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

/**
 * Modelo estrutural de Sala.
 * Gera objetos modelos para comunicação com BD ou utilização na GUI.
 * @author Evandro Alessi
 * @author Eric Ueta
 * @see Sala
 */
public class Sala {
    private int salaID;
    private int numero;
    private int capacidade;

    public Sala(int salaID, int numero, int capacidade) {
        this.salaID = salaID;
        this.numero = numero;
        this.capacidade = capacidade;
    }

    public Sala() {
    }

    public int getSalaID() {
        return salaID;
    }

    public void setSalaID(int salaID) {
        this.salaID = salaID;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    @Override
    public String toString() {
        return String.valueOf(numero);
    }
}
