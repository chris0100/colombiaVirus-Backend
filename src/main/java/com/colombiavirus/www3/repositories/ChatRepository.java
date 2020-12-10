package com.colombiavirus.www3.repositories;

import com.colombiavirus.www3.models.Mensaje;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatRepository extends MongoRepository<Mensaje, String> {

    public List<Mensaje> findFirst10ByOrderByFechaDesc();
}
