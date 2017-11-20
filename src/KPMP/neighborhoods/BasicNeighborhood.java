package KPMP.neighborhoods;

import KPMP.utilities.Edge;
import KPMP.utilities.Solution;
import KPMP.utilities.SolutionUtilities;

import java.util.ArrayList;

public class BasicNeighborhood implements KPMP.utilities.Neighborhood{
    Solution solution;
    int curSwap = 0;

    private int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }

    public BasicNeighborhood(Solution solution) {
        this.solution = solution;
    }

    public Solution get_next_random() {
        Solution nextSolution = SolutionUtilities.cloneSolution(solution);
        ArrayList<Edge> page1 = nextSolution.getPage(randomWithRange(0, solution.numberOfPages() - 1));
        ArrayList<Edge> page2 = nextSolution.getPage(randomWithRange(0, solution.numberOfPages() - 1));

        Edge edge1 = page1.get(randomWithRange(0, page1.size() - 1));
        Edge edge2 = page2.get(randomWithRange(0, page2.size() - 1));

        page1.remove(edge1);
        page2.remove(edge2);

        page2.add(edge1);
        page1.add(edge2);

        return nextSolution;
    }

    public Solution get_next() {
        int nrSteps = 0;

        int bestSwap = (1 << 30) - 1;
        Solution bestSolution = null;

        while (nrSteps < 500) {
                Solution nextSolution = SolutionUtilities.cloneSolution(solution);
                ArrayList<Edge> page1 = nextSolution.getPage(curSwap);
                ArrayList<Edge> page2 = nextSolution.getPage(curSwap);

                Edge edge1 = page1.get(randomWithRange(0, page1.size() - 1));
                Edge edge2 = page2.get(randomWithRange(0, page2.size() - 1));

                page1.remove(edge1);
                page2.remove(edge2);

                page2.add(edge1);
                page1.add(edge2);

                if (SolutionUtilities.getFitness(nextSolution) < bestSwap) {
                    bestSwap = curSwap;
                    bestSolution = SolutionUtilities.cloneSolution(nextSolution);

                }

                ++nrSteps;
            }

            System.out.println()

        return bestSolution;
    }
}
