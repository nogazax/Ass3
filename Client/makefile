CFLAGS:=-c -Wall -Weffc++ -g -std=c++11 -Iinclude
LDFLAGS:=-lboost_system -pthread

all: BGRSClient

BGRSClient:bin/inputTaker.o bin/connectionHandler.o
	g++ -o bin/BGRSClient bin/connectionHandler.o bin/inputTaker.o $(LDFLAGS)
	
bin/connectionHandler.o: src/connectionHandler.cpp
	g++ $(CFLAGS) -o bin/connectionHandler.o src/connectionHandler.cpp

bin/inputTaker.o: src/inputTaker.cpp
	g++ $(CFLAGS) -o bin/inputTaker.o src/inputTaker.cpp


.PHONY: clean
clean:
	rm -f bin/*
