package util;

import com.carrotsearch.hppc.IntArrayList;
import grph.Grph;
import grph.algo.distance.DistanceMatrix;
import grph.path.Path;
import org.jfree.data.category.DefaultCategoryDataset;
import toools.set.IntSet;

import java.util.stream.IntStream;

/**
 * Created by kourosh on 9/23/16.
 */
public class Algorithms {

    public static double getGlobalEfficiency2(Grph graph) {

        int[] vertices = graph.getVertices().toIntArray();
        int numOfVertices = vertices.length;
        double[] averagePaths = new double[numOfVertices];

        for (int i = 0; i < numOfVertices; i++) {
            int srcVertex = vertices[i];
            double[] pathLength = new double[numOfVertices - 1];
            int k = 0;
            for (int j = i + 1; j < numOfVertices; j++) {
                if (j == i) continue;
                int destVertex = vertices[j];
//                if (graph.containsAPath(srcVertex, destVertex)) {
//                    Path shortestPath = graph.getShortestPath(srcVertex, destVertex);
//                    int length = shortestPath.getLength();
//                    pathLength[k++] = length;
//                } else {
//                    pathLength[k++] = -1;
//                }
                try {
                    Path shortestPath = graph.getShortestPath(srcVertex, destVertex);
                    int length = shortestPath.getLength();
                    pathLength[k++] = length;
                } catch (Exception e) {
                    pathLength[k++] = -1;
                }
            }

            double s = 0;
            for (int j = 0; j < k/*pathLength.length*/; j++) {
                if (pathLength[j] != -1) {
                    s += 1 / pathLength[j];
                }
                // else s = s + 0
            }

            averagePaths[i] = s;
        }

        double sum = 0;
        for (int i = 0; i < averagePaths.length; i++) {
            sum += averagePaths[i];
        }

        return 2 * (sum / ((numOfVertices) * (numOfVertices - 1)));
    }


    public static double getGlobalEfficiency(Grph graph) {
        int graphSize = graph.getVertices().size();
        DistanceMatrix distanceMatrix = graph.getDistanceMatrix(graph.getEdgeWidthProperty());
        int[][] graphShortestDistanceArray = distanceMatrix.array;
        double sum = 0;
        for (int i = 0; i < graphShortestDistanceArray.length; i++) {
            double s = 0;
            for (int j = 0; j < graphShortestDistanceArray[i].length; j++) {
                int d = graphShortestDistanceArray[i][j];
                if (d > 0 /*&& d < Integer.MAX_VALUE*/) {
                    s += (1.0 / d);
                }
            }
            sum += s;
        }
        return sum / (graphSize * (graphSize - 1));
    }


    public static double getUniqueRobustnessMeasure(Grph graph, boolean showChart) {
        IntArrayList allInEdgeDegrees = graph.getAllInEdgeDegrees();
        int[] verticesDegrees = allInEdgeDegrees.toArray();
        int[] sortedIndices = IntStream.range(0, verticesDegrees.length)
                .boxed().sorted((i, j) -> verticesDegrees[j] - verticesDegrees[i])
                .mapToInt(ele -> ele).toArray();

        int graphSize = graph.getVertices().size();
        double[] Qs = new double[graphSize];

        double sum = 0;
        for (int i = 0; i < graphSize; i++) {
            System.out.println(i);
            Grph tempGraph = graph.clone();
            for (int j = 0; j <= i; j++) {
                tempGraph.removeVertex(sortedIndices[j]);
            }
            IntSet largestConnectedComponent = tempGraph.getLargestConnectedComponent();
            if (largestConnectedComponent != null) {
                int size = largestConnectedComponent.size();
                double qs = (double) size / graphSize;
                Qs[i] = qs;
                sum += qs;
            }
        }

        if (showChart) {
            DefaultCategoryDataset QsDataset = new DefaultCategoryDataset();
            for (int i = 0; i < graphSize; i++) {
                double size = Qs[i];
                QsDataset.addValue(size, "", String.valueOf(i));
            }
            Chart.displayChart(QsDataset);
        }

        return ((double) 1 / graphSize) * sum;
    }


}
