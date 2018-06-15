package com.swapp;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.ContextConfig;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.valves.rewrite.RewriteValve;

import com.swapp.ws.AddItem;
import com.swapp.ws.AddUpdateSwappUser;
import com.swapp.ws.DeleteItem;
import com.swapp.ws.GetMytemList;
import com.swapp.ws.GetNotMytemList;
import com.swapp.ws.ImagesServlet;
import com.swapp.ws.UpdateNotMyItems;

public class TomcatServer {
	
	private Tomcat tomcat = new Tomcat();
	private Context rootCtx;
	private final File workDir;
	
	public TomcatServer() {
		workDir = new File("/Java/Projects/swapp/SwappTomcatServer/webServer");
		tomcat.setBaseDir(workDir.getAbsolutePath());

		rootCtx = tomcat.addContext("", workDir.getAbsolutePath());
		((StandardContext) rootCtx).setWorkDir(workDir.getAbsolutePath());
		// added in order to add configurations in web.xml file
		rootCtx.addLifecycleListener(new ContextConfig());
		// URL re-writing
		rootCtx.getPipeline().addValve(new RewriteValve());

		tomcat.setPort(8080);
	}
	
	private void addServlets() {
		
		Tomcat.addServlet(rootCtx, "imagesWS", new ImagesServlet());
		rootCtx.addServletMappingDecoded("/images/*", "imagesWS");
		
		Tomcat.addServlet(rootCtx, "addItemWS", new AddItem());
		rootCtx.addServletMappingDecoded("/addItem", "addItemWS");
		
		Tomcat.addServlet(rootCtx, "addUpdateSwappUserWS", new AddUpdateSwappUser());
		rootCtx.addServletMappingDecoded("/addUpdateSwappUser", "addUpdateSwappUserWS");
		
		Tomcat.addServlet(rootCtx, "updateNotMyItemsWS", new UpdateNotMyItems());
		rootCtx.addServletMappingDecoded("/updateNotMyItems", "updateNotMyItemsWS");
		
		Tomcat.addServlet(rootCtx, "deleteItemWS", new DeleteItem() );
		rootCtx.addServletMappingDecoded("/deleteItem", "deleteItemWS");
		
		Tomcat.addServlet(rootCtx, "getMytemListWS", new GetMytemList() );
		rootCtx.addServletMappingDecoded("/getMytemList", "getMytemListWS");
		
		Tomcat.addServlet(rootCtx, "getNotMytemListWS", new GetNotMytemList() );
		rootCtx.addServletMappingDecoded("/getNotMytemList", "getNotMytemListWS");
		
	}

	
	private void start() throws LifecycleException {
		tomcat.start();
	}
	
	private void waitTomcat() {
		tomcat.getServer().await();
	}

	public static void main(String[] args) throws LifecycleException {
		TomcatServer server = new TomcatServer(); 
		server.addServlets();
		server.start();
		server.waitTomcat();

	}
	
	

}
