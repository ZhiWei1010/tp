package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents Events for a Patient
 */
public class Event implements Comparable<Event> {
    public static final String NAME_MESSAGE_CONSTAINTS = "The Name of the Event should be alphanumerical and non-empty";
    public static final String DATETIME_MESSAGE_CONSTRAINTS = "Dates should be in the format: DD-MM-YYYY, "
            + "HH:mm - HH:mm, where the End Time is after or equal to the Start Time, \n"
            + "OR if there is no time period, in the format: DD-MM-YYYY \n"
            + "Dates and Times should also be valid, i.e., 12-12-2023, 00:00 - 24:00 is not valid due to '24:00'";
    public static final String NAME_PATTERN = "^.*[^a-zA-Z0-9 ].*$";
    public static final String DATE_PATTERN = "dd-MM-yyyy";
    public static final String TIME_PATTERN = "HH:mm";



    /** The name of the Event */
    public final String name;
    /** The Date of the Event */
    public final String date;
    /** The Start Time of the Event, null if there is no specific start time */
    public final String startTime;
    /** The End Time of the Event, null if there is no specific end time */
    public final String endTime;


    /**
     * Constructs a {@Code Event}
     *
     * @param event
     */
    public Event(String name, String event) {
        name = name.strip();
        requireNonNull(name);
        checkArgument(isValidEventName(name), DATETIME_MESSAGE_CONSTRAINTS);

        event = event.strip();
        requireNonNull(event);
        checkArgument(isValidEvent(event), DATETIME_MESSAGE_CONSTRAINTS);

        String[] args = extractDateTimeArgs(event);
        this.name = name;
        this.date = args[0];
        this.startTime = args[1];
        this.endTime = args[2];
    }

    /**
     * Returns true if the given string is a valid Event Name String
     *
     * @param test the given string
     * @return true if the {@param test} is valid
     *         false if the {@param test} is not valid
     */
    public static boolean isValidEventName(String test) {
        if (test.isEmpty() || test.matches(NAME_PATTERN)) {
            return false;
        }

        return true;
    }

    /**
     * Returns true if the given string is a valid Date / Datetime String
     *
     * @param test the given string
     * @return true if the {@param test} is valid,
     *         false if the {@param test} is not valid
     */
    public static boolean isValidEvent(String test) {
        String[] args = test.split(",");

        try {
            LocalDate.parse(args[0].trim(), DateTimeFormatter.ofPattern(DATE_PATTERN));
        } catch (DateTimeParseException e) {
            return false;
        }

        if (args.length > 1) {
            return isValidDateTimeStr(args[1]);
        }

        return true;
    }


    /**
     * Returns true if the given string is a valid time String
     *
     * @param timeStr the given string
     * @return true if the {@param timeStr} is valid,
     *         false if the {@param timeStr} is not valid
     */
    public static boolean isValidDateTimeStr(String timeStr) {
        String[] args = timeStr.split("-");

        if (args.length != 2) {
            return false;
        }

        try {
            LocalTime start = LocalTime.parse(args[0].trim(), DateTimeFormatter.ofPattern(TIME_PATTERN)); // start time
            LocalTime end = LocalTime.parse(args[1].trim(), DateTimeFormatter.ofPattern(TIME_PATTERN)); // end time

            if (end.isBefore(start)) {
                return false;
            }

            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Returns True if the Date / DateTime of the Event is before the current Date / Datetime
     *
     * @return True if the Date / DateTime is before the current Date / Datetime
     *         False otherwise
     */
    public boolean isPastEvent() {
        LocalDate eventDate = LocalDate.parse(this.date, DateTimeFormatter.ofPattern(DATE_PATTERN));
        LocalDate curDate = LocalDate.now();

        if (eventDate.isBefore(curDate)) {
            return true;
        } else if (eventDate.equals(curDate) && this.endTime != null) {
            return LocalTime.parse(this.endTime, DateTimeFormatter.ofPattern(TIME_PATTERN)).isBefore(LocalTime.now());
        }

        return false;
    }

    /**
     * Extracts the date and/or time arguments from the user input
     *
     * @param userInput
     * @return a String[] of the arguments extracted
     */
    public static String[] extractDateTimeArgs(String userInput) {
        String[] args = userInput.split(",");
        args[0] = args[0].strip();

        String[] temp = new String[2];
        if (args.length > 1) {
            temp = args[1].split("-");
            temp[0] = temp[0].strip();
            temp[1] = temp[1].strip();
        } else {
            temp[0] = null;
            temp[1] = null;
        }

        return new String[] {args[0], temp[0], temp[1]};
    }


    @Override
    public int compareTo(Event other) {
        if (!this.date.equals(other.date)) {
            return this.date.compareTo(other.date);
        }

        if (this.startTime != null && other.startTime != null) {
            // If both events have time information, sort by start time then end time
            if (!this.startTime.equals(other.startTime)) {
                return this.startTime.compareTo(other.startTime);
            }

            if (!this.endTime.equals(other.endTime)) {
                return this.endTime.compareTo(other.endTime);
            }
        }
        // If only 1 event has time information, that event should be sorted behind
        if (this.startTime != null && other.startTime == null) {
            return 1;
        }
        if (this.startTime == null && other.startTime != null) {
            return -1;
        }

        // If all date / datetime information is the same, sort by name
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString() {
        // If there is a start time, there must be a end time
        if (this.startTime != null) {
            return String.format("%s (%s, from %s to %s)", this.name, this.date, this.startTime, this.endTime);
        }

        return String.format("%s (%s)", this.name, this.date);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        if (this.startTime == null) {
            return this.name.equals(otherEvent.name)
                    && this.date.equals(otherEvent.date);
        }

        return this.name.equals(otherEvent.name)
                && this.date.equals(otherEvent.date)
                && this.startTime.equals(otherEvent.startTime)
                && this.endTime.equals(otherEvent.endTime);
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }
}
