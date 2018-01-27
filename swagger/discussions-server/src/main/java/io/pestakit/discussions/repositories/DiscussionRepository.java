/**
File which containts interface DiscussionRepository 
*/
package io.pestakit.discussions.repositories;

import io.pestakit.discussions.entities.CommentEntity;
import io.pestakit.discussions.entities.DiscussionEntity;
import org.springframework.data.repository.CrudRepository;

public interface DiscussionRepository extends CrudRepository<DiscussionEntity, Integer>{
     //allows to find all discussion using its ID  
    public DiscussionEntity findByIdDiscussion(int id_discussion);
}
