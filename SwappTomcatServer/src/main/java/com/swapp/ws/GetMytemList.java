package com.swapp.ws;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import clientItem.MyItem;
import serverApi.sqlite.SqliteDB;

public class GetMytemList extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
		WsUtills.defultGet(req, resp);
    }
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String jsonString = WsUtills.returnJsonFromRequest(req);
		String userID = WsUtills.gson.fromJson(jsonString, String.class);		
		List<MyItem> res  = SqliteDB.instance.getMytemList(userID);
		WsUtills.sendJsonResponse(resp, WsUtills.gson.toJson(res));
	}

}
