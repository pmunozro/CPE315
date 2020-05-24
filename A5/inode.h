#ifndef INODE_H
#define INODE_H
#define DIRECT_ZONES 7
#include <stdint.h>

typedef struct
{
   uint16_t mode;
   uint16_t links;
   uint16_t uid;
   uint16_t gid;
   uint32_t size;
   int32_t  atime;
   int32_t  mtime;
   int32_t  ctime;
   uint32_t zone[DIRECT_ZONES];
   uint32_t indirect;
   uint32_t two_indirect;
   uint32_t unused;
}Inode;

#endif
