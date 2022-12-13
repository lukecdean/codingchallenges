import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.io.File;
import java.util.Arrays;

class AdventOfCode2022 {

    static Scanner input;

    public static void main(String[] args) {
        input = getInput("input/" + args[0] + ".txt");
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
                //dayThreeOne();
                break;
            case ("3-2"):
                //dayThreeTwo();
                break;
            case ("4-1"):
                //dayFourOne();
                break;
            case ("4-2"):
                //dayFourTwo();
                break;
            case ("5-1"):
                //dayFiveOne();
                break;
            case ("5-2"):
                //dayFiveTwo();
                break;
            case ("6-1"):
                //daySixOne();
                break;
            case ("6-2"):
                //daySixTwo();
                break;
            case ("7-1") :
                //daySevenOne();
                break;
            case ("7-2"):
                //daySevenTwo();
                break;
            case ("8-1"):
                //dayEightOne();
                break;
            case ("8-2"):
                //dayEightTwo();
                break;
            case ("9-1") :
                //dayNineOne();
                break;
            case ("9-2") :
                //dayNineTwo();
                break;
        } // switch
    } // main()

    // getInput()
    static Scanner getInput(String filePath) {
        try {
            Scanner input;
            input = new Scanner(new File(filePath));
            return input;
        } catch (Exception e) {
            System.out.println(e);
            System.exit(1);
        } // try
        return null;
    } // getInput()

    static void dayX() {
        return;
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
        int elf;
        int you;
        String line;
        while (input.hasNextLine()) {
            line = input.nextLine();
            elf = line.charAt(0) - 'A';
            you = line.charAt(2) - 'X';
            //System.out.printf("e: %d, y: %d\n", elf, you);

            for (int i = 0; i <= 2; i++) {
                if (((you + 4 - i) % 3) == elf) {
                    score += i * 3;
                    //System.out.println("you == elf");
                } // if
            } // for i

            score += you + 1;
            //System.out.println(score);
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

    // --- Day 7: No Space Left On Device ---
    static void daySevenOne() {
        Scanner in;
        try {
            in = new Scanner(new File("./input/7-1.txt"));
        } catch (Exception e) {
            System.out.println(e);
            return;
        } // try
        Dir root = new Dir();
        Dir cur = root;
        while (in.hasNextLine()) {
            String[] tokens = in.nextLine().split(" ");
            System.out.println("iter");
            if (tokens.length == 3 && tokens[1].equals("cd")) { // if $ cd
                cur = cur.getSubdir(tokens[2]);
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
        System.out.println(res.get());
    } // day7()

    static class Dir {
        Dir parent;
        Map<String, Dir> subdirs;
        Map<String, Integer> files;
        int size;

        Dir(Dir parent) {
            this.parent = parent;
            this.subdirs = new HashMap<>();
            this.files = new HashMap<>();
            this.size = 0;
        } // Dir()

        Dir() {
            this.parent = parent;
            this.subdirs = new HashMap<>();
            this.files = new HashMap<>();
            this.size = 0;
        } // Dir()

        void addSubdir(String subdir) {
            if (!subdirs.containsKey(subdir)) {
                subdirs.put(subdir, new Dir());
            } // if
        } // addSubdir()

        Dir getSubdir(String subdir) {
            if (subdirs.containsKey(subdir)) {
                return subdirs.get(subdir);
            } else {
                System.out.println("oops");
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
                size += subdirSize;
            } // for key subdirs

            if (this.size <= 100_000) {
                ip.add(this.size);
            } // if
            return this.size;
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

} // class AdventOfCode2022
