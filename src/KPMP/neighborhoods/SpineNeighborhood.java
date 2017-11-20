package KPMP.neighborhoods;

import KPMP.utilities.Edge;
import KPMP.utilities.Solution;
import KPMP.utilities.SolutionUtilities;

import java.util.ArrayList;

public class SpineNeighborhood implements KPMP.utilities.Neighborhood {
    Solution solution;

    private int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }

    public SpineNeighborhood(Solution solution) {
        this.solution = solution;
    }

    public Solution get_next_random() {
        Solution nextSolution = SolutionUtilities.cloneSolution(solution);
        int vertex1 = randomWithRange(0, nextSolution.spineSize() - 1);
        int vertex2 = randomWithRange(0, nextSolution.spineSize() - 1);

        nextSolution.swapOrder(vertex1, vertex2);

        return nextSolution;
    }

    public Solution get_next() {
        int bestSwap = (1 << 30) - 1;
        Solution bestSolution = null;

        for (int curSwap = 0; curSwap < solution.spineSize() - 1; ++curSwap) {

            Solution nextSolution = SolutionUtilities.cloneSolution(solution);
            int vertex1 = curSwap;
            int vertex2 = curSwap + 1;

            nextSolution.swapOrder(vertex1, vertex2);

            if (SolutionUtilities.getFitness(nextSolution) < bestSwap) {
                bestSwap = curSwap;
                bestSolution = SolutionUtilities.cloneSolution(nextSolution);

            }
        }
        if (bestSwap == (1 << 30) - 1)
            return null;

        return bestSolution;
    }
}
