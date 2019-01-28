package org.elsys.netprog.rest;

public class JSONBuilder {
	int length;
	String server_hash;
	String client_hash;
	String client_bytes;
	
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public String getServer_hash() {
		return server_hash;
	}
	public void setServer_hash(String server_hash) {
		this.server_hash = server_hash;
	}
	public String getClient_hash() {
		return client_hash;
	}
	public void setClient_hash(String client_hash) {
		this.client_hash = client_hash;
	}
	public String getClient_bytes() {
		return client_bytes;
	}
	public void setClient_bytes(String client_bytes) {
		this.client_bytes = client_bytes;
	}
	
	@Override
	public String toString() {
		return "{" + "\"length\":" + "\"" + length + "\"" + ", \"server_hash\":" + "\"" + server_hash + "\"" + ", \"client_hash\":" + "\"" + client_hash + "\"" + ", \"client_bytes\":" + "\"" + client_bytes + "\"" +  "}";
	}
	
}
