class Solution {
    // 84/36 @ 1ms
    public int reachNumber(int target) {
        if (target < 0) {
            target *= -1;
        }

        int steps = 0;
        int currNum = 0;
        
        while (currNum < target) {
            steps++;
            currNum += steps;
        } // while

        int difference = currNum - target;

        if ((difference & 1) == 0) {
            // an even difference can be cancelled by reversing the diff/2 step
            return steps;
        } else {
            // if one more step makes diff even
            // else two more steps make diff even
            return steps + 1 + (steps & 1);
        }
    } // reachNumber()
} // class
