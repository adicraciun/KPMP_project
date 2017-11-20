import KPMP.utilities.*;

import java.util.ArrayList;

public class LocalSearch {
    private Solution startingSolution;
    private Neighborhood nStruct;
    private int maxNumberOfTrials;

    public LocalSearch(Solution solution, Neighborhood nStruct, int maxNumberOfTrials) {
        this.startingSolution = solution;
        this.nStruct = nStruct;
        this.maxNumberOfTrials = maxNumberOfTrials;
    }

    public Solution doLocalSearch() {
        int numberOfSteps = 0;

        Solution currentSolution = SolutionUtilities.cloneSolution(startingSolution);
        int curFitness = SolutionUtilities.getFitness(currentSolution);
        while (numberOfSteps < maxNumberOfTrials) {
            Solution nextSolution = nStruct.get_next();
            int nextFitness = SolutionUtilities.getFitness(nextSolution);

            if (curFitness > nextFitness) {
                currentSolution = nextSolution;
                curFitness = nextFitness;
            }
            numberOfSteps++;
        }

        return currentSolution;
    }
}
