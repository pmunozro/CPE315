#include <stdio.h>
#include <stdlib.h>

/* The hello file prints 'Hello, world!' and exits with a zero exit status
 * indicating success. If printf fails then exit with an EXIT_FAILURE.
 */

int main()
{
   if(printf("Hello, world!\n") > 0)
      exit(0);
   fprintf(stderr, "printf failed\n");
   exit(EXIT_FAILURE);
}
