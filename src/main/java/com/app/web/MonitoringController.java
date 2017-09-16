package com.app.web;

import com.app.model.Measurement;
import com.app.service.MeasurementService;
import com.app.web.dto.MeasurementConverter;
import com.app.web.dto.MeasurementDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.List;
import static com.app.web.dto.MeasurementConverter.*;

/**
 * Created by Sergey on 13.09.2017.
 */
@RequestMapping("/api")
@Controller
public class MonitoringController {

    private MeasurementService measurementService;

    @Autowired
    public MonitoringController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public @ResponseBody
    String ping() {
        return "ping";
    }

    @RequestMapping(value = "/getMeasurements/{userId}", method = RequestMethod.GET)
    public @ResponseBody
    List<MeasurementDTO> getMeasurements(@PathVariable(value = "userId") Integer userId) {
        return toMeasurementDTO(measurementService.getMeasurements(userId));
    }


    @RequestMapping(value = "/submitMeasurement", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void submitMeasurement(@RequestBody MeasurementDTO measurement) {
        measurementService.saveMeasurement(toMeasurement(measurement));
    }

    @ExceptionHandler
    void handleConstraintViolationException(ConstraintViolationException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }
}
