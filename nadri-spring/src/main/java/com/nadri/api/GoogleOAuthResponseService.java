package com.nadri.api;

import lombok.Data;

@Data
public class GoogleOAuthResponseService {
	private String accessToken;
	private String expiresIn;
	private String refreshToken;
	private String scope;
	private String tokenType;
	private String idToken;
}
