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
public class EgiftController {

    @Value("#{environment['SERVICE_ENDPOINT'] ?: 'localhost:8448'}")
    private String serviceEndpoint;

    @Inject
    private final EgiftRepository egifts;
    private static final Logger logger = LoggerFactory.getLogger(EgiftController.class);


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
}
