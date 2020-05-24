#ifndef SUPER_H
#define SUPER_H

#include <stdint.h>

typedef struct
{
   uint32_t ninodes;
   uint16_t pad1;
   int16_t  i_blocks;
   int16_t  z_blocks;
   uint16_t firstdata;
   int16_t  log_zone_size;
   int16_t  pad2;
   uint32_t max_file;
   uint32_t zones;
   int16_t  magic;
   int16_t  pad3;
   uint16_t blocksize;
   uint8_t  subversion;
}Superblock;

#endif
