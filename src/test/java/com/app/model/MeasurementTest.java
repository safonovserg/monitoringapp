package com.app.model;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import javax.validation.Validation;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

//import javax.validation.Validator;

/**
 * Created by Sergey on 16.09.2017.
 */
public class MeasurementTest {

    private static Validator validator;

    private Measurement validMeasurement;

    @Before
    public void setUp() {
        validator = new SpringValidatorAdapter(Validation.buildDefaultValidatorFactory().getValidator());
        validMeasurement = new Measurement(1, 1, MeasurementPeriod.MONTH, MeasurementType.CW, new BigDecimal(22.1).setScale(2, BigDecimal.ROUND_DOWN), LocalDateTime.now());

    }

    private Errors invokeValidator(Object bean) {
        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(bean, "bean");
        ValidationUtils.invokeValidator(validator, bean, errors);
        return errors;
    }

    @Test
    public void measurementSuccess() {
        Errors actual = invokeValidator(validMeasurement);
        System.out.println(actual.getAllErrors());
        assertFalse("No validation errors", actual.hasErrors());
    }

    @Test
    public void wrongFractionValue() {
        Measurement measurement = new Measurement();
        measurement.setValue(new BigDecimal(1.2222).setScale(4, BigDecimal.ROUND_DOWN));
        Errors actual = invokeValidator(measurement);
        System.out.println(actual.getAllErrors());
        assertFalse("allowed only fraction 3", !actual.hasErrors());
    }

    @Test
    public void emptyFields() {
        Measurement measurement = new Measurement();
        measurement.setMeasurementPeriod(null);
        measurement.setValue(null);
        measurement.setType(null);
        measurement.setUserId(null);

        Errors actual = invokeValidator(measurement);
        assertEquals("should be 4 null validation errors", 4, actual.getAllErrors().size());
    }

}
