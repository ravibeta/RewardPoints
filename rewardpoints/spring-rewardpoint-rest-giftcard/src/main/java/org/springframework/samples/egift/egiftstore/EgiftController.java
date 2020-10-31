/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.egift.egiftstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.samples.egift.model.Owner;
import org.springframework.samples.egift.model.Egift;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

import com.google.gson.Gson;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;
import java.io.StringReader;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Base64;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@RestController
public class EgiftController {

    @Value("#{environment['SERVICE_ENDPOINT'] ?: 'localhost:8448'}")
    private String serviceEndpoint;

    @Inject
    private final EgiftRepository egifts;
    private static final Logger logger = LoggerFactory.getLogger(EgiftController.class);
    private static String apiPath = EgiftConstants.DEFAULT_METHOD;
    private static String httpsURL = EgiftConstants.DEFAULT_HTTPSURL;
    private static String email = EgiftConstants.REGISTERED_EMAIL;
    private static String clientId = EgiftConstants.REGISTERED_PUBLISHABLE;
    private static String clientSecret = EgiftConstants.REGISTERED_SECRET + ":";
    private static String accountId = EgiftConstants.REGISTERED_ACCOUNT;


    public EgiftRepository getEgifts() {
        return egifts;
    }

    @Autowired
    public EgiftController(EgiftRepository egifts) {
        this.egifts = egifts;
    }

    @RequestMapping(value = "/owners/{ownerId}/egifts/", method = RequestMethod.POST)
    public void addEgift(Egift egift) {
        if (egift.getDescription() == null || egift.getDescription().trim().equals("")) {
            egift.setDescription("direct");
        }
        this.egifts.save(egift);
    }

    @RequestMapping(value = "/owner/{ownerId}/", method = RequestMethod.GET)
    public ResponseEntity<List<Egift>> getOwnerEgifts(@PathVariable int ownerId){
        List<Egift> egiftList = this.egifts.findByOwnerId(ownerId);
        if (egiftList == null) {
            return new ResponseEntity<List<Egift>>(egiftList, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<List<Egift>>(egiftList, HttpStatus.OK);
    }

    public static void main(String[] args) throws Exception {
    URL myurl = new URL(httpsURL+apiPath);
    HttpsURLConnection con = (HttpsURLConnection)myurl.openConnection();
    con.setRequestMethod("GET");
    con.setRequestProperty("Content-Type","application/x-www- form-urlencoded"); 
    con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0;Windows98;DigExt)"); 
    con.setRequestProperty("Authorization", "Basic " + Base64.getEncoder().encodeToString(new String(clientSecret+":").getBytes()));
    con.setRequestProperty("Stripe-Account", accountId);
    con.setDoInput(true); 
    DataInputStream input = new DataInputStream( con.getInputStream() ); 
    for( int c = input.read(); c != -1; c = input.read() ) 
    System.out.print( (char)c ); 
    input.close(); 

    System.out.println("Resp Code:"+con .getResponseCode()); 
    System.out.println("Resp Message:"+ con .getResponseMessage()); 
   }

   private String getToken() {
    String url = httpsURL + apiPath;
    HttpHeaders requestHeaders=new HttpHeaders();
    Base64.Encoder encoder = Base64.getEncoder();
    String encodedEmail = encoder.encodeToString(email.getBytes(StandardCharsets.UTF_8));
    requestHeaders.set("Content-Type","application/json");
    requestHeaders.set("Email", encodedEmail);
    requestHeaders.set("AccessToken", clientSecret);
    RestTemplate rest = new RestTemplate();
    String text = rest.postForObject(url,
            new HttpEntity("", requestHeaders), String.class);
    ReadContext response = JsonPath.parse(text);
    try(JsonReader jsonReader = Json.createReader(new StringReader(response.toString()))) {
         JsonObject result = jsonReader.readObject();
         String token = result.getString("value");
         return token;
    }
   }

    public String createGiftCard(@RequestParam(value="email", defaultValue="noone@none.com") String email) {
    String tokentext  = getToken();
    String url = httpsURL+ "/v1/Orders";
    String params= "{\"type\": \"Codes\", \"lineItems\":[{\"id1\"}], \"poNumber\": \"PO-123456789\"}";
    HttpHeaders requestHeaders=new HttpHeaders();
    requestHeaders.set("Content-Type","application/json");
    requestHeaders.set("Authorization","Bearer " + tokentext);
    RestTemplate rest = new RestTemplate();
    ReadContext response = JsonPath.parse(rest.postForObject(url,
            new HttpEntity(params, requestHeaders), String.class));
    try(JsonReader jsonReader = Json.createReader(new StringReader(response.toString()))) {
         JsonObject result = jsonReader.readObject();
         String id = result.getString("id");
         return id;
    }
   }
}
