package com.openclassroomsProject.Mediscreenreport.service.impl;

import com.openclassroomsProject.Mediscreenreport.constants.Regex;
import com.openclassroomsProject.Mediscreenreport.controller.ReportController;
import com.openclassroomsProject.Mediscreenreport.model.Note;
import com.openclassroomsProject.Mediscreenreport.model.Patient;
import com.openclassroomsProject.Mediscreenreport.model.Report;
import com.openclassroomsProject.Mediscreenreport.service.IReportService;
import com.openclassroomsProject.Mediscreenreport.constants.RiskLevel;
import com.openclassroomsProject.Mediscreenreport.constants.Triggers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * IReportService interface implementation.
 * Performs operations related to generating a report.
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */
@Service
public class IReportServiceImpl implements IReportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    private Triggers triggers;
    /**
     * Counts the number of triggers in the given list of words.
     *
     * @param wordsList The list of words to count triggers.
     * @return The number of triggers found.
     */
    @Override
    public int countTriggers(List<String> wordsList) {
        LOGGER.info("[SERVICE]-> call method countTriggers() [PARAM]-> wordsList " + wordsList);
        int triggerCount = 0;
        List<String> triggersFoundList = new ArrayList<>();
        for (String word : wordsList) {
            if (triggers.getTriggers().contains(word.toLowerCase())) {
                triggersFoundList.add(word);
                triggerCount++;
            }
        }
        LOGGER.info("[SERVICE]-> triggersFoundList = " + triggersFoundList);
        return triggerCount;
    }

    /**
     * Extracts words from a list of notes.
     *
     * @param noteList The list of notes from which to extract words.
     * @return A list containing the extracted words from the notes.
     */
    @Override
    public List<String> extractWords(List<Note> noteList) {
        LOGGER.info("[SERVICE]-> call method extractWords() [PARAM]-> noteList " + noteList);
        List<String> wordsList = new ArrayList<>();
        Pattern pattern = Pattern.compile(Regex.EXTRACT_WORDS_REGEX);
        for (Note note : noteList) {
            Matcher matcher = pattern.matcher(note.getComment());
            while (matcher.find()) {
                String word = matcher.group();
                if (!word.isEmpty()) {
                    wordsList.add(word);
                }
            }
        }
        return wordsList;
    }

    /**
     * Calculates the age based on the given date of birth.
     * The method transforms the date object into a LocalDate object in order to compare the two LocalDate objects.
     *
     * @param dateOfBirth The date of birth.
     * @return The calculated age.
     */
    @Override
    public int calculateAge(Date dateOfBirth) {
        LOGGER.info("[SERVICE]-> call method calculateAge() [PARAM]-> date " + dateOfBirth);
        LocalDate localDateOfBirth = new Date(dateOfBirth.getTime()).toLocalDate();
        LOGGER.info("[SERVICE]-> call method calculateAge() [PARAM]-> localDateOfBirth " + localDateOfBirth);
        return Period.between(localDateOfBirth, LocalDate.now()).getYears();
    }

    /**
     * Calculates the risk level based on the age, gender, and trigger count.
     * The possible risks are listed in the enum class Risk Level.
     *
     * @param age          The age.
     * @param gender       The gender.
     * @param triggerCount The trigger count.
     * @return The calculated risk level.
     */
    @Override
    public RiskLevel calculateRiskLevel(int age, String gender, int triggerCount) {
        LOGGER.info("[SERVICE]-> call method calculateRiskLevel() [PARAM]-> age " + age + "[PARAM]-> gender " + gender + "[PARAM]-> triggerCount " + triggerCount);
        if (age >= 30) {
            if (triggerCount >= 2 && triggerCount < 6) {
                return RiskLevel.BORDERLINE;
            } else if ((triggerCount >= 6 && triggerCount < 8)) {
                return RiskLevel.IN_DANGER;
            } else if (triggerCount >= 8) {
                return RiskLevel.EARLY_ONSET;
            }
        } else {
            if (gender.equalsIgnoreCase("F")) {
                if (triggerCount >= 4 && triggerCount <= 6) {
                    return RiskLevel.IN_DANGER;
                } else if (triggerCount >= 7) {
                    return RiskLevel.EARLY_ONSET;
                }
            } else if (gender.equalsIgnoreCase("M")) {
                if (triggerCount >= 3 && triggerCount <= 4) {
                    return RiskLevel.IN_DANGER;
                } else if (triggerCount >= 5) {
                    return RiskLevel.EARLY_ONSET;
                }
            }
        }
        return RiskLevel.NONE;
    }

    /**
     * Generates a report based on the given patient, age, and risk level.
     *
     * @param patient   The patient.
     * @param age       The age.
     * @param riskLevel The risk level.
     * @return The generated report.
     */
    @Override
    public Report generateReport(Patient patient, int age, RiskLevel riskLevel) {
        LOGGER.info("[SERVICE]-> call method generateReport() [PARAM]-> patient " + patient + "[PARAM]-> age " + age + "[PARAM]-> riskLevel" + riskLevel);
        return new Report(patient.getFamily(), patient.getGiven(), age, riskLevel.toString());
    }
}