class Solution {
    public int minPairSum(int[] nums) {
        Arrays.sort(nums);
        int maxSum = -1;
        for (int i = 0; i < nums.length / 2; i++) {
            int currSum = nums[i] +  nums[nums.length - 1 - i];
            if (maxSum < currSum) {
                maxSum = currSum;
            } // if
        } // for i
        return maxSum;
    }
}
