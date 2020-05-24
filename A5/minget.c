#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "min.h"

void show_Usage()
{
   printf("usage: minget  [ -v ] [ -p num [ -s num ] ] imagefile minixpath");
   printf(" [ hostpath ]\n");
   printf("Options:\n");
   printf("        -p   \tpart    --- select partition for filesystem ");
   printf("(default: none)\n");
   printf("        -s   \tsub     --- select subpartition for filesystem");
   printf(" (default: none)\n");
   printf("        -h   \thelp    --- print usage information and exit\n");
   printf("        -v   \tverbose --- increase verbosity level\n");
   exit(EXIT_FAILURE);
}

void check(Disk *disk, int *i, char *argv[])
{
   if(strcmp(argv[*i], "-v") == 0)
      disk->verbose = 1;
   else if(strcmp(argv[*i], "-p") == 0)
   {
      disk->part = 1;
      (*i)++;
      disk->partnum = atoi(argv[*i]);
   }
   else if(strcmp(argv[*i], "-s") == 0)
   {
      disk->sub = 1;
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

void error(int num, char *partition)
{
   printf("%s %d out of range.  Must be 0..3.\n", partition, num);
   show_Usage();
}

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
      check(&disk, &i, argv);
   if(disk.partnum < 0 || disk.partnum > 4)
      error(disk.partnum, "Partition");
   if(disk.subnum < 0 || disk.subnum > 4)
      error(disk.subnum, "Subpartition");
   return disk;
}

int main(int argc, char *argv[])
{
   Disk disk;
   FILE *fp = NULL;
   int partOffset = 0;
   Superblock super;
   if(argc == 1)
      show_Usage();
   else
   {
      disk = setDisk(argc, argv);
      if((fp = fopen(disk.image, "r")) == NULL)
      {
         fprintf(stderr, "Could not open image\n");
         exit(EXIT_FAILURE);
      }
      build(disk, fp, &partOffset, &super);
      fileFinder(fp, &super, &partOffset, disk);
      fclose(fp);
   }
   return 0;
}
