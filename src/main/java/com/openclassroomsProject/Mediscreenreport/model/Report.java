package com.openclassroomsProject.Mediscreenreport.model;

/**
 *  Report object
 *  It contains information about a patient's family name, given name, age, and assessment.
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */
public class Report {

    private String family;
    private String given;
    private int age;
    private String assessment;

    /**
     * Constructs a new Report instance with the specified attributes.
     *
     * @param family     The family name of the patient.
     * @param given      The given name of the patient.
     * @param age        The age of the patient.
     * @param assessment The assessment result for the patient.
     */
    public Report(String family, String given, int age, String assessment) {
        this.family = family;
        this.given = given;
        this.age = age;
        this.assessment = assessment;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getGiven() {
        return given;
    }

    public void setGiven(String given) {
        this.given = given;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }

    @Override
    public String toString() {
        return "Report{" +
                "family='" + family + '\'' +
                ", given='" + given + '\'' +
                ", age=" + age +
                ", assessment='" + assessment + '\'' +
                '}';
    }
}