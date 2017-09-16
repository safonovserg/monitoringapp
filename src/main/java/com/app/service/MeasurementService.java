package com.app.service;

import com.app.model.Measurement;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Sergey on 13.09.2017.
 */
public interface MeasurementService {

    void saveMeasurement(Measurement measurement);

    List<Measurement> getMeasurements(Integer userId);

    void deleteAll();
}
