import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

class AdventOfCode2022 {

    static Scanner input;

    public static void main(String[] args) {
        input = getInput(args[0]);
        switch (args[0]) {
            case ("1-1"):
                dayOneOne();
                break;
            case ("1-2"):
                dayOneTwo();
                break;
            case ("2-1"):
                dayTwoOne();
                break;
            case ("2-2"):
                dayTwoTwo();
                break;
            case ("3-1"):
                dayThreeOne();
                break;
            case ("3-2"):
                dayThreeTwo();
                break;
            case ("4-1"):
                dayFourOne();
                break;
            case ("4-2"):
                dayFourTwo();
                break;
            case ("5-1"):
                dayFiveOne();
                break;
            case ("5-2"):
                dayFiveTwo();
                break;
            case ("6-1"):
                daySixOne();
                break;
            case ("6-2"):
                daySixTwo();
                break;
            case ("7-1") :
                daySevenOne();
                break;
            case ("7-2"):
                daySevenOne();
                break;
            case ("8-1"):
                dayEightOne();
                break;
            case ("8-2"):
                dayEightTwo();
                break;
            case ("9-1") :
                dayNineOne();
                break;
            case ("9-2") :
                dayNineTwo();
                break;
            case ("10-1") :
                dayTenOne();
                break;
            case ("10-2") :
                dayTenTwo();
                break;
            case ("11-1") :
                dayElevenOne();
                break;
            case ("11-2") :
                dayElevenTwo();
                break;
            case ("12-1") :
                dayTwelveOne();
                break;
            case ("12-2") :
                dayTwelveTwo();
                break;
            case ("13-1") :
                dayThirteenOne();
                break;
            case ("13-2") :
                dayThirteenTwo();
                break;
            case ("14-1") :
                dayFourteenOne();
                break;
            case ("14-2") :
                dayFourteenTwo();
                break;
            case ("15-1") :
                dayFifteenOne();
                break;
            case ("15-2") :
                dayFifteenTwo();
                break;
            case ("16-1") :
                daySixteenOne();
                break;
            case ("16-2") :
                daySixteenTwo();
                break;
            case ("17-1") :
                daySeventeenOne();
                break;
            case ("17-2") :
                daySeventeenTwo();
                break;
            case ("18-1") :
                dayEighteenOne();
                break;
            case ("18-2") :
                dayEighteenTwo();
                break;
            case ("19-1") :
                dayNineteenOne();
                break;
            case ("19-2") :
                dayNineteenTwo();
                break;
            case ("20-1") :
                dayTwentyOne();
                break;
            case ("20-2") :
                dayTwentyTwo();
                break;
            case ("21-1") :
                dayTwentyoneOne();
                break;
            case ("21-2") :
                dayTwentyoneTwo();
                break;
            case ("22-1") :
                dayTwentytwoOne();
                break;
            case ("22-2") :
                dayTwentytwoTwo();
                break;
            case ("23-1") :
                dayTwentythreeOne();
                break;
            case ("23-2") :
                dayTwentythreeTwo();
                break;
            case ("24-1") :
                dayTwentyfourOne();
                break;
            case ("24-2") :
                dayTwentyfourTwo();
                break;
            case ("25-1") :
                dayTwentyfiveOne();
                break;
            case ("25-2") :
                dayTwentyfiveTwo();
                break;

        } // switch
    } // main()

    // getInput()
    static Scanner getInput(String fileName) {
        String filePath = "input/" + fileName + ".txt";
        try {
            Scanner input;
            input = new Scanner(new File(filePath));
            return input;
        } catch (Exception e) {
            System.out.println(e);
            //System.exit(1);
        } // try
        return null;
    } // getInput()

    static void dayX() {
        int res = 0;
        String line;
        while (input.hasNextLine()) {
            line = input.nextLine();
        } // while
        System.out.println(res);
    } // dayX()

    static void dayOneOne() {
        int max = 0;
        int cur = 0;
        String line;
        while (input.hasNextLine()) {
            line = input.nextLine();
            if (line.length() == 0) {
                max = Math.max(max, cur);
                cur = 0;
                continue;
            } // if
            cur += Integer.parseInt(line);
        } // while
        System.out.println(max);
    } // dayX()

    static void dayOneTwo() {
        PriorityQueue<Integer> heap = new PriorityQueue<>((Integer a, Integer b) -> b - a);
        int cur = 0;
        String line;
        while (input.hasNextLine()) {
            line = input.nextLine();
            if (line.length() == 0) {
                heap.offer(cur);
                cur = 0;
                continue;
            } else {
                cur += Integer.parseInt(line);
            } // if
        } // while
        int sum = 0;
        sum += heap.poll();
        sum += heap.poll();
        sum += heap.poll();
        System.out.println(sum);
    } // dayOneTwo()

