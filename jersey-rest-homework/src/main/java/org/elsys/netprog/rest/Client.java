package org.elsys.netprog.rest;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class Client {
	static int length;
	static String hash,server_hash;
	static Random random;
	static byte[] guess;
	
	public static String getMd5(byte[] input)
    { 
        try { 
   
            MessageDigest md = MessageDigest.getInstance("MD5"); 
   
            byte[] messageDigest = md.digest(input); 
   
            BigInteger no = new BigInteger(1, messageDigest); 
  
            String hashtext = no.toString(16); 
            while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
            return hashtext; 
        }  
  
        catch (NoSuchAlgorithmException e) { 
            throw new RuntimeException(e); 
        } 
    } 
	
	static int postHTTP() throws ClientProtocolException, IOException, JSONException {
		String url = "http://localhost:8080/jersey-rest-homework/rest/sendInfo";
		URL object=new URL(url);

		HttpURLConnection con = (HttpURLConnection) object.openConnection();
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");
		con.setRequestMethod("POST");

		JSONBuilder json = new JSONBuilder();
		json.setClient_bytes(Base64.getEncoder().encodeToString(guess));
		json.setClient_hash(hash);
		OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
		System.out.println("Guess_client: " + new String(guess));
		wr.write(json.toString());
		wr.flush();

		StringBuilder sb = new StringBuilder();  
		int HttpResult = con.getResponseCode(); 
		if (HttpResult == HttpURLConnection.HTTP_OK) {
		    BufferedReader br = new BufferedReader(
		            new InputStreamReader(con.getInputStream(), "utf-8"));
		    String line = null;  
		    while ((line = br.readLine()) != null) {  
		        sb.append(line + "\n");  
		    }
		    br.close(); 
		} else {
		    System.out.println(con.getResponseMessage());  
		}  
		
		return  HttpResult;
	}
	
	static JSONObject getHTTP() throws ClientProtocolException, IOException, ParseException, JSONException {
		String url = "http://localhost:8080/jersey-rest-homework/rest/serverInfo";
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		JSONObject json = new JSONObject(response.toString());
		return json;
	}
	
	static void connect() throws ClientProtocolException, IOException, JSONException {
		JSONObject server_response = getHTTP();
		length = Integer.parseInt(server_response.get("length").toString());
		server_hash = (String) server_response.get("server_hash");
		
		int i=0;
		long t= System.currentTimeMillis();
		
		do {
			guess = new byte[length];
			random = new Random();
			random.nextBytes(guess);
			hash = getMd5(guess);
			System.out.println("Hash" + i + ": " + hash);
			i++;
		}while(!server_hash.equals(hash));
		
		long end = System.currentTimeMillis();
		System.out.println("Seconds: " + ((end-t)/1000));
		
		int status = postHTTP();
		if(status == 200) {
			System.out.println("OK");
		}
		else if(status == 406) {
			System.out.println("ERROR");
			System.out.println("ClientBytes: " + guess.toString());
			System.out.println("ClientHash: " + getMd5(guess));
		}
		
	}
	
	public static void main(String[] args) throws ClientProtocolException, IOException, JSONException {
		while(true) {
			connect();
			System.out.println("Client's new GET REQUEST");
		}
	}
}
