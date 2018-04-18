#include <stdio.h>

char get1(char s1,char s2){
	printf("get1 method\n");
	return 'a';
}


char get2(int id){
	char c1 = 'b';
	char c2 = 'c';
	return get1(c1,c2);
}

int main(){
	printf("%c",get2(1));
	return 0;
}
