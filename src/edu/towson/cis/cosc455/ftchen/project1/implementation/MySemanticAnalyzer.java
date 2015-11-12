package edu.towson.cis.cosc455.ftchen.project1.implementation;

import java.util.*;

/**
 * MySemanticAnalyzer
 *   Takes an abstract syntax tree (parseTree) translates it to HTML5
 *   and returns the HTML5 equivalent to be written into a file.
 * @author Felicia Tchen
 * @param voidw
 */

public class MySemanticAnalyzer {
	Stack <String> reordered = new Stack <String>();
	String match = "";
	ArrayList <String> scopeName = new ArrayList <String>();
	ArrayList <String> scopeValue = new ArrayList <String>();
	int scopePosition = 0;
	
	/**
	 * Method to reorder the parseTree stack, resolving any variables/etc. along the way.
	 */
	public void reorderStack() {
		int variables = -1;
		//try {
			while (variables != 0)
			{
				variables = 0;
			do {
				System.out.println("[While]");
				System.out.println("Working with: " + MyCompiler.parseTree.peek());
				if (MyCompiler.parseTree.peek().equalsIgnoreCase(Tokens.USEB)) {
					//"use" case
					System.out.println("[use case]");
					variables++;
					if (scopeName.size() > 0) {
						if (isDeclared(reordered.peek())) {
							System.out.println(reordered.peek() + " equals " + scopeName.get(scopeName.size()-1));
						}
					}
					else {
						System.out.println("variable not yet defined");
						System.out.println(reordered.push(MyCompiler.parseTree.pop())); //push $USE
					}
				}
				else if (MyCompiler.parseTree.peek().equalsIgnoreCase(Tokens.DEFUSEE)) {
					//"define" case
					System.out.println("[define case]");
					reordered.push(MyCompiler.parseTree.pop()); //$END
					reordered.push(MyCompiler.parseTree.pop()); //name or value
					System.out.println("DEFINE PEEK: " + MyCompiler.parseTree.peek());
					if (MyCompiler.parseTree.peek().equalsIgnoreCase(Tokens.EQSIGN)) {
						System.out.println("defined ");
						//look for $DEF
						MyCompiler.parseTree.pop(); //pop =
						scopeName.add(MyCompiler.parseTree.pop()); //name to scopeName
						scopeValue.add(reordered.pop()); //value to scopeValue
						reordered.pop(); //pop $END
					}
				}
				else if (isEndToken(MyCompiler.parseTree.peek())) {
					//"end" case
					System.out.println("[end case]");
					determineMatch(MyCompiler.parseTree.peek());
					reordered.push(MyCompiler.parseTree.pop());
					System.out.println("reordered PEEK: " + reordered.peek());
				}
				else if (isBeginToken(MyCompiler.parseTree.peek())) {
					System.out.println("[begin case]");
					if (MyCompiler.parseTree.peek().equalsIgnoreCase(Tokens.DEFB)) {
						System.out.println("DO SOMETHING");
						MyCompiler.parseTree.pop(); //pop $DEF
						//run back through reordered stack to fill in variable usages
						while (reordered.size() > 0) {
							if (reordered.peek().equalsIgnoreCase(Tokens.USEB)) {
								reordered.pop(); //pop $USE
								if (reordered.peek().equalsIgnoreCase(scopeName.get(scopeName.size()-1))) {
									reordered.pop(); //pop variable name
									MyCompiler.parseTree.push(scopeValue.get(scopeValue.size()-1)); //push variable value
									reordered.pop(); //pop $END
									System.out.println("variable replacement ok!");
									variables--;
								}
								else {
									System.out.println("11111111" + MyCompiler.parseTree.push(Tokens.USEB)); //wrong variable. push $USE
									System.out.println("22222222" +MyCompiler.parseTree.push(reordered.pop())); //.. push variable name
									System.out.println("variable replacement not done!");
								}
							}
							else {
								System.out.println("entered else");
								if (MyCompiler.parseTree.peek().equalsIgnoreCase(Tokens.PARAE)) {
									scopeName.remove(scopeName.size()-1);
									scopeValue.remove(scopeValue.size()-1);
								}
								MyCompiler.parseTree.push(reordered.pop()); //regular begin token. Push back to stack.
								System.out.println("finished else");
							}
						}
						while(MyCompiler.parseTree.size() > 0)
								System.out.println(reordered.push(MyCompiler.parseTree.pop()));
					}
					else
						reordered.push(MyCompiler.parseTree.pop()); //push begin token back to stack
				}
				else {
					//"text" case
					System.out.println("[text case]");
					reordered.push(MyCompiler.parseTree.pop());
				}
				System.out.println("variables is: " + variables);
			} while (!reordered.peek().equalsIgnoreCase(Tokens.DOCB));
			if (variables != 0)
				while (reordered.size() > 0)
					MyCompiler.parseTree.push(reordered.pop());
		}
		//}
		//catch (Exception e) {
			//System.out.println("Error. A semantic error has occured. Undefined variables attemped to be used.");
			//System.exit(1);
		//}
		System.out.println("\n\n REORDERED: ");
		while(reordered.size() > 0)
			System.out.println(reordered.pop());
		
		
		System.out.println("Stack reordered!");
	}
	
	/**
	 * Method to check whether a token is a begin token or not.
	 * @param s
	 * @return boolean
	 */
	public boolean isBeginToken(String s) {
		for (String t : MyCompiler.beginTokens) {
			if (s.equalsIgnoreCase(t)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Method to check whether a token is an end token or not.
	 * @param s
	 * @return boolean
	 */
	public boolean isEndToken(String s) {
		for (String t : MyCompiler.endTokens) {
			if (s.equalsIgnoreCase(t)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Method to check whether a variable has been declared
	 * @param s
	 * @return
	 */
	public boolean isDeclared(String s) {
		for (String t : scopeName) {
			if (t.equalsIgnoreCase(s)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Method to determine matching begin/end token.
	 */
	public void determineMatch(String s) {
		if (s.equalsIgnoreCase(Tokens.DOCE)) {
			match = Tokens.DOCB;
		}
		else if (s.equalsIgnoreCase(Tokens.HEAD)) {
			match = Tokens.HEAD;
		}
		else if (s.equalsIgnoreCase(Tokens.TITLEE)) {
			match = Tokens.TITLEB;
		}
		else if (s.equalsIgnoreCase(Tokens.PARAE)) {
			match = Tokens.PARAB;
		}
		else if (s.equalsIgnoreCase(Tokens.BOLD)) {
			match = Tokens.BOLD;
		}
		else if (s.equalsIgnoreCase(Tokens.ITALICS)) {
			match = Tokens.ITALICS;
		}
		else if (s.equalsIgnoreCase(Tokens.LISTITEME)) {
			match = Tokens.LISTITEMB;
		}
	}
	
	public String generateHTML() {
		String html ="";
		reorderStack();
		return html;
	}
}