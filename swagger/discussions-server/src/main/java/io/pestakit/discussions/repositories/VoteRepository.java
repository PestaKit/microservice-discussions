package io.pestakit.discussions.repositories;

import io.pestakit.discussions.entities.VoteEntity;
import org.springframework.data.repository.CrudRepository;

public interface VoteRepository extends CrudRepository<VoteEntity, Integer>{

}