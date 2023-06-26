package Client.controllers;

import Client.controllers.tools.ObservableResourceFactory;
import Client.utility.Outputer;
import Client.utility.OutputerUI;
import common.data.*;
import common.exceptions.MustBeNotEmptyException;
import common.exceptions.NotInDeclaredLimitsException;
import common.interaction.GroupRaw;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Sets the controller window.
 */

public class AskWindowController {

    @FXML
    private Label nameLabel;

    @FXML
    private Label coordinatesXLabel;

    @FXML
    private Label coordinatesYLabel;

    @FXML
    private Label studentsCountLabel;

    @FXML
    private Label transferredStudentsLabel;

    @FXML
    private Label formOfEducationLabel;

    @FXML
    private Label semesterLabel;

    @FXML
    private Label groupAdminNameLabel;

    @FXML
    private Label groupAdminBirthdayLabel;

    @FXML
    private Label groupAdminHeightLabel;

    @FXML
    private Label groupAdminWeightLabel;

    @FXML
    private Label groupAdminPassportIDLabel;

    @FXML
    private TextField nameField;

    @FXML
    private TextField coordinatesXField;

    @FXML
    private TextField coordinatesYField;

    @FXML
    private TextField studentsCountField;

    @FXML
    private TextField transferredStudentsField;

//    @FXML
//    private TextField formOfEducationField;
//
//    @FXML
//    private TextField semesterField;

    @FXML
    private TextField groupAdminNameField;

    @FXML
    private TextField groupAdminBirthdayField;

    @FXML
    private TextField groupAdminHeightField;

    @FXML
    private TextField groupAdminWeightField;

    @FXML
    private TextField groupAdminPassportIDField;

    @FXML
    private ComboBox<FormOfEducation> formOfEducationComboBox;

    @FXML
    private ComboBox<Semester> semesterComboBox;

    @FXML
    private Button enterButton;

    private Stage askStage;

    private GroupRaw resultGroup;

    private ObservableResourceFactory resourceFactory;

    LocalDate groupAdminBirthday;

    /**
     * Initialize ask window.
     */

    public void initialize() {
        formOfEducationComboBox.setItems(FXCollections.observableArrayList(FormOfEducation.values()));
        semesterComboBox.setItems(FXCollections.observableArrayList(Semester.values()));
    }

    /**
     * Enter button on action.
     */

    @FXML
    private void enterButtonOnAction() {
        try {
            resultGroup = new GroupRaw(
                    convertName(),
                    new Coordinates(
                            convertCoordinatesxX(),
                            convertCoordinatesxY()
                    ),
                    convertStudentsCount(),
                    convertTransferredStudents(),
                    formOfEducationComboBox.getValue(),
                    semesterComboBox.getValue(),
                    new Person(
                            convertGroupAdminName(),
                            convertGroupAdminBirthday(),
                            convertGroupAdminHeight(),
                            convertGroupAdminWeight(),
                            convertGroupAdminPassportId()
                    )
            );
            askStage.close();
        } catch (IllegalArgumentException exception) { /* ? */ }
    }

    /**
     * Binds interface language.
     */

    private void bindGuiLanguage() {
        nameLabel.textProperty().bind(resourceFactory.getStringBinding("NameColumn"));
        coordinatesXLabel.textProperty().bind(resourceFactory.getStringBinding("CoordinatesXColumn"));
        coordinatesYLabel.textProperty().bind(resourceFactory.getStringBinding("CoordinatesYColumn"));
        studentsCountLabel.textProperty().bind(resourceFactory.getStringBinding("StudentsCountColumn"));
        transferredStudentsLabel.textProperty().bind(resourceFactory.getStringBinding("TransferredStudentsColumn"));
        groupAdminNameLabel.textProperty().bind(resourceFactory.getStringBinding("GroupAdminNameColumn"));
        groupAdminBirthdayLabel.textProperty().bind(resourceFactory.getStringBinding("GroupAdminBirthdayColumn"));
        groupAdminHeightLabel.textProperty().bind(resourceFactory.getStringBinding("GroupAdminHeightColumn"));
        groupAdminWeightLabel.textProperty().bind(resourceFactory.getStringBinding("GroupAdminWeightColumn"));
        groupAdminPassportIDLabel.textProperty().bind(resourceFactory.getStringBinding("GroupAdminPassportIDColumn"));
        enterButton.textProperty().bind(resourceFactory.getStringBinding("EnterButton"));
    }

