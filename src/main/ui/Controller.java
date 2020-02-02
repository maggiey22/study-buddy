package ui;

// import com.oracle.javafx.jmx.json.JSONException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import model.places.*;
import model.schedule.CourseManager;
import model.schedule.Schedule;
import model.schedule.TimeBlock;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ui.loader.DefaultLoader;
import ui.loader.PlaceManagerLoader;
import ui.observer.PlaceManagerObserver;
import ui.printer.InfoPrinter;
import ui.saver.PlaceManagerSaver;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.time.DayOfWeek.*;
import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

public class Controller implements PlaceManagerObserver {

    private static final String ADD_NEW_SESSION = "Add new session";

    @FXML
    private CheckBox showMarkersCheckBox;

//    @FXML
//    private CheckBox showOpenOnlyCheckBox;

    @FXML
    private MenuItem saveLibrariesMenuItem;

    @FXML
    private MenuItem saveLCsMenuItem;

    @FXML
    private MenuItem loadLibrariesMenuItem;

    @FXML
    private MenuItem loadLCsMenuItem;
//    @FXML
//    private MenuItem loadDataMenuItem;

//    @FXML
//    private MenuItem clearData1MenuItem;
//
//    @FXML
//    private MenuItem clearData2MenuItem;
//
//    @FXML
//    private MenuItem clearData3MenuItem;

    @FXML
    private MenuItem clearSavedLCdata;

    @FXML
    private MenuItem clearSavedLibData;
//
//    @FXML
//    private MenuItem loadEmptyFileMenuItem;

    @FXML
    private MenuItem reloadMapMenuItem;

    @FXML
    private MenuItem userGuideMenuItem;

    @FXML
    private MenuItem aboutMenuItem;

//    @FXML
//    private ComboBox<String> openHourComboBox;
//
//    @FXML
//    private ComboBox<String> openMinComboBox;
//
//    @FXML
//    private ComboBox<String> closeHourComboBox;
//
//    @FXML
//    private ComboBox<String> closeMinComboBox;

    @FXML
    private Button addLibraryButton;

    @FXML
    private Button submitPlaceChangesButton;

    @FXML
    private Button addLearningCentreButton;

//    @FXML
//    private Button testButton;
//
//    @FXML
//    private Button hideMarkersButton;
//
//    @FXML
//    private ToolBar mapToolBar;

    @FXML
    private TextArea scheduleOfSelectedPlaceTextArea;

    @FXML
    private TextArea librariesTextArea;

    @FXML
    private TextArea learningCentresTextArea;

//    @FXML
//    private TextArea scheduleTextArea;

//    @FXML
//    private TextArea selectedPlaceTextArea;

    @FXML
    private TextField overviewPlaceTextField;

    @FXML
    private TextField placeNameTextField;

    @FXML
    public TextField openHourTextField;

    @FXML
    private TextField openMinuteTextField;

    @FXML
    private TextField closeHourTextField;

    @FXML
    private TextField closeMinuteTextField;

    @FXML
    private TextField streetAddressTextField;

    @FXML
    private TextField cityTextField;

    @FXML
    private TextField provinceTextField;


//    @FXML
//    public Spinner<String> testZeroPadSpinner;
    //public Spinner<LocalTime> testZeroPadSpinner;

//    @FXML
//    public Spinner<String> openHourSpinner;
//
//    @FXML
//    private Spinner<String> openMinuteSpinner;
//
//    @FXML
//    private Spinner<String> closeHourSpinner;
//
//    @FXML
//    private Spinner<String> closeMinuteSpinner;

    //    @FXML
//    public Spinner<Integer> openHourSpinner;
//
//    @FXML
//    private Spinner<Integer> openMinuteSpinner;
//
//    @FXML
//    private Spinner<Integer> closeHourSpinner;
//
//    @FXML
//    private Spinner<Integer> closeMinuteSpinner;
//    @FXML
//    private TitledPane randomPane;

    @FXML
    private ListView<String> libraryListView;

    @FXML
    private ListView<String> learningCentreListView;

    @FXML
    private ListView<DayOfWeek> placeDayListView;

    @FXML
    private ListView<String> placeSessionsListView;
//
//    @FXML
//    private TabPane tabPane;

    @FXML
    private Tab mapTab;

//
//    @FXML
//    private Tab overviewTab;

    @FXML
    private Accordion placesAccordion;

    @FXML
    private TitledPane librariesPane;

    @FXML
    private TitledPane lcPane;

    private WebView mapBrowser = new WebView();

    private WebEngine webEngine = mapBrowser.getEngine();

    private List<double[]> coords = new ArrayList<>();

    //    private static final String googleMapsGeocodingQueryPreAddress =
//            "https://maps.googleapis.com/maps/api/geocode/json?address=";
    private static final String QUERY_PRE_ADDRESS =
            "https://maps.googleapis.com/maps/api/geocode/json?address=";
    private static final String QUERY_POST_ADDRESS =
            "&key=KEY_REMOVED_!!!!"; //APIKEY

    @FXML
    private void handleButtonAction(ActionEvent event) {
        sayHello();
    }

    //PlaceManager pm = initLoader.loadLibraries().mergePlaceManagers(initLoader.loadLearningCentres(cm));
    //pm.addObserver(this);

    @FXML
    private void initialize() throws IOException {
        StudyBuddy sb = new StudyBuddy();
        DefaultLoader initLoader = new DefaultLoader();

        CourseManager cm = initLoader.loadCourses();
        PlaceManager lm = initLoader.loadLibraries(); //actual type LibraryManager
        PlaceManager lcm = initLoader.loadLearningCentres(cm); //actual type LCManager

        lm.addObserver(this);
        lcm.addObserver(this);

//        CourseManagerSaver cms = new CourseManagerSaver();
        PlaceManagerSaver placesSaver = new PlaceManagerSaver();
        PlaceManagerLoader placesLoader = new PlaceManagerLoader();
//        CourseManagerLoader cml = new CourseManagerLoader();

        loadDataIntoTextArea(lm, lcm, cm);
        // loadMapTab(); // STUB
        assignEvents(sb, cm, lm, lcm, placesSaver, placesLoader);
        //assignEvents(sb, cm, lm, lcm, cms, cml);
        populateAndDisplayBothPlaceLists(lm, lcm);
        populateAndDisplayDayOfWeekList();

        // NOT USING SPINNERS ANYMORE - TOO MANY PROBLEMS
        // setUpTestSpinner(testZeroPadSpinner);
        // setUpTimeSpinners();

        // NOT USING COMBO BOX EITHER - TOO MANY PROBLEMS
//        ObservableList<String> mins = FXCollections.observableArrayList();
//        for (int i = 0; i < 60; i++) {
//            mins.add(String.format("%02d", i));
//        }
//        openMinComboBox.setItems(mins);
        // this is really not working at all :(
//        setUpCommitEditAfterTimeInput(openHourSpinner);
//        setUpCommitEditAfterTimeInput(openMinuteSpinner);
//        setUpCommitEditAfterTimeInput(closeHourSpinner);
//        setUpCommitEditAfterTimeInput(closeMinuteSpinner);

        //useScriptEngine(); // semiworking


        // AUG8 GO HERE HERE HERE HERE HERE
        placeAllDefaultMapMarkers(lm, lcm);


        //3:08PM, AUG8
//        List<double[]> coords = new ArrayList<>();
//        for (Place p : lcm) {
//            coords.add(getLatLngOfPlace(p));
//        }
//        for (Place p : lm) {
//            coords.add(getLatLngOfPlace(p));
//        }

//        for (double[] coord : coords) {
//            webEngine.executeScript("addMarkerAtLatLong(" + coord[0] + "," + coord[1] + ");");
        //}
//        testButton.fire();


//        Place demco = lcm.getPlace("Demco Learning Centre");
//        getLatLngOfPlace(demco);

//        for (Place p : lcm) {
//            System.out.println(getLatLngOfPlace(p));
//        }
//
//        for (Place p : lm) {
//            System.out.println(getLatLngOfPlace(p));
//        }


    }

