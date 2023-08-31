class Solution {
    // 100/96 @ 3ms
    public long minimumReplacement(int[] nums) {
        long replacements = 0;
        // right will be the value curr cannot be greater than
        int right = nums[nums.length - 1];
        // start from the 2nd to last index (no reason to split last index)
        for (int i = nums.length - 2; i >= 0; i--) {
            int curr = nums[i];

            if (right < curr) {
                // not non-decreasing so split up

                // start with a naive split
                int divisions = curr / right;
                // add how many splits it would take
                replacements += divisions - 1;

                int remainder = curr % right;
                if (remainder > 0) {
                    // if there is extra after the naive split,
                    // maximize the value of the remainder
                    replacements++; // will take an extra split
                    //                             (right - remainder)
                    // this formula: rows = ceiling(-----------------)
                    //                             (  1 + divsions   )
                    // I call it row because I see the divisions lined up
                    // like stacks of objects with the remainder as a shorter
                    // stack to the left. Want to know how many rows of the
                    // objects to take from the divisions and add to
                    // the remainder stack.
                    // Something like this:
                    //      divisions-|||
                    //                vvv
                    //
                    //                XXX <- row
                    //                XXX
                    //      rem. ->  XXXX
                    int numer = right - remainder;
                    int denom = 1 + divisions;
                    int rows = numer / denom;
                    int rowsRemainder = numer % denom;
                    if (rowsRemainder > 0) {
                        rows++;
                    } // if

                    right -= rows; // the max right can be
                } // if
            } else {
            // non-decreasing so fine
                right = curr;
            } // if
        } // for curr
        return replacements;
    } // minR()

    /* attempt 1
    public long minimumReplacement(int[] nums) {
        if (nums.length < 2) {
            return 0;
        } // if
        // place all values in a stack starting with 0
        Stack<Integer> stack = new Stack<>(nums.length);
        for (int num : nums) {
            stack.push(num);
        } // for
        
        long replacements = 0;
        int right = stack.pop();

        while (!stack.isEmpty()) {
            int curr = stack.pop();
            if (curr > right) {
                // if not non-decreasing, split in half
                replacements++;
                stack.push(curr / 2);
                right = curr / 2 + curr & 1; // if curr was odd, 


            } // if
        } // while
    } // minR()
    */

    /* attempt 2
    public long minimumReplacement(int[] nums) {
        long replacements = 0;
        for (int i = nums.length - 2; i >= 0; i--) {
            int right = nums[i + 1];
            int curr = nums[i];

            if (right < curr) {
                // not non-decreasing
                // split up
                replacements += (curr / right) - 1;

                int remainder = curr % right;
                if (remainder > 0) {
                    // may make left larger than what the naive split did
                    right = (remainder + right ) / 2;
                    replacements++;
                } // if
            } // if
            // else non-decreasing ie fine
        } // for curr
        return replacements;
    } // minR()
    */
} // class
