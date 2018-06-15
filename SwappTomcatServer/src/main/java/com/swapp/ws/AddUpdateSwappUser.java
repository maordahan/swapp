package com.swapp.ws;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import item.Item;
import serverApi.sqlite.SqliteDB;
import user.SwappUser;

public class AddUpdateSwappUser extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
		WsUtills.defultGet(req, resp);
    }
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String jsonString = WsUtills.returnJsonFromRequest(req);
		SwappUser swappUser = WsUtills.gson.fromJson(jsonString, SwappUser.class);
		SqliteDB.instance.addUpdateSwappUser(swappUser);
		WsUtills.sendJsonResponse(resp, Boolean.TRUE.toString()); 
		//TODO here and all WS return the real result.
		//TODO Like in add item the the image is bytes, save to disk and put the location.
		//TODO Fix the test after doing the previous comment 
	}

}
