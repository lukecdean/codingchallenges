class Solution {
    // 57/98 @ 90ms
    public int[] longestObstacleCourseAtEachPosition(int[] obstacles) {
        int[] res = new int[obstacles.length];

        int[] tails = new int[obstacles.length];
        int size = 0;

        for (int i = 0; i < obstacles.length; i++) {
            int o = obstacles[i];
            int b = 0;
            int e = size;
            int m;
            
            while (b != e) {
                m = (b + e) / 2;
                if (tails[m] <= o) {
                    b = m + 1;
                } else { // tails[m] > o
                    e = m;
                } // if
            } // while

            tails[b] = o;
            if (b == size) {
                size++;
            } // if

            res[i] = b + 1;
        } // for

        return res;
    } //lOCAEP()
}
