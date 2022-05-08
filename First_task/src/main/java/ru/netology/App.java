package ru.netology;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Hello world!
 */
public class App {
	private static final String REMOTE_SERVICE_URL = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";

	private static final ObjectMapper mapper = new ObjectMapper();

	public static void main(String[] args) throws IOException {

		CloseableHttpClient httpClient = HttpClientBuilder.create()
				.setDefaultRequestConfig(RequestConfig.custom()
						.setConnectionRequestTimeout(5_000)
						.setSocketTimeout(30_000)
						.setRedirectsEnabled(false)
						.build())
				.build();

		HttpGet request = new HttpGet(REMOTE_SERVICE_URL);
		request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());

		CloseableHttpResponse response = httpClient.execute(request);
		Arrays.stream(response.getAllHeaders()).forEach(System.out::println);

		List<Cats> cats = mapper.readValue(response.getEntity().getContent(), new TypeReference<List<Cats>>() {
		});

		cats.stream()
				.filter(value -> (value.getUpvotes() != null && value.getUpvotes() > 0))
				.forEach(System.out::println);
	}
}
