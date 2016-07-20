/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planilha;

import com.planilha.core.PlanilhaUtils;

/**
 * @author Higor Eduardo Borges Galdino
 * @since 20-06-2016 Planilha V2.0
 *
 */
public class Planilha {

    public static void main(String[] args) {
        try {
              String path = args[0];
              String user = args[1];
              String pass = args[2];
              Double salarioBruto = 0d;
              if(args.length > 3){
                salarioBruto = new Double(args[3]);
              }
                
            PlanilhaUtils planilhaUtils = new PlanilhaUtils(path, user, pass, salarioBruto);
            planilhaUtils.preencherPlanilha();
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