    static void dayTwoOne() {
        /*
         * A X   0
         * B Y   1
         * C Z   2
         */
        int score = 0;
        while (input.hasNextLine()) {
            String line = input.nextLine();
            int elf = line.charAt(0) - 'A';
            int you = line.charAt(2) - 'X';

            for (int i = 0; i <= 2; i++) {
                // you + 4 - i traverses the cycle in order of lose, draw, win 
                // until it finds the one the elf played
                if (((you + 4 - i) % 3) == elf) {
                    score += i * 3;
                } // if
            } // for i
            score += you + 1;
        } // while
        System.out.println(score);
    } // dayTwoOne()

    static void dayTwoTwo() {
        int score = 0;
        int elf;
        int outcome;
        String line;
        while (input.hasNextLine()) {
            line = input.nextLine();
            elf = line.charAt(0) - 'A';
            outcome = line.charAt(2) - 'X';

            score += ((elf + 2 + outcome) % 3) + 1;
            score += outcome * 3;
        } // while

        System.out.println(score);
    } // dayTwoTwo()

    static void dayThreeOne() {
        int sum = 0;
        while (input.hasNextLine()) {
            boolean[] upper = new boolean[26];
            boolean[] lower = new boolean[26];
            char[] chars = input.nextLine().toCharArray(); 
            for (int i = 0; i < chars.length / 2; i++) {
                char c = chars[i];
                if (Character.isUpperCase(c)) {
                    upper[c - 'A'] = true;
                } else { // isLowerCase
                    lower[c - 'a'] = true;
                } // if
            } // for i
            for (int i = chars.length / 2; i < chars.length; i++) {
                char c = chars[i];
                if (Character.isUpperCase(c) && upper[c - 'A'] == true) {
                    sum += 26 + c - 'A' + 1;
                    break;
                } else if (Character.isLowerCase(c) && lower[c - 'a'] == true) { // isLowerCase
                    sum += c - 'a' + 1;
                    break;
                } // if
            } // for i
        } // while
        System.out.println(sum);
    } // dayThreeOne()

    static void dayThreeTwo() {
        //input = getInput("3-1-eg");
        int sum = 0;
        while (input.hasNextLine()) {
            boolean[][] contains = new boolean[3]['z' - 'A' + 1];
            // log each item for the next 3 elves
            for (int elf = 0; elf < 3; elf++) {
                char[] chars = input.nextLine().toCharArray(); 
                for (char item : chars) {
                    contains[elf][item - 'A'] = true;
                } // for item
            } // for elf

            // look for the common item
            for (int item = 0; item < contains[0].length; item++) {
                boolean containsItem = true;
                for (int elf = 0; elf < 3; elf++) {
                    containsItem = containsItem && contains[elf][item];
                } // for elf
                if (containsItem) { // found common item
                    //System.out.println((char) (item + 'A'));
                    // find priority
                    if (Character.isLowerCase((char) (item + 'A'))) {
                        sum += item - 26 - ('a' - 'Z') + 1;
                    } else { // upper case
                        sum += item + 26;
                    } // if
                    sum += 1; // 1-indexed priority values
                } // if
            } // for
        } // while
        System.out.println(sum);
    } // dayThreeTwo()

    static void dayFourOne() {
        int res = 0;
        int[][] sections = new int[2][2]; // [elf][<bgn, end>]

        input.useDelimiter("[-\n,]");

        while (input.hasNextInt()) {
            for (int elf = 0; elf < 2; elf++) {
                for (int end = 0; end < 2; end++) {
                    sections[elf][end] = input.nextInt();
                    //System.out.println(sections[elf][end]);
                } // for end
            } // for elf

            if (sections[0][0] <= sections[1][0] && sections[1][1] <= sections[0][1]) {
                res++;
            } else if (sections[1][0] <= sections[0][0] && sections[0][1] <= sections[1][1]) {
                res++;
            } // if
        } // while
        System.out.println(res);
    } // dayFourOne()

