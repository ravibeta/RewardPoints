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
import org.springframework.samples.rewardpoint.model.RewardPointPolicy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class RewardPointClassifier {

    @Inject
    private final RewardPointRepository rewardpoints;
    @Inject
    private final RewardPointPolicyRepository rewardPointsPolicy;
    private static final Logger logger = LoggerFactory.getLogger(RewardPointClassifier.class);

    public RewardPointRepository getRewardPoints() {
        return rewardpoints;
    }
    public RewardPointPolicyRepository getRewardPointsPolicy() { return rewardPointsPolicy; }

    @Autowired
    public RewardPointClassifier(RewardPointRepository rewardpoints,
                                 RewardPointPolicyRepository rewardPointsPolicy) {
        this.rewardpoints = rewardpoints;
        this.rewardPointsPolicy = rewardPointsPolicy;
    }

    public boolean admit(RewardPoint rewardPoint){
        List<RewardPoint> rewardpointList = this.rewardpoints.findByOwnerId(rewardPoint.getOwnerId());
        if (rewardpointList == null) {
            return true;
        }
        Collection<RewardPointPolicy> policiesList = this.rewardPointsPolicy.getPolicies();
        for (RewardPointPolicy policy : policiesList){
            if (!policy.evaluate(rewardPoint)){
                return false;
            }
        }
        return true;
    }
}
