package com.openclassroomsProject.Mediscreenreport.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassroomsProject.Mediscreenreport.constants.RiskLevel;
import com.openclassroomsProject.Mediscreenreport.model.Note;
import com.openclassroomsProject.Mediscreenreport.model.Patient;
import com.openclassroomsProject.Mediscreenreport.model.Report;
import com.openclassroomsProject.Mediscreenreport.model.ReportRequest;
import com.openclassroomsProject.Mediscreenreport.service.IReportService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import java.util.Arrays;
import java.sql.Date;
import java.util.List;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Unit tests for the ReportController controller.
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
class ReportControllerTest {

    @Mock
    private IReportService reportService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = standaloneSetup(new ReportController(reportService)).build();
    }

    @Test
    public void generateReport_ShouldReturnReport() throws Exception {
        ReportRequest reportRequest = new ReportRequest();
        reportRequest.setNoteList(Collections.singletonList(new Note()));
        Patient patient = new Patient();
        patient.setDob(new Date(1000));
        patient.setSex("F");
        patient.setFamily("familyTest");
        patient.setGiven("givenTest");
        reportRequest.setPatient(patient);
        List<String> wordsList = Arrays.asList("word1", "word2", "word3");
        int triggerNumber = 3;
        int patientAge = 30;
        RiskLevel riskLevel = RiskLevel.BORDERLINE;
        Report report = new Report("familyTest", "givenTest", patientAge, riskLevel.toString());
        when(reportService.extractWords(anyList())).thenReturn(wordsList);
        when(reportService.countTriggers(anyList())).thenReturn(triggerNumber);
        when(reportService.calculateAge(any(Date.class))).thenReturn(patientAge);
        when(reportService.calculateRiskLevel(anyInt(), anyString(), anyInt())).thenReturn(riskLevel);
        when(reportService.generateReport(any(Patient.class), anyInt(), any(RiskLevel.class))).thenReturn(report);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(reportRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.family").value("familyTest"))
                .andExpect(jsonPath("$.given").value("givenTest"))
                .andExpect(jsonPath("$.age").value(patientAge))
                .andExpect(jsonPath("$.assessment").value(riskLevel.toString()));
        verify(reportService, times(1)).extractWords(anyList());
        verify(reportService, times(1)).countTriggers(anyList());
        verify(reportService, times(1)).calculateAge(any(Date.class));
        verify(reportService, times(1)).calculateRiskLevel(anyInt(), anyString(), anyInt());
        verify(reportService, times(1)).generateReport(any(Patient.class), anyInt(), any(RiskLevel.class));
    }
}