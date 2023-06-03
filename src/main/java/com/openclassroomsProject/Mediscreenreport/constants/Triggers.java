package com.openclassroomsProject.Mediscreenreport.constants;

import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents a list of triggers used to determine the number of occurrences of keywords in patient notes.
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */
@Component
public class Triggers {

    /**
     * Returns a list of triggers.
     *
     * @return A list of triggers.
     */
    public List<String> getTriggers() {
        return Arrays.asList(
                "hémoglobine", "microalbumine", "taille", "poids", "fumeur",
                "anormal", "cholestérol", "vertige", "rechute", "réaction", "anticorps",
                "hemoglobin", "microalbumin", "height", "weight", "smoker",
                "abnormal", "cholesterol", "dizziness", "relapse", "reaction", "antibodies"
        );
    }
}