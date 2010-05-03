package com.infrno.multiplayer;

import com.wowza.wms.application.*;
import com.wowza.wms.amf.*;
import com.wowza.wms.client.*;
import com.wowza.wms.module.*;
import com.wowza.wms.request.*;

public class Application extends ModuleBase 
{
	public IApplicationInstance 	app_instance;
	public ChatManager				chatManager;
	public StreamManager			streamManager;
	public UserManager				userManager;
	
	public void onAppStart(IApplicationInstance appInstance) 
	{
		String fullname = appInstance.getApplication().getName() + "/"+ appInstance.getName();
		log("Infrno version 0.8.3 onAppStart: " + fullname);
		
		app_instance = appInstance;
		chatManager = new ChatManager(this);
		streamManager = new StreamManager(this);
		userManager = new UserManager(this);
	}

	public void onAppStop(IApplicationInstance appInstance) 
	{
		String fullname = appInstance.getApplication().getName() + "/"+ appInstance.getName();
		log("onAppStop: " + fullname);
		
		chatManager = null;
		streamManager = null;
		userManager = null;
		app_instance = null;
	}

	public void onConnect(IClient client, RequestFunction function,AMFDataList params) 
	{
		userManager.userConnect(client, params);
	}

	public void onDisconnect(IClient client) 
	{
		userManager.userDisconnect(client);
	}

	public void log(String msgIn)
	{
		getLogger().info(msgIn);
	}
	
	public void error(String msgIn)
	{
		getLogger().error(msgIn);
	}
	
	/*
	 * Client Methods
	 */
	
	public void chatToServer(IClient client, RequestFunction function,AMFDataList params) 
	{
		chatManager.chatToServer(client, params);
	}
	
	public void getUserStats(IClient client, RequestFunction function,AMFDataList params)
	{
		userManager.getUserStats();
	}
	
	public void reportUserStats(IClient client, RequestFunction function,AMFDataList params) 
	{
		userManager.reportUserStats(client, params);
	}

	public void updateUserInfo(IClient client, RequestFunction function,AMFDataList params) 
	{
		userManager.updateUserInfo(Integer.toString(client.getClientId()), params.get(PARAM1));
	}
	
}