package utils;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Utils {
    public static String prettyString(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
    }

    public static GridPane buildGridPane(){
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        return grid;
    }
}
