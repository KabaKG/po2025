package po.samochog_gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import po.samochog_gui.CarData;
import symulator.Pozycja;
import symulator.Samochod;

import java.io.IOException;

public class HelloController {

    @FXML private Pane mapPane;
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
        // 1. Ładowanie ikony - nazwa pliku musi być identyczna jak w resources (carlcon.jfif)
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

        // 2. Obsługa ComboBox
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

        // 3. Uruchomienie wątku ruchu
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
    protected void onHelloButtonClick() {
        Samochod wybrany = carComboBox.getSelectionModel().getSelectedItem();
        if (wybrany != null) {
            wybrany.wlacz();
            // UWAGA: upewnij się, że masz metodę setPredkosc w klasie Samochod
            // wybrany.setPredkosc(50); // Testowo, żeby ruszył
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
        // Pobieramy aktualną pozycję myszy względem panelu mapy
        mouseX = event.getX();
        mouseY = event.getY();
    }

    private void startMovementThread() {
        Thread moveThread = new Thread(() -> {
            try {
                while (true) {
                    Samochod s = carComboBox.getSelectionModel().getSelectedItem();

                    if (s != null && s.isWlaczony() && s.getPozycja() != null) {

                        // 1. Obliczamy aktualną prędkość wynikającą z obrotów i biegu
                        s.aktualizujPredkosc();
                        double predkosc = s.getPredkosc();

                        // 2. SPRAWDZENIE ODLEGŁOŚCI OD MYSZKI
                        double dx = mouseX - s.getPozycja().getX();
                        double dy = mouseY - s.getPozycja().getY();
                        double odleglosc = Math.sqrt(dx * dx + dy * dy);

                        // Jeśli jesteśmy bardzo blisko myszki (np. 5px), zatrzymujemy auto
                        if (odleglosc < 5.0) {
                            predkosc = 0;
                        }

                        if (predkosc > 0) {
                            // Zapamiętujemy starą pozycję na wypadek uderzenia w krawędź
                            double staraX = s.getPozycja().getX();
                            double staraY = s.getPozycja().getY();

                            // Wykonujemy ruch
                            s.getPozycja().moveTo((float)predkosc, 0.02f, new Pozycja(mouseX, mouseY));

                            // 3. SPRAWDZENIE KRAWĘDZI MAPY (mapPane)
                            // Zakładamy, że auto nie może wyjechać poza mapPane
                            if (s.getPozycja().getX() < 0 || s.getPozycja().getX() > mapPane.getWidth() ||
                                    s.getPozycja().getY() < 0 || s.getPozycja().getY() > mapPane.getHeight()) {

                                // Jeśli przekroczyło krawędź, cofnij do ostatniej dobrej pozycji i zatrzymaj
                                s.getPozycja().setX(staraX);
                                s.getPozycja().setY(staraY);
                                predkosc = 0;
                            }

                            // 4. Aktualizacja grafiki
                            final double finalPredkosc = predkosc;
                            Platform.runLater(() -> {
                                carIcon.setLayoutX(s.getPozycja().getX() - (carIcon.getFitWidth() / 2));
                                carIcon.setLayoutY(s.getPozycja().getY() - (carIcon.getFitHeight() / 2));

                                if (finalPredkosc > 0) {
                                    double angle = Math.toDegrees(Math.atan2(mouseY - s.getPozycja().getY(), mouseX - s.getPozycja().getX()));
                                    carIcon.setRotate(angle + 90);
                                }
                                carSpeed.setText((int)finalPredkosc + " km/h");
                            });
                        }
                    }
                    Thread.sleep(20);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        moveThread.setDaemon(true);
        moveThread.start();
    }
}