    /**
     * Convert name.
     *
     * @return Name.
     */
    private String convertName() throws IllegalArgumentException {
        String name;
        try {
            name = nameField.getText();
            if (name.equals("")) throw new MustBeNotEmptyException();
        } catch (MustBeNotEmptyException exception) {
            OutputerUI.error("NameEmptyException");
            throw new IllegalArgumentException();
        }
        return name;
    }

    /**
     * Convert Coordinates X.
     *
     * @return X.
     */
    private int convertCoordinatesxX() throws IllegalArgumentException {
        String strX;
        int x;
        try {
            strX = coordinatesXField.getText();
            x = Integer.parseInt(strX);
        } catch (NumberFormatException exception) {
            OutputerUI.error("CoordinatesXFormatException");
            throw new IllegalArgumentException();
        }
        return x;
    }

    /**
     * Convert Coordinates Y.
     *
     * @return Y.
     */
    private Long convertCoordinatesxY() throws IllegalArgumentException {
        String strY;
        Long y;
        try {
            strY = coordinatesYField.getText();
            y = Long.parseLong(strY);
        } catch (NumberFormatException exception) {
            OutputerUI.error("CoordinatesYFormatException");
            throw new IllegalArgumentException();
        }
        return y;
    }

    private Long convertStudentsCount() throws IllegalArgumentException {
        String strStudentsCount;
        Long studentsCount;
        try {
            strStudentsCount = studentsCountField.getText();
            studentsCount = Long.parseLong(strStudentsCount);
            if (studentsCount <= 0) throw new NotInDeclaredLimitsException();
        } catch (NumberFormatException exception) {
            OutputerUI.error("StudentsCountFormatException");
            throw new IllegalArgumentException();
        } catch (NotInDeclaredLimitsException exception) {
            OutputerUI.error("Number of student can not be negative");
            throw new IllegalArgumentException();
        }
        return studentsCount;
    }

    private int convertTransferredStudents() throws IllegalArgumentException {
        String strTransferredStudents;
        int transferredStudents;
        try {
            strTransferredStudents = transferredStudentsField.getText();
            transferredStudents = Integer.parseInt(strTransferredStudents);
            if (transferredStudents <= 0) throw new NotInDeclaredLimitsException();
        } catch (NumberFormatException exception) {
            OutputerUI.error("transferredStudentsFormatException");
            throw new IllegalArgumentException();
        } catch (NotInDeclaredLimitsException exception) {
            OutputerUI.error("Number of transferred students can not be negative");
            throw new IllegalArgumentException();
        }
        return transferredStudents;
    }

    private String convertGroupAdminName() throws IllegalArgumentException {
        String groupAdminName;
        try {
            groupAdminName = groupAdminNameField.getText();
            if (groupAdminName.equals("")) throw new MustBeNotEmptyException();
        } catch (MustBeNotEmptyException exception) {
            OutputerUI.error("GroupNameEmptyException");
            throw new IllegalArgumentException();
        }
        return groupAdminName;
    }

    private LocalDate convertGroupAdminBirthday() throws IllegalArgumentException {
        String strGroupAdminBirthday;
        try {
            strGroupAdminBirthday = groupAdminBirthdayField.getText();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            groupAdminBirthday = LocalDate.parse(strGroupAdminBirthday, formatter);
        } catch (DateTimeParseException exception) {
            Outputer.println(exception.getMessage());
            OutputerUI.error("DateTimeParseException");
        } catch (NullPointerException exception) {
            Outputer.println(exception.getMessage());
            OutputerUI.error("NullPointerException");
        } catch (IllegalArgumentException exception) {
            Outputer.println(exception.getMessage());
            OutputerUI.error("IllegalArgumentException");
        }
        return groupAdminBirthday;
    }


