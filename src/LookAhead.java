import KPMP.utilities.Edge;
import at.ac.tuwien.ac.heuoptws15.KPMPInstance;

import java.util.ArrayList;
import java.util.List;

public class LookAhead {

    private boolean[][] alreadyUsed;
    private int curEdges = 0;
    private List<List<Integer>> adjacencyList;
    private KPMPInstance graph;
    private ArrayList<ArrayList<Edge>> result;

    private int nrEdgeIntersect(Edge edge, List<Edge> edges) {
        int nrIntersections = 0;
        for (Edge curEdge : edges) {
            if (curEdge.left < edge.left && curEdge.right < edge.right && curEdge.right > edge.left) nrIntersections += 1;
            if (curEdge.left > edge.left && curEdge.left < edge.right && curEdge.right > edge.right) nrIntersections += 1;
        }
        return nrIntersections;
    }

    private boolean isEdgeUsed(int vertexA, int vertexB) {
        return alreadyUsed[Math.min(vertexA, vertexB)][Math.max(vertexA, vertexB)];
    }

    private void markUsedEdge(int vertexA, int vertexB) {
        alreadyUsed[Math.min(vertexA, vertexB)][Math.max(vertexA, vertexB)] = true;
    }

    private Edge getBestEdge(KPMPInstance graph, List<Edge> edges, int numberOfAllowedIntersections) {
        for (int vertex = 0; vertex < graph.getNumVertices(); ++vertex) {
            for (int nextVertex : adjacencyList.get(vertex)) {
                if (isEdgeUsed(vertex, nextVertex)) continue;
                if (nextVertex < vertex) continue;

                if (nrEdgeIntersect(new Edge(vertex, nextVertex), edges) <= numberOfAllowedIntersections)
                    return new Edge(vertex, nextVertex);
            }
        }
        return null;
    }

    LookAhead(KPMPInstance graph, boolean[][] alreadyUsed, int curEdges, ArrayList<ArrayList<Edge>> result) {
        this.alreadyUsed = new boolean[graph.getNumVertices()][graph.getNumVertices()];
        for(int i = 0; i < alreadyUsed.length; i++)
          for(int j = 0; j < alreadyUsed[i].length; j++)
              this.alreadyUsed[i][j] = alreadyUsed[i][j];
        this.curEdges = curEdges;
        this.adjacencyList = graph.getAdjacencyList();
        this.result = result;
        this.graph = graph;
    }

    public int lookAhead() {
        int numberOfAllowedIntersections = 0;
        int curK = 0;
        int cost = 0;

        System.out.println(curEdges + " " + graph.getNumEdges());

        while (curEdges < graph.getNumEdges() ) {
            Edge edge;

            ArrayList<Edge> curResult = result.get(curK);

            while (true) {
                edge = getBestEdge(graph, curResult, numberOfAllowedIntersections);
                if (edge == null)
                    break;
                curResult.add(edge);
                curEdges += 1;
                markUsedEdge(edge.left, edge.right);
                cost += numberOfAllowedIntersections;
            }

            System.out.println(curEdges + " " + graph.getNumEdges() + " " + numberOfAllowedIntersections);




            ++curK;
            if (curK == graph.getK()) {
                numberOfAllowedIntersections += 1;
                curK = 0;
            }
        }
        return cost;
    }
}
