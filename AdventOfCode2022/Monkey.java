import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;

class Monkey {
    int inspections;
    Queue<Integer> items;

    boolean oldIsOperand; // old is both operands
    boolean addsWorry; // else multiplies
    int operand;
    int testDivisor;
    int trueMonkey;
    int falseMonkey;

    public String toString() {
        String s = "Inpsections: " + inspections + "\n";
        s += "oldIsOperand: " + oldIsOperand + "\n";
        s += "addsWorry: " + addsWorry + "\n";
        s += "operand: " + operand + "\n";
        s += "testDivisor: " + testDivisor + "\n";
        s += "trueMonkey: " + trueMonkey + "\n";
        s += " falseMonkey: " + falseMonkey + "\n";
        s += "\n";
        return s;
    } // toString()

    Monkey(Scanner input) {
        input.nextLine(); // clear first line
        // get starting items
        String[] startingItems = input.nextLine().split(" ");
        //for (String s : startingItems) System.out.println(s);
        this.items = new LinkedList<>();
        for (int i = 4; i < startingItems.length - 1; i++) {
            String itemStr = startingItems[i];
            int itemStrLength = itemStr.length();
            itemStr = itemStr.substring(0, itemStrLength - 1);
            int item = Integer.parseInt(itemStr);
            this.items.offer(item);
        } // for i
        this.items.offer(Integer.parseInt(startingItems[startingItems.length - 1]));

        // get operation operator
        String[] operator = input.nextLine().split(" ");
        //for (String s : operator) System.out.println(s);
        this.addsWorry = operator[6].equals("+");
        this.oldIsOperand = operator[7].equals("old");
        this.operand = oldIsOperand ? 0 : Integer.parseInt(operator[7]);

        // get test
        String[] test = input.nextLine().split(" ");
        this.testDivisor = Integer.parseInt(test[5]);

        // get to monkeys
        String[] tm = input.nextLine().split(" ");
        this.trueMonkey = Integer.parseInt(tm[9]);
        String[] fm = input.nextLine().split(" ");
        this.falseMonkey = Integer.parseInt(fm[9]);
        this.inspections = 0;

        if (input.hasNextLine()) {
            input.nextLine(); // clear last line
        } // if
    } // Monkey()

    void takeTurn(Monkey[] monkeys) {
        while (!items.isEmpty()) {
            inspect(monkeys);
        } // while
    } // takeTurn()

    // inspect the next item in the monkey's list
    void inspect(Monkey[] monkeys) {
        int worryLevel = items.poll();

        if (this.oldIsOperand) { // uses old as both operands
            if (this.addsWorry) {
                worryLevel += worryLevel;
            } else {
                worryLevel *= worryLevel;
            } // if
        } else { // uses a value as an operand
            if (this.addsWorry) { // adds
                worryLevel += this.operand;
            } else { // multiplies
                worryLevel *= this.operand;
            } // if
        } // if

        worryLevel /= 3;

        int throwToMonkey = worryLevel % testDivisor == 0 ? trueMonkey : falseMonkey;
        monkeys[throwToMonkey].catchItem(worryLevel);
        
        this.inspections++;
    } // inspect()

    void takeTurn(Monkey[] monkeys, boolean worryDecreases) {
        while (!items.isEmpty()) {
            inspect(monkeys, false);
        } // while
    } // takeTurn()

    void inspect(Monkey[] monkeys, boolean worryDecreases) {
        int worryLevel = items.poll();

        if (this.oldIsOperand) { // uses old as both operands
            if (this.addsWorry) {
                worryLevel += worryLevel;
            } else {
                worryLevel *= worryLevel;
            } // if
        } else { // uses a value as an operand
            if (this.addsWorry) { // adds
                worryLevel += this.operand;
            } else { // multiplies
                worryLevel *= this.operand;
            } // if
        } // if

        // reduce worry level
        worryLevel %= cumulativeDivisor(monkeys);

        //worryLevel /= 3;

        int throwToMonkey = worryLevel % testDivisor == 0 ? trueMonkey : falseMonkey;
        monkeys[throwToMonkey].catchItem(worryLevel);
        
        this.inspections++;
    } // inspect()

    void catchItem(int worryLevel) {
        //worryLevel %= testDivisor;
        //worryLevel += worryLevel == 0 ? worryLevel : 0;
        items.offer(worryLevel);
    } // catchItem()

    int cumulativeDivisor(Monkey[] monkeys) {
        int cd = 1;
        for (Monkey m : monkeys) {
            cd *= m.testDivisor;
        } // for m
        return cd;
    } // cumulativeDivisor()
} // class
