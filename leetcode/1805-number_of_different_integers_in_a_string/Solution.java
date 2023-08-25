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
}
