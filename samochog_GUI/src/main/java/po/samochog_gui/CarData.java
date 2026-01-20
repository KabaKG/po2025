package po.samochog_gui;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import symulator.Samochod;

public class CarData {
    private static final ObservableList<Samochod> listaSamochodow = FXCollections.observableArrayList();

    public static ObservableList<Samochod> getListaSamochodow() {
        return listaSamochodow;
    }
    public static void dodajSamochod(Samochod s) {
        listaSamochodow.add(s);
    }
    public static void usunSamochod(Samochod s) {
        if (s != null) {
            listaSamochodow.remove(s);
        }
    }
}