    private void placeAllDefaultMapMarkers(PlaceManager lm, PlaceManager lcm) throws IOException {
        for (Place p : lcm) {
            coords.add(getLatLngOfPlace(p));
        }
        for (Place p : lm) {
            coords.add(getLatLngOfPlace(p));
        }
        initializeScriptEngine(coords);
    }

    /*// CREDITS TO SERGIO - https://stackoverflow.com/questions/32340476/manually-typing-in-text-in-javafx-spinner-
    // is-not-updating-the-value-unless-user
    private void setUpCommitEditAfterTimeInput(Spinner<Integer> spinner) {
        spinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                try {
                    spinner.increment(0); // won't change value, but will commit editor
                } catch (NumberFormatException e) {
                    spinner.getValueFactory().setValue(0);//not working :(
                    displayTimeErrorAlert();
                } catch (NullPointerException e) {
                    spinner.getValueFactory().setValue(0);//not working :(
                }
            }
        });
    } // CREDITS TO END FROM SERGIO*/

    private void displayTimeErrorAlert(String errorSpot) {
        Alert nonNumbersEnteredAlert = new Alert(ERROR, "Please enter a valid " + errorSpot + " value.");
        nonNumbersEnteredAlert.showAndWait();
    }

    private void displaySuccessAlert(String newName, Address ads) {
        Alert successAlert = new Alert(INFORMATION, "Name: " + newName
                + "\nAddress: " + ads.getStreet() + ", " + ads.getCity() + ", " + ads.getProvince());
        successAlert.setTitle("Successful Edit");
        successAlert.setHeaderText("Study place details changed");
        successAlert.showAndWait();
    }

/*    private void setUpTestSpinner(Spinner<String> spinner) {
        ObservableList<String> testTimes = FXCollections.observableArrayList();
        for (int i = 0; i <= 23; i++) {
            testTimes.add(String.format("%02d", i));
        }
        SpinnerValueFactory<String> valueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(testTimes);
        spinner.setValueFactory(valueFactory);
    }*/

    private double[] getLatLngOfPlace(Place place) throws IOException {
        if (place.getAddress() != null) {
            JSONObject obj = readJsonFromUrl(QUERY_PRE_ADDRESS + place.getAddress()
                    .makeQueryableAddress() + QUERY_POST_ADDRESS); // APIKEY
            if (obj.get("status").equals("OK")) {
                //System.out.println("Status of query is: " + (String) obj.get("status"));
                //  -- HOW DOES COMPILER KNOW THAT (String)    here    IS AN UNNEEDED CAST?
                JSONArray jsonarray = (JSONArray) obj.get("results");
                //System.out.println(jsonarray.toString());
                double[] latlng = new double[2];
                // V CREDITSTO SPRINGBREAKER
                // V CREDITSTO - stackoverflow.com/questions/18977144/how-to-parse-json-array-not-json-object-in-android
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
//            String address = jsonobject.getString("formatted_address");
//            String id = jsonobject.getString("place_id");
                    // V CREDITSTO ******* END of SPRING BREAKER's answer
                    JSONObject geometry = jsonobject.getJSONObject("geometry");
//            System.out.println("address: " + address);
//            System.out.println("id: " + id);
                    //System.out.println(geometry);
                    JSONObject location = geometry.getJSONObject("location");

                    //System.out.println(location);

//                    Double lat = (Double) location.get("lat");
//                    latlng[0] = lat;
                    latlng[0] = (Double) location.get("lat");
                    Double lng = (Double) location.get("lng");
                    latlng[1] = lng;
                    //System.out.println("Latitude is " + lat);
                    //System.out.println("Longitude is " + lng);
                }
                return latlng;
            }
        }
        return null;
    }

    private void assignEvents(StudyBuddy sb, CourseManager cm, PlaceManager lm, PlaceManager lcm,
                              PlaceManagerSaver s, PlaceManagerLoader l) {
//    private void assignEvents(StudyBuddy sb, CourseManager cm, PlaceManager lm, PlaceManager lcm,
//                              CourseManagerSaver cms, CourseManagerLoader cml) throws IOException {
        //assignActionEvents(sb, lm, lcm, cm, cms, cml);
        assignActionEvents(sb, lm, lcm, s, l);
        assignListViewEvents(libraryListView, learningCentreListView, lm, lcm, placeDayListView);
        placeDayListView.setOnMouseClicked(event -> {
            DayOfWeek day = placeDayListView.getSelectionModel().getSelectedItem();
            Place p1 = lm.getPlace(libraryListView.getSelectionModel().getSelectedItem());
            Place p2 = lcm.getPlace(learningCentreListView.getSelectionModel().getSelectedItem());
            if (placesAccordion.getExpandedPane() != null) {
                if (p1 != null && placesAccordion.getExpandedPane().equals(librariesPane)) { //if library selected
                    populateAndDisplaySessionsOnDay(p1, day); //show sessions for that day and that library
                } else if (p2 != null && placesAccordion.getExpandedPane().equals(lcPane)) {
                    populateAndDisplaySessionsOnDay(p2, day);
                }
                handleSelectBlockThenChangeDayOrPlace();
            }
        });
        placeSessionsListView.setOnMouseClicked(event -> setTimeTextFields());
        // show open only is not working :(
//        showOpenOnlyCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
//            List<double[]> openCoords = coords;
//            addOpenPlacesCoordToList(lm, openCoords);
//            addOpenPlacesCoordToList(lcm, openCoords);
//            if (newValue) {
//                if (coords != null) {
//                    for (double[] coord : openCoords) {
//                        webEngine.executeScript("addMarkerAtLatLong(" + coord[0] + "," + coord[1] + ");");
//                    }
//                }
//            }
//        });
    }

