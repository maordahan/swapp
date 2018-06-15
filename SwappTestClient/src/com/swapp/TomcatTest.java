package com.swapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class TomcatTest {
	
    public static void main( String [] args ) throws Exception {
        
    }
    
    public static String test(String wsName, String input ) {
    	String res = "";
    	try {
		URL url = new URL("http://localhost:8080/" + wsName);
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);

		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
		out.write(input);
		out.flush();
		out.close();
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		String response;
		while ((response = in.readLine()) != null) {
			res += response;
		}
		in.close(); 
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    	return res;
    	
    }
}
