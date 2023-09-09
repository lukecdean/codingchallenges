/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    // 100/75 @ 1ms
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0) {
            return null;
        } // if
        return mergeKLists(lists, 0, lists.length - 1);
    }

    private ListNode mergeKLists(ListNode[] lists, int l, int r) {
        int listSetSize = r - l + 1;
        switch (listSetSize) {
            case 0: // may not happen TODO
            case 1:
                // base case: l == r
                return lists[l];
            case 2:
                // base case: down to 2 lists to merge
                return merge2Lists(lists[l], lists[r]);
            default:
                // recursive case
                int midpoint = (l + r) / 2;
                ListNode a = mergeKLists(lists, l, midpoint);
                ListNode b = mergeKLists(lists, midpoint + 1, r);
                return merge2Lists(a, b);
        } // switch
    } // mergeKLists()

    private ListNode merge2Lists(ListNode a, ListNode b) {
        ListNode mergedHead = new ListNode();
        ListNode mergedCurr = mergedHead;
        while ((a != null) && (b != null)) {
            if (a.val <= b.val) {
                mergedCurr.next = a;
                a = a.next;
            } else {
                mergedCurr.next = b;
                b = b.next;
            } // if
            mergedCurr = mergedCurr.next;
        } // while

        // if one of the lists is now empty
        if (a == null) {
            mergedCurr.next = b;
        } else {
            mergedCurr.next = a;
        } // if

        return mergedHead.next;
    } // merge2Lists()
}
