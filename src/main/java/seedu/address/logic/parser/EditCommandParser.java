package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FAMILY_CONDITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOOD_PREFERENCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOBBY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREFERRED_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.EditPatientDescriptor;
import seedu.address.model.patient.FamilyCondition;
import seedu.address.model.patient.FoodPreference;
import seedu.address.model.patient.Hobby;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {
    private static final Logger logger = LogsCenter.getLogger(EditCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        logger.log(Level.INFO, "Received arguments: " + args + " for EditCommand; Attempting to parse..");
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PID, PREFIX_NAME, PREFIX_PREFERRED_NAME, PREFIX_FOOD_PREFERENCE,
                    PREFIX_FAMILY_CONDITION, PREFIX_HOBBY, PREFIX_TAG);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PID, PREFIX_NAME, PREFIX_PREFERRED_NAME);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }
        logger.log(Level.INFO, "Patient Index is valid.");

        EditPatientDescriptor editPatientDescriptor = new EditPatientDescriptor();

        if (argMultimap.getValue(PREFIX_PID).isPresent()) {
            editPatientDescriptor.setPatientHospitalId(ParserUtil.parsePatientHospitalId(
                argMultimap.getValue(PREFIX_PID).get()));
        }
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPatientDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PREFERRED_NAME).isPresent()) {
            editPatientDescriptor.setPreferredName(ParserUtil.parsePreferredName(
                argMultimap.getValue(PREFIX_PREFERRED_NAME).get()));
        }

        parseFoodPreferenceForEdit(argMultimap.getAllValues(PREFIX_FOOD_PREFERENCE)).ifPresent(
            editPatientDescriptor::setFoodPreferences);

        parseFamilyConditionForEdit(argMultimap.getAllValues(PREFIX_FAMILY_CONDITION)).ifPresent(
            editPatientDescriptor::setFamilyConditions);

        parseHobbyForEdit(argMultimap.getAllValues(PREFIX_HOBBY)).ifPresent(editPatientDescriptor::setHobbies);

        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPatientDescriptor::setTags);

        if (!editPatientDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        logger.log(Level.INFO, "All arguments are valid.");
        return new EditCommand(index, editPatientDescriptor);
    }

    /**
     * Parses {@code Collection<String> foodPreferences} into a {@code Set<FoodPreference>} if {@code foodPreferences}
     * is non-empty.
     * If {@code foodPreferences} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<FoodPreference>} containing zero foodPreference.
     */
    private Optional<Set<FoodPreference>> parseFoodPreferenceForEdit(
        Collection<String> foodPreferences) throws ParseException {
        assert foodPreferences != null;
        if (foodPreferences.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> foodPreferenceSet = foodPreferences.size() == 1 && foodPreferences.contains("")
            ? Collections.emptySet() : foodPreferences;
        return Optional.of(ParserUtil.parseFoodPreferences(foodPreferenceSet));
    }

    /**
     * Parses {@code Collection<String> familyConditions} into a {@code Set<FamilyCondition>}
     * if {@code familyConditions} is non-empty.
     * If {@code familyConditions} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<FamilyCondition>} containing zero familyCondition.
     */
    private Optional<Set<FamilyCondition>> parseFamilyConditionForEdit(
        Collection<String> familyConditions) throws ParseException {
        assert familyConditions != null;
        if (familyConditions.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> familyConditionSet = familyConditions.size() == 1 && familyConditions.contains("")
            ? Collections.emptySet() : familyConditions;
        return Optional.of(ParserUtil.parseFamilyConditions(familyConditionSet));
    }

    /**
     * Parses {@code Collection<String> hobbies} into a {@code Set<Hobby>}
     * if {@code hobbies} is non-empty.
     * If {@code hobbies} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Hobby>} containing zero hobby.
     */
    private Optional<Set<Hobby>> parseHobbyForEdit(Collection<String> hobbies) throws ParseException {
        assert hobbies != null;
        if (hobbies.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> hobbySet = hobbies.size() == 1 && hobbies.contains("")
            ? Collections.emptySet() : hobbies;
        return Optional.of(ParserUtil.parseHobbies(hobbySet));
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
