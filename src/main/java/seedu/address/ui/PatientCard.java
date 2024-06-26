package seedu.address.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.patient.Event;
import seedu.address.model.patient.FamilyCondition;
import seedu.address.model.patient.FoodPreference;
import seedu.address.model.patient.Hobby;
import seedu.address.model.patient.Patient;

/**
 * An UI component that displays information of a {@code Patient}.
 */
public class PatientCard extends UiPart<Region> {

    private static final String FXML = "PatientListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Patient patient;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label patientHospitalId;
    @FXML
    private Label preferredName;
    @FXML
    private VBox foodPreferences;
    @FXML
    private VBox familyConditions;
    @FXML
    private VBox hobbies;
    @FXML
    private FlowPane tags;
    @FXML
    private VBox events;

    /**
     * Creates a {@code PatientCode} with the given {@code Patient} and index to display.
     */
    public PatientCard(Patient patient, int displayedIndex) {
        super(FXML);
        this.patient = patient;
        id.setText(displayedIndex + ". ");
        name.setText(patient.getName().fullName);
        preferredName.setText(patient.getPreferredName().preferredName);

        ArrayList<FoodPreference> allFoodPreferences = new ArrayList<>(patient.getFoodPreferences());
        Collections.sort(allFoodPreferences);
        for (int i = 1; i <= allFoodPreferences.size(); i++) {
            Label foodPreferenceLabel = new Label("\u2981 " + allFoodPreferences.get(i - 1).toString());
            foodPreferenceLabel.setWrapText(true);
            foodPreferences.getChildren().add(foodPreferenceLabel);
        }

        ArrayList<FamilyCondition> allFamilyConditions = new ArrayList<>(patient.getFamilyConditions());
        Collections.sort(allFamilyConditions);
        for (int i = 1; i <= allFamilyConditions.size(); i++) {
            Label familyConditionLabel = new Label("\u2981 " + allFamilyConditions.get(i - 1).toString());
            familyConditionLabel.setWrapText(true);
            familyConditions.getChildren().add(familyConditionLabel);
        }

        ArrayList<Hobby> allHobbies = new ArrayList<>(patient.getHobbies());
        Collections.sort(allHobbies);
        for (int i = 1; i <= allHobbies.size(); i++) {
            Label hobbyLabel = new Label("\u2981 " + allHobbies.get(i - 1).toString());
            hobbyLabel.setWrapText(true);
            hobbies.getChildren().add(hobbyLabel);
        }

        patient.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        if (patient.getEvents().size() >= 1) {
            events.getChildren().add(new Label("Events:\n"));

            ArrayList<Event> allEvents = new ArrayList<>(patient.getEvents());
            Collections.sort(allEvents);
            for (int i = 1; i <= allEvents.size(); i++) {
                events.getChildren().add(new Label((i) + ". "
                        + allEvents.get(i - 1).toString() + "\n"));
            }
        }
    }
}
