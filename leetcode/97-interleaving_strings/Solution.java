class Solution {
    public boolean isInterleave(String s1, String s2, String s3) {
        boolean[] invalid = new boolean[s2.length()];

        return isInterleave(s1, s2, s3, -1, -1, -1, invalid);
    }

    boolean isInterleave(String s1, String s2, String s3, int p1, int p2, int p3, boolean[] invalid) {
        boolean wasInterleaving = false;
        if (s1.charAt(p2 + 1) == s3.charAt(p3 + 1)) {
            // if the next char in s3 matches the next char in s2, try it
            wasInterleaving = isInterleave(s1, s2, s3, p1, p2 + 1, p3 + 1, invalid);

            if (wasInterleaving) {
                return true;
            } else if (s1.charAt(p1 + 1) == s3.charAt(p3 + 1)) {
                    wasInterleaving wasInterleaving = isInterleave(s1, s2, s3, p1 + 1, p2, p3 + 1, invalid);
            } // if

            return wasInterleaving;
        } // if
    } // isInterleave()
}
