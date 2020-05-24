#include <stdio.h>

int main()
{
   char *blk = (char*) malloc(1);
   *blk = 'C';
   putchar(*blk);
   free(blk);
   putchar(*blk);
   return 0;
}
