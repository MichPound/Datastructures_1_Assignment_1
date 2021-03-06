package Assignment;

import Lists.CustomList;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Main extends Application {

    public static Scene main, addShow, addPerformance, addBooking, viewFacility, cancelFacility, cancelShow, cancelPerformance, cancelBooking;
    private static Stage setStage;
    public static CustomList<Show> shows = new CustomList<>();

    @Override
    public void start(Stage primaryStage) throws Exception {

        setStage = primaryStage;
        primaryStage.setTitle("Assignment_1");

        //Setting up each scene
        Parent root1 = FXMLLoader.load(getClass().getResource("../fxml/main.fxml"));
        main = new Scene(root1, 210, 400);
        root1.getStylesheets().add(getClass().getResource("../Style/styleSheet.css").toExternalForm());

        Parent root2 = FXMLLoader.load(getClass().getResource("../fxml/addShow.fxml"));
        addShow = new Scene(root2, 369, 342);
        root2.getStylesheets().add(getClass().getResource("../Style/styleSheet.css").toExternalForm());

        Parent root3 = FXMLLoader.load(getClass().getResource("../fxml/addPerformance.fxml"));
        addPerformance = new Scene(root3, 359, 326);
        root3.getStylesheets().add(getClass().getResource("../Style/styleSheet.css").toExternalForm());

        Parent root4 = FXMLLoader.load(getClass().getResource("../fxml/addBooking.fxml"));
        addBooking = new Scene(root4, 600, 500);
        root4.getStylesheets().add(getClass().getResource("../Style/styleSheet.css").toExternalForm());

        Parent root5 = FXMLLoader.load(getClass().getResource("../fxml/viewFacilities.fxml"));
        viewFacility = new Scene(root5, 810, 475);
        root5.getStylesheets().add(getClass().getResource("../Style/styleSheet.css").toExternalForm());

        Parent root6 = FXMLLoader.load(getClass().getResource("../fxml/cancelFacility.fxml"));
        cancelFacility = new Scene(root6, 174, 255);
        root6.getStylesheets().add(getClass().getResource("../Style/styleSheet.css").toExternalForm());

        Parent root7 = FXMLLoader.load(getClass().getResource("../fxml/cancelShow.fxml"));
        cancelShow = new Scene(root7, 235, 310);
        root7.getStylesheets().add(getClass().getResource("../Style/styleSheet.css").toExternalForm());

        Parent root8 = FXMLLoader.load(getClass().getResource("../fxml/cancelPerformance.fxml"));
        cancelPerformance = new Scene(root8, 235, 374);
        root8.getStylesheets().add(getClass().getResource("../Style/styleSheet.css").toExternalForm());

        Parent root9 = FXMLLoader.load(getClass().getResource("../fxml/cancelBooking.fxml"));
        cancelBooking = new Scene(root9, 600, 365);
        root9.getStylesheets().add(getClass().getResource("../Style/styleSheet.css").toExternalForm());

        setStage.setScene(main);
        primaryStage.show();
    }

    //These methods allow easy scene changing and also call start methods in some scenes
    static void setMain() {
        setStage.setScene(main);
    }

    static void setAddShow() {
        setStage.setScene(addShow);
    }

    static void setAddPerformance() {
        setStage.setScene(addPerformance);
    }

    static void setAddBooking() {
        setStage.setScene(addBooking);
        ControllerAddBooking.addBookingController.startView();
    }

    static void setViewFacility() {
        setStage.setScene(viewFacility);
        ControllerViewFacilities.viewFacilitiesController.startView();
    }

    static void setCancelFacility() {
        setStage.setScene(cancelFacility);
    }

    static void setCancelShow() {
        setStage.setScene(cancelShow);
    }

    static void setCancelPerformance() {
        setStage.setScene(cancelPerformance);
        ControllerCancelPerformance.cancelPerformanceController.startView();
    }

    static void setCancelBooking() {
        setStage.setScene(cancelBooking);
        ControllerCancelBooking.cancelBookingController.startView();
    }

    //Updates listViews through out project with the newest version of shows
    static void updateShows() {
        ControllerAddPerformance.addPerformanceController.selectShow.getItems().clear();
        ControllerViewFacilities.viewFacilitiesController.viewShows.getItems().clear();
        ControllerCancelShow.cancelShowController.removeShow.getItems().clear();
        ControllerCancelPerformance.cancelPerformanceController.selectShow.getItems().clear();
        ControllerAddBooking.addBookingController.bookShow.getItems().clear();
        ControllerCancelBooking.cancelBookingController.viewShows.getItems().clear();

        for (Show s : shows) {
            ControllerAddPerformance.addPerformanceController.selectShow.getItems().add(s.getTitle() + ", " + s.getsDate() + " to " + s.geteDate() + ", " + s.getTime() + " Minutes");
            ControllerViewFacilities.viewFacilitiesController.viewShows.getItems().add(s.getTitle());
            ControllerCancelShow.cancelShowController.removeShow.getItems().add(s.getTitle() + ", " + s.getsDate() + " to " + s.geteDate());
            ControllerCancelPerformance.cancelPerformanceController.selectShow.getItems().add(s.getTitle() + ", " + s.getsDate() + " to " + s.geteDate());
            ControllerAddBooking.addBookingController.bookShow.getItems().add(s.getTitle());
            ControllerCancelBooking.cancelBookingController.viewShows.getItems().add(s.getTitle());
        }
    }

    static Show listOfShows(int input) {
        Show theShow = shows.get2(input);
        return theShow;
    }

    static Performance listOfPerformances(int input, int input2) {
        Performance thePerformance = listOfShows(input).getPerformances().get2(input2);
        return thePerformance;
    }

    static Booking listOfBookings(int input, int input2, int input3) {
        Booking theBooking = listOfPerformances(input, input2).getBooking().get2(input3);
        return theBooking;
    }

    //Saves to an xml file
    public static void save() throws Exception {
        XStream xstream = new XStream(new DomDriver());
        ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter("shows.xml"));
        out.writeObject(shows);
        out.close();
    }

    //Reads and casts xml to shows linked list
    public static void load() throws Exception {
        XStream xstream = new XStream(new DomDriver());
        ObjectInputStream is = xstream.createObjectInputStream(new FileReader("shows.xml"));
        shows = (CustomList<Show>) is.readObject();
        is.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
