import com.carrotsearch.hppc.IntArrayList;
import com.opencsv.CSVReader;
import grph.Grph;
import grph.in_memory.InMemoryGrph;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import toools.set.IntSet;
import util.Algorithms;
import util.Chart;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Created by kourosh on 9/22/16.
 */
public class Main {

    public static void main(String[] args) throws IOException {

        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);
        CSVReader reader = new CSVReader(new FileReader("/home/kourosh/data.txt"), ',');
        List myEntries = reader.readAll();
        int numberOfNodes = myEntries.size();

        Grph graph = new InMemoryGrph();

        for (int row = 0; row < numberOfNodes; row++) {
            String[] columns = (String[]) myEntries.get(row);
            for (int col = 0; col < columns.length; col++) {
                int connect = Integer.valueOf(columns[col]);
                if (connect == 1) {
                    graph.addUndirectedSimpleEdge(row, col);
                }
            }
        }

        Grph randomGraph = graph.clone();

        IntArrayList allInEdgeDegrees = graph.getAllInEdgeDegrees();
        int[] verticesDegrees = allInEdgeDegrees.toArray();
        int[] sortedIndices = IntStream.range(0, verticesDegrees.length)
                .boxed().sorted((i, j) -> verticesDegrees[i] - verticesDegrees[j])
                .mapToInt(ele -> ele).toArray();

        int largestConnectedComponentSizes[] = new int[numberOfNodes];
        double globalEfficiencies[] = new double[numberOfNodes];
        double randomGlobalEfficiencies[] = new double[numberOfNodes];
/*
        for (int i = numberOfNodes - 1; i >= 0; i--) {
            System.out.print(i + "\t");
            int indice = sortedIndices[i];
            graph.removeVertex(indice);
            int edgeSize = graph.getEdges().size();
            System.out.println(indice + "  " + edgeSize);
            if (edgeSize > 0) {
                IntSet largestConnectedComponent = graph.getLargestConnectedComponent();
                int size = largestConnectedComponent.size();
                largestConnectedComponentSizes[numberOfNodes - i - 1] = size;
                globalEfficiencies[numberOfNodes - i - 1] = Algorithms.getGlobalEfficiency(graph);
            } else {
                largestConnectedComponentSizes[numberOfNodes - i - 1] = 0;
                globalEfficiencies[numberOfNodes - i - 1] = 0;
            }
        }

*/
        List<Integer> indecies = new ArrayList<>();
        for (int i = 0; i < numberOfNodes; i++) {
            indecies.add(i);
        }

        Random random = new Random();

        for (int i = 0; i < numberOfNodes; i++) {
            int randIndex = random.nextInt(numberOfNodes - i);
            Integer selectedIndex = indecies.remove(randIndex);
            System.out.println(i + " : " + selectedIndex);
            randomGraph.removeVertex(selectedIndex);
            int edgeSize = randomGraph.getEdges().size();
            if (edgeSize > 0) {
                try {
                    randomGlobalEfficiencies[i] = Algorithms.getGlobalEfficiency(randomGraph);
                } catch (Exception e) {
                    randomGlobalEfficiencies[i] = 0;
                }
            } else {
                randomGlobalEfficiencies[i] = 0;
            }
        }

/*
        DefaultCategoryDataset largestConnectedComponentSizesDataset = new DefaultCategoryDataset();
        for (int i = 0; i < numberOfNodes; i++) {
            int size = largestConnectedComponentSizes[i];
            largestConnectedComponentSizesDataset.addValue(size, "", String.valueOf(i));
        }

        DefaultCategoryDataset globalEfficienciesDataset = new DefaultCategoryDataset();
        for (int i = 0; i < numberOfNodes; i++) {
            double size = globalEfficiencies[i];
            globalEfficienciesDataset.addValue(size, "", String.valueOf(i));
        }
*/
        DefaultCategoryDataset randomGlobalEfficienciesDataset = new DefaultCategoryDataset();
        for (int i = 0; i < numberOfNodes; i++) {
            double size = randomGlobalEfficiencies[i];
            randomGlobalEfficienciesDataset.addValue(size, "", String.valueOf(i));
        }

//        Chart.displayChart(largestConnectedComponentSizesDataset);
//        Chart.displayChart(globalEfficienciesDataset);
        Chart.displayChart(randomGlobalEfficienciesDataset);

//        grph.algo.clustering.


        System.out.println("heree");
    }
}
