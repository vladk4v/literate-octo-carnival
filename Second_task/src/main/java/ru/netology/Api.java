package ru.netology;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Api {
	private String copyright;
	private String date;
	private String explanation;
	private String url;

	public Api(
			@JsonProperty("copyright") String copyright,
			@JsonProperty("date") String date,
			@JsonProperty("explanation") String explanation,
			@JsonProperty("url") String url
	) {
		this.copyright = copyright;
		this.date = date;
		this.explanation = explanation;
		this.url = url;
	}

	public String getCopyright() {
		return copyright;
	}

	public String getDate() {
		return date;
	}

	public String getExplanation() {
		return explanation;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public String toString() {
		return "Api{" +
				"copyright='" + copyright + '\'' +
				", date='" + date + '\'' +
				", explanation='" + explanation + '\'' +
				", url='" + url + '\'' +
				'}';
	}
}
