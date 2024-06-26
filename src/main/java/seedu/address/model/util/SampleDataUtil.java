package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.patient.Event;
import seedu.address.model.patient.FamilyCondition;
import seedu.address.model.patient.FoodPreference;
import seedu.address.model.patient.Hobby;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.PatientHospitalId;
import seedu.address.model.patient.PreferredName;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Patient[] getSamplePatients() {
        return new Patient[]{
            new Patient(new PatientHospitalId("12345"),
                new Name("Alex Yeoh Jia Jun"),
                new PreferredName("Alex"),
                getFoodPreferenceSet("Chicken rice"),
                getFamilyConditionSet("Stable ", "Has 2 sons that visits him regularly"),
                getHobbySet("Singing karaoke"),
                getTagSet("diabetes"),
                getEventSet(new String[]{"Birthday celebration"}, new String[]{"20-12-2024"})),
            new Patient(new PatientHospitalId("12346"),
                new Name("Bernice Yu Sheng Huat"),
                new PreferredName("Bern"),
                getFoodPreferenceSet("Char Kuay Tiao", "Black Carrot Cake"),
                getFamilyConditionSet("Lives alone", "no family members around"),
                getHobbySet("Playing Mahjong"),
                getTagSet("high blood pressure"),
                getEventSet(new String[]{"Family Visit"}, new String[]{"30-03-2024, 15:00 - 18:00"})),
            new Patient(new PatientHospitalId("12347"),
                new Name("Mary Jane"),
                new PreferredName("Mary"),
                getFoodPreferenceSet("Korean"),
                getFamilyConditionSet("Lives with only daughter", "quarrels regularly with daughter"),
                getHobbySet("Watching Drama"),
                getTagSet("fall risk")),
            new Patient(new PatientHospitalId("12348"),
                new Name("David Li"),
                new PreferredName("David"),
                getFoodPreferenceSet("Bak Kut Teh"),
                getFamilyConditionSet("Son visits him every weekend"),
                getHobbySet("Plays erhu"),
                getTagSet("diabetes", "skin irritation")),
            new Patient(new PatientHospitalId("12349"),
                new Name("Irfan Ibrahim"),
                new PreferredName("Fan"),
                getFoodPreferenceSet("Roti Prata"),
                getFamilyConditionSet("Children encountered accident 2 months ago"),
                getHobbySet("Plays badminton"),
                getTagSet("tumour")),
            new Patient(new PatientHospitalId("12350"),
                new Name("Roy Balakrishnan"),
                new PreferredName("Rony"),
                getFoodPreferenceSet("Fish Ball Soup"),
                getFamilyConditionSet("Financial problem"),
                getHobbySet("Jog around park"),
                getTagSet("wheelchair")),
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Patient samplePatient : getSamplePatients()) {
            sampleAb.addPatient(samplePatient);
        }
        return sampleAb;
    }

    /**
     * Returns a family condition set containing the list of strings given.
     */
    public static Set<FamilyCondition> getFamilyConditionSet(String... strings) {
        return Arrays.stream(strings)
            .map(FamilyCondition::new)
            .collect(Collectors.toSet());
    }

    /**
     * Returns a food preference set containing the list of strings given.
     */
    public static Set<FoodPreference> getFoodPreferenceSet(String... strings) {
        return Arrays.stream(strings)
            .map(FoodPreference::new)
            .collect(Collectors.toSet());
    }

    /**
     * Returns a hobby set containing the list of strings given.
     */
    public static Set<Hobby> getHobbySet(String... strings) {
        return Arrays.stream(strings)
            .map(Hobby::new)
            .collect(Collectors.toSet());
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a set of Events containing the list of strings given.
     */
    public static Set<Event> getEventSet(String[] names, String[] dates) {
        Event[] events = new Event[names.length];
        for (int i = 0; i < names.length; i++) {
            events[i] = new Event(names[i], dates[i]);
        }

        return Arrays.stream(events).collect(Collectors.toSet());
    }

}
