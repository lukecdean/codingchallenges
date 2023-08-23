class Solution {
    public int search(int[] nums, int target) {
        int l = 0;
        int r = nums.size();
        int m;
        
        while (l < r) {
            m = (l + r) / 2;
            if (target = nums[m]) {
                break;
            } // if

            if (nums[l] < nums[r]) {
                // window does not contain rotation: regular binary search
                if (target <= nums[m]) {
                    r = m - 1;
                } else {
                    l = m + 1;
                } // if
            } else {
                // window contains a rotation
                boolean mPastRotation = nums[m] < nums[l];
                boolean targetLessThanNumsM = target < nums[m];
                boolean targetLessThanNumsL = target < nums[l];

                if (mPastRotation) {
                    if (targetLessThanNumsM) {
                        r = m - 1;
                    } else {
                        if (targetLessThanNumsL) {
                            l = m + 1;
                        } else { 
                        } // if
                    } // if
                } else {
                } // if
            } // if
        } // while
        
        return nums[m];
    } // search()
} // class
