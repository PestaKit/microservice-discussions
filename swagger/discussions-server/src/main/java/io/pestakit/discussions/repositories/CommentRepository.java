package io.pestakit.discussions.repositories;

import io.pestakit.discussions.entities.CommentEntity;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<CommentEntity, Integer>{

    public CommentEntity findByIdComment(int id_comment);
}