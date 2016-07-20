/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planilha.core;

import com.planilha.Constantes;
import com.planilha.core.vo.AtividadeListVO;
import com.planilha.core.vo.AtividadeVO;
import com.planilha.core.vo.LinhaVO;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Higor Eduardo Borges Galdino
 */
public class PlanilhaUtils {

    private XSSFWorkbook wb;
    private AccwebUtils accwebUtils;
    private String path;
    private Double salarioBruto;
    private BigDecimal valorHoraExtra;
    private Double totalExtra;

    public PlanilhaUtils() {

    }

    public PlanilhaUtils(String path, String user, String pass, Double salarioBruto) {
        try {
            this.path = path;
            this.salarioBruto = salarioBruto;
            this.totalExtra = 0d;
            setWb(path);
            accwebUtils = new AccwebUtils(user, pass);
            valorHoraExtra = BigDecimal.ZERO;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public XSSFWorkbook getWb() {
        return wb;
    }

    public void setWb(String path) {
        try {
            System.out.print("Abrindo planilha");
            InputStream ExcelFileToRead = new FileInputStream(path);
            wb = new XSSFWorkbook(ExcelFileToRead);
            System.out.print("...\n");
        } catch (Exception ex) {
            System.out.println(ex.toString());
            ex.printStackTrace();
        }
    }

    public Map<Integer, Date> getDatasPlanilha() {
        Map<Integer, Date> datas = new HashMap<Integer, Date>();
        XSSFSheet sheet = wb.getSheetAt(0);
        for (int i = 0; (null != sheet.getRow(i) && null != sheet.getRow(i).getCell(0)); i++) {
            if (sheet.getRow(i).getCell(0).getCellType() == 0) {
                Date data = sheet.getRow(i).getCell(0).getDateCellValue();
                datas.put(i, data);
            }
        }
        return datas;
    }

    public List<LinhaVO> getLinhaAccWeb() throws ParseException, IOException {
        List<LinhaVO> linhas = new ArrayList<LinhaVO>();
        Map<Integer, Date> datas = getDatasPlanilha();
        for (Integer key : datas.keySet()) {
            AtividadeListVO atividadeListVO = accwebUtils.getAtividadeListVO(accwebUtils.getHtml(accwebUtils.getURL(datas.get(key))));
            LinhaVO linha = transformToLinhaVO(atividadeListVO, key);

            if (linha != null) {
                linhas.add(linha);
                if (salarioBruto > 0) {
                    calcularHorasExtras(atividadeListVO.getTotalHoras());
                }
            }
        }
        return linhas;
    }

    public LinhaVO transformToLinhaVO(AtividadeListVO atividadeList, int indice) {
        if (atividadeList != null && atividadeList.getAtividades().size() > 0) {
            LinhaVO linha = new LinhaVO();
            Collections.sort(atividadeList.getAtividades());
            linha.setIndice(indice);

            int i = 0;
            Double totalHoras = 0d;
            Date aux = new Date();

            for (AtividadeVO atividade : atividadeList.getAtividades()) {
                totalHoras += atividade.getHoras();
                if (i == 0) {
                    linha.setInicio(atividade.getInicio());
                    aux = adicionarHorasDate(atividade.getInicio(), atividade.getHoras());
                } else {
                    if (aux.compareTo(atividade.getInicio()) != 0) {
                        linha.setInicioAlmoco(aux);
                        linha.setSaidaAlmoco(atividade.getInicio());
                        aux = adicionarHorasDate(atividade.getInicio(), atividade.getHoras());
                        linha.setSaida(aux);
                    } else {
                        aux = adicionarHorasDate(atividade.getInicio(), atividade.getHoras());
                        linha.setSaidaExtra(aux);

                    }
                    if (totalHoras == Constantes.CARGA_HORARIA) {
                        linha.setSaida(aux);
                    } else {
                        Double horaSaida = totalHoras - Constantes.CARGA_HORARIA;
                        Date saida = subtrairHorasDate(aux, horaSaida);
                        linha.setSaida(saida);
                        linha.setInicioExtra(saida);
                    }
                }
                i++;
            }
            if (totalHoras == Constantes.CARGA_HORARIA) {
                linha.setInicioExtra(null);
                linha.setSaidaExtra(null);
            }

            linha.setInicio(atividadeList.getAtividades().get(0).getInicio());
            return linha;
        } else {
            return null;
        }
    }

    public Date adicionarHorasDate(Date data, Double horas) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.add(Calendar.HOUR_OF_DAY, horas.intValue());
        if ((horas - horas.intValue()) == 0.5d) {
            cal.add(Calendar.MINUTE, 30);
        }

        return cal.getTime();
    }

    public Date subtrairHorasDate(Date data, Double horas) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.add(Calendar.HOUR_OF_DAY, -horas.intValue());
        if ((horas - horas.intValue()) == 0.5d) {
            cal.add(Calendar.MINUTE, -30);
        }

        return cal.getTime();
    }

