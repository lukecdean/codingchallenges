class Problems {

    // 11. Container With Most Water
    public int maxArea(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int max = 0;

        while (left < right) {
            newTank = Math.min(height[left], height[right]) * (right - left);
            max = Math.max(max, newTank);
            // check for the next wall for whicheve side is shorter
            if (height[left] < height[right]) {
                //nextWall = nextWall(height, left, true);
                nextWall = left + 1;
                left = nextWall;
            } else {
                //nextWall = nextWall(height, right, false);
                nextWall = right - 1;
                right = nextWall;
            } // else
        } // while
        return max;
    } // maxArea()

    public int maxArea0(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int max = 0;
        int newTank;
        int nextWall;

        while (left < right) {
            newTank = Math.min(height[left], height[right]) * (right - left);
            max = Math.max(max, newTank);
            // check for the next wall for whicheve side is shorter
            if (height[left] < height[right]) {
                nextWall = nextWall(height, left, true);
                // if the neither left nor right find a next wall, the search is over
                if (nextWall == left) {
                    nextWall = nextWall(height, right, false);
                    if (nextWall == right) return max;
                } // if
                left = nextWall;
            } else {
                nextWall = nextWall(height, right, false);
                // if the neither left nor right find a next wall, the search is over
                if (nextWall == right) {
                    nextWall = nextWall(height, left, true);
                    if (nextWall == left) return max;
                } // if
                right = nextWall;
            } // else
        } // while
        return max;
    } // maxArea()

    int nextWall(int[] height, int current, boolean left) {
        // if left, move positive steps, else move negative steps
        //int direction = left ? 1 : -1;
        int steps = 1;
        // looking for a wall that is on or above the line starting at the top of current
        // and and moving with a slope of 1 in the appropriate direction
        if (left) {
            while (current + steps < height.length) {
                if (height[current + steps] >= height[current] + steps) {
                    return current + steps;
                } // if
                steps++;
            } // while
        } else { // right
            while (current - steps >= 0) {
                if (height[current - steps] >= height[current] + steps) {
                    return current - steps;
                } // if
                steps++;
            } // while
        } // if
        // if it didn't find a high enough wall, don't change the wall
        return current;
    } // nextWall()

    // 20. Valid Parentheses
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else if (c == ')' || c == '}' || c == ']') {
                switch (c) {
                    case ')': c = '(';
                              break;
                    case '}': c = '{';
                              break;
                    case ']': c = '[';
                              break;
                } // switch
                if (stack.size() == 0 || stack.pop() != c) {
                    return false;
                } // fi
            } // if
        } // for
        return stack.size() == 0;
    } // isValid
    // 53. Maximum Subarray
    //
    public int maxSubArray(int[] nums) {
        if (nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];
        int s = 0;
        int e = 0;
        int sum = nums[s];
        int max = sum;
        // use a sliding window
        while (s < nums.length) {
            if (e == nums.length - 1) {
                if (e - s == 1) {
                    sum -= nums[s];
                    s = nums.length; // to break the loop
                } else {
                    sum -= nums[s];
                    s++;
                } // if
            } else if (s == e) {
                e++;
                sum += nums[e];
            } else if (-1 * nums[s] > nums[e + 1]) {
                sum -= nums[s];
                s++;
            } else {
                e++;
                sum += nums[e];
            } // if
            System.out.printf("(%d,%d)",s, e);
            max = Math.max(max, sum);
        } // while
        return max;
    } // maxSubArray()
    // 61. Rotate List
    // 80/60
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null || k == 0) {
            return head;
        } // if
        ListNode oghead = head;
        // will make a window with two pointers
        // head is the head of the window and will be the 'new tail' 
        // tail is the tail of the window and will be the 'new head'
        ListNode tail = head;
        int i = 0; // size if the window
        // expand and shift the window until it hits the end
        while (tail.next != null) {
            tail = tail.next; // move tail
            if (i < k) { // expand window until it is size k
                i++;
            } else { // when window size is size k start shifting head
                head = head.next;
            } // else
        } // while
        // if the window size never reaches k, find the right 'new tail'
        // i is window size - 1 so increment it
        i++;
        if (i <= k) { // if this is true, head will not have moved at all
            k = k % i; // if k is large, mod it by size so we don't over rotate
            if (k == 0) { // no rotation so just return
                return head;
            } else { // else move the head forward to the 'new tail'
                for (int j = 0; j < i - k - 1; j++) {
                    head = head.next;
                } // for
            } // if
        } // if
        tail.next = oghead; // link the end of the list to the oghead
        ListNode newHead = head.next; // this will be the return head
        head.next = null; // head is the 'new tail' so cut it
        return newHead;
    } // rotateRight
    // 5/80
    public ListNode rotateRight0(ListNode head, int k) {
        if (head == null || head.next == null) {
            return head;
        } // if
        int len = 0;
        ListNode n = head;
        len++;
        do {
            len++;
            n = n.next;
        } while (n.next != null); // while
        // n will be the end node
        k = k % len; // to account for overrotation
        n.next = head; // link the end of the list
        // go to the end of the rotated list
        for (int i = 0; i < len - k - 1; i++) {
            head = head.next;
        } // for
        n = head.next; // save the head of the rotated list
        head.next = null; // cut the end of the rotated list
        return n;
    } // rotateList
    // 62. Unique Paths
    // 100/60
    public int uniquePaths(int m, int n) {
        if (m == 1 || n == 1) return 1; // edge

        // make sure m is not the longest dimension
        if (m > n) {
            int temp = m;
            m = n;
            n = temp;
        } // if

        // we only need the previous row to find the next one
        int[] lastRow = new int[m - 1];

        // bottom row is always 1
        for (int i = 0; i < m - 1; i++) lastRow[i] = 1;

        for (int y = n - 2; y >= 0; y--) {
            for (int x = m - 2; x >= 0; x--) {
                // right most col is always 1
                if (x == m - 2) lastRow[x] = lastRow[x] + 1;
                // else a spot is the sum of the one below and the one to the right
                // the one below is stored at the same index
                else lastRow[x] = lastRow[x] + lastRow[x + 1];
            } // for x
        } // for y

        return lastRow[0];
    } // uniquePaths()
    // 100 - 53/75 - 25
    public int uniquePaths0(int m, int n) {
        if (m == 1 && n == 1) return 1; // edge
        int[][] paths = new int[m][n]; // 2D arr for memo
        for (int x = 0; x < m; x++) // initialize arr
            for (int y = 0; y < n; y++)
                paths[x][y] = -1;
        return up(m - 1, n - 1, paths);
    } // uniquePaths()
    int up0(int m, int n, int[][] paths) {
        if (m < 0 || n < 0) return 0; // bounds check
        if (m + n == 1) return 1; // if next to the goal
        if (paths[m][n] == -1) { // if no val, calc it
            // num of paths is equal to the sum of the next two
            paths[m][n] = 
                up(m - 1, n, paths) +
                up(m, n - 1, paths);
        } // if
        return paths[m][n];
    } // up()
    // 70. Climbing Stairs
    // 11/41
    HashMap<Integer, Integer> map = new HashMap<>();
    public int climbStairs(int n) {
        if (n == 1 || n == 0) {
            return 1;
        } // if

        if (!map.containsKey(n)) {
            map.put(n, climbStairs(n - 1) + climbStairs(n - 2));
        } // if
        
        return map.get(n);
    } // climbStairs()
    // 79. Word Search
    //
    public boolean exist(char[][] board, String word) {
    } // exist()
    // 99. Recover Binary Search Tree
    //
    public void recoverTree(TreeNode root) {
        findBadNode(root, Integer.MAX_VALUE, Integer.MIN_VALUE);
    } // recoverTree()
    private TreeNode findBadNode(TreeNode root, int max, int min) {
        if (root == null) {
            return null;
        } // if
        if (root.val > max || root.val < min) {
            return root;
        } // if
        TreeNode res = findBadNode(root.left, root.val, min);
        if (res == null) {
            res = findBadNode(root.right, max, root.val);
        } // if
        return res;
    } // findBadNode

    // 100. Same Tree
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null || q == null) {
            if (p == q) {
                return true;
            } // if
            return false;
        } // if

        if (p.val == q.val) {
            return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
        } else {
            return false;
        } // if
    } // isSameTree
    // 167. Two Sum II
    //
    public int[] twoSum(int[] numbers, int target) {
        int b = 0;
        int e = numbers.length - 1;
        int sum;
        int[] res = new int[]{-1, -1};
        while (b < e) {
            sum = numbers[b] + numbers[e];
            if (sum == target) {
                res = int[]{b, e};
                break;
            } else if (sum < target) {
                b++;
            } else { // sum > target
                e--;
            } // if
        } // while
        return res;
    } // twoSum()

    // 190. Reverse Bits
    // 
    public int reverseBits(int n) {
        x = ((x & 0xaaaaaaaa) >> 1) | ((x & 0x55555555) << 1);
        x = ((x & 0xcccccccc) >> 2) | ((x & 0x33333333) << 2);
        x = ((x & 0xf0f0f0f0) >> 4) | ((x & 0x0f0f0f0f) << 4);
        x = ((x & 0xff00ff00) >> 8) | ((x & 0x00ff00ff) << 8);
        x = ((x & 0xffff0000) >> 16) | ((x & 0x0000ffff) << 16);
        return x;
    } // reverseBits()
    // 99/11
    // you need treat n as an unsigned value
    public int reverseBits0(int n) {
        // I chose to use a new int instead of doing it place for simplicity
        int res = 0;
        int claw = 1;
        for (int i = 0; i < 31; i++) {
            claw = claw << i; // put the claw in position
            claw = claw & n; // grab the bit
            claw = claw >>> i; // move claw back to the starting position
            claw = claw << 31 - i; // put the claw in position to place
            res = res | claw; // place the bit
            claw = 1; // reset the claw
        } // if
        return res;
    } // reverseBits
    // 198. House Robber
    //
    public int rob(int[] nums) {
        return 0;
    } // rob()
    // 200. Number of Islands
    // 77/32, 98/1
    public int numIslands(char[][] grid) {
        int res = 0;
        int m = grid.length;
        int n = grid[0].length;
        for (int x = 0; x < m; x++) {
            for (int y = 0; y < n; y++) {
                if (grid[x][y] == '1') {
                    res++;
                    deleteIsland(grid, x, y, m, n);
                } // if
            } // for y
        } // for x 
        return res;
    } // numIslands()
    // recursively 'deletes' an island by turning 1s to 0s
    private void deleteIsland(char[][] grid, int ix, int iy, int m, int n) {
        // bounds check and check for land
        if (ix < 0 || ix >= m || iy < 0 || iy >= n || grid[ix][iy] == '0') {
            return;
        } // if
        // delete land
        grid[ix][iy] = '0';
        // check surrounding land
        deleteIsland(grid, ix + 1, iy, m, n);
        deleteIsland(grid, ix - 1, iy, m, n);
        deleteIsland(grid, ix, iy - 1, m, n);
        deleteIsland(grid, ix, iy + 1, m, n);
    } // deleteIsland()
    // 206. Reverse Linked List
    // 100/65-90
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        } // if
        ListNode n = reverseList(head.next);
        head.next.next = head;
        head.next = null;
        return n; 
    } // reverseList()
    // 212. Word Search II
    // 6/7 @ 2189ms
    class Solution {
    public List<String> findWords(char[][] board, String[] words) {
        int m = board.length;
        int n = board[0].length;
        // build the Trie
        Trie t = new Trie();
        for (String word : words) {
            Trie.addWord(t, word);
        } // for word
        List<String> res = new ArrayList<>();
        Set<String> resSet = new HashSet<>();
        // scan through the board seeing if that letter starts a word
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                // if the letter is, see if any adjacent chars are children of the start char
                // build a string along the way to add to res when an end is encounter
                if (t.contains(board[r][c])) {
                    StringBuilder sb = new StringBuilder();
                    find(t, sb, res, resSet, board, r, c);
                } // if
            } // for c
        } // for r
        return res;
    } // findWords()
    private void find(Trie t, StringBuilder sb, List<String> res, Set<String> resSet, char[][] board, int r, int c) {
        // add current letter
        sb.append(board[r][c]);
        // move down trie
        t = t.get(board[r][c]);
        board[r][c] = 0; // so this space won't be rechecked
        // if a whole word has been found
        if (t.isEnd() && !resSet.contains(sb.toString())) {
            res.add(sb.toString());
            resSet.add(sb.toString());
        } // if
        // search surrounding letters
        int[] dirs = new int[]{0, -1, 0, 1, 0};
        for (int i = 1; i < dirs.length; i++) {
            int nextR = r + dirs[i];
            int nextC = c + dirs[i - 1];
            if (validSpace(board, nextR, nextC) && t.contains(board[nextR][nextC])) {
                find(t, sb, res, resSet, board, nextR, nextC);
            } // if
        } // for i
        board[r][c] = sb.charAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
    } // find()
    private boolean validSpace(char[][] board, int r, int c) {
        return !(r < 0 || board.length <= r ||
               c < 0 || board[0].length <= c ||
               board[r][c] == 0);
    } // validSpace()
    class Trie {
        Trie[] children;
        boolean end;

        Trie() {
            children = new Trie[26];
            end = false;
        } // Trie()
        boolean isEnd() {
            return end;
        } // isEnd()
        boolean contains(char c) {
            return children[c - 'a'] != null;
        } // contains
        Trie get(char c) {
            return children[c - 'a'];
        } // getChild()
        void add(char c) {
            children[c - 'a'] = new Trie();
        } // add()
        static void addWord(Trie t, String s) {
            for (char c : s.toCharArray()) {
                if (!t.contains(c)) {
                    t.add(c);
                } // if
                t = t.get(c);
            } // for c
            t.end = true;
        } // addWord()
    } // class Trie
    } // mark

    // 226. Invert Binary Tree
    // 100/74
    public TreeNode invertTree(TreeNode root) {
        if (root != null) {
            TreeNode temp = root.left;
            root.left = root.right;
            root.right = temp;

            invertTree(root.left);
            invertTree(root.right);
        } // if
        return root;
    } // invertTree()
    // 238. Product of Array Except Self
    // 52/80 @ 3 ms; 88/90 @ 2 ms; 
    // make 2 arrs which hold the prefix and suffix at each point
    // then multiply each together.
    public int[] productExceptSelf(int[] nums) {
        int[] pref = new int[nums.length];
        pref[0] = 1;
        pref[1] = nums[0];
        int[] suff = new int[nums.length];
        suff[nums.length - 1] = 1;
        suff[nums.length - 2] = nums[nums.length - 1];
        for (int p = 2; p < pref.length; p++) {
            pref[p] = pref[p - 1] * nums[p - 1];
        } // for p
        for (int s = suff.length - 3; s >= 0; s--) {
            suff[s] = suff[s + 1] * nums[s + 1];
        } // for s
        for (int i = 0; i < nums.length; i++) {
            nums[i] = pref[i] * suff[i];
        } // for i
        return nums;
    } // productExceptSelf()
    // 257. Binary Tree Paths
    // 95/52 @ 2ms
    public List<String> binaryTreePaths(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        List<String> res = new LinkedList();
        dfs(root, sb, res);
        return res;
    } // binaryTreePaths()
    private void dfs(TreeNode root, StringBuilder sb, List<String> res) {
        if (root == null) {
            return;
        } // if
        sb.append(root.val);
        if (root.left == null && root.right == null) { // if a leaf
            res.add(sb.toString());
        } else { // else not a leaf
            sb.append("->");
            dfs(root.left, sb, res);
            dfs(root.right, sb, res);
            sb.delete(sb.length() - 2, sb.length()); // remove "->"
        } // if
        // remove the root val from the str
        while (sb.length() > 0 && sb.charAt(sb.length() - 1) != '>') {
            sb.deleteCharAt(sb.length() - 1);
        } // while
    } // dfs()

    // 286. Missing Number
    // 73/59 @ 1 ms; 100/54 @ 0 ms
    public int missingNumber(int[] nums) {
        // a ^ b ^ b = a
        // therefore, whatever is not canceled out after ^ each
        // 0 to n and each val in nums will be the missing
        int xor = nums.length;
        for (int i = 0; i < nums.length; i++) {
            xor = xor ^ i ^ nums[i];
        } // for
        return xor;
    } // missingNumber()
    // 73/10 @ 1 ms; 100/91 @ 0 ms
    public int missingNumber0(int[] nums) {
        int s = 0;
        // add the ints up to and including n
        for (int i = 0; i <= nums.length; i++) {
            s += i;
        } // for
        // subtract the val at each index
        for (int i = 0; i < nums.length; i++) {
            s -= nums[i];
        } // for
        return s;
    } // missingNumber()
    // 299. Bulls and Cows
    // bad but on the right track
    public String getHint(String secret, String guess) {
        int bulls = 0;
        for (int i = 0; i < secret.length(); i++) {
            if (secret.charAt(i) == guess.charAt(i)) {
                bulls++;
            } // if
        } // for i
        int[] numCounts = new int[10];
        boolean[] presentSecret = new boolean[10];
        boolean[] presentGuess = new boolean[10];
        int cows = 0;
        for (int i = 0; i < secret.length(); i++) {
            if (secret.charAt(i) != guess.charAt(i)) {
                presentSecret[secret.charAt(i) - '0'] = true;
                presentGuess[guess.charAt(i) - '0'] = true;
                cows++;
                numCounts[secret.charAt(i) - '0']++;
                numCounts[guess.charAt(i) - '0']--;
            } // if
        } // for i
        for (int i = 0; i < present.length; i++) {
            if (presentSecret[i]) {
                cows -= numCounts[i];
            } // if
            if (presentGuess[i] && !presentSecret[i]) {
                cows += numCounts[i];
            } // if
        } // for i
        return bulls + "A" + cows + "B";
    } // getHint()

    // 332. Coin Change
    // TODO
    public int coinChange(int[] coins, int amount) {
        Arrays.sort(coins);
        return coinChange2(coins, amount, coins.length - 1);
    } // coinChange()
    public int coinChange2(int[] coins, int amount, int i) {
        if (amount == 0) return 0;
        int count, res;
        while (i >= 0) {
            if (coins[i] <= amount) {
                count = amount / coins[i];
                do {
                    res = coinChange2(coins, amount - count * coins[i], i - 1);
                    if (res != -1) return count + res;
                    count--;
                } while (res == -1 && count > 0);
            } // if
            i--;
        } // while
        return -1;
    } // coinChange2()
    public int coinChange(int[] coins, int amount) {
        if (amount == 0) return 0;
        Arrays.sort(coins);
        int count = -1;
        int coinVal;
        int res;
        for (int i = coins.length - 1; i >= 0; i--) {
            if (coins[i] == -1) continue;
            coinVal = coins[i];
            coins[i] = -1;
            if (coinVal <= amount) {
                count = amount / coinVal;
                if (amount % coinVal == 0) return count;
                do {
                    res = coinChange(coins, amount - count * coinVal);
                    count--;
                } while (res == -1 && count > 0);
                if (res != -1) return count + res;
                return coinChange(coins, amount);
            } // if
            coins[i] = coinVal;
        } // for
        return -1;
    } // coinChange()
    // 338. Counting Bits
    // 99/86
    public int[] countBits(int n) {
        int[] res = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            if (i % 2 == 1) {
                // if odd it has the same bits + 1 as the even before
                res[i] = res[(i - 1)] + 1;
            } else {
                // evens have the same bits as half their value
                res[i] = res[i / 2];
            } // if
        } // for
        return res;
    } // countBits()
    // 371. Sum of Two Integers
    // 100/74
    public int getSum(int a, int b) {
        int res = 0;
        int bcurrent = 0;
        int carry = 0;
        for (int i = 0; i < 32; i++) {
            // get the bits
            int ba = (((1 << i) & a) >> i);
            int bb = (((1 << i) & b) >> i);
            // checking if 
            if (ba == bb) { // both 1 or both 0
                bcurrent = carry; // carry the bit
                if (ba == 1) { // determine the carry
                    carry = 1;
                } else {
                    carry = 0;
                } // if
            } else { // they are 1 and 0
                if (carry == 0) {
                    bcurrent = 1;
                } // else nothing updates
            } // if
            // insert the new bit
            res = res | (bcurrent << i);
            // reset bcurrent
            bcurrent = 0;
        } // for
        return res;
    } // getSum()
    // 430. Flatten a Multilevel Doubly Linked List
    // 100/83 @ 0ms
    public Node flatten(Node head) {
        Stack<Node> stack = new Stack<>();
        Node curr = head;
        while (curr != null) { 
            if (curr.child != null) { // has child
                // if there is a next node of the node with a child, add it to the stack
                if (curr.next != null) {
                    stack.push(curr.next);
                } // if
                // make the child the next node
                curr.next = curr.child;
                curr.next.prev = curr;
                curr.child = null;
            } else if (curr.next == null) { // no next
                // once the end of a set of nodes is reached,
                // check the stack for any nodes that were previously removed and append them
                if (!stack.isEmpty()) {
                    curr.next = stack.pop();
                    curr.next.prev = curr;
                } // if
            } // if
            curr = curr.next;
        } // while
        return head;
    } // flatten()

    // 433. Minimum Genetic Mutation
    // 93/31 @ 1ms
    public int minMutation(String startGene, String endGene, String[] bank) {
        // HashSet for seen; Queue for BFS
        Set<String> seen = new HashSet<>();
        Queue<String> q = new LinkedList<>();
        // start with startGene: add to q and set
        q.offer(startGene);
        seen.add(startGene);
        int mutations = 0;
        // increment a counter, bfs the currently queued genes
        // Repeat until endGene is found or queue is empty
        while (!q.isEmpty()) {
            mutations++;
            int currentQ = q.size();
            while (currentQ > 0) {
                String currentGene = q.poll();
                // loop through each gene in bank and add unseen singly differing genes to q and set
                for (String gene : bank) {
                    if (!seen.contains(gene) && isAdjacent(currentGene, gene)) {
                        if (gene == endGene) { // path found
                            return mutations;
                        } // if
                        // else q it up
                        q.offer(gene);
                        seen.add(gene);
                    } // if
                } // for gene
                currentQ--;
            } // while
        } // while
        return -1; // if a path to the endGene was never found
    } // minMutation()

    // Genes are adjacent if they differ in one and only one letter
    private boolean isAdjacent(String currentGene, String gene) {
        int differences = 0;
        for (int i = 0; i < currentGene.length(); i++) {
            if (currentGene.charAt(i) != gene.charAt(i)) {
                differences++;
            } // if
        } // for i
        return differences == 1;
    } // isAdjacent()

    // 450. Delete Node in a BST
    // 100/79 @ 0m;
    public TreeNode deleteNode(TreeNode root, int key) {
        TreeNode tempRoot = new TreeNode(Integer.MAX_VALUE);
        tempRoot.left = root;
        TreeNode parent = tempRoot;
        TreeNode child = root;
        boolean leftward = true;
        // look for the node with the key
        while (child != null && child.val != key) {
            parent = child;
            if (key < child.val) { // key is leftward
                leftward = true;
                child = child.left;
            } else { // key is rightward
                leftward = false;
                child = child.right;
            } // if
        } // while
        if (child == null) { // key not found
            return tempRoot.left;
        } // if
        // else the node with the key exists and is pointed to by child so delete child
        // find child's predecessor
        if (child.left == null) { // no predecessor
            if (leftward) {
                parent.left = child.right;
            } else {
                parent.right = child.right;
            } // if
        } else if (child.left.right == null) { // immediate left child is predecessor
            TreeNode pred = child.left;
            pred.right = child.right;
            if (leftward) {
                parent.left = pred;
            } else {
                parent.right = pred;
            } // if
        } else { // predecessor is right of the left child
            TreeNode predParent = child.left;
            TreeNode pred = predParent.right;
            // traverse down to the predecessor
            while (pred.right != null) {
                predParent = pred;
                pred = pred.right;
            } // while
            // remove pred
            predParent.right = pred.left; // assign its only child to its parent
            pred.left = null; // remove its only child
            pred.left = child.left; // give pred the node to remove's children
            pred.right = child.right;
            // now replace child with pred
            if (leftward) {
                parent.left = pred;
            } else {
                parent.right = pred;
            } // if
        } // if
        return tempRoot.left;
    } // dN()
    public TreeNode deleteNode(TreeNode root, int key) {
        if (key == root.val) {
            if (root.left == null) { // no predecessor
                root = root.right();
                return root;
            } else { // there is a predecessor
                if (root.left.right == null) { // predecessor is root's left child
                    root.left.right = root.right;
                    root = root.left;
                } else { // predecessor is not immediate left child
                    TreeNode parent = root.left;
                    TreeNode child = parent.right;
                    while (child.right != null) {
                        parent = child;
                        child = child.right;
                    } // while
                    // child will point to the predecessor after this for loop
                    // parent's right child will be the predecessor
                    parent.right = child.left;
                    child.left = null;
                    child.left = root.left;
                    child.right = root.right;
                    root = child;
                } // if
            } // if
        } else { // the node to remove is not the root
        } // if
    } // deleteNode()
    private void removePredecessor(TreeNode parent, TreeNode child) {
    } // removePredecessor()

    private void removePredecessor(TreeNode nodeParent, TreeNode node) {
        if (node.left == null) { // no predecessor
            nodeParent.left = null;
        } else if (node.left.right == null) { // the left child is the predecessor
            node.left.right = node.right;
            node = node.left;
        } // if
        TreeNode parent = node;
        TreeNode child = parent.left;
        while (child.right != null) {
            parent = child;
            child = child.right;
        } // while
    } // findPredecessor()

    // 523. Continuous Subarray Sum
    //
    public boolean checkSubarraySum(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        int sum = 0;
        int rem;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            rem = sum % k;
            if (!map.containsKey(rem)) {
                map.put(rem, i);
            } // if
            // if another sum with the same remainder exists at least 2 indeces before i
            if (i - 1 > map.get(rem)) {
                return true;
            } // if
        } // for i
        return false;
    } // checkSubArraySum()

    // Time Limit Exceded
    public boolean checkSubarraySum(int[] nums, int k) {
        int[] subArraySum = new int[nums.length];
        subArraySum[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            subArraySum[i] = subArraySum[i - 1] + nums[i];
        } // for i
        int sum; // temp var
        for (int i = 1; i < subArraySum.length; i++) {
            // check if the subArraySum is a multiple
            if (isMultiple(subArraySum[i], k)) {
                return true;
            } // if
            // else check if any subArraySum - subArraySum is a multiple
            for (int j = 0; j < i - 1; j++) {
                sum = subArraySum[i] - subArraySum[j];
                if (isMultiple(sum, k)) {
                    return true;
                } // if
            } // for j
        } // for i
        return false; // if no multiples were found 
    } // checkSubarraySum()
    // true if n is a multiple of k
    private boolean isMultiple(int n, int k) {
        return ((n == 0) || (n % k == 0));
    } // isMultiple()

            // 572. Subtree of Another Tree
            public boolean isSubtree(TreeNode root, TreeNode subRoot) {
                if (root == null || subRoot == null) {
                    if (root == subRoot) {
                        return true;
                    } // if
                    return false;
                } // if
                if (isSameTree(root, subRoot)) {
                    return true;
                } // if
                return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
                // isSubtree(root, subRoot, false);
            } // isSubtree
    // 695. Max Area of Island
    // 100/93
    public int maxAreaOfIsland(int[][] grid) {
        int max = 0;
        int m = grid.length;
        int n = grid[0].length;
        for (int x = 0; x < m; x++) {
            for (int y = 0; y < n; y++) {
                if (grid[x][y] == 1) {
                    max = Math.max(max, deleteIsland(grid, x, y, m, n));
                } // if
            } // for y
        } // for x 
        return max;
    // delete island and return its size
    } // maxAreaOfIsland()
    private int deleteIsland(int[][] grid, int ix, int iy, int m, int n) {
        // bounds check and check for land
        if (ix < 0 || ix >= m || iy < 0 || iy >= n || grid[ix][iy] == 0) {
            return 0;
        } // if
        // delete land
        grid[ix][iy] = 0;
        // check surrounding land
        int size = 1 +
            deleteIsland(grid, ix + 1, iy, m, n) +
            deleteIsland(grid, ix - 1, iy, m, n) +
            deleteIsland(grid, ix, iy - 1, m, n) +
            deleteIsland(grid, ix, iy + 1, m, n);
        return size;
    } // deleteIsland()
    // 997. Find The Town Judge
    // 57/25 @ 9ms
    public int findJudge(int n, int[][] trust) {
        int[] trusts = new int[n]; // how many people i trusts
        int[] trustedBy = new int[n]; // how many people trust i
        //boolean[] trustsSelf = new boolean[n]; // if i trusts themself
        for (int[] t : trust) {
            trusts[t[0] - 1]++;
            trustedBy[t[1] - 1]++;
        } // for t
        for (int i = 1; i <= n; i++) {
            if (trusts[i - 1] == 0 && trustedBy[i - 1] == n - 1) {
                return i;
            } // if
        } // for i
        return -1;
    } // findJudge()

    // 1143. Longest Common Subsequence
    //
    public int longestCommonSubsequence(String a, String b) {
        int afm = -1; // first common char in a
        int bfm = -1; // first common char in b
        // find fm
        int[] firstOccurenceA = new int[26];
        Arrays.fill(firstOccurenceA, -1);
        int[] firstOccurenceB = new int[26];
        Arrays.fill(firstOccurenceB, -1);
        for (int i = 0; i < a.length() || i < b.length(); i++) {
            if (i < a.length()) {
                if (firstOccurenceB[a.charAt(i) - 'a'] != -1) { // found match
                    afm = i;
                    bfm = firstOccurenceB[a.charAt(i) - 'a'];
                    break;
                } else if (firstOccurenceA[a.charAt(i) - 'a'] == -1) {
                    firstOccurenceA[a.charAt(i) - 'a'] = i;
                } // if
            } // if
            if (i < b.length()) {
                if (firstOccurenceA[b.charAt(i) - 'a'] != -1) { // found match
                    bfm = i;
                    afm = firstOccurenceA[b.charAt(i) - 'a'];
                    break;
                } else if (firstOccurenceB[b.charAt(i) - 'a'] == -1) {
                    firstOccurenceB[b.charAt(i) - 'a'] = i;
                } // if
            } // if
        } // for 
        if (afm == -1) { // no matching chars
            return 0;
        } // if
        //System.out.printf("afm: %d, bfm: %d\n", afm, bfm);

        int res = 0;
        // find subsequence for a of b and b of a
        for (int i = 0; i < 2; i++) {
            int ap = afm;
            for (int bp = bfm; bp < b.length(); bp++) {
                if (a.charAt(ap) == b.charAt(bp)) {
                    ap++;
                } // if
            } // for bp
            res = Math.max(res, ap - afm);

            // swap
            String temp = a;
            a = b;
            b = temp;
            int tempInt = afm;
            afm = bfm;
            bfm = tempInt;
        } // for i
        return res;
    } // lCS()

    // 1396. Design Underground System
    //
    class UndergroundSystem {

        class CheckIn {
            int checkInTime;
            String checkInStation;

            CheckIn(int checkInTime, String checkInStation) {
                this.checkInTime = checkInTime;
                this.checkInStation = checkInStation;
            } // CheckIn()

        } // class CheckIn

        class AverageTrip {
            int numberOfTrips;
            int totalTimeOfTheTrips;

            AverageTrip() {
            } // AverageTrip()

            void addTrip(int checkInTime, int checkOutTime) {
                totalTimeOfTheTrips += checkOutTime - checkInTime;
                numberOfTrips++;
            } // addTrip()

            double getAverageTime() {
                return Double.valueOf(totalTimeOfTheTrips) / Double.valueOf(numberOfTrips);
            } // getAverageTime()
        } // class AverageTrip

        Map<Integer, CheckIn> checkIns;
        Map<String, AverageTrip> averageTrips;

        public UndergroundSystem() {
            checkIns = new HashMap<>();
            averageTrips = new HashMap<>();
        } // UndergroundSystem()

        public void checkIn(int id, String stationName, int t) {
            // if already checked in
            if (checkIns.containsKey(id)) {
                return;
            } // if
            CheckIn cin = new CheckIn(t, stationName);
            checkIns.put(id, cin);
        } // checkIn()

        public void checkOut(int id, String stationName, int t) {
            if (!checkIns.containsKey(id)) {
                return;
            } // if
            CheckIn cin = checkIns.remove(id);
            String tripStations = cin.checkInStation + '#' + stationName;
            AverageTrip at = averageTrips.getOrDefault(tripStations, new AverageTrip());
            at.addTrip(cin.checkInTime, t);
        } // checkOut()

        public double getAverageTime(String startStation, String endStation) {
            AverageTrip at = averageTrips.get(startStation + '#' + endStation);
            return at.getAverageTime();
        } // getAverageTime()
    } // class UndergroundSystem

    // 1472. Design Browser History
    // 91/92 @ 56ms
    class BrowserHistory {
        List<String> history;
        int placeInHistory;
        int endOfHistory;

        public BrowserHistory(String homepage) {
            history = new ArrayList<>();
            history.add(homepage);
            placeInHistory = 0;
            endOfHistory = 0;
        }

        // no: O(amortized constant)/
        // usually consant but sometimes linear
        public void visit(String url) {
            placeInHistory++;
            endOfHistory = placeInHistory;
            history.add(placeInHistory, url);
        }

        // O(n)/
        public String back(int steps) {
            placeInHistory -= steps;
            if (placeInHistory < 0) {
                placeInHistory = 0;
            } // if
            return history.get(placeInHistory);
        }

        // O(n)
        public String forward(int steps) {
            placeInHistory += steps;
            if (placeInHistory > endOfHistory) {
                placeInHistory = endOfHistory;
            } // if
            return history.get(placeInHistory);
        }
    } // class BrowserHistory
    
    // 1544. Make The String Great
    //
    public String makeGood(String s) {
        char a;
        char b;
        String res;
        for (int i = 0; i < s.length() - 1; i++) {
            a = s.charAt(i);
            b = s.charAt(i + 1);
            if (a - b == 'a' - 'A' || b - a == 'a' - 'A') {
                res.append(a);
            } else {
                i += 2;
            } // if
        } // for
    } // makeGood()

    // 1657. Determine If Two Strings Are Close
    // 
    public boolean closeStrings(String word1, String word2) {

    } // closeStrings()
    public boolean closeStrings(String word1, String word2) {
        if (word1.length() != word2.length()) {
            return false;
        } // if
        int[] word1Counts = new int[26];
        int[] word2Counts = new int[26];

        for (int i = 0; i < word1.length(); i++) {
            word1Counts[word1.charAt(i) - 'a']++;
            word2Counts[word2.charAt(i) - 'a']++;
        } // for i

        int val = 0;
        for (int i = 0; i < word1Counts.length; i++) {
            //System.out.printf("w1: %d; w2: :d\n", word1Counts[i], word1Counts[i]);
            if (word1Counts[i] != 0) val ^= word1Counts[i];
            if (word2Counts[i] != 0) val ^= word2Counts[i];
        } // for i
        
        return val == 0;

        /*
        Map<Integer, Integer> countCounts = new HashMap<>();
        for (int i = 0; i < word1.counts; i++) {
            int w1 = countCounts.getOrDefault(word1Counts[i], 0)
            int w2 = countCounts.getOrDefault(word2Counts[i], 0)
            countCounts.put(word1Counts[i], w1);
            countCounts.put(word2Counts[i], w2);
        } // for i

        for (int n : countCounts) {
            if (n != 0) {
                return false;
            } // if
        } // for n

        return true;
        */

        /*
        int val = 0;
        for (int i = 0; i < word1Counts.length; i++) {
           val ^= word1Counts[i];
            val ^= word2Counts[i];
        } // for i
        return val == 0;
        */
    } // closeStrings()

    // 1662. Check If Two String Arrays are Equivalent
    // 96/99 @ 1ms
    public boolean arrayStringsAreEqual(String[] word1, String[] word2) {
        int ip1 = 0;
        int ip2 = 0;
        int cp1 = 0;
        int cp2 = 0;
        while (ip1 < word1.length && ip2 < word2.length) {
            //System.out.printf("ip1: %d, cp1: %d, ip2: %d, cp2: %d\n", ip1, cp1, ip2, cp2);
            if (word1[ip1].charAt(cp1) != word2[ip2].charAt(cp2)) {
                return false;
            } // if
            cp1++;
            if (cp1 == word1[ip1].length()) {
                ip1 += 1;
                cp1 = 0;
            } // if
            cp2++;
            if (cp2 == word2[ip2].length()) {
                ip2 += 1;
                cp2 = 0;
            } // if
        } // while
        return ((ip1 == word1.length) && (ip2 == word2.length));
    } // arrayStringsAreEqual()

    //1834 Single-Theaded CPU
    // 21/12 @ 214 ms
    public int[] getOrder(int[][] tasks) {
        // add the tasks to a priority queue based on enque time
        PriorityQueue<Task> waitingToEnque = new PriorityQueue<>((Task t1, Task t2) -> t1.enqueTime - t2.enqueTime);
        for (int i = 0; i < tasks.length; i++) {
            waitingToEnque.offer(new Task(i, tasks[i][0], tasks[i][1]));
        } // for i

        // create a priority queue based on processing time for tasks ready to process
        PriorityQueue<Task> waitingToProcess = new PriorityQueue<>((Task t1, Task t2) -> t1.processingTime - t2.processingTime);

        // run the clock to find the tasks ready to process
        int[] res = new int[tasks.length];
        int tasksRun = 0;
        int clock = 1;
        while (!waitingToEnque.isEmpty() && !waitingToProcess.isEmpty()) {
            // enque tasks that are ready to be processed
            while (!waitingtoEnque.isEmpty() && waitingToEnque.peek().enqueTime <= clock) {
                waitingToProcess.offer(waitingToEnque.poll());
            } // while
            
            // if there are no tasks ready to process, increment clock and continue
            if (waitingToProcess.isEmpty()) {
                clock = waitingToEnque.peek().enqueTime;
                continue;
            } // if

            // will 'fast forward' the clock to end of the current tasks' processing time so 
            // may always find the next process to run at this point in execution
            // if there are multiple tasks with the same processing time, run the one with the smallest index
            PriorityQueue<Task> run = new PriorityQueue<>((Task t1, Task t2) -> t1.index - t2.index);
            run.offer(waitingToProcess.poll());
            while (!waitingToProcess.isEmpty() && run.peek().processingTime == waitingToProcess.peek().processingTime) {
                run.offer(waitingToProcess.poll());
            } // while

            System.out.println("running");
            // new process the next task
            Task nextTask = run.poll();
            clock += nextTask.processingTime; // 'fast forward' clock
            res[tasksRun] = nextTask.index; // add to res
            tasksRun++;

            // return the unrun tasks to waitingToProcess
            while (!run.isEmpty()) {
                waitingToProcess.offer(run.poll());
            } // while
        } // while clock

        return res;
    } // getOrder()

    class Task {
        int index;
        int enqueTime;
        int processingTime;

        Task(int index, int enqueTime, int processingTime) {
            this.index = index;
            this.enqueTime = enqueTime;
            this.processingTime = processingTime;
        } // Task()
    } // Task


} // Problems
