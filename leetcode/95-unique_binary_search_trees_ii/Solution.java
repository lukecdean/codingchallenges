/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
import java.util.List;
import java.util.ArrayList;
// 22/15 @ 2 ms
class Solution {
    public List<TreeNode> generateTrees(int n) {
        // create dp structure
        // bsts[i][j] => list of all BSTs with nodes [i, j]
        List<List<List<TreeNode>>> bsts = new ArrayList<>(n + 1);
        bsts.add(null); // zero node (dne)
        for (int i = 1; i <= n; i++) {
            //BSTs[i] = new List<TreeNode>[](n - i + 1);
            bsts.add(new ArrayList<List<TreeNode>>(n - i + 1 + 1));

            for (int filler = 0; filler < i; filler++) {
                bsts.get(i).add(null);
            } // for filler

            for (int j = i; j <= n; j++) {
                //BSTs[i][j] = new ArrayList<TreeNode>();
                bsts.get(i).add(j, new ArrayList<TreeNode>());
            } // for j
            bsts.get(i).get(i).add(new TreeNode(i));
        } // for i

        // tabulation: find all bsts of size 2 then 3 then ... n
        for (int bstsize = 2; bstsize <= n; bstsize++) {
            // pick a starting node in the range
            for (int l = 1; l <= n - bstsize + 1; l++) {
                int r = l + bstsize - 1;
                // each node in the range [l, r] (r = l - 1 + bstsize) will be a root
                // edge case: left most in range is root ie bsts with no left nodes
                for (TreeNode rightbst : bsts.get(l + 1).get(r)) {
                    TreeNode curr = new TreeNode(l, null, copy(rightbst));
                    bsts.get(l).get(r).add(curr);
                } // for each r bst

                // bsts with left and right nodes
                for (int root = l + 1; root < r; root++) {
                    // bsts with nodes in the range [l, root)
                    for (TreeNode leftbst : bsts.get(l).get(root - 1)) {
                        // bsts with nodes in the range (root, r]
                        for (TreeNode rightbst : bsts.get(root + 1).get(r)) {
                            TreeNode curr = new TreeNode(root, copy(leftbst), copy(rightbst));
                            bsts.get(l).get(r).add(curr);
                        } // for rightbst
                    } // for leftbst
                } // for root
                
                // edge case: rightmost in range is root ie bsts with no right nodes
                for (TreeNode leftbst : bsts.get(l).get(r - 1)) {
                    TreeNode curr = new TreeNode(r, copy(leftbst), null);
                    bsts.get(l).get(r).add(curr);
                } // for each r bst

                // need to try to work edge cases into the main loop, maybe with do while?
            } // for l
        } // for bstsize

        return bsts.get(1).get(n); // to compile
    } // generateTrees()
    
    TreeNode copy(TreeNode root) {
        // shouldn't need for this sol
        if (root == null) {
            return null;
        } // if

        TreeNode copy = new TreeNode(root.val, copy(root.left), copy(root.right));

        return copy;
        
        /*
        TreeNode copy = new TreeNode(root.val);

        if (root.left != null) {
            copy.left = copy(root.left);
        } // if
        if (root.right != null) {
            copy.right = copy(root.right);
        } // if

        return copy;
        */
    } // copy()
} // class
