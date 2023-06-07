class Solution {
    public int minFlips(int a, int b, int c) {
        int flips = 0;

        for (int i = 0; i < 32; i++) {
            int aa = 1 & (a >> i); // nth bit of a
            int bb = 1 & (b >> i); // nth bit of b
            int cc = 1 & (c >> i); // nth bit of c

            flips += 1 & (cc & ~(aa | bb)); // cc is 1, but neither aa or bb are
            flips += (~cc) & (aa | bb); // cc is 0 one of aa and bb are 1
            flips += (~cc) & (aa & bb); // cc is 0 but both aa and bb are 1
        } // for i

        return flips;
    } // minFlips()
}
