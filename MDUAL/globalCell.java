package MDUAL;
import loader.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class SpatialGridCell {
    private HashMap<ArrayList<Short>, Double> neighboringCellDistances;
    private ArrayList<Short> cellIndex;
    private int cardinality;
    private boolean isUpdatedRecently;
    private HashMap<Integer, Integer> cardinalityPerSlide; 

    public SpatialGridCell(ArrayList<Short> cellIndex) {
        this.cellIndex = new ArrayList<>(cellIndex);
        this.cardinality = 0;
        this.neighboringCellDistances = new HashMap<>();
        this.isUpdatedRecently = false;
        this.cardinalityPerSlide = new HashMap<>();
    }

    public int computeTotalCardinalityFromSlide(int initialSlideID) {
        return cardinalityPerSlide.entrySet().stream()
                                  .filter(entry -> entry.getKey() >= initialSlideID)
                                  .mapToInt(HashMap.Entry::getValue)
                                  .sum();
    }

    public ArrayList<ArrayList<Short>> getCellsWithinDistanceThreshold(double distanceThreshold, boolean inclusive) {
        ArrayList<ArrayList<Short>> thresholdCells = new ArrayList<>();
        for (HashMap.Entry<ArrayList<Short>, Double> entry : neighboringCellDistances.entrySet()) {
            if (inclusive && entry.getValue() <= distanceThreshold || !inclusive && entry.getValue() < distanceThreshold) {
                thresholdCells.add(entry.getKey());
            }
        }
        return thresholdCells;
    }
}

}