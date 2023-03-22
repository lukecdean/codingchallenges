class SolveUGA {
    // First meeting
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
    // 10-19-2022
    public boolean checkIfPangram(String sentence) {
        int letters = 0;
        for (int i = 0; i < sentence.length(); i++) {
            letters = letters | (1 << sentence.charAt(i) - 'a');
            if (letters == 67108863) {
                return true;
            } // if
        } // for i
        return false;
    } // checkIfPangram()
    public boolean checkIfPangram(String sentence) {
        int letters = 0;
        for (int i = 0; i < sentence.length(); i++) {
            letters = letters | (1 << sentence.charAt(i) - 'a');
        } // for i
        return letters == 67108863;
    } // checkIfPangram()
    // 1832. Check if the Sentence Is Pangram
    public boolean checkIfPangram(String sentence) {
        int letters = 0;
        // mark all found letters
        for (int i = 0; i < sentence.length(); i++) {
            int intVal = sentence.charAt(i) - 'a';
            letters = letters | (1 << intVal);
        } // for i
        for (int i = 0; i < 26; i++) {
            if (((letters >> i) & 1) != 1) {
                return false;
            } // if
        } // for i
        return true;
    } // checkIfPangram()

    // 692. Top K Frequent Words
    // 10-26-2022
    // 99/98 @ 18ms
    public boolean checkSubarraySum(int[] nums, int k) {
        // the trick: if a sum has remainder r another sum with remainder r can be subtracted
        // from it, the resulting sums remainder will be 0 i.e. a multiple
        Set<Integer> set = new HashSet<>();
        int prevRem = 0; // the remainder before last. start with 0
        int currRem;
        for (int i = 0; i < nums.length; i++) {
            // calculate currRem and check if it already exists
            currRem = (prevRem + nums[i]) % k;
            if (set.contains(currRem)) {
                return true;
            } // if
            // now the rem before last may be added to the set
            // this is bc the sub array must be at least two
            // could use a queue to generalize to at least n sized subarray
            set.add(prevRem);
            prevRem = currRem;
        } // for i
        return false; // if no matching remainders were found
    } // checkSubarraySum()

    public boolean checkSubarraySum(int[] nums, int k) {
        int[] prefixRems = new int[nums.length + 1];
        int sum = 0;
        prefixRems[0] = 0;
        for (int i = 1; i < prefixRems.length; i++) {
            sum += nums[i - 1];
            sum %= k;
            prefixRems[i] = sum;
        } // for i
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < prefixRems.length; i++) {
            if (!map.containsKey(prefixRems[i])) {
                map.put(prefixRems[i], i - 1);
            } else if (map.get(prefixRems[i]) < i - 1) {
            } // if
        } // for i

    } // checkSubarraySum()

    // 11-2-2022
    // 1706 Where will the ball fall
    public int[] findBall(int[][] grid) {
        int lyrs = grid.length;
        int cols = grid[0].length;
        int[] res = new int[cols];
        for (int i = 0; i < cols; i++) {
            int lyr = 0; // layer the ball is on
            int col = i; // column the ball is in
            //System.out.printf("lyr: %d, col: %d\n", lyr, col);
            while (lyr < lyrs) {
                if (grid[lyr][col] == 1) {
                    col += 1;
                    if (col >= cols || grid[lyr][col] == -1) {
                        res[i] = -1;
                        break;
                    } else {
                        lyr++;
                    } // if
                } else { // == -1
                    col -= 1;
                    if (col < 0 || grid[lyr][col] == 1) {
                        res[i] = -1;
                        break;
                    } else {
                        lyr++;
                    } // if
                } // if
            } // while lyr
            if (lyr == lyrs) {
                res[i] = col;
            } // if
        } // for i
        return res;
    } // findBall()
    // part II
    // 74/ @ 2ms
    public int[] findBall(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int[] res = new int[cols];
        for (int i = 0; i < cols; i++) {
            int row = 0; // row the ball is on
            int col = i; // col the ball is in
            //System.out.printf("lyr: %d, col: %d\n", lyr, col);
            while (row < rows) {
                int val = grid[row][col];
                col += val;
                if (col < 0 || col >= cols || grid[row][col] != val) {
                    res[i] = -1;
                    break;
                } else {
                    row++;
                } // if
            } // while row
            if (row == rows) {
                res[i] = col;
            } // if
        } // while row
        return res;
    } // findBall()

    // 11-4-2022
    // 2131. Longest Palindrome by Concatenating Two Letter Words
    // 86/70 @ 23ms
    public int longestPalindrome(String[] words) {
        int res = 0;
        int[] doubleLetters = new int[26];
        int[][] wordsMap = new int[26][26];
        for (String word : words) {
            if (word.charAt(0) == word.charAt(1)) {
                doubleLetters[word.charAt(0) - 'a']++;
            } else {
                // if its reverse has been found before and is unmatched
                if (wordsMap[word.charAt(1) - 'a'][word.charAt(0) - 'a'] > 0) {
                    res += 4;
                    wordsMap[word.charAt(1) - 'a'][word.charAt(0) - 'a']--;
                } else {
                    wordsMap[word.charAt(0) - 'a'][word.charAt(1) - 'a']++;
                } // if
            } // if
        } // for word
        boolean oddDoubleLetter = false;
        for (int count : doubleLetters) {
            if ((count & 1) == 1) { // odd number of doubleLetter words
                if (!oddDoubleLetter) {
                    res += 2;
                    oddDoubleLetter = true;
                } // if
                count--;
            } // if
            res += 2 * count;
        } // for count
        return res;
    } // longestPalindrome()

    // 3-20-23
    // 1206. Design Skiplist
