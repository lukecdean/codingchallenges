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
    // 9-30-2022
    public boolean equationsPossible0(String[] equations) {
        // unionfind approach
        Map<Character, Character> map = new HashMap<>();
        // begin by unioning all operands based on equalities
        for (String eqn : equations) {
            if (eqn.charAt(1) == '=') {
                char op1 = eqn.charAt(0);
                char op2 = eqn.charAt(3);
                boolean op1Related = map.containsKey(op1);
                boolean op2Related = map.containsKey(op2);
                if (!op1Related && !op2Related) {
                    // if neither are in a set, make op1 rep and point op2 to it
                    map.put(op1, op1);
                    map.put(op2, op1);
                } else if (op1Related && op2Related) { // if both are in a set, union if necessary
                    // get represntative of each
                    char op1rep = representative(op1, map);
                    char op2rep = representative(op2, map);
                    // if different representatvies, union
                    if (op1rep != op2rep) {
                        map.put(op2rep, op1);
                        map.put(op2, op1);
                    } // if
                    // else they are already unioned
                } else if (op1Related) { // if only op1 is in a set
                    map.put(op2, represntative(op1, map));
                } else if (op2Related) { // if only op2 is in a set
                    map.put(op1, represntative(op2, map));
                } // if
            } // if
        } // for eqn
        // have each char points directly to its representative for easy lookup
        collapseSets(map);
        // now search all inequalities and check for contradictions
        for (String eqn : equations) {
            if (eqn.charAt(1) == '!') {
                char op1 = eqn.charAt(0);
                char op2 = eqn.charAt(3);
                // if one of the operands is not in a union, there will be no contradition
                if (!map.containsKey(op1) || !map.containsKey(op2)) {
                    continue;
                } else { // both are in a union so check if they are in the same union
                    char op1rep = representative(op1, map);
                    char op2rep = representative(op2, map);
                    if (op1rep == op2rep) { // if they are, contradiction => unsatisifiable
                        return false;
                    } // if
                } // if
            } // if
        } // for eqn
        // if no contadictions were found, equations is satisfiable
        return true;
    } // equationsPossible()
    public boolean equationsPossible1(String[] equations) {
        // unionfind approach
        Map<Character, Character> map = new HashMap<>();
        // create relationships with all of the operands in the == statements
        for (String eqn : equations) {
            char op1 = eqn.charAt(0);
            char op2 = eqn.charAt(3);
            if (eqn.charAt(1) == '=') {
                // make op2's representative op1's representative
                // if an op has no rep, make it a rep of itself
                map.put(representative(op2, map), representative(op1, map));
            } else { // '!='
                // ensure all operands in eqations is in a family
                // if the operands don't have a parent, make them parents of themselves
                if (!map.containsKey(op1)) {
                    map.put(op1, op1);
                } // if
                if (!map.containsKey(op2)) {
                    map.put(op2, op2);
                } // if
                // if the two operands have the same parent, it is a contradiction
                if (map.get(op1) == map.get(op2)) {
                    return false;
                } // if
            } // if
        } // for eqn
        // have each char point directly to its representative for easy lookup
        collapseSets(map);
        // now search all inequalities and check for contradictions
        for (String eqn : equations) {
            if (eqn.charAt(1) == '!') {
                char op1 = eqn.charAt(0);
                char op1rep = representative(op1, map);
                char op2 = eqn.charAt(3);
                char op2rep = representative(op2, map);
                // if the operands have the same representative, it is a contradiction
                if (op1rep == op2rep) {
                    return false;
                } // if
            } // if
        } // for eqn
        // if no contadictions were found, equations is satisfiable
        return true;
    } // equationsPossible()
    // recursively find the representative of the character, and makes a char its own parent if
    // it lacks one
    private char representative(char c, Map<Character, Character> map) {
        // if the char has no rep, assign it as a rep of itself
        if (!map.containsKey(c)) {
            map.put(c, c);
            return c;
        } // if
        // recursively search for the representative until it's found
        char rep = map.get(c);
        if (rep == c) {
            return c;
        } else {
            return representative(rep, map);
        } // if
    } // representative() 
    // Make each member of the set point directly to its representative
    private void collapseSets(Map<Character, Character> map) {
        // for each char in the map, assign its representative as its direct parent
        for (Character c : map.keySet()) {
            char rep = representative(map.get(c), map);
            map.put(c, rep);
        } // for c
    } // collapseSets()

    // 10-05-2022
    // 36. Valid Sudoku
    public boolean isValidSudoku(char[][] board) {
        Set<Character> set = new HashSet<>();
        // check each row
        for (int r = 0; r < board.length; r++) {
            set.clear();
            for (int c = 0; c < board[0].length; c++) {
                if (Character.isDigit(board[r][c]) && set.add(board[r][c]) == false) {
                    return false;
                } // if
            } // for c
        } // for r
        // check each col
        for (int c = 0; c < board[0].length; c++) {
            set.clear();
            for (int r = 0; r < board.length; r++) {
                if (Character.isDigit(board[r][c]) && set.add(board[r][c]) == false) {
                    return false;
                } // if
            } // for c
        } // for r
        // check each 3x3
        for (int gridr = 0; gridr < 3; gridr++) {
            for (int gridc = 0; gridc < 3; gridc++) {
                set.clear();
                for (int r = gridr * 3; r < gridr * 3 + 3; r++) {
                    for (int c = gridc * 3; c < gridc * 3 + 3; c++) {
                        if (Character.isDigit(board[r][c]) && set.add(board[r][c]) == false) {
                            return false;
                        } // if
                    } // for c
                } // for r
            } // for gridc
        } // for gridr
        return true;
    } // isValidSudoku()

    // 10-12-2022
    // no meeting
    // 10-14-2022
    // no meeting

} // class

