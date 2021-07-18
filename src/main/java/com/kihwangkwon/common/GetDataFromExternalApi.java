package com.kihwangkwon.common;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

@Service
public class GetDataFromExternalApi {
	
	public static String getDataFromApi(String restApiURL) {
		
		final String USER_AGENT = "Mozila/5.0";
		final String GET_URL = restApiURL;
		String responseData = null;

		// http client 생성
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpPost httpPost = new HttpPost(restApiURL);
		
		
		// get 메서드와 URL 설정
		HttpGet httpGet = new HttpGet(GET_URL);

		// agent 정보 설정
		httpGet.addHeader("User-Agent", USER_AGENT);
		httpGet.addHeader("Content-type", "application/json");
		
		// get 요청
		CloseableHttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(httpGet);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//응답이 정상적인 경우에만 진행
		if(httpResponse.getStatusLine().getStatusCode()==200) {
			try {
				responseData = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return responseData;
	}
	
	public CloseableHttpResponse postRequest(String url) {
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpPost httpPost = new HttpPost(url);
		
		httpPost.setHeaders(requestHeadMaker());

		CloseableHttpResponse httpResponse = null;
		
		try {
			httpResponse = httpClient.execute(httpPost);
		}catch (Exception e) {
			
		}
			
		return httpResponse;
	}
	
	private Header[] requestHeadMaker() {
		//final String riotApiKey = constructorProperties.getApiKey();
		Header[] header = {
				new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.182 Safari/537.36")
				//new BasicHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.182 Safari/537.36")
			, new BasicHeader("Accpet-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7,ja;q=0.6")
			, new BasicHeader("Accept-Charset", "application/x-www-form-urlencoded; charset=UTF-8")
			, new BasicHeader("Origin", "https://developer.riotgames.com")
			, //new BasicHeader("X-Riot-Token", riotApiKey)
		};
		return header;
	}
	
	public String urlMaker() {
		return null;
	}
}
