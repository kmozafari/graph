import com.opencsv.CSVReader;
import grph.Grph;
import grph.in_memory.InMemoryGrph;
import org.miv.graphstream.algorithm.Algorithm;
import util.Algorithms;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by kourosh on 9/25/16.
 */
public class RMain {

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

        double uniqueRobustnessMeasure = Algorithms.getUniqueRobustnessMeasure(graph, true);
        System.out.println("uniqueRobustnessMeasure = " + uniqueRobustnessMeasure);

    }
}
