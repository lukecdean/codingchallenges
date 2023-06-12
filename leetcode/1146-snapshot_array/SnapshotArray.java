// 54/97 @ 67ms
class SnapshotArray {
    // each index will have its own list associated with it
    // these lists will contain entries: int[2] = [snap, value]
    // since the entries will be added in order,
    // the list may be binary searched to find the value at a snap

    List<List<int[]>> snapshotArray;
    int snap;

    public SnapshotArray(int length) {
        this.snapshotArray = new ArrayList<>(length);
        this.snap = 0;
        for (int i = 0; i < length; i++) {
            // init each list with a [snap = 0,val = 0]
            this.snapshotArray.add(i, new ArrayList<int[]>());
            this.snapshotArray.get(i).add(new int[] {0, 0});
        } // for i
    }
    
    public void set(int index, int val) {
        List<int[]> listAtIndex = this.snapshotArray.get(index);
        
        // the last entry in the list will be the most recent snap/value
        if (listAtIndex.get(listAtIndex.size() - 1)[0] == snap) {
            // if there is already an entry for the current snap, update it
            listAtIndex.get(listAtIndex.size() - 1)[1] = val;
        } else {
            // else there is no entry for the current snap so make one
            listAtIndex.add(new int[] {snap, val});
        } // if
    }
    
    public int snap() {
       this.snap++; 
       return this.snap - 1;
    }
    
    public int get(int index, int snap_id) {
        List<int[]> listAtIndex = this.snapshotArray.get(index);
        
        // binary search for the desired snap_id
        // or the snap immediatly before it
        int l = 0;
        int r = listAtIndex.size();
        int m;
        while (l < r) {
            m = 1 + (l + r) / 2;
            if (listAtIndex.get(m)[0] == snap_id) {
                return listAtIndex.get(m)[1];
            } else if (listAtIndex.get(m)[0] > snap_id) {
                r = m - 1;
            } else { // < snap_id
                l = m;
            } // if
        } // while
        return listAtIndex.get(l)[1];
    }
}
