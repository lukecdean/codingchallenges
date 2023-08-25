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

