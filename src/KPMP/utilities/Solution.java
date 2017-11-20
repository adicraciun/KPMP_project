package KPMP.utilities;

import java.util.ArrayList;
import java.util.*;

public class Solution {
    private ArrayList<ArrayList<Edge>> pages;
    private Map<Integer, Integer> spineMap = new HashMap<Integer, Integer>();

    public Solution(ArrayList<ArrayList<Edge>> pages, List<Integer> spineOrder) {
        this.pages = pages;

        for (int i = 0; i < spineOrder.size(); ++i) {
            spineMap.put(spineOrder.get(i), i);
        }
    }

    public void setSpineOrder(List<Integer> spineOrder) {
        spineMap = new HashMap<Integer, Integer>();
        for (int i = 0; i < spineOrder.size(); ++i) {
            spineMap.put(spineOrder.get(i), i);
        }
    }

    public int spineSize() {
        return spineMap.size();
    }


    public int numberOfPages() {
        return pages.size();
    }

    public void addPage(ArrayList<Edge> page) {
        pages.add(page);
    }

    public ArrayList<Edge> getPage(int index) {
        return pages.get(index);
    }

    public ArrayList<ArrayList<Edge>> getPages() {
        return pages;
    }

    public Solution() {
        this.pages = new ArrayList<ArrayList<Edge>>();
    }

    public ArrayList<Integer> getSpineOrder() {
        ArrayList<Integer> spineOrder = new ArrayList<Integer>();

        for (int i = 0; i < spineMap.size(); ++i)
            spineOrder.add(0);

        for (Map.Entry<Integer, Integer> entry : spineMap.entrySet()) {
            spineOrder.set(entry.getValue(), entry.getKey());
        }

        return spineOrder;
    }

    public int getOrder(int index) {
        return spineMap.get(index);
    }

    public void swapOrder(int vertex1, int vertex2) {
        int pos1 = spineMap.get(vertex1);
        int pos2 = spineMap.get(vertex2);

        spineMap.replace(vertex1, pos2);
        spineMap.replace(vertex2, pos1);
    }

};