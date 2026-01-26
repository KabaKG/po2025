package po.samochog_gui;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import symulator.*;

import java.util.Arrays;
import java.util.List;

public class NewCarController {

    @FXML private TextField carModel, carReg, carWeight;
    @FXML private TextField engineName, enginePrice, engineWeight;
    @FXML private TextField gearboxName, gearboxPrice, gearboxWeight;
    @FXML private TextField clutchName, clutchPrice, clutchWeight;
    @FXML private ComboBox<Integer> maxGearsCombo;




    @FXML
    private void handleCreateCar(ActionEvent event) {
        try {
            // czy są puste
            if (isAnyFieldEmpty()) {
                showAlert("Błąd formularza", "Wszystkie pola muszą być wypełnione!");
                return;
            }

            // czy rejestracja sie nie powtarza
            String rej = carReg.getText().trim();
            if (isRegistrationDuplicate(rej)) {
                showAlert("Błąd danych", "Samochód o rejestracji " + rej + " już istnieje w systemie!");
                return;
            }

            // tworzenie komponentow
            //int maxGears = maxGearsCombo.getValue();

            Silnik sil = new Silnik();
            sil.setNazwa(engineName.getText());
            sil.setCena(Double.parseDouble(enginePrice.getText()));
            sil.setWaga(parseInteger(engineWeight.getText()));

            SkrzyniaBiegow sk = new SkrzyniaBiegow();
            sk.setNazwa(gearboxName.getText());
            sk.setCena(Double.parseDouble(gearboxPrice.getText()));
            sk.setWaga(parseInteger(gearboxWeight.getText()));
           //sk.setIloscBiegow(maxGears);

            Sprzeglo spr = new Sprzeglo();
            spr.setNazwa(clutchName.getText());
            spr.setCena(Double.parseDouble(clutchPrice.getText()));
            spr.setWaga(parseInteger(clutchWeight.getText()));

            // tworzenie calego samochodu
            String model = carModel.getText();
            int wagaAuta = parseInteger(carWeight.getText());
            Pozycja startowa = new Pozycja(100, 100); // Startowa pozycja na mapie

            Samochod auto = new Samochod(model, rej, spr, sil, sk, startowa, wagaAuta);

            
            CarData.dodajSamochod(auto);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            showAlert("Błąd formatu", "Pola ceny i wagi muszą zawierać poprawne liczby!");
        }
    }


     //Sprawdzenie czy pola są puste
    private boolean isAnyFieldEmpty() {
        List<TextField> fields = Arrays.asList(
                carModel, carReg, carWeight,
                engineName, enginePrice, engineWeight,
                gearboxName, gearboxPrice, gearboxWeight,
                clutchName, clutchPrice, clutchWeight
        );

        return fields.stream().anyMatch(f -> f.getText() == null || f.getText().trim().isEmpty());
    }


     //Sprawdza, czy podana rejestracja już istnieje na liście

    private boolean isRegistrationDuplicate(String newReg) {
        return CarData.getListaSamochodow().stream()
                .anyMatch(s -> s.getNrRejestracyjny().equalsIgnoreCase(newReg));
    }


     // okienko z błędem.

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private int parseInteger(String text) {
        String cleaned = text.replaceAll("[^0-9]", "");
        return cleaned.isEmpty() ? 0 : Integer.parseInt(cleaned);
    }






}


