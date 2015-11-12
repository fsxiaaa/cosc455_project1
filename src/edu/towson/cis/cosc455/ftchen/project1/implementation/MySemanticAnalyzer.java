package edu.towson.cis.cosc455.ftchen.project1.implementation;

import java.util.*;

/**
 * MySemanticAnalyzer
 *   Takes an abstract syntax tree (parseTree) translates it to HTML5
 *   and returns the HTML5 equivalent to be written into a file.
 * @author Felicia Tchen
 * @param void
 */
public class MySemanticAnalyzer {
	
	private int treePosition = 0;
	private String value = "";
	private String name = "";
	private String html = "";
	private ArrayList <String> variableNames = new ArrayList <String>();
	private ArrayList <String> variableValues = new ArrayList <String>();
	
	public String generateHTML() {
		value = MyCompiler.parseTree.get(treePosition);
		System.out.println("value = " + value);
		
		if (value.equalsIgnoreCase(Tokens.DOCB)) {
			html += "<html>";
			if (treePosition != MyCompiler.parseTree.size()) {
				treePosition++;
				generateHTML();
				if (treePosition != MyCompiler.parseTree.size()) {
					treePosition++;
					value = MyCompiler.parseTree.get(treePosition);
					if (value.equalsIgnoreCase(Tokens.DOCE)) {
						html += "</html>";
					}
				}
			}
		}
		else if (value.equalsIgnoreCase(Tokens.HEAD)) {
			html += "<head>";
			if (treePosition != MyCompiler.parseTree.size()) {
				treePosition++;
				generateHTML();
				if (treePosition != MyCompiler.parseTree.size()) {
					treePosition++;
					value = MyCompiler.parseTree.get(treePosition);
					if (value.equalsIgnoreCase(Tokens.HEAD)) {
						html += "</head>";
						if (treePosition != MyCompiler.parseTree.size()) {
							treePosition++;
							generateHTML();
						}
					}
				}
			}
		}
		else if (value.equalsIgnoreCase(Tokens.TITLEB)) {
			html += "<title>";
			if (treePosition != MyCompiler.parseTree.size()) {
				treePosition++;
				value = MyCompiler.parseTree.get(treePosition);
				//if (Tokens.isText(value)) {
					// html += value;
					//if (treePosition != MyCompiler.parseTree.size()) {
						treePosition++;
						//value = MyCompiler.parseTree.get(treePosition);
						generateHTML();
						if (value.equalsIgnoreCase(Tokens.HEAD)) {
							html += "</title>";
							if (treePosition != MyCompiler.parseTree.size()) {
								treePosition++;
								generateHTML();
							}
						}
					//}
				//}
			}
		}
		else if (value.equalsIgnoreCase(Tokens.PARAB)) {
			html += "<p>";
			if (treePosition != MyCompiler.parseTree.size()) {
				treePosition++;
				generateHTML();
				if (treePosition != MyCompiler.parseTree.size()) {
					treePosition++;
					value = MyCompiler.parseTree.get(treePosition);
					if (value.equalsIgnoreCase(Tokens.PARAE)) {
						html += "</p>";
						if (treePosition != MyCompiler.parseTree.size()) {
							treePosition++;
							generateHTML();
						}
					}
				}
			}
		}
		else if (value.equalsIgnoreCase(Tokens.BOLD)) {
			html += "<b>";
			if (treePosition != MyCompiler.parseTree.size()) {
				treePosition++;
				generateHTML();
				if (treePosition != MyCompiler.parseTree.size()) {
					treePosition++;
					value = MyCompiler.parseTree.get(treePosition);
					if (value.equalsIgnoreCase(Tokens.BOLD)) {
						html += "</b>";
						if (treePosition != MyCompiler.parseTree.size()) {
							treePosition++;
							generateHTML();
						}
					}
				}
			}
		}
		else if (value.equalsIgnoreCase(Tokens.ITALICS)) {
			html += "<i>";
			if (treePosition != MyCompiler.parseTree.size()) {
				treePosition++;
				generateHTML();
				if (treePosition != MyCompiler.parseTree.size()) {
					treePosition++;
					value = MyCompiler.parseTree.get(treePosition);
					if (value.equalsIgnoreCase(Tokens.ITALICS)) {
						html += "</i>";
						if (treePosition != MyCompiler.parseTree.size()) {
							treePosition++;
							generateHTML();
						}
					}
				}
			}
		}
		else if (value.equalsIgnoreCase(Tokens.LISTITEMB)) {
			html += "<li>";
			if (treePosition != MyCompiler.parseTree.size()) {
				treePosition++;
				generateHTML();
				if (treePosition != MyCompiler.parseTree.size()) {
					treePosition++;
					value = MyCompiler.parseTree.get(treePosition);
					if (value.equalsIgnoreCase(Tokens.LISTITEME)) {
						html += "</li>";
						if (treePosition != MyCompiler.parseTree.size()) {
							treePosition++;
							generateHTML();
						}
					}
				}
			}
		}
		else if (value.equalsIgnoreCase(Tokens.NEWLINE)) {
			html += "<br>";
			if (treePosition != MyCompiler.parseTree.size()) {
				treePosition++;
				generateHTML();
			}
		}
		else if (value.equalsIgnoreCase(Tokens.LINKB)) {
			html += "<a href=\" ";
			if (treePosition != MyCompiler.parseTree.size()) {
				treePosition++;
				generateHTML();
				if (treePosition != MyCompiler.parseTree.size()) {
					treePosition++;
					value = MyCompiler.parseTree.get(treePosition);
					if (value.equalsIgnoreCase(Tokens.LINKE)) {
						html += "</a>";
						if (treePosition != MyCompiler.parseTree.size()) {
							treePosition++;
							generateHTML();
						}
					}
				}
			}
		}
		else if (value.equalsIgnoreCase(Tokens.ADDRESSB)) {
			if (treePosition != MyCompiler.parseTree.size()) {
				treePosition++;
				generateHTML();
				if (treePosition != MyCompiler.parseTree.size()) {
					treePosition++;
					value = MyCompiler.parseTree.get(treePosition);
					if (value.equalsIgnoreCase(Tokens.ADDRESSE)) {
						html += "<\">";
						if (treePosition != MyCompiler.parseTree.size()) {
							treePosition++;
							generateHTML();
						}
					}
				}
			}
		}
		else if (value.equalsIgnoreCase(Tokens.AUDIO)) {
			html += "<audio controls> <source src=\"";
			if (treePosition != MyCompiler.parseTree.size()) {
				treePosition++;
				generateHTML();
			}
			html += "< \"> </audio>>";
		}
		else if (value.equalsIgnoreCase(Tokens.VIDEO)) {
			html += "<iframe src=\" ";
			if (treePosition != MyCompiler.parseTree.size()) {
				treePosition++;
				generateHTML();
			}
			html += "< \"/>";
		}
		else if (value.equalsIgnoreCase(Tokens.DEFB)) {
			value = MyCompiler.parseTree.get(treePosition);
			name = value;
			treePosition++;
			treePosition++;
			value = MyCompiler.parseTree.get(treePosition);
			if (treePosition != MyCompiler.parseTree.size()) {
				treePosition++;
				generateHTML();
			}
		}
		else if (value.equalsIgnoreCase(Tokens.USEB)) {
			html += "<audio controls> <source src=\"";
			if (treePosition != MyCompiler.parseTree.size()) {
				treePosition++;
				generateHTML();
			}
			html += "< \"> </audio>>";
		}
		else if (Tokens.isText(value)) {
			html += value;
			if (treePosition != MyCompiler.parseTree.size()) {
				treePosition++;
				generateHTML();
			}
		}
		return html;
	}
	
	
}
