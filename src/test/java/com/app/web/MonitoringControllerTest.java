package com.app.web;

import com.app.config.SpringWebApplication;
import com.app.model.Measurement;
import com.app.model.MeasurementPeriod;
import com.app.model.MeasurementType;
import com.app.web.dto.MeasurementDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by Sergey on 13.09.2017.
 *
 * Actually it is integration test
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringWebApplication.class)
@WebAppConfiguration
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) //needed to re-init database state
public class MonitoringControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MeasurementDTO measurement;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }
    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        this.measurement = new MeasurementDTO(1, 999, MeasurementPeriod.MONTH,MeasurementType.G,new BigDecimal(1111.2).setScale(1,BigDecimal.ROUND_DOWN), LocalDateTime.now());
    }
    @Test
    public void getMeasurements() throws Exception {
        mockMvc.perform(post("/api/submitMeasurement/")
                .content(this.json(measurement))
                .contentType(contentType))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/getMeasurements/" + measurement.getUserId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].userId", is(measurement.getUserId())))
                .andExpect(jsonPath("$[0].type", is(measurement.getType().toString())))
                .andExpect(jsonPath("$[0].value").value(is(measurement.getValue().doubleValue())))
                .andExpect(jsonPath("$[0].creationDate", notNullValue()));
    }

    @Test
    public void submitMeasurement() throws Exception {
        mockMvc.perform(post("/api/submitMeasurement/")
                .content(this.json(measurement))
                .contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    public void submitMeasurementErored() throws Exception {
        measurement.setValue(null);
        mockMvc.perform(post("/api/submitMeasurement/")
                .content(this.json(measurement))
                .contentType(contentType))
                .andExpect(status().is4xxClientError());
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

}
