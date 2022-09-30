class SolveUGA {
    public boolean validMountainArray(int[] arr) {
        int i = 1;
        while (i < arr.length) {
            if (arr[i] < arr[i - 1])
                break;
            i++;
        } // while
        if (arr[i] > arr[i - 1])
            return false;
        while (i < arr.length) {
            if (arr[i] > arr[i -1])
                return false;
            i++;
        } // while
        return true;
    } // vMA()
    public boolean validMountainArray(int[] arr) {
	int  hasPeaked = 0;
	int i  =  0;
	while (i < arr.length) {
		if (arr[i] > arr[i-1]) {
			hasPeaked++;
		}
		else if (arr[i] == arr[i-1]) {
			return false;
		}
		else if (arr[i] < arr[i-1]) {
			break;
		}
		i++;
	}
	if (hasPeaked < 1) {
		return false;
	}
	while  (i < arr.length) {
		if (arr[i] > arr[i-1]) {
			return false;
		}
	}
	return true;
    } // validMountainArray()
    // 9-23-22
    // Two Knights
    public int twoKnights(int k) {
        int res = 0;
        int sqr = 1;
        for (int r = 0; r < k; r++) {
            for (int c = 0; c < k; c++) {
                // add to res: the total number of squares on the board minus
                // the spaces where a knight is attacking minus
                // the spaces already checked
                int openSquares = (k * k) - possibleJumps(r, c, k) - sqr;
                res += openSquares;
                sqr++;
            } // for c
        } // for r
        return res;
    } // twoKnights()
    private int possibleJumps(int r, int c, int k) {
        int ct = 0;

        if (((k - 1) - c) >= 1) {
            if (r >= 2) ct++;
            if (((k - 1) - r) >= 2) ct++;

            if (((k - 1) - c) >= 2) {
                if (r >= 1) ct++;
                if (((k - 1) - r) >= 1) ct++;
            } // if
        } // if

        return ct;
    } // possibleJumps()
    // 9-28-2022
    // 77/51 @ 10ms
    public int islandPerimeter(int[][] grid) {
        int bds = 0;
        for (int r = 0; r < grid.length; r++) {
            int geo = 0;
            for (int c = 0; c < grid[0].length; c++) {
                if (geo != grid[r][c]) {
                    bds++;
                    geo == 0 ? 1 : 0;
                } // if
            } // for c
            if (geo == 1) bds++;
        } // for r
        for (int c = 0; c < grid[0].length; c++) {
            int geo = 0;
            for (int r = 0; r < grid.length; r++) {
                if (geo != grid[r][c]) {
                    bds++;
                    geo == 0 ? 1 : 0;
                } // if
            } // for c
            if (geo == 1) bds++;
        } // for r
        return bds;
    } // islandPerimeter()
    public int islandPerimeter(int[][] grid) {
        int bds = 0;
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] == 1) bds += neighbors(grid, r, c);
            } // for c
        } // for r
    } // islandPerimeter()
    private int neighbors(int[][] grid, int r, int c) {
        int nbs = 0;
        if (0 <= r - 1 && grid[r - 1][c] == 1) nbs++;
        if (r + 1 < grid.length && grid[r + 1][c]) nbs++;
        if (0 <= c - 1
    } // neighbors()

    // BFS
    public int maxAreaOfIsland(int[][] grid) {
        int max = 0;
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] == 1) max = Math.max(max, bfs(grid, r, c));
            } // for c
        } // for r
        return max;
    } // maxAreaOfIsland()
    private int bfs(int[][] grid, int r, int c) {
        int[] drs = new int[]{0, 1, 0, -1, 0};
        int ct = 0;
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{r, c});
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            ct++;
            for (int dr = 1; dr < drs.length; dr++) {
                if (    0 <= cur[0] + drs[dr] && 
                        cur[0] + drs[dr] < grid.length &&
                        0 <= cur[1] + drs[dr - 1] &&
                        cur[1] + drs[dr - 1] < grid[0].length && 
                        grid[cur[0] + drs[dr]][cur[1] + drs[dr - 1]] == 1) {
                    q.offer(new int[]{cur[0] + drs[dr], cur[1] + drs[dr - 1]}); 
                    grid[cur[0] + drs[dr]][cur[1] + drs[dr - 1]] = 0; 
                        } // if
            } // for dr
        } // while
        return ct;
    } // bfs()
} // class