class Skiplist {
    SLNode head;
    int size;
    int layers;
    double coinFlipOdds = .5;

    public Skiplist() {
        SLNode head;
        size = 0;
        layers = 0;
    }
    
    public boolean search(int target) {
        if (head == null) {
            return false;
        } // if
        SLNode curr = head;
        if (target < curr.val) {
            // if the head is greater than target
            return false;
        } // if
        while (curr != null) {
            if (curr.next == null || target < curr.next.val) {
                curr = curr.down;
            } else if (target > curr.next.val) {
                curr = curr.next;
            } else { // target == curr.next.val
                return true;
            } // if
        } // while
        return false;
    }
    
    public void add(int num) {
        size++;
        if (size == 1)  {
            head = new SLNode(num, null, null);
            layers = 1;
            return;
        } // if
        if (needToAddLayer()) {
            addLayer();
        } // if
        if (num < head.val) { // need to make new head
            addNewHead(num, head);
            return;
        } // if
        // now recursively find the place where the num is to be placed
        addHelper(num, head);
    } // add()
    
    private boolean needToAddLayer() {
        return (layers < (Math.log(size) / Math.log(1.0 / coinFlipOdds)));
    } // needToAddLayer()

    private void addLayer() {
        SLNode newHead = new SLNode(head.val, null, head);
        this.head = newHead;
    } // addLayer()

    // recursively finds the bottom of the list then adds a new head
    // coin flips to determine where to prune old head
    private SLNode addNewHead(int num, SLNode curr) {
        if (curr.down != null) {
            SLNode below = addNewHead(num, curr.down);
            SLNode newHead = new SLNode(num, curr, below);
            if (below.next != null) { // if the old head hasn't been pruned yet
                boolean pruneOldHead = Math.random() > coinFlipOdds;
                // if true, start removing old heads
                if (pruneOldHead) {
                    newHead.next = curr.next; // 'cuts' out old head
                } // if
            } else { // old heads have started to be pruned already so cont
                newHead.next = curr.next; // 'cuts' out old head
            } // if

            return newHead;
        } else { // found bottom
            SLNode newHead = new SLNode(num, curr, null);
            return newHead;
        } // if
    } // addNewHead()

    // returns the node below if a coin flip is to be made else null
    private SLNode addHelper(int num, SLNode curr) {
        // go as far next in the list as the num should go
        while (curr.next != null && curr.next.val < num) {
            curr = curr.next;
        } // while
        if (curr.down == null) { // base case, now place num nodes
            SLNode numNode = new SLNode(num, curr.next, null);
            curr.next = numNode;
            return numNode;
        } else { // may traverse down a layer
            SLNode belowNode = addHelper(num, curr.down);
            // on return, see if a node is to be added
            if (belowNode == null) { // do not coin flip
                return null;
            } else { // do coin flip
                if (Math.random() <= coinFlipOdds) {
                    SLNode numNode = new SLNode(num, curr.next, belowNode);
                    curr.next = numNode;
                    return numNode;
                } else {
                    return null;
                } // if
            } // if
        } // if
    } // addHelper()
    
    public boolean erase(int num) {
        if (size == 0) {
            return false;
        } // if

        if (head.val == num) {
            eraseHead();
            size--;
        } else if (eraseHelper(num, head)) {
            size--;
        } // if

        return true;
    } // erase()

    private void eraseHead() {
        if (size == 1) {
            head = null;
            return;
        } // if
        // else remove head and make next val the new head
        eraseHeadHelper(head);
        head = head.next;
    } // eraseHead()

    // builds up the next element to the top layer
    // does not delete the current head
    private SLNode eraseHeadHelper(SLNode curr) {
        if (curr.down != null) { // not at the bottom
            SLNode downNext = eraseHeadHelper(curr.down);
            if (downNext != null) {
                // check if need to insert next head to build it up to the top
                if (curr.next == null || curr.next.val != downNext.val) {
                    SLNode newHeadInLayer = 
                        new SLNode(downNext.val, curr.next, downNext);
                    return newHeadInLayer;
                } else {
                    return curr.next;
                } // if
            } // if
            return null;
        } else { // at the bottom
            return curr.next;
        } // if
    } // eHH()

    //
    private boolean eraseHelper(int num, SLNode curr) {
        if (curr == null) { // did not find the target num
            return false;
        } // if
        while (curr.next != null && (num < curr.next.val)) {
            curr = curr.next;
        } // while
        if (curr.next == null || curr.next.val != num) { 
            // reached end of this list or place where num should be so go down
            return eraseHelper(num, curr.down);
        } else (curr.next.val == num) { // found num to remove
            curr.next = curr.next.next; // 'cut' out the num
            return true;
        } // if
    } // eraseHelper()
} // class Skiplist

class SLNode {
    int val;
    SLNode next;
    SLNode down;

    public SLNode(int val, SLNode next, SLNode down) {
        this.val = val;
    } // SLNode()
} // SLNode


} // class
