// 33/7 @ 80ms
class Skiplist {

    Node head;
    int size;
    int layers;
    double coinOdds = 0.5;

    public Skiplist() {
        head = null;
        size = 0;
        layers = 0;
    }
    
    public boolean search(int target) {
        Node curr = head;
        while (curr != null) {
            // move through the current layer
            while (curr.next != null && curr.next.val <= target) {
                curr = curr.next;
            } // while

            // check curr for the target else go down a layer
            if (curr.val == target) {
                return true;
            } else {
                curr = curr.down;
            } // if
        } // while
        return false;
    } // search()
    
    public void add(int num) {
        // if there is no head
        if (head == null) {
            head = new Node(num, null, null);
            size = 1;
        System.out.print(toString()); // debug
            return;
        } // if

        // if need to make a new layer
        if (needsNewLayer()) {
            addNewLayer();
        } // if

        // if the new number is less than the current head value
        if (num < head.val) {
            addNewHead(num);
            System.out.print(toString()); // debug
            size++;
            return;
        } // if
        
        // else add normally
        // stack up the nodes in each layer after which num may be placed
        Stack<Node> layerStack = new Stack<>();
        Node curr = head;
        while (curr != null) {
            // find the largest smaller node (than num) in the current layer
            while (curr.next != null && curr.next.val <= num) {
                curr = curr.next;
            } // while
            layerStack.push(curr);
            curr = curr.down;
        } // while curr != null

        // now start adding in the new val to the skiplist
        do {
            Node down = curr; // curr will be null leaving the while loop
                              // so, the bottom node will have a null down
            curr = layerStack.pop();
            Node newNum = new Node(num, curr.next, down);
            curr.next = newNum;
        } while (!layerStack.isEmpty() && (Math.random() < coinOdds));

        size++;
        System.out.print(toString()); // debug
    } // add()

    private void addNewHead(int num) {
        int oldHeadVal = head.val;
        // replace old head with new num
        replaceHeadVals(num);
        // add old head val instead of new num
        add(oldHeadVal);
        size++;
    } // addNewHead()

    // replace all of the values in the head nodes with the new val
    private void replaceHeadVals(int num) {
        int oldHeadVal = head.val;
        Node curr = head;
        while (curr != null) {
            curr.val = num;
            curr = curr.down;
        } // while
    } // replaceHeadVals()
    
    private boolean needsNewLayer() {
        return (layers < (Math.log(size) / Math.log(1.0 / coinOdds)));
    } // needsNewLayer()

    private void addNewLayer() {
        Node newHead = new Node(head.val, null, head);
        this.head = newHead;
        this.layers++;
    } // addNewLayer()


    public boolean erase(int num) {
        boolean res = false;
        if (head == null) { // or size == 0
            res = false;
        } else if (num == head.val) {
            if (size == 1) { // shortcut
                head = null;
                res = true;
            } else {
                // else replace the head with the second val and delete second
                int newHeadVal = secondElementVal(); // find second element
                replaceHeadVals(newHeadVal); // replace head
                res = eraseLaterElement(newHeadVal); // erase second element
            } // if
        } else {
            // else num is not the head so try to erase it
            res = eraseLaterElement(num);
        } // if

        // see if there are too many layers
        if (tooManyLayers()) {
            removeLayer();
        } // if

        System.out.print(toString()); // debug
        return res;
    } // erase()

    // if head is target, that case is handled so only check second and later
    private boolean eraseLaterElement(int num) {
        boolean res = false;
        Node curr = head;
        while (curr != null) {
            // find where num should be the next node
            while (curr.next != null && curr.next.val < num) {
                curr = curr.next;
            } // while

            // see if num is actually the next node
            if (curr.next != null && curr.next.val == num) {
                // next is the target num
                res = true;
                curr.next = curr.next.next; // cut out num
            } // if

            // go down a layer
            curr = curr.down;
        } // while
        return res;
    } // eraseLaterElement()

    // gets the second
    private int secondElementVal() {
        Node curr = head;
        while (curr.down != null) { // go to the bottom
            curr = curr.down;
        } // while
        return curr.next.val;
    } // secondElementVal();

    private void removeHead() {
        if (size == 1) { // if head is the only element
            head = null;
            return;
        } // if

        // else go down the layers then build up the next element
        Stack<Node> layerStack = new Stack<>();
        Node curr = head;
        while (curr != null) {
            layerStack.push(curr);
            curr = curr.down;
        } // while
        curr = layerStack.pop(); // the bottom head node
        int newHeadVal = curr.next.val;
    } // removeHead()

    private boolean tooManyLayers() {
        return !needsNewLayer(); 
    } // tooManyLayers()

    private void removeLayer() {
        if (layers == 1) { // safety check
            return;
        } // if
        head = head.down; // removing the top layer is simple
        layers--;
    } // removeLayer()

    /*
    public String toString() {
        printsl();
        return "";
        String s = "===" + "Size: " + size + "===\n";
        if (size == 0) {
            return s;
        } // if
        int[] elements = new int[size];
        Node curr = head;
        while (curr.down != null) {
            curr = curr.down;
        } // while
        for (int i = 0; i < size; i++) {
            elements[i] = curr.val;
            curr = curr.next;
        } // for i

        curr = head;
        for (int l = 0; l < layers; l++) {
            Node currcurr = head;
            for (int i = 0; i < elements.length && currcurr != null; i++) {
                if (currcurr.val == elements[i]) {
                    s += elements[i];
                    currcurr = currcurr.next;
                } else {
                    s += ":.:";
                } // if
            } // for i
            curr = curr.down;
            s += "\n";
        } // for l
        return s;
    } // toString()
    //

    private void printsl() {
        System.out.println("size: " + size + "=========");
        Node curr = head;
        while (curr != null) {
            Node currcurr = curr;
            while (currcurr != null) {
                System.out.print(currcurr.val  + " ");
                currcurr = currcurr.next;
            } // while
            curr = curr.down;
            System.out.println();
        } // while
        System.out.println("==================");
    } // printsl()
    */
} // class Skiplist

class Node {
    int val;
    Node next;
    Node down;

    Node(int val, Node next, Node down) {
        this.val = val;
        this.next = next;
        this.down = down;
    } // Node()
} // class Node
