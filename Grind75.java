public class Grind75 {

    // 1. Two Sum
    // 92/31
    public int[] twoSum(int[] nums, int target) {
        int[] res = new int[2];
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                res[0] = i;
                res[1] = map.get(target - nums[i]);
                break;
            } else {
                map.put(nums[i], i);
            } // if
        } // for
        return res;
    } // twoSum()

    // 13. Roman to Integer
    // 71/61 @ 7ms; 94/64 @ 5ms
    public int romanToInt0(String s) {
        int curr;
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            curr = romanVal(s.charAt(i));
            if (i < s.length() - 1 && curr < romanVal(s.charAt(i + 1))) {
                curr = romanVal(s.charAt(i + 1)) - curr;
                i++;
            }
            count += curr;
        } // for
        return count;
    } // romanToInt()
    private int romanVal(char c) {
        switch (c) {
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            case 'M': return 1000;
            default: return -1;
        } // switch
    } // romanVal()
    // ~50% slower
    public int romanToInt(String s) {
        HashMap<Character, Integer> romanMap = new HashMap<>();
        romanMap.put('I', 1);
        romanMap.put('V', 5);
        romanMap.put('X', 10);
        romanMap.put('L', 50);
        romanMap.put('C', 100);
        romanMap.put('D', 500);
        romanMap.put('M', 1000);
        int count = 0;
        int curr;
        for (int i = 0; i < s.length(); i++) {
            curr = romanMap.get(s.charAt(i));
            if (i < s.length() - 1 && curr < romanMap.get(s.charAt(i + 1))) {
                curr = romanMap.get(s.charAt(i + 1)) - curr;
                i++;
            }
            count += curr;
        } // for
        return count;
    } // romanToInt()


    // 20. Valid Parentheses
    // 91/34
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        char chari;
        for (int i = 0; i < s.length(); i++) {
            chari = s.charAt(i);
            if (chari == '(' || chari == '{' || chari == '[') {
                stack.push(chari);
            } else {
                if (stack.size() == 0) 
                    return false;
                switch (chari) {
                    case ')':
                        if (stack.pop() != '(')
                            return false;
                        break;
                    case '}':
                        if (stack.pop() != '{')
                            return false;
                        break;
                    case ']':
                        if (stack.pop() != '[')
                            return false;
                        break;
                } // switch
            } // if
        } // for
        return stack.size() == 0;
    } // isValid()
    
    // 21. Merge Two Sorted Lists
    // 80/29
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode res = new ListNode();
        ListNode end = res;
        // merge the lists node by node until one of them ends
        while (list1 != null && list2 != null) {
            // append the lowest value node and move down the list
            if (list1.val <= list2.val) {
                end.next = list1;
                list1 = list1.next;
            } else {
                end.next = list2;
                list2 = list2.next;
            } // if 
            end = end.next;
        } // while
        if (list1 == null) {
            end.next = list2;
        } else {
            end.next = list1;
        } // if
        return res.next;
    } // mergeTwoLists()

    // 53. Maximum Subarray
    // 10/78 @ 4ms; 
    public int maxSubArray(int[] nums) {
        int count = nums[0];
        int max = count;
        if (count < 0) count = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < 0) {
                if (count + nums[i] > 0) {
                    count += nums[i];
                    max = max > count ? max : count;
                } else {
                    count = nums[i];
                    max = max > count ? max : count;
                    count = 0;
                } // if
            } else { // nums[i] >= 0
                count += nums[i];
                max = max > count ? max : count;
            } // if

            /*
            // if adding the next num to the count is a net negative
            // move past the current subarray
            if (count + nums[i] < 0) {
                // check if the value of the single num is a greater value
                max = Math.max(max, nums[i]);
                count = 0;
            } else if (count + nums[i] == 0) {
                count = nums[i];
                max = Math.max(max, nums[i]);
            } else {
                // if it is still a net positive to add the next num, add it and compare
                count += nums[i];
                max = Math.max(max, count);
            } // if
            */
        } // for
        return max;
    } // maxSubArray()

    // 57. Insert Inteval
    // O(n)/O(n) 45/77 @ 3ms; 
    public int[][] insert(int[][] intervals, int[] newInterval) {
        if (intervals.length == 0) return new int[]{newInterval};
        int i; // the index at which newInterval is to be inserted
        for (i = 0; i < intervals.length; i++) {
            if (newInterval[0] <= intervals[i][0]) break;
        } // for i
        // check if no overlap
        if (i == 0) {
            if (newInterval[1] < intervals[i][0]) return insertInterval(intervals, newInterval, i);
        } else if (i == intervals.length) {
            if (intervals[i - 1][1] < newInterval[0]) return insertInterval(intervals, newInterval, i); 
        } else if ((intervals[i - 1][1] < newInterval[0]) && (newInterval[1] < intervals[i][0])) {
            return insertInterval(intervals, newInterval, i);
        } // if
        // else there is overlap
        // assume the bounds of the overlap to begin with
        int s = Math.max(0, i - 1); // start of overlap
        int e = intervals.length - 1; // end of overlap
        boolean overlapping = false;
        for (int j = s; j < intervals.length; j++) {
            int newIntS = newInterval[0];
            int newIntE = newInterval[1];
            int jIntS = intervals[j][0];
            int jIntE = intervals[j][1];
            // if the start or end of newInterval is within interval j
            // if there is overlap
            boolean isOverlap = (jIntS <= newIntS && newIntS <= jIntE) || (jIntS <= newIntE && newIntE <= jIntE);
            // or if the j interval is entirely inside newInterval
            isOverlap = isOverlap || (newIntS <= jIntS && jIntE <= newIntE);
            if (isOverlap) {
                if (overlapping == false) s = j;
                overlapping = true;
            } else { // else not overlapping
                // if it was overlapping but now is not, the previous interval is the final overlap
                if (overlapping == true) {
                    e = j - 1;
                    break;
                } // if
            } // if
        } // for j
        //System.out.printf("s: %d, e: %d\n", s, e);
        return insertIntervalWithOverlap(intervals, newInterval, s, e);
    } // insert()
    private int[][] insertInterval(int[][] intervals, int[] newInterval, int index) {
        int[][] newIntervals = new int[intervals.length + 1][2];
        // i = newIntervals ptr, j = intervals ptr
        for (int i = 0, j = 0; i < newIntervals.length; i++) {
            if (i == index) {
                newIntervals[i] = newInterval;
                continue;
            }
            // only copy over and increment j if insert index is not the current index
            newIntervals[i] = intervals[j];
            j++;
        } // for
        return newIntervals;
    } // insertInterval()
    private int[][] insertIntervalWithOverlap(int[][] intervals, int[] newInterval, int s, int e) {
        // the differnce in s and e is how many intervals from the array will be combined into other intervals
        int newLength = (intervals.length) - (e - s);
        int[][] newIntervals = new int[newLength][2];
        // find the bounds of the new combined interval
        int intervalS = Math.min(intervals[s][0], newInterval[0]);
        int intervalE = Math.max(intervals[e][1], newInterval[1]);
        int[] newCombinedInterval = new int[]{intervalS, intervalE};
        // // i = newIntervals ptr, j = intervals ptr
        // i = intervals ptr, j = newIntervals ptr
        for (int i = 0, j = 0; i < intervals.length; i++) {
            if (s == i) {
                newIntervals[j] = newCombinedInterval;
            } else if (s < i && i <= e) {
                // if inside (s, e]
                continue;
            } else { // are outside s and e
                newIntervals[j] = intervals[i];
            } // if
            j++;
        } // for i
        return newIntervals;
    } // iIWO()

    // 67. Add Binary
    // 36/ @ 5ms
    public String addBinary(String a, String b) {
        11 0
            1 -1
            carry = 1
            count =  
            int ba = a.length() - 1;
        int bb = b.length() - 1;
        String res = "";
        char carry = '0';
        while (ba >= 0 || bb >= 0) {
            int count = 0;
            if (ba >= 0 && a.charAt(ba) == '1') count++;
            if (bb >= 0 && b.charAt(bb) == '1') count++;
            if (carry == '1') count++;
            if (count == 0) {
                res = "0" + res;
                carry = '0';
            } else if (count == 1) {
                res = "1" + res;
                carry = '0';
            } else if (count == 2) {
                res = "0" + res;
                carry = '1';
            } else { // count == 3
                res = "1" + res;
                carry = '1';
            } // if
            // after the fact:
            // current bit = count % 2
            // carry bit = count / 2
            ba--;
            bb--;
        } // while
        if (carry == '1') res = "1" + res;
        return res;
    } // addBinary()

    // 70. Climbing Stairs
    // 100/68 @ 0ms;
    public int climbStairs(int n) {
        // can either take one step or two steps so ways = the sum
        int ways = 0;
        int onestep = 1;
        int twosteps = 0;
        int step = 1;
        while (step <= n) {
            ways = onestep + twosteps;
            step++;
            // shift the twosteps and one step ways
            // ie the twosteps is the same as the next onestep
            // and onestep is the same as the next ways
            twosteps = onestep;
            onestep = ways;
        } // for
        return ways;
    } // climbStairs()

    // 104. Maximum Depth of Binary Tree
    // 100/93
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    } // maxDepth()

    // 110. Balanced Binary Tree
    //
    public boolean isBalanced0(TreeNode root) {
        return Math.abs(maxDepth(root) - minDepth(root)) <= 1;
    } // isBalanced()
    // finds the maximum depth of a Binary Tree
    private int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    } // maxDepth()
    // finds the minimum depth of a Binary Tree
    private int minDepth(TreeNode root) {
        if (root == null) return 0;
        if (root.left == null && root.right == null) return 1;
        if (root.left == null || root.right == null) return 2;
        return 1 + Math.min(minDepth(root.left), minDepth(root.right));
        /*
        if (root.left == null || root.right == null) {
            if (root.left == root.right) return 1;// if both null
            // else only one is null return the min depth of the other
            else if (root.left == null) return 1 + minDepth(root.right);
            else return 1 + minDepth(root.left);
            // else root.right == null
        } // if
        return 1 + Math.max(minDepth(root.left), minDepth(root.right));
        */
    } // minDepth()
    // 99/68 @ 1ms;
    public boolean isBalanced(TreeNode root) {
        return depth(root) != -1;
    } // isBalanced()
    private int depth(TreeNode root) {
        if (root == null) return 0;
        int l = depth(root.left);
        if (l == -1) return -1;
        int r = depth(root.right);
        if (r == -1) return -1;
        // if the depths are more than 1 different
        if (r - l < -1 || r - l > 1) return -1;
        else return 1 + (r > l ? r : l);
    } // depth()

    // 121. Best Time to Buy and Sell Stock
    // 69/55 @ 3 ms; 94/69 @ 2 ms
    public int maxProfit(int[] prices) {
        int max = 0;
        int buy = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] < prices[buy]) {
                buy = i;
            } else {
                max = Math.max(max, prices[i] - prices[buy]);
            } // if
        } // for
        return max;
    } // maxProfit()
    
    // 121. Valid Palindrome
    // 78/70 @ 5 ms
    public boolean isPalindrome(String s) {
        s = s.toLowerCase();
        // two pointer approach
        int b = -1;
        int e = s.length();
        // started the pointers outside the bounds so we can use the method
        // look for the next alpha num char
        b = nextAlphaNumChar(b, 1, s);
        e = nextAlphaNumChar(e, -1, s);
        // start on the ends and work inward until they meet
        while(b < e) {
            // if they ever don't match, it's not a palindrome
            if (s.charAt(b) != s.charAt(e))
                return false;
            // look for the next alpha num char
            b = nextAlphaNumChar(b, 1, s);
            e = nextAlphaNumChar(e, -1, s);
        } // while
        // if no mismatches were found
        return true;
    } // isPalindrome()
    // looks for the next alpha num char
    private int nextAlphaNumChar(int pointer, int step, String s) {
        pointer += step;
        while (0 <= pointer && pointer < s.length()) {
            if (Character.isLetterOrDigit(s.charAt(pointer))) {
                return pointer;
            } // if
            pointer += step;
        } // while
        // only gets here if there is no other alphanum
        return -1;
    } // nextAlphaNumChar()

    // 141. Linked List Cycle
    // 100/40,67 @ 0ms; 
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) return false;
        ListNode fast = head.next; 
        ListNode slow = head; 
        // have one pointer move faster through the list
        // if it ever finds a null next, the list has an end so false
        // if they ever equal, the fast one has looped around and caught up to slow
        // so there is a cycle
        while (fast != null && slow != null) {
            if (slow == fast) return true;
            if (fast.next == null) return false;
            fast = fast.next.next;
            slow = slow.next;
        } // while
        return false;
    } // hasCycle()

    // 169. Majority Element
    //
    public int majorityElement0(int[] nums) {
        int[][] cts = new int[2][2];
        int currentnum = nums[0];
        int currentct = 0;
        boolean b = false;
        for (int num : nums) {
            // deflate the counts every other iter. Only the maj can survive
            if (b) currentct = deflate(cts, currentnum, currentct);
            b = !b;
            if (num == currentnum) {
                currentct++;
            } else {
                tally(cts, currentnum, currentct);
                currentnum = num;
                currentct = 1;
            } // if
        } // for
        return cts[0][0];
    } // majorityElement()
    private void tally(int[][] cts, int currentnum, int currentct) {
        // if currentnum is already in cts, add the count
        if (cts[0][0] == currentnum) cts[0][1] += currentct;
        else if (cts[1][0] == currentnum) cts[1][1] += currentct;
        else { // currentnum is not in cts
            // if currentnum is > the lower count, replace it in cts
            if (currentct > cts[1][1]) {
                cts[1][0] = currentnum;
                cts[1][1] = currentct;
            } // if
        } // if
        // make sure cts is sorted
        if (cts[1][1] > cts[0][1]) {
            int tempnum = cts[1][0];
            int tempct = cts[1][1];
            cts[1][0] = cts[0][0];
            cts[1][1] = cts[0][1];
            cts[0][0] = tempnum;
            cts[0][1] = tempct;
        } // if
    } // tally()
    private int deflate(int[][] cts, int currentnum, int currentct) {
        cts[0][1]--;
        cts[1][1]--;
        // do not want to double decrement a ct
        if (currentnum != cts[0][0] && currentnum != cts[1][0]) currentct--;
        return currentct;
    } // deflate()
    // Moore's Voting, optimal
    public int majorityElement(int[] nums) {
        int maj = nums[0];
        int ct = 1;
        for (int i = 1; i < nums.length; i++) {
            if (ct == 0) {
                maj = nums[i];
                ct++;
            } else if (nums[i] == maj) {
                ct++;
            } else {
                ct--;
            }
        } // for
        return maj;
    } // majortyElement()

    // 206. Reverse Linked List
    // 100/ @ 0ms
    public ListNode reverseList(ListNode head) {
        if (head == null) return null;
        if (head.next == null) return head;
        ListNode prev = head;
        ListNode curr = head.next;
        ListNode temp;
        prev.next = null;
        while (curr.next != null) {
            temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        } // while
        curr.next = prev;
        return curr;
    } // reverseList()
    // 100/94
    public ListNode reverseList(ListNode head) {
        ListNode newHead = null;
        ListNode temp;
        while (head != null) {
            temp = head.next;
            head.next = newHead;
            newHead = head;
            head = temp;
        } // while
        return newHead;
    } // reverseList()

    // 217. Contains Duplicate
    // 89/72 @ 8ms; 
    public boolean containsDuplicate(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        for (int i : nums) {
            if (set.add(i) == false) return true;
        } // for
        return false;
    } // containsDuplicate()

    // 226. Invert Binary Tree
    // 100/60 @ 0 ms
    public TreeNode invertTree(TreeNode root) {
        if (root == null) return root;
        TreeNode temp = root.left;
        root.left = invertTree(root.right);
        root.right = invertTree(temp);
        return root;
    } // invertTree()

    // 232. Implement Queue Using Stack
    // 71/81 @ 1ms; 
    private Stack<Integer> s1;
    private Stack<Integer> s2;

    public MyQueue() {
        s1 = new Stack<>();
        s2 = new Stack<>();
    }
    
    public void push(int x) {
        s1.push(x);
    }
    
    public int pop() {
        flip(s1, s2);
        int res = s2.pop();
        flip(s2, s1);
        return res;
    }
    
    public int peek() {
        flip(s1, s2);
        int res = s2.peek();
        flip(s2, s1);
        return res;
        
    }
    
    public boolean empty() {
       return s1.empty();
    }

    private void flip(Stack<Integer> a, Stack<Integer> b) {
        while (!a.empty()) {
            b.push(a.pop());
        } // while
    } // flip()

    // 235. Lowest Common Ancestor of a Binary Search Tree
    // 34/32 @ 9ms; 48/ @ 8ms; 
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (p.val >= root.val && root.val >= q.val || p.val <= root.val && root.val <= q.val)
            return root;
        // else both nodes lie on the same side of the root
        // so find that side
        if (p.val < root.val) return lowestCommonAncestor(root.left, p, q);
        else return lowestCommonAncestor(root.right, p, q);
    } // lowestCommonAncestor()

    // 242. Valid Anagram
    // 73/68 @ 5 ms; 
    public boolean isAnagram(String s, String t) {
        // must be the same length to be anagrams
        if (s.length() != t.length()) return false;
        // use an array to keep count
        int[] counts = new int['z' - 'a' + 1];
        for (int i = 0; i < s.length(); i++) {
            // add to index of letter from s; subtract from t
            counts['z' - s.charAt(i)]++;
            counts['z' - t.charAt(i)]--;
        } // for
        // check that all letters were canceled out
        for (int c : counts) {
            if (c != 0) return false;
        } // for
        // if no non zeros were found, return true
        return true;
    } // isAnagram()

    // 252. Meeting Rooms
    // O(nlogn) time/O(1) space
    public boolean canAttendMeetings(Interval[] intervals) {
        Arrays.sort(intervals, (int[] a, int[] b) -> a[0] > b[0]);
        for (int i = 0; i < intervals.length - 1; i++) {
            if (intervals[i][1] > intervals[i + 1][0]) return false;
        } // for
        return true;
    } // canAttendMeetings()
    // O(n) time/O(m) space
    // where n is no of meetings and m is units of time when the last meeting will end
    public boolean canAttendMeetings(Interval[] intervals) {
        int maxTime = 0; // the time the final meeting will end
        // finding the maxTime
        for (int[] intv : intervals) {
            maxTime = Math.max(maxTime, intv[1]);
        } // for
        // each unit of time gets a boolean in this array
        boolean[] meetingTimes = new boolean[maxTime];
        // mark each unit of time true if there is a meeting there
        // go through each meeting and each unit of time of that meeting
        for (int[] intv : intervals) {
            for (int i = intv[0]; i <= intv[1]; i++) {
                // if a unit of time is already true, meetings are overlapping
                if (meetingTimes[i] == true) return false;
                meetingTimes[i] = true;
            } // for
        } // for
        return true;
    } // canAttendMeetings()

    // 278. First Bad Version
    // 53/43 @ 22ms; 73/51 @ 18 ms;
    public int firstBadVersion0(int n) {
        long s = 1;
        long mid = (s + n) / 2;
        // fbv will always be in the range [s,n]
        // once they converge on a point, the fbv is found
        while (n != s) {
            //System.out.printf("s = %d, m = %d, n = %d\n", s, mid, n);
            if (isBadVersion((int)mid)) {
                n = (int)mid;
            } else {
                s = mid + 1;
            } // if
            mid = (s + n) / 2;
        } // while
        return (int)mid;
    } // firstBadVersion()
    // 32 ms, 20 ms, 28 ms
    public int firstBadVersion(int n) {
        int s = 0;
        int mid = s + ((n - s) / 2);
        while (s != n) {
            if (isBadVersion(mid)) n = mid;
            else s = mid + 1;
            mid = s + ((n - s) / 2);
        } // while
        return mid;
    } // firstBadVersion()

    // 338. Counting Bits
    // 88/ @ 2ms
    public int[] countBits(int n) {
        int[] res = new int[n + 1];
        for (int i = 0; i < res.length; i++) {
            res[i] = res[i/2]; // n has the same bit pattern (sans bit 0) as n/2
            if ((i & 1) == 1) res[i]++; // if odd there is an extra bit
        } // for
        return res;
    } // countBits()

    // 383. Ransom Note
    // 85/82 @ 4ms
    public boolean canConstruct(String ransomNote, String magazine) {
        int[] letters = new int[26];
        // count the letters in the magazine
        for (int i = 0; i < magazine.length(); i++) letters[magazine.charAt(i) - 'a']++;
        // 'construct' the note if possible, if there aren't enough letters, it is false
        for (int i = 0; i < ransomNote.length(); i++) {
            if (letters[ransomNote.charAt(i) - 'a'] == 0) return false;
            letters[ransomNote.charAt(i) - 'a']--;
        } // for
        return true;
    } // canConstruct()

    // 409. Longest Palindrome
    // 75/85 @ 3ms; 93/ @ 2ms;
    public int longestPalindrome(String s) {
        if (s.length() == 1) return 1;
        int[] cts = new int['z' - 'A' + 1];
        for (int c = 0; c < s.length(); c++) cts[s.charAt(c) - 'A']++;
        // there may only be a single odd count
        boolean oddExists = false;
        int count = 0;
        for (int c : cts) {
            // subtracts 1 from all but the first odd
            //if (oddExists) c = (c / 2) * 2; 
            if (oddExists) c = c - (c & 1);
            //if (!oddExists && c % 2 == 1) oddExists = true;
            if (!oddExists && ((c & 1) == 1)) oddExists = true;
            count += c;
        } // for c
        return count;
    } // longestPalindrome()

    // 543. Diameter of a Binary Tree
    // 65/7 @ 1ms;
    public int diameterOfBinaryTree(TreeNode root) {
        return depthAndDiameter(root)[1];
    } // diameterOfBinaryTree()
    // returns {depth, diameter}
    private int[] depthAndDiameter(TreeNode root) {
        int[] res = new int[2];
        if (root == null) {
            res = new int[]{0, 0};
        } else if (root.left == null && root.right == null) {
            res = new int[]{1, 0};
        } else {
            int[] l = depthAndDiameter(root.left);
            int[] r = depthAndDiameter(root.right);
            // depth is the max of l and r depths + 1
            res[0] = l[0] > r[0] ? l[0] : r[0];
            res[0]++;
            // diameter is the max of diameters from l or r
            res[1] = l[1] > r[1] ? l[1] : r[1];
            // or max of diameter from both l and r
            res[1] = res[1] > l[0] + r[0] ? res[1] : l[0] + r[0];
        } // if
        return res;
    } // depthAndDiameter()

    // 542. 01 Matrix
    // O(n)/O(n) 39/85 @ 26ms;
    // Uses the BFS approach, I would like to implement the DP solution though. TODO
    int[] DIR = new int[]{0, 1, 0, -1, 0};
    public int[][] updateMatrix(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;
        Queue<int[]> q = new ArrayDeque<>();
        // queue up the 0s and mark the rest as -1 (unprocessed)
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (mat[r][c] == 0) q.offer(new int[]{r, c});
                else mat[r][c] = -1;
            } // for c
        } // for r
        while (q.peek() != null) {
            int[] cur = q.poll();
            int r = cur[0];
            int c = cur[1];
            for (int i = 0; i < 4; i++) {
                int rr = r + DIR[i];
                int cc = c + DIR[i + 1];
                if (rr < 0 || m == rr || cc < 0 || n == cc || mat[rr][cc] != -1) continue;
                mat[rr][cc] = mat[r][c] + 1;
                q.offer(new int[]{rr, cc});
            } // for
        } // while
        return mat;
    } // updateMatrix()
        
    // 704. Binary Search
    // 100/73 O(log(n))
    public int search(int[] nums, int target) {
        int l = 0;
        int r = nums.length - 1;
        int mid;
        // loops until l and r pass eachother
        while (l <= r) {
            mid = (l + r) / 2;
            if (nums[mid] > target) {        // target is leftward
                r = mid - 1;
            } else if (nums[mid] < target) { // target is rightward
                l = mid + 1;
            } else {                         // target found
                return mid;
            } // if
        } // while
        // control only gets here if target isn't found
        return -1;
    } // search()

    // 733. Flood Fill
    //
    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        int colorToReplace = image[sr][sc];
        floodFill(image, sr, sc, colorToReplace, color);
        return image;
    } // floodFill()
    public void floodFill(int[][] image, int sr, int sc, int colorToReplace, int color) {
        // bounds check
        if (sr < 0 || sc < 0 ||
                sr >= image.length ||
                sc >= image[0].length) return;
        // if the pixel is a different color or already the color
        if (image[sr][sc] != colorToReplace || image[sr][sc] == color) return;
        image[sr][sc] = color;
        floodFill(image, sr + 1, sc, colorToReplace, color);
        floodFill(image, sr - 1, sc, colorToReplace, color);
        floodFill(image, sr, sc + 1, colorToReplace, color);
        floodFill(image, sr, sc - 1, colorToReplace, color);
    } // floodFill()

    // 844. Backspace String Compare
    // 95/73 @ 1ms;
    public boolean backspaceCompare(String s, String t) {
        // how many backspaces are queued up
        int sb = 0;
        int tb = 0;
        // pointes for each string
        int sptr = s.length() - 1;
        int tptr = t.length() - 1;
        // work backwards through the strings
        while (sptr >= 0 || tptr >= 0) {
            // first check for backspaces
            if (sptr >= 0 && s.charAt(sptr) == '#') {
                sb++;
                sptr--;
            } else if (tptr >= 0 && t.charAt(tptr) == '#') {
                tb++;
                tptr--;
            // then check if there is a char to backspace
            } else if (sb > 0 && sptr >= 0) {
                sb--;
                sptr--;
            } else if (tb > 0 && tptr >= 0) {
                tb--;
                tptr--;
            // now the backspacing is caught up with
            } else {
                // if one string is left with more chars
                if (sptr < 0 || tptr < 0) return false;
                // if the chars at a point do not match
                if (s.charAt(sptr) != t.charAt(tptr)) return false;
                // else no discreprencies found and move on
                sptr--;
                tptr--;
            } // if
        } // while
        return true;
    } // backspaceCompare()

    // 867. Middle of the Linked List
    // 100/ @ 0ms;
    public ListNode middleNode0(ListNode head) {
        int ct = 0;
        ListNode curr = head;
        while (curr != null) {
            ct++;
            curr = curr.next;
        } // while
        if (ct == 0 || ct == 1) return head;
        ct = ct / 2;
        for (int i = 0; i < ct; i++) head = head.next;
        return head;
    } // middleNode()
    // 100/
    public ListNode middleNode(ListNode head) {
        boolean b = false;
        ListNode mid = head;
        while (head != null) {
            if (b) mid = mid.next;
            b = !b;
            head = head.next;
        } // while
        return mid;
    } // middleNode()

    // 973. K Closest Points to Origin
    //
    public int[][] kClosest0(int[][] points, int k) {
        // a max heap sorted by distance
        PriorityQueue<int[]> q = new PriorityQueue<int[]>(k, ((int[] p1, int[] p2) -> (distanceFromO(p1) < distanceFromO(p2) ? 1 : -1)));
        //System.out.println("here");
        // add the first k elements to save some calculations later
        for (int i = 0; i < k; i++) {
            q.offer(points[i]);
        } // for i
        // now start adding to the heap if the point is close enough
        for (int p = k; p < points.length; p++) {
            // if new point is closer than q.peek, offer and poll
            if (distanceFromO(points[p]) < distanceFromO(q.peek()))
                    q.offer(points[p]);
            q.poll();
        } // for p
        return q.toArray(new int[0][0]);
    } // kClosest()
    private double distanceFromO(int[] point) {
        return distanceEuclidian(point[0], point[1], 0, 0);
    } // distanceFromO()
    private double distanceEuclidean(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    } // distanceEuclidian()
    // O(nlogn)/O(k) 52/70 @ 50ms
    public int[][] kClosest1(int[][] points, int k) {
        // make a max heap
        PriorityQueue<int[]> q = new PriorityQueue<int[]>(
                k, (int[] p1, int[] p2) -> (eD(p1)) < eD(p2) ? 1 : -1
                );
        for (int[] p : points) {
            q.offer(p);
            while (q.size() > k) q.poll();
        } // for
        return q.toArray(new int[k][2]);
    } // kClosest()
    // O(nlogn)/O(k) 83/92 @ 33ms
    public int[][] kClosest(int[][] points, int k) {
        // make a max heap
        PriorityQueue<int[]> q = new PriorityQueue<int[]>(
                k, (int[] p1, int[] p2) -> (eD(p1)) < eD(p2) ? 1 : -1
                );
        for (int i = 0; i < k; i++) q.offer(points[i]);
        for (int i = k; i < points.length; i++) {
            if (eD(points[i]) < eD(q.peek())) {
                q.offer(points[i]);
                q.poll();
            } 
        } // for
        return q.toArray(new int[k][2]);
    } // kClosest()
    private double eD(int[] p) {
        return distanceEuclidian(p[0], p[1], 0, 0);
    } // eD


} // class
