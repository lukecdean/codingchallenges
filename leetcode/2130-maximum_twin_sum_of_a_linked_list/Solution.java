class Solution {
    // array approach 
    // 90/13 @ 5ms
    public int pairSum(ListNode head) {
        ListNode slow = head;
        ListNode fast = head.next; // n >= 2
        int nodesInHalf = 1;

        while (fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            nodesInHalf++;
        } // while

        ListNode curr = slow.next; // first second half node
        int[] vals = new int[nodesInHalf];
        nodesInHalf--;

        while (curr != null) {
            vals[nodesInHalf] = curr.val;
            nodesInHalf--;
            curr = curr.next;
        }

        curr = head;
        int valsPtr = 0;
        int maxPairSum = curr.val + vals[valsPtr];

        while (valsPtr < vals.length) {
            int currPairSum = curr.val + vals[valsPtr];
            if (maxPairSum < currPairSum) {
                maxPairSum = currPairSum;
            }
            valsPtr++;
            curr = curr.next;
        }
        
        return maxPairSum;
    }

    // stack approach
    // 7/70 @ 69ms
    public int pairSum(ListNode head) {
        Stack<Integer> nodeValStack = new Stack<>();
        ListNode slow = head;
        ListNode fast = head.next; // n >= 2

        nodeValStack.push(slow.val);

        while (fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            nodeValStack.push(slow.val);
        } // while

        ListNode curr = slow.next;
        int maxPairSum = curr.val + nodeValStack.peek();
        while (!nodeValStack.isEmpty()) {
            if (maxPairSum < curr.val + nodeValStack.peek()) {
                maxPairSum = curr.val + nodeValStack.peek();
            } // if

            curr = curr.next;
            nodeValStack.pop();
        } // while
        
        return maxPairSum;
    }
}