    public Date adicionarMargemHora(Date hora) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(hora);
        cal.add(Calendar.MINUTE, aleatoriar());
        return cal.getTime();
    }

    public int aleatoriar() {
        Random random = new Random();
        return random.nextInt((Constantes.MARGEM_ERRO_TOP - Constantes.MARGEM_ERRO_DOWN) + 1) + Constantes.MARGEM_ERRO_DOWN;
    }

    public void preencherPlanilha() throws ParseException, IOException {
        XSSFSheet sheet = wb.getSheetAt(0);
        List<LinhaVO> linhas = getLinhaAccWeb();

        for (LinhaVO linha : linhas) {
            if (null != linha.getInicio()) {
                sheet.getRow(linha.getIndice()).getCell(1).setCellValue(adicionarMargemHora(linha.getInicio()));
            }
            if (null != linha.getInicioAlmoco()) {
                sheet.getRow(linha.getIndice()).getCell(2).setCellValue(adicionarMargemHora(linha.getInicioAlmoco()));
            }
            if (null != linha.getSaidaAlmoco()) {
                sheet.getRow(linha.getIndice()).getCell(3).setCellValue(adicionarMargemHora(linha.getSaidaAlmoco()));
            }
            if (null != linha.getSaida()) {
                sheet.getRow(linha.getIndice()).getCell(4).setCellValue(adicionarMargemHora(linha.getSaida()));
            }
            if (null != linha.getInicioExtra()) {
                sheet.getRow(linha.getIndice()).getCell(4).setCellValue(linha.getInicioExtra());
                sheet.getRow(linha.getIndice()).getCell(5).setCellValue(linha.getInicioExtra());
            }
            if (null != linha.getSaidaExtra()) {
                sheet.getRow(linha.getIndice()).getCell(6).setCellValue(linha.getSaidaExtra());
            }
        }
        System.out.print("Escrevendo arquivo");
        FileOutputStream fileOut = new FileOutputStream(path);
        wb.write(fileOut);
        fileOut.flush();
        fileOut.close();
        System.out.print("...sucesso.\n");

        if (salarioBruto > 0) {
            System.out.println("Você fez " + totalExtra + " Hora(s) extras este mês.");
            System.out.println("Previsão de valor: R$ " + valorHoraExtra);
        }

    }

    public void calcularHorasExtras(Double totalHoras) {
        Double qtdExtra = totalHoras - Constantes.CARGA_HORARIA;
        if (qtdExtra > 0) {
            totalExtra = totalExtra + qtdExtra;
            if (qtdExtra <= 2) {
                Double d = ((salarioBruto / 200) * Constantes.FATOR_HR_EXTRA_75) * qtdExtra;
                valorHoraExtra = valorHoraExtra.add(new BigDecimal(d));
            } else {
                Double d = ((salarioBruto / 200) * Constantes.FATOR_HR_EXTRA_75) * 2;
                Double e = ((salarioBruto / 200) * Constantes.FATOR_HR_EXTRA_100) * (qtdExtra - 2);
                valorHoraExtra = valorHoraExtra.add(new BigDecimal(d));
                valorHoraExtra = valorHoraExtra.add(new BigDecimal(e));
            }
        }
    }

}
