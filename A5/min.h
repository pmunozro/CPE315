#ifndef MIN_H
#define MIN_H

#include "super.h"
#include "partition.h"
#include "inode.h"
#include "minls.h"

void build(Disk disk, FILE *fp, int *partOffset, Superblock *super);
void fileFinder(FILE *fp, Superblock *super, int *partOffset, Disk disk);

#endif
