#ifndef DINE_H
#define DINE_H

#ifndef NUM_PHILOSOPHERS
#define NUM_PHILOSOPHERS 5
#endif

#include <semaphore.h>
#define LEFT (phil+4)%5
#define RIGHT (phil+1)%5
#define CHANGE 0
#define EAT 1
#define THINK 2

sem_t mutex;
sem_t s[5];
static int state[5];

void print_start();
void print_end();
void print_state(int num);
void *State(void* philosopher);
void dawdle();

#endif
