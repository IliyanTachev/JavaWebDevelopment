package org.elsys.netprog.rest;

import java.util.Random;

public class Game {
	private String gameId = null;
	private int turnsCount = 0;
	private String secret = null;
	private boolean success = false;
	private String number = null;
	
	public Game(String gameId){
		this.gameId = gameId;
		turnsCount++;
	}
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getGameId() {
		return gameId;
	}
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	public int getTurnsCount() {
		return turnsCount;
	}
	public void setTurnsCount(int turnsCount) {
		this.turnsCount = turnsCount;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
//	public int cowsNumber(){
//		int cows = 0;
//		String guessNum = Integer.toString(number);
//		int i=0;
//		for(char ch : secret.toCharArray()){
//			if(guessNum.indexOf(ch) > -1 && guessNum.charAt(i)!=ch){
//				cows++;
//			}
//			
//			i++;
//		}
//		
//		return cows;
//	}
//	
	public int cowsOrBullsNumber(String type){
		int counter = 0;
		int i=0;
		for(char ch : secret.toCharArray()){
			if(type.equals("cows")){
				if(number.indexOf(ch) > -1 && number.charAt(i)!=ch){
					counter++;
				}
			}
			else if(type.equals("bulls")){
				if(number.indexOf(ch) > -1 && number.charAt(i)==ch){
					counter++;
				}
			}
			i++;
		}
		
		if(type.equals("bulls") && counter == 4) 
			setSuccess(true);
		return counter;
	}
	
}
