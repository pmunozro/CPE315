#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include "min.h"
#include "minls.h"
#include "partition.h"

void getInode(Inode *inode, FILE *fp, Superblock *super, int *Offset, int iNum);
/*Print the permission string stating whether a file is a direcctory or not
 * print the read, write, or execute permissions for owner, group, or other */
void printPermission(uint16_t mode, char *permiso)
{
   char string[] = "----------";

   if(mode & DIRECTORY){
      string[0] = 'd';}
   if(mode & OWNER_READ_PERMISSION){
      string[1] = 'r';}
   if(mode & OWNER_WRITE_PERMISSION){
      string[2] = 'w';}
   if(mode & OWNER_EXECUTE_PERMISSION){
      string[3] = 'x';}
   if(mode & GROUP_READ_PERMISSION){
      string[4] = 'r';}
   if(mode & GROUP_WRITE_PERMISSION){
      string[5] = 'w';}
   if(mode & GROUP_EXECUTE_PERMISSION){
      string[6] = 'x';}
   if(mode & OTHER_READ_PERMISSION){
      string[7] = 'r';}
   if(mode & OTHER_WRITE_PERMISSION){
      string[8] = 'w';}
   if(mode & OTHER_EXECUTE_PERMISSION){
      string[9] = 'x';}     
   strcpy(permiso, string);
}

void fileInfo(FILE *fp, Superblock *super, int *Offset, int iNum, char *fname)
{
   Inode inode;
   char permiso[11];
   getInode(&inode, fp, super, Offset, iNum);
   /*Get the permission string for each inode directory*/
   printPermission(inode.mode, permiso);
   /*Print the permission string, the inode size, and the filename*/
   printf("%s %9d %s\n", permiso, inode.size, fname);
}

void printDirectory(Inode *inode, FILE *fp, Superblock *super, int *partOffset)
{
   Inode temp;
   Directory *entry = malloc(inode->size);
   int directories, i = -1;
   int zoneFile = inode->zone[0] * super->blocksize;
   /*Get number of directories after inode size diveded by 
    * sizeof Directory*/
   directories = inode->size/sizeof(Directory);
   /*Move the file pointer to the correct offset to being reading 
    * the appropiate bytes*/
   fseek(fp, zoneFile + *partOffset, SEEK_SET);
   /*Read the appropiate number of bytes and set it to entry*/
   fread(entry, sizeof(Directory), directories, fp);

   while(++i < directories)
   {
      memset(&temp, 0, sizeof(Inode));
      if(entry[i].inode != 0)
         fileInfo(fp, super, partOffset, entry[i].inode, (char *)entry[i].name);
   }
   /*Free entry since memory has been allocated*/
   free(entry);
}
/*Print the Super Block structure*/
void printSuper(Superblock *super)
{
   int zoneSize = super->blocksize << super->log_zone_size;
   printf("\n");
   printf("Superblock Contents:\nStored Fields:\n");
   printf("  ninodes    %9d\n", super->ninodes);
   printf("  i_blocks   %9d\n", super->i_blocks);
   printf("  z_blocks   %9d\n", super->z_blocks);
   printf("  firstdata  %9d\n", super->firstdata);
   printf("  log_zone_size %6d",super->log_zone_size);
   printf(" (zone size: %d)\n", zoneSize);
   printf("  max_file  %10u\n", super->max_file);
   printf("  magic         0x%x\n", super->magic);
   printf("  zones      %9d\n", super->zones);
   printf("  blocksize  %9d\n", super->blocksize);
   printf("  subversion %9d\n", super->subversion);
}

