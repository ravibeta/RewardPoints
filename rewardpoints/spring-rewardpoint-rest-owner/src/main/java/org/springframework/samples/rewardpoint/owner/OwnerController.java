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

import javax.inject.Inject;
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

    @RequestMapping(value = "/owner/{ownerId}/getVisits", method = RequestMethod.GET)
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
        ResponseEntity<RewardPoint[]> entity = restTemplate.getForEntity("http://"+serviceEndpoint+"/rewardpoint/", RewardPoint[].class);
        if (entity.getBody() != null) {
            rewardpointList = Arrays.asList(entity.getBody()).stream().filter(t -> t.getOwnerId() == ownerId).collect(Collectors.toList());
        }
        return rewardpointList;
    }
}
