#define LEFT 0
#define RIGHT 1

/**
 * Definition for a binary tree node.
 * struct TreeNode {
 *     int val;
 *     struct TreeNode *left;
 *     struct TreeNode *right;
 * };
 */

// 77/46 @ 116 ms
int longestZigZag(struct TreeNode* root){
    return dfs(root, -1, 0);
}

// dir: 0 left, 1 right
int dfs(struct TreeNode* root, int dir, int zigzaglen) {
    if (root == NULL ) {
        // subtract 1 since this is not actually a node
        return zigzaglen - 1;
    } // if

    int zzleft = 0;
    int zzright = 0;

    // go left
    // if same dir as from parent, reset len
    zzleft = dfs(root->left, 0, 
            dir == LEFT ? 1 : (zigzaglen + 1));

    // go right
    // if same dir as from parent, reset len
    zzright = dfs(root->right, 1,
            dir == RIGHT ? 1 : (zigzaglen + 1));

    // find longest zigzag
    int longestzigzag = zigzaglen;
    if (zzleft > longestzigzag) {
        longestzigzag = zzleft;
    } // if
    if (zzright > longestzigzag) {
        longestzigzag = zzright;
    } // if

    return longestzigzag;
} // dfs()
