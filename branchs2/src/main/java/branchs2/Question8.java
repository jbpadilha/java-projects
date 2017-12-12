package branchs2;

import java.util.Scanner;

public class Question8 {
	
	public static void main(String[] args) {
		
		Scanner valueA = new Scanner(System.in);
        int a = 0;
        System.out.println("Digite um valor para a letra a: ");
        a = valueA.nextInt();
        
        Scanner valueB = new Scanner(System.in);
        int b = 0;
        System.out.println("Digite um valor para a letra b: ");
        b = valueB.nextInt();
        
        System.out.println(new Question8().returnLetterC(a, b));
        
	}
	
	
	public long returnLetterC(int a, int b) {
		
		String stringA = new Integer(a).toString();
		String stringB = new Integer(b).toString();
		String stringC = "";
		
		for (int i=0; i<= Math.max(stringA.length(),stringB.length());i++) {
			
			if(i<stringA.length()) {
				stringC += stringA.charAt(i);
			}
			if(i<stringB.length()) {
				stringC += stringB.charAt(i);
			}
		}
		
		return (stringC.length() > 6)?-1:Integer.valueOf(stringC);
		
	}
	
}
