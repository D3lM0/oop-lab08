package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;

class TestDeathNote {
    private DeathNote deathNote;

    @BeforeEach
    void setUp() {
        deathNote = new DeathNoteImpl();
    }

    @Test
    void testInvalidRuleThrowException() {
        try {
            deathNote.getRule(0);
            fail("Expected an exception for rule 0");
        } catch (final Exception e) {
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank());
        }

        try {
            deathNote.getRule(-1);
            fail("Expected an exception for negative rule");
        } catch (final Exception e) {
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank());
        }
    }

    @Test
    void testNoRuleIsEmptyOrNull() {
        int rulesNumber = DeathNote.RULES.size();

        for (int i = 1; i <= rulesNumber; i++) {
            String rule = deathNote.getRule(i);
            assertNotNull(rule, "Rule " + i + "should not be null");
            assertFalse(rule.isBlank(), "Rule " + i + "should not be blank");
        }
    }

    @Test
    void testWritingNameInDeathNote() {
        String human1 = "Matteo Del Moro";
        String human2 = "Light Yagami";
        String empty = "";

        assertFalse(deathNote.isNameWritten(human1));
        deathNote.writeName(human1);
        assertTrue(deathNote.isNameWritten(human1));
        assertFalse(deathNote.isNameWritten(human2));
        assertFalse(deathNote.isNameWritten(empty));
    }

    @Test
    void testCauseOfDeath() throws InterruptedException {
        try {
            deathNote.writeDeathCause("heart attack");
            fail("Expected exception when writing death cause before the name");
        } catch (IllegalStateException e) {
            assertNotNull(e.getMessage());
        }

        deathNote.writeName("Misa Amane");
        assertEquals("heart attack", deathNote.getDeathCause("Misa Amane"));

        deathNote.writeName("L Lawliet");
        boolean causeWritten = deathNote.writeDeathCause("karting accident");
        assertTrue(causeWritten);
        assertEquals("karting accident", deathNote.getDeathCause("L Lawliet"));

        Thread.sleep(100);

        boolean newCause = deathNote.writeDeathCause("poisoning");
        assertFalse(newCause);
        assertEquals("karting accident", deathNote.getDeathCause("L Lawliet"));

    }

    @Test
    void testWritingDeathDetails() {
        try {
            deathNote.writeDetails("stabbed 41 times in the heart");
            fail("Excepted exception when writing details before writing the name");
        } catch (IllegalStateException e) {
            assertNotNull(e.getMessage());
        }

        deathNote.writeName("Near");

        assertEquals("", deathNote.getDeathDetails("Near"));

        deathNote.writeDeathCause("respiratory arrest");

        boolean detailsWritten = deathNote.writeDetails("ran for too long");
        assertTrue(detailsWritten);
        assertEquals("ran for too long", deathNote.getDeathDetails("Near"));

        deathNote.writeName("Mello");

        try {
            Thread.sleep(6100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        boolean detailschanged = deathNote.writeDetails("trained too much");
        assertFalse(detailschanged);
    }
}