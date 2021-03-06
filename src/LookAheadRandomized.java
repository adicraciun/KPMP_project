import KPMP.utilities.Edge;
import at.ac.tuwien.ac.heuoptws15.KPMPInstance;

import java.util.ArrayList;
import java.util.List;

public class LookAheadRandomized {

    private boolean[][] alreadyUsed;
    private int curEdges = 0;
    private List<List<Integer>> adjacencyList;
    private KPMPInstance graph;
    private ArrayList<ArrayList<Edge>> result;
    private int maxNumberOfTrials;

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

    private int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }

    private Edge getBestEdge(KPMPInstance graph, List<Edge> edges) {
        int best = (1 << 30) - 1;
        int numberOfTrials = 0;
        Edge bestEdge = null;

        while (numberOfTrials < maxNumberOfTrials || bestEdge == null) {
            Edge currentEdge = graph.getEdgeList().get(randomWithRange(0, graph.getEdgeList().size() - 1));
            if (isEdgeUsed(currentEdge.left, currentEdge.right)) continue;
            int nrIntersections = nrEdgeIntersect(currentEdge, edges);
            if (nrIntersections < best) {
                best = nrIntersections;
                bestEdge = currentEdge;
            }
            numberOfTrials++;
        }

        return bestEdge;
    }

    // result will be mutated so please be carefull about this
    LookAheadRandomized(KPMPInstance graph, ArrayList<ArrayList<Edge>> result, int maxNumberOfTrials) {
        this.alreadyUsed = new boolean[graph.getNumVertices()][graph.getNumVertices()];
        this.adjacencyList = graph.getAdjacencyList();
        this.result = result;
        this.graph = graph;
        this.maxNumberOfTrials = maxNumberOfTrials;
    }

    // result will be mutated so please be carefull about this
    LookAheadRandomized(KPMPInstance graph, boolean[][] alreadyUsed, int curEdges, ArrayList<ArrayList<Edge>> result, int maxNumberOfTrials ) {
        this.alreadyUsed = new boolean[graph.getNumVertices()][graph.getNumVertices()];
        for(int i = 0; i < alreadyUsed.length; i++)
            for(int j = 0; j < alreadyUsed[i].length; j++)
                this.alreadyUsed[i][j] = alreadyUsed[i][j];
        this.curEdges = curEdges;
        this.adjacencyList = graph.getAdjacencyList();
        this.result = result;
        this.graph = graph;
        this.maxNumberOfTrials = maxNumberOfTrials;
    }

    public int lookAhead() {
        int numberOfAllowedIntersections = 0;
        int curK = 0;
        int cost = 0;

        //System.out.println(curEdges + " " + graph.getNumEdges());

        while (curEdges < graph.getNumEdges() ) {
            Edge edge;

            ArrayList<Edge> curResult = result.get(curK);

            while (curEdges < graph.getNumEdges() && (curResult.size() < graph.getNumEdges() / graph.getK() ||
                    curK == graph.getK() - 1) ) {
                edge = getBestEdge(graph, curResult);
                if (edge == null)
                    break;
                curResult.add(edge);
                curEdges += 1;
                markUsedEdge(edge.left, edge.right);
                cost += nrEdgeIntersect(edge, curResult);
                //System.out.println("ANOTHER EDGE" + curEdges + " " + graph.getNumEdges() / graph.getK());
            }

            curK++;

            //System.out.println(curEdges + " " + graph.getNumEdges() + " " + graph.getK());
        }
        return cost;
    }
}
