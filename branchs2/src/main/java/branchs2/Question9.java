package branchs2;

import java.util.Scanner;

public class Question9 {
	
	private BinaryTree coreTree;
	
	public static void main(String[] args) {		
		
		Scanner value = new Scanner(System.in);
        int valueEntered;
        Question9 question9 = new Question9();
        
        for (int i=0;i<10;i++) {        
	        System.out.println("Digite um valor para a árvore: ");
	        valueEntered = value.nextInt();
	        question9.add(valueEntered);
        }
        
        System.out.println(question9.returnAddAllNo(question9.coreTree));
		
	}
	
	public void add(int value) {
		
		if(coreTree == null) {
			coreTree = new BinaryTree();
			coreTree.valor = value;
		}
		else {
			BinaryTree newCoreTree = new BinaryTree();
			newCoreTree.valor = value;
			addNode(coreTree,newCoreTree);
		}
		
	}
	
	private void addNode(BinaryTree nodeCore, BinaryTree newNode) {
		
		if(newNode.valor > nodeCore.valor) {
			if(nodeCore.right == null) {
				nodeCore.right = newNode;
			}
			else {
				addNode(nodeCore.right,newNode);
			}
		}
		else {
			if(nodeCore.left == null) {
				nodeCore.left = newNode;
			}
			else {
				addNode(nodeCore.left,newNode);
			}
		}
		
	}
	
	
	public int returnAddAllNo(BinaryTree nodeActual) {
		
		if(nodeActual == null) {
			return 0;
		}
		else {
			int addctionCurrentNode = 0;
			addctionCurrentNode += returnAddAllNo(nodeActual.left);
			addctionCurrentNode += returnAddAllNo(nodeActual.right);
			addctionCurrentNode += nodeActual.valor;
			return addctionCurrentNode;
		}
	}

}

class BinaryTree{
	
	int valor;
	BinaryTree left;
	BinaryTree right;
}
