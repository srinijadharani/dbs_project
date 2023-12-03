package MDUAL;

import java.util.HashSet;
import loader.DataTuple;
import java.util.ArrayList;
import java.util.HashMap;

public class SpatialCell {
    private HashMap<ArrayList<Short>, SpatialCell> subCells;
    private ArrayList<Short> index;
    private HashSet<DataTuple> dataTuples;
    private double[] centralPoint;

    // Constructor
    public SpatialCell(ArrayList<Short> idx, double[] center) {
        this.index = new ArrayList<>(idx);
        this.centralPoint = center.clone();
        this.dataTuples = new HashSet<>();
        this.subCells = new HashMap<>();
    }

    // Static factory method for creating a SpatialCell with dimension lengths and min values
    public static SpatialCell fromDimensions(ArrayList<Short> idx, double[] dimensions, double[] minValues) {
        double[] center = computeCenter(idx, dimensions, minValues);
        return new SpatialCell(idx, center);
    }

    // Static factory method for creating a SpatialCell with a given center
    public static SpatialCell fromCenter(ArrayList<Short> idx, double[] center) {
        return new SpatialCell(idx, center);
    }

    private static double[] computeCenter(ArrayList<Short> idx, double[] dimensions, double[] minValues) {
        double[] center = new double[dimensions.length];
        for (int i = 0; i < dimensions.length; i++) {
            center[i] = minValues[i] + idx.get(i) * dimensions[i] + dimensions[i] / 2;
        }
        return center;
    }

    public int countDataTuples() {
        return this.dataTuples.size();
    }

    public void includeTupleInSubDimension(DataTuple tuple, double[] dimensions, double[] minValues) {
        double cellTuples;
        this.dataTuples.add(tuple);
        ArrayList<Short> subDimensionIndex = tuple.fullDimensionIndex; // Renamed for clarity

        // Ensuring the creation of a sub-cell if it doesn't exist
        this.subCells.computeIfAbsent(subDimensionIndex, k -> SpatialCell.fromDimensions(k, dimensions, minValues));
        
        // Adding the tuple to the relevant sub-cell
        this.subCells.get(subDimensionIndex).includeTuple(tuple);
    }

    public void includeTuple(DataTuple tuple) {
        this.dataTuples.add(tuple);
    }
}
