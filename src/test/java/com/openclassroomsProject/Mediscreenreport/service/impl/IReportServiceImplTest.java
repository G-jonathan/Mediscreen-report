package com.openclassroomsProject.Mediscreenreport.service.impl;

import com.openclassroomsProject.Mediscreenreport.constants.RiskLevel;
import com.openclassroomsProject.Mediscreenreport.constants.Triggers;
import com.openclassroomsProject.Mediscreenreport.model.Patient;
import com.openclassroomsProject.Mediscreenreport.model.Report;
import com.openclassroomsProject.Mediscreenreport.model.Note;
import com.openclassroomsProject.Mediscreenreport.service.IReportService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the ReportServiceImpl class.
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */
class IReportServiceImplTest {
    @Mock
    private Triggers triggers;

    @InjectMocks
    private IReportService reportService = new IReportServiceImpl();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCountTriggers() {
        List<String> triggerList = Arrays.asList(
                "hémoglobine", "microalbumine", "taille", "poids", "fumeur",
                "anormal", "cholestérol", "vertige", "rechute", "réaction", "anticorps",
                "hemoglobin", "microalbumin", "height", "weight", "smoker",
                "abnormal", "cholesterol", "dizziness", "relapse", "reaction", "antibodies"
        );
        List<Note> noteList = new ArrayList<>();
        Note noteFrenchTriggers = new Note();
        noteFrenchTriggers.setComment(" hémoglobine a1c, microalbumine, taille, poids fumeur anormal: cholestérol. VERTIGE, Rechute: Réaction. Anticorps");
        noteList.add(noteFrenchTriggers);
        Note noteEnglishTriggers = new Note();
        noteEnglishTriggers.setComment(" hemoglobin a1c, microalbumin, height, weight smoker abnormal: cholesterol. DIZZINESS, Relapse: Reaction. Antibodies");
        noteList.add(noteEnglishTriggers);
        List<String> wordsList = reportService.extractWords(noteList);
        when(triggers.getTriggers()).thenReturn(triggerList);
        int triggerCount = reportService.countTriggers(wordsList);
        assertEquals(22, triggerCount);
        verify(triggers, times(22)).getTriggers();
    }

    @Test
    void testExtractWords() {
        List<Note> noteList = new ArrayList<>();
        Note noteFrenchTriggers = new Note();
        noteFrenchTriggers.setComment(" hémoglobine a1c, microalbumine, taille, poids fumeur anormal: cholestérol. VERTIGE, Rechute: Réaction. Anticorps");
        noteList.add(noteFrenchTriggers);
        Note noteEnglishTriggers = new Note();
        noteEnglishTriggers.setComment(" hemoglobin a1c, microalbumin, height, weight smoker abnormal: cholesterol. DIZZINESS, Relapse: Reaction. Antibodies");
        noteList.add(noteEnglishTriggers);
        List<String> wordsList = reportService.extractWords(noteList);
        System.out.println(wordsList);
        assertEquals(22, wordsList.size());
        Assertions.assertTrue(wordsList.contains("hémoglobine"));
        Assertions.assertTrue(wordsList.contains("microalbumine"));
        Assertions.assertTrue(wordsList.contains("taille"));
        Assertions.assertTrue(wordsList.contains("poids"));
        Assertions.assertTrue(wordsList.contains("fumeur"));
        Assertions.assertTrue(wordsList.contains("anormal"));
        Assertions.assertTrue(wordsList.contains("cholestérol"));
        Assertions.assertTrue(wordsList.contains("VERTIGE"));
        Assertions.assertTrue(wordsList.contains("Rechute"));
        Assertions.assertTrue(wordsList.contains("Réaction"));
        Assertions.assertTrue(wordsList.contains("Anticorps"));
        Assertions.assertTrue(wordsList.contains("hemoglobin"));
        Assertions.assertTrue(wordsList.contains("microalbumin"));
        Assertions.assertTrue(wordsList.contains("height"));
        Assertions.assertTrue(wordsList.contains("weight"));
        Assertions.assertTrue(wordsList.contains("smoker"));
        Assertions.assertTrue(wordsList.contains("abnormal"));
        Assertions.assertTrue(wordsList.contains("cholesterol"));
        Assertions.assertTrue(wordsList.contains("DIZZINESS"));
        Assertions.assertTrue(wordsList.contains("Relapse"));
        Assertions.assertTrue(wordsList.contains("Reaction"));
        Assertions.assertTrue(wordsList.contains("Antibodies"));
    }

