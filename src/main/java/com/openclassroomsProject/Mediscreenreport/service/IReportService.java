package com.openclassroomsProject.Mediscreenreport.service;

import com.openclassroomsProject.Mediscreenreport.model.Note;
import com.openclassroomsProject.Mediscreenreport.model.Patient;
import com.openclassroomsProject.Mediscreenreport.model.Report;
import com.openclassroomsProject.Mediscreenreport.constants.RiskLevel;
import java.sql.Date;
import java.util.List;

/**
 * @author jonathan GOUVEIA
 * @version 1.0
 */
public interface IReportService {

    List<String> extractWords(List<Note> noteList);

    int countTriggers(List<String> wordsList);

    int calculateAge(Date date);

    RiskLevel calculateRiskLevel(int age, String gender, int triggerCount);

    Report generateReport(Patient patient, int age, RiskLevel riskLevel);
}