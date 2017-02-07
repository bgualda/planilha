package com.planilha.core;

import com.planilha.core.vo.AtividadeListVO;
import com.planilha.core.vo.AtividadeVO;
import static com.planilha.Constantes.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author Higor Edurado Borges Galdino
 * @since 20/06/2016 Classe responsavel por acessar o ACCWEB e extrair as horas
 */
public class AccwebUtils {

    DefaultHttpClient client = new DefaultHttpClient();

    public AccwebUtils(String user, String pass) throws IOException {
        autenticarUsuario(user, pass);
    }

    public AccwebUtils() {

    }

    public void autenticarUsuario(String user, String pass) throws IOException {
        System.out.print("Autenticando usuario: "+user);
        HttpHost httpHost = new HttpHost(HOSTNAME, PORT, PROTOCOL);
        client.getParams().setParameter(ClientPNames.DEFAULT_HOST, httpHost);

        HttpGet securedResource = new HttpGet(URL_HOME);
        HttpResponse httpResponse = client.execute(securedResource);
        HttpEntity responseEntity = httpResponse.getEntity();
        EntityUtils.consume(responseEntity);

        HttpPost authpost = new HttpPost(URL_LOGIN);
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair(OS_USERNAME, user));
        nameValuePairs.add(new BasicNameValuePair(OS_PASSWORD, pass));
        nameValuePairs.add(new BasicNameValuePair(OS_COOKIE, "true"));
        nameValuePairs.add(new BasicNameValuePair(OS_DESTINATION, "/loginAccProjetos.jsp"));

        authpost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        httpResponse = client.execute(authpost);
        responseEntity = httpResponse.getEntity();
        EntityUtils.consume(responseEntity);
        httpResponse = client.execute(securedResource);
        responseEntity = httpResponse.getEntity();
        EntityUtils.consume(responseEntity);
        System.out.print("...sucesso.\n");
    }

    public String getHtml(String url) throws IOException {
        String html = "";
        HttpGet resource = new HttpGet(url);
        HttpResponse response = client.execute(resource);
        HttpEntity entity = response.getEntity();
        html = EntityUtils.toString(entity);
        int codigoStatus = response.getStatusLine().getStatusCode();
        EntityUtils.consume(entity);
        return html;
    }

    public String getURL(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String url = URL_HORAS;
        url = url.concat(format.format(date));
        return url;
    }

    public AtividadeListVO getAtividadeListVO(String html) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        AtividadeListVO atividadeListVO = new AtividadeListVO();
        List<AtividadeVO> atividades = new ArrayList<AtividadeVO>();
        Date dataAtividade = new Date();
        Double totalHoras = 0d;
        Document doc = Jsoup.parse(html);
        Elements elements = doc.getElementsByClass("texto_ativ");
        for (int i = 0; i < elements.size(); i += 12) {
            AtividadeVO atividade = new AtividadeVO();
            atividade.setRecurso(elements.get(i).text());
            atividade.setContratante(elements.get(i + 1).text());
            atividade.setProjeto(elements.get(i + 2).text());
            atividade.setContrato(elements.get(i + 3).text());
            atividade.setChave(elements.get(i + 4).text());
            atividade.setAtividade(elements.get(i + 5).text());
            atividade.setEtapa(elements.get(i + 7).text());
            atividade.setFase(elements.get(i + 8).text());
            String data = elements.get(i + 9).text().replace(" ", "");
            String hora = elements.get(i + 10).text().replace(" ", "");
            atividade.setData(formatter.parse(data.concat(" ").concat(hora)));
            atividade.setInicio(formatter.parse(data.concat(" ").concat(hora)));
            atividade.setHoras(Double.parseDouble(elements.get(i + 11).text().replace(" ", "").replace(",", ".")));

            dataAtividade = formatter.parse(data.concat(" 00:00"));
            totalHoras += atividade.getHoras();
            atividades.add(atividade);
        }
        Collections.sort(atividades);

        atividadeListVO.setAtividades(atividades);
        atividadeListVO.setData(dataAtividade);
        if(totalHoras != 0)
            System.out.println("Atividades encontradas. Data:"+dataAtividade+" Total Horas: "+totalHoras+" qtdAtividades: "+atividades.size());
        return atividadeListVO;
    }

}
