/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 * Modelo estrutural de Filme.
 * Gera objetos modelos para comunicação com BD ou utilização na GUI.
 * @author Evandro Alessi
 * @author Eric Ueta
 */
public class Filme {
    private int filmeID;
    private String titulo;
    private String diretor;
    private String genero;
    private String idioma;
    private int duracao;

    public Filme(String titulo, String diretor, String genero, String idioma, int duracao) {
        this.titulo = titulo;
        this.diretor = diretor;
        this.genero = genero;
        this.idioma = idioma;
        this.duracao = duracao;
    }

    public Filme(String titulo, String genero, String idioma, int duracao) {
        this.titulo = titulo;
        this.genero = genero;
        this.idioma = idioma;
        this.duracao = duracao;
    }
    
    public Filme(int filmeID, String titulo, String diretor, String genero, String idioma, int duracao) {
        this.filmeID = filmeID;
        this.titulo = titulo;
        this.diretor = diretor;
        this.genero = genero;
        this.idioma = idioma;
        this.duracao = duracao;
    }

    public Filme() {
    }

    
    public int getFilmeID() {
        return filmeID;
    }

    public void setFilmeID(int filmeID) {
        this.filmeID = filmeID;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDiretor() {
        return diretor;
    }

    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    @Override
    public String toString() {
        return titulo;
    }
    
    
}
