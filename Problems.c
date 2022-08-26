// 62. Unique Paths
//
int uniquePaths(int m, int n){

} // uniquePathes()


unsigned long long choose(int p, int q) {

} // choose()
unsigned long long factorial(int n) {
    if (n == 0) return 1;
    unsigned long long nn = n;
    unsigned long long res = 1;
    return nn * factorial(n - 1);
} // factorial()
int uniquePaths(int m, int n){
    m--;
    n--;
    int p = m + n;
    int r = m > n ? m : n;
    unsigned long long denom = factorial(p - r);
    unsigned long long num = 1;
    while (p > r) {
        num *= p;
        p--;
    } // while
    return num / denom;
} // uniquePaths()

// 198. House Robber
// 100/34 I don't know how you could use less memory
int rob(int* nums, int numsSize){
    if (numsSize == 1) return nums[0];
    int nextnext = nums[0];
    int next = nums[1] > nextnext ? nums[1] : nextnext;
    int current = next > nextnext ? next : nextnext;
    for (int i = 2; i < numsSize; i++) {
        current = next > nextnext + nums[i] ? next : nextnext + nums[i];
        nextnext = next;
        next = current;
    } // for i
    return current;
} // rob()

// 338. Counting Bits
int* countBits(int n, int* returnSize){
    int* ans = calloc(n + 1, sizeof(int));
    for (int i = 0; i <= n; i++) {
        for (int b = 0; b < 32; b++) {
            ans[i] += (i >> b) && 1;
        } // for b
    } // for i
    return ans;
} // countBits()
int* countBitsMalloc(int n, int* returnSize){
    int* ans = malloc((1 + n) * sizeof(int));
    for (int i = 0; i <= n; i++) {
        ans[i] = 0;
    } // for i
    for (int i = 0; i <= n; i++) {
        for (int b = 0; b < 32; b++) {
            ans[i] += (i >> b) && 1;
        } // for b
    } // for i
    return ans;
} // countBits()