    static void dayFourTwo0() {
        int res = 0;
        int[][] sections = new int[2][2]; // [elf][<bgn, end>]

        input.useDelimiter("[-\n,]");

        while (input.hasNextInt()) {
            for (int elf = 0; elf < 2; elf++) {
                for (int end = 0; end < 2; end++) {
                    sections[elf][end] = input.nextInt();
                } // for end
            } // for elf

            // if sections 0 contains either end of section 1
            if (sections[0][0] <= sections[1][0] && sections[1][0] <= sections[0][1]) {
                res++;
            } else if (sections[0][0] <= sections[1][1] && sections[1][1] <= sections[0][1]) {
                res++;
            // if sections 1 contains either end of section 0
            } else if (sections[1][0] <= sections[0][0] && sections[0][0] <= sections[1][1]) {
                res++;
            } else if (sections[1][0] <= sections[0][1] && sections[0][1] <= sections[1][1]) {
                res++;
            } // if
        } // while
        System.out.println(res);
    } // dayFourTwo()
    static void dayFourTwo() {
        int res = 0;
        int[][] sections = new int[2][2]; // [elf][<bgn, end>]

        input.useDelimiter("[-\n,]");

        while (input.hasNextInt()) {
            for (int elf = 0; elf < 2; elf++) {
                for (int end = 0; end < 2; end++) {
                    sections[elf][end] = input.nextInt();
                } // for end
            } // for elf
            
            res++;
            // assume they overlap and subtract if not
            // ie if a close is less than the other open
            if (sections[0][1] < sections[1][0] || sections[1][1] < sections[0][0]) {
                res--;
            } // if
        } // while
        System.out.println(res);
    } // dayFourTwo()


    static void dayFiveOne() {
        List<Stack<Character>> stacks = new ArrayList<>();
        // build the initial stacks
        while (input.hasNextLine()) {
            String line = input.nextLine();
            if (line.length() == 0) { // finished making stacks
                break;
            } // if
            for (int stack = 0; (stack * 4) + 1 < line.length(); stack++) {
                if (stacks.size() <= stack) { // initialize the stack
                    stacks.add(new Stack<>());
                } // if
                char crate = line.charAt((stack * 4) + 1);
                if (Character.isLetter(crate)) {
                    stacks.get(stack).push(crate);
                    // crates will be in reverse order
                } // if
            } // for stack
        } // while

        reverseCrateStacks(stacks);

        //input.useDelimiter("[^0-9]");

        while (input.hasNext()) {
            input.next();
            int count = input.nextInt();
            input.next();
            int from = input.nextInt();
            from--; // 0 indexed
            input.next();
            int to = input.nextInt();
            to--; // 0 indexed

            while (count >= 1) {
                char movingCrate = stacks.get(from).pop();
                stacks.get(to).push(movingCrate);

                count--;
            } // while
        } // while

        String res = "";
        for (Stack<Character> stack : stacks) {
            res += stack.pop();
        } // for stack
        System.out.println(res);
    } // dayFiveOne()

    static void reverseCrateStacks(List<Stack<Character>> stacks) {
        for (int i = 0; i < stacks.size(); i++) {
            Stack<Character> newStack = new Stack<>();
            while (!stacks.get(i).empty()) {
                newStack.push(stacks.get(i).pop());
            } // while
            stacks.set(i, newStack);
        } // for i
    } // rCS()

    static void dayFiveTwo() {
        List<Stack<Character>> stacks = new ArrayList<>();
        // build the initial stacks
        while (input.hasNextLine()) {
            String line = input.nextLine();
            if (line.length() == 0) { // finished making stacks
                break;
            } // if
            for (int stack = 0; (stack * 4) + 1 < line.length(); stack++) {
                if (stacks.size() <= stack) { // initialize the stack
                    stacks.add(new Stack<>());
                } // if
                char crate = line.charAt((stack * 4) + 1);
                if (Character.isLetter(crate)) {
                    stacks.get(stack).push(crate);
                    // crates will be in reverse order
                } // if
            } // for stack
        } // while

        reverseCrateStacks(stacks);

        //input.useDelimiter("[^0-9]");

        // Moving crates
        while (input.hasNext()) {
            input.next();
            int count = input.nextInt();
            input.next();
            int from = input.nextInt();
            from--; // 0 indexed
            input.next();
            int to = input.nextInt();
            to--; // 0 indexed

            Stack<Character> temp = new Stack<>();
            while (count >= 1) {
                char movingCrate = stacks.get(from).pop();
                temp.push(movingCrate);
                count--;
            } // while
            while (!temp.isEmpty()) {
                stacks.get(to).push(temp.pop());
            } // while
        } // while

        String res = "";
        for (Stack<Character> stack : stacks) {
            res += stack.pop();
        } // for stack
        System.out.println(res);
    } // dayFiveTwo()

