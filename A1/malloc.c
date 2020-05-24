#include <stdio.h>
#include <string.h>
#include <errno.h>
#include <unistd.h>

/*global variable for head node*/
void *head = NULL;
/*struct node*/
typedef struct Node
{
   struct Node *next;
   int free;
   size_t size;
}Node;
/*Header Size*/
#define HSize sizeof(struct Node)
/*declares and intitalizes the Node*/
struct Node *setup_Node(struct Node *temp, size_t size, struct Node *last)
{
   if(last != NULL)
      last->next = temp;
   temp->free = 0;
   temp->size = size;
   temp->next = NULL;
   return temp;
}
/*Looks for free space within linked-list*/
struct Node* free_space(struct Node **last, size_t size)
{
   struct Node* current = head;
   while(current)
   {
      if(!(current->free && current->size >= size))
      {
         *last = current;
         current = current->next;
      }
      else
         break;
   }
   return current;
}
/*sets errno to ENOMEM*/
void *error()
{
   errno = ENOMEM;
   return NULL;
}
/*allocates space by calling sbrk creates a Node*/
void *malloc(size_t size)
{
   struct Node *temp;
   struct Node *last;
   int total_size = size + HSize;
   if(size <= 0)
      error();
   if(total_size%16 != 0)
      total_size += total_size%16;
   if(head == NULL)
   {
      /*creating first Node*/
      temp = sbrk(0);
      if((temp = sbrk(total_size)) == (void*)-1)
         error();
      temp = setup_Node(temp, size, NULL);
      head = temp;
   }
   else
   {
      /*adding another Node*/
      last = head;
      temp = free_space(&last, size);
      if(temp == NULL)
      {
         temp = sbrk(0);
         if((temp = sbrk(total_size)) == (void*)-1)
            error();
         setup_Node(temp, size, last);
      }
      else
         temp->free = 0;
   }
   return (temp+1);
}
void *calloc(size_t nmemb, size_t size)
{
   size_t nsize = nmemb*size;
   void *ptr = malloc(nsize);
   memset(ptr, 0, nsize);
   return ptr;
}
/*Sets a Node's space to free*/
void free(void *ptr)
{
   struct Node *temp;
   struct Node *ptr1 = ptr;
   if(!ptr)
      return;
   temp = ptr1 - 1;
   temp->free = 1;
}
/*reallocates the size of a specific Node*/
void *realloc(void *ptr, size_t size)
{
   struct Node *ptr1 = ptr;
   void *new;
   if(!ptr)
      return malloc(size);
   else if(size == 0)
      if(ptr != NULL)
         free(ptr);
   if(ptr1->size >= size)
      return ptr;
   new = malloc(size);
   memcpy(new, ptr, ptr1->size);
   free(ptr);
   return new;
}