//    private void addOpenPlacesCoordToList(PlaceManager pm, List<double[]> openCoords) {
//        for (Place p : pm) {
//            if (p.isOpen(LocalDate.now().getDayOfWeek(), LocalTime.now())) {
//                try {
//                    openCoords.add(getLatLngOfPlace(p));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    private void handleSelectBlockThenChangeDayOrPlace() {
        String tb = placeSessionsListView.getSelectionModel().getSelectedItem(); //this isn't an issue here.
        if (!(tb == null)) {
            setTimeTextFields();
        } else {
            emptyTimeTextFields();
        }
    }

    private void setTimeTextFields() {
        String tb = placeSessionsListView.getSelectionModel().getSelectedItem();
        if (!(tb == null)) {
            if (tb.equals(ADD_NEW_SESSION)) {
                emptyTimeTextFields();
            } else {
                openHourTextField.setText(tb.substring(0, 2));
                openMinuteTextField.setText(tb.substring(3, 5));
                closeHourTextField.setText(tb.substring(6, 8));
                closeMinuteTextField.setText(tb.substring(9, 11));
                //openHourSpinner.getValueFactory().setValue(tb.substring(0, 2));
//            openHourSpinner.getValueFactory().setValue(Integer.parseInt(tb.substring(0, 2)));
//            openMinuteSpinner.getValueFactory().setValue(Integer.parseInt(tb.substring(3, 5)));
//            closeHourSpinner.getValueFactory().setValue(Integer.parseInt(tb.substring(6, 8)));
//            closeMinuteSpinner.getValueFactory().setValue(Integer.parseInt(tb.substring(9, 11)));
            }
        }
    }

    private void emptyTimeTextFields() {
        openHourTextField.setText("00");
        openMinuteTextField.setText("00");
        closeHourTextField.setText("00");
        closeMinuteTextField.setText("00");
//        openHourSpinner.getValueFactory().setValue(0);
//        openMinuteSpinner.getValueFactory().setValue(0);
//        closeHourSpinner.getValueFactory().setValue(0);
//        closeMinuteSpinner.getValueFactory().setValue(0);

    }


    private void initializeScriptEngine(List<double[]> coords) { // V CREDITSTO vlopezla
        //double[] coords = getLatLngOfPlace(p);
        //V CREDITSTO https://stackoverflow.com/questions/23011718/show-marker-in-a-google-map-in-javafx-desktop-app
        final URL googleMap = getClass().getResource("GoogleMap.html");
        webEngine.setJavaScriptEnabled(true);
        webEngine.load(googleMap.toExternalForm());
        mapTab.setContent(mapBrowser);
        // webEngine.executeScript("addMarkerAtLatLong(49.265077,-123.250571);");//ubc bookstore
        // V CREDITSTO End from vlopezla

        showMarkersCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                if (coords != null) { //FIX ME why is it even null? probably because it couldn't find anything
                    for (double[] coord : coords) {
                        webEngine.executeScript("addMarkerAtLatLong(" + coord[0] + "," + coord[1] + ");");
                    }
                }
            } else {
                webEngine.executeScript("hideAllMarkers();");
            }
        });
//        if (showMarkersCheckBox.isSelected()) { //for refresh. This doesn't work though.
//            if (coords != null) { //FIX ME why is it even null? probably because it couldn't find anything
//                for (double[] coord : coords) {
//                    webEngine.executeScript("addMarkerAtLatLong(" + coord[0] + "," + coord[1] + ");");
//                }
//            }
//        }

    }
// AUG8
        /*testButton.setOnAction(event -> {
                    //webEngine.executeScript("addMarker(49.260299,-123.248406);");
            //sayHello();
                    for (double[] coord : coords) {
                        webEngine.executeScript("addMarkerAtLatLong(" + coord[0] + "," + coord[1] + ");");
                    }

                    //webEngine.executeScript("addMarkerAtLatLong(49.265077,-123.250571);");//ubc bookstore
                    //webEngine.executeScript("addMarkerAtLatLong(49.262428,-123.250185);");
                }
        );
        hideMarkersButton.setOnAction(event -> webEngine.executeScript("hideAllMarkers();"));*/


    //showMarkersCheckBox.setOnAction();
//        testButton.fire();
//    private void useScriptEngine() {
////        // CREDITS TO mod MRK187
//// stackoverflow.com/questions/22856279/call-external-javascript-functions-from-java-code
//        ScriptEngineManager manager = new ScriptEngineManager();
//        ScriptEngine engine = manager.getEngineByName("JavaScript");
//        try {
//            engine.eval(Files.newBufferedReader(Paths
//                            .get("/Users/hnjbing/Desktop/CPSC-210/study_buddy/src/main/ui/sample.js"),
//                    StandardCharsets.UTF_8));
//        } catch (ScriptException e) {
//            System.out.println("ScriptException caught");
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Invocable inv = (Invocable) engine;
//
//        tryCallingFnFromScript(inv);
//    }

//    private void tryCallingFnFromScript(Invocable inv) {
//        try {
//            // call function from script file
//            inv.invokeFunction("msg");
//        } catch (ScriptException e) {
//            System.out.println("ScriptException caught");
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            System.out.println("Method not found");
//            e.printStackTrace();
//        }
//    }

    //        try { //MINE
//            engine.eval("print('Hello, World');\nprint('Hello, User')");
//        } catch (ScriptException e) {
//            e.printStackTrace();
//        } //ENDMINE
    // read script file
        /*try {
            engine.eval(Files.newBufferedReader(Paths
                    .get("/Users/hnjbing/Desktop/CPSC-210/study_buddy/src/main/ui/sample.js"),
    StandardCharsets.UTF_8));
} catch (ScriptException e) {
            System.out.println("ScriptException caught");
            e.printStackTrace();
} catch (IOException e) {
            e.printStackTrace();
}

    Invocable inv = (Invocable) engine;

        try {
    // call function from script file
            inv.invokeFunction("msg");
} catch (ScriptException e) {
            System.out.println("ScriptException caught");
            e.printStackTrace();
} catch (NoSuchMethodException e) {
            System.out.println("Method not found");
            e.printStackTrace();
}

    // CREDITS TO  END MRK187*/


    // used to be right after call to setUpTimeSpinners() in init()
    //        readGoogleMapsGeocodeQuery(lcm.getPlace("Demco Learning Centre").getAddress());
        /*JSONObject obj = readJsonFromUrl(googleMapsGeocodingQueryPreAddress
                + lcm.getPlace("Demco Learning Centre").getAddress().makeQueryableAddress()
                + QUERY_POST_ADDRESS); // APIKEY
        System.out.println(obj.get("place_id"));*/
