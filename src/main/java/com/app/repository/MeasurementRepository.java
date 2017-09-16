package com.app.repository;

import com.app.model.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Sergey on 13.09.2017.
 */
//@Repository("measurementRepository") intelij don't like it
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {

    List<Measurement> findByUserId(Integer userId);
}
