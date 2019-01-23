package com.elsys;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import org.apache.http.HttpResponse;

public class Client {
	static int length;
	static String hash,server_hash;
	static Random random;
	static byte[] guess;
	
	public static String getMd5(byte[] input)
    { 
        try { 
  
            // Static getInstance method is called with hashing MD5 
            MessageDigest md = MessageDigest.getInstance("MD5"); 
  
            // digest() method is called to calculate message digest 
            //  of an input digest() return array of byte 
            byte[] messageDigest = md.digest(input); 
  
            // Convert byte array into signum representation 
            BigInteger no = new BigInteger(1, messageDigest); 
  
            // Convert message digest into hex value 
            String hashtext = no.toString(16); 
            while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
            return hashtext; 
        }  
  
        // For specifying wrong message digest algorithms 
        catch (NoSuchAlgorithmException e) { 
            throw new RuntimeException(e); 
        } 
    } 
	
	static int postHTTP() throws ClientProtocolException, IOException, JSONException {
		JSONObject data = new JSONObject();
		data.put("input", Base64.getEncoder().encodeToString(guess));
		data.put("client_hash", hash);
		
		String       postUrl       = "http://localhost:8080/Rest-Server-Client/Server";// put in your url
		HttpClient   httpClient    = HttpClientBuilder.create().build();
		HttpPost     post          = new HttpPost(postUrl);
		post.setHeader("Content-type", "application/json");
		StringEntity postingString = new StringEntity(data.toString());//gson.tojson() converts your pojo to json
		post.setEntity(postingString);
		HttpResponse  response = httpClient.execute(post);
		int status = response.getStatusLine().getStatusCode();
		return status;
	}
	
	static JSONObject getHTTP() throws ClientProtocolException, IOException, JSONException {
		String       getUrl       = "http://localhost:8080/Rest-Server-Client/Server";// put in your url
		HttpClient   httpClient    = HttpClientBuilder.create().build();
		HttpGet      get          = new HttpGet(getUrl);
		get.setHeader("Content-type", "application/json");
		HttpResponse  response = httpClient.execute(get);
		JSONObject json = new JSONObject(EntityUtils.toString(response.getEntity()));

		return json;
	}
	
	static void connect() throws JSONException, ClientProtocolException, IOException {
//		HttpResponse<JsonNode> response = Unirest.get("http://localhost:8080/Rest-Server-Client/Server").asJson();
//		System.out.println(response.getBody().getObject().getString("length"));
//		System.out.println(response.getBody().getObject().getString("hash"));
//		length = Integer.parseInt(response.getBody().getObject().getString("length"));
//		server_hash = response.getBody().getObject().getString("hash");
		JSONObject server_response = getHTTP();
		length = Integer.parseInt(server_response.get("length").toString());
		server_hash = (String) server_response.get("hash");
		
		
		
		int i=0;
		do {
			guess = new byte[length];
			random = new Random();
			random.nextBytes(guess);
			hash = getMd5(guess);
			//System.out.println("hash" + i + ":" + " " + hash);
			i++;
		}while(!server_hash.equals(hash));
		
		int status = postHTTP();
		if(status == 200) {
			System.out.println("OK");
		}
		else if(status == 406) {
			System.out.println("ERROR");
		}
		
//		JSONObject data = new JSONObject();
//		data.put("input", Base64.getEncoder().encodeToString(guess));
//		data.put("client_hash", hash);
//		
//		HttpResponse<JsonNode> responseStatus = Unirest.post("http://localhost:8080/Rest-Server-Client/Server").header("accept", "application/json").header("Content-Type", "application/json")
//				.body(data).asJson();
//		System.out.println(responseStatus.getBody());
	}
	public static void main(String[] args) throws JSONException, ClientProtocolException, IOException {
		int i=0;
		while(i<2) {
			connect();
			i++;
		}
	}
}
