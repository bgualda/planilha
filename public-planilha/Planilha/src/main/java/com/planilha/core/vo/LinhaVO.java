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
public class LinhaVO {

    private int indice;
    private Date inicio;
    private Date saida;
    private Date inicioAlmoco;
    private Date saidaAlmoco;
    private Date inicioExtra;
    private Date saidaExtra;

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getSaida() {
        return saida;
    }

    public void setSaida(Date saida) {
        this.saida = saida;
    }

    public Date getInicioAlmoco() {
        return inicioAlmoco;
    }

    public void setInicioAlmoco(Date inicioAlmoco) {
        this.inicioAlmoco = inicioAlmoco;
    }

    public Date getSaidaAlmoco() {
        return saidaAlmoco;
    }

    public void setSaidaAlmoco(Date saidaAlmoco) {
        this.saidaAlmoco = saidaAlmoco;
    }

    public Date getInicioExtra() {
        return inicioExtra;
    }

    public void setInicioExtra(Date inicioExtra) {
        this.inicioExtra = inicioExtra;
    }

    public Date getSaidaExtra() {
        return saidaExtra;
    }

    public void setSaidaExtra(Date saidaExtra) {
        this.saidaExtra = saidaExtra;
    }

    @Override
    public String toString() {
        return "LinhaVO{" + "indice=" + indice + ", inicio=" + inicio + ", saida=" + saida + ", inicioAlmoco=" + inicioAlmoco + ", saidaAlmoco=" + saidaAlmoco + ", inicioExtra=" + inicioExtra + ", saidaExtra=" + saidaExtra + '}';
    }
    
    

}
