package com.swapp.ws;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import serverApi.Api.updateNotMyItemsInput;
import serverApi.sqlite.SqliteDB;

public class UpdateNotMyItems extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
		WsUtills.defultGet(req, resp);
    }
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String jsonString = WsUtills.returnJsonFromRequest(req);
		updateNotMyItemsInput input = WsUtills.gson.fromJson(jsonString, updateNotMyItemsInput.class);		
		SqliteDB.instance.updateNotMyItems(input);
		WsUtills.sendJsonResponse(resp, Boolean.TRUE.toString());
	}

} 


