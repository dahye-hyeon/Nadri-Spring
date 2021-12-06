package com.nadri.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class LoginUtil {
	 public static HashMap<String, Object> getUserInfo (String access_Token) {

         //    요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
         HashMap<String, Object> userInfo = new HashMap<>();
         String reqURL = "https://kapi.kakao.com/v2/user/me";
         try {
             URL url = new URL(reqURL);
             HttpURLConnection conn = (HttpURLConnection) url.openConnection();
             conn.setRequestMethod("GET");

             //    요청에 필요한 Header에 포함될 내용
             conn.setRequestProperty("Authorization", "Bearer " + access_Token);

             int responseCode = conn.getResponseCode();
             System.out.println("responseCode : " + responseCode);

             BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

             String line = "";
             String result = "";

             while ((line = br.readLine()) != null) {
                 result += line;
             }
             System.out.println("response body : " + result);

             JsonParser parser = new JsonParser();
             JsonElement element = parser.parse(result);

             String id = element.getAsJsonObject().get("id").getAsString();
             JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
             JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

             String nickname = properties.getAsJsonObject().get("nickname").getAsString();
             String profile_image = properties.getAsJsonObject().get("profile_image").getAsString();
             String email;
             try {
            	 email = kakao_account.getAsJsonObject().get("email").getAsString();
             } catch(NullPointerException e) {
            	 email = "Kakao" + id;
             }
             

             userInfo.put("nickname", nickname);
             userInfo.put("email", email);
             userInfo.put("profile_image", profile_image);

         } catch (IOException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         }

         return userInfo;
     }
}
