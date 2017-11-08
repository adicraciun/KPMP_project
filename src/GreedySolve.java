import at.ac.tuwien.ac.heuoptws15.KPMPInstance;
import at.ac.tuwien.ac.heuoptws15.KPMPSolutionWriter;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class GreedySolve {
    public static void main(String[] args) {
        KPMPInstance graph;

        try {
            graph = KPMPInstance.readInstance("/Users/adicraciun/Desktop/Algoritmica/instances/instance-02.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't read!");
            return;
        }

        GreedySolve solver = new GreedySolve();

        solver.solve(graph);
    }

    private List<List<Integer>> adjacencyList;
    private boolean[][] alreadyUsed;

    public List<Integer> buildSolution(int vertex) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        result.add(vertex);
        for (int nextVertex: adjacencyList.get(vertex)) {
            if (isEdgeUsed(vertex, nextVertex)) continue;
            if (nextVertex < vertex) continue;

            markUsedEdge(vertex, nextVertex);
            result.addAll(buildSolution(nextVertex));
            break;
        }
        return result;
    }

    private boolean isEdgeUsed(int vertexA, int vertexB) {
        return alreadyUsed[Math.min(vertexA, vertexB)][Math.max(vertexA, vertexB)];
    }

    private void markUsedEdge(int vertexA, int vertexB) {
        alreadyUsed[Math.min(vertexA, vertexB)][Math.max(vertexA, vertexB)] = true;
    }

    public void solve(KPMPInstance graph) {
        KPMPSolutionWriter solution = new KPMPSolutionWriter();

        alreadyUsed = new boolean[graph.getNumVertices()][graph.getNumVertices()];

        adjacencyList = graph.getAdjacencyList();

        for (int vertex = 0; vertex < graph.getNumVertices(); ++vertex) {
            for (int nextVertex : adjacencyList.get(vertex)) {
                if (isEdgeUsed(vertex, nextVertex)) continue;
                if (nextVertex < vertex) continue;

                markUsedEdge(vertex, nextVertex);
                List<Integer> newPage = new ArrayList<Integer>();
                newPage.add(vertex);
                newPage.addAll(buildSolution(nextVertex));

                System.out.println(newPage);

                solution.addPage(newPage);
            }
        }
        List<Integer> spineOrder = new ArrayList<Integer>();

        for (int i = 0; i < graph.getNumVertices(); ++i)
            spineOrder.add(i);

        solution.setSpineOrder(spineOrder);

        try {
            solution.write("/Users/adicraciun/Desktop/Algoritmica/instances/instance-02-sol.txt");
        } catch (Exception e) {
            System.out.println("Couldn't write the file");
        }
    }
}