/*Print the Inode structure and the Direct Zones*/
void printInode(Inode *inode)
{
   int i = -1;
   time_t tiempo;
   char permiso[11];
   time(&tiempo);
   printPermission(inode->mode, permiso);
   printf("\n");
   printf("File inode:\n");
   printf("  uint16_t mode            0x%x (%s)\n", inode->mode, permiso);
   printf("  uint16_t links %16d\n", inode->links);
   printf("  uint16_t uid   %16d\n", inode->uid);
   printf("  uint16_t gid   %16d\n", inode->gid);
   printf("  uint32_t size  %16lu\n", (long unsigned int) inode->size);
   printf("  uint32_t atime %16lu --- %s", (long unsigned int) inode->atime, 
      ctime(&tiempo));
   printf("  uint32_t mtime %16lu --- %s", (long unsigned int) inode->mtime, 
      ctime(&tiempo));
   printf("  uint32_t ctime %16lu --- %s", (long unsigned int) inode->ctime, 
      ctime(&tiempo));
   printf("\n");
   printf("  Direct zones:\n");
   while(++i < DIRECT_ZONES)
   {
      printf("              zone[%d]    = %10d\n", i, inode->zone[i]);
   }
   printf("  unit32_t    indirect %14d\n", inode->indirect);
   printf("  uint32_t    double %16d\n", inode->two_indirect);
}
/*Prints the header of the Partition Table*/
void printHeader()
{
   printf("\n");
   printf("Partition table:\n");
   printf("       ----Start----      ------End-----\n");
   printf("  Boot head  sec  cyl Type head  sec  cyl      First       Size\n");
}
/*Print the partition table for the 4 objects*/
void printPartition(Partition *part)
{
   printf("  0x%02X ", part->bootind);
   printf("%4d %4d %4d ", part->start_head, part->start_sec, part->start_cyl);
   printf("0x%02X ", part->type);
   printf("%4d %4d %4d ", part->end_head, part->end_sec, part->end_cyl);
   printf(" %9d  %9d\n", part->lFirst, part->size);
}
void getInode(Inode *inode, FILE *fp, Superblock *super, int *Offset, int iNum)
{
   int index, twoBlocks;
   int b_imap, b_zmap, b_inodes;
   index = iNum - 1;
   twoBlocks = 2 * super->blocksize;
   b_imap = super->i_blocks * super->blocksize;
   b_zmap = super->z_blocks * super->blocksize;
   b_inodes = index * sizeof(Inode);
   index = twoBlocks + b_imap + b_zmap + b_inodes;
   index += *Offset;
   fseek(fp, index + *Offset, SEEK_SET);
   fread(inode, sizeof(Inode), 1, fp);
}

void fileFinder(FILE *fp, Superblock *super, int *partOffset, Disk disk)
{
   Inode inode;
   int inodeNum = 1;
   /*Initialize inode structure for whaterever file passed*/
   getInode(&inode, fp, super, partOffset, inodeNum);
   /*Checks if the file passed is a Directory*/
   if((inode.mode & DIRECTORY) == 0)
   {
      if(strcmp(disk.path, "/") == 0)
      {
         if(disk.verbose)
            printInode(&inode);/*print Inode structure*/
         printf("/:\n");
         /*Print files in directory*/
         printDirectory(&inode, fp, super, partOffset);
      }
   }
   else
   {
      if(strcmp(disk.path, "/") == 0)
      {
         if(disk.verbose)
            printInode(&inode);/*print Inode structure*/
         printf("/:\n");
         /*Print files in directory*/
         printDirectory(&inode, fp, super, partOffset);
      }
   }
}
void buildPartition(Partition part[], FILE *fp, Disk disk, int *partOffset)
{
   int i = -1;
   uint8_t byte1[2]; 
   /*Move file pointer by 510 bytes*/
   fseek(fp, BYTE_510 + *partOffset, SEEK_SET);
   /*Read 1 byte*/
   fread(byte1, 1, 1, fp);
   /*Move file pointer by 511 bytes*/
   fseek(fp, BYTE_511 + *partOffset, SEEK_SET);
   /*Read 1 byte*/
   fread(byte1+1, 1, 1, fp);
   if(byte1[0] != BYTE_510_ADD || byte1[1] != BYTE_511_ADD)
   {
      printf("Invalid partition table.\n");
      printf("Unable to open disk image \"%s\".\n", disk.image);
      exit(EXIT_FAILURE);
   }
   /*Move the file pointer by the Partitioin table offset
    * and the partition offset */
   fseek(fp, PARTITION_TABLE_OFFSET + *partOffset, SEEK_SET);
   /*Read 4 bytes and save it as the partition offset*/
   fread(part, sizeof(Partition), 4, fp);
   /*If there is a verbose flag print the partition table*/
   if(disk.verbose)
   {
      printHeader();
      while(++i < 4)
         printPartition(part + i);
   }
}
/*Start building the partition table and the superblock structure*/
void build(Disk disk, FILE *fp, int *partOffset, Superblock *super)
{
   /*Create an array of 4 Partition table objects*/
   Partition partTable[4];
   if(disk.part)
   {
      /*Build partition table*/
      buildPartition(partTable, fp, disk, partOffset);
      if(partTable->type == MINIX_PARTITION_TYPE)
         *partOffset = partTable->lFirst * SECTOR_SIZE;
   }
   /*Move file pointer from the beginning of the file 
    * by 1024 plus partition offset bytes*/
   fseek(fp, *partOffset + 1024, SEEK_SET);
   /*Read 1 bytes into the super structure*/
   fread(super, sizeof(Superblock), 1, fp);
   /*Check if the current directory is not a MINIx filesystem*/
   if(super->magic != MINIX_MAGIC_NUMBER)
   {
      printf("Bad magic number. (0x%04X)\n", (unsigned int)super->magic);
      printf("This doesn't look like a MINIX filesystem.\n");
      exit(EXIT_FAILURE);
   }
   /*If a verbose flag is present then print the super block structure*/
   if(disk.verbose)
      printSuper(super);
}
