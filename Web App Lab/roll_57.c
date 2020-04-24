#include<stdio.h>
#include<stdlib.h>

typedef  struct Node{
        int coeff;
        int pow;
        struct Node* next;
}node;

node* Create(node* start){
        node* temp=(node*)malloc(sizeof(node*));
        start=temp;
        int choice;
        do{
                printf("Coeff-->" );
                scanf("%d",&temp->coeff);
                printf("Pow-->");
                scanf("%d",&temp->pow);
                printf("Want To Enter More Inputs?? (For Yes->Press 1 0r For No->Press 0):: ");
                scanf("%d",&choice);
                if(choice){
                        temp->next=(node*)malloc(sizeof(node*));
                        temp=temp->next;
                }else
                        temp->next=NULL;
        }while(choice);
        return start;
}

void Display(node* start){
        while(start!=NULL){
                if(start->coeff!=1 && start->coeff!=0)
                        printf("%d",start->coeff);
                if(start->coeff==1 && start->pow==0)
                        printf("%d",start->coeff);
                if(start->coeff!=0 && start->pow!=0){
                        if(start->pow==1)
                                printf("x" );
                        else
                        printf("x^%d",start->pow );
                }
                if(start->next!=NULL)
                        printf(" + ");
                start=start->next;
        }
}

int Pow(int a,int x){
	if(x==0)
		return 1;
	else{
		return a*Pow(a,x-1);
	}
}

int Evaluate(node* start,int x){
	int result=0;
	while(start){
		result=result+(start->coeff)*(Pow(x,start->pow));
		start=start->next;
	}
	return result;
}

int main(){
	node *poly=NULL;
	int x;
	printf("\nEnter Your polynomial expresion:\n ");
	poly=Create(poly);
	printf("Polynomial Expression is: ");
	Display(poly);
        printf("\nNow Enter The Value Of 'x': ");
        scanf("%d",&x);
	printf("\nAfter Evaluating: %d\n",Evaluate(poly,x));
}	