//        readGoogleMapsGeocodeQuery(new Address("6138 Student Union Blvd", "Vancouver", "BC"));

    /*private void setUpTimeSpinners() {
//        setUpTestSpinner(openHourSpinner);
//        setUpTestSpinner(openMinuteSpinner);
//        setUpTestSpinner(closeHourSpinner);
//        setUpTestSpinner(closeMinuteSpinner);
//        handleInvalidTimeInputs();
        SpinnerValueFactory<Integer> hourValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0);
        SpinnerValueFactory<Integer> hourValueFactory2 =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0);
        openHourSpinner.setValueFactory(hourValueFactory);
        closeHourSpinner.setValueFactory(hourValueFactory2);

        SpinnerValueFactory<Integer> minuteValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
        SpinnerValueFactory<Integer> minuteValueFactory2 =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
        openMinuteSpinner.setValueFactory(minuteValueFactory);
        closeMinuteSpinner.setValueFactory(minuteValueFactory2);
        handleInvalidTimeInputs();
    }*/

    /*private void handleInvalidTimeInputs() {
        // something weird happening here - text - 60 - enter = shows text again
//        EventHandler<KeyEvent> enterKeyEventHandlerOpenHour = event -> checkHourInputValidity(event, openHourSpinner);
//        EventHandler<KeyEvent> enterKeyEventHandlerOpenMinute = event ->
//                checkMinuteInputValidity(event, openMinuteSpinner);
//        EventHandler<KeyEvent> enterKeyEventHandlerCloseHour = event -> checkHourInputValidity(event,
//                closeHourSpinner);
//        EventHandler<KeyEvent> enterKeyEventHandlerCloseMinute = event ->
//                checkMinuteInputValidity(event, closeMinuteSpinner);
//        openHourSpinner.getEditor().addEventHandler(KEY_PRESSED, enterKeyEventHandlerOpenHour);
//        openMinuteSpinner.getEditor().addEventHandler(KEY_PRESSED, enterKeyEventHandlerOpenMinute);
//        closeHourSpinner.getEditor().addEventHandler(KEY_PRESSED, enterKeyEventHandlerCloseHour);
//        closeMinuteSpinner.getEditor().addEventHandler(KEY_PRESSED, enterKeyEventHandlerCloseMinute);

        EventHandler<KeyEvent> enterKeyEventHandlerOpenHour = event -> checkTimeInputValidity(event, openHourSpinner);
        EventHandler<KeyEvent> enterKeyEventHandlerOpenMinute = event ->
                checkTimeInputValidity(event, openMinuteSpinner);
        EventHandler<KeyEvent> enterKeyEventHandlerCloseHour = event -> checkTimeInputValidity(event,
                closeHourSpinner);
        EventHandler<KeyEvent> enterKeyEventHandlerCloseMinute = event ->
                checkTimeInputValidity(event, closeMinuteSpinner);
        openHourSpinner.getEditor().addEventHandler(KEY_PRESSED, enterKeyEventHandlerOpenHour);
        openMinuteSpinner.getEditor().addEventHandler(KEY_PRESSED, enterKeyEventHandlerOpenMinute);
        closeHourSpinner.getEditor().addEventHandler(KEY_PRESSED, enterKeyEventHandlerCloseHour);
        closeMinuteSpinner.getEditor().addEventHandler(KEY_PRESSED, enterKeyEventHandlerCloseMinute);
    }*/


//    private void checkHourInputValidity(KeyEvent event, Spinner<String> spinner) {
//        if (event.getCode() == ENTER) {
//            //if (!(spinner.getValue().matches("\\d\\d"))) {
//            if (!(spinner.getValue().matches("((0|1)[0-9])|(2[0-3])"))) {
//                spinner.getEditor().setText("00");
//            }
//        }
//    }

