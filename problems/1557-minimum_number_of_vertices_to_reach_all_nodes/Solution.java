class Solution {
    // union-find
    // 77/21 @ 11ms
    public List<Integer> findSmallestSetOfVertices(int n, List<List<Integer>> edges) {
        // find nodes with no incoming edges
        boolean[] hasIncoming = new boolean[n];
        for (List<Integer> edge : edges) {
            hasIncoming[edge.get(1)] = true;
        } // for edge

        List<Integer> noIncoming = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (hasIncoming[i] == false) {
                noIncoming.add(i);
            } // if
        } // for i
        return noIncoming;
    } // findSmallestSetOfVertices()
}
