package com.app.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by Sergey on 13.09.2017.
 */
@Entity
@Table(name = "measurement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "userId", nullable = false)
    @NotNull(message = "userId can't be null")
    private Integer userId;

    @Column(name = "period", nullable = false)
    @NotNull(message = "measurementPeriod can't be null")
    @Enumerated(EnumType.STRING)
    private MeasurementPeriod measurementPeriod;

    @Column(name = "type", nullable = false)
    @NotNull(message = "type can't be null")
    @Enumerated(EnumType.STRING)
    private MeasurementType type;

    @Column(name = "value", nullable = false)
    @Digits(fraction = 3, integer = 16)
    @DecimalMin(value = "0", inclusive = false)
    @NotNull(message = "value can't be null")
    private BigDecimal value;

    @Column(name = "creationDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime creationDate;

}