    static void daySixOne() {
        int res = 0;
        String line;
        line = input.nextLine();

        int[] counts = new int[26];
        int uniqueChars = 0;
        // sliding window
        for (int i = 0; i < line.length(); i++) {
            // add to the count of the char at p
            counts[line.charAt(i) - 'a']++;
            // if the count goes to 1, increment the # of unique chars
            if (counts[line.charAt(i) - 'a'] == 1) {
                uniqueChars++;
            } // if
            // subtract from the count of a char 4 behind p
            if (i > 3) { // counts the initial 4 chars
                counts[line.charAt(i - 4) - 'a']--;
                // if the count goes to 0, decrement the # of unique chars
                if (counts[line.charAt(i - 4) - 'a'] == 0) {
                    uniqueChars--;
                } // if
            } // if
            if (uniqueChars == 4) {
                res = i + 1;
                break;
            } // if
        } // for i


        System.out.println(res);
    } // daySixOne()

    static void daySixTwo() {
        int markerSize = 14;
        
        int res = 0;
        String line;
        line = input.nextLine();

        int[] counts = new int[26];
        int uniqueChars = 0;
        // sliding window
        for (int i = 0; i < line.length(); i++) {
            // add to the count of the char at p
            counts[line.charAt(i) - 'a']++;
            // if the count goes to 1, increment the # of unique chars
            if (counts[line.charAt(i) - 'a'] == 1) {
                uniqueChars++;
            } // if
            // subtract from the count of a char 4 behind p
            if (i >= markerSize) { // counts the initial chars
                counts[line.charAt(i - markerSize) - 'a']--;
                // if the count goes to 0, decrement the # of unique chars
                if (counts[line.charAt(i - markerSize) - 'a'] == 0) {
                    uniqueChars--;
                } // if
            } // if
            if (uniqueChars == markerSize) {
                res = i + 1;
                break;
            } // if
        } // for i


        System.out.println(res);
    } // daySixTwo()

    static void daySevenOne() {
        Scanner in = input;
        Dir root = new Dir();
        Dir cur = root;
        in.nextLine();
        while (in.hasNextLine()) {
            String line = in.nextLine();
            String[] tokens = line.split(" ");
            //System.out.println(line);
            if (tokens.length == 3 && tokens[1].equals("cd")) { // if $ cd
                //System.out.println("Going to dir: " + tokens[2]);
                if (tokens[2].equals("..")) {
                    cur = cur.parent;
                } else {
                    cur = cur.getSubdir(tokens[2]);
                } // if
            } else if (tokens.length == 2 && tokens[1].equals("ls")) { // if $ ls
                continue; // ignore
            } else { // not $ cd or $ ls i.e. an ls printout
                if (tokens.length < 2) {
                    continue;
                } else if (tokens[0].equals("dir")) { // a dir
                    cur.addSubdir(tokens[1]);
                } else { // a file
                    cur.addFile(Integer.parseInt(tokens[0]), tokens[1]);
                } // if dir or file
            } // if linetype
        } // while

        IntegerPointer res = new IntegerPointer();
        root.size(res);
        System.out.println("7-1: " + res.get());

        int spaceUsed = root.size;
        int freeSpace = 70_000_000 - spaceUsed;
        int needToDelete = 30_000_000 - freeSpace;
        //System.out.println(needToDelete);
        int s = root.smallestDirBiggerThan(needToDelete);
        System.out.println("7-2: " + s);
    } // day7()

    static class Dir {
        Dir parent;
        Map<String, Dir> subdirs;
        Map<String, Integer> files;
        int size;
        boolean isRoot;

        Dir(Dir parent) {
            this.parent = parent;
            this.subdirs = new HashMap<>();
            this.files = new HashMap<>();
            this.size = 0;
            this.isRoot = false;
        } // Dir()

        Dir() {
            this.parent = parent;
            this.subdirs = new HashMap<>();
            this.files = new HashMap<>();
            this.size = 0;
            this.isRoot = true;
        } // Dir()

        void addSubdir(String subdir) {
            if (!subdirs.containsKey(subdir)) {
                subdirs.put(subdir, new Dir(this));
            } // if
        } // addSubdir()

        Dir getSubdir(String subdir) {
            if (subdirs.containsKey(subdir)) {
                return subdirs.get(subdir);
            } else {
                System.out.println("INVALID SUBDIR");
                return null;
            } // if
        } // getSubdir()

        void addFile(int size, String file) {
            files.put(file, size);
        } // addFile()

        boolean isLeaf() {
            return subdirs.size() == 0;
        } // isLeaf()

