package com.openclassroomsProject.Mediscreenreport.model;

import java.util.List;

/**
 * ReportRequest object
 * Contains a list of notes and information about a patient.
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */
public class ReportRequest {
    private List<Note> noteList;
    private Patient patient;

    public List<Note> getNoteList() {
        return noteList;
    }

    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public String toString() {
        return "ReportRequest{" +
                "noteList=" + noteList +
                ", patient=" + patient +
                '}';
    }
}