CC = gcc
CFLAGS = -Wall -pedantic -g -ansi -D_BSD_SOURCE

all: pipeit

pipeit: pipeit.o
		$(CC) $(CFLAGS) -o pipeit pipeit.o

pipeit.o: pipeit.c
		$(CC) $(CFLAGS) -c -o pipeit.o pipeit.c 

clean:
		@echo "Cleaning any existing intermediate .o files..."
		@rm -f *.o
		@echo "All done!"
