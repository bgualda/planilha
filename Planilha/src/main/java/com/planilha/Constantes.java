package com.planilha;

public interface Constantes {

    static final String URL_HOME = "https://lab.accurate.com.br/request/loginAccProjetos.jsp";
    static final String URL_LOGIN = "https://lab.accurate.com.br/request/login.jsp";
    static final String URL_HORAS = "https://lab.accurate.com.br/accweb/ativ_inclusao.asp?pagResumo=true&Data=";
    static final String PATTERN_DATA = "dd/MM/yyyy";
    static final String HOSTNAME = "lab.accurate.com.br";
    static final int PORT = 8080;
    static final String PROTOCOL = "http";

    static final String OS_USERNAME = "os_username";
    static final String OS_PASSWORD = "os_password";
    static final String OS_COOKIE = "os_cookie";
    static final String OS_DESTINATION = "os_destination";
    
    static final int CARGA_HORARIA = 8;
    static final int MARGEM_ERRO_TOP = 3;
    static final int MARGEM_ERRO_DOWN = -3;
    
    static final Double FATOR_HR_EXTRA_75 = 1.75;
    static final Double FATOR_HR_EXTRA_100 = 2.0;
}