        int size(IntegerPointer ip) {
            for (String key : files.keySet()) {
                this.size += files.get(key);
            } // for key files

            for (String key : subdirs.keySet()) {
                int subdirSize = subdirs.get(key).size(ip);
                this.size += subdirSize;
            } // for key subdirs

            if (this.size <= 100_000) {
                ip.add(this.size);
            } // if

            return this.size;
        } // sizeOfDir()

        int smallestDirBiggerThan(int min) {
            //System.out.println(this.size);
            int res = Integer.MAX_VALUE;
            if (this.size >= min) {
                res = Math.min(res, this.size);
            } // if

            for (String key : subdirs.keySet()) {
                int subdirMinSize = subdirs.get(key).smallestDirBiggerThan(min);
                if (subdirMinSize >= min) {
                    res = Math.min(res, subdirMinSize);
                } // if
            } // for key subdirs

            return res;
        } // sizeOfDir()

    } // class Dir

    static class IntegerPointer {
        int n;

        IntegerPointer() {
            n = 0;
        } // IntegerPointer()

        void add(int n) {
            this.n += n;
        } // add

        int get() {
            return n;
        } // get()
    } // class IntegerPointer

    static void daySevenTwo() {
        int res = 0;
        String line;
        while (input.hasNextLine()) {
            line = input.nextLine();
        } // while
        System.out.println(res);
    } // daySevenTwo()

    static void dayEightOne() {
        //input = getInput("8-1-eg");
        String line; // for the scanner
        // build array of trees
        List<List<Integer>> trees = new ArrayList<>();
        int rows = 0;
        int cols = 0;
        while (input.hasNextLine()) {
            line = input.nextLine();
            cols = line.length();
            rows++;
            trees.add(new ArrayList<Integer>(cols));
            for (char tree : line.toCharArray()) {
                trees.get(rows - 1).add((Character.getNumericValue(tree)));
            } // for tree
        } // while

        boolean[][] visible = new boolean[rows][cols];

        int r0 = 0;
        int r$ = rows - 1;
        int c0 = 0;
        int c$ = cols - 1;
        int r = 0;
        int c = 0;
        int temp;

        for (int t = 0; t < 2; t++) { // transposed and not transposed
            int step = 1;
            for (int d = 0; d < 2; d++) { // direction
                //System.out.printf("t: %d, d: %d\n", t, d);
                // scan the trees from outside in 
                // any tree taller than the current taller is visible
                r = r0;
                while (r <= r$) {
                    int maxTree = -1;
                    c = c0;
                    while (c != c$ + step) {
                        if (t == 0) { // not transposed
                            if (trees.get(r).get(c) > maxTree) {
                                visible[r][c] = true;
                                maxTree = trees.get(r).get(c);
                            } // if
                        } else { // transposed
                            if (trees.get(c).get(r) > maxTree) {
                                visible[c][r] = true;
                                maxTree = trees.get(c).get(r);
                            } // if
                        } // if
                        //System.out.println(r + "" + c);
                        //System.out.println("c + step: " + (c$ + step));
                        c += step;
                        //System.out.println("c: " + c);
                    } // while c
                    r++;
                } // while r
                // swap the bgn, end and dir for cols
                temp = c0;
                c0 = c$;
                c$ = temp;
                step = -1;
            } // for d
            // 'transpose' the matrix (swap the rows and cols vars)
            temp = r0;
            r0 = c0;
            c0 = temp;
            temp = r$;
            r$ = c$;
            c$ = temp;
        } // for t

        int res = 0;
        for (r = 0; r < rows; r++) {
            for (c = 0; c < cols; c++) {
                res += visible[r][c] ? 1 : 0;
                System.out.print(visible[r][c] ? 1 : 0);
            } // for c
            System.out.println();
        } // for r

        System.out.println("res: " + res);
    } // dayEightOne()

