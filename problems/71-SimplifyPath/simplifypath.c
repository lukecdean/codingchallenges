#include <string.h>
#include <stdlib.h>

// 36/72 @ 6ms
char * simplifyPath(char * path){
    int pathLen = strlen(path);
    char *res = (char *) malloc(pathLen + 1);
    int curResLen = 0;

    int pathPtr = 0;
    while (pathPtr < pathLen) {
        if (path[pathPtr] == '/') {
            if (curResLen == 0
                    || (curResLen > 1 && res[curResLen - 1] != '/')
               ) {
                res[curResLen] = '/';
                curResLen++;
            } // if

            // remove '/'s
            while (pathPtr < pathLen && path[pathPtr] == '/') {
                pathPtr++;
            } // while
        } else if (path[pathPtr] == '.') {
            if (pathPtr + 1 < pathLen && path[pathPtr + 1] != '/') {
                if ((pathPtr + 1 < pathLen && path[pathPtr + 1] != '.')
                        || (pathPtr + 2 < pathLen && path[pathPtr + 2] != '/')) {
                    // if dir is '...' or more, treat as a dir name
                    // or any other format of periods
                    while (pathPtr < pathLen && path[pathPtr] != '/') {
                        res[curResLen] = path[pathPtr];
                        curResLen++;
                        pathPtr++;
                    } // while
                } else { // is '..'
                    // 'go up a dir' ie move curResLen back to previous '/'
                    if (curResLen > 1) {
                        // remove most recently copied '/' but not the root '/'
                        curResLen--;
                    } // if

                    // remove the current dir
                    while (curResLen > 1 && res[curResLen - 1] != '/') {
                        curResLen--;
                    } // while

                    if (curResLen > 1) { // remove the '/' for this dir
                        curResLen--;
                    } // if

                    // move down the path
                    pathPtr += 2; // '..' len is 2
                } // if
            } else { // is '.'
                // just ignore this dir
                pathPtr += 1; // '.' len is 1
                if (curResLen > 1) { // remove the '/' for this dir
                    curResLen--;
                } // if
            } // if
        } else {
            // this is just a dir so add it to the res
            while (pathPtr < pathLen && path[pathPtr] != '/') {
                res[curResLen] = path[pathPtr];
                curResLen++;
                pathPtr++;
            } // while
        } // if

        //res[curResLen] = '\0';
        //printf("%s\n", res);
    } // while

    // if a trailing '/' is left
    if (curResLen > 1 && res[curResLen - 1] == '/') {
        curResLen--;
    } // if

    res[curResLen] = '\0';

    return res;
} // simplifyPath()
