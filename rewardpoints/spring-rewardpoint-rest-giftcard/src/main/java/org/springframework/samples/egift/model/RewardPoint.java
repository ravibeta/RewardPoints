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
package org.springframework.samples.egift.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.egift.model.BaseEntity;

/**
 * Simple JavaBean domain object representing a rewardpoint.
 *
 */
@Entity
@Table(name = "rewardpoints")
public class RewardPoint extends BaseEntity {

    /**
     * Holds value of property date.
     */
    @Column(name = "rewardpoint_date")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date date;

    /**
     * Holds value of property description.
     */
    @NotEmpty
    @Column(name = "description")
    private String description;

    /**
     * Holds value of property owner.
     */
    @Column(name = "owner_id")
    private Integer ownerId;


    /**
     * Holds value of reward points.
     */
    @Column(name = "points")
    private Integer points;

    /**
     * Creates a new instance of RewardPoint for the current date
     */
    public RewardPoint() {
        this.date = new Date();
    }


    /**
     * Getter for property date.
     *
     * @return Value of property date.
     */
    public Date getDate() {
        return this.date;
    }

    /**
     * Setter for property date.
     *
     * @param date New value of property date.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Getter for property description.
     *
     * @return Value of property description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Setter for property description.
     *
     * @param description New value of property description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for property owner id.
     *
     * @return Value of property owner id.
     */
    public Integer getOwnerId() {
        return this.ownerId;
    }

    /**
     * Setter for property owner id.
     *
     * @param ownerId New value of property owner id.
     */
    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * Getter for property points.
     *
     * @return Value of property points.
     */
    public Integer getPoints() {
        return this.points;
    }

    /**
     * Setter for property points.
     *
     * @param points New value of property points
     */
    public void setPoints(Integer points) {
        this.points = points;
    }
}
