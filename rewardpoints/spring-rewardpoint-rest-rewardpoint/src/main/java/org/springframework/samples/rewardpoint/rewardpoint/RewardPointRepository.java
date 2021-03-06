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

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.samples.rewardpoint.model.BaseEntity;
import org.springframework.samples.rewardpoint.model.Owner;
import org.springframework.samples.rewardpoint.model.RewardPoint;
import org.springframework.samples.rewardpoint.model.RewardPointPolicy;
import org.springframework.transaction.annotation.Transactional;

@RepositoryRestResource(collectionResourceRel = "rewardpoint", path = "rewardpoint")

public interface RewardPointRepository extends PagingAndSortingRepository<RewardPoint, Integer> {
    /**
     * Retrieve an {@link Owner} from the data store by id.
     *
     * @param id the id to search for
     * @return the {@link Owner} if found
     */
    @Query("SELECT rewardpoint FROM RewardPoint rewardpoint WHERE ownerId =:ownerId")
    @Transactional(readOnly = true)
    List<RewardPoint> findByOwnerId(@Param("ownerId") Integer ownerId);

}

