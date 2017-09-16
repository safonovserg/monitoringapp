package com.app.web.dto;

import com.app.model.Measurement;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Sergey on 16.09.2017.
 */
public class MeasurementConverter {

    public static List<MeasurementDTO> toMeasurementDTO(List<Measurement> measurements) {
        return measurements.stream()
                .map(MeasurementConverter::toMeasurementDTO)
                .collect(Collectors.toList());
    }

    public static MeasurementDTO toMeasurementDTO(Measurement measurement){
        return new MeasurementDTO(measurement.getId(),measurement.getUserId(),measurement.getMeasurementPeriod(),measurement.getType(),measurement.getValue(),measurement.getCreationDate());
    }

    public static Measurement toMeasurement(MeasurementDTO measurement){
        return new Measurement(measurement.getId(),measurement.getUserId(),measurement.getMeasurementPeriod(),measurement.getType(),measurement.getValue(),measurement.getCreationDate());
    }
}
