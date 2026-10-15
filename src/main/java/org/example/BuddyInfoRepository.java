package org.example;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "buddies", collectionResourceRel = "buddies")
public interface BuddyInfoRepository extends CrudRepository<BuddyInfo, Integer> {

    List<BuddyInfo> findByName(@Param("name") String name);
}