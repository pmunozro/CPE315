#include <stdio.h>
#include "lwp.h"
#include "fp.h"
#include <stdlib.h>
#define next_t lib_one
#define prev_t lib_two

void rr_remove(thread victim);
void rr_admit(thread new);
thread rr_next();

static struct scheduler rr_sched = {NULL, NULL, rr_admit, rr_remove, rr_next};
scheduler RR = &rr_sched; /* setting up default Round Robin scheduler */
static context *originalContext = NULL; /* pointer to Original Context */
static void *returnSP;
static context *threadRunning = NULL; /* pointer to thread that is running */
static context *threadHead = NULL; /* head for thread linked list */
/*static thread schedHead = NULL;*/ /* head of scheduler linked list */
static tid_t threadID = 1; /* Count for total threads */

extern tid_t lwp_create(lwpfun func,void *args,size_t stacksize)
{
   thread temp = threadHead;

   /* starting new linked list else add to existing linked list */
   if(!threadHead)
   {
      temp = malloc(sizeof(context));
      if(!temp)
         return -1;
      temp->next_t = NULL;
      temp->prev_t = NULL;
      threadHead = temp;
   }
   else
   {
      while(temp->next_t)
         temp = temp->next_t;
      temp->next_t = malloc(sizeof(context));
      if(!(temp->next_t))
         return -1;
      temp->next_t->prev_t = temp;
      temp = temp->next_t;
      temp->next_t = NULL;
   }
   
   /* set tid to threadID and increment threadID */
   temp->tid = threadID++;
   /* allocate stacksize and set it to stack */
   //temp->stack = malloc(stacksize * sizeof(unsigned long));
   if(!temp->stack)
      return -1;
   /* save the stacksize for the thread */
   temp->stacksize = stacksize;

   RR->admit(temp);

   return temp->tid;
}

extern void lwp_exit()
{
   thread next = NULL;
   /* remove current running thread */
   RR->remove(threadRunning);
   /* retrieve next thread in scheduler */
   next = RR->next();
   /*free(threadRunning->stack);*/
   free(threadRunning->stack);

   if(!next)
   {
      /* call lwp_stop since no more threads */
      threadRunning = NULL;
      //lwp_stop();
   }
   else
   {
      /* runs next thread */
      threadRunning = next;
      load_context(&(threadRunning->state));
   }
}

/* return current thread's id */
extern tid_t lwp_gettid()
{
   if(threadRunning)
      return threadRunning->tid;
   else
      return NO_THREAD;
}

extern void lwp_yield()
{
   save_context(&(threadRunning->state));
   threadRunning = RR->next();
   if(threadRunning)
      load_context(&(threadRunning->state));
   else
      load_context(&(originalContext->state));
}

extern void lwp_start()
{
   if(threadID == 1)
      return;
   save_context(&(originalContext->state));
   threadRunning = RR->next();
   load_context(&(originalContext->state));
}

extern void lwp_stop()
{
   if(threadRunning)
      save_context(&(threadRunning->state));
   load_context(&(originalContext->state));
}

/*Transfer all old threads from old to new scheudler*/
extern void lwp_set_scheduler(scheduler fun)
{
   thread temp = RR->next();
   if(!temp)
   {
      /*library should return to RoundRobin scheduling*/
      return;
   }
   /*Go through all the threads of round robin*/
   while(RR->next())
   {
      RR->remove(temp);/*remove thread from old scheduler*/
      fun->admit(temp);/*admit thread into new scheduler*/
   }
   /*shutdown old scheduler*/
   RR->shutdown();
   RR = fun;
}

/*Return current scheduler*/
extern scheduler lwp_get_scheduler()
{
   return RR;
}

/* find an id match */
extern thread tid2thread(tid_t tid)
{
   thread searching = RR->next();
   if(tid < 1 || tid > threadID)
      return NULL;
   while(searching)
   {
      if(searching->tid == tid)
         return searching;
      searching = searching->next_t;
   }
   /* if no id match return NULL */
   return NULL;
}

void rr_init()
{
}

void rr_shutdown()
{
}

void rr_admit(thread new)
{
   /* if no head of thread linked list exists create the head */
   if(!(RR->next()))
   {
      threadHead = new;
   }
}

/*Remove thread from old scheduler*/
void rr_remove(thread victim)
{
   if(!victim)
      return;
   else
   {
      if(victim->next_t)
      {
         /*set current victim NULL and set victim to the next thread*/
         victim = victim->next_t;
         victim->prev_t = NULL;
      }
      else
      {
         victim = NULL;
      }
   }
}

/*Returns the next thread to be run or NULL if there isn't */
thread rr_next()
{
   /* if head of thread linked list is empty return NULL */
   if(!threadHead)
      return NULL;
   else
      return threadHead;
}
