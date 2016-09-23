import grph.algo.AdjacencyMatrix;
import grph.algo.SpanningTreeAlgorithm;
import grph.algo.distance.DistanceMatrix;
import util.Algorithms;
import grph.Grph;
import grph.in_memory.InMemoryGrph;
import grph.path.Path;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by kourosh on 9/23/16.
 */
public class TestMain {

    public static void main(String[] args) throws IOException {


        Grph graph = new InMemoryGrph();

        graph.addUndirectedSimpleEdge(0, 1);
        graph.addUndirectedSimpleEdge(0, 2);
        graph.addUndirectedSimpleEdge(1, 3);
        graph.addUndirectedSimpleEdge(1, 4);
        graph.addUndirectedSimpleEdge(2, 3);
        graph.addUndirectedSimpleEdge(3, 4);
        graph.addVertex(5);

//        AdjacencyMatrix compute = graph.adjacencyMatrixAlgo.compute(graph);
//        DistanceMatrix distanceMatrix = graph.getDistanceMatrix(graph.getEdgeWidthProperty());
//        int[][] array = distanceMatrix.array;


//
//        graph.display();

        boolean b = graph.containsAPath(1, 4);
        Path shortestPath = graph.getShortestPath(1, 4);
        boolean b1 = graph.containsAPath(0, 5);
//        Path shortestPath1 = graph.getShortestPath(0, 5);

        double globalEfficiency1 = Algorithms.getGlobalEfficiency(graph);
        System.out.println("globalEfficiency1 = " + globalEfficiency1);


    }
}
