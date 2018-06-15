package com.swapp.ws;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import serverApi.sqlite.SqliteDB;

public class DeleteItem extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
		WsUtills.defultGet(req, resp);
    }
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String jsonString = WsUtills.returnJsonFromRequest(req);
		String itemId = WsUtills.gson.fromJson(jsonString, String.class);		
		Boolean res  =  SqliteDB.instance.deleteItem(itemId);
		WsUtills.sendJsonResponse(resp,  res.toString()); 
	}
}


