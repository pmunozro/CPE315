#include <stdio.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <libgen.h>
#include <signal.h>

void error()
{
   perror(NULL);
   exit(EXIT_FAILURE);
}

int main()
{
   int fd[2];
   int i, d = 0;
   int status;
   pid_t pid;
   pipe(fd);
   if((pid = fork()) < 0)
      error();
   else if(pid == 0)
   {
      close(3);
      dup2(4, STDOUT_FILENO);
      close(4);
      if(execlp("ls", "ls", (char*) NULL) < 0)
         fprintf(stderr, "Error");
   }
   else
   {
      if((pid = fork()) < 0)
         error();
      else if(pid == 0)
      {
         close(4);
         dup2(3, STDIN_FILENO);
         close(3);
         if((d = open("outfile", O_WRONLY | O_CREAT | O_TRUNC, 0666)) == -1)
            fprintf(stderr, "Error");
         dup2(d, STDOUT_FILENO);
         close(d);
         if(execlp("sort", "sort", "-r", (char*) NULL) < 0)
            fprintf(stderr, "Error");
      }
      close(4);
      close(3);
      while(i < 1)
      {
         wait(&status);
         i++;
      }
   }
   return 0;
}
