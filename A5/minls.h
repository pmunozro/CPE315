#ifndef MINLS_H
#define MINLS_H

#include <stdint.h>
typedef struct
{
   int verbose;
   int part, partnum;
   int sub, subnum;
   char *image;
   char *path;
}Disk;

typedef struct
{
   uint32_t inode;
   unsigned char name[60];
}Directory;

#endif
