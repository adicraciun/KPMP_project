package KPMP.utilities;

public class Edge {
    public int left, right;

    public Edge(int left, int right) {
        this.left = left;
        this.right = right;
    }

    public Edge(Edge edge) {
        this.left = edge.left;
        this.right = edge.right;
    }
}