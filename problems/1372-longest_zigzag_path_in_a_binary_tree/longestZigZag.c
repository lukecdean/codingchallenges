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

// 85/46 @ 113 ms
int longestZigZag(struct TreeNode* root){
    return dfs(root, -1, 0);
}

// dir: 0 left, 1 right
int dfs(struct TreeNode* root, int dir, int zigzaglen) {
    if (root == NULL ) {
        // subtract 1 since this is not actually a node
        return zigzaglen - 1;
    } // if

    // go left
    // if same dir as from parent, reset len
    int zzleft = dfs(root->left, 0, 
            dir == LEFT ? 1 : (zigzaglen + 1));

    // go right
    // if same dir as from parent, reset len
    int zzright = dfs(root->right, 1,
            dir == RIGHT ? 1 : (zigzaglen + 1));

    return zzleft > zzright ? zzleft : zzright;
} // dfs()
