#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "minls.h"
#include "min.h"

/*Print usage statement if no arguments or -h flag*/
void show_Usage()
{
   printf("usage: minls  [ -v ] [ -p num [ -s num ] ] imagefile [ path ]\n");
   printf("Options:\n");
   printf("        -p \t part    --- select partition for filesystem ");
   printf("(default: none)\n");
   printf("        -s \t sub     --- select subpartition for filesystem");
   printf(" (default: none)\n");
   printf("        -h \t help    --- print usage information and exit\n");
   printf("        -v \t verbose --- increase verbosity level\n");
   exit(EXIT_FAILURE);
}
/*Check commandline for any flags or imagefiles*/
void check(Disk *disk, int *i, char *argv[])
{
   if(strcmp(argv[*i], "-v") == 0)
      disk->verbose = 1;/*Set verbose flag high*/
   else if(strcmp(argv[*i], "-p") == 0)
   {
      disk->part = 1;/*set partition flag high*/
      (*i)++;
      disk->partnum = atoi(argv[*i]);
   }
   else if(strcmp(argv[*i], "-s") == 0)
   {
      disk->sub = 1;/*set subpartition high*/
      (*i)++;
      disk->subnum = atoi(argv[*i]);
   }
   else if(strcmp(argv[*i], "-h") == 0)
      show_Usage();
   else if(disk->image == NULL)
      disk->image = argv[*i];
   else
      disk->path = argv[*i];
}
/*Print out of bounds range for partition or subpartition value*/
void error(int num, char *partition)
{
   printf("%s %d out of range.  Must be 0..3.\n", partition, num);
   show_Usage();
}
/*Initialize the disk structure*/
Disk setDisk(int argc, char *argv[])
{
   Disk disk;
   int i = 0;
   disk.verbose = 0;
   disk.part = disk.partnum = 0;
   disk.sub = disk.subnum = 0;
   disk.image = NULL;
   disk.path = "/";
   while(++i < argc)
   {
      check(&disk, &i, argv);
   }
   /*Check for out of bounds*/
   if(disk.partnum < 0 || disk.partnum > 4)
      error(disk.partnum, "Partition");
   if(disk.subnum < 0 || disk.subnum > 4)
      error(disk.subnum, "Subpartition");
   return disk;
}

int main (int argc, char *argv[])
{
   Disk disk;
   FILE *fp = NULL;
   int partOffset = 0;
   Superblock super;
   if(argc == 1)
      show_Usage();
   else
   {
      disk = setDisk(argc, argv);/*Initialize disk structure*/
      if((fp = fopen(disk.image, "r")) == NULL)
      {
         printf("disk.image = %s\n", disk.image);
         printf("Could not open image\n");
         exit(EXIT_FAILURE);
      }
      build(disk, fp, &partOffset, &super);/**Build the superblock*/
      fileFinder(fp, &super, &partOffset, disk);/*Begin looking for path file*/
      fclose(fp);/*Close imagefile*/
   }
   return 0;
}
