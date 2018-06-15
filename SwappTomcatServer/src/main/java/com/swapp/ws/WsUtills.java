package com.swapp.ws;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WsUtills {
	
	public static Gson gson;
	static {
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting(); 
		//How the thread safe work, if we have a lot of request do we have starvation to some request. 
		gson = builder.create();
	}
	
    public static String returnJsonFromRequest(HttpServletRequest req) throws IOException {
    	BufferedReader bufferedReader =  req.getReader();
    	StringBuffer sb = new StringBuffer();
    	
    	
        char[] charBuffer = new char[128];
        int bytesRead;
        while ( (bytesRead = bufferedReader.read(charBuffer)) != -1 ) {
            sb.append(charBuffer, 0, bytesRead);
        }
       
        String jsonString = sb.toString();
        return jsonString;
    }
    
    public static void sendJsonResponse(HttpServletResponse resp, String jsonResponse) throws IOException {
    	ServletOutputStream out = resp.getOutputStream();
    	out.write(jsonResponse.getBytes());
        out.flush();
        out.close();
    	
    }
    
    public static void defultGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ServletOutputStream out = resp.getOutputStream();
        out.write("Hi this is a defult get".getBytes());
        out.flush();
        out.close();
    }

}
