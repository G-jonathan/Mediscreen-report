package com.openclassroomsProject.Mediscreenreport.constants;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the RiskLevel enum class.
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */
class RiskLevelTest {

    @Test
    void testEnumValues() {
        Assertions.assertEquals(4, RiskLevel.values().length);
        Assertions.assertEquals(RiskLevel.NONE, RiskLevel.valueOf("NONE"));
        Assertions.assertEquals(RiskLevel.BORDERLINE, RiskLevel.valueOf("BORDERLINE"));
        Assertions.assertEquals(RiskLevel.IN_DANGER, RiskLevel.valueOf("IN_DANGER"));
        Assertions.assertEquals(RiskLevel.EARLY_ONSET, RiskLevel.valueOf("EARLY_ONSET"));
    }
}