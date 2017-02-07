/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planilha.core.vo;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Higor Eduardo Borges Galdino
 */
public class AtividadeListVO {
    
    private Date data;
    private Double totalHoras;
    private List<AtividadeVO> atividades;

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Double getTotalHoras() {
        this.totalHoras = 0d;
        for(AtividadeVO ativ : getAtividades()){
            totalHoras += ativ.getHoras();
        }                
        return totalHoras;
    }

    public List<AtividadeVO> getAtividades() {
        return atividades;
    }

    public void setAtividades(List<AtividadeVO> atividades) {
        this.atividades = atividades;
    }

    @Override
    public String toString() {
        return "AtividadeListVO{" + "data=" + data + ", totalHoras=" + totalHoras + ", atividades=" + atividades + '}';
    }
    
}
