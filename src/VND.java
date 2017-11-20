import KPMP.neighborhoods.BasicNeighborhood;
import KPMP.utilities.Neighborhood;
import KPMP.utilities.Solution;
import KPMP.utilities.SolutionUtilities;
import sun.jvm.hotspot.debugger.Page;

public class VND {
    private Solution startingSolution;
    private Neighborhood nStruct_1;
    private Neighborhood nStruct_2;
    private int maxNumberOfTrials;

    public VND(Solution solution, Neighborhood nStruct_1, Neighborhood nStruct_2) {
        this.startingSolution = solution;
        this.nStruct_1 = nStruct_1;
        this.nStruct_2 = nStruct_2;
    }

    public Solution doVND() {
        int numberOfSteps = 0;
        System.out.println("INcepem");
        Solution currentSolution = SolutionUtilities.cloneSolution(startingSolution);
        System.out.println(currentSolution.getPages());
        int curFitness = SolutionUtilities.getFitness(currentSolution);
        int curN = 1;
        while (true) {
            Solution nextSolution;
            if (curN == 1) {
                System.out.println("dasdas");
                nextSolution = nStruct_1.get_next();
            }
            else {
                System.out.println("adsdadsad");
                nextSolution = nStruct_2.get_next();
            }

            if (nextSolution == null)
                if (curN == 1) {
                    curN = 2;
                    continue;
                } else {
                    break;
                }

            System.out.println(nextSolution.getPages());
            int nextFitness = SolutionUtilities.getFitness(nextSolution);

            if (curFitness > nextFitness) {
                currentSolution = nextSolution;
                curFitness = nextFitness;
                if (curN == 1) {
                    nStruct_1 = new BasicNeighborhood(nextSolution);
                }
                if (curN == 2) {
                    nStruct_2 = new BasicNeighborhood(nextSolution);
                }
            }
            else if (curN == 2)
                break;
            else
                curN += 1;
            numberOfSteps++;
            System.out.println("AICI");
        }

        return currentSolution;
    }
}
