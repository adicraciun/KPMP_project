import at.ac.tuwien.ac.heuoptws15.KPMPInstance;
import at.ac.tuwien.ac.heuoptws15.KPMPSolutionWriter;

import KPMP.utilities.Edge;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class GreedySolve {
    public static void main(String[] args) {
        KPMPInstance graph;

        try {
            graph = KPMPInstance.readInstance("/Users/adicraciun/Desktop/Algoritmica/instances/instance-11.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't read!");
            return;
        }

        GreedySolve solver = new GreedySolve();

        solver.solve(graph);
    }

    private List<List<Integer>> adjacencyList;
    // this matrix contains the edges that are used
    private boolean[][] alreadyUsed;

    private boolean isEdgeUsed(int vertexA, int vertexB) {
        return alreadyUsed[Math.min(vertexA, vertexB)][Math.max(vertexA, vertexB)];
    }

    private void changeStateEdge(int vertexA, int vertexB) {
        alreadyUsed[Math.min(vertexA, vertexB)][Math.max(vertexA, vertexB)] = !alreadyUsed[Math.min(vertexA, vertexB)][Math.max(vertexA, vertexB)];
    }

    private int nrEdgeIntersect(Edge edge, List<Edge> edges) {
        int nrIntersections = 0;
        for (Edge curEdge : edges) {
            if (curEdge.left < edge.left && curEdge.right < edge.right && curEdge.right > edge.left) nrIntersections += 1;
            if (curEdge.left > edge.left && curEdge.left < edge.right && curEdge.right > edge.right) nrIntersections += 1;
        }
        return nrIntersections;
    }


    public void solve(KPMPInstance graph) {
        KPMPSolutionWriter solution = new KPMPSolutionWriter(graph.getK());

        alreadyUsed = new boolean[graph.getNumVertices()][graph.getNumVertices()];

        adjacencyList = graph.getAdjacencyList();

        List<Edge> curPage = new ArrayList<Edge>();

        ArrayList<ArrayList<Edge>> result = new ArrayList<ArrayList<Edge>>(graph.getK());
        for (int i = 0; i < graph.getK(); ++i) {
            result.add(new ArrayList<Edge>());
        }

        int curEdges = 0;
        int totalCost = 0;
        while (curEdges < graph.getNumEdges()) {
            Edge bestEdge = null;
            int bestCost = 1 << 29;
            int bestPage = 0;
            int nrTestedEdges = 0;
            for (int vertex = 0; vertex < graph.getNumVertices() && nrTestedEdges < 100; ++vertex) {
                for (int nextVertex : adjacencyList.get(vertex)) {
                    if (vertex > nextVertex) continue;
                    if (isEdgeUsed(vertex, nextVertex)) continue;
                    ++nrTestedEdges;
                    changeStateEdge(vertex, nextVertex);
//                    System.out.println("another");
                    for (int pageNumber = 0; pageNumber < graph.getK(); ++pageNumber) {
                        ArrayList<ArrayList<Edge>> alternativeResult = new ArrayList<ArrayList<Edge>>();
                        for (int j = 0; j < graph.getK(); ++j) {
                            alternativeResult.add(new ArrayList<Edge>());
                            for (Edge edge : result.get(j)) {
                                alternativeResult.get(j).add(new Edge(edge));
                            }
                        }

                        alternativeResult.get(pageNumber).add(new Edge(vertex, nextVertex));
                        LookAhead cost_computer = new LookAhead(graph, alreadyUsed, curEdges + 1, alternativeResult);
                                                System.out.println("TRY");
                        int curCost = cost_computer.lookAhead();
                        curCost += nrEdgeIntersect(new Edge(vertex, nextVertex), result.get(pageNumber));
                        if (curCost < bestCost) {
                            bestCost = curCost;
                            bestPage = pageNumber;
                            bestEdge = new Edge(vertex, nextVertex);
                        }
                    }
                    changeStateEdge(vertex, nextVertex);
                    System.out.println("TRY");

                }
            }
            curEdges++;

           // if (result.get(pageNumber).size() > graph.getNumEdges() / graph.getK()) pageNumber++;
            totalCost += nrEdgeIntersect(bestEdge, result.get(bestPage));
            System.out.println("ANOTHER EDGE " + bestPage + " " + bestEdge.left + " " + bestEdge.right + " " + totalCost);
            result.get(bestPage).add(bestEdge);
            changeStateEdge(bestEdge.left, bestEdge.right);
        }

        for (int i = 0; i < graph.getK(); ++i)
            solution.addPage(result.get(i));
        List<Integer> spineOrder = new ArrayList<Integer>();

        for (int i = 0; i < graph.getNumVertices(); ++i)
            spineOrder.add(i);

        solution.setSpineOrder(spineOrder);

        try {
            solution.write("/Users/adicraciun/Desktop/Algoritmica/instances/instance-11-sol.txt");
        } catch (Exception e) {
            System.out.println("Couldn't write the file");
        }
    }
}