//    private void checkMinuteInputValidity(KeyEvent event, Spinner<String> spinner) {
//        if (event.getCode() == ENTER) {
//            //if (!(spinner.getValue().matches("\\d\\d"))) {
//            if (!(spinner.getValue().matches("[0-5][0-9]"))) {
//                spinner.getEditor().setText("00");
//            }
//        }
//    }
//
//    private void checkTimeInputValidity(KeyEvent event, Spinner<Integer> spinner) {
//        //CREDITS TO adapted from https://stackoverflow.com/questions/25885005/insert-only-numbers-in-spinner-control
//        //CREDITS TO marcus bleil start from SO
//        if (event.getCode() == ENTER) {
//            try {
//                Integer.parseInt(spinner.getEditor().textProperty().get());
//            } catch (NumberFormatException e) {
//                //spinner.getEditor().textProperty().set("0");
//                spinner.getValueFactory().setValue(0);
//            }
//        } // CREDITS TO Marcus Bleil end from SO
//    }


    //private void populateAndDisplayBothPlaceLists(PlaceManager pm) {
    private void populateAndDisplayBothPlaceLists(PlaceManager lm, PlaceManager lcm) {
        populateAndDisplayLibList(lm);
        populateAndDisplayLClist(lcm);
        //populateLists(libraryList, lcList, pm);
    }

    private void populateAndDisplayLibList(PlaceManager lm) {
        ObservableList<String> libraryList = FXCollections.observableArrayList();
        populatePlacesList(libraryList, lm);
        libraryListView.setItems(libraryList);
    }

    private void populateAndDisplayLClist(PlaceManager lcm) {
        ObservableList<String> lcList = FXCollections.observableArrayList();
        populatePlacesList(lcList, lcm);
        learningCentreListView.setItems(lcList);
    }

    private void populateAndDisplayDayOfWeekList() {
        ObservableList<DayOfWeek> dayList = FXCollections.observableArrayList();
        populateDayOfWeekList(dayList);
        placeDayListView.setItems(dayList);
    }

    private void populateAndDisplaySessionsOnDay(Place p, DayOfWeek day) {
        if (day != null) {
            ObservableList<String> sessionsList = FXCollections.observableArrayList();
            populateSessionsOnDayList(sessionsList, p, day);
            placeSessionsListView.setItems(sessionsList);
        }
    }

    private void assignActionEvents(StudyBuddy sb, PlaceManager lm, PlaceManager lcm, PlaceManagerSaver s,
                                    PlaceManagerLoader l) {
        //CourseManager cm, CourseManagerSaver cms, CourseManagerLoader cml) {
        //refreshPlacesButton.setOnAction(event -> loadDataIntoTextArea(lm, lcm, cm));
        addLibraryButton.setOnAction(event -> sb.addMyPlace(lm, "library"));
        addLearningCentreButton.setOnAction(event -> sb.addMyPlace(lcm, "lc"));
        assignSaveLoadMenuItems(lm, lcm, s, l);
//        clearData1MenuItem.setOnAction(event -> s.wipeSaveSlot("saved_places1.json"));
//        clearData2MenuItem.setOnAction(event -> s.wipeSaveSlot("saved_places2.json"));
//        clearData3MenuItem.setOnAction(event -> s.wipeSaveSlot("saved_places3.json"));
        //loadEmptyFileMenuItem.setOnAction(event -> LOAD EMPTY PLACE LIST);

        //loadEmptyFileMenuItem.setOnAction(event -> cml.loadSaveSlot(cm, "empty_places.json"));
        aboutMenuItem.setOnAction(event -> displayAboutAlert());
        userGuideMenuItem.setOnAction(event -> displayUserGuide());
        reloadMapMenuItem.setOnAction(event -> {
            //try {
            showMarkersCheckBox.setSelected(false);
            //webEngine.executeScript("hideAllMarkers();");
            initializeScriptEngine(coords);
//            } catch (netscape.javascript.JSException e) {
//                System.out.println("Something went wrong!");
//                e.printStackTrace();
//            }
        });
        //try {
        //initializeScriptEngine(coords);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        //});
        //reloadMapMenuItem.setOnAction(event -> loadMapTab());
        //reloadMapMenuItem.setOnAction(event -> webEngine.reload()); this is actually just refreshing the page
        submitPlaceChangesButton.setOnAction(event -> {
            try {
                submitPlaceChanges(lm, lcm);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
//        showMarkersCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//                if (newValue) {
//                    webEngine.executeScript()
//                }
//            }
//        });
    }

    private void assignSaveLoadMenuItems(PlaceManager lm, PlaceManager lcm, PlaceManagerSaver s, PlaceManagerLoader l) {
        saveLibrariesMenuItem.setOnAction(event -> chooseLibSaveFile(lm, s));
        saveLCsMenuItem.setOnAction(event -> chooseLCsaveFile(lcm, s));
        clearSavedLibData.setOnAction(event -> chooseClearLibFile(s));
        clearSavedLCdata.setOnAction(event -> chooseClearLCfile(s));
        loadLibrariesMenuItem.setOnAction(event -> chooseLoadLibFile(lm, l));
        loadLCsMenuItem.setOnAction(event -> chooseLoadLCfile(lcm, l));
    }

    private void chooseLoadLibFile(PlaceManager lm, PlaceManagerLoader l) {
        // V CREDITSTO CODE MAKERY https://code.makery.ch/blog/javafx-dialogs-official/
        List<String> files = new ArrayList<>();
        files.add("1");
        files.add("2");
        files.add("3");
        ChoiceDialog<String> chooseLibFileDialog = new ChoiceDialog<>("1", files);
        chooseLibFileDialog.setTitle("Load Libraries");
        chooseLibFileDialog.setHeaderText("Load previously saved libraries");
        chooseLibFileDialog.setContentText("Choose a file to load: ");

        Optional<String> choice = chooseLibFileDialog.showAndWait();
        // V END CREDITSTO CODE MAKERY
        if (choice.isPresent()) {
            l.loadSaveSlot(lm, "saved_libraries" + choice.get() + ".json");
            populateAndDisplayLibList(lm);
        }
    }

    private void chooseLoadLCfile(PlaceManager lcm, PlaceManagerLoader l) {
        // V CREDITSTO CODE MAKERY https://code.makery.ch/blog/javafx-dialogs-official/
        List<String> files = new ArrayList<>();
        files.add("1");
        files.add("2");
        files.add("3");
        ChoiceDialog<String> chooseLCfileDialog = new ChoiceDialog<>("1", files);
        chooseLCfileDialog.setTitle("Load Learning Centres");
        chooseLCfileDialog.setHeaderText("Load previously saved learning centres");
        chooseLCfileDialog.setContentText("Choose a file to load: ");

        Optional<String> choice = chooseLCfileDialog.showAndWait();
        // V END CREDITSTO CODE MAKERY
        if (choice.isPresent()) {
            l.loadSaveSlot(lcm, "saved_learningCentres" + choice.get() + ".json");
            populateAndDisplayLClist(lcm);
        }
    }

    private void chooseClearLibFile(PlaceManagerSaver s) {
        // V CREDITSTO CODE MAKERY https://code.makery.ch/blog/javafx-dialogs-official/
        List<String> files = new ArrayList<>();
        files.add("1");
        files.add("2");
        files.add("3");
        ChoiceDialog<String> chooseLibFileDialog = new ChoiceDialog<>("1", files);
        chooseLibFileDialog.setTitle("Clear Saved Library Data");
        chooseLibFileDialog.setHeaderText("Delete saved libraries");
        chooseLibFileDialog.setContentText("Choose a file to clear: ");

        Optional<String> choice = chooseLibFileDialog.showAndWait();
        // V END CREDITSTO CODE MAKERY
        choice.ifPresent(s1 -> s.wipeSaveSlot("saved_libraries" + s1 + ".json"));
    }

    private void chooseClearLCfile(PlaceManagerSaver s) {
        // V CREDITSTO CODE MAKERY https://code.makery.ch/blog/javafx-dialogs-official/
        List<String> files = new ArrayList<>();
        files.add("1");
        files.add("2");
        files.add("3");
        ChoiceDialog<String> chooseLCfileDialog = new ChoiceDialog<>("1", files);
        chooseLCfileDialog.setTitle("Clear Saved Learning Centre Data");
        chooseLCfileDialog.setHeaderText("Delete saved learning centres");
        chooseLCfileDialog.setContentText("Choose a file to clear: ");

        Optional<String> choice = chooseLCfileDialog.showAndWait();
        // V END CREDITSTO CODE MAKERY
        choice.ifPresent(s1 -> s.wipeSaveSlot("saved_learningCentres" + s1 + ".json"));
    }

    private void chooseLibSaveFile(PlaceManager lm, PlaceManagerSaver s) {
        // V CREDITSTO CODE MAKERY https://code.makery.ch/blog/javafx-dialogs-official/
        List<String> files = new ArrayList<>();
        files.add("1");
        files.add("2");
        files.add("3");
        ChoiceDialog<String> chooseLibFileDialog = new ChoiceDialog<>("1", files);
        chooseLibFileDialog.setTitle("Save Libraries");
        chooseLibFileDialog.setHeaderText("Save libraries");
        chooseLibFileDialog.setContentText("Choose a file to save to: ");

        Optional<String> choice = chooseLibFileDialog.showAndWait();
        if (choice.isPresent()) {
            // V END CREDITSTO CODE MAKERY
            System.out.println("Saving to " + choice.get());
            try {
                s.saveToSaveSlot(lm, "saved_libraries" + choice.get() + ".json");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void chooseLCsaveFile(PlaceManager lcm, PlaceManagerSaver s) {
        // V CREDITSTO CODE MAKERY https://code.makery.ch/blog/javafx-dialogs-official/
        List<String> files = new ArrayList<>();
        files.add("1");
        files.add("2");
        files.add("3");
        ChoiceDialog<String> chooseLCfileDialog = new ChoiceDialog<>("1", files);
        chooseLCfileDialog.setTitle("Save Learning Centres");
        chooseLCfileDialog.setHeaderText("Save learning centres");
        chooseLCfileDialog.setContentText("Choose a file to save to: ");

        Optional<String> choice = chooseLCfileDialog.showAndWait();
        if (choice.isPresent()) {
            // V END CREDITSTO CODE MAKERY
            System.out.println("Saving to " + choice.get());
            try {
                s.saveToSaveSlot(lcm, "saved_learningCentres" + choice.get() + ".json");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void submitPlaceChanges(PlaceManager libManager, PlaceManager lcManager) throws IOException {
        //String name = libraryListView.getSelectionModel().getSelectedItem();asdfasdfsadf
        if (placesAccordion.getExpandedPane() != null) {
            String name = null;
            if (placesAccordion.getExpandedPane().equals(librariesPane)) {
                name = libraryListView.getSelectionModel().getSelectedItem();
            } else if (placesAccordion.getExpandedPane().equals(lcPane)) {
                name = learningCentreListView.getSelectionModel().getSelectedItem();
            }
            //String newName = placeNameTextField.getText();
            Address ads = new Address(streetAddressTextField.getText(), cityTextField.getText(),
                    provinceTextField.getText());
            //System.out.println(LINE_DIV + "\nYOU HAVE ENTERED\nName: " + newName);
//            System.out.println("Address: " + streetAddressTextField.getText() + ", " + cityTextField.getText() + ","
//                    + provinceTextField.getText());
            //System.out.println("Day: " + placeDayListView.getSelectionModel().getSelectedItem());
            //System.out.println("Session: " + placeSessionsListView.getSelectionModel().getSelectedItem());

            Place p = libManager.getPlace(name);
            if (p == null) {
                p = lcManager.getPlace(name);
            }
            modifyPlaceDetails(libManager, lcManager, ads, p);
//            if (changePlaceFields(p, newName, ads, libManager, lcManager)) {
//                coords.add(getLatLngOfPlace(p));
//                displaySuccessAlert(newName, ads);
//            }
        }

//        if (openHourTextField.getText().matches("([0-1]?[0-9])|(2[0-3])")
//                && closeHourTextField.getText().matches("([0-1]?[0-9])|(2[0-3])")
//                && openMinuteTextField.getText().matches("[0-5][0-9]")
//                && closeMinuteTextField.getText().matches("[0-5][0-9]")) {
//            System.out.println("New times: " + String.format("%02d", Integer.parseInt(openHourTextField.getText()))
//                    + ":" + openMinuteTextField.getText() + "-"
//                    + String.format("%02d", Integer.parseInt(closeHourTextField.getText())) + ":"
//                    + closeMinuteTextField.getText());
//        } else {
//            displayTimeErrorAlert();
//        }
//        System.out.println("New times: " + String.format("%02d", openHourSpinner.getValue()) + ":"
//                + String.format("%02d", Integer.parseInt(openMinuteSpinner.getValue().toString())) + "-"
//                + String.format("%02d", closeHourSpinner.getValue()) + ":"
//                + String.format("%02d", closeMinuteSpinner.getValue()));
//        System.out.println("New times: " + String.format("%02d", openHourSpinner.getValue()) + ":"
//                + String.format("%02d", openMinuteSpinner.getValue()) + "-"
//                + String.format("%02d", closeHourSpinner.getValue()) + ":"
//                + String.format("%02d", closeMinuteSpinner.getValue()));
    }

    private void modifyPlaceDetails(PlaceManager libManager, PlaceManager lcManager, Address ads, Place p)
            throws IOException {
        boolean ok = handleInvalidTimeInput(p);

        if (changePlaceFields(p, placeNameTextField.getText(), ads, libManager, lcManager) && ok) {
            coords.add(getLatLngOfPlace(p));
            displaySuccessAlert(placeNameTextField.getText(), ads);
        }
    }

    private boolean changePlaceFields(Place p, String newName, Address ads, PlaceManager lm, PlaceManager lcm) {
        if (!(p == null)) {
            p.setAddress(ads);
            showMarkersCheckBox.setSelected(false);
            p.setPlaceName(newName);
            populateAndDisplayBothPlaceLists(lm, lcm);
            overviewPlaceTextField.setText(p.getName());
            librariesTextArea.setText(lm.printAllPlaces().trim());
            learningCentresTextArea.setText(lcm.printAllPlaces().trim());
            return true;
        }
        return false;
    }

    private boolean handleInvalidTimeInput(Place p) {
        boolean isOk = false;
        if (!(openHourTextField.getText().matches("([0-1]?[0-9])|(2[0-3])"))) {
            openHourTextField.setText("00");
            displayTimeErrorAlert("opening hour");
        } else if (!(closeHourTextField.getText().matches("([0-1]?[0-9])|(2[0-3])"))) {
            closeHourTextField.setText("00");
            displayTimeErrorAlert("closing hour");
        } else if (!(openMinuteTextField.getText().matches("[0-5][0-9]"))) {
            openMinuteTextField.setText("00");
            displayTimeErrorAlert("opening minutes");
        } else if (!(closeMinuteTextField.getText().matches("[0-5][0-9]"))) {
            closeMinuteTextField.setText("00");
            displayTimeErrorAlert("closing minutes");
        } else {
//            System.out.println("New times: " + String.format("%02d", Integer.parseInt(openHourTextField.getText()))
//                    + ":" + openMinuteTextField.getText() + "-" + String.format("%02d", Integer.parseInt(
//                    closeHourTextField.getText())) + ":" + closeMinuteTextField.getText());
            setNewTimesForPlace(p);
            isOk = true;
        }
        return isOk;
    }

    private void setNewTimesForPlace(Place p) {
        String tb = placeSessionsListView.getSelectionModel().getSelectedItem();
        DayOfWeek day = placeDayListView.getSelectionModel().getSelectedItem();
        handleDifferentSessionTypes(p, tb, day);
    }

    private void handleDifferentSessionTypes(Place p, String tb, DayOfWeek day) {
        if (!(p == null) && !(tb == null)) {
            if (tb.equals(ADD_NEW_SESSION)) {
                if (placeSessionsListView.getItems().size() <= 1) {
                    addNewSessionToEmptyDay(p, day);
                } else {
                    addNewSessionToNonEmptyDay(p, day);
                }
            } else {
                p.getSchedule().setMeetingTime(day,
                        LocalTime.parse(tb.substring(0, 5)), LocalTime.parse(tb.substring(6, 11)),
                        LocalTime.parse(String.format("%02d", Integer.parseInt(openHourTextField.getText()))
                                + ":" + openMinuteTextField.getText()), LocalTime.parse(String.format("%02d", Integer
                                .parseInt(closeHourTextField.getText())) + ":" + closeMinuteTextField.getText()));
            }
            updateDisplayForPlaceTimes(p, day);
        }
    }

    private void addNewSessionToNonEmptyDay(Place p, DayOfWeek day) {
        p.getSchedule().addMeetingTimeUnderExistingDay(day, LocalTime.parse(String.format("%02d",
                Integer.parseInt(openHourTextField.getText())) + ":" + openMinuteTextField.getText()),
                LocalTime.parse(String.format("%02d", Integer.parseInt(closeHourTextField.getText())) + ":"
                        + closeMinuteTextField.getText()));
    }

    private void addNewSessionToEmptyDay(Place p, DayOfWeek day) {
        p.getSchedule().addNewDayAndMeetingTime(day, LocalTime.parse(String.format("%02d",
                Integer.parseInt(openHourTextField.getText())) + ":" + openMinuteTextField.getText()),
                LocalTime.parse(String.format("%02d", Integer.parseInt(closeHourTextField.getText())) + ":"
                        + closeMinuteTextField.getText()));
    }

    private void updateDisplayForPlaceTimes(Place p, DayOfWeek day) {
        populateAndDisplaySessionsOnDay(p, day);
        scheduleOfSelectedPlaceTextArea.setText(InfoPrinter.printSchedule(p.getSchedule()));
        overviewPlaceTextField.setText(p.getName());
    }

    private void assignListViewEvents(ListView<String> libraryListView, ListView<String> learningCentreListView,
                                      PlaceManager libManager, PlaceManager lcManager, ListView<DayOfWeek> days) {
        setPlaceManagerListViewEvent(libraryListView, libManager, days);
        setPlaceManagerListViewEvent(learningCentreListView, lcManager, days);
//        learningCentreListView.setOnMouseClicked(event ->
//                System.out.println(learningCentreListView.getSelectionModel().getSelectedItem()));
    }

    private void setPlaceManagerListViewEvent(ListView<String> listView, PlaceManager placeManager,
                                              ListView<DayOfWeek> days) {
        listView.setOnMouseClicked(event -> {
                    String placeName = listView.getSelectionModel().getSelectedItem();
                    Place selectedPlace = placeManager.getPlace(placeName);
                    displayWhenPlaceSelected(days, placeName, selectedPlace);

                    //3:08PM, AUG8
                    if (!(selectedPlace == null)) {
                        if (!(selectedPlace.getAddress() == null)) {
                            try {
                                double[] coord = getLatLngOfPlace(selectedPlace);
                                if (!(coord == null)) {
                                    //webEngine.executeScript("centerMapAt(" + null + "," + null + ");");
                                    webEngine.executeScript("centerMapAt(" + coord[0] + "," + coord[1] + ");");
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }


                    // UNCOMMENT FOR DEMO
//                    if (selectedPlace != null) {
//                        System.out.println(placeName);
//                        //loadMapTabByURL("https://www.youtube.com/");
//                        if (selectedPlace.getAddress() != null) {
//                            try {
//                                double[] latlng = getLatLngOfPlace(selectedPlace);
////                                System.out.println("LATITIUDE=" + latlng[0]);
////                                System.out.println("LONGITUDE=" + latlng[1]);
//                                loadMapTabByURL("https://www.google.com/maps/search/" + latlng[0] + ",+" + latlng[1]);
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
                }
        );
    }

    private void displayWhenPlaceSelected(ListView<DayOfWeek> days, String placeName, Place selectedPlace) {
        setTextOfPlaceTextFields(placeName, selectedPlace);
        populateAndDisplaySessionsOnDay(selectedPlace, days.getSelectionModel().getSelectedItem());
        handleSelectBlockThenChangeDayOrPlace();
    }

    private void setTextOfPlaceTextFields(String placeName, Place selectedPlace) {
        if (!(selectedPlace == null)) {
            scheduleOfSelectedPlaceTextArea.setText(InfoPrinter.printSchedule(selectedPlace.getSchedule()));
            //.trim().replaceAll("\t", ""));
            //selectedPlaceTextArea.setText(placeName);
            overviewPlaceTextField.setText(placeName);
            placeNameTextField.setText(placeName);
            Address address = selectedPlace.getAddress();
            if (!(address == null)) {
                streetAddressTextField.setText(address.getStreet());
                cityTextField.setText(address.getCity());
                provinceTextField.setText(address.getProvince());
            } else {
                streetAddressTextField.setText("");
            }
            /*DayOfWeek day = placeDayListView.getSelectionModel().getSelectedItem();
            String tb = placeSessionsListView.getSelectionModel().getSelectedItem();
            //if (!(selectedPlace.getSchedule().getTimeBlocksForDay(day) == null)) {
            if (!(tb == null)) {
                // FIXME: add this section to event listener for clicking on a session in the placeSessionsLV
                //openHourSpinner.getValueFactory().setValue(Integer.parseInt(tb.substring(0, 2)));
                //openMinuteSpinner.getValueFactory().setValue(Integer.parseInt(tb.sub))
            }
            //}*/
        }
    }

    private void displayAboutAlert() {
        Alert about = new Alert(INFORMATION, "Author: Maggie Yang\nCPSC 210 Personal Project, Summer 2019\n\n"
                + "APIs:\nGoogle Geocoding API\nGoogle Maps JavaScript API\n\n"
                + "References:\nCode Makery,\t\tcode.makery.ch/blog/javafx-dialogs-official/\n"
                + "Google,\t\t\tdevelopers.google.com/maps/documentation/javascript/examples/marker-labels\n"
                + "Haggra,\t\t\tstackoverflow.com/questions/15695984/java-print-contents-of-text-file-to-screen\n"
                + "Jimmy Lu,\t\tgithub.students.cs.ubc.ca/jmlu99/FileIODemo\n"
                + "Roland Illig,\t\tstackoverflow.com/questions/4308554/simplest-way-to-read-json-from-a-url-in-java\n"
                + "Spring Breaker,\t"
                + "stackoverflow.com/questions/18977144/how-to-parse-json-array-not-json-object-in-android\n"
                + "vlopezla,\t\t\t"
                + "stackoverflow.com/questions/23011718/show-marker-in-a-google-map-in-javafx-desktop-app\n\n\n"
                + "Thanks for trying the StudyBuddy!");
        about.setHeaderText("About");
        about.setTitle("About");
        about.getDialogPane().setPrefWidth(750);
        about.showAndWait();
    }

    public static void displayUserGuide() {
        Alert userGuide = new Alert(INFORMATION, "Welcome to the Study Buddy!"
                + " Here are some tips to get you started.\n"
                + "\nMap viewer:\n- Right-click the Map tab label to toggle marker visibility.\n- Click on a place name"
                + " to center the map there.\n- Accidentally click on a link in the map viewer? "
                + "Go to Reset > Reload map."
                + "\n\nEditing places:\n- Use the quick add button at the bottom of the lefthand dropdown menus "
                + "to add a new place.\n- See your study place on the map by first adding an address from the "
                + "Edit Place tab."
                + "\n\nSaving and loading:\n- See the File menu at the top left for save and load options.");
        userGuide.setHeaderText("Welcome!");
        userGuide.setTitle("User Guide");
        userGuide.showAndWait();
    }



/*    private void populateLists(ObservableList<String> libList, ObservableList<String> lcList, PlaceManager pm) {
//        for (Place p : pm.getPlaces()) {
//            if (p instanceof Library) {
//                libList.add(p.getName());
//            } else if (p instanceof LearningCentre) {
//                lcList.add(p.getName());
//            }
//        }
        for (Place p : pm) {
            if (p instanceof Library) {
                libList.add(p.getName());
            } else if (p instanceof LearningCentre) {
                lcList.add(p.getName());
            }
        }
    }*/

    private void populatePlacesList(ObservableList<String> list, PlaceManager pm) {
//        for (Place p : pm.getPlaces()) {
//            list.add(p.getName());
//        }
        for (Place p : pm) {
            list.add(p.getName());
        }
    }

    private void populateDayOfWeekList(ObservableList<DayOfWeek> list) {
        list.add(SUNDAY);
        list.add(MONDAY);
        list.add(TUESDAY);
        list.add(WEDNESDAY);
        list.add(THURSDAY);
        list.add(FRIDAY);
        list.add(SATURDAY);
    }

    private void populateSessionsOnDayList(ObservableList<String> list, Place p, DayOfWeek day) {
        Schedule sch = p.getSchedule();
        if (sch.getSchedule().containsKey(day)) {
            for (TimeBlock t : p.getSchedule().getTimeBlocksForDay(day)) {
                list.add(t.getStartTime() + "-" + t.getEndTime());
            }
        }
        list.add(ADD_NEW_SESSION);
    }

    private static void sayHello() {
        System.out.println("You pressed a button.");
    }

    private void loadMapTabByURL(String url) {
        webEngine.load(url);
        mapTab.setContent(mapBrowser);
    }

    private void loadMapTab() {
        webEngine.load("https://www.google.com/");
        // webEngine.load("http://localhost:63342/study_buddy/ui/MapBrowserDraft.html?
        //          _ijt=527k5jfansf2ejms6jhf06alpv");
        // webEngine.load("http://localhost:63342/study_buddy/ui/MapBrowserDraft2.html?
        //          _ijt=8mb4f9475eq8dnfmdu9g4u2drl");
        // TODO note ----- DO NOT UNCOMMENT THIS UNLESS WORKING ON MAP
        // TODO - rename this html file. This is not a static map!
//        webEngine.load("http://localhost:63342/study_buddy/ui/GoogleStaticMapsDemo.html?_ijt="
//                + "3q8k703a3fko5b7nfk9t6ub2de");
        mapTab.setContent(mapBrowser);
        System.out.println("Content should be loaded");
    }

    /*// CREDITS TO edX deliverable 10 pg, which quoted http://zetcode.com/articles/javareadwebpage/
    private void readGoogleMapsGeocodeQuery(Address a) throws IOException {
        String address = a.makeQueryableAddress();
        // System.out.println(address);

        BufferedReader br = null;

        try {
            URL urlToParse = new URL("https://www.google.com/");
//            URL urlToParse = new URL(googleMapsGeocodingQueryPreAddress + address
//                    + QUERY_POST_ADDRESS); //APIKEY
//            URL urlToParse = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=2366+Main+Mall,+"
//                    + "Vancouver,+BC&key=KEY_REMOVED_!!!!"); //APIKEY
            br = new BufferedReader(new InputStreamReader(urlToParse.openStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }
            System.out.println(sb);
        } finally {
            if (br != null) {
                br.close();
            }
        }


    } // CREDITS TO END FROM ZETCODE*/


    // V CREDITSTO roland illig -- stackoverflow.com/questions/4308554/simplest-way-to-read-json-from-a-url-in-java
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    } // V END CREDITSTO roland illig

    private void loadDataIntoTextArea(PlaceManager lm, PlaceManager lcm, CourseManager cm) {
        System.out.println("Loading data...");
        librariesTextArea.setText(lm.printAllPlaces().trim());
        learningCentresTextArea.setText(lcm.printAllPlaces().trim());
        //scheduleTextArea.setText(InfoPrinter.printScheduleForAllCourses(cm));//.trim());
    }

//    private void loadDataIntoTextArea(PlaceManager pm, CourseManager cm) {
//        System.out.println("Loading data...");
//        //studyPlacesTextArea.setText(pm.printAllPlaces()); // + "\n\n" + lcm.printAllPlaces());
//        scheduleTextArea.setText(InfoPrinter.printScheduleForAllCourses(cm));
//    }

    @Override
    public void update(Place p) {
        System.out.println(p.getName() + " was added successfully!");
        if (p instanceof Library) {
            librariesTextArea.appendText("\n" + p.getName());
            libraryListView.getItems().add(p.getName());
        } else if (p instanceof LearningCentre) {
            learningCentresTextArea.appendText("\n" + p.getName());
            learningCentreListView.getItems().add(p.getName());
        }
    }

    /*public void updateDisplayedPlaceInfo(Place p, PlaceManager libManager, PlaceManager lcManager) {
        populateAndDisplayBothPlaceLists(libManager, lcManager);
    }*/


    //
//        String content = "abalajkdfajfdl";
//        webEngine.loadContent(content, "text/html");

    //webEngine.load("https://www.google.com/");

    // TO DO: have program open the html file, read the url, and load it into the webengine
    // because this url may change for different users with different server number things

    // How I got this url: run the desired html file in chrome, copy url to here.


    //webEngine.load("http://localhost:63342/study_buddy/ui/MapBrowser.html?_ijt=l0tagrmjnvt1jvquh1ortaopj0");
    //webEngine.load("http://localhost:63342/study_buddy/ui/MapBrowserDraft.html?_ijt=89ghkmhm6gq3hv2uqia1gkduco");


    //webEngine.load("http://localhost:63342/study_buddy/ui/MapBrowser.html?_ijt=89ghkmhm6gq3hv2uqia1gkduco");
//        System.out.println(mapBrowser.getEngine());
//        System.out.println(webEngine);


    // Handle Button event.
//        saveLibrariesMenuItem.setOnAction(event -> { //this overwrites the action I added from scenebuilder?? FIXME
//            System.out.println("THis is coded from Intellij, not scene builder");
//        });

}
