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
package org.springframework.samples.rewardpoint.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.samples.rewardpoint.model.Owner;
import org.springframework.samples.rewardpoint.model.RewardPoint;
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

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.rewardpoint.model.BaseEntity;

import com.google.gson.Gson;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "rewardpointpolicys")
public class RewardPointPolicy extends BaseEntity {
    @Column(name = "predicate")
    @NotEmpty
    @Getter @Setter private String predicate;


    public boolean evaluate(RewardPoint rewardPoint){
        if (this.predicate == null) {
            return true;
        }
        boolean result = true;
        for (String condition : predicate.split(",")) {
            if (condition.indexOf("AND") != -1) {
                int index = condition.indexOf("AND");
                String field = condition.substring(0,index);
                String value = condition.substring(index+3);
                result = result && evaluateCondition(field, value, "AND", rewardPoint);
            } else if (condition.indexOf("OR") != -1) {
                int index= condition.indexOf("OR");
                String field = condition.substring(0, index);
                String value = condition.substring(index+2);
                result = result && evaluateCondition(field, value, "OR", rewardPoint);
            } else if (condition.indexOf("BEFORE") != -1) {
                int index= condition.indexOf("BEFORE");
                String field = condition.substring(0, index);
                String value = condition.substring(index+6);
                result = result && evaluateCondition(field, value, "BEFORE", rewardPoint);
            } else if (condition.indexOf("AFTER") != -1) {
                int index= condition.indexOf("AFTER");
                String field = condition.substring(0, index);
                String value = condition.substring(index+5);
                result = result && evaluateCondition(field, value, "AFTER", rewardPoint);
            }  else if (condition.indexOf("LIKE") != -1) {
                int index= condition.indexOf("LIKE");
                String field = condition.substring(0, index);
                String value = condition.substring(index+4);
                result = result && evaluateCondition(field, value, "LIKE", rewardPoint);
            } else {
                result = result && true;
            }
        }
        return result;
    }

    public boolean evaluateCondition(String field, String value, String operator, RewardPoint rewardPoint) {
        if (field.equals("date")){
            if (operator.equals("BEFORE")) {
                if (rewardPoint.getDate().toString().compareTo(value) <= 0) {
                    return true;
                } else {
                    return false;
                }
            }
            if (operator.equals("AFTER")) {
                if (rewardPoint.getDate().toString().compareTo(value) > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } else if (field.equals("description")) {
            if (rewardPoint.getDescription().indexOf(value) != -1) {
                return true;
            } else {
                return false;
            }
        } else if (field.equals("ownerId")) {
            if (rewardPoint.getOwnerId().toString().equals(value)) {
                return true;
            } else {
                return false;
            }
        } else if (field.equals("points")) {
            if (rewardPoint.getPoints().toString().equals(value)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
        return false;
    }
}
