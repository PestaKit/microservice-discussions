package io.pestakit.discussions.repositories;

import io.pestakit.discussions.entities.CommentEntity;
import io.pestakit.discussions.entities.DiscussionEntity;
import org.springframework.data.repository.CrudRepository;

public interface DiscussionRepository extends CrudRepository<DiscussionEntity, Long>{

    public DiscussionEntity findByIdDiscussion(String id_discussion);
}