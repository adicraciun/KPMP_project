package KPMP.utilities;

import sun.jvm.hotspot.debugger.Page;

import java.util.ArrayList;
import java.util.List;

public class SolutionUtilities {
    private static int nrEdgeIntersect(Solution sol, Edge edge, List<Edge> edges) {
        int nrIntersections = 0;
        for (Edge curEdge : edges) {
            if (sol.getOrder(curEdge.left) < sol.getOrder(edge.left) && sol.getOrder(curEdge.right) < sol.getOrder(edge.right) && sol.getOrder(curEdge.right) > sol.getOrder(edge.left)) nrIntersections += 1;
            if (sol.getOrder(curEdge.left) > sol.getOrder(edge.left) && sol.getOrder(curEdge.left) < sol.getOrder(edge.right) && sol.getOrder(curEdge.right) > sol.getOrder(edge.right)) nrIntersections += 1;
        }
        return nrIntersections;
    }
    public static int getFitness(Solution sol) {
        int cost = 0;

        for (List<Edge> curPage : sol.getPages()) {
            for (Edge edge : curPage) {
                cost += nrEdgeIntersect(sol, edge, curPage);
            }
        }
        return cost / 2;
    }

    public static Solution cloneSolution(Solution solution) {
        Solution newSolution = new Solution();
        for (int j = 0; j < solution.numberOfPages(); ++j) {
            newSolution.addPage(new ArrayList<Edge>());
            for (Edge edge : solution.getPage(j)) {
                newSolution.getPage(j).add(new Edge(edge));
            }
        }
        newSolution.setSpineOrder(solution.getSpineOrder());
        return newSolution;
    }
}
