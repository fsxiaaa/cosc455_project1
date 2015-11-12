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
			nameValue = value;
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
				html += nameValue;
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
}