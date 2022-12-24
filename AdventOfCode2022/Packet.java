import java.util.List;
import java.util.ArrayList;

// everything is a packet; numbers are packets with just an int
class Packet implements Comparable<Packet> {
    static boolean debug = false;

    boolean isInt; // isPrimitive?
    int intData;
    List<Packet> listData;

    Packet() {
        this.isInt = false;
        this.intData = 0;
        this.listData = new ArrayList<>();
    } // Packet()
    
    Packet(Packet p) { // new packet with only p as its child
        this.isInt = false;
        listData = new ArrayList<>();
        this.add(p);
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
        if (Packet.debug) System.out.println("text: " + packetText);
        int n = 0;
        char c = packetText.charAt(index);
        while (c != ',' && Character.isDigit(c)) {
            n *= 10;
            n += Character.getNumericValue(c);
            index++;
            c = packetText.charAt(index);
            if (Packet.debug) System.out.println("c: " + c);
        } // while
        return n;
    } // parseInt()

    public String toString() {
        if (this.isInt) {
            return "" + this.intData + ",";
        } // if
        String res = "[";
        for (Packet p : listData) {
            if (p.isInt) {
                res += p.intData + ",";
            } else {
                res += p.toString();
            } // if
        } // for Packet
        res += "]";
        return res;
    } // toString()

    public static int inOrder(Packet l, Packet r) {
        // -1 not, 0 neither, 1 is
        for (int i = 0; i < l.size() && i < r.size(); i++) {
            if (l.get(i).isInt || r.get(i).isInt) {
                int res = compareInt(l, r, i);
                if (res != 0) {
                    return res;
                } // if
            } else { // both lists
                int res = inOrder(l.get(i), r.get(i));
                if (res != 0) {
                    return res;
                } // if
            } // if
        } // for i
        // no determining matches were found
        return l.size() < r.size() ? 1 : -1;
    } // inOrder()

    public static int compareIntX(Packet l, Packet r, int i) {
        if (l.get(i).isInt && r.get(i).isInt) {
            if (l.get(i).intData < r.get(i).intData) {
                return 1;
            } else if (l.get(i).intData > r.get(i).intData) {
                return -1;
            } // else ==, move on
        } else if (l.get(i).isInt) {
            if (r.get(i).size() == 0) {
                return -1;
            } // if
            Packet p = r.get(i);
            while (!p.isInt && p.size() != 0) {
                p = p.get(0);
            } // while
            if (p.size() == 0) {
                return -1;
            } else {
                if (l.get(i).intData < p.intData) {
                    return 1;
                } else if (l.get(i).intData > p.intData) {
                    return -1;
                } // if
            } // if
        } else if (r.get(i).isInt) {
            if (l.get(i).size() == 0) {
                return 1;
            } // if
            Packet p = l.get(i);
            while (!p.isInt && p.size() != 0) {
                p = p.get(0);
            } // while
            if (p.size() == 0) {
                return -1;
            } else {
                if (r.get(i).intData < p.intData) {
                    return -1;
                } else if (r.get(i).intData > p.intData) {
                    return 1;
                } // if
            } // if
        } // if
        return 0;
    } // compareInt()

    public static int compareInt(Packet l, Packet r, int i) {
        l = l.get(i);
        r = r.get(i);

        if (l.isInt && r.isInt) {
            if (l.intData < r.intData) {
                return 1;
            } else if (l.intData > r.intData) {
                return -1;
            } else { // else ==
                return 0;
            } 
        } else if (l.isInt) {
            if (r.size() == 0) {
                return -1;
            } // if
            Packet p = r;
            while (!p.isInt && p.size() != 0) {
                p = p.get(0);
            } // while
            if (!p.isInt && p.size() == 0) {
                return -1;
            } else if (p.isInt) {
                if (l.intData < p.intData) {
                    return 1;
                } else if (l.intData > p.intData) {
                    return -1;
                } // if
            } // if
        } else if (r.isInt) {
            if (l.size() == 0) {
                return 1;
            } // if
            Packet p = l;
            while (!p.isInt && p.size() != 0) {
                p = p.get(0);
            } // while
            if (!p.isInt && p.size() == 0) {
                return 1;
            } else if (p.isInt) {
                if (r.intData < p.intData) {
                    return -1;
                } else if (r.intData > p.intData) {
                    return 1;
                } // if
            } // if
        } // if
        return 0;
    } // compareInt()

    public static int inOrder2(Packet l, Packet r) {
        if (Packet.debug) System.out.println("inOrder2");
        // compare packets item by item
        for (int i = 0; i < l.size() && i < r.size(); i++) {
            if (Packet.debug) System.out.println("in for");
            if (l.get(i).isInt || r.get(i).isInt) { // one of them is an int
                if (Packet.debug) System.out.println("l || r");
                int res = compareInt2(l.get(i), r.get(i));
                if (res != 0) {
                    return res;
                } // if
            } else { // both are lists
                int res = inOrder2(l.get(i), r.get(i));
                if (res != 0) {
                    return res;
                } // if
            } // if
        } // for i
        // if left runs out first, in order
        if (l.size() < r.size()) {
            return 1;
        } else if (l.size() > r.size()) {
            return -1;
        } else { // == 
            return 0;
        } // if
    } // inOrder()

    public static int compareInt2(Packet l, Packet r) {
        if (Packet.debug) System.out.println("compareInt2");
        if (l.isInt && r.isInt) { // both
            if (Packet.debug) System.out.println("both");
            if (Packet.debug) System.out.println("l < r" + (l.intData < r.intData));
            if (l.intData < r.intData) {
                return 1;
            } else if (l.intData > r.intData) {
                return -1;
            } else {
                return 0;
            } // if
        } else if (l.isInt) { // l is, r isnt
            if (Packet.debug) System.out.println("l");
            Packet p = new Packet(l);
            if (Packet.debug) System.out.println(p);
            if (Packet.debug) System.out.println(r);
            int res = inOrder2(p, r);
            if (res != 0) {
                return res;
            } // if
        } else { // l isnt, r is
            if (Packet.debug) System.out.println("r");
            Packet p = new Packet(r);
            int res = inOrder2(l, p);
            if (res != 0) {
                return res;
            } // if
        } // if
        return 0;
    } // compareInt2()

    public int compareTo(Packet other) {
        return inOrder2(other, this);
    } // compareTo()

    public boolean equalTo(Packet other) {
        if (this.size() != other.size()) {
            if (Packet.debug) System.out.println("size failed");
            return false;
        } // if

        for (int i = 0; i < this.size() && i < other.size(); i++) {
            // both must be the same type
            if (this.get(i).isInt ^ other.get(i).isInt) {
                if (Packet.debug) System.out.println("^ failed");
                return false;
            } else if (this.get(i).isInt && other.get(i).isInt) {
                if (this.get(i).intData != other.get(i).intData) {
                    if (Packet.debug) System.out.println("int failed");
                    return false;
                } // if
            } else { // both lists
                if (!this.get(i).equalTo(other.get(i))) {
                    if (Packet.debug) System.out.println("recursive failed");
                    return false;
                } // if
            } // if
        } // for i
        return true;
    } // equalTo()


} // class
