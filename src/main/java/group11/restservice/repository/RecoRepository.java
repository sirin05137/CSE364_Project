package group11.restservice.repository;

import group11.restservice.model.MovieData;
import group11.restservice.model.RecoData;
import group11.restservice.model.UserData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

//@Repository
@RepositoryRestResource(collectionResourceRel = "movies", path = "movies")
public interface RecoRepository extends MongoRepository<RecoData, String>  {
    List<RecoData> findByTitle(@Param("title") String title);
}