    static void dayEightTwo() {
        //input = getInput("8-1-eg");
        // build array of trees
        List<List<Integer>> trees = new ArrayList<>();
        int rows = 0;
        int cols = 0;
        String line;
        while (input.hasNextLine()) {
            line = input.nextLine();
            cols = line.length();
            rows++;
            trees.add(new ArrayList<Integer>(cols));
            for (char tree : line.toCharArray()) {
                trees.get(rows - 1).add((Character.getNumericValue(tree)));
            } // for tree
        } // while

        int res = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                res = Math.max(res, scenicScore(trees, r, c));
                //System.out.printf("r: %d, c: %d, score: %d\n", r, c, res);
            } // for c
        } // for r

        System.out.println(res);
    } // dayEightTwo()

    // find the scenic score of a single tree
    static int scenicScore(List<List<Integer>> trees, int r, int c) {
        int score = 1;
        // get the score from each dir
        int[] dirs = new int[]{0, -1, 0, 1, 0};
        for (int dir = 1; dir < dirs.length && score != 0; dir++) {
            int ssod = scenicScoreOneDir(trees, r, c, dirs[dir - 1], dirs[dir], -1, trees.get(r).get(c));
            //System.out.println(ssod);
            score *= ssod;
        } // for dir
        return score;
    } // scenicScore()

    // find the scenic score of a single direction of a tree
    static int scenicScoreOneDir(List<List<Integer>> trees, int r, int c, int rdir, int cdir, int minHeight, int ogHeight) {
        r += rdir;
        c += cdir;
        int score = 0;
        if (r < 0 || trees.size() <= r || c < 0 || trees.get(0).size() <= c) { // out of bounds
            return 0;
        } else if (trees.get(r).get(c) >= ogHeight) { // tree is same height as og (max height)
            return 1; // the last tree it can see
            /*
        } else if (trees.get(r).get(c) < minHeight) { // cannot see tree
            score = 0;
            */
        } else { // can see tree
            score = 1;
        } // if
        minHeight = Math.max(minHeight, trees.get(r).get(c));
        return score + scenicScoreOneDir(trees, r, c, rdir, cdir, minHeight, ogHeight);
    } // scenicSCoreOneDir()

    static void dayNineOne() {
        Scanner input = getInput("./input/9-1.txt");
        
        int hr = 0;
        int hc = 0;
        int lasthr = 0;
        int lasthc = 0;

        int tr = 0;
        int tc = 0;

        Set<String> resSet = new HashSet<>();
        resSet.add(tr + "#" + tc);

        while (input.hasNextLine()) {
            String[] cmd = input.nextLine().split(" ");
            char dir = cmd[0].charAt(0);
            int steps = Integer.parseInt(cmd[1]);
            while (steps > 0) {
                lasthr = hr;
                lasthc = hc;
                switch (dir) {
                    case 'U' :
                        hr++;
                        break;
                    case 'D' :
                        hr--;
                       break;
                    case 'L' :
                        hc--;
                        break;
                    case 'R' :
                        hc++;
                        break;
                    default :
                } // switch

                if (!isAdjacent(hr, hc, tr, tc)) {
                    tr = lasthr;
                    tc = lasthc;
                    resSet.add(tr + "#" + tc);
                } // if
                steps--;
            } // while steps
        } // while ()

        System.out.println(resSet.size());
    } // dayNineOne()

    static boolean isAdjacent(int hr, int hc, int tr, int tc) {
        int[] dirs = new int[]{0, 1, 1, -1, 1, 0, 0, -1, -1, 0};
        for (int dir = 1; dir < dirs.length; dir++) {
            if (tr + dirs[dir] == hr && tc + dirs[dir - 1] == hc) {
                return true;
            } // if
        } // for dir
        return false;
    } // isAdjacent()

    static void dayNineTwo() {
        int ropelength = 10;
        Scanner input = getInput("./input/9-2.txt");

        int[] roper = new int[ropelength];
        int[] ropec = new int[ropelength];
        
        Set<String> resSet = new HashSet<>();
        resSet.add("0#0");

        while (input.hasNextLine()) {
            // get the dir and distance of the next input
            String[] cmd = input.nextLine().split(" ");
            char dir = cmd[0].charAt(0);
            int steps = Integer.parseInt(cmd[1]);
            while (steps > 0) {
                // move the head one step in the specified dir
                switch (dir) {
                    case 'U' :
                        roper[0]++;
                        break;
                    case 'D' :
                        roper[0]--;
                       break;
                    case 'L' :
                        ropec[0]--;
                        break;
                    case 'R' :
                        ropec[0]++;
                        break;
                } // switch

                for (int i = 1; i < ropelength; i++) { // for each segment of the rope
                    // if the preceeding segment is no longer adjacent
                    if (!isAdjacent(roper[i - 1], ropec[i - 1], roper[i], ropec[i])) {
                        // if the segments share a row or col, that dimension does not need to be changed hence 0
                        // if the rows and/or cols differ, the current segment needs to move towards the preceeding by 1
                        // the direction of that move is found by taking the difference in the preceeding and the current
                        int rdifference = roper[i - 1] - roper[i];
                        int rdir = rdifference == 0 ? 0 : rdifference > 0 ? 1 : -1;

                        int cdifference = ropec[i - 1] - ropec[i];
                        int cdir = cdifference == 0 ? 0 : cdifference > 0 ? 1 : -1;

                        roper[i] = roper[i] + rdir;
                        ropec[i] = ropec[i] + cdir;
                    } // if
                } // for i

                resSet.add(roper[ropelength - 1] + "#" + ropec[ropelength - 1]);

                steps--;
            } // while steps
        } // while ()

        System.out.println(resSet.size());
    } // dayNineTwo()

    static void printRope(int[] roper, int[] ropec) {
        int renderSize = 30;
        char[][] render = new char[renderSize][renderSize];
        for (int r = 0; r < renderSize; r++) {
            for (int c = 0; c < renderSize; c++) {
                render[r][c] = '.';
            } // for c
        } // for r
        for (int i = 0; i < roper.length; i++) {
            render[roper[i]][ropec[i]] = Character.forDigit(i, 10);
        } // for i
        
        for (int r = 0; r < renderSize; r++) {
            for (int c = 0; c < renderSize; c++) {
                System.out.print(render[r][c]);
            } // for c
            System.out.println();
        } // for r
        System.out.println("\n\n");
    } // printRope()

    static void dayTenOne() {
        //input = getInput("10-1-eg");
        int res = 0;
        int x = 1;
        int cycle = 1;
        String line;
        while (input.hasNextLine()) {
            line = input.nextLine();
            String[] tokens = line.split(" ");
            if (tokens[0].equals("noop")) { // noop intstuction
                cycle++;
                res += addInterestingSignal(cycle, x);
            } else { // addx instruction
                cycle++;
                res += addInterestingSignal(cycle, x);
                x += Integer.parseInt(tokens[1]);
                cycle++;
                res += addInterestingSignal(cycle, x);
            } // if
        } // while
        System.out.println(res);
    } // dayTenOne()

    static int addInterestingSignal(int cycle, int x) {
            if (cycle == 20 || ((cycle - 20) % 40) == 0) {
                return x * cycle;
            } // if
            return 0;
    } // addInterestingSignal()

    
    static void dayTenTwo() {
        //input = getInput("10-1-eg");
        int x = 1;
        int cycle = 1;
        String line;
        while (input.hasNextLine()) {
            line = input.nextLine();
            String[] tokens = line.split(" ");
            if (tokens[0].equals("noop")) { // noop 
                render(cycle, x);
                cycle++;
            } else { // addx 
                render(cycle, x);
                cycle++;
                render(cycle, x);
                x += Integer.parseInt(tokens[1]);
                cycle++;
            } // if
        } // while
        System.out.println();
    } // dayTenTwo()

    
    static void render(int cycle, int x) {
        int pos = (cycle - 1) % 40;
        if (pos == 0) { // new line
            System.out.println();
        } // if
        if (x - 1 == pos || x == pos || x + 1 == pos) { // sprite on printing pixel
            System.out.print('#');
        } else {
            System.out.print('.');
        } // if
    } // render()

    static void dayElevenOne() {
        int res = 0;
        String line;
        while (input.hasNextLine()) {
            line = input.nextLine();
        } // while
        System.out.println(res);
    } // dayElevenOne()

    static void dayElevenTwo() {
        int res = 0;
        String line;
        while (input.hasNextLine()) {
            line = input.nextLine();
        } // while
        System.out.println(res);
    } // dayElevenTwo()

    static void dayTwelveOne() {
        int res = 0;
        String line;
        while (input.hasNextLine()) {
            line = input.nextLine();
        } // while
        System.out.println(res);
    } // dayTwelveOne()

    static void dayTwelveTwo() {
        int res = 0;
        String line;
        while (input.hasNextLine()) {
            line = input.nextLine();
        } // while
        System.out.println(res);
    } // dayTwelveTwo()

    static void dayThirteenOne() {
        int res = 0;
        String line;
        while (input.hasNextLine()) {
            line = input.nextLine();
        } // while
        System.out.println(res);
    } // dayThirteenOne()

    static void dayThirteenTwo() {
        int res = 0;
        String line;
        while (input.hasNextLine()) {
            line = input.nextLine();
        } // while
        System.out.println(res);
    } // dayThirteenTwo()

    static void dayFourteenOne() {
        int res = 0;
        String line;
        while (input.hasNextLine()) {
            line = input.nextLine();
        } // while
        System.out.println(res);
    } // dayFourteenOne()

    static void dayFourteenTwo() {
        int res = 0;
        String line;
        while (input.hasNextLine()) {
            line = input.nextLine();
        } // while
        System.out.println(res);
    } // dayFourteenTwo()

    static void dayFifteenOne() {
        int res = 0;
        String line;
        while (input.hasNextLine()) {
            line = input.nextLine();
        } // while
        System.out.println(res);
    } // dayFifteenOne()

    static void dayFifteenTwo() {
        int res = 0;
        String line;
        while (input.hasNextLine()) {
            line = input.nextLine();
        } // while
        System.out.println(res);
    } // dayFifteenTwo()

    static void daySixteenOne() {
        int res = 0;
        String line;
        while (input.hasNextLine()) {
            line = input.nextLine();
        } // while
        System.out.println(res);
    } // daySixteenOne()

    static void daySixteenTwo() {
        int res = 0;
        String line;
        while (input.hasNextLine()) {
            line = input.nextLine();
        } // while
        System.out.println(res);
    } // daySixteenTwo()

    static void daySeventeenOne() {
        int res = 0;
        String line;
        while (input.hasNextLine()) {
            line = input.nextLine();
        } // while
        System.out.println(res);
    } // daySeventeenOne()

    static void daySeventeenTwo() {
        int res = 0;
        String line;
        while (input.hasNextLine()) {
            line = input.nextLine();
        } // while
        System.out.println(res);
    } // daySeventeenTwo()

    static void dayEighteenOne() {
        int res = 0;
        String line;
        while (input.hasNextLine()) {
            line = input.nextLine();
        } // while
        System.out.println(res);
    } // dayEighteenOne()

    static void dayEighteenTwo() {
        int res = 0;
        String line;
        while (input.hasNextLine()) {
            line = input.nextLine();
        } // while
        System.out.println(res);
    } // dayEighteenTwo()

    static void dayNineteenOne() {
        int res = 0;
        String line;
        while (input.hasNextLine()) {
            line = input.nextLine();
        } // while
        System.out.println(res);
    } // dayNineteenOne()

    static void dayNineteenTwo() {
        int res = 0;
        String line;
        while (input.hasNextLine()) {
            line = input.nextLine();
        } // while
        System.out.println(res);
    } // dayNineteenTwo()

    static void dayTwentyOne() {
        int res = 0;
        String line;
        while (input.hasNextLine()) {
            line = input.nextLine();
        } // while
        System.out.println(res);
    } // dayTwentyOne()

    static void dayTwentyTwo() {
        int res = 0;
        String line;
        while (input.hasNextLine()) {
            line = input.nextLine();
        } // while
        System.out.println(res);
    } // dayTwentyTwo()

    static void dayTwentyoneOne() {
        int res = 0;
        String line;
        while (input.hasNextLine()) {
            line = input.nextLine();
        } // while
        System.out.println(res);
    } // dayTwentyoneOne()

    static void dayTwentyoneTwo() {
        int res = 0;
        String line;
        while (input.hasNextLine()) {
            line = input.nextLine();
        } // while
        System.out.println(res);
    } // dayTwentyoneTwo()

    static void dayTwentytwoOne() {
        int res = 0;
        String line;
        while (input.hasNextLine()) {
            line = input.nextLine();
        } // while
        System.out.println(res);
    } // dayTwentytwoOne()

    static void dayTwentytwoTwo() {
        int res = 0;
        String line;
        while (input.hasNextLine()) {
            line = input.nextLine();
        } // while
        System.out.println(res);
    } // dayTwentytwoTwo()

    static void dayTwentythreeOne() {
        int res = 0;
        String line;
        while (input.hasNextLine()) {
            line = input.nextLine();
        } // while
        System.out.println(res);
    } // dayTwentythreeOne()

    static void dayTwentythreeTwo() {
        int res = 0;
        String line;
        while (input.hasNextLine()) {
            line = input.nextLine();
        } // while
        System.out.println(res);
    } // dayTwentythreeTwo()

    static void dayTwentyfourOne() {
        int res = 0;
        String line;
        while (input.hasNextLine()) {
            line = input.nextLine();
        } // while
        System.out.println(res);
    } // dayTwentyfourOne()

    static void dayTwentyfourTwo() {
        int res = 0;
        String line;
        while (input.hasNextLine()) {
            line = input.nextLine();
        } // while
        System.out.println(res);
    } // dayTwentyfourTwo()

    static void dayTwentyfiveOne() {
        int res = 0;
        String line;
        while (input.hasNextLine()) {
            line = input.nextLine();
        } // while
        System.out.println(res);
    } // dayTwentyfiveOne()

    static void dayTwentyfiveTwo() {
        int res = 0;
        String line;
        while (input.hasNextLine()) {
            line = input.nextLine();
        } // while
        System.out.println(res);
    } // dayTwentyfiveTwo()

} // class AdventOfCode2022
