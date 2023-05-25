/**
 * Definition for a binary tree node.
 * struct TreeNode {
 *     int val;
 *     struct TreeNode *left;
 *     struct TreeNode *right;
 * };
 */
struct nodeq {
    int capacity;
    int size;
    struct TreeNode *q;
}

struct nodeq q = {.capacity = 32, .size = 0, )};

static void enq(struct TreeNode *node) {
    if (q.size == q.capacity) {
        realloc(q.q, sizeof(nodepos) * q.capacity * 2);
        q.capacity *= 2;
    } // if
    q.q[size] = node;
    q.size++;
} // enq

static struct TreeNode * deq() {
    q.size--;
    return q.q[size];
} // deq()

int widthOfBinaryTree(struct TreeNode* root){
    // init queue
    //q.capacity = 32;
    //q.size = 0;
    //q.q = malloc(sizeof(nodepos) * 32);

    // bfs tree noting the pos of each node
    TreeNode *curr = root;

    curr->val = 0;
    enq(curr);

    int maxwidth = 1;
    int levelnodes;
    int levelwidth;

    while (q.size > 0) {
        levelnodes = q.size;
        levelwidth = q.q[q.size - 1].val - q.q[0].val + 1;
        maxwidth = maxwidth < levelwidth ? levelwidth : maxwidth;
        while (levelnodes > 0) {
            // dequeue
            curr = deq();

            // enqueue left and right children if they exist
            if (curr->left != NULL) {
                curr->left->val = curr->val * 2;
                enq(curr->left);
            } // if
            if (curr->right != NULL) {
                curr->right->val = curr->val * 2 + 1;
                enq(curr->right);
            } // if

            levelnodes--;
        } // while
    } // while

    return maxwidth;

    free(q.q);
} // wOBT()


