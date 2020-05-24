CC	= gcc
CFLAGS	= -Wall
LDFLAGS	= -lpthread -lrt

dine: dine.o phil.o
	$(CC) $(CFLAGS) $(LDFLAGS) -o dine dine.o dawdle.c phil.o

dine.o: dine.h
	$(CC) $(CFLAGS) -c dine.c

phil.o: dine.h
	$(CC) $(CFLAGS) -c phil.c

clean:
	rm -f dine dine.o phil.o
