package Client.controllers;

import Client.Client;
import Client.App;
import Client.controllers.tools.ObservableResourceFactory;
import Client.utility.Outputer;
import Client.utility.OutputerUI;
import common.data.FormOfEducation;
import common.data.Semester;
import common.data.StudyGroup;
import common.interaction.GroupRaw;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.table.TableFilter;


import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Main window controller.
 */

public class MainWindowController {

    public static final String LOGIN_COMMAND_NAME = "login";

    public static final String REGISTER_COMMAND_NAME = "register";

    public static final String REFRESH_COMMAND_NAME = "refresh";

    public static final String INFO_COMMAND_NAME = "info";

    public static final String ADD_COMMAND_NAME = "add";

    public static final String UPDATE_COMMAND_NAME = "update";

    public static final String REMOVE_COMMAND_NAME = "remove_by_id";

    public static final String CLEAR_COMMAND_NAME = "clear";

    public static final String EXIT_COMMAND_NAME = "exit";

    public static final String HISTORY_COMMAND_NAME = "history";

    public static final String GROUP_COUNTING_BY_COORDINATES_COMMAND_NAME = "group_counting_by_coordinates";

    private static final String SORT_COMMAND_NAME = "sort";

    public static final String MIN_SEMESTER_COMMAND_NAME = "min_by_semester_enum";

    public static final String REMOVE_AT_INDEX_COMMAND_NAME = "remove_at_index";

    public static final String SUM_OF_TRANSFERRED_STUDENTS_COMMAND_NAME = "sum_of_transferred_students";

    private final long RANDOM_SEED = 1821L;

    private final Duration ANIMATION_DURATION = Duration.millis(800);

    private final double MAX_SIZE = 250;

    @FXML
    private TableView<StudyGroup> studyGroupTable;

    @FXML
    private TableColumn<StudyGroup , Long> idColumn;

    @FXML
    private TableColumn<StudyGroup, String> ownerColumn;

    @FXML
    private TableColumn<StudyGroup, LocalDateTime> creationDateColumn;

    @FXML
    private TableColumn<StudyGroup, String> nameColumn;

    @FXML
    private TableColumn<StudyGroup, Integer> coordinatesXColumn;

    @FXML
    private TableColumn<StudyGroup, Long> coordinatesYColumn;

    @FXML
    private TableColumn<StudyGroup, Long> studentsCountColumn;

    @FXML
    private TableColumn<StudyGroup, Integer> transferredStudentsColumn;

    @FXML
    private TableColumn<StudyGroup, FormOfEducation> formOfEducationTableColumn;

    @FXML
    private TableColumn<StudyGroup, Semester> semesterTableColumn;

    @FXML
    private TableColumn<StudyGroup, String> groupAdminNameColumn;

    @FXML
    private TableColumn<StudyGroup, String> groupAdminBirthdayColumn;

    @FXML
    private TableColumn<StudyGroup, Long> groupAdminHeightColumn;

    @FXML
    private TableColumn<StudyGroup, Double> groupAdminWeightColumn;

    @FXML
    private TableColumn<StudyGroup, String> groupAdminPassportIDColumn;

    @FXML
    private AnchorPane canvasPane;

    @FXML
    private Tab tableTab;

    @FXML
    private Tab canvasTab;

    @FXML
    private Button infoButton;

    @FXML
    private Button addButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button removeButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button executeScriptButton;

    @FXML
    private Button historyButton;

    @FXML
    private Button refreshButton;

    @FXML
    private Button groupCountingByCoordinatesButton;

    @FXML
    private Button minBySemesterEnumButton;

    @FXML
    private Button removeAtIndexButton;

    @FXML
    private Button sumOfTransferredStudentsButton;

    @FXML
    private Button sortButton;

    @FXML
    private Tooltip infoButtonTooltip;

    @FXML
    private Tooltip addButtonTooltip;

    @FXML
    private Tooltip updateButtonTooltip;

    @FXML
    private Tooltip removeButtonTooltip;

    @FXML
    private Tooltip clearButtonTooltip;

