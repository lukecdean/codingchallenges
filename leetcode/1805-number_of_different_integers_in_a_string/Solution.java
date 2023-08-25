class Solution {
    public int numDifferentIntegers0(String word) {
        Set<Integer> uniqueInts = new HashSet<>();
        boolean onInt = false;
        int currentInt = 0;

        for (int c = 0; c < word.length(); c++) {
            if (Character.isDigit(word.charAt(c))) {
                // is digit
                if (onInt == false) {
                    // if it's the first digit in an int
                    currentInt = 0;
                    onInt = true;
                } // if
                currentInt *= 10;
                currentInt += Character.getNumericValue(word.charAt(c));
            } else {
                // is not digit
                if (onInt == true) {
                    // made it past an int; add int to set
                    uniqueInts.add(currentInt);

                    onInt = false;
                } // if
                // else move on to next char
            } // if
        } // for c

        if (onInt == true) {
            // edge case: ended while on an int
            uniqueInts.add(currentInt);
        } // if

        return uniqueInts.size();
    }

    public int numDifferentIntegers(String word) {
        Set<String> uniqueInts = new HashSet<>();
        boolean inInt = false;
        boolean foundNonZero = false;
        int intStart = -1;

        int c;
        for (c = 0; c < word.length(); c++) {
            if (Character.isDigit(word.charAt(c))) {
                // is part of an int
                if (!inInt) {
                    // the first digit of the int
                    inInt = true;
                    if (!foundNonZero && word.charAt(c) != '0') {
                        foundNonZero = true;
                        intStart = c;
                    } // if
                } // if
            } else {
                // is not part of an int
                if (inInt) {
                    // i.e. got to end of the int
                    if (foundNonZero) {
                        uniqueInts.add(word.substring(intStart, c));
                        inInt = false;
                        foundNonZero = false;
                    } else {
                        // the int was only made of 0s
                        uniqueInts.add("0");
                        inInt = false;
                        foundNonZero = false;
                    }
                } // if
            } // if
        } // for c

        if (inInt) {
            // edge case: word ends with an int
            if (foundNonZero) {
                uniqueInts.add(word.substring(intStart, word.length()));
            } else {
                // the int was only made of 0s
                uniqueInts.add("0");
            }
        } // if

        return uniqueInts.size();
    }

    // 78/99 @ 3ms
    public int numDifferentIntegers(String word) {
        Set<String> ints = new HashSet<>();
        boolean nonzeroInt = false;
        boolean zero = false;
        int intStart = -1;

        int i;
        char c = 0;
        for (i = 0; i <= word.length(); i++) {
            if (i < word.length()) {
                c = word.charAt(i);
            } // if

            if (!nonzeroInt && !zero) {
                // state: not int
                if (i == word.length()) {
                    // next state: end
                    zero = true;
                    nonzeroInt = true;
                } else if (c == '0') {
                    // next state: zero
                    zero = true;
                    nonzeroInt = false;
                } else if (Character.isDigit(c)) {
                    // next state: int
                    zero = false;
                    nonzeroInt = true;
                    intStart = i;
                } // if
            } else if (zero && !nonzeroInt) {
                // state: zero
                if (i == word.length()) {
                    // next state: end
                    zero = true;
                    nonzeroInt = true;
                    ints.add("0");
                } else if (word.charAt(i) == '0') {
                    // next state: zero
                    continue;
                } else if (Character.isDigit(c)) {
                    // next state: int
                    zero = false;
                    nonzeroInt = true;
                    intStart = i;
                } else { // nonint character
                    // next state: not int
                    zero = false;
                    nonzeroInt = false;
                    ints.add("0");
                } // if
            } else if (!zero && nonzeroInt) {
                // state: int
                if (i == word.length()) {
                    // next state: end
                    zero = true;
                    nonzeroInt = true;
                    ints.add(word.substring(intStart, word.length()));
                } else if (!Character.isDigit(c)) {
                    // next state: not int
                    zero = false;
                    nonzeroInt = false;
                    ints.add(word.substring(intStart, i));
                } // if
                // else continue
            } else { // zero && nonzero
                // state: end
                break;
            } // if
        } // for i

        return ints.size();
    }

}