    @Test
    void testCalculateAge() {
        Date dateOfBirth = Date.valueOf(LocalDate.now().minusYears(30));
        int age = reportService.calculateAge(dateOfBirth);
        Assertions.assertEquals(30, age);
    }

    @Test
    void testCalculateRiskLevel_whenTriggerIs0SexIsMAndAgeIsGreaterThan30_thenReturnNone() {
        int age = 35;
        String gender = "M";
        int triggerCount = 0;
        RiskLevel riskLevel = reportService.calculateRiskLevel(age, gender, triggerCount);
        Assertions.assertEquals(RiskLevel.NONE, riskLevel);
    }

    @Test
    void testCalculateRiskLevel_whenTriggerIs0SexIsMAndAgeIsLessThan30_thenReturnNone() {
        int age = 20;
        String gender = "M";
        int triggerCount = 0;
        RiskLevel riskLevel = reportService.calculateRiskLevel(age, gender, triggerCount);
        Assertions.assertEquals(RiskLevel.NONE, riskLevel);
    }

    @Test
    void testCalculateRiskLevel_whenTriggerIs0SexIsFAndAgeGreaterThan30_thenReturnNone() {
        int age = 35;
        String gender = "F";
        int triggerCount = 0;
        RiskLevel riskLevel = reportService.calculateRiskLevel(age, gender, triggerCount);
        Assertions.assertEquals(RiskLevel.NONE, riskLevel);
    }

    @Test
    void testCalculateRiskLevel_whenTriggerIs0SexIsFAndAgeIsLessThan30_thenReturnNone() {
        int age = 20;
        String gender = "F";
        int triggerCount = 0;
        RiskLevel riskLevel = reportService.calculateRiskLevel(age, gender, triggerCount);
        Assertions.assertEquals(RiskLevel.NONE, riskLevel);
    }

    @Test
    void testCalculateRiskLevel_whenTriggerIs2SexIsMAndAgeIsGreaterThan30_thenReturnBorderline() {
        int age = 35;
        String gender = "M";
        int triggerCount = 2;
        RiskLevel riskLevel = reportService.calculateRiskLevel(age, gender, triggerCount);
        Assertions.assertEquals(RiskLevel.BORDERLINE, riskLevel);
    }

    @Test
    void testCalculateRiskLevel_whenTriggerIs2SexIsFAndAgeIsGreaterThan30_thenReturnBorderline() {
        int age = 35;
        String gender = "F";
        int triggerCount = 2;
        RiskLevel riskLevel = reportService.calculateRiskLevel(age, gender, triggerCount);
        Assertions.assertEquals(RiskLevel.BORDERLINE, riskLevel);
    }

    @Test
    void testCalculateRiskLevel_whenTriggerIs3SexIsMAndAgeIsLessThan30_thenReturnDanger() {
        int age = 20;
        String gender = "M";
        int triggerCount = 3;
        RiskLevel riskLevel = reportService.calculateRiskLevel(age, gender, triggerCount);
        Assertions.assertEquals(RiskLevel.IN_DANGER, riskLevel);
    }

    @Test
    void testCalculateRiskLevel_whenTriggerIs4SexIsFAndAgeIsLessThan30_thenReturnDanger() {
        int age = 20;
        String gender = "F";
        int triggerCount = 4;
        RiskLevel riskLevel = reportService.calculateRiskLevel(age, gender, triggerCount);
        Assertions.assertEquals(RiskLevel.IN_DANGER, riskLevel);
    }

