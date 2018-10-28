package org.elsys.netprog.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


@Path("/game")
public class GameController {
	static private List<Game> games = new ArrayList<Game>();
	
	boolean isValidNumber(String guess){
		try{
			int num = Integer.parseInt(guess); 
		}
		catch(NumberFormatException e){
			return false;
		}
		
		if(guess.charAt(0) == '0') return false;
		if(guess.length() != 4){
			return false;
		}
		
		for(int i=0;i<guess.length() - 1;i++){
			for(int k=i+1;k<guess.length();k++){
				if(guess.charAt(i) == guess.charAt(k))
					return false;
			}
		}
		
		return true;
	}
	
	@POST
	@Path("/startGame")
	//@Produces(value={MediaType.APPLICATION_JSON})
	public Response startGame() throws URISyntaxException{
	
		String uniqueID = UUID.randomUUID().toString();
		Random random = new Random();
		String secret = null;
		
		while(true){
			secret = String.format("%04d", random.nextInt(10000));
			if(isValidNumber(secret)) break;
		}
		
		Game game = new Game(uniqueID);
		game.setSecret(secret);
		games.add(game);
				
		return Response.created(new URI("/games")).status(201).entity(uniqueID).build();
		
		//		return Response.status(201).created(new URI("/games")).status(201).build();
	}
	
	@PUT
	@Path("/guess/{id}/{guess}")
	@Produces(value={MediaType.APPLICATION_JSON})
	public Response guess(@PathParam("id") String gameId, @PathParam("guess") String guess) throws Exception{
		boolean found = false;
		Game currentGame = null;
		
		for(Game game : games){
			if(game.getGameId().equals(gameId)){
				found = true;
				currentGame = game;
			}
		}
		
		if(!found){
			return Response.status(404).build();
		}
		else if(!isValidNumber(guess)){
			return Response.status(400).build();
		}
		
		currentGame.setNumber(guess);
		JSONObject json = new JSONObject();
		
		json.put("gameId", currentGame.getGameId());
		json.put("cowsNumber", currentGame.cowsOrBullsNumber("cows"));
		json.put("bullsNumber", currentGame.cowsOrBullsNumber("bulls"));
		json.put("turnsCount", currentGame.getTurnsCount());
		json.put("success", currentGame.isSuccess());
		
		return Response.status(200).entity(json.toString()).build();
	}
	
	@GET
	@Path("/games")
	@Produces(value={MediaType.APPLICATION_JSON})
	public Response getGames() throws JSONException {
		JSONArray array = new JSONArray();
		JSONObject object = null;
		for(Game game : games){
			object = new JSONObject();
			object.put("gameId", game.getGameId());
			object.put("turnsCount", game.getTurnsCount());
			
			if(!game.isSuccess())
				object.put("secret", "****");
			else{
				object.put("secret", game.getSecret());
			}
			
			object.put("success", game.isSuccess());
			
			array.put(object);
		}
		
		return Response.status(200).entity(array.toString()).build();
	}
}
