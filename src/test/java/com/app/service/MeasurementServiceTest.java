package com.app.service;

import com.app.model.Measurement;
import com.app.repository.MeasurementRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by Sergey on 16.09.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class MeasurementServiceTest {

    @Mock
    private MeasurementRepository measurementRepository;

    @Mock
    private MeasurementServiceImpl service;

    @Before
    public void setup(){
        service = new MeasurementServiceImpl(measurementRepository);
    }

    @Test
    public void saveMeasurement(){
        Measurement m = new Measurement();
        m.setId(11);

        service.saveMeasurement(m);

        ArgumentCaptor<Measurement> argument = ArgumentCaptor.forClass(Measurement.class);
        verify(measurementRepository).save(argument.capture());

        verify(measurementRepository, times(1)).save(m);
        assertEquals(new Integer(11), argument.getValue().getId());
        assertNotNull( argument.getValue().getCreationDate());
    }
}
