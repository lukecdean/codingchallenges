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

} // Problems
