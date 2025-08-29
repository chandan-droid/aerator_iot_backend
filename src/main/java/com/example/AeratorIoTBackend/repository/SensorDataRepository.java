package com.example.AeratorIoTBackend.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.example.AeratorIoTBackend.model.SensorDataModel;

@Repository
public interface SensorDataRepository extends MongoRepository<SensorDataModel, String> {
    
}
