package com.openclassroomsProject.Mediscreenreport.constants;

/**
 * This class contains regular expressions used for word extraction.
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */
public class Regex {
    /**
     * Regular expression pattern used to extract words.
     * It matches either "Hemoglobin A1C", "Hémoglobine A1C" or any other words joined by spaces, commas, point or two points.
     * Accents are also supported.
     */
    public static final String EXTRACT_WORDS_REGEX = "(?U)(?<=\\s|^)(Hemoglobin A1C|hémoglobine A1C|\\p{L}+)(?=\\s|,|\\.|:|$)";
}