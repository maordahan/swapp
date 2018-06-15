package com.swapp.ws;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import item.Item;
import serverApi.sqlite.SqliteDB;

public class AddItem extends HttpServlet {
	
	

	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
		WsUtills.defultGet(req, resp);
    }
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String jsonString = WsUtills.returnJsonFromRequest(req);
		Item item = WsUtills.gson.fromJson(jsonString, Item.class);
		item.setImage(saveImageOnDisk(item));
		SqliteDB.instance.addItem(item);
		WsUtills.sendJsonResponse(resp, Boolean.TRUE.toString()); //TODO here and all WS return the real result.
	}
	
	/**
	 * return the path where the image will be stored
	 * @throws IOException 
	 */
	private static String saveImageOnDisk(Item item) throws IOException {
		byte[] image = Base64.getDecoder().decode(item.getImage());
		String fileName  =  item.getItemId() + ".png";
		Path p = Paths.get(ImagesServlet.imagesLocation, fileName);
		Files.write(p, image);
		return fileName;
		
	}
}
