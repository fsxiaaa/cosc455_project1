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
				System.out.println("Working with: " + MyCompiler.parseTree.peek());
				if (MyCompiler.parseTree.peek().equalsIgnoreCase(Tokens.USEB)) {
					//"use" case
					if (scopeName.size() > 0) {
						if (isDeclared(reordered.peek())) {
							System.out.println(reordered.peek() + " equals " + scopeName.get(scopeName.size()-1));
						}
					}
					else {
						variables++;
						System.out.println("variable not yet defined");
						reordered.push(MyCompiler.parseTree.pop()); //push $USE
					}
				}
				else if (MyCompiler.parseTree.peek().equalsIgnoreCase(Tokens.DEFUSEE)) {
					//"define" case
					reordered.push(MyCompiler.parseTree.pop()); //$END
					reordered.push(MyCompiler.parseTree.pop()); //name or value
					MyCompiler.parseTree.peek();
					if (MyCompiler.parseTree.peek().equalsIgnoreCase(Tokens.EQSIGN)) {
						//look for $DEF
						MyCompiler.parseTree.pop(); //pop =
						scopeName.add(MyCompiler.parseTree.pop()); //name to scopeName
						scopeValue.add(reordered.pop()); //value to scopeValue
						reordered.pop(); //pop $END
					}
				}
				else if (isEndToken(MyCompiler.parseTree.peek())) {
					//"end" case
					determineMatch(MyCompiler.parseTree.peek());
					reordered.push(MyCompiler.parseTree.pop());
				}
				else if (isBeginToken(MyCompiler.parseTree.peek())) {
					if (MyCompiler.parseTree.peek().equalsIgnoreCase(Tokens.DEFB)) {
						MyCompiler.parseTree.pop(); //pop $DEB
						//run back through reordered stack to fill in variable usages
						while (reordered.size() > 0) {
							if (reordered.peek().equalsIgnoreCase(Tokens.USEB)) {
								reordered.pop(); //pop $USE
								if (scopeName.isEmpty()) {System.out.println("no defined variables");}
								else if (reordered.peek().equalsIgnoreCase(scopeName.get(scopeName.size()-1))) {
									reordered.pop(); //pop variable name
									MyCompiler.parseTree.push(scopeValue.get(scopeValue.size()-1)); //push variable value
									reordered.pop(); //pop $END
									variables--;
								}
								else {
									MyCompiler.parseTree.push(Tokens.USEB); //wrong variable. push $USE
									MyCompiler.parseTree.push(reordered.pop()); //.. push variable name
								}
							}
							else {
								if (MyCompiler.parseTree.peek().equalsIgnoreCase(Tokens.PARAE)) {
									scopeName.remove(scopeName.size()-1);
									scopeValue.remove(scopeValue.size()-1);
								}
								MyCompiler.parseTree.push(reordered.pop()); //regular begin token. Push back to stack.
							}
						}
						while (MyCompiler.parseTree.size() > 0)
								reordered.push(MyCompiler.parseTree.pop());
					}
					else
						reordered.push(MyCompiler.parseTree.pop()); //push begin token back to stack
				}
				else {
					//"text" case
					reordered.push(MyCompiler.parseTree.pop());
				}
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
		//System.out.println("\n\n REORDERED: ");
		//while(reordered.size() > 0)
			//System.out.println(reordered.pop());
		
		
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
	
	/**
	 * Organizes the stack to make it ready for direct translating from tokens -> HTML
	 * returns HTML
	 * @return String of HTML to be written to file
	 */
	public String generateHTML() {
		String html ="";
		String current = "";
		boolean repeat = false;
		
		reorderStack();
		while (!reordered.isEmpty()) {
			current = reordered.pop();
			if (current.equalsIgnoreCase(Tokens.DOCB)) {
				html += "<html>";
			}
			else if (current.equalsIgnoreCase(Tokens.DOCE)) {
				html += "</html>";
			}
			else if (current.equalsIgnoreCase(Tokens.HEAD)) {
				html += "<head>";
				repeat = true;
			}
			else if (current.equalsIgnoreCase(Tokens.HEAD) && repeat) {
				html += "</head>";
				repeat = false;
			}
			else if (current.equalsIgnoreCase(Tokens.TITLEB)) {
				html += "<title>";
			}
			else if (current.equalsIgnoreCase(Tokens.TITLEE)) {
				html += "</title>";
			}
			else if (current.equalsIgnoreCase(Tokens.PARAB)) {
				html += "<p>";
			}
			else if (current.equalsIgnoreCase(Tokens.PARAE)) {
				html += "</p>";
			}
			else if (current.equalsIgnoreCase(Tokens.BOLD)) {
				html = html + "<b>";
				repeat = true;
			}
			else if (current.equalsIgnoreCase(Tokens.BOLD) && repeat) {
				html = html + "</b>";
				repeat = false;
			}
			else if (current.equalsIgnoreCase(Tokens.ITALICS)) {
				html = html + "<i>";
				repeat = true;
			}
			else if (current.equalsIgnoreCase(Tokens.ITALICS) && repeat) {
				html = html + "</i>";
				repeat = false;
			}
			else if (current.equalsIgnoreCase(Tokens.LISTITEMB)) {
				html += "<li>";
			}
			else if (current.equalsIgnoreCase(Tokens.LISTITEME)) {
				html += "</li>";
			}
			else if (current.equalsIgnoreCase(Tokens.NEWLINE)) {
				html += "<br>";
			}
			else if (current.equalsIgnoreCase(Tokens.AUDIO)) {
				reordered.pop(); //pop (
				current = reordered.pop(); //pop audio address
				html += "<audio controls> <source src=\"" + current + "\"> </audio>";
				reordered.pop(); //pop )
			}
			else if (current.equalsIgnoreCase(Tokens.VIDEO)) {
				reordered.pop(); // pop (
				current = reordered.pop(); //pop video address
				html = html + "<iframe src=\"" + current + "/>";
				reordered.pop(); // pop )
			}
			else if (current.equalsIgnoreCase(Tokens.LINKB)) {
				String name = reordered.pop();
				reordered.pop(); // pop ]
				reordered.pop(); // pop (
				current = reordered.pop(); // pop link address
				html = html + "<a href=\"" + current + "\">" + name + "</a>";
				reordered.pop();
			}
			else { //text
				html += current;
			}
		}
		
		return html;
	}
}