package com.app.service;

import com.app.model.Measurement;
import com.app.repository.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Sergey on 13.09.2017.
 */
@Service("measurementService")
public class MeasurementServiceImpl implements MeasurementService{

    private MeasurementRepository measurementRepository;

    @Autowired
    public MeasurementServiceImpl(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    @Override
    @Transactional
    public void saveMeasurement(Measurement measurement) {
        // TODO add converter layer, service should not interact with request
        measurement.setCreationDate(LocalDateTime.now());
        measurementRepository.save(measurement);
    }

    @Override
    public List<Measurement> getMeasurements(Integer userId) {
       return measurementRepository.findByUserId(userId);
    }

    @Override
    @Transactional
    public void deleteAll(){
        measurementRepository.deleteAll();
    }
}
