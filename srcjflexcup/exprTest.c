#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <stdbool.h>
#include <string.h>

//procedure di supporto
char * itos(int n) {
	char * dest = malloc(sizeof(char)*16);
	sprintf(dest, "%d", n);
	return dest;
}

char * dtos(double n) {
	char * dest = malloc(sizeof(char)*16);
	sprintf(dest, "%f", n);
	return dest;
}

char* ctos(char c){	char* dest = malloc(sizeof(char)*2);
	sprintf(dest, "%c", c);
	return dest;
}

char * btos(bool b) {
	char * dest = malloc(sizeof(char)*2);
	if(b == true)
		dest = "true";
	else
		dest = "false";
	return dest;
}

char * strconcat(char * str1, char * str2) {
	char * dest = malloc(sizeof(char)*256);
	strcat(dest, str1);
	strcat(dest, str2);
	return dest;
}

//Dichiarazione variabili globali
//Dichiarazione funzioni 
//main
int main(void){
	int a = 1;
	switch ( a ) {
		case 2 : 
			printf("2\n");
			break;
		case 1 : 
			printf("1\n");
			break;
		default: break;
	}
}

