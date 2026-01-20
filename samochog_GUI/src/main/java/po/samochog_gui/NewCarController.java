package po.samochog_gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
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

    @FXML
    private void handleCreateCar(ActionEvent event) {
        try {
            // 1. Walidacja pustych pól
            if (isAnyFieldEmpty()) {
                showAlert("Błąd formularza", "Wszystkie pola muszą być wypełnione!");
                return;
            }

            // 2. Walidacja unikalności rejestracji
            String rej = carReg.getText().trim();
            if (isRegistrationDuplicate(rej)) {
                showAlert("Błąd danych", "Samochód o rejestracji " + rej + " już istnieje w systemie!");
                return;
            }

            // --- 3. TWORZENIE KOMPONENTÓW ---
            Silnik sil = new Silnik();
            sil.setNazwa(engineName.getText());
            sil.setCena(Double.parseDouble(enginePrice.getText()));
            sil.setWaga(parseInteger(engineWeight.getText()));

            SkrzyniaBiegow sk = new SkrzyniaBiegow();
            sk.setNazwa(gearboxName.getText());
            sk.setCena(Double.parseDouble(gearboxPrice.getText()));
            sk.setWaga(parseInteger(gearboxWeight.getText()));

            Sprzeglo spr = new Sprzeglo();
            spr.setNazwa(clutchName.getText());
            spr.setCena(Double.parseDouble(clutchPrice.getText()));
            spr.setWaga(parseInteger(clutchWeight.getText()));

            // --- 4. TWORZENIE SAMOCHODU ---
            String model = carModel.getText();
            int wagaAuta = parseInteger(carWeight.getText());
            Pozycja startowa = new Pozycja(100, 100); // Startowa pozycja na mapie

            Samochod auto = new Samochod(model, rej, spr, sil, sk, startowa, wagaAuta);

            // --- 5. FINALIZACJA ---
            CarData.dodajSamochod(auto);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            showAlert("Błąd formatu", "Pola ceny i wagi muszą zawierać poprawne liczby!");
        }
    }

    /**
     * Sprawdza, czy którekolwiek z pól TextField jest puste.
     */
    private boolean isAnyFieldEmpty() {
        List<TextField> fields = Arrays.asList(
                carModel, carReg, carWeight,
                engineName, enginePrice, engineWeight,
                gearboxName, gearboxPrice, gearboxWeight,
                clutchName, clutchPrice, clutchWeight
        );

        return fields.stream().anyMatch(f -> f.getText() == null || f.getText().trim().isEmpty());
    }

    /**
     * Sprawdza, czy podana rejestracja już istnieje na liście (ignoruje wielkość liter).
     */
    private boolean isRegistrationDuplicate(String newReg) {
        return CarData.getListaSamochodow().stream()
                .anyMatch(s -> s.getNrRejestracyjny().equalsIgnoreCase(newReg));
    }

    /**
     * Wyświetla okienko z błędem.
     */
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