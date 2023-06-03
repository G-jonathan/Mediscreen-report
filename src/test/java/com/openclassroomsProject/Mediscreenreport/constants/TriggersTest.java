package com.openclassroomsProject.Mediscreenreport.constants;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;

/**
 * Unit tests for the Trigger class.
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */
class TriggersTest {

    @Test
    void testGetTriggers() {
        Triggers triggers = new Triggers();
        List<String> triggerList = triggers.getTriggers();
        Assertions.assertEquals(22, triggerList.size());
        Assertions.assertTrue(triggerList.contains("hémoglobine"));
        Assertions.assertTrue(triggerList.contains("microalbumine"));
        Assertions.assertTrue(triggerList.contains("taille"));
        Assertions.assertTrue(triggerList.contains("poids"));
        Assertions.assertTrue(triggerList.contains("fumeur"));
        Assertions.assertTrue(triggerList.contains("anormal"));
        Assertions.assertTrue(triggerList.contains("cholestérol"));
        Assertions.assertTrue(triggerList.contains("vertige"));
        Assertions.assertTrue(triggerList.contains("rechute"));
        Assertions.assertTrue(triggerList.contains("réaction"));
        Assertions.assertTrue(triggerList.contains("anticorps"));
        Assertions.assertTrue(triggerList.contains("hemoglobin"));
        Assertions.assertTrue(triggerList.contains("microalbumin"));
        Assertions.assertTrue(triggerList.contains("height"));
        Assertions.assertTrue(triggerList.contains("weight"));
        Assertions.assertTrue(triggerList.contains("smoker"));
        Assertions.assertTrue(triggerList.contains("abnormal"));
        Assertions.assertTrue(triggerList.contains("cholesterol"));
        Assertions.assertTrue(triggerList.contains("dizziness"));
        Assertions.assertTrue(triggerList.contains("relapse"));
        Assertions.assertTrue(triggerList.contains("reaction"));
        Assertions.assertTrue(triggerList.contains("antibodies"));
    }
}