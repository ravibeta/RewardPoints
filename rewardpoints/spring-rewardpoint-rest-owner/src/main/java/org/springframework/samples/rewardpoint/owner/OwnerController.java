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
package org.springframework.samples.rewardpoint.owner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.samples.rewardpoint.model.Owner;
import org.springframework.samples.rewardpoint.model.RewardPoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonArray;
import javax.json.JsonObject;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
class OwnerController {

    @Value("#{environment['SERVICE_ENDPOINT'] ?: 'localhost:8118'}")
    private String serviceEndpoint;

    @Inject
    private OwnerRepository owners;
    private static final Logger logger = LoggerFactory.getLogger(OwnerController.class);

    @RequestMapping(value = "/owner/{ownerId}/getrewardpoints", method = RequestMethod.GET)
    public ResponseEntity<List<RewardPoint>> getOwnerRewardPoints(@PathVariable int ownerId){
        List<RewardPoint> rewardpointList = new ArrayList<RewardPoint>();
        Owner owner = this.owners.findByOwnerId(ownerId);
        if (owner == null) {
            return new ResponseEntity<List<RewardPoint>>(rewardpointList, HttpStatus.BAD_REQUEST);
        }
        rewardpointList = getRewardPoints(ownerId);
        return new ResponseEntity<List<RewardPoint>>(rewardpointList, HttpStatus.OK);
    }

    private List<RewardPoint> getRewardPoints(int ownerId){
        List<RewardPoint> rewardpointList = new ArrayList<RewardPoint>();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> entity2 = restTemplate.getForEntity("http://"+serviceEndpoint+"/rewardpoint/", String.class);
        RewardPoint[] rewardpoints = null;
        try(JsonReader jsonReader = Json.createReader(new StringReader(entity2.getBody()))) {
         JsonObject result = jsonReader.readObject();
         JsonObject embedded = result.getJsonObject("_embedded");
         JsonArray jsonArray = embedded.getJsonArray("rewardpoint");
         rewardpoints = new Gson().fromJson(jsonArray.toString(), RewardPoint[].class);
        }
        if (rewardpoints  != null) {
            rewardpointList = Arrays.asList(rewardpoints).stream().filter(t -> t.getOwnerId() == ownerId).collect(Collectors.toList());
        }
        return rewardpointList;
    }
    /*
 curl -i -k 'http://localhost:8228/owner/7/getrewardpoints'
HTTP/1.1 200
Content-Type: application/json
Transfer-Encoding: chunked
Date: Wed, 21 Oct 2020 10:17:37 GMT

[{"id":null,"date":"2013-01-01T00:00:00.000+0000","description":"","ownerId":7,"new":true},{"id":null,"date":"2013-01-04T00:00:00.000+0000","description":"","ownerId":7,"new":true},{"id":null,"date":"2013-01-04T00:00:00.000+0000","description":"","ownerId":7,"new":true}]
     */
}
