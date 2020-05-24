#ifndef PARTITION_H
#define PARTITION_H

#define PARTITION_TABLE_OFFSET 0x1BE
#define MINIX_PARTITION_TYPE 0x81
#define BYTE_510 510
#define BYTE_511 511
#define BYTE_510_ADD 0x55
#define BYTE_511_ADD 0xAA
#define MINIX_MAGIC_NUMBER 0x4D5A
#define REVERSED_MINIX_MAGIC_NUMBER 0x5A4D
#define STANDARD_SIZE 64
#define SECTOR_SIZE 512

#define FILE_TYPE_MASK 0170000
#define REGULAR_FILE 0100000
#define DIRECTORY 0040000
#define OWNER_READ_PERMISSION 0000400
#define OWNER_WRITE_PERMISSION 0000200
#define OWNER_EXECUTE_PERMISSION 0000100
#define GROUP_READ_PERMISSION 0000040
#define GROUP_WRITE_PERMISSION 0000020
#define GROUP_EXECUTE_PERMISSION 0000010
#define OTHER_READ_PERMISSION 0000004
#define OTHER_WRITE_PERMISSION 0000002
#define OTHER_EXECUTE_PERMISSION 0000001

#include <stdint.h>

typedef struct
{
   uint8_t bootind;
   uint8_t start_head;
   uint8_t start_sec;
   uint8_t start_cyl;
   uint8_t type;
   uint8_t end_head;
   uint8_t end_sec;
   uint8_t end_cyl;
   uint32_t lFirst;
   uint32_t size;
}Partition;

#endif
