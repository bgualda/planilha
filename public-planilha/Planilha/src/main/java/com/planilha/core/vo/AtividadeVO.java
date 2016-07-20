/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planilha.core.vo;

import java.util.Date;

/**
 *
 * @author Higor Eduardo Borges Galdino
 */
public class AtividadeVO implements Comparable<AtividadeVO>{
    
    private String recurso;
    private String contratante;
    private String projeto;
    private String contrato;
    private String chave;
    private String atividade;
    private String etapa;
    private String fase;
    private Date data;
    private Date inicio;
    private Double horas;

    public String getRecurso() {
        return recurso;
    }

    public void setRecurso(String recurso) {
        this.recurso = recurso;
    }

    public String getContratante() {
        return contratante;
    }

    public void setContratante(String contratante) {
        this.contratante = contratante;
    }

    public String getProjeto() {
        return projeto;
    }

    public void setProjeto(String projeto) {
        this.projeto = projeto;
    }

    public String getContrato() {
        return contrato;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getAtividade() {
        return atividade;
    }

    public void setAtividade(String atividade) {
        this.atividade = atividade;
    }

    public String getEtapa() {
        return etapa;
    }

    public void setEtapa(String etapa) {
        this.etapa = etapa;
    }

    public String getFase() {
        return fase;
    }

    public void setFase(String fase) {
        this.fase = fase;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Double getHoras() {
        return horas;
    }

    public void setHoras(Double horas) {
        this.horas = horas;
    }

    @Override
    public String toString() {
        return "RegistroVO{" + "recurso=" + recurso + ", contratante=" + contratante + ", projeto=" + projeto + ", contrato=" + contrato + ", chave=" + chave + ", atividade=" + atividade + ", etapa=" + etapa + ", fase=" + fase + ", data=" + data + ", inicio=" + inicio + ", horas=" + horas + '}';
    }

    @Override
    public int compareTo(AtividadeVO o) {
        return this.getInicio().compareTo(o.getInicio());
    }
    
    
    
    
}
