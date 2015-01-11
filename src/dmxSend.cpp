#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <stdio.h>
#include <stdlib.h>
#include <strings.h>
#include <string>
#include <cstring>
#include <iostream>
#include <unistd.h>

using namespace std;

/**
 * Writes UDP data to specified IP address, port 6454
 **/ 

int main(int argc, char**argv) {
	struct sockaddr_in servaddr,cliaddr;
	size_t packetsToSend = 100;
	size_t milliBetweenPackets = 100;
	const size_t packetSize = 550;

	if(argc<2) {
		cerr<<"Usage: "<<argv[0]<<": IP address  [packets to send] [milliseconds between packets] "<<endl;
		exit(1);
	}

	if(argc>2) {
		packetsToSend = atoi(argv[2]);
	}
	if(argc>3) {
		milliBetweenPackets = atoi(argv[3]);
	}


	int sockfd=socket(AF_INET,SOCK_DGRAM,0);

	bzero(&servaddr,sizeof(servaddr));
	servaddr.sin_family = AF_INET;
	servaddr.sin_addr.s_addr=inet_addr(argv[1]);
	servaddr.sin_port=htons(6454);

	cout<<"Sending "<<packetsToSend<<" packets"<<endl;

	//http://en.wikipedia.org/wiki/Art-Net
	for(size_t i = 0 ; i < packetsToSend ; i++) {
		//cout<<"sending "<<i<<endl;
		char packetData[packetSize];
		memset(packetData, '.', packetSize);
		sendto(sockfd,packetData,packetSize,0, (struct sockaddr *)&servaddr,sizeof(servaddr));
		usleep(milliBetweenPackets * 1000);
	}
	string sendline = "done";
	sendto(sockfd,sendline.c_str(),sendline.length(),0, (struct sockaddr *)&servaddr,sizeof(servaddr));
	cout<<"Done"<<endl;
}
