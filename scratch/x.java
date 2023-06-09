class x {
    public static void main(String[] args) {
        char[] letters = {'a','e','o','u','c','m','n','r','s','v','w','x','z'};
        for (int i = 0; i < letters.length; i++) {
        for (int ii = 0; ii < letters.length; ii++) {
        for (int iii = 0; iii < letters.length; iii++) {
            if (i == 9 || ii == 9 || iii == 9) {
            System.out.printf("%c%c%c\n", letters[i], letters[ii], letters[iii]);
            }
        }
        }
        } // for
    } // main()
} // x
