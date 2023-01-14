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
int fibonacci(int a) {
	int res = 0;
	if (a<=1){
		return 1;
	}
	if (a==2){
		return 1;
	}
	return fibonacci(a-1)+fibonacci(a-2);
}
void stampaMenu() {
	int i = 0;
	for (int i = 0;i != 2 ;i++){
		if (i==0){
			printf("0) somma 2 numeri\n");
		}
		else {
			if (i==1){
				printf("1) moltiplicazione con somma\n");
			}
			else {
				if (i==2){
					printf("2) potenza due numeri\n");
				}
			}
		}
	}
	printf("3) fibonacci\n");
	printf("4) divisione intera\n");
	printf("5) stampa menu\n");
	printf("6) exit\n");
}
float somma(float a,float b) {
	return a+b;
}
int timesWithSum(int a,int b) {
	int res = 0;
	if (b==1){
		res = a;
	}
	else {
		if (b!=0){
			res = a+timesWithSum(a,b-1);
		}
	}
	return res;
}
float divisione(int a,int b) {
	return a/b;
}
float potenza(float a,int b) {
	float res = 0.0;
	if (b==0){
		res = 1.0;
	}
	else {
		if (b==1){
			res = a;
		}
		else {
			res = a*potenza(a,b-1);
		}
	}
	return res;
}
//main
int main(void){
	int scelta = 0;
	stampaMenu();
	scanf( "%d", &scelta);
	while(scelta!=6){
		if (scelta==0){
			float a = 0.0, b = 0.0;
			printf("Inserisci i due numeri da sommare");
			scanf( "%f", &a);
			scanf( "%f", &b);
			printf("%s", dtos(a));
			printf(" + ");
			printf("%s", dtos(b));
			printf(" = ");
			printf("%s\n", dtos(somma(a,b)));
		}
		else {
			if (scelta==1){
				int a = 0, b = 0;
				printf("inserisci i due numeri da moltiplicare e sommare");
				scanf( "%d", &a);
				scanf( "%d", &b);
				printf("risultato = ");
				printf("%s\n", itos(timesWithSum(a,b)));
			}
			else {
				if (scelta==2){
					float a = 0.0;
					int b = 0;
					printf("inserisci il numero x da elevare alla y-esima potenza");
					scanf( "%f", &a);
					scanf( "%d", &b);
					printf("%s", dtos(a));
					printf(" ^ ");
					printf("%s", itos(b));
					printf(" = ");
					printf("%s\n", dtos(potenza(a,b)));
				}
				else {
					if (scelta==3){
						int a = 0;
						printf("quanti numeri di fibonacci vuoi vedere?");
						scanf( "%d", &a);
						printf("fibonacci to ");
						printf("%s", itos(a));
						printf(": ");
						printf("%s\n", itos(fibonacci(a)));
					}
					else {
						if (scelta==4){
							int a = 0, b = 0;
							printf("inserisci il dividendo e il divisore");
							scanf( "%d", &a);
							scanf( "%d", &b);
							printf("%s", itos(a));
							printf(" : ");
							printf("%s", itos(b));
							printf(" = ");
							printf("%s\n", dtos(divisione(a,b)));
						}
						else {
							if (scelta==5){
								stampaMenu();
								scanf( "%d", &scelta);
							}
						}
					}
				}
			}
		}
		stampaMenu();
		scanf( "%d", &scelta);
	}
	printf("%s\n", strconcat("prima parte della stringa da concatenare", "seconda parte della stringa concatenata"));
	printf("il bool è: ");
	printf("%s\n", btos(1<2));

}

