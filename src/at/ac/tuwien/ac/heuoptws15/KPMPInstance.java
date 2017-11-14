package at.ac.tuwien.ac.heuoptws15;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

import sun.security.provider.certpath.AdjacencyList;



public class KPMPInstance {
	private int K;
	private int numVertices;
	private int numEdges = 0;
	private List<List<Integer>> adjacencyList = new ArrayList<>();
	private boolean[][] adjacencyMatrix;
	
	private KPMPInstance() {
		
	}
	
	public static KPMPInstance readInstance(String path) throws FileNotFoundException {
		KPMPInstance inst = new KPMPInstance();
		Scanner s = new Scanner(new File(path));
		
		s.skip("(#.*[\r\n]+)*");
		inst.numVertices = s.nextInt();
		s.skip("(#.*[\r\n]+)*");
		inst.K = s.nextInt();
		
		
		for(int i=0; i<inst.numVertices; ++i) {
			s.skip("(#.*[\r\n]+)*");
			s.nextInt();
			inst.adjacencyList.add(new ArrayList<Integer>());
		}
		
		inst.adjacencyMatrix = new boolean[inst.numVertices][inst.numVertices];
		for(int i=0; i<inst.numVertices; ++i) {
			Arrays.fill(inst.adjacencyMatrix[i], false);
		}
		
		while(s.hasNext()) {
			s.skip("(#.*[\r\n]+)*");
			int a = s.nextInt();
			int b = s.nextInt();
			s.skip("\\s*\\[[0-9]+\\]");
			inst.adjacencyList.get(a).add(b);
			inst.adjacencyList.get(b).add(a);
			inst.adjacencyMatrix[a][b] = true;
			inst.adjacencyMatrix[b][a] = true;
		}
		
		s.close();
		
		for(int i=0; i<inst.numVertices; ++i) {
			List<Integer> al = inst.adjacencyList.get(i);
			inst.adjacencyList.set(i, al.stream().distinct().collect(Collectors.toList()));
		}

        for (int vertex = 0; vertex < inst.getNumVertices(); ++vertex) {
            for (int nextVertex : inst.adjacencyList.get(vertex)) {
                if (vertex <= nextVertex)
                 inst.numEdges += 1;
            }
        }

		return inst;
	}

	public int getK() { return K; }

    public int getNumEdges() { return numEdges; }

	public int getNumVertices() {
		return numVertices;
	}

	public List<List<Integer>> getAdjacencyList() {
		return adjacencyList;
	}

	public final boolean[][] getAdjacencyMatrix() {
		return adjacencyMatrix;
	}
}
