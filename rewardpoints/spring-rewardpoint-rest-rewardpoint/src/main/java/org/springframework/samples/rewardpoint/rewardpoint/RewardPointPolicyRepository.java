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

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.samples.rewardpoint.model.BaseEntity;
import org.springframework.samples.rewardpoint.model.RewardPoint;
import org.springframework.samples.rewardpoint.model.RewardPointPolicy;
import org.springframework.transaction.annotation.Transactional;

@RepositoryRestResource(collectionResourceRel = "rewardpointpolicy", path = "rewardpointpolicy")
public interface RewardPointPolicyRepository extends PagingAndSortingRepository<RewardPointPolicy, Integer> {
    /**
     * Retrieve a Collection of {@link RewardPointPolicy} from the data store by id.
     * @return a Collection of {@link RewardPointPolicy} if found
     */
    @Query("SELECT policy FROM RewardPointPolicy policy")
    @Transactional(readOnly = true)
    Collection<RewardPointPolicy> getPolicies();

    /**
     * Retrieve {@link RewardPointPolicy}s from the data store returning all policies
     * @param lastName Value to search for
     * @return a Collection of matching {@link Owner}s (or an empty Collection if none
     * found)
     */
    @Query("SELECT DISTINCT policy FROM RewardPointPolicy policy WHERE policy.predicate LIKE :predicate%")
    @Transactional(readOnly = true)
    Collection<RewardPointPolicy> findByPredicate(@Param("predicate") String predicate);
}

