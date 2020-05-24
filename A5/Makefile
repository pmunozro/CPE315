CC = gcc
CFLAGS = -Wall -pedantic -g

all: minls minget

minls: minls.o min.o
	$(CC) $(CFLAGS) -o minls minls.o min.o

minls.o: minls.c min.h
	$(CC) $(CFLAGS) -c -o minls.o minls.c

minget: minget.o min.o
	$(CC) $(CFLAGS) -o minget minget.o min.o

minlget.o: minget.c min.h
	$(CC) $(CFLAGS) -c -o minget.o minget.c

min.o: min.c min.h
	$(CC) $(CFLAGS) -c -o min.o min.c

test_minls: minls
	@echo "Testing mush..."
	@./minls
	@echo "Testing Complete!"

.PHONY: clean

clean:
	@echo "Cleaning any existing intermediate .o files..."
	@rm -f *.o
	@echo "All done!"
