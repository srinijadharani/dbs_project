package MDUAL;

import java.util.ArrayList;
import java.util.stream.IntStream;
import loader.Tuple;

public class SpatialAnalytics {

    public static double calculateTupleDistance(Tuple tuple1, Tuple tuple2) {
        return Math.sqrt(calculateSquaredDistance(tuple1.value, tuple2.value));
    }

    public static double calculateTupleDistanceWithThreshold(Tuple tuple1, Tuple tuple2, double threshold) {
        double thresholdSquared = Math.pow(threshold, 2);
        double distanceSquared = calculateSquaredDistance(tuple1.value, tuple2.value);

        return distanceSquared > thresholdSquared ? Double.MAX_VALUE : Math.sqrt(distanceSquared);
    }

    public static boolean areTuplesNeighbors(Tuple tuple1, Tuple tuple2, double threshold) {
        return calculateSquaredDistance(tuple1.value, tuple2.value) <= Math.pow(threshold, 2);
    }

    public static boolean areCellsNeighbors(ArrayList<Short> cell1, ArrayList<Short> cell2, double minRadius, double threshold) {
        return calculateNormalizedCellDistance(cell1, cell2, minRadius) <= threshold;
    }

    private static double calculateNormalizedCellDistance(ArrayList<Short> cell1, ArrayList<Short> cell2, double minRadius) {
        double sumOfSquares = IntStream.range(0, cell1.size())
                                       .mapToDouble(i -> Math.pow(cell1.get(i) - cell2.get(i), 2))
                                       .sum();
        return Math.sqrt(sumOfSquares / cell1.size()) * minRadius;
    }

    private static double calculateSquaredDistance(double[] values1, double[] values2) {
        return IntStream.range(0, values1.length)
                        .mapToDouble(i -> Math.pow(values1[i] - values2[i], 2))
                        .sum();
    }
}
