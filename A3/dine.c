#include <stdio.h>
#include <errno.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>
#include <semaphore.h>
#include "dine.h"

/* Grab argument 2 and set as number of cyles 
 * going through EAT THINK CHANGE, else default to 1 */
static int cycles = 1;
//sem_t mutex;

int main(int argc, char *argv[])
{
   int i;
   int id[NUM_PHILOSOPHERS];
   pthread_t phil[NUM_PHILOSOPHERS];
   /* Find the number of cylces else stay at 1 */
   if(argc > 1)
      sscanf(argv[1], " %d", &cycles);
   /* Initialize the semaphores */
   sem_init(&mutex, 0, 1);
   print_start();
   
   for(i = 0; i < NUM_PHILOSOPHERS; i++)
   {
      id[i] = i;
      sem_init(&s[i], 0, 1);
      state[i] = CHANGE;
   }
   int philosopher[2];
   philosopher[0] = id[0];
   philosopher[1] = cycles;
   for(i = 0; i < NUM_PHILOSOPHERS; i++)
   {
      philosopher[0] = id[i];
      pthread_create(&phil[i], NULL, State, (void*)philosopher);
      state[i] = THINK;
      print_state(id[i]);
   }
   for(i = 0; i < NUM_PHILOSOPHERS; i++)
   {
      pthread_join(phil[i], NULL);
   }
   print_end();
   return 0;
}
