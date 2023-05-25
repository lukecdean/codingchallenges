class Solution {
    public boolean isBipartite(int[][] graph) {
        // -1 or 1 or 0 for no group yet
        int[] group = new int[graph.length];
        int currGrouping = 1;

        // bfs and group each breadth in the same group
        Queue<Integer> breadth = new LinkedList<>();
        for (int i = 0; i < graph.length; i++) {
            if (group[i] != 0) {
                // already grouped
                continue;
            } // if

            breadth.offer(i);
            // add to group
            group[i] = currGrouping;

            while (!breadth.isEmpty()) {
                int breadthSize = breadth.size();
                currGrouping *= -1;
                while (breadthSize > 0) {
                    // current size of breadth
                    int currNode = breadth.poll();
                    if (group[currNode] == currGrouping) {
                        // grouping conflict
                        return false;
                    } else if (group[currNode] != 0) {
                        // already grouped
                        breadthSize--;
                        continue;
                    } // if

                    // add adjacent nodes to breadth
                    for (int adj = 0; adj < graph[currNode].length; adj++) {
                        if (group[graph[currNode][adj]] == 0) {
                            group[graph[currNode][adj]] = currGrouping;
                        } // if
                        breadth.offer(graph[currNode][adj]);
                    } // for adjacent

                    breadthSize--;
                } // while breadthSize
            } // while breadth is not empty
        } // for i

        // no conflicts found
        return true;
    } // isBipratite()

    public boolean isBipartite0(int[][] graph) {
        // -1 or 1 or 0 for no group yet
        int[] group = new int[graph.length];
        int currGrouping = 1;

        // bfs and group each breadth in the same group
        Queue<Integer> breadth = new LinkedList<>();
        for (int i = 0; i < graph.length; i++) {
            if (group[i] != 0) {
                // already grouped
                continue;
            } // if

            breadth.offer(i);

            while (!breadth.isEmpty()) {
                int breadthSize = breadth.size();
                while (breadthSize > 0) {
                    // current size of breadth
                    int currNode = breadth.poll();

                    // add to group
                    group[currNode] = currGrouping;

                    // add adjacent nodes to breadth
                    for (int adjacent : graph[currNode]) {
                        if (group[adjacent] == currGrouping) {
                            // grouping conflict
                            return false;
                        } else if (group[adjacent] == 0) {
                            // only offer nodes that have not been grouped
                            breadth.offer(adjacent);
                        } // if
                    } // for adjacent
                    breadthSize--;
                } // while breadthSize
                currGrouping *= -1;
            } // while breadth is not empty
        } // for i

        // no conflicts found
        return true;
    } // isBipratite()
}
