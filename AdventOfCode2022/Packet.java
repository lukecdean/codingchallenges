import java.util.List;
import java.util.ArrayList;

// everything is a packet; numbers are packets with just an int
class Packet {
    boolean isInt; // isPrimitive?
    int intData;
    List<Packet> listData;

    Packet() {
        this.isInt = false;
        this.intData = 0;
        this.listData = new ArrayList<>();
    } // Packet()
    
    Packet(String packet) {
        this.isInt = false;
        this.intData = 0;
        this.listData = new ArrayList<>();

        int index = 1;
        while (index < packet.length() - 1) {
            char c = packet.charAt(index);
            switch (c) {
                // no ']' case with proper input
                case ',': // separator; do nothing
                    index++;
                    break;
                case '[': // create subpacket
                    index = subpacket(this, packet, index + 1);
                    break;
                default: // number
                    int n = parseInt(packet, index);
                    Packet p = new Packet(n);
                    this.add(p);
                    // skip past the number
                    while (Character.isDigit(packet.charAt(index))) {
                        index++;
                    } // while

            } // switch
        } // while
    } // Packet()

    // returns the index after this subpacket
    // index should be the first char inside the child packet
    // returns the index of the first char after the child packet
    int subpacket(Packet parent, String packetText, int index) {
        Packet child = new Packet();
        while (index < packetText.length() - 1) {
            char c = packetText.charAt(index);
            switch (c) {
                case ',': // do nothing
                    index++;
                    break;
                case '[': // new subpacket
                    index = subpacket(child, packetText, index + 1);
                    break;
                case ']': // end of subpacket
                    parent.add(child);
                    return index + 1;
                default: // number
                    int n = parseInt(packetText, index);
                    Packet p = new Packet(n);
                    child.add(p);
                    // skip past the number
                    while (Character.isDigit(packetText.charAt(index))) {
                        index++;
                    } // while
                    break;
            } // switch
        } // while
        // should return before execution reaches here
        System.err.println("########### how did i get here #########");
        return -1;
    } // subpacket()

    Packet(String packet, int index) {
        listData = new ArrayList<Packet>();
        int subpacketDepth = 0;
        while (index < packet.length()) {
            while (subpacketDepth > 0) { // skip the subpacket
                if (packet.charAt(index) == ']') {
                    subpacketDepth--;
                } // if
                index++;
            } // while subpacketDepth

            if (packet.charAt(index) == '[') { // start of new list
                listData.add(new Packet(packet, index + 1));
                subpacketDepth++;
            } else if (packet.charAt(index) == ',') { // value separator
                // continue
            } else { // is digit

            } // if

            index++;
        } // while
    } // Packet()

    Packet(int n) {
        isInt = true;
        intData = n;
    } // Packet

    void add(Packet child) {
        if (this.listData == null) {
            this.listData = new ArrayList<>();
        } // if
        this.listData.add(child);
    } // add()

    public Packet get(int i) {
        //return this.isInt ? intData : this.listData.get(i)
        return this.listData.get(i);
    } // get()

    public int size() {
        if (this.isInt) {
            return 0;
        } else {
            return listData.size();
        } // if
    } // size()

    int parseInt(String packetText, int index) {
    // converts the number starting at the index to an int
        int n = 0;
        char c = packetText.charAt(index);
        while (Character.isDigit(c)) {
            n *= 10;
            n += Character.getNumericValue(c);
            index++;
            c = packetText.charAt(index);
        } // while
        return n;
    } // parseInt()

    public String toString() {
        if (this.isInt) {
            return "" + this.intData;
        } // if
        String res = "[";
        for (Packet p : listData) {
            if (p.isInt) {
                res += p.intData;
            } else {
                res += p.toString();
            } // if
        } // for Packet
        res += "]";
        return res;
    } // toString()

    public static boolean inOrder(Packet l, Packet r) {
        boolean res = false;;
        if (l.isInt && r.isInt) { // both ints
            res = l.intData <= r.intData;
        } else { // not ints
            for (int i = 0; i < l.size() && i < r.size(); i++) {
                //System.out.println("iterating");
                //System.out.println("comparing: " + l + r);
                if (l.get(i).isInt && r.get(i).isInt) {
                    if (l.get(i).intData < r.get(i).intData) {
                        return true;
                    } else if (l.get(i).intData > r.get(i).intData) {
                        return false;
                    } // if
                    // if ==, move on
                } else if (!inOrder(l.get(i), r.get(i))) {
                    //System.out.println("in if");
                    return false;
                } // if
            } // for i
            // if the for loop concluded, sizes are == as well as vals
            // left should be smaller
            if (l.size() == r.size()) {
                res = true;
            } else { 
                res = l.size() < r.size();
            } // if
        } // if
        return res;
    } // inOrder()

} // class
