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

    // 3. Longest Substring Without Repeating Characters
    // O(n)/O(1) 23/98 @ 35ms
    public int lengthOfLongestSubstring0(String s) {
        //if (s.length() == 0 || s.length() == 1) return s.length();
        Queue<Character> q = new LinkedList<>();
        int max = 0;
        for (int i = 0; i < s.length(); i++) {
            while (q.contains(s.charAt(i))) q.poll(); 
            q.offer(s.charAt(i));
            max = max > q.size() ? max : q.size();
        } // for
        return max;
    } // lengthOfLongstSubstring()
    // 62/ @ 12ms; 86/ @ 7ms
    public int lengthOfLongestSubstring1(String s) {
        if (s.length() == 0 || s.length() == 1) return s.length();
        // using a set and a ptr to effectively make a queue
        HashSet<Character> set = new HashSet<>();
        set.add(s.charAt(0));
        int max = 1;
        int sptr = 0;
        for (int i = 1; i < s.length(); i++) {
            // 'dequeue' while the char i is in the set
            while (set.contains(s.charAt(i))) {
                set.remove(s.charAt(sptr));
                sptr++;
            } // while
            // once char i is not in the set, add it and find max
            set.add(s.charAt(i));
            max = max > set.size() ? max : set.size();
        } // for
        return max;
    } // lengthOfLongstSubstring()
    // based on a solution from the leetcode discussion
    // 97/74 @ 4ms
    public int lengthOfLongestSubstring(String str) {
        int max = 0;
        int s = 0; // start ptr
        int e = 0; // end ptr
        // the val at the index is the char of the string after the most recent appearance of the current char
        int[] cache = new int[256];
        while (e < str.length()) {
            s = s > cache[str.charAt(e)] ? s : cache[str.charAt(e)];
            cache[str.charAt(e)] = e + 1;
            max = max > e - s + 1 ? max : e - s + 1;
            e++;
        } // while
        return max;
    } // lengthOfLongstSubstring()

    // 5. Longest Palindromic Substring
    //
    //
    // O(n^2)/O(n^2) 
    // 37/25 @ 137ms;
    // I could do this with 2n space instead of n^2
    public String longestPalindrome(String s) {
        int[] longestPalindrome = new int[]{0, 1};
        // will store true if from bgn to end indeces in s is a palindrome
        // pldrm[bgn][end]
        boolean[][] dp = new boolean[s.length()][s.length()];
        // fill the diagonal with true since all single chars are palindromes
        for (int i = 0; i < dp.length; i++) {
            dp[i][i] = true;
        } // for i
        // check for all pairs of duplicate chars since they are also palindromes
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i - 1) == s.charAt(i)) {
                dp[i - 1][i] = true;
                // update the longest found substring as it's found
                longestPalindrome[0] = i - 1;
                longestPalindrome[1] = i;
            } // if
        } // for i
        // now travel down the daigonals up/right of dp
        // start on the 3rd diagonal since all palindromes up to length 2 have been found
        for (int diagonal = 2; diagonal < dp.length; diagonal++) { // the diagonal being checked
            for (int i = 0; i < dp.length - diagonal; i++) {
                // first check if [bgn + 1][end - 1] is a palindrome
                if (dp[i + 1][i + diagonal - 1] == true) {
                    // if it is, check if the new bgn and end chars match, if they do, it's a palindrome
                    if (s.charAt(i) == s.charAt(i + diagonal)) {
                        dp[i][i + diagonal] = true;
                        // update the longest found substring as it's found
                        longestPalindrome[0] = i;
                        longestPalindrome[1] = i + diagonal;
                    } // if
                } // if
            } // for i
        } // for diagonal
        return s.substring(longestPalindrome[0], longestPalindrome[1] + 1);
    } // longestPalindrome()
    // O(n^3)/O(1)
    // 15/98 @ 488ms
    public String longestPalindrome(String s) {
        // search for  largest possible palindrome size then shrink it until one is found
        for (int size = s.length(); size > 0; size--) {
            // makes a window that is shifted over one char after each check
            // ptrs to the indeces being checked over
            int bgn = 0;
            int end = size - 1;
            // checks every substring of each size
            while (end < s.length()) {
                if (isPalindrome(s, bgn, end)) {
                    return s.substring(bgn, end + 1);
                } // if
                bgn++;
                end++;
            } // while
        } // for size
        // if no palindrome was found the first char as a string 
        return s.substring(0, 1);
    } // longestPalindrome()
    // checks for a palindrome at the substring bgn, end inclusive
    private boolean isPalindrome(String s, int bgn, int end) {
        while (bgn < end) {
            if (s.charAt(bgn) != s.charAt(end)) {
                return false;
            } // if
            bgn++;
            end--;
        } // while
        // if no mismatches were found
        return true;
    } // isPalindrome()

    // 8. String to Integer (atoi)
    // TODO - this can be done much better
    public int myAtoi(String s) {
        // trim whitespace and determine sign
        s = s.trim();
        int sign = 1;
        if (s.charAt(0) == '-')
            sign = -1;
        if (s.charAt(0) == '-' || s.charAt(0) == '+')
            s = s.substring(1);
        // add the following digits from right to left to a stack
        // the digit on top of the stack will be the 1s place
        // the next digit will be the 10s place and etc.
        Stack<Integer> digits = new Stack<>();
        int sptr = 0;
        int decimal = decimalValue(s.charAt(sptr));
        while (decimal != -1 && sptr < s.length()) {
            digits.push(decimal);
            sptr++;
            decimal = decimalValue(s.charAt(sptr));
        } // while
        int sum = 0;
        int place = 0;
        while (!digits.isEmpty()) {
            int nextValue = digits.pop() * (Math.pow(10, place));
            // check if the value needs to be clamped
            // if it gets to MAX_VALUE and the sign is positive, need to clamp
            // if sign is negative, then the value could still be one greater
            if (nextValue == (Integer.MAX_VALUE - sum) && sign == 1)
                return Integer.MAX_VALUE;
            // if the sum will overflow or if the nextValue has overflown
            else if (nextValue > (Integer.MAX_VALUE - sum) || nextValue < 0)
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            else
                sum += nextValue;
            place++;
        } // while
        return sum;
    } // myAtoi()
    // returns decimal value of char or -1 if not a digit
    private int decimalValue(char c) {
        int d = c - '0';
        // ASCII char values
        if (c < 0 || 9 < c)
            return -1; // not a digit
        return d;
    } // decimalValue()

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


    // 15. 3Sum
    // O(n^2)/O(1)
    // 65/76 @ 43ms
    // modes: 1/2
    public List<List<Integer>> threeSum(int[] num) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        Arrays.sort(nums); // sort in order to use Two Sum II approach
        // go through each value, then use the Two Sum II approach 
        // to find values that make up the remainder
        for (int i = 0; i < nums.length; i++) {
            // skip duplicate values
            if (i != 0 && nums[i] == nums[i - 1]) continue;
            int target = 0 - nums[i];
            int l = i + 1;
            int r = nums.length - 1;
            while (l < r) {
                // skip duplicates at l
                if (l != i + 1 && nums[l] == nums[l - 1]) l++;
                // don't need to check r since ptrs are moved in on sum found
                // now check if the ptrs sum to the target
                else if (nums[l] + nums[r] == target) {
                    List<Integer> triplet = new ArrayList<Integer>(3);
                    triplet.add(nums[i]);
                    triplet.add(nums[l]);
                    triplet.add(nums[r]);
                    res.add(triplet);
                    l++;
                    r--;
                }
                // lastly moving the ptrs based on sum being over or under
                else if (nums[l] + nums[r] < target) l++;
                else r--;
            } // while
        } // for i
        return res;
    } // threeSum()
    // this has become an absolute disaster
    public List<List<Integer>> threeSum0(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Map<Integer, List<List<Integer>>> doubles = new HashMap<>();
        // get each sum of two that is possible
        // mapping each sum to a list of the pairs that sum to it
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                // if no lists of this sum exist, make a key/list map
                if (!doubles.containsKey(nums[i] + nums[j])) {
                    doubles.put(nums[i] + nums[j], new ArrayList<List<Integer>>());
                } // if
                List<Integer> l = new ArrayList<Integer>();
                if (nums[i] < nums[j]) {
                    l.add(nums[i]);
                    l.add(nums[j]);
                } else {
                    l.add(nums[j]); 
                    l.add(nums[i]);
                } // if
                Collections.sort(l);
                if (!alreadyExists(doubles.get(nums[i] + nums[j]), l)) doubles.get(nums[i] + nums[j]).add(l);
            } // for j
        } // for i
        // now iterate through nums looking for -1*nums[i] in the doubles map
        for (int i = 0; i < nums.length; i++) {
            // if a doubles giving that sum exist, create new lists with triples
            if (!doubles.containsKey(-1 * nums[i])) continue;
            // create a triple for each list in the index of doubles
            for (int j = 0; j < doubles.get(-1 * nums[i]).size(); j++) {
                // remove the list so it cannot be used again
                List<Integer> l = doubles.get(-1 * nums[i]).remove(j);
                // insert to maintain sorted order
                int k = 0;
                while (k < l.size()) {
                    if (nums[i] < l.get(k)) break;
                } // while
                l.add(k, nums[i]);
                // check if this triple already exists
                addTriple(res, l);
            } // for j
        } // for i
        return res;
    } // threeSum()
    private boolean alreadyExists(List<List<Integer>> list, List<Integer> newList) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).get(0) == newList.get(0))
                if (list.get(i).get(1) == newList.get(1))
                    return true;
        } // for i
        return false;
    } // alreadyExists()
    private void addTriple(List<List<Integer>> list, List<Integer> newList) {
        for (int i = 0; i < list.size(); i++) {
            if (    list.get(i).get(0) == newList.get(0) &&
                    list.get(i).get(1) == newList.get(1) &&
                    list.get(i).get(2) == newList.get(2)) {
                return;
                    } // if
        } // for
        list.add(newList);
    } // tripleExists()

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

    // 33. Search in Rotated Sorted Array
    // 76/56 @ 1ms;
    // O(logn)/ : do a binary search to find a region without a rotation
    // then do a binary search for the target
    // TODO implement iteratively
    public int search(int[] nums, int target) {
        int l = 0;
        int r = nums.length - 1;
        return search(nums, l, r, target); - 1
    } // search()
    // search recursively for a non rotated region
    // once found, look for the target with normal BS
    private int search(int[] nums, int l, int r, int target) {
        if (nums[l] > nums[r]) { // there is a rotation in bounds
            int m = (l + r) / 2;
            // search the left and right regions
            int leftRegion = search(nums, l, m, target);
            int rightRegion = search(nums, m + 1, r, target);
            // return the non -1 res if it exists
            return leftRegion == -1 ? rightRegion : leftRegion; 
        } else { // there is not a rotation in bounds
            return regularBinarySearch(nums, l, r, target);
        } // if
    } // search()
    private int regularBinarySearch(int[] nums, int l, int r, int target) {
        int m = (l + r) / 2;
        while (l < r) {
            if (nums[m] == target) break;
            if (target < nums[m]) r = m - 1;
            else l = m + 1;
            m = (l + r) / 2;
        } // while
        // if the target is found, return index, else -1
        return nums[m] == target ? m : -1;
    } // regularBinarySearch()

    // 39. Combination Sum
    // backtracing
    // O()/O(1)
    // 61/24 @ 6ms
    // modes: 1/2
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        ArrayList<Integer> l = new ArrayList<Integer>();
        backtrace(candidates, target, 0, res, l);
        return res;
    } // combinationSum();
    private boolean backtrace(int[] candidates, int target, int start, List<List<Integer>> res, ArrayList<Integer> l) {
        int sum = 0;
        for (Integer num : l) sum += num;
        // if sum is <= then no need to look further indicated by returning true
        if (target <= sum) {
            // if ==, add the list of ints to the res
            if (target == sum) res.add((List<Integer>)l.clone());
            return true;
        } // if
        for (int i = start; i < candidates.length && candidates[i] <= target - sum; i++) {
            l.add(candidates[i]);
            if (backtrace(candidates, target, i, res, l)) {
                l.remove(l.size() - 1);
                return false;
            } // if
            l.remove(l.size() - 1);
        } // for i
        return false;
    } // backtrace()
    // could work but I realized I could just use backtracing
    public List<List<Integer>> combinationSum0(int[] candidates, int target) {
        Arrays.sort(candidates);
        // get a list of all combinations that add to each int <= target
        Map<Integer, List<List<Integer>>> map = new HashMap<>();
        // initialize a list for each num <= target
        for (int i = 1; i <= target; i++) {
            List<List<Integer>> l = new ArrayList<List<Integer>>();
            map.put(i, l);
        } // for i
        int rightBound = candidates.length;
        // each candidate <= target gets a list where it's the only value
        // mark where the the candidates <= target stop
        for (int i = 0; i < rightBound; i++) {
            if (candidates[i] > target) {
                rightBound = i;
                break;
            } // if
            List<Integer> l = new ArrayList<Integer>();
            l.add(candidates[i]);
            map.get(candidates[i]).add(l);
        } // for
    } // combinationSum()

    // 46. Permutations
    // O(n!)/O(n!)
    // 13/25 @ 5ms on the tail end of the time normal curve/on the right curve of the bimodal distribution
    // Should try implementing with backtracing next time TODO
    public List<List<Integer>> permute(int[] nums) {
        int elements = nums.length;
        // create the two lists which will be used to build up the result
        List<List<Integer>> la = new ArrayList<List<Integer>>(1);
        List<Integer> starterL = new ArrayList<>(1);
        starterL.add(nums[0]);
        la.add(0, starterL);
        List<List<Integer>> lb;
        // for each element in elements
        for (int element = 1; element < elements; element++) {
            int laLists = la.size();
            int lbLists = laLists * (laLists + 1);
            lb = new ArrayList<List<Integer>>(lbLists);
            int lbListSize = la.get(0).size() + 1;
            // each position in the new list (lb) that element can be
            for (int insertIndex = 0; insertIndex < lbListSize; insertIndex++) {
                // must go through each list in previous list (la)
                for (List<Integer> list : la) {
                    List<Integer> newList = new ArrayList<>(lbListSize);
                    // need to copy elements from la to lb and insert the new element at insertIndex
                    int posList = 0;
                    for (int posNewList = 0; posNewList < lbListSize; posNewList++) {
                        if (posNewList == insertIndex) {
                            newList.add(posNewList, nums[element]);
                        } else {
                            newList.add(posNewList, list.get(posList));
                            posList++;
                        } // if
                    } // for pesNewList
                    lb.add(newList);
                } // for each list in la
            } // for posB
            // reassign lb to la to repeat the loop
            la = lb;
        } // for element
        return la;
    } // permute()

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
        } // for
        return max;
    } // maxSubArray()

    // 54. Spiral Matrix
    // O(n)/O(1)
    // 100/95 @ 0ms
    public List<Integer> spiralOrder(int[][] matrix) {
        // create walls which the 'snake' cannot pass
        // these walls will close in as the snake gathers numbers
        int redge = matrix[0].length;
        int bedge = matrix.length;
        int ledge = -1;
        int tedge = -1;
        int[] pos = new int[]{0,-1}; // [row, col] position, head of the snake
        // start it to the left of the top left cell
        List<Integer> res = new ArrayList<>(redge * bedge);
        int[][] dirs = new int[][]{{0, 1},{1, 0},{0, -1},{-1, 0}};
        //                         right, down,  left,   up
        while (!isTrapped(pos, redge, bedge, ledge, tedge)) {
            // traveling along a wall moves that wall inward
            // ex. moving right (across the top wall) means the top wall must move inward
            traverse(res, matrix, pos, dirs[0], redge, bedge, ledge, tedge); // traverse right
            tedge++;
            traverse(res, matrix, pos, dirs[1], redge, bedge, ledge, tedge); // traverse down
            redge--;
            traverse(res, matrix, pos, dirs[2], redge, bedge, ledge, tedge); // traverse left
            bedge--;
            traverse(res, matrix, pos, dirs[3], redge, bedge, ledge, tedge); // traverse up
            ledge++;
        } // while
        return res;
    } // spiralOrder()
    private void traverse(List<Integer> res, int[][] matrix, int[] pos, int[] dir, int redge, int bedge, int ledge, int tedge) {
        pos[0] += dir[0];
        pos[1] += dir[1];
        while (isReachable(pos, redge, bedge, ledge, tedge)) {
            res.add(matrix[pos[0]][pos[1]]);
            pos[0] += dir[0];
            pos[1] += dir[1];
        } // while
        // don't overshoot
        pos[0] -= dir[0];
        pos[1] -= dir[1];
    } // traverse()
    private boolean isReachable(int[] pos, int redge, int bedge, int ledge, int tedge) {
        return (ledge < pos[1] && pos[1] < redge &&
                tedge < pos[0] && pos[0] < bedge);
    } // isReachable()
    private boolean isTrapped(int[] pos, int redge, int bedge, int ledge, int tedge) {
        int[] dirs = new int[]{0, 1, 0, -1, 0};
        // check if any adjacent cells are reachable
        for (int i = 1; i < dirs.length; i++) {
            pos[0] += dirs[i];
            pos[1] += dirs[i - 1];
            if (isReachable(pos, redge, bedge, ledge, tedge)) {
                pos[0] -= dirs[i];
                pos[1] -= dirs[i - 1];
                return false;
            } // if
            pos[0] -= dirs[i];
            pos[1] -= dirs[i - 1];
        } // for i
        // if none were found, return false
        return true;
    } // isTrapped()

    public List<Integer> spiralOrder(int[][] matrix) {
        // create walls which the 'snake' cannot pass
        // these walls will close in as the snake gathers numbers
        int redge = matrix[0].length;
        int bedge = matrix.length;
        int ledge = -1;
        int tedge = -1;
        int[] pos = new int[]{0,-1}; // [row, col] position, head of the snake
        List<Integer> res = new ArrayList<>(redge * bedge);
        while (!trapped(pos, redge, bedge, ledge, tedge)) {
            // traveling along a wall moves that wall inward
            // ex. moving right (across the top wall) means the top wall must move inward
            traverse(res, matrix, pos, 1, redge, false); // traverse right
            tedge++;
            traverse(res, matrix, pos, 1, bedge, true); // traverse down
            redge--;
            traverse(res, matrix, pos, -1, ledge, false); // traverse left
            bedge--;
            traverse(res, matrix, pos, -1, tedge, true); // traverse up
            ledge++;
        } // while
        return res;
    } // spiralOrder()
    // if there is no open path
    private boolean trapped(int[] pos, int redge, int bedge, int ledge, int tedge) {
        // check if there is a direction which can be taken
        return (pos[1] + 1 >= redge && pos[0] + 1 >= bedge &&
                pos[1] - 1 <= ledge && pos[0] - 1 <= tedge);
    } // trapped()
    private void traverse(List<Integer> res, int[][] matrix, int[] pos, int step, int edge, boolean verticalDir) {
        if (verticalDir) {
            int nextPos = pos[0] + step;
            // may use the same boolean expression to check less than if it's multiplied by step
            // because a negative step (meaning it's traversing to a cell with a lower number) 
            // means it really should be greater than
            // the wall/edge it's heading towards. Similar to an absolute value.
            while (step * nextPos < step * edge) {
                res.add(matrix[nextPos][pos[1]]);
                nextPos += step;
            } // while
            pos[0] = nextPos - step;
        } else {
            int nextPos = pos[1] + step;
            while (step * nextPos < step * edge) {
                res.add(matrix[pos[0]][nextPos]);
                nextPos += step;
            } // while
            pos[1] = nextPos - step;
        } // if
    } // traverse()

    // 56. Merge K Intervals
    // O(nlogn)/O(n)
    // 83/44 @ 11ms
    // modes: 2/2
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (int[] a, int[] b) -> a[0] - b[0]);
        int cur = 0;
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] <= intervals[cur][1]) {
                intervals[cur][1] = Math.max(intervals[cur][1], intervals[i][1]);
            } else { // ivls not overlapping
                cur++;
                intervals[cur] = intervals[i];
            } // if
        } // for
        int[][] res = new int[cur + 1][];
        for (int c = 0; c < res.length; c++) res[c] = intervals[c];
        return res;
    } // merge()
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (int[] a, int[] b) -> a[0] < b[0] ? 1 : -1);
        List<Integer[]> res = new ArrayList<Integer[]>();
        addIntToInteger(res, intervals[0]);
        for (int i = 1; i < intervals.length; i++) {
            // if an ivl overlaps with the previous, combine them
            if (intervals[i][0] <= res.get(res.size() - 1)[1])
                res.get(res.size() - 1)[1] = Math.max(intervals[i][1], res.get(res.size() - 1)[1]);
            // else add the ivl to the list
            else addIntToInteger(intervals[i]);
        } // for i
        int[][] resArr = new int[res.size()][];
        res.toArray(resArr);
        return resArr;
    } // merge()
    private void addIntToInteger(List<Integer[]> l, int[] arr) {
        Integer[] newarr = new Integer[2];
        newarr[0] = arr[0];
        newarr[1] = arr[1];
        l.add(newarr);
    } // addIntToInteger()
    // O(n)/O(n) doesn't work
    public int[][] merge(int[][] intervals) {
        // create an int arr which holds flags for if a time is a start, end, or interior
        // 000-1111-1000 0 not in ivl, -1 a start/end of an ivl, and 1 an interior
        int end = 0; // the final end time in the intervals which is arr size
        for (int[] ivl : intervals) end = Math.max(end, ivl[1]);
        int[] times = new int[end + 1];
        // give each time the proper flag
        for (int[] ivl : intervals) {
            if (times[ivl[0]] != 1) times[ivl[0]] = -1;
            if (times[ivl[1]] != 1) times[ivl[1]] = -1;
            for (int t = ivl[0] + 1; t < ivl[1]; t++) times[t] = 1;
        } // for ivl
        // if a -1 is surrounded by 1, it is an end that is now contained
        for (int t = 2; t < times.length - 2; t++) {
            if (times[t] == -1 && times[t - 1] == 1 && times[t + 1] == 1)
                times[t] = 1;
        } // for
        // find how many ivls there are
        boolean inIvl = false;
        int ivlCt = 0;
        for (int t = 0; t < times.length; t++) {
            if (times[t] == -1) {
                if (inIvl == false)
                    ivlCt++;
                inIvl = !inIvl;
            } // if
        } // for t
        int[][] res = new int[ivlCt][2];
        inIvl = false;
        int curIvl = 0;
        for (int t = 0; t < times.length; t++) {
            if (times[t] == -1) {
                if (inIvl == false) {
                    res[curIvl][0] = t;
                } else { // inIvl == true
                    res[curIvl][1] = t;
                    curIvl++;
                } // if
                inIvl = !inIvl;
            }
        } // for t
        return res;
    } // merge()
    // O(n)/O(n)
    public int[][] merge(int[][] intervals) {
        //Arrays.sort(intervals, (int[] a, int[] b) -> a[0] < b[0]);
        // create a boolean list that stores t or f for each unit of time
        List<Boolean> times = new ArrayList<Boolean>();
        for (int[] ivl : intervals) {
            while (times.size() <= ivl[1]) times.add(false);
            for (int t = ivl[0]; t <= ivl[1]; t++) times.set(t, true);
        } // for ivl
        int ivlCt = 0;
        boolean inIvl = false;
        for (int i = 0; i < times.size(); i++) {
            if (inIvl == false && times.get(i) == true) {
                inIvl = true;
                ivlCt++;
            } else if (times.get(i) == false) {
                inIvl = false;
            } // if
        } // for i
        int[][] res = new int[ivlCt][2];
        int curIvl = 0;
        inIvl = false;
        for (int i = 0; i < times.size(); i++) {
            if (inIvl == false && times.get(i) == true) {
                inIvl = true;
                res[curIvl][0] = i;
            } else if (inIvl == true && times.get(i) == false) {
                inIvl = false;
                res[curIvl][1] = i - 1;
                curIvl++;
            } // if
        } // for i
        // if loop ends inside an ivl, need to set the final ivl's end
        if (inIvl == true) res[curIvl][1] = times.size() - 1;
        return res;
    } // merge()

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

    // 62. Unique Paths
    // O(mn)/O(n)
    // 56/81 @ 1ms
    // DP
    public int uniquePaths(int m, int n) {
        // the number of paths is the number of paths rightward plus
        // the numbor of paths downward
        // only need to know the most recent row's values so only need a 1D array
        int[] dp = new int[n];
        Arrays.fill(dp, 1); // the bottom row only has a single path
        for (int row = m - 2; row >= 0; row--) { // already accounted for the bottom row
            for (int col = n - 2; col >= 0; col--) { // the last index will always be 1
                dp[col] = dp[col] + dp[col + 1]; // rightward + downward
            } // for col
        } // while row
        return dp[0];
    } // uniquePaths()
    // O(n)/O(1)
    // Mathematically
    public int uniquePaths(int m, int n) {
        int movesRequired = m - 1 + n - 1;
        // movesRequired pick m or n
        return nChooseK(movesRequired, m);
    } // uniquePaths()

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

    // 75. Sort Colors
    // O(2n)/O(1)
    // 100/72
    // Should generalize to k colors and be no more than O(k*n)
    public void sortColors(int[] nums) {
        // sort the first two colors. The third must be sorted afterward
        int[] colors = new int[]{0, 1};
        int colorPointer = 0; // where the next target color should be swapped to
        // ie the index of the first non target color in nums
        int temp;
        for (int color : colors) {
            for (int i = colorPointer; i < nums.length; i++) {
                if (nums[i] == color) {
                    // swap with colorPointer if they are not the same index
                    if (i != colorPointer) {
                        temp = nums[colorPointer];
                        nums[colorPointer] = nums[i];
                        nums[i] = temp;
                    } // if
                    colorPointer++;
                }
            } // for i
        } // for color
    } // sortColors()
    // sigle pass solution inspired by leetcode discussion post
    public void sortColors(int[] nums) {
        int red = 0;
        int white = 0;
        int blue = nums.length - 1;
        while (white <= blue) {
            if (nums[white] == 0) { // red
                nums[white] = nums[red];
                nums[red] = 0;
                white++;
                red++;
            } else if (nums[white] == 1) { // white
                white++;
            } else { // blue
                nums[white] = nums[blue];
                nums[blue] = 2;
                blue--;
            } // if
        } // while
    } // sortColors()

    // 78. Subsets
    // O(n!)/O(n!)
    // 41/69 @ 2ms
    public List<List<Integer>> subsets(int[] nums) {
        List<Integer> lst = new ArrayList<>(nums.length);
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        backtrack(nums, 0, res, lst);
        return res;
    } // subsets()
    private void backtrack(int[] nums, int cur, List<List<Integer>> res, List<Integer> lst) {
        if (cur >= nums.length) {
            res.add(cloneList(lst)); // clone lst into res
            return;
        } // if
        lst.add(nums[cur]); // add cur num
        backtrack(nums, cur + 1, res, lst); // backtrack with cur num
        lst.remove(lst.size() - 1); // remove cur num
        backtrack(nums, cur + 1, res, lst); // backtrack without cur num
    } // backtrack()
    private <E> List<E> cloneList(List<E> lst) {
        List<E> cln = new ArrayList<E>(lst.size());
        for (E e : lst) {
            cln.add(e);
        } // for e
        return cln;
    } // cloneList()

    // 98. Validate Binary Search Tree
    // O(n)/ : 100/ @ 0ms
    // The key concept was found but it can be done with much less code. TODO
    public boolean isValidBST(TreeNode root) {
        int leftTreeMax = Integer.MAX_VALUE;
        int rightTreeMin = Integer.MIN_VALUE;
        return isValidBSTR(root, leftTreeMax, rightTreeMin);
    } // isValidBST()
    private boolean isValidBSTR(TreeNode root, int leftTreeMax, int rightTreeMin) {
        if (root == null) return true;
        boolean leftIsBST;
        boolean rightIsBST;
        if (root.left != null) {
            if (    root.left.val >= root.val ||
                    root.left.val < rightTreeMin ||
                    root.left.val > leftTreeMax)
                return false;
            int newLeftTreeMax = Math.min(leftTreeMax, root.val - 1);
            if (isValidBSTR(root.left, newLeftTreeMax, rightTreeMin) == false)
                return false;
        } // left if 
        if (root.right != null) {
            if (    root.right.val <= root.val ||
                    root.right.val < rightTreeMin ||
                    root.right.val > leftTreeMax)
                return false;
            int newRightTreeMin = Math.max(rightTreeMin, root.val + 1);
            if (isValidBSTR(root.right, leftTreeMax, newRightTreeMin) == false)
                return false;
        } // right if 
        return true;
    } // isValidBSTR()

    // 102. Binary Tree Level Order Traversal
    //
    public List<List<Integer>> levelOrder01(TreeNode root) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        Queue<TreeNode> q = new LinkedList<TreeNode>(); // for BFS
        q.add(null); // the initial elements
        q.add(root);
        while (!q.isEmpty()) {
            // do the BFS
            List<Integer> l = new ArrayList<Integer>();
            TreeNode a = q.poll();
            if (a != null) {
                q.offer(a.left);
                q.offer(a.right);
                l.add(a.val);
            } // if
            TreeNode b = q.poll();
            if (b != null) {
                q.offer(b.left);
                q.offer(b.right);
                l.add(b.val);
            } // if
            if (l.size() > 0) res.add(l);
        } // while
        return res;
    } // levelOrder()
    public List<List<Integer>> levelOrder1(TreeNode root) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        if (root == null) return res;
        Queue<TreeNode> q = new LinkedList<TreeNode>(); // for BFS
        boolean hasNextLevel = false; // want to know if there is a next level (ie a non-null below)
        int level = 0; // level -> power of 2
        int ct = 0;
        int curLevelNulls = 0;
        int nextLevelNulls = 0;
        List<Integer> l = new ArrayList<>();
        q.offer(root); // the initial node
        while (!q.isEmpty()) {
            // process the next element
            TreeNode node = q.poll();
            if (node == null) {
                nextLevelNulls += 2;
            } else {
                l.add(node.val);
                q.offer(node.left);
                q.offer(node.right);
                if (hasNextLevel == false && (node.left != null || node.right != null))
                    hasNextLevel = true;
            } // if
            ct++;
            System.out.printf("level: %d, ct: %d, nLN: %d\n", level, ct, nextLevelNulls);
            
            // check if the next level has been reached
            if (isNthPowerOfTwo(ct + curLevelNulls, level)) { // we have reached the end of a level
                res.add(l);
                if (!hasNextLevel) break; // if there is no next level, work is done
                hasNextLevel = false;
                level++;
                ct = 0;
                curLevelNulls = nextLevelNulls; // next level is now current level
                nextLevelNulls *= 2; // because each null will give two nulls in the next level
                l = new ArrayList<>(); // new list for the level
            } // if
        } // while
        return res;
    } // levelOrder()
    private boolean isNthPowerOfTwo(int ct, int level) {
        // level corresponds to which power of two is next
        if ((ct & (1 << level)) > 0) return true;
        return false;
        /*
        //if (n < 0) return false;
        while (n > 1) {
            if ((n % 2) != 0) return false;
            n /= 2;
        } // while
        return true;
        */ 
    } // isPowerOfTwo()
    // O(n)/O(n) 93/48 @ 1ms
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        if (root == null) return res; // edge case
        Queue<TreeNode> q = new LinkedList<TreeNode>(); // for BFS
        int nodesLeft = 1; // how many nodes to process in this level
        int nextLevelNodes = 0; // how many nodes to process in the next level
        TreeNode node; // working var for node
        TreeNode[] lr = new TreeNode[2]; // working var for children
        List<Integer> l = new ArrayList<>();
        q.offer(root); // initial member
        while (nodesLeft > 0) {
            // process node
            node = q.poll();
            l.add(node.val);
            lr[0] = node.left;
            lr[1] = node.right;
            // check the children
            for (TreeNode tn : lr) {
                if (tn != null) {
                    q.offer(tn);
                    nextLevelNodes++;
                } // if
            } // for
            nodesLeft--;

            if (nodesLeft == 0) { // end of a level reached
                // set vars as needed
                nodesLeft = nextLevelNodes;
                nextLevelNodes = 0;
                res.add(l);
                l = new ArrayList<>();
            } // if
        } // while
        return res;
    } // levelOrder()

    // 104. Maximum Depth of Binary Tree
    // 100/93
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    } // maxDepth()

    // 105. Construct Binary Tree from Preorder and Inorder Traversal
    //
    // preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
    //                      3
    //                  9       20
    //                        15  7
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        TreeNode root = new TreeNode();
        buildTree(preorder, inorder, root, 0, preorder.length - 1);
        return root;
    } // buildTree()
    // recursively builds the tree
    private void buildTree(int[] preorder, int[] inorder, TreeNode root, int start, int end) {
        // the first val in preorder is the root so everything to its left in inorder is the left subtree
        root.val = preorder[start];
        // find the root val index in inorder
        int rootInInorder = -1;
        for (int i = start; i < end; i++)
            if (inorder[i] == preorder[start])
                rootInInorder = i;
        if (rootInInOrder != start) { // there are nodes in the left subtree
            TreeNode leftRoot = new TreeNode();
            buildTree(preorder, inorder, leftRoot, 
        }
    } // buildTree()

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
    // 99/98
    public boolean isPalindrome1(String s) {
        s = s.toLowerCase();
        int b = 0; // beginning pointer
        int e = s.length() - 1; // ending pointer
        while (b <= e) {
            // find the next letter or digit
            while (b <= e && !isLetterOrDigit(s.charAt(b)))
                b++;
            while (e >= b && !isLetterOrDigit(s.charAt(e)))
                e--;
            // ensure they are the same
            if (s.charAt(b) != s.charAt(e))
                return false;
            b++;
            e--;
        } // while
        // if no mimatches were found, it is an anagram
        return true;
    } // isPalindrome()

    // 78/70 @ 5 ms
    public boolean isPalindrome0(String s) {
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

    // 133. Clone Graph
    // O(n)/O(n) 100/98 @ 25ms
    public Node cloneGraph(Node node) {
        if (node == null) return node;
        Set<Integer> set = new HashSet<>();
        Map<Integer, Node> map = new HashMap<>(); // map for the cloned nodes
        return cloneGraphR(node, map);
    } // cloneGraph()
    public Node cloneGraphR(Node node, Map<Integer, Node> map) {
        if (map.containsKey(node.val)) return map.get(node.val); // if already cloned, return the node from map
        Node clone = new Node(node.val); // clone of the node itself
        map.put(node.val, clone); // hash the node so we know it's being cloned
        for (Node neighbor : node.neighbors)
            clone.neighbors.add(cloneGraphR(neighbor, map));
        return clone;
    } // cloneGraphR()

    // 139. Word Break
    // O(n^2)/O(n)
    // 84/98 @ 5ms
    public boolean wordBreak(String s, List<String> wordDict) {
        // will be going through s starting at the end to see if a words can fill it
        boolean[] dp = new boolean[s.length() + 1];
        dp[s.length()] = true; // if s can be filled the its length, dp should be true
        for (int i = s.length() - 1; i >= 0; i--) {
            for (String word : wordDict) {
                if (fills(s, i, word)) {
                    dp[i] = dp[i] || dp[i + word.length()];
                } // if fills
                if (dp[i] == true) {
                    continue;
                } // if
            } // for word
        } // for i
        return dp[0];
    } // wordBreak()
    // checks if a word fills string s starting at char b to b + length of word
    private boolean fills(String s, int b, String word) {
        // check that each char in word matches each char in s starting at b
        for (int i = 0; i < word.length(); i++) {
            if (b >= s.length()) { // i.e. if word is too long
                return false;
            } else if (word.charAt(i) != s.charAt(b)) { // if mismatch
                return false;
            } // if
            b++;
        } // for 
        // if no mismatches and not too long, it does fill
        return true;
    } // fills()
    public boolean wordBreak(String s, List<String> wordDict) {
        /*
        // remove any words that are not in s
        for (int w = 0; w < wordDict.size(); w++) {
        if (!s.contains(wordDict.get(w))) {
        wordDict.remove(w);
        w--; // to not skip a word
        } // if
        } // for
        */
        // ensure each letter in s can be found in wordDict
        boolean[] lettersInS = new boolean[26];
        for (int i = 0; i < s.length(); i++)
            lettersInS[s.charAt(i) - 'a'] = true;
        boolean[] lettersInWordDict = new boolean[26];
        for (String word : wordDict)
            for (int i = 0; i < word.length(); i++)
                lettersInWordDict[word.charAt(i) - 'a'] = true;
        for (int i = 0; i < lettersInS.length; i++)
            if (lettersInS[i] == true && lettersInWordDict[i] != true)
                return false;
        // add each word from wordDict to a list mapped to the letter it starts with
        Map<Character, List<String>> words = new HashMap<>();
        for (String word : wordDict) {
            if (!words.containsKey(word.charAt(0)))
                words.put(word.charAt(0), new ArrayList<String>());
            words.get(word.charAt(0)).add(word);
        } // for word
        // sort the strings in order of descending height for potentially less computation
        for (int i = 0; i < 26; i++)
            if (words.containsKey(i + 'a'))
                words.get(i + 'a').sort((String a, String b) -> Integer.compare(b.length(), a.length()));
        return backTrace(s, 0, words);
    } // wordBreak()
    private boolean backTrace(String s, int start, Map<Character, List<String>> words) {
        char startingChar = s.charAt(start);
        // make sure there are words that start with startingChar
        if (!words.containsKey(startingChar)) return false;
        for (String word : words.get(startingChar)) {
            // see if/where the current word fills to
            int fillsToRes = fillsTo(s, start, word);
            // if it fills
            if (fillsToRes != -1) {
                // if it fills to the end of the word, the answer is true
                if (fillsToRes == s.length() - 1)
                    return true;
                // else see if the next portion of s can be filled
                boolean btres = backTrace(s, fillsToRes + 1, words);
                if (btres == true)
                    return true;
            } // if
        } // for word
        // if could not fill, return false
        return false;
    } // backTrace()
    // if w is found in s starting at start, returns index it fills to, else -1
    private int fillsTo(String s, int start, String w) {
        // can skip the first index since this only gets called with
        // strings that begin with the same char
        int si = start;
        for (int wi = 1; wi < w.length(); wi++) {
            si++;
            if (si >= s.length() || s.charAt(si) != w.charAt(wi))
                return -1;
        } // for i
        return si;
    } // fillsTo()

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

    // 150. Evaluate Reverse Polish Notation
    //
    public int evalRPN0(String[] tokens) {
        int i = tokens.length;
        while (tokens[i] == null) i--;
        String operator = tokens[i];
        tokens[i] == null;
        if (operator.isDigit()) return Integer.parseInt(operator);
        int a = evalRPN(tokens);
        int b = evalRPN(tokens);
        switch (operator) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            default: return a / b; // case "/":
        } // switch
    } // evalRPN()
    public int evalRPN1(String[] tokens) {
        return evalRPNR(tokens, tokens.length - 1);
    } // evalRPN()
    public int evalRPNR(String[] tokens, int nextOperand) {
        String operator = tokens[nextOperand];
        if (Character.isDigit(operator.charAt(0))) return Integer.parseInt(operator);
        int a = evalRPNR(tokens, nextOperand - 2);
        int b = evalRPNR(tokens, nextOperand - 1);
        switch (operator) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            default: return a / b; // case "/":
        } // switch
    } // evalRPN(R)
    // 6/82 @ 43ms
    public int evalRPN2(String[] tokens) {
        return evalRPNR(tokens, tokens.length - 1);
    } // evalRPN()
    public int evalRPNR(String[] tokens, int nextOperand) {
        while (tokens[nextOperand] == null) nextOperand--; // it will be the first non null token
        String operator = tokens[nextOperand];
        tokens[nextOperand] = null; // null out the token once it's been pulled
        // if digit, return it
        if (Character.isDigit(operator.charAt(operator.length() - 1)))
            return Integer.parseInt(operator);
        // else it is an operator and the next operands need to be found
        int b = evalRPNR(tokens, nextOperand - 1);
        int a = evalRPNR(tokens, nextOperand - 2);
        switch (operator) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/": return a / b;
            default:  return a * b;
        } // switch
    } // evalRPN()
    // O(n)/O(n) 68/39 @ 8ms; 91/89 @ 6ms
    public int evalRPN2(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        for (String token : tokens) {
            // if a digit, add it to the stack
            if (Character.isDigit(token.charAt(token.length() - 1))) {
                stack.push(Integer.parseInt(token));
                continue;
            } // if
            // else it is an operand so pop the last two digits and operate
            int b = stack.pop();
            int a = stack.pop();
            int res;
            switch (token) {
                case "+":
                    res = a + b;
                    break;
                case "-":
                    res = a - b;
                    break;
                case "*":
                    res = a * b;
                    break;
                case "/":
                    res = a / b;
                    break;
                default:
                    res = a + b;
            } // switch
            stack.push(res); // put the res in the stack
        } // for
        return stack.peek();
    } // evalRPN()

    // 155. Min Stack
    // O(1) time for each method
    // 54/ @ 8ms; 81/ @ 6ms
    // Should have created my own stack instead of using the built in class.
    // would have a next instance var in MinStackNode.
    class MinStack {

        private Stack<MinStackNode> stack;

        public MinStack() {
            stack = new Stack<MinStackNode>();
        }

        public void push(int val) {
            // only pull the current min if the stack is not empty
            int min = stack.isEmpty() ? val : getMin();
            MinStackNode newNode = new MinStackNode(val, min);
            stack.push(newNode);
        }

        public void pop() {
            stack.pop();
        }

        public int top() {
            return stack.peek().val;
        }

        public int getMin() {
            return stack.peek().minVal;
        }
    } // class MinStack
    class MinStackNode {
        int val;
        int minVal; // will be the min val at or below this node in the stack

        public MinStackNode(int val, int minVal) {
            this.val = val;
            // min is the lesser of the previous min or the new val
            this.minVal = Math.min(minVal, val);
        } // MinStackNode()
    } // class MinStackNode

    // 167. Two Sum II - Input Array Is Sorted
    // O(n)/O(1)
    // 81/81 @ 2ms
    public int[] twoSum(int[] numbers, int target) {
        int[] ptrs = new int[]{1, numbers.length};
        int sum = numbers[ptrs[0] - 1] + numbers[ptrs[1] - 1];
        // two pointers. if the sum is too great, move the left ptr inward
        // else if the sum is too low, move the right pointer inward
        while (sum != target) {
            if (sum < target) ptrs[0]++;
            else ptrs[1]--;
            sum = numbers[ptrs[0] - 1] + numbers[ptrs[1] - 1];
        } // while
        return ptrs;
    } // twoSum()

    // 169. Majority Element
    //
    public int majorityElement0(int[] rums) {
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

    // 199. Binary Tree Right Side View
    // O(n)/O(n)
    // 95/90 @ 1ms; 49/15 @ 2ms 
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> res = new LinkedList<>();
        // BFS
        Deque<TreeNode> deq = new LinkedList<>();
        if (root != null) {
            deq.addLast(root);
        } // if
        while (!deq.isEmpty()) {
            // BFS one layer at a time
            int size = deq.size();
            // the last node in the deque at each layer is the one seen from the right
            res.add(deq.getLast().val); 
            while (size > 0) {
                TreeNode tnd = deq.removeFirst();
                size--;
                if (tnd.left != null) {
                    deq.addLast(tnd.left);
                } // if
                if (tnd.right != null) {
                    deq.addLast(tnd.right);
                } // if
            } // while
        } // while
        return res;
    } // rightSideView()

    // 200. Number of Islands
    // O(n)/O(n) stack memory O(1) write memory
    // 75/100 @4ms; 38/75 @ 6ms;
    public int numIslands(char[][] grid) {
        int islands = 0;
        // go through each section in the grid
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                // if land is found, increment islands and delete the island
                // if deleted, the island cannot be double counted
                if (grid[r][c] == '1') {
                    islands++;
                    deleteIsland(grid, r, c);
                } // if
            } // for c
        } // for r
        return islands;
    } // numIslands()
    // recursively deletes an entire island (all surrounding 1s)
    private void deleteIsland(char[][] grid, int r, int c) {
        // bounds check
        if (r < 0 || r >= grid.length) return;
        if (c < 0 || c >= grid[r].length) return;
        if (grid[r][c] == '0') return; // if not land
        grid[r][c] = '0'; // delete the land
        // delete all of the surrounding land
        int[] dir = {0, 1, 0, -1, 0};
        for (int i = 1; i < dir.length; i++)
            deleteIsland(grid, r + dir[i - 1], c + dir[i]);
    } // deleteIsland();

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

    // 207. Course Schedule
    //
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // compile the prereqs into a set for each course
        //Set<Integer>[] prereqs = new HashSet<>[numCourses];
        List<Set<Integer>> prereqs = new ArrayList<>(numCourses);
        for (int[] prereq : prerequisites) {
            prereqs.get(prereq[0]).add(prereq[1]);
        } // for
        //Set<Integer> parents = new HashSet<>();
        // if a course is not in here, its subprereqs have not been found
        Set<Integer> subPrereqsAdded = new HashSet<>();
        for (int course = 0; course < numCourses; course++) {
            // prereqs[course].add(addSubReqs(course, prereqs, subPrereqsAdded));
            addSubReqs(course, prereqs, subPrereqsAdded);
        } // for
        for (int course = 0; course < numCourses; course++) {
            if (prereqs.get(course).contains(course)) return false;
        } // for
        return true;
    } // canFinish()
    private Set<Integer> addSubReqs(int course, List<Set<Integer>> prereqs, Set<Integer> subPrereqsAdded) {
        if (subPrereqsAdded.add(course) == false) return prereqs.get(course);
        for (int prereq : prereqs.get(course)) {
            prereqs.get(course).add(addSubReqs(prereq, prereqs, subPrereqsAdded));
        } // for
        return prereqs.get(course);
        /*
        Set<Integer> subreqs = new HashSet<>();
        for (int prereq : prereqs[course]) {
            subreqs.addAll(addSubReqs(prereq, prereqs, subPrereqsAddded));
        } // for prereq
        */
    } // addSubReqs()
    // O(n^2)/O(n^2) 5/5; awful runtime and memory usage.
    // TODO
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // create and initialize list of sets 
        List<Set<Integer>> prereqs = new ArrayList<Set<Integer>>(numCourses);
        for (int i = 0; i < numCourses; i++)
            prereqs.add(new HashSet<Integer>());
        // compile the prereqs
        for (int[] prereq : prerequisites)
            prereqs.get(prereq[0]).add(prereq[1]);
        // the key is that each prereq's prereq (sub prereq) is also a prereq.
        // so DFS to add all of the sub prereqs to the prereqs set
        boolean[] visited = new boolean[numCourses];
        // for (int course = 0; course < numCourses; course++)
            // prereqs.get(course).add(getSubPrereqs(course, prereqs, visited));
        for (int course = 0; course < numCourses; course++) {
            getSubPrereqs(course, prereqs, visited);
            if (prereqs.get(course).contains(course)) return false;
            /*
            for (Integer subprereq : getSubPrereqs(course, prereqs, visited)) {
                if (subprereq == course) return false; // if the course is a sub prereq, cycle
                else prereqs.get(course).add(subprereq);
            } // for subprereq
            */
        } // for course
        return true;
    } // canFinish()
    // a DFS to get all subprereqs
    private Set<Integer> getSubPrereqs(int course, List<Set<Integer>> prereqs, boolean[] visited) {
        if (visited[course]) return prereqs.get(course);
        visited[course] = true;
        Set<Integer> subprereqs = new HashSet<Integer>();
        // compile the subprereqs
        for (Integer prereq : prereqs.get(course))
            subprereqs.addAll(getSubPrereqs(prereq, prereqs, visited));
        prereqs.get(course).addAll(subprereqs);
        return prereqs.get(course);
    } // getSubPrereqs()

    // 208. Implement Trie
    class Trie0 {
    // O(n)/O(n)
    // overly complex and should make nodes instead of sub Tries
    // only failed the large testcase and the output is too unruly to see why
        private boolean anEnd;
        Map<Character, Trie> prefixes;

        public Trie() {
            prefixes = new HashMap<Character, Trie>();
            anEnd = false;
        }

        public void insert(String word) {
            insert(word, 0);
        }
        // return true on the first out of bounds index
        private boolean insert(String word, int index) {
            if (index >= word.length())
                return true;
            char cur = word.charAt(index);
            // if prefixes doesn't have the next char, make a new Trie for it
            if (!prefixes.containsKey(cur))
                prefixes.put(cur, new Trie());
            // add the next char, if a false, mark this Trie as an and
            if (prefixes.get(cur).insert(word, index + 1))
                anEnd = true;
            return false;
        } // insert

        public boolean search(String word) {
            return find(word, true);
        }

        public boolean startsWith(String prefix) {
            return find(prefix, false);
        }

        public boolean find(String s, boolean fullWord) {
            Trie curTrie = this;
            char curChar;
            for (int ch = 0; ch < s.length(); ch++) {
                curChar = s.charAt(ch);
                // if the current char is not in prefixes
                if (!curTrie.prefixes.containsKey(curChar))
                    return false;
                // if looking for full word:
                // if it's the end of the word, check if the Trie is anEnd
                if (fullWord && ch == s.length() - 1 && curTrie.isAnEnd())
                    return true;
                // go to the next Trie
                curTrie = curTrie.prefixes.get(curChar);
            } // for ch
            // if execution made it here, no mismatch chars were found
            // in this case, full word was not found but prefix was so
            // fullWord can just be flipped and returned
            return !fullWord;
        }

        private boolean isAnEnd() {
            return anEnd;
        } // isAnEnd()
    } // class Trie0
    // O(n)/O(n): 10/50 @ 107ms; 54/50 @ 57ms;
    // TODO implement with hashmaps instead of arrays?
    class Trie {
        class TrieNode {
            private TrieNode[] children;
            private boolean end;

            TrieNode() {
                 children = new TrieNode['z' - 'a' + 1];
                 end = false;
            } // TrieNode()

            TrieNode getChild(char ch) {
                return children[ch - 'a'];
            } // getChild()

            boolean hasChild(char ch) {
                return getChild(ch) != null;
            } // hasChild()

            void makeChild(char ch) {
                if (hasChild(ch)) return;
                children[ch - 'a'] = new TrieNode();
            } // makeChild()

            boolean isEnd() {
                return end;
            } // isEnd()

            void setEnd() {
                end = true;
            } // setEnd()
        } // class TrieNode
        
        private TrieNode root;

        public Trie() {
            root = new TrieNode();
        } //Trie()

        public void insert(String word) {
            TrieNode cur = root;
            char ch;
            for (int i = 0; i < word.length(); i++) {
                ch = word.charAt(i);
                cur.makeChild(ch);
                cur = cur.getChild(ch);
            } // for
            cur.setEnd();
        } // insert()

        public boolean search(String word) {
            return find(word, true);
        } // search()

        public boolean startsWith(String prefix) {
            return find(prefix, false);
        } // startsWith()

        private boolean find(String s, boolean lookingForWord) {
            TrieNode cur = root;
            char ch;
            for (int i = 0; i < s.length(); i++) {
                ch = s.charAt(i);
                if (!cur.hasChild(ch)) return false;
                cur = cur.getChild(ch);
            } // for
            // the loop completes if every letter was found
            // either not looking for a word or check if it's an end
            return (!lookingForWord || cur.isEnd());
        } // find()
    } // class Trie

    // 217. Contains Duplicate
    // 89/72 @ 8ms; 
    public boolean containsDuplicate(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        for (int i : nums)
            if (set.add(i) == false) return true;
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

    // 236. Lowest Common Ancestor of a Binary Tree
    // 69/27 @ 9ms; 21/18 @ 15ms
    // only traverses through the tree a single time
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode[] lca = new TreeNode[1]; // bootleg pointer
        findLCA(root, p.val, q.val, lca);
        return lca[0];
    } // lowestCommonAncestor()
    // boolean[0] is ancestor of p, boolean[1] is ancestor of q
    private boolean[] findLCA(TreeNode root, int p, int q, TreeNode[] lca) {
        boolean[] b = new boolean[]{false, false};
        // lca has been found already or the root is null
        if      (lca[0] != null || root == null) return b; 
        // if the root as a target node, mark the proper boolean
        else if (root.val == p) b[0] = true;
        else if (root.val == q) b[1] = true;
        // search for the next target node
        boolean[] l = findLCA(root.left, p, q, lca);
        boolean[] r = findLCA(root.right, p, q, lca);
        // is ancestor if it is the node or the right or left is an ancestor
        b[0] = b[0] || l[0] || r[0]; 
        b[1] = b[1] || l[1] || r[1];
        // if it is an ancestor of both, set the lca
        if (b[0] == true && b[1] == true) {
            lca[0] = root;
            // clear booleans so no higher ancestors overwrite the lca
            b[0] = false;
            b[1] = false;
        } // if
        return b;
    } // findLCA()
    // this is the soln above before lca became a "pointer"
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode lca = null;
        findLCA(root, p.val, q.val, lca);
        return lca;
    } // lowestCommonAncestor()
    // boolean[0] is ancestor of p, boolean[1] is ancestor of q
    private boolean[] findLCA(TreeNode root, int p, int q, TreeNode lca) {
        boolean[] b = new boolean[]{false, false};
        // lca has been found already or the root is null
        if      (lca != null || root == null) return b; 
        else if (root.val == p) b[0] = true;
        else if (root.val == q) b[1] = true;
        boolean[] l = findLCA(root.left, p, q, lca);
        boolean[] r = findLCA(root.right, p, q, lca);
        // is ancestor if it is the node or the right or left is an ancestor
        b[0] = b[0] || l[0] || r[0]; 
        b[1] = b[1] || l[1] || r[1];
        // if it is an ancestor of both, set the lca
        if (b[0] == true && b[1] == true) lca = root;
        return b;
    } // findLCA()
    // O(n)/O(n)
    // 37/87 @ 12ms 
    // two dfs to find ancestry and a bfs to find the common
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // first a DFS to find all of the ancestors of p and q
        Set<Integer> pAncestors = new HashSet<>();
        Set<Integer> qAncestors = new HashSet<>();
        hasDescendant(root, p, pAncestors);
        hasDescendant(root, q, qAncestors);
        // then a BFS to find the lowest root with both descendants
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        TreeNode lca = root;
        while (!queue.isEmpty()) {
            if (t == null) continue;
            TreeNode t = queue.poll();
            if (pAncestors.contains(t.val) && qAncestors.contains(t.val))
                lca = t;
            queue.offer(t.left);
            queue.offer(t.right);
        } // while
        return lca;
    } // lowestCommonAncestor()
    // DFS to see if a node has the given descendant
    private boolean hasDescendant(TreeNode root, TreeNode d, Set<Integer> dAncestor) {
        if (root == null) return false;
        else if (root.val == d.val) {
            dAncestor.add(root.val);
            return true;
        } else {
            boolean hasDescendant = 
                hasDescendant(root.left, d, dAncestor) ||
                hasDescendant(root.right, d, dAncestor);
            if (hasDescendant) dAncestor.add(root.val);
            return hasDescendant;
        } // if
    } // hasDescendant()

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

    // 238. Product of Array Except Self
    // 89/90 @ 2ms
    public int[] productExceptSelf(int[] nums) {
        int[] pre = new int[nums.length];
        pre[0] = 1; // because suf of the beginning is 1
        // get the products of the nums before
        for (int i = 1; i < pre.length; i++)
            pre[i] = nums[i - 1] * pre[i - 1];
        // get the products of the nums after
        int[] suf = new int[nums.length];
        suf[suf.length - 1] = 1; // because suf of the beginning is 1
        for (int i = suf.length - 2; i >= 0; i--)
            suf[i] = nums[i + 1] * suf[i + 1];
        // now multiply each corresponding index in pre and suf for the answer
        // will write into suf
        for (int i = 0; i < suf.length; i++)
            suf[i] = pre[i] * suf[i];
        return suf;
    } // productExceptSelf()
    // 89/31 @ 2ms
    public int[] productExceptSelf(int[] nums) {
        int[] pre = new int[nums.length];
        pre[0] = 1; // because suf of the beginning is 1
        // get the products of the nums before
        for (int i = 1; i < pre.length; i++)
            pre[i] = nums[i - 1] * pre[i - 1];
        // get the products of the nums after
        // will write into the nums/suf arr because the values will no longer be needed
        int[] suf = nums;
        // this uses 66% of the space (2 n length arrs instead of 3)
        int prevNum = suf[suf.length - 1];
        suf[suf.length - 1] = 1;
        int curNum;
        for (int i = suf.length - 2; i >= 0; i--) {
            curNum = suf[i];
            suf[i] = suf[i + 1] * prevNum;
            prevNum = curNum;
        } // for
        // now multiply each corresponding index in pre and suf for the answer
        // will write into suf
        for (int i = 0; i < suf.length; i++)
            suf[i] = pre[i] * suf[i];
        return suf;
    } // productExceptSelf()

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
            //count[s.charAt(i) - 'a'];
            //count[s.charAt(i) - 'a'];
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

    // 322. Coin Change
    // O(n)/O(n) 87/69 @ 19ms;
    // using tabulation
    public int coinChange(int[] coins, int amount) {
        if (amount == 0) return 0;
        Arrays.sort(coins);
        int[] tab = new int[amount + 1];
        for (int coin : coins)
            if (coin <= amount) tab[coin] = 1;
        for (int amt = 1; amt < tab.length; amt++) {
            for (int i = coins.length - 1; i >= 0; i--)
                if (coins[i] < amt)
                    if (tab[amt - coins[i]] != 0)
                        if (tab[amt] == 0) tab[amt] = tab[amt - coins[i]] + 1;
                        else tab[amt] = Math.min(1 + tab[amt - coins[i]], tab[amt]);
            // if tab[amt] is still 0 here, change is not possible
            if (tab[amt] == 0) tab[amt] = -1;
        } // for amt
        return tab[amount] == 0 ? -1 : tab[amount];
    } // coinChange()
    // using memoization
    // not working, may need a Math.min to fix it
    public int coinChange(int[] coins, int amount) {
        Arrays.sort(coins);
        Map<Integer, Integer> memo = new HashMap<>();
        memo.put(0, 0);
        return coinChange(coins, amount, memo);
    } // coinChange()
    private int coinChange(int[] coins, int amount, Map<Integer, Integer> memo) {
        System.out.printf("checking amount: %d\n", amount);
        if (memo.containsKey(amount)) return memo.get(amount);
        for (int i = coins.length - 1; i >= 0; i--) {
            // want to start with the largest coin less than amount
            if (coins[i] > amount) continue;
            // if there is no way to make change for that amount
            if (coinChange(coins, amount - coins[i], memo) == -1) continue;
            // else a valid way to make change was found
            memo.put(amount, 1 + coinChange(coins, amount - coins[i], memo));
            System.out.printf("amount: %d needs: %d\n", amount, memo.get(amount));
            return memo.get(amount);
        } // for
        // if the loop ends and execution gets here, there was no way to make that value
        memo.put(amount, -1);
        return -1;
    } // coinChange()

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

    // 416. Partition Equal Subset Sum
    //
    public boolean canPartition(int[] nums) {
        // get the total sum of the array
        int sum = 0;
        for (int n : nums) sum += n;
        // if odd, it can't be split
        if ((sum % 2) == 1) return false;
        // get half of the total sum which will be the target to add up to
        int target = sum / 2;

    } // canPartition()
    public boolean canPartition0(int[] nums) {
        // get the total sum of the array
        int sum = 0;
        for (int n : nums) sum += n;
        // if odd, it can't be split
        if ((sum % 2) == 1) return false;
        // get half of the total sum which will be the target to add up to
        int target = sum / 2;
        return backtrack(nums, 0, target);
    } // canPartition()
    // if the target can be subtracted from to be exactly 0,
    // then there are numbers which can sum to the target
    private boolean backtrack(int[] nums, int start, int target) {
        if (target == 0) return true;
        if (target < 0 || start >= nums.length) return false;
        for (int i = start; i < nums.length; i++) {
            target -= nums[i]; 
            if (backtrack(nums, i + 1, target)) return true;
            target += nums[i];
        } // for i
        return false;
    } // backtrack()
    public boolean canPartition1(int[] nums) {
        // get the total sum of the array
        int sum = 0;
        for (int n : nums) sum += n;
        // if odd, it can't be split
        if ((sum % 2) == 1) return false;
        // get half of the total sum which will be the target to add up to
        int target = sum / 2;
        Arrays.sort(nums);
        return backtrack(nums, 0, target);
    } // canPartition()
    private boolean backtrack(int[] nums, int start, int target) {
        if (target == 0) return true; // partition has been found
        if (start >= nums.length) return false; // out of bounds start
        // backtrack
        for (int i = start; i < nums.length; i++) {
            target -= nums[i];
            if (target < 0) return false; // since sorted, can exit early
            if (backtrack(nums, i + 1, target)) return true;
            target += nums[i];
        } // for i
        return false;
    } // backtrack()



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

    // 721. Accounts Merge
    // TODO
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        // associate each email in the accounts with an account
        Map<String, Integer> map = new HashMap<>();
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        // initialize every edge
        for (int a = 0; a < accounts.size(); a++)
            graph.put(a, new HashSet<Integer>());
        for (int accountInt = 0; accountInt < accounts.size(); accountInt++) {
            List<String> account = accounts.get(accountInt);
            for (int emailInt = 1; account.size(); emailInt++) {
                String email =  account.get(emailInt);
                if (map.containsKey(email) { // email is mapped to an account
                    // if it's mapped to the same account, it's a dupe so remove it
                    if (map.get(email) == accountInt) {
                        account.remove(emailInt);
                        emailInt--; // so as not to skip an email
                    } else { // the email is part of another account
                        graph.get(map.get(email)).add(accountInt);
                        graph.get(accountInt).add(map.get(email));
                        // make edges between both associated nodes
                    } // if
                } else { // email is not mapped to an account
                    map.put(email, accountInt);
                } // if
            } // for emailInt
        } // for account
        // now all dupe emails should be removed and accounts should be connected
        // 
        boolean[] seen = new boolean[accounts.size()];
        for (account = 0; account < accounts.size(); account++) {
            mergeAccounts(graph, seen, account, account);
        } // for account
    } // accountsMerge()
    private void mergeAccounts(Map<Integer, Set<Integer>> graph, boolean[] seen, int main, int subNode) {
        if (seen[subNode]) return;
        seen[subNode] = true;
        Integer[] neighbors = new Integer[graph.get(subNode).size()];
        graph.get(subNode).toArray(neighbors);
        for (Integer neighbor : neighbors) {
            graph.get(main).add(neighbor);
            mergeAccounts(graph, seen, main, neighbor);
        } // for neighbor
    } // mergeAccounts()

    // attempt 0; a linear approach that fails to account for later assc between accounts
    // count work if the entire function ran in a loop but would be O(n^2)
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        Map<String, Integer> map = new HashMap<>();
        for (int acct = 0; acct < accounts.size(); acct++) {
            for (int e = 1; e < accounts.get(acct).size(); e++) {
                String email = accounts.get(acct).get(e);
                // check if the email is already associated with an acct
                if (map.containsKey(email)) {
                    if (map.get(email) == acct) {
                        // if the email is a dublicate within the account
                        accounts.get(acct).remove(e);
                        e--;
                    } else { // if the email is associated with a different account
                        // merge the two accounts
                        int mergeTo = map.get(email);
                        mergeAccounts(mergeTo, acct, accounts, map);
                        accounts.remove(acct); // then remove the extra account
                        acct--; // need to decrement the current acct number since one was removed
                        break; // since this account is accounted for
                    } // if
                } else { // email has not been associated with an account
                    map.put(email, acct); // associate it
                } // if
            } // for email
        } // for acct
        // all accounts should now be merged, so it is time to sort them
        // remove the name from the list, sort it, and reenter the name
        for (List<String> account : accounts) {
            String name = account.remove(0);
            account.sort((String a, String b) -> a.compareToIgnoreCase(b));
            account.add(0, name);
        } // for account
        return accounts;
    } // accountsMerge()

    // merges 2 accounts ie adds the emails from mergeFrom to mergeTo
    private void mergeAccounts(int mergeTo, int mergeFrom, List<List<String>> accounts, Map<String, Integer> map) {
        List<String> mergeToAcct = accounts.get(mergeTo);
        List<String> mergeFromAcct = accounts.get(mergeFrom);
        for (int email = 1; email < mergeFromAcct.size(); email++) {
            // first, check if the email is already in mergeTo
            String emailStr = mergeFromAcct.get(email);
            if (    map.containsKey(emailStr) && 
                    map.get(emailStr) == mergeTo) {
               continue;
            } else { // else add it to mergeTo and associate it
                mergeToAcct.add(mergeFromAcct.get(email));
                map.put(mergeFromAcct.get(email), mergeTo);
            } // if
        } // for email
    } // mergeAccounts()

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

    // 981. Time Based Key-Value Store
    // TODO
    class TimeMap {

        public TimeMap() {

        } // TimeMap()

        public void set(String key, String value, int timestamp) {

        } // set(k,v,t)

        public String get(String key, int timestamp) {

        } // get(k,t) 
    } // TimeMap()
    // 994. Rotting Oranges
    // approach is like BFS
    // O(n)/O(n)
    // 46/12 @ 4ms; 76/12 @ 3ms
    public int orangesRotting(int[][] grid) {
        Queue<int[]> q = new LinkedList<int[]>();
        // queue up the rotten oranges
        queueOranges(grid, q, 2);
        // now rot the oranges and track how many minutes it takes
        int minutes = 0;
        while (!q.isEmpty()) {
            int batch = q.size(); // rot oranges by batches
            while (batch > 0) {
                rotNeighbors(grid, q);
                batch--;
            } // while
            minutes++;
        } // while
        if (minutes > 0) minutes--; 
        // 1 minute will pass with no new rotting happening if oranges have already rotted
        // all rottable oranges should now be rotted so search for any fresh ones
        queueOranges(grid, q, 1);
        // if the q has any elements, there are fresh oranges remaining so return -1
        return q.isEmpty() ? minutes : -1;
    } // orangesRotting()
    private void queueOranges(int[][] grid, Queue<int[]> q, int orangeType) {
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                if (grid[r][c] == orangeType)
                    q.offer(new int[]{r, c});
            } // for c
        } // for r
    } // queueOranges()
    // rot and queue each fresh neighbor of the next orange in the queue
    private void rotNeighbors(int[][] grid, Queue<int[]> q) {
        int[] rottenOrange = q.poll();
        int[] dir = new int[]{0, 1, 0, -1, 0};
        int[] neighbor;
        for (int d = 1; d < dir.length; d++) {
            neighbor = new int[]{ rottenOrange[0] + dir[d - 1],
                                        rottenOrange[1] + dir[d]};
            // bounds check then check if it's a fresh orange
            if (neighbor[0] < 0 || neighbor[0] >= grid.length    ||
                neighbor[1] < 0 || neighbor[1] >= grid[0].length ||
                grid[neighbor[0]][neighbor[1]] != 1) continue;
            // else it is a fresh orange so rot it and add it to the queue
            grid[neighbor[0]][neighbor[1]] = 2;
            q.offer(neighbor);
        } // for
    } // rotNeighbors()


} // class
