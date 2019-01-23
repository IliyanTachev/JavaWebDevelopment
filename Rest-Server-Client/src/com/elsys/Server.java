package com.elsys;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger; 
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
import java.util.stream.Collectors; 

/**
 * Servlet implementation class Server
 */
@WebServlet("/Server")
public class Server extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static int length = 1;
	private byte[] input;
	private Random random;
	private String hash;
	
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
	
	public void randomize() {
		random = new Random();
    	input = new byte[length];
    	random.nextBytes(input);
    	hash = getMd5(input);
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
    public Server() {
    	randomize();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		try {
			json.put("length", length);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		try {
			json.put("hash", hash);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.println("{");
        out.println("\"hash\"" + ":" + "\"" + hash +"\",");
        out.println("\"length\"" + ":" + "\"" + length +"\",");
        out.println("}");
        out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		byte[] requested_input = Base64.getDecoder().decode(request.getParameter("input"));
//		String hashClient = request.getParameter("client_hash");
//		System.out.println(requested_input);
//		System.out.println(hashClient);
//		
//		String server_hash = getMd5(requested_input);
//		
//		if(server_hash.equals(hashClient) && Arrays.equals(requested_input, input)){
			//response.setStatus(200);
	//	}
	//	else {
	//		response.setStatus(406);
	//	}
			StringBuilder sb = new StringBuilder();
		    BufferedReader reader = request.getReader();
		    try {
		        String line;
		        while ((line = reader.readLine()) != null) {
		            sb.append(line).append('\n');
		        }
		    } finally {
		        reader.close();
		    }
		    
		    try {
				JSONObject json = new JSONObject(sb.toString());
				byte[] requested_input = Base64.getDecoder().decode(json.getString("input"));
		    	String hashClient = json.getString("client_hash");
		    	
		    	String server_hash = getMd5(requested_input);
					
				if(server_hash.equals(hashClient) && Arrays.equals(requested_input, input)){
					System.out.println("Server input:" + input.toString());
					System.out.println("Client input:" + requested_input);
					System.out.println("Server hash:" + server_hash);
					System.out.println("Client hash:" + hashClient);
					response.setStatus(200);
					randomize();
				}
				else {
					response.setStatus(406);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		    
		    
//		    try {
//		    	byte[] requested_input = Base64.getDecoder().decode(json.getString("input"));
//		    	String hashClient = json.getString("client_hash");
//		    	
//		    	String server_hash = getMd5(requested_input);
//					
//				if(server_hash.equals(hashClient) && Arrays.equals(requested_input, input)){
//					response.setStatus(200);
//				}
//				else {
//					response.setStatus(406);
//				}
//		    } catch (JSONException e) {
//				e.printStackTrace();
//			}
	}

}