    @FXML
    private Tooltip executeScriptButtonTooltip;

    @FXML
    private Tooltip historyButtonTooltip;

    @FXML
    private Tooltip refreshButtonTooltip;

    @FXML
    private Tooltip groupCountingByCoordinatesButtonTooltip;

    @FXML
    private Tooltip minBySemesterEnumButtonTooltip;

    @FXML
    private Tooltip removeAtIndexButtonTooltip;

    @FXML
    private Tooltip sumOfTransferredStudentsButtonTooltip;

    @FXML
    private Tooltip sortButtonTooltip;

    @FXML
    private ComboBox<String> languageComboBox;

    @FXML
    private Label usernameLabel;

    private Client client;

    private Stage askStage;

    private Stage primaryStage;
    private FileChooser fileChooser;

    private AskWindowController askWindowController;
    private Map<String, Color> userColorMap;
    private Map<Shape, Long> shapeMap;
    private Map<Long, Text> textMap;

    private Shape prevClicked;

    private Color prevColor;
    private Random randomGenerator;

    private ObservableResourceFactory resourceFactory;
    private Map<String, Locale> localeMap;




    /**
     * Initialize main window.
     */

    public void initialize() {
        initializeTable();
        fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));
        userColorMap = new HashMap<>();
        shapeMap = new HashMap<>();
        textMap = new HashMap<>();
        randomGenerator = new Random(RANDOM_SEED);
        localeMap = new HashMap<>();
        localeMap.put("English", new Locale("en", "NZ"));
        localeMap.put("Русский", new Locale("ru", "RU"));
        localeMap.put("Deutsch", new Locale("de", "DE"));
        localeMap.put("Dansk", new Locale("da", "DK"));
        localeMap.put("Vietnam", new Locale("vi", "VN"));
        languageComboBox.setItems(FXCollections.observableArrayList(localeMap.keySet()));
        if (languageComboBox == null) {
            Outputer.print("Khong co ngon ngu nao");
        }
    }

    /**
     * Initialize table.
     */
    private void initializeTable() {
        idColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        ownerColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getOwner().getUsername()));
        creationDateColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getCreationDate()));
        nameColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getName()));
        coordinatesXColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getCoordinates().getX()));
        coordinatesYColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getCoordinates().getY()));
        studentsCountColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getStudentsCount()));
        transferredStudentsColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getTransferredStudents()));
        formOfEducationTableColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getFormOfEducation()));
        semesterTableColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getSemesterEnum()));
        groupAdminNameColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getGroupAdmin().getName()));
        groupAdminBirthdayColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getGroupAdmin().getBirthDayToString()));
        groupAdminHeightColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getGroupAdmin().getHeight()));
        groupAdminWeightColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getGroupAdmin().getWeight()));
        groupAdminPassportIDColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getGroupAdmin().getPassportID()));
    }

    /**
     * Bind gui language.
     */

    private void bindGuiLanguage() {

        resourceFactory.setResources(ResourceBundle.getBundle
                (App.BUNDLE, localeMap.get(languageComboBox.getSelectionModel().getSelectedItem())));
        idColumn.textProperty().bind(resourceFactory.getStringBinding("IdColumn"));
        ownerColumn.textProperty().bind(resourceFactory.getStringBinding("OwnerColumn"));
        creationDateColumn.textProperty().bind(resourceFactory.getStringBinding("CreationDateColumn"));
        nameColumn.textProperty().bind(resourceFactory.getStringBinding("NameColumn"));
        coordinatesXColumn.textProperty().bind(resourceFactory.getStringBinding("CoordinatesXColumn"));
        coordinatesYColumn.textProperty().bind(resourceFactory.getStringBinding("CoordinatesYColumn"));
        studentsCountColumn.textProperty().bind(resourceFactory.getStringBinding("StudentsCountColumn"));
        transferredStudentsColumn.textProperty().bind(resourceFactory.getStringBinding("TransferredStudentsColumn"));
        formOfEducationTableColumn.textProperty().bind(resourceFactory.getStringBinding("FormOfEducationTableColumn"));
        semesterTableColumn.textProperty().bind(resourceFactory.getStringBinding("SemesterTableColumn"));
        groupAdminNameColumn.textProperty().bind(resourceFactory.getStringBinding("GroupAdminNameColumn"));
        groupAdminBirthdayColumn.textProperty().bind(resourceFactory.getStringBinding("GroupAdminBirthdayColumn"));
        groupAdminHeightColumn.textProperty().bind(resourceFactory.getStringBinding("GroupAdminHeightColumn"));
        groupAdminWeightColumn.textProperty().bind(resourceFactory.getStringBinding("GroupAdminWeightColumn"));
        groupAdminPassportIDColumn.textProperty().bind(resourceFactory.getStringBinding("GroupAdminPassportIDColumn"));

        tableTab.textProperty().bind(resourceFactory.getStringBinding("TableTab"));
        canvasTab.textProperty().bind(resourceFactory.getStringBinding("CanvasTab"));

        infoButton.textProperty().bind(resourceFactory.getStringBinding("InfoButton"));
        addButton.textProperty().bind(resourceFactory.getStringBinding("AddButton"));
        updateButton.textProperty().bind(resourceFactory.getStringBinding("UpdateButton"));
        removeButton.textProperty().bind(resourceFactory.getStringBinding("RemoveButton"));
        clearButton.textProperty().bind(resourceFactory.getStringBinding("ClearButton"));
        executeScriptButton.textProperty().bind(resourceFactory.getStringBinding("ExecuteScriptButton"));
        historyButton.textProperty().bind(resourceFactory.getStringBinding("HistoryButton"));
        refreshButton.textProperty().bind(resourceFactory.getStringBinding("RefreshButton"));
        groupCountingByCoordinatesButton.textProperty().bind(resourceFactory.getStringBinding("GroupCountingByCoordinatesButton"));
        minBySemesterEnumButton.textProperty().bind(resourceFactory.getStringBinding("MinBySemesterEnumButton"));
//        removeAtIndexButton.textProperty().bind(resourceFactory.getStringBinding("RemoveAtIndexButton"));
        sumOfTransferredStudentsButton.textProperty().bind(resourceFactory.getStringBinding("SumOfTransferredStudentsButton"));
        sortButton.textProperty().bind(resourceFactory.getStringBinding("SortButton"));

        infoButtonTooltip.textProperty().bind(resourceFactory.getStringBinding("InfoButtonTooltip"));
        addButtonTooltip.textProperty().bind(resourceFactory.getStringBinding("AddButtonTooltip"));
        updateButtonTooltip.textProperty().bind(resourceFactory.getStringBinding("UpdateButtonTooltip"));
        removeButtonTooltip.textProperty().bind(resourceFactory.getStringBinding("RemoveButtonTooltip"));
        clearButtonTooltip.textProperty().bind(resourceFactory.getStringBinding("ClearButtonTooltip"));
        executeScriptButtonTooltip.textProperty().bind(resourceFactory.getStringBinding("ExecuteScriptButtonTooltip"));
        historyButtonTooltip.textProperty().bind(resourceFactory.getStringBinding("HistoryButtonTooltip"));
        refreshButtonTooltip.textProperty().bind(resourceFactory.getStringBinding("RefreshButtonTooltip"));
        groupCountingByCoordinatesButtonTooltip.textProperty().bind(resourceFactory.getStringBinding("GroupCountingByCoordinatesButtonTooltip"));
        minBySemesterEnumButtonTooltip.textProperty().bind(resourceFactory.getStringBinding("MinBySemesterEnumButtonTooltip"));
//        removeAtIndexButtonTooltip.textProperty().bind(resourceFactory.getStringBinding("RemoveAtIndexButtonTooltip"));
        sumOfTransferredStudentsButtonTooltip.textProperty().bind(resourceFactory.getStringBinding("SumOfTransferredStudentsButtonTooltip"));
        sortButtonTooltip.textProperty().bind(resourceFactory.getStringBinding("SortButtonTooltip"));
    }

    /**
     * Refresh button on action.
     */

    @FXML
    public void refreshButtonOnAction() {
        requestAction(REFRESH_COMMAND_NAME);
    }

    /**
     * Info button on action.
     */
    @FXML
    private void infoButtonOnAction() {
        requestAction(INFO_COMMAND_NAME);
    }

    /**
     * Add button on action.
     */

    @FXML
    private void addButtonOnAction() {
        askWindowController.clearGroup();
        askStage.showAndWait();
        GroupRaw groupRaw = askWindowController.getAndClear();
        if (groupRaw != null) requestAction(ADD_COMMAND_NAME, "", groupRaw);
    }

    /**
     * Update button on action.
     */
    @FXML
    private void updateButtonOnAction() {

        if (!studyGroupTable.getSelectionModel().isEmpty()) {
            long id = studyGroupTable.getSelectionModel().getSelectedItem().getId();
            askWindowController.setGroup(studyGroupTable.getSelectionModel().getSelectedItem());
            askStage.showAndWait();
            GroupRaw groupRaw = askWindowController.getAndClear();
            if (groupRaw != null) requestAction(UPDATE_COMMAND_NAME, id + "", groupRaw);
        } else OutputerUI.error("UpdateButtonSelectionException");
    }

    /**
     * Remove button on action.
     */
    @FXML
    private void removeButtonOnAction() {
        if (!studyGroupTable.getSelectionModel().isEmpty())
            requestAction(REMOVE_COMMAND_NAME,
                    studyGroupTable.getSelectionModel().getSelectedItem().getId().toString(), null);
        else OutputerUI.error("RemoveButtonSelectionException");
    }

    @FXML
    private void removeAtIndexButtonOnAction() {
        if (!studyGroupTable.getSelectionModel().isEmpty())
            requestAction(REMOVE_AT_INDEX_COMMAND_NAME,
                    studyGroupTable.getSelectionModel().getSelectedItem().getId().toString(), null);
        else OutputerUI.error("RemoveAtIndexButtonSelectionException");
    }

    @FXML
    private void groupCountingByCoordinatesButtonOnAction() {
        requestAction(GROUP_COUNTING_BY_COORDINATES_COMMAND_NAME);
    }

    @FXML
    private void minBySemesterEnumButtonOnAction() {
        requestAction(MIN_SEMESTER_COMMAND_NAME);
    }

    @FXML
    private void sortButtonOnAction() {
        requestAction(SORT_COMMAND_NAME);
    }
    /**
     * Clear button on action.
     */
    @FXML
    private void clearButtonOnAction() {
        requestAction(CLEAR_COMMAND_NAME);
    }

    /**
     * Execute script button on action.
     */
    @FXML
    private void executeScriptButtonOnAction() {
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile == null) return;
        if (client.processScriptToServer(selectedFile)) Platform.exit();
        else refreshButtonOnAction();
    }

    /**
     * History button on action.
     */
    @FXML
    private void historyButtonOnAction() {
        requestAction(HISTORY_COMMAND_NAME);
    }

    /**
     * Sum of health button on action.
     */
    @FXML
    private void sumOfTransferredStudentsButtonOnAction() {
        requestAction(SUM_OF_TRANSFERRED_STUDENTS_COMMAND_NAME);
    }

    /**
     * Request action.
     */
    private void requestAction(String commandName, String commandStringArgument, Serializable commandObjectArgument) {
        LinkedList<StudyGroup> responsedGroups = client.processRequestToServer(commandName, commandStringArgument,
                commandObjectArgument);
        if (responsedGroups != null) {
            ObservableList<StudyGroup> groupsList = FXCollections.observableArrayList(responsedGroups);
            studyGroupTable.setItems(groupsList);
            TableFilter.forTableView(studyGroupTable).apply();
            studyGroupTable.getSelectionModel().clearSelection();
            refreshCanvas();
        }
    }

    /**
     * Binds request action.
     */
    private void requestAction(String commandName) {
        requestAction(commandName, "", null);
    }

    /**
     * Refreshes canvas.
     */
    private void refreshCanvas() {
        shapeMap.keySet().forEach(s -> canvasPane.getChildren().remove(s));
        shapeMap.clear();
        textMap.values().forEach(s -> canvasPane.getChildren().remove(s));
        textMap.clear();
        for (StudyGroup group : studyGroupTable.getItems()) {
            if (!userColorMap.containsKey(group.getOwner().getUsername()))
                userColorMap.put(group.getOwner().getUsername(),
                        Color.color(randomGenerator.nextDouble(), randomGenerator.nextDouble(), randomGenerator.nextDouble()));

            double size = Math.min(group.getStudentsCount(), MAX_SIZE);

            Shape circleObject = new Circle(size, userColorMap.get(group.getOwner().getUsername()));
            circleObject.setOnMouseClicked(this::shapeOnMouseClicked);
            circleObject.translateXProperty().bind(canvasPane.widthProperty().divide(2).add(group.getCoordinates().getX()));
            circleObject.translateYProperty().bind(canvasPane.heightProperty().divide(2).subtract(group.getCoordinates().getY()));

            Text textObject = new Text(group.getId().toString());
            textObject.setOnMouseClicked(circleObject::fireEvent);
            textObject.setFont(Font.font(size / 3));
            textObject.setFill(userColorMap.get(group.getOwner().getUsername()).darker());
            textObject.translateXProperty().bind(circleObject.translateXProperty().subtract(textObject.getLayoutBounds().getWidth() / 2));
            textObject.translateYProperty().bind(circleObject.translateYProperty().add(textObject.getLayoutBounds().getHeight() / 4));

            canvasPane.getChildren().add(circleObject);
            canvasPane.getChildren().add(textObject);
            shapeMap.put(circleObject, group.getId());
            textMap.put(group.getId(), textObject);

            ScaleTransition circleAnimation = new ScaleTransition(ANIMATION_DURATION, circleObject);
            ScaleTransition textAnimation = new ScaleTransition(ANIMATION_DURATION, textObject);
            circleAnimation.setFromX(0);
            circleAnimation.setToX(1);
            circleAnimation.setFromY(0);
            circleAnimation.setToY(1);
            textAnimation.setFromX(0);
            textAnimation.setToX(1);
            textAnimation.setFromY(0);
            textAnimation.setToY(1);
            circleAnimation.play();
            textAnimation.play();
        }
    }

    /**
     * Shape on mouse clicked.
     */
    private void shapeOnMouseClicked(MouseEvent event) {
        Shape shape = (Shape) event.getSource();
        long id = shapeMap.get(shape);
        for (StudyGroup group : studyGroupTable.getItems()) {
            if (group.getId() == id) {
                studyGroupTable.getSelectionModel().select(group);
                break;
            }
        }
        if (prevClicked != null) {
            prevClicked.setFill(prevColor);
        }
        prevClicked = shape;
        prevColor = (Color) shape.getFill();
        shape.setFill(prevColor.brighter());
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setUsername(String username) {
        usernameLabel.setText(username);
    }

    public void setAskStage(Stage askStage) {
        this.askStage = askStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setAskWindowController(AskWindowController askWindowController) {
        this.askWindowController = askWindowController;
    }

    public void initLangs(ObservableResourceFactory resourceFactory) {
        try {
            this.resourceFactory = resourceFactory;
            for (String localeName : localeMap.keySet()) {
                if (localeMap.get(localeName).equals(resourceFactory.getResources().getLocale()))
                    languageComboBox.getSelectionModel().select(localeName);
            }
            String selectedItem = languageComboBox.getSelectionModel().getSelectedItem();
            if (selectedItem == null)
                languageComboBox.getSelectionModel().selectFirst();
//        if (languageComboBox.getSelectionModel().getSelectedItem().isEmpty())
//            languageComboBox.getSelectionModel().selectFirst();
            languageComboBox.setOnAction((event) ->
                    resourceFactory.setResources(ResourceBundle.getBundle
                            (App.BUNDLE, localeMap.get(languageComboBox.getValue()))));
        } catch (NullPointerException exception) {
            Outputer.println(exception.getMessage() + " + ");
        }

        bindGuiLanguage();
    }
}
