package com.app.web.dto;

import com.app.model.MeasurementPeriod;
import com.app.model.MeasurementType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by Sergey on 16.09.2017.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MeasurementDTO {

    private Integer id;

    @NotNull(message = "userId can't be null")
    private Integer userId;

    @NotNull(message = "measurementPeriod can't be null")
    @Enumerated(EnumType.STRING)
    private MeasurementPeriod measurementPeriod;

    @NotNull(message = "type can't be null")
    @Enumerated(EnumType.STRING)
    private MeasurementType type;

    @Digits(fraction = 3, integer = 16)
    @DecimalMin(value = "0", inclusive = false)
    @NotNull(message = "value can't be null")
    private BigDecimal value;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime creationDate;
}
