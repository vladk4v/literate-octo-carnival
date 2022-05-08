package ru.netology;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;


/**
 * Hello world!
 *
 */
public class App 
{
    private static final String REMOTE_SERVICE_URL = "https://api.nasa.gov/planetary/apod?api_key=YBLNJXQqh956UIUcmSjtNAaYINoeF13BBhkhoX5b";

    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectionRequestTimeout(5_000)
                        .setSocketTimeout(30_000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();

        //преобразовать json в java
        HttpGet request = new HttpGet(REMOTE_SERVICE_URL);
        request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());

        CloseableHttpResponse response = httpClient.execute(request);
        Arrays.stream(response.getAllHeaders()).forEach(System.out::println);

        Api api = mapper.readValue(new URL(REMOTE_SERVICE_URL), Api.class);

        //проверка
        System.out.println(api);

        //получить картинку
        String url = api.getUrl();
        HttpGet secRequest = new HttpGet(url);
        secRequest.setHeader(HttpHeaders.ACCEPT, ContentType.IMAGE_PNG.getMimeType());

        CloseableHttpResponse secResponse = httpClient.execute(secRequest);

        String[] array = url.split("/");
        String pathTo = "C:\\" + array[array.length - 1];

        //проверка
        System.out.println(pathTo);

        //сохранить тело ответа
        try (FileOutputStream fos = new FileOutputStream(pathTo)) {
            secResponse.getEntity().writeTo(fos);
        } catch (IOException ex) {
            System.out.println("Smth goes wrong");
        }
    }
}