    private Long convertGroupAdminHeight() throws IllegalArgumentException {
        String strGroupAdminHeight;
        Long groupAdminHeight;
        try {
            strGroupAdminHeight = groupAdminHeightField.getText();
            if (strGroupAdminHeight.equals("")) throw new MustBeNotEmptyException();
            groupAdminHeight = Long.parseLong(strGroupAdminHeight);
        } catch (MustBeNotEmptyException exception) {
            OutputerUI.error("GroupAdminHeightEmptyException");
            throw new IllegalArgumentException();
        }
        return groupAdminHeight;
    }

    private Double convertGroupAdminWeight() throws IllegalArgumentException {
        String strGroupAdminWeight;
        Double groupAdminWeight;
        try {
            strGroupAdminWeight = groupAdminWeightField.getText();
            if (strGroupAdminWeight.equals("")) throw new MustBeNotEmptyException();
            groupAdminWeight = Double.parseDouble(strGroupAdminWeight);
        } catch (MustBeNotEmptyException exception) {
            OutputerUI.error("GroupAdminWeightEmptyException");
            throw new IllegalArgumentException();
        }
        return groupAdminWeight;
    }

    private String convertGroupAdminPassportId() throws IllegalArgumentException {
        String strGroupAdminPassportId;
        try {
            strGroupAdminPassportId = groupAdminPassportIDField.getText();
            if (strGroupAdminPassportId.equals("")) throw new MustBeNotEmptyException();
        } catch (MustBeNotEmptyException exception) {
            OutputerUI.error("GroupAdminPassportIdEmptyException");
            throw new IllegalArgumentException();
        }
        return strGroupAdminPassportId;
    }



    /**
     * Set Group.
     *
     * @param group Group to set.
     */
    public void setGroup(StudyGroup group) {
        nameField.setText(group.getName());
        coordinatesXField.setText(group.getCoordinates().getX() + "");
        coordinatesYField.setText(group.getCoordinates().getY() + "");
        studentsCountField.setText(group.getStudentsCount() + "");
        transferredStudentsField.setText(group.getTransferredStudents() + "");
        formOfEducationComboBox.setValue(group.getFormOfEducation());
        semesterComboBox.setValue(group.getSemesterEnum());
        groupAdminNameField.setText(group.getGroupAdmin().getName());
        groupAdminBirthdayField.setText(group.getGroupAdmin().getBirthday() + "");
        groupAdminHeightField.setText(group.getGroupAdmin().getHeight() + "");
        groupAdminWeightField.setText(group.getGroupAdmin().getWeight() + "");
        groupAdminPassportIDField.setText(group.getGroupAdmin().getPassportID());
    }

    /**
     * Clear Group.
     */

    public void clearGroup() {
        nameField.clear();
        coordinatesXField.clear();
        coordinatesYField.clear();
        studentsCountField.clear();
        transferredStudentsField.clear();
        formOfEducationComboBox.setValue(FormOfEducation.DISTANCE_EDUCATION);
        semesterComboBox.setValue(Semester.SIXTH);
        groupAdminNameField.clear();
        groupAdminBirthdayField.clear();
        groupAdminHeightField.clear();
        groupAdminWeightField.clear();
        groupAdminPassportIDField.clear();
    }

    /**
     * Get and clear Group.
     *
     * @return Group to return.
     */

    public GroupRaw getAndClear() {
        GroupRaw groupToReturn = resultGroup;
        resultGroup = null;
        return groupToReturn;
    }

    public GroupRaw getResultGroup() {
        GroupRaw groupRaw = resultGroup;
        return groupRaw;
    }

    public void setAskStage(Stage askStage) {
        this.askStage = askStage;
    }

    /**
     * Init langs.
     *
     * @param resourceFactory Resource factory to set.
     */

    public void initLangs(ObservableResourceFactory resourceFactory) {
        this.resourceFactory = resourceFactory;
        bindGuiLanguage();
    }

}
