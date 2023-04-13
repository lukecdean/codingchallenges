#define push(sp, n) (*((sp)++)=(n))
#define pop(sp) (*--(sp))

// 83/70 @ 5ms 
bool validateStackSequences(int* pushed, int pushedSize, int* popped, int poppedSize){
    int *stack = (int *) malloc(sizeof(int) * pushedSize);
    int *sp = stack;

    int pshptr = 0;
    int popptr = 0;

    bool res = true;
    
    while (pshptr <= pushedSize && popptr < poppedSize) {
        if (pshptr < pushedSize && sp == stack) {
            // stack is empty
            push(sp, pushed[pshptr]);
            pshptr++;
        } else if (popped[popptr] == *(sp - 1)) {
            // if the current popped is on the top of the stack
            pop(sp);
            popptr++;
        } else if (pshptr < pushedSize) {
            // top of stack does not match current popped
            push(sp, pushed[pshptr]);
            pshptr++;
        } else {
            // popped could not be created
            res = false;
            break;
        } // if
    } // while

    free(stack);
    return res;
} // vSS()
