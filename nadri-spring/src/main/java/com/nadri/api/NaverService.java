package com.nadri.api;

import com.github.scribejava.core.builder.api.DefaultApi20;

//scribe library용 구현체
public class NaverService extends DefaultApi20{
	protected NaverService() {	
	}
	
	private static class InstanceHolder{
		private static final NaverService INSTANCE = new NaverService();
	}
	
	public static NaverService instance() {
		return InstanceHolder.INSTANCE;
	}
	
	@Override
	public String getAccessTokenEndpoint() {
		return "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code";
	}
	
	@Override
	protected String getAuthorizationBaseUrl() {
		return "https://nid.naver.com/oauth2.0/authorize";
	}
	
}
