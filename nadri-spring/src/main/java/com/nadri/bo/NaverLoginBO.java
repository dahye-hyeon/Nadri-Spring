package com.nadri.bo;

import java.math.BigInteger;
import java.security.SecureRandom;

import javax.servlet.http.HttpSession;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.nadri.api.NaverService;

public class NaverLoginBO {
	private final static String CLIENT_ID = "SR3YR1kOQAMN7qDKjv8s"; //Client ID
    private final static String CLIENT_SECRET = "z67QvBqbov"; //Cliend Secret
    private final static String REDIRECT_URI = "http://localhost:8088/main/index"; //로그인 성공시 url
    
    public String generateState() {
    	SecureRandom random = new SecureRandom();
    	return new BigInteger(130, random).toString(32);
    }
    
    /* 네아로 인증 URL 생성 */

    public String getAuthorizationUrl(HttpSession session) {
    	String state = generateState();
    	session.setAttribute("state", state);
    	
    	/* scribe에서 제공하는 인증 url 생성 기능을 이용해 네아로 인증 url 생성 */
    	OAuth20Service oauthService = new ServiceBuilder()
    			.apiKey(CLIENT_ID)
    			.apiSecret(CLIENT_SECRET)
    			.callback(REDIRECT_URI)
    			.state(state)
    			.build(NaverService.instance());
    	return oauthService.getAuthorizationUrl();
    }
}
