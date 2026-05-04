package br.com.hms.domain.enums;

/**
 * Manchester Triage Protocol urgency levels.
 * Lower ordinal = higher urgency.
 */
public enum UrgencyLevel {
    RED,      // Immediate – resuscitation
    ORANGE,   // Very urgent – ≤ 10 min
    YELLOW,   // Urgent – ≤ 60 min
    GREEN,    // Standard – ≤ 120 min
    BLUE      // Non-urgent – ≤ 240 min
}
