package com.openclassroomsProject.Mediscreenreport.controller;

import com.openclassroomsProject.Mediscreenreport.model.Report;
import com.openclassroomsProject.Mediscreenreport.model.ReportRequest;
import com.openclassroomsProject.Mediscreenreport.service.IReportService;
import com.openclassroomsProject.Mediscreenreport.constants.RiskLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * Controller for generating patient reports.
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */
@RestController
public class ReportController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportController.class);

    private final IReportService reportServiceImpl;

    /**
     * ReportController constructor. Inject the service
     *
     * @param reportServiceImpl The ReportService implementation to use for report generation
     */
    public ReportController(IReportService reportServiceImpl) {
        this.reportServiceImpl = reportServiceImpl;
    }

    /**
     * Handles the POST request for generating a report.
     *
     * @param reportRequest The ReportRequest object containing the necessary data for report generation.
     * @return ResponseEntity containing the generated Report and the HTTP code
     */
    @PostMapping("/report")
    public ResponseEntity<Report> generateReport(@RequestBody ReportRequest reportRequest) {
        LOGGER.info("[CONTROLLER]-> call method generateReport() [PARAM]-> reportRequest " + reportRequest);
        List<String> wordsList = reportServiceImpl.extractWords(reportRequest.getNoteList());
        LOGGER.info("[CONTROLLER]-> wordsList = " + wordsList);
        int triggerNumber = reportServiceImpl.countTriggers(wordsList);
        LOGGER.info("[CONTROLLER]-> triggerNumber = " + triggerNumber);
        int patientAge = reportServiceImpl.calculateAge(reportRequest.getPatient().getDob());
        LOGGER.info("[CONTROLLER]-> patientAge = " + patientAge);
        RiskLevel riskLevel = reportServiceImpl.calculateRiskLevel(patientAge, reportRequest.getPatient().getSex(), triggerNumber);
        LOGGER.info("[CONTROLLER]-> riskLevel = " + riskLevel);
        Report report = reportServiceImpl.generateReport(reportRequest.getPatient(), patientAge, riskLevel);
        LOGGER.info("[CONTROLLER]-> report = " + report);
        return ResponseEntity.ok(report);
    }
}