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
		try {
			while ((MyCompiler.parseTree.size() > 0)) {
				System.out.println("[While]");
				System.out.println("Working with: " + MyCompiler.parseTree.peek());
				if (MyCompiler.parseTree.peek().equalsIgnoreCase(Tokens.USEB)) {
					//"use" case
					System.out.println("[use case]");
					if (scopeName.size() > 0) {
						if (isDeclared(reordered.peek())) {
							System.out.println(reordered.peek() + " equals " + scopeName.get(scopeName.size()-1));
						}
					}
					else {
						System.out.println("variable not yet defined");
						reordered.push(MyCompiler.parseTree.pop());
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
								}
								else {
									MyCompiler.parseTree.push(reordered.pop()); //wrong variable. push $USE
									MyCompiler.parseTree.push(reordered.pop()); //.. push variable name
									MyCompiler.parseTree.push(reordered.pop()); //.. push $END
									System.out.println("variable replacement not done!");
								}
							}
							else {
								System.out.println("entered else");
								if (MyCompiler.parseTree.peek().equalsIgnoreCase(Tokens.PARAE)) {
									scopeName.remove(scopeName.size()-1);
									scopeValue.remove(scopeName.size()-1);
								}
								MyCompiler.parseTree.push(reordered.pop()); //regular begin token. Push back to stack.
							}
						}
						while(MyCompiler.parseTree.size() > 0)
							System.out.println(MyCompiler.parseTree.pop());
					}
					else
						reordered.push(MyCompiler.parseTree.pop()); //push begin token back to stack
				}
				else {
					//"text" case
					System.out.println("[text case]");
					reordered.push(MyCompiler.parseTree.pop());
				}
			}
		}
		catch (Exception e) {
			System.out.println("Error. A semantic error has occured. Undefined variables attemped to be used.");
			System.exit(1);
		}
		for (String s : scopeName) {
			System.out.println("\n\n SN VALUE " + s);
		}
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
	
	

	/*
	private int treePosition = 0;
	private String value = "";
	private String name = "";
	private String nameValue = "";
	private String html = "";
	private ArrayList <String> variableNames = new ArrayList <String>();
	private ArrayList <String> variableValues = new ArrayList <String>();
	
	public String generateHTML() {
		System.out.println("generateHTML, treePosition: " + treePosition);
		value = MyCompiler.parseTree.get(treePosition);
		System.out.println("value = " + value);
		
		if (value.equalsIgnoreCase(Tokens.DOCB)) {
			System.out.println("DOCB");
			html += "<html>";
			if (treePosition < MyCompiler.parseTree.size()) {
				treePosition++;
				generateHTML();
			}
		}
		else if (value.equalsIgnoreCase(Tokens.DOCE)) {
			System.out.println("DOCE");
			html += "</html>";
		}
		else if (value.equalsIgnoreCase(Tokens.HEAD)) {
			System.out.println("head");
			html += "<head>";
			if (treePosition < MyCompiler.parseTree.size()) {
				treePosition++;
				generateHTML();
				if (treePosition < MyCompiler.parseTree.size()) {
					treePosition++;
					value = MyCompiler.parseTree.get(treePosition);
					if (value.equalsIgnoreCase(Tokens.HEAD)) {
						System.out.println("head2");
						html += "</head>";
						if (treePosition < MyCompiler.parseTree.size()) {
							treePosition++;
							generateHTML();
						}
					}
				}
			}
		}
		else if (value.equalsIgnoreCase(Tokens.TITLEB)) {
			System.out.println("title");
			html += "<title>";
			if (treePosition < MyCompiler.parseTree.size()) {
				treePosition++;
				value = MyCompiler.parseTree.get(treePosition);
				if (treePosition < MyCompiler.parseTree.size()) {
					treePosition++;
					generateHTML();
					if (treePosition < MyCompiler.parseTree.size()) {
						if (value.equalsIgnoreCase(Tokens.TITLEE)) {
							System.out.println("title2");
							html += "</title>";
							if (treePosition < MyCompiler.parseTree.size()) {
								treePosition++;
								generateHTML();
							}
						}
					}
				}

			}
		}
		else if (value.equalsIgnoreCase(Tokens.PARAB)) {
			System.out.println("para");
			html += "<p>";
			if (treePosition < MyCompiler.parseTree.size()) {
				treePosition++;
				generateHTML();
			}
		}
		else if (value.equalsIgnoreCase(Tokens.PARAE)) {
			System.out.println("para2");
			html += "</p>";
			if (treePosition < MyCompiler.parseTree.size()) {
				treePosition++;
				generateHTML();
			}
		}
		else if (value.equalsIgnoreCase(Tokens.BOLD)) {
			System.out.println("bold");
			html += "<b>";
			if (treePosition < MyCompiler.parseTree.size()) {
				treePosition++;
				generateHTML();
				if (treePosition < MyCompiler.parseTree.size()) {
					treePosition++;
					value = MyCompiler.parseTree.get(treePosition);
					if (value.equalsIgnoreCase(Tokens.BOLD)) {
						System.out.println("bold2");
						html += "</b>";
						if (treePosition < MyCompiler.parseTree.size()) {
							treePosition++;
							generateHTML();
						}
					}
				}
			}
		}
		else if (value.equalsIgnoreCase(Tokens.ITALICS)) {
			System.out.println("italic");
			html += "<i>";
			if (treePosition < MyCompiler.parseTree.size()) {
				treePosition++;
				generateHTML();
				if (treePosition < MyCompiler.parseTree.size()) {
					treePosition++;
					value = MyCompiler.parseTree.get(treePosition);
					if (value.equalsIgnoreCase(Tokens.ITALICS)) {
						System.out.println("italic2");
						html += "</i>";
						if (treePosition < MyCompiler.parseTree.size()) {
							treePosition++;
							generateHTML();
						}
					}
				}
			}
		}
		else if (value.equalsIgnoreCase(Tokens.LISTITEMB)) {
			System.out.println("listitem");
			html += "<li>";
			if (treePosition < MyCompiler.parseTree.size()) {
				treePosition++;
				generateHTML();
				if (treePosition < MyCompiler.parseTree.size()) {
					treePosition++;
					value = MyCompiler.parseTree.get(treePosition);
					if (value.equalsIgnoreCase(Tokens.LISTITEME)) {
						System.out.println("listitem2");
						html += "</li>";
						if (treePosition < MyCompiler.parseTree.size()) {
							treePosition++;
							generateHTML();
						}
					}
				}
			}
		}
		else if (value.equalsIgnoreCase(Tokens.NEWLINE)) {
			System.out.println("newline");
			html += "<br>";
			if (treePosition < MyCompiler.parseTree.size()) {
				treePosition++;
				generateHTML();
			}
		}
		else if (value.equalsIgnoreCase(Tokens.LINKB)) {
			System.out.println("link");
			html += "<a href=\" ";
			if (treePosition < MyCompiler.parseTree.size()) {
				treePosition++;
				generateHTML();
				if (treePosition < MyCompiler.parseTree.size()) {
					treePosition++;
					value = MyCompiler.parseTree.get(treePosition);
					if (value.equalsIgnoreCase(Tokens.LINKE)) {
						System.out.println("link2");
						html += "</a>";
						if (treePosition < MyCompiler.parseTree.size()) {
							treePosition++;
							generateHTML();
						}
					}
				}
			}
		}
		else if (value.equalsIgnoreCase(Tokens.ADDRESSB)) {
			System.out.println("address");
			if (treePosition < MyCompiler.parseTree.size()) {
				treePosition++;
				generateHTML();
				if (treePosition < MyCompiler.parseTree.size()) {
					treePosition++;
					value = MyCompiler.parseTree.get(treePosition);
					if (value.equalsIgnoreCase(Tokens.ADDRESSE)) {
						System.out.println("address2");
						html += "<\">";
						if (treePosition < MyCompiler.parseTree.size()) {
							treePosition++;
							generateHTML();
						}
					}
				}
			}
		}
		else if (value.equalsIgnoreCase(Tokens.AUDIO)) {
			System.out.println("audio");
			html += "<audio controls> <source src=\"";
			if (treePosition < MyCompiler.parseTree.size()) {
				treePosition++;
				generateHTML();
			}
			html += "< \"> </audio>>";
		}
		else if (value.equalsIgnoreCase(Tokens.VIDEO)) {
			System.out.println("video");
			html += "<iframe src=\" ";
			if (treePosition < MyCompiler.parseTree.size()) {
				treePosition++;
				generateHTML();
			}
			html += "< \"/>";
		}
		else if (value.equalsIgnoreCase(Tokens.DEFB)) {
			System.out.println("def");
			System.out.println(value);
			treePosition++;
			value = MyCompiler.parseTree.get(treePosition);
			name = value;
			treePosition++;
			treePosition++;
			value = MyCompiler.parseTree.get(treePosition);
			String nv = value;
			System.out.println("n " + name);
			value = MyCompiler.parseTree.get(treePosition);
			System.out.println("v " + value);
			if (treePosition < MyCompiler.parseTree.size()) {
				treePosition++;
				value = MyCompiler.parseTree.get(treePosition);
				if (value.equalsIgnoreCase(Tokens.DEFUSEE)) {
					if (treePosition < MyCompiler.parseTree.size()) {
						treePosition++;
						generateHTML();
					}
				}
			}
		}
		else if (value.equalsIgnoreCase(Tokens.USEB)) {
			System.out.println("use");
			treePosition++;
			value = MyCompiler.parseTree.get(treePosition);
			if (value.equalsIgnoreCase(name))
			{
				System.out.println("cries");
				html += nv;
				if (treePosition < MyCompiler.parseTree.size()) {
					treePosition++;
					if (treePosition < MyCompiler.parseTree.size()) {
						treePosition++;
						generateHTML();
					}
				}
			}
		}
		else if (Tokens.isText(value.substring(0,1))) {
			System.out.println("Text");
			html += value;
			if (treePosition < MyCompiler.parseTree.size()) {
				treePosition++;
				generateHTML();
			}
		}
		System.out.println(treePosition + " " + MyCompiler.parseTree.size());
		System.out.println(html);
		return html;
	}
	*/
}