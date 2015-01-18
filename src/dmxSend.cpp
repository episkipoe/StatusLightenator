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

const size_t PACKET_SIZE = 550;
const size_t COLORS_IN_UNIVERSEe = 510 * 3;  // 510 lights by R G B

/**
 * Writes UDP data to specified IP address, port 6454
 **/ 
int main(int argc, char**argv) {
	struct sockaddr_in servaddr,cliaddr;
	uint16_t universe;
	size_t offset = 0;
	unsigned char data[COLORS_IN_UNIVERSEe];
	size_t packetsToSend = 100;
	size_t milliBetweenPackets = 100;

	if(argc<2) {
		cerr<<"Usage: "<<argv[0]<<": IP address [universe=0] [offset] [packets to send = 100] [milliseconds between packets = 100] [RGB values]"<<endl;
		exit(1);
	}

	if(argc > 2) {
		universe = (uint16_t)atoi(argv[2]);
	}
	if(argc>3) {
		offset = atoi(argv[3]);
	}
	if(argc>4) {
		packetsToSend = atoi(argv[4]);
	}
	if(argc>5) {
		milliBetweenPackets = atoi(argv[5]);
	}
	memset(data, 0, COLORS_IN_UNIVERSEe);
	if(argc > 6) {
		size_t index = offset;
		for (size_t i = 6 ; i < argc;) {
			if (index >= COLORS_IN_UNIVERSEe) {
				cerr << "Invalid light specified"<<endl;
				exit(1);
			}
			data[index++] = atoi(argv[i++]);	
		}
	}



	int sockfd=socket(AF_INET,SOCK_DGRAM,0);

	bzero(&servaddr,sizeof(servaddr));
	servaddr.sin_family = AF_INET;
	servaddr.sin_addr.s_addr=inet_addr(argv[1]);
	servaddr.sin_port=htons(6454);

	cout<<"Sending "<<packetsToSend<<" packets"<<endl;

	//http://en.wikipedia.org/wiki/Art-Net
	char packetData[PACKET_SIZE];
	memset(packetData, 0, PACKET_SIZE);
	strcpy(packetData, "Art-Net");	
	memcpy(&packetData[14], &universe, sizeof(universe));
	memcpy(&packetData[18], &data, COLORS_IN_UNIVERSEe);
	for(size_t i = 0 ; i < packetsToSend ; i++) {
		//cout<<"sending "<<i<<endl;
		sendto(sockfd,packetData,PACKET_SIZE,0, (struct sockaddr *)&servaddr,sizeof(servaddr));
		usleep(milliBetweenPackets * 1000);
	}
	string sendline = "done";
	sendto(sockfd,sendline.c_str(),sendline.length(),0, (struct sockaddr *)&servaddr,sizeof(servaddr));
	cout<<"Done"<<endl;
}
