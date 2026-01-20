package po.samochog_gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import symulator.Samochod;

import java.io.IOException;

public class HelloController {


    @FXML private ImageView carIcon;

    @FXML private ComboBox<Samochod> carComboBox;

    @FXML private TextField carModel, carReg, carWeight, carSpeed;
    @FXML private TextField gearboxName, gearboxPrice, gearboxWeight, gearboxGear;
    @FXML private TextField engineName, enginePrice, engineWeight, engineRPM;
    @FXML private TextField clutchName, clutchPrice, clutchWeight, clutchState;

    private double mouseX;
    private double mouseY;

    @FXML
    public void initialize() {
        //  Ładowanie ikony
        try {
            var resource = getClass().getResourceAsStream("/po/samochog_gui/carIcon.jfif.png");
            if (resource != null) {
                Image img = new Image(resource);
                carIcon.setImage(img);
            } else {
                System.out.println("Nie znaleziono pliku carlcon.png w resources!");
            }
        } catch (Exception e) {
            System.out.println("Błąd ładowania obrazka: " + e.getMessage());
        }

        //  obsługa ComboBox
        carComboBox.setItems(CarData.getListaSamochodow());
        carComboBox.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Samochod item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getModel() + " [" + item.getNrRejestracyjny() + "]");
            }
        });
        carComboBox.setButtonCell(carComboBox.getCellFactory().call(null));

        carComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                updateUI(newVal);
                // Ustaw ikonę na pozycji startowej auta
                if (newVal.getPozycja() != null) {
                    carIcon.setLayoutX(newVal.getPozycja().getX());
                    carIcon.setLayoutY(newVal.getPozycja().getY());
                }
            }
        });

        if (CarData.getListaSamochodow().isEmpty()) {
            clearUI();
        }

        //  Uruchomienie wątku
        startMovementThread();
    }

    private void updateUI(Samochod s) {
        carModel.setText(s.getModel());
        carReg.setText(s.getNrRejestracyjny());
        carWeight.setText(s.getWaga() + " kg");
        carSpeed.setText(s.getPredkosc() + " km/h");

        engineName.setText(s.getSilnik().getModel());
        enginePrice.setText(String.valueOf(s.getSilnik().getCena()));
        engineWeight.setText(s.getSilnik().getWaga() + " kg");
        engineRPM.setText(String.valueOf(s.getSilnik().getObroty()));

        gearboxName.setText(s.getSkrzynia().getModel());
        gearboxPrice.setText(String.valueOf(s.getSkrzynia().getCena()));
        gearboxWeight.setText(s.getSkrzynia().getWaga() + " kg");
        gearboxGear.setText(String.valueOf(s.getSkrzynia().getAktualnyBieg()));

        clutchName.setText(s.getSprzeglo().getModel());
        clutchPrice.setText(String.valueOf(s.getSprzeglo().getCena()));
        clutchWeight.setText(s.getSprzeglo().getWaga() + " kg");
        clutchState.setText(s.getSprzeglo().getStan() ? "Wciśnięte" : "Wolne");
    }

    private void clearUI() {
        carModel.setText(""); carReg.setText(""); carWeight.setText(""); carSpeed.setText("");
        engineName.setText(""); gearboxName.setText(""); clutchName.setText("");
    }

    @FXML
    private void handleOpenCreator(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Car-Creator.fxml"));
        Parent root = loader.load();
        Stage creatorStage = new Stage();
        creatorStage.setScene(new Scene(root));
        creatorStage.setTitle("Kreator Nowego Samochodu");
        Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        creatorStage.initOwner(mainStage);
        creatorStage.initModality(Modality.APPLICATION_MODAL);
        creatorStage.showAndWait();

        if (!CarData.getListaSamochodow().isEmpty()) {
            carComboBox.getSelectionModel().selectLast();
        }
    }
    @FXML
    private void handleDeleteOperation(ActionEvent event) throws IOException {
        Samochod wybranySamochod = carComboBox.getSelectionModel().getSelectedItem();


        if (wybranySamochod != null) {

            // alert!
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Usuwanie samochodu");
            alert.setHeaderText("Czy na pewno chcesz usunąć wybrany samochód?");
            alert.setContentText("Model: " + wybranySamochod.getModel() + "\nRejestracja: " + wybranySamochod.getNrRejestracyjny());

            // ok
            if (alert.showAndWait().get().getButtonData().isDefaultButton()) {

                CarData.usunSamochod(wybranySamochod);
                clearCarDetails();

                carIcon.setVisible(false);

                System.out.println("Samochód został usunięty pomyślnie.");
            }
        } else {
            // nie wybrano nic
            Alert errorAlert = new Alert(Alert.AlertType.WARNING);
            errorAlert.setTitle("Błąd");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Najpierw wybierz samochód z listy, aby go usunąć!");
            errorAlert.showAndWait();
        }
    }


    private void clearCarDetails() {
        carModel.clear();
        carReg.clear();
        carWeight.clear();
        carSpeed.clear();
        engineName.clear();
        engineRPM.clear();
        gearboxName.clear();
        gearboxGear.clear();
        clutchState.clear();
        // Dodaj tu inne pola, które masz w widoku
    }

    @FXML
    protected void onHelloButtonClick() {
        Samochod wybrany = carComboBox.getSelectionModel().getSelectedItem();
        if (wybrany != null) {
            wybrany.wlacz();
            updateUI(wybrany);
        }
    }

    @FXML
    protected void onExitButtonClick() {
        Samochod wybrany = carComboBox.getSelectionModel().getSelectedItem();
        if (wybrany != null) {
            wybrany.wylacz();
            updateUI(wybrany);
        }
    }

    @FXML
    protected void increaseGear() {
        Samochod s = carComboBox.getSelectionModel().getSelectedItem();
        if (s != null) {
            s.getSkrzynia().zwiekszBieg();
            s.aktualizujPredkosc();
            updateUI(s);
        }
    }

    @FXML
    protected void decreaseGear() {
        Samochod s = carComboBox.getSelectionModel().getSelectedItem();
        if (s != null) {
            s.getSkrzynia().zmniejszBieg();
            updateUI(s);
        }
    }

    @FXML
    protected void handleAddGaz() {
        Samochod s = carComboBox.getSelectionModel().getSelectedItem();
        if (s != null) {
            int obecne = s.getSilnik().getObroty();
            if (obecne < 7000) { // Ogranicznik obrotów
                s.getSilnik().setObroty(obecne + 500);
            }
            s.aktualizujPredkosc();
            updateUI(s);
        }
    }

    @FXML
    protected void handleSubtractGaz() {
        Samochod s = carComboBox.getSelectionModel().getSelectedItem();
        if (s != null) {
            int obecne = s.getSilnik().getObroty();
            if (obecne >= 500) s.getSilnik().setObroty(obecne - 500);
            updateUI(s);
        }
    }

    @FXML
    protected void handleClutchClick() {
        Samochod s = carComboBox.getSelectionModel().getSelectedItem();
        if (s != null) {
            s.getSprzeglo().setStan(true);
            updateUI(s);
        }
    }

    @FXML
    protected void handleClutchUnclick() {
        Samochod s = carComboBox.getSelectionModel().getSelectedItem();
        if (s != null) {
            s.getSprzeglo().setStan(false);
            updateUI(s);
        }
    }


    @FXML
    private void handleMouseMoved(MouseEvent event) {
        mouseX = event.getX();
        mouseY = event.getY();
    }
    private void startMovementThread() {
        Thread uiThread = new Thread(() -> {
            try {
                while (true) {
                    Samochod s = carComboBox.getSelectionModel().getSelectedItem();

                    if (s != null && s.getPozycja() != null) {

                        if (s.isWlaczony()) {
                            s.setCel(mouseX, mouseY);
                            if (s.getState() == Thread.State.NEW) {
                                s.start();
                            }
                        }

                        // zmiana grafiki
                        Platform.runLater(() -> {

                            carIcon.setVisible(true);

                            // sledzenie pozycji
                            carIcon.setLayoutX(s.getPozycja().getX() - (carIcon.getFitWidth() / 2));
                            carIcon.setLayoutY(s.getPozycja().getY() - (carIcon.getFitHeight() / 2));

                            if (s.isWlaczony()) {
                                // Obrót i
                                if (s.getPredkosc() > 0) {
                                    double angle = Math.toDegrees(Math.atan2(mouseY - s.getPozycja().getY(), mouseX - s.getPozycja().getX()));
                                    carIcon.setRotate(angle + 90);
                                }
                                carSpeed.setText(s.getPredkosc() + " km/h");
                                engineRPM.setText(String.valueOf(s.getSilnik().getObroty()));
                            } else {
                                carSpeed.setText("0 km/h");
                            }
                        });
                    }
                    // schowanie ikonki
                    else {

                        Platform.runLater(() -> carIcon.setVisible(false));
                    }

                    Thread.sleep(20);
                }
            } catch (InterruptedException e) {
                System.err.println("Wątek UI przerwany.");
            }
        });

        uiThread.setDaemon(true);
        uiThread.start();
    }

}