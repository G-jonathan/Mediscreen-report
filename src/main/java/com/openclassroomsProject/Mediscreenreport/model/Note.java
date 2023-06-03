package com.openclassroomsProject.Mediscreenreport.model;

/**
 * Note object.
 * Contains information about a note's ID, patient ID, and comment.
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */
public class Note {

    private String id;
    private Integer patientId;
    private String comment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id='" + id + '\'' +
                ", patientId=" + patientId +
                ", comment='" + comment + '\'' +
                '}';
    }
}