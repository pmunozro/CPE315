#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <semaphore.h>
#include <pthread.h>
#include "dine.h"

static char forks[6][6] = {{"-----"},{"-----"},{"-----"},{"-----"},{"-----"}};

void print_outline()
{
   printf("|=============");
}

void print_philosopher(int i)
{
   printf("|      %c      ", i);
}

void print_end()
{
   int i = 5;
   while(i-- > 0)
      print_outline();
   printf("|\n");
}
void print_start()
{
   int i = 0;
   while(i++ < 5)
      print_outline();
   printf("|\n");
   i = 64;
   while(++i < 70)
      print_philosopher(i);
   printf("|\n");
   i = 0;
   while(i++ < 5)
      print_outline();
   printf("|\n");
}
void print_state(int phil)
{
   int i = -1;
   while(++i < 5)
   {
      if(state[i] == CHANGE)
         printf("|%s        ", forks[i]);
      else if(state[i] == EAT)
         printf("|%sEAT     ", forks[i]);
      else
         printf("|%sTHINK   ", forks[i]);
   }
   printf("|\n");
}
void tenedor(int phil)
{
   if(phil%2 == 1)
   {
      forks[phil][RIGHT] = (RIGHT + '0');
      forks[phil][phil] = (phil + '0');
   }
   else
   {
      forks[phil][LEFT] = (LEFT + '0');
      forks[phil][phil] = (phil + '0');
   }
}
void tenedor1(int phil)
{
   if(phil%2 == 0)
   {
      forks[phil][LEFT] = ('-');
      forks[phil][phil] = ('-');
   }
   else
   {
      forks[phil][RIGHT] = ('-');
      forks[phil][phil] = ('-');
   }
}
void lets_eat(int phil)
{
   if(state[phil] == CHANGE && state[LEFT] != EAT && state[RIGHT] != EAT)
   {
      state[phil] = EAT;
      sleep(2);
      print_state(phil);
      sem_post(&s[phil]);
   }
}
void pick_fork(int phil)
{
   sem_wait(&mutex);
   state[phil] = CHANGE;
   print_state(phil);
   lets_eat(phil);
   //tenedor(phil);
   sem_post(&mutex);
   sem_wait(&s[phil]);
   sleep(1);
}
void set_fork(int phil)
{
   sem_wait(&mutex);
   state[phil] = THINK;
   print_state(phil);
   //tenedor1(phil);
   lets_eat(LEFT);
   lets_eat(RIGHT);

   sem_post(&mutex);
}
void* State(void* philosopher)
{
   int cycles = ((int*)philosopher)[1];
   while(cycles-- > 0)
   {
      int i = ((int*)philosopher)[0];
      state[i] = CHANGE;
      sleep(1);
      pick_fork(i);
      sleep(0);
      set_fork(i);
      //state[i] = THINK;
   }
   pthread_exit(NULL);
}
