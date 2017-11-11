package io.pestakit.discussions.repositories;

import io.pestakit.discussions.entities.CommentEntity;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<CommentEntity, Long>{

}