    @Test
    void testCalculateRiskLevel_whenTriggerIs6SexIsFAndAgeIsGreaterThan30_thenReturnDanger() {
        int age = 35;
        String gender = "F";
        int triggerCount = 6;
        RiskLevel riskLevel = reportService.calculateRiskLevel(age, gender, triggerCount);
        Assertions.assertEquals(RiskLevel.IN_DANGER, riskLevel);
    }

    @Test
    void testCalculateRiskLevel_whenTriggerIs6SexIsMAndAgeIsGreaterThan30_thenReturnDanger() {
        int age = 35;
        String gender = "M";
        int triggerCount = 6;
        RiskLevel riskLevel = reportService.calculateRiskLevel(age, gender, triggerCount);
        Assertions.assertEquals(RiskLevel.IN_DANGER, riskLevel);
    }

    @Test
    void testCalculateRiskLevel_whenTriggerIs6SexIsMAndAgeIsLessThan30_thenReturnEarlyOnset() {
        int age = 20;
        String gender = "M";
        int triggerCount = 5;
        RiskLevel riskLevel = reportService.calculateRiskLevel(age, gender, triggerCount);
        Assertions.assertEquals(RiskLevel.EARLY_ONSET, riskLevel);
    }

    @Test
    void testCalculateRiskLevel_whenTriggerIs7SexIsFAndAgeIsLessThan30_thenReturnEarlyOnset() {
        int age = 20;
        String gender = "F";
        int triggerCount = 7;
        RiskLevel riskLevel = reportService.calculateRiskLevel(age, gender, triggerCount);
        Assertions.assertEquals(RiskLevel.EARLY_ONSET, riskLevel);
    }

    @Test
    void testCalculateRiskLevel_whenTriggerIs8SexIsFAndAgeIsGreaterThan30_thenReturnEarlyOnset() {
        int age = 35;
        String gender = "F";
        int triggerCount = 8;
        RiskLevel riskLevel = reportService.calculateRiskLevel(age, gender, triggerCount);
        Assertions.assertEquals(RiskLevel.EARLY_ONSET, riskLevel);
    }

    @Test
    void testCalculateRiskLevel_whenTriggerIs8SexIsMAndAgeIsGreaterThan30_thenReturnEarlyOnset() {
        int age = 35;
        String gender = "M";
        int triggerCount = 8;
        RiskLevel riskLevel = reportService.calculateRiskLevel(age, gender, triggerCount);
        Assertions.assertEquals(RiskLevel.EARLY_ONSET, riskLevel);
    }

    @Test
    void testCalculateRiskLevel_whenTriggerIs15SexIsFAndAgeIsGreaterThan30_thenReturnEarlyOnset() {
        int age = 35;
        String gender = "F";
        int triggerCount = 15;
        RiskLevel riskLevel = reportService.calculateRiskLevel(age, gender, triggerCount);
        Assertions.assertEquals(RiskLevel.EARLY_ONSET, riskLevel);
    }

    @Test
    void testCalculateRiskLevel_whenTriggerIs15SexIsMAndAgeIsGreaterThan30_thenReturnEarlyOnset() {
        int age = 35;
        String gender = "M";
        int triggerCount = 15;
        RiskLevel riskLevel = reportService.calculateRiskLevel(age, gender, triggerCount);
        Assertions.assertEquals(RiskLevel.EARLY_ONSET, riskLevel);
    }

    @Test
    void testGenerateReport() {
        Patient patient = new Patient();
        patient.setGiven("Test");
        patient.setFamily("test");
        patient.setDob(Date.valueOf(LocalDate.now()));
        patient.setSex("M");
        patient.setAddress("AddressTest");
        int age = 40;
        RiskLevel riskLevel = RiskLevel.EARLY_ONSET;
        Report report = reportService.generateReport(patient, age, riskLevel);
        Assertions.assertEquals("test", report.getFamily());
        Assertions.assertEquals("Test", report.getGiven());
        Assertions.assertEquals(40, report.getAge());
        Assertions.assertEquals("EARLY_ONSET", report.getAssessment());
    }
}