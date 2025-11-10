package it.unibo.deathnote.impl;

import java.util.HashMap;
import java.util.Map;

import it.unibo.deathnote.api.DeathNote;

public class DeathNoteImpl implements DeathNote {
    private static final long MAX_CAUSE_TIME_MILLIS = 40;
    private static final long MAX_DEATH_DETAILS_MILLIS = 6040;

    private long lastNameTime = 0L;
    private String lastNameWritten = null;

    private static class DeathStatistics {
        String cause = "heart attack";
        String details = "";
        long timeNameWritten;
        long timeCauseWritten;
    }

    private final Map<String, DeathStatistics> humansToKill = new HashMap<>();

    @Override
    public String getRule(final int ruleNumber) {
        if (ruleNumber < 1 || ruleNumber > RULES.size()) {
            throw new IllegalArgumentException(
                    "Rule Number is not correct. It should be within one and the number of the rules");
        }

        return RULES.get(ruleNumber - 1);
    }

    @Override
    public void writeName(final String name) {
        if (name == null) {
            throw new NullPointerException("The name given is null");
        }

        final DeathStatistics deathStatistics = new DeathStatistics();
        deathStatistics.timeNameWritten = System.currentTimeMillis();
        humansToKill.put(name, deathStatistics);
        lastNameWritten = name;
        lastNameTime = deathStatistics.timeNameWritten;

    }

    @Override
    public boolean writeDeathCause(String cause) {
        if (lastNameWritten == null) {
            throw new IllegalStateException("No name written");
        }

        if (cause == null) {
            throw new IllegalStateException("No cause specified");
        }

        final long now = System.currentTimeMillis();
        DeathStatistics deathStatistics = humansToKill.get(lastNameWritten);
        deathStatistics.cause = cause;
        deathStatistics.timeCauseWritten = now;
        return (now - this.lastNameTime) <= MAX_CAUSE_TIME_MILLIS;
    }

    @Override
    public boolean writeDetails(String details) {
        if (details == null) {
            throw new IllegalStateException("Details are null");
        }

        if (lastNameWritten == null) {
            throw new IllegalStateException("No name in this deathnote");
        }

        DeathStatistics deathStatistics = humansToKill.get(lastNameWritten);
        final long now = System.currentTimeMillis();
        deathStatistics.details = details;
        return (now - deathStatistics.timeCauseWritten) <= MAX_DEATH_DETAILS_MILLIS;
    }

    @Override
    public String getDeathCause(String name) {
        if (!humansToKill.containsKey(name)) {
            throw new IllegalArgumentException("The name provided is not in this deathnote");
        }

        return humansToKill.get(name).cause;
    }

    @Override
    public String getDeathDetails(String name) {
        if (!humansToKill.containsKey(name)) {
            throw new IllegalArgumentException("The name is not in this deathnote");
        }

        return humansToKill.get(name).details;
    }

    @Override
    public boolean isNameWritten(String name) {
        return humansToKill.containsKey(name);
    }

}
