class Solution {
    // TODO
    public int maximumDetonation(int[][] bombs) {
        int maxx = 0;
        int maxy = 0;
        for (int[] bomb : bombs) {
            maxx = Math.max(maxx, bomb[0]);
            maxy = Math.max(maxy, bomb[1]);
        } // for bomb

        int[][] field = new int[maxx][maxy];
        
        // add the bombs to the field and the pq
        for (int[] bomb : bombs) {
            field[bomb[0]][bomb[1]] = bomb[2];
        } // for bomb

        // explode starting with the biggest bomb and exploding its neighbors
    } // mD()

    private int explode(int[] bomb, int[][] field) {
        int explosionCount = 1;
        int x = bomb[0];
        int y = bomb[1];
        int r = bomb[2];
        field[x][y] = 0;

        // find the search space for the blast
        int x = Math.max(0, x - r);
        int xx = Math.min(field.length, x + r);

        int y = Math.max(0, y - r);
        int yy = Math.min(field[0].length, y + r);

    } // explode()
}
