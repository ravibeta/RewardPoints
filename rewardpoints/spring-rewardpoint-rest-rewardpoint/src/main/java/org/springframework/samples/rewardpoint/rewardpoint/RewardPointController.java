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
package org.springframework.samples.rewardpoint.rewardpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RewardPointController {

    @Value("#{environment['SERVICE_ENDPOINT'] ?: 'localhost:8118'}")
    private String serviceEndpoint;

    @Inject
    private final RewardPointRepository rewardpoints;
    private static final Logger logger = LoggerFactory.getLogger(RewardPointController.class);


    public RewardPointRepository getRewardPoints() {
        return rewardpoints;
    }

    @Autowired
    public RewardPointController(RewardPointRepository rewardpoints) {
        this.rewardpoints = rewardpoints;
    }

    @RequestMapping(value = "/owners/{ownerId}/rewardpoints/", method = RequestMethod.POST)
    public void addRewardPoint(RewardPoint rewardpoint) {
        if (rewardpoint.getDescription() == null || rewardpoint.getDescription().trim().equals("")) {
            rewardpoint.setDescription("direct");
        }
        this.rewardpoints.save(rewardpoint);
    }

    @RequestMapping(value = "/owner/{ownerId}/", method = RequestMethod.GET)
    public ResponseEntity<List<RewardPoint>> getOwnerRewardPoints(@PathVariable int ownerId){
        List<RewardPoint> rewardpointList = this.rewardpoints.findByOwnerId(ownerId);
        if (rewardpointList == null) {
            return new ResponseEntity<List<RewardPoint>>(rewardpointList, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<List<RewardPoint>>(rewardpointList, HttpStatus.OK);
    }
/*
curl -i -k 'http://localhost:8118/owner/7/'
HTTP/1.1 200
Content-Type: application/json
Transfer-Encoding: chunked
Date: Thu, 22 Oct 2020 00:10:38 GMT

[{"id":1,"date":"2013-01-01T00:00:00.000+0000","description":"","ownerId":7,"new":false},{"id":4,"date":"2013-01-04T00:00:00.000+0000","description":"","ownerId":7,"new":false},{"id":20,"date":"2013-01-04T00:00:00.000+0000","description":"","ownerId":7,"new":false}]
curl -i -k 'http://localhost:8118/owners/7/rewardpoints/' -X POST -d '{"rewardpoint_description":"direct","rewardpoint_date":"2020/10/22","owner_id":7}' -H 'Content-Type: application/xml'  -H 'Content-length: 0'
HTTP/1.1 200
Content-Length: 0
Date: Thu, 22 Oct 2020 07:30:09 GMT
*/
}
