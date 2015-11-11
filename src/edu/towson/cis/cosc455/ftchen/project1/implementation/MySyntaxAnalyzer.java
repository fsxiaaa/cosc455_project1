package edu.towson.cis.cosc455.ftchen.project1.implementation;

import edu.towson.cis.cosc455.ftchen.project1.interfaces.SyntaxAnalyzer;

public class MySyntaxAnalyzer implements SyntaxAnalyzer {

	//@Override
	public void markdown() throws CompilerException {
		try {
			if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.DOCB)) {
				MyCompiler.parseTree.push(MyCompiler.currentToken);
				System.out.println("PUSHED " + MyCompiler.currentToken + " TO PARSE TREE");
				MyCompiler.lexicalAnalyzer.ridWhiteSpace();
				MyCompiler.lexicalAnalyzer.getNextToken();
				//code
				if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.DEFB)) {
					System.out.println("markdown -> variable Define");
					variableDefine();
				}
				if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.HEAD)) {
					System.out.println("markdown -> head");
					head();
					MyCompiler.lexicalAnalyzer.ridWhiteSpace();
					MyCompiler.lexicalAnalyzer.getNextToken();
				}
				if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.PARAB) ||
						MyCompiler.currentToken.equalsIgnoreCase(Tokens.NEWLINE) ||
							Tokens.isText(MyCompiler.currentToken.substring(0,1)) || 
								MyCompiler.lexicalAnalyzer.isSpecial(MyCompiler.currentToken)) {
					System.out.println("markdown -> body");
					body();
				}
				//end code
				if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.DOCE)) {
					MyCompiler.parseTree.push(MyCompiler.currentToken);
					System.out.println("PUSHED " + MyCompiler.currentToken + " TO PARSE TREE");
					//MyCompiler.lexicalAnalyzer.ridWhiteSpace();
					MyCompiler.lexicalAnalyzer.getNextToken();
					System.out.println("SUCCESS!");
				}
				else {
					System.out.println("The current token is: " + MyCompiler.currentToken);
					throw new CompilerException("Error. Syntax rules not being followed."
							+ " Invalid begin/end tag pairing."
							+ " A syntax error has occured.");	
				}
			}
			else
				throw new CompilerException("Error. The file must start with #BEGIN."
						+ " A syntax error has occured.");
		}
		catch (CompilerException e) {
			System.out.println(e.getErrorMessage());
			System.exit(1);
		}
	}

	//@Override
	public void head() throws CompilerException {
		try {
			System.out.println("[This is head]");
			if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.HEAD)) {
				MyCompiler.parseTree.push(MyCompiler.currentToken);
				System.out.println("PUSHED " + MyCompiler.currentToken + " TO PARSE TREE");
				MyCompiler.lexicalAnalyzer.ridWhiteSpace();
				MyCompiler.lexicalAnalyzer.getNextToken();
				System.out.println("[head -> title]");
				if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.TITLEB)) {
					title();
				}
				MyCompiler.lexicalAnalyzer.ridWhiteSpace();
				MyCompiler.lexicalAnalyzer.getNextToken();
				if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.HEAD)) {
					MyCompiler.parseTree.push(MyCompiler.currentToken);
					System.out.println("PUSHED " + MyCompiler.currentToken + " TO PARSE TREE");
				}
				else
					throw new CompilerException("Error. ^ tag must end with another ^ tag."
							+ " A syntax error has occured.");
			}
		}
		catch (CompilerException e) {
			System.out.println(e.getErrorMessage());
			System.exit(1);
		}
	}

	//@Override
	public void title() throws CompilerException {
		try {
			System.out.println("[This is title]");
			//if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.TITLEB)) {
				MyCompiler.parseTree.push(MyCompiler.currentToken);
				System.out.println("PUSHED " + MyCompiler.currentToken + " TO PARSE TREE");
				//get text
				MyCompiler.lexicalAnalyzer.ridWhiteSpace();
				MyCompiler.lexicalAnalyzer.getNextToken();
				MyCompiler.parseTree.push(MyCompiler.currentToken);
				System.out.println("PUSHED " + MyCompiler.currentToken + " TO PARSE TREE");
				//finished getting&pushing text
				MyCompiler.lexicalAnalyzer.ridWhiteSpace();
				MyCompiler.lexicalAnalyzer.getNextToken();
				if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.TITLEE)) {
					MyCompiler.parseTree.push(MyCompiler.currentToken);
					System.out.println("PUSHED " + MyCompiler.currentToken + " TO PARSE TREE");
				}
				else
					throw new CompilerException("Error. < tag must end with a > tag."
							+ " A syntax error has occured.");
			//}
		}
		catch (CompilerException e) {
			System.out.println(e.getErrorMessage());
			System.exit(1);
		}
	}

	//@Override
	public void body() throws CompilerException {
		try {
			System.out.println("[This is body]");
			System.out.println("Current Token is: " + MyCompiler.currentToken);
			if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.PARAB)) {
				System.out.println("[(body) paragraph]");
				paragraph();
				if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.PARAE)) {
					System.out.println("[ending body paragraph]");
					MyCompiler.parseTree.push(MyCompiler.currentToken);
					System.out.println("PUSHED " + MyCompiler.currentToken + " TO PARSE TREE");
					MyCompiler.lexicalAnalyzer.ridWhiteSpace();
					MyCompiler.lexicalAnalyzer.getNextToken();
				}
				if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.PARAB) ||
						MyCompiler.currentToken.equalsIgnoreCase(Tokens.NEWLINE) ||
							Tokens.isText(MyCompiler.currentToken.substring(0,1)) || 
								MyCompiler.lexicalAnalyzer.isSpecial(MyCompiler.currentToken)) {
					System.out.println("[(body) paragraph -> body]");
					body();
				}
				
			}
			else if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.NEWLINE)) {
				System.out.println("[(body) newline");
				System.out.println("[(body) newline -> body");
				newline();
				if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.PARAB) ||
						MyCompiler.currentToken.equalsIgnoreCase(Tokens.NEWLINE) ||
							Tokens.isText(MyCompiler.currentToken.substring(0,1)) || 
								MyCompiler.lexicalAnalyzer.isSpecial(MyCompiler.currentToken)) {
					System.out.println("[(body) paragraph -> body]");
					body();
				}
			}
			else if (Tokens.isText(MyCompiler.currentToken.substring(0,1)) || 
					MyCompiler.lexicalAnalyzer.isSpecial(MyCompiler.currentToken)) {
				System.out.println("[(body) text]");
				System.out.println("[(body) innertext -> body]");
				innerText();
				if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.PARAB) ||
						MyCompiler.currentToken.equalsIgnoreCase(Tokens.NEWLINE) ||
							Tokens.isText(MyCompiler.currentToken.substring(0,1)) || 
								MyCompiler.lexicalAnalyzer.isSpecial(MyCompiler.currentToken)) {
					System.out.println("[(body) paragraph -> body]");
					body();
				}
			}
			else {
				throw new CompilerException("Error. in body");
			}
		}
		catch (CompilerException e) {
			System.out.println(e.getErrorMessage());
			System.exit(1);
		}
	}

	//@Override
	public void paragraph() throws CompilerException {
		try {
			System.out.println("[This is paragraph]");
			//if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.PARAB)) {
				MyCompiler.parseTree.push(MyCompiler.currentToken);
				System.out.println("PUSHED " + MyCompiler.currentToken + " TO PARSE TREE");
				MyCompiler.lexicalAnalyzer.ridWhiteSpace();
				MyCompiler.lexicalAnalyzer.getNextToken();
				System.out.println("NEXT TOKEN IS: " + MyCompiler.nextToken);
				if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.DEFB)) {
					System.out.println("[paragraph -> variableDefine]");
					variableDefine();
				}
				if (Tokens.isText(MyCompiler.currentToken.substring(0,1))) {
					System.out.println("[paragraph -> innerText]");
					innerText();
				}
				else
					throw new CompilerException("Error. { tag must end with a } tag."
							+ " A syntax error has occured.");
			//}
		}
		catch (CompilerException e) {
			System.out.println(e.getErrorMessage());
			System.exit(1);
		}
	}

	//@Override
	public void innerText() throws CompilerException {
		try {
			System.out.println("[This is inner text]");
			if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.USEB)) {
				System.out.println("[innerText -> variableUse]");
				variableUse();
				if (Tokens.isText(MyCompiler.currentToken.substring(0,1))) {
					innerText();
				}
			}
			else if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.BOLD)) {
				System.out.println("[innerText -> bold]");
				bold();
				if (Tokens.isText(MyCompiler.currentToken.substring(0,1))) {
					innerText();
				}
			}
			else if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.ITALICS)) {
				System.out.println("[innerText -> italics]");
				italics();
				if (Tokens.isText(MyCompiler.currentToken.substring(0,1))) {
					innerText();
				}
			}
			else if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.LISTITEMB)) {
				System.out.println("[innerText -> list]");
				listitem();
				if (Tokens.isText(MyCompiler.currentToken.substring(0,1))) {
					innerText();
				}
			}
			else if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.AUDIO)) {
				System.out.println("[innerText -> audio]");
				audio();
				if (Tokens.isText(MyCompiler.currentToken.substring(0,1))) {
					innerText();
				}
			}
			else if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.VIDEO)) {
				System.out.println("[innerText -> video]");
				video();
				if (Tokens.isText(MyCompiler.currentToken.substring(0,1))) {
					innerText();
				}
			}
			else if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.LINKB)) {
				System.out.println("[innerText -> link]");
				link();
				if (Tokens.isText(MyCompiler.currentToken.substring(0,1))) {
					innerText();
				}
			}
			else if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.NEWLINE)) {
				System.out.println("[innerText -> newline]");
				newline();
				if (Tokens.isText(MyCompiler.currentToken.substring(0,1))) {
					innerText();
				}
			}
			else if (Tokens.isText(MyCompiler.currentToken.substring(0,1))) {
				System.out.println("[innerText -> innerText]");
				MyCompiler.parseTree.push(MyCompiler.currentToken);
				System.out.println("PUSHED " + MyCompiler.currentToken + " TO PARSE TREE");				MyCompiler.lexicalAnalyzer.ridWhiteSpace();
				//MyCompiler.lexicalAnalyzer.ridWhiteSpace();
				MyCompiler.lexicalAnalyzer.getNextToken();
				if (Tokens.isText(MyCompiler.currentToken.substring(0,1)) ||
						MyCompiler.lexicalAnalyzer.isSpecial(MyCompiler.currentToken.substring(0,1))) {
					System.out.println("repeat innerText");
					innerText();
				}
			}
			else {
				throw new CompilerException("Error. Invalid inner text.");
			}
			System.out.println("finish innerText");
		}
		catch (CompilerException e) {
			System.out.println(e.getErrorMessage());
			System.exit(1);
		}
	}

	//@Override
	public void variableDefine() throws CompilerException {
		try {
			System.out.println("[This is variableDefine]");
			MyCompiler.parseTree.push(MyCompiler.currentToken);
			System.out.println("PUSHED " + MyCompiler.currentToken + " TO PARSE TREE");
			//MyCompiler.lexicalAnalyzer.ridWhiteSpace();
			MyCompiler.lexicalAnalyzer.getNextToken();
			//getText
			if (Tokens.isText(MyCompiler.currentToken.substring(0,1))) {
				MyCompiler.parseTree.push(MyCompiler.currentToken);
				System.out.println("PUSHED " + MyCompiler.currentToken + " TO PARSE TREE");
				MyCompiler.lexicalAnalyzer.ridWhiteSpace();
				MyCompiler.lexicalAnalyzer.getNextToken();
				System.out.println(MyCompiler.currentToken);
				if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.EQSIGN)) {
					MyCompiler.parseTree.push(MyCompiler.currentToken);
					System.out.println("PUSHED " + MyCompiler.currentToken + " TO PARSE TREE");
					MyCompiler.lexicalAnalyzer.ridWhiteSpace();
					MyCompiler.lexicalAnalyzer.getNextToken();
					if (Tokens.isText(MyCompiler.currentToken.substring(0,1))) {
						MyCompiler.parseTree.push(MyCompiler.currentToken);
						System.out.println("PUSHED " + MyCompiler.currentToken + " TO PARSE TREE");
						MyCompiler.lexicalAnalyzer.ridWhiteSpace();
						MyCompiler.lexicalAnalyzer.getNextToken();
						if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.DEFUSEE)) {
							MyCompiler.parseTree.push(MyCompiler.currentToken);
							System.out.println("PUSHED " + MyCompiler.currentToken + " TO PARSE TREE");
							MyCompiler.lexicalAnalyzer.ridWhiteSpace();
							MyCompiler.lexicalAnalyzer.getNextToken();
							if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.DEFB)){
								variableDefine();
							}
						}
						else
							throw new CompilerException("Error. A syntax error has occured. "
									+ "Variable definition must end with $END");
					}
					else
						throw new CompilerException("Error. A syntax error has occured. "
								+ "Variable definition = sign must be followed by more text.");
				}
				else
					throw new CompilerException("Error. A syntax error has occured. "
							+ "Variable definition name must be followed by = sign.");
				System.out.println("Finish variable define");
			}
			else
				throw new CompilerException("Error. A syntax error has occured. "
						+ "Variable definition $DEF tag must be followed by text.");
		}
		catch (CompilerException e) {
			System.out.println(e.getErrorMessage());
			System.exit(1);
		}
	}

	//@Override
	public void variableUse() throws CompilerException {
		try {
			System.out.println("[This is variableUse]");
			MyCompiler.parseTree.push(MyCompiler.currentToken);
			System.out.println("PUSHED " + MyCompiler.currentToken + " TO PARSE TREE");
			//get name of variable to be used
			MyCompiler.lexicalAnalyzer.ridWhiteSpace();
			MyCompiler.lexicalAnalyzer.getNextToken();
			if (Tokens.isText(MyCompiler.currentToken.substring(0,1))) {
				MyCompiler.parseTree.push(MyCompiler.currentToken);
				System.out.println("PUSHED " + MyCompiler.currentToken + " TO PARSE TREE");
				MyCompiler.lexicalAnalyzer.ridWhiteSpace();
				MyCompiler.lexicalAnalyzer.getNextToken();
				if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.DEFUSEE)) {
					MyCompiler.parseTree.push(MyCompiler.currentToken);
					System.out.println("PUSHED " + MyCompiler.currentToken + " TO PARSE TREE");
					MyCompiler.lexicalAnalyzer.ridWhiteSpace();
					MyCompiler.lexicalAnalyzer.getNextToken();
				}
				else
					throw new CompilerException("Error. A syntax error has occured."
							+ " Variable $USE text must end with $END");
			}
			else
				throw new CompilerException("Error. A syntax error has occured."
						+ " Invalid variable use syntax. Variable $USE must be followed by text.");
		}
		catch (CompilerException e) {
			System.out.println(e.getErrorMessage());
			System.exit(1);
		}
	}

	//@Override
	public void bold() throws CompilerException {
		try {
			System.out.println("[This is bold]");
			MyCompiler.parseTree.push(MyCompiler.currentToken);
			System.out.println("PUSHED " + MyCompiler.currentToken + " TO PARSE TREE");
			//get text
			MyCompiler.lexicalAnalyzer.ridWhiteSpace();
			MyCompiler.lexicalAnalyzer.getNextToken();
			if (Tokens.isText(MyCompiler.currentToken.substring(0,1))) {
				MyCompiler.parseTree.push(MyCompiler.currentToken);
				System.out.println("PUSHED " + MyCompiler.currentToken + " TO PARSE TREE");
				//get bold end
				MyCompiler.lexicalAnalyzer.ridWhiteSpace();
				MyCompiler.lexicalAnalyzer.getNextToken();
				if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.BOLD)) {
					MyCompiler.parseTree.push(MyCompiler.currentToken);
					System.out.println("PUSHED " + MyCompiler.currentToken + " TO PARSE TREE");
					MyCompiler.lexicalAnalyzer.ridWhiteSpace();
					MyCompiler.lexicalAnalyzer.getNextToken();
					}
				else
					throw new CompilerException("Error. A syntax error has occured."
						+ " Bold must end with **.");
			}
			else
				throw new CompilerException("Error. A syntax error has occured."
						+ " Bold must be followed by text.");
			
		}
		catch (CompilerException e) {
			System.out.println(e.getErrorMessage());
			System.exit(1);
		}
	}

	//@Override
	public void italics() throws CompilerException {
		try {
			System.out.println("[This is italics]");
			MyCompiler.parseTree.push(MyCompiler.currentToken);
			System.out.println("PUSHED " + MyCompiler.currentToken + " TO PARSE TREE");
			//get text
			MyCompiler.lexicalAnalyzer.ridWhiteSpace();
			MyCompiler.lexicalAnalyzer.getNextToken();
			if (Tokens.isText(MyCompiler.currentToken.substring(0,1))) {
				MyCompiler.parseTree.push(MyCompiler.currentToken);
				System.out.println("PUSHED " + MyCompiler.currentToken + " TO PARSE TREE");
				//get bold end
				MyCompiler.lexicalAnalyzer.ridWhiteSpace();
				MyCompiler.lexicalAnalyzer.getNextToken();
				if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.ITALICS)) {
					MyCompiler.parseTree.push(MyCompiler.currentToken);
					System.out.println("PUSHED " + MyCompiler.currentToken + " TO PARSE TREE");
					MyCompiler.lexicalAnalyzer.ridWhiteSpace();
					MyCompiler.lexicalAnalyzer.getNextToken();
					}
				else
					throw new CompilerException("Error. A syntax error has occured."
						+ " Italics must end with *.");
			}
			else
				throw new CompilerException("Error. A syntax error has occured."
						+ " Italics must be followed by text.");
			
		}
		catch (CompilerException e) {
			System.out.println(e.getErrorMessage());
			System.exit(1);
		}
	}

	//@Override
	public void listitem() throws CompilerException {
		try {
			System.out.println("[This is listitem]");
			MyCompiler.parseTree.push(MyCompiler.currentToken);
			System.out.println("PUSHED " + MyCompiler.currentToken + " TO PARSE TREE");
			//inner item
			MyCompiler.lexicalAnalyzer.ridWhiteSpace();
			MyCompiler.lexicalAnalyzer.getNextToken();
			System.out.println("[listitem -> innerItem]");
			if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.USEB) ||
					MyCompiler.currentToken.equalsIgnoreCase(Tokens.BOLD) ||
					MyCompiler.currentToken.equalsIgnoreCase(Tokens.ITALICS) ||
					MyCompiler.currentToken.equalsIgnoreCase(Tokens.LINKB) ||
					Tokens.isText(MyCompiler.currentToken.substring(0,1))) {
				innerItem();
			}
			else
				throw new CompilerException("Error. A syntax error has occured. "
						+ "Lists can only contain variable usage, bold, italics, links, or text.");
			if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.LISTITEME)) {
				MyCompiler.parseTree.push(MyCompiler.currentToken);
				System.out.println("PUSHED " + MyCompiler.currentToken + " TO PARSE TREE");
				MyCompiler.lexicalAnalyzer.ridWhiteSpace();
				MyCompiler.lexicalAnalyzer.getNextToken();
			}
			else
				throw new CompilerException("Error. A syntax error has occured."
						+ " Invalid list item syntax.");
			if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.LISTITEMB)) {
				System.out.println("[repeat. listitem -> listitem]");
				listitem();
			}
			
		}
		catch (CompilerException e) {
			System.out.println(e.getErrorMessage());
			System.exit(1);
		}
	}

	//@Override
	public void innerItem() throws CompilerException {
		try {
			System.out.println("[This is innerItem");
			if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.USEB)) {
				System.out.println("[innerItem -> variableUse]");
				variableUse();
				if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.USEB) ||
						MyCompiler.currentToken.equalsIgnoreCase(Tokens.BOLD) ||
						MyCompiler.currentToken.equalsIgnoreCase(Tokens.ITALICS) ||
						MyCompiler.currentToken.equalsIgnoreCase(Tokens.LINKB) ||
						Tokens.isText(MyCompiler.currentToken.substring(0,1))) {
					innerItem();
				}
			}
			else if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.BOLD)) {
				System.out.println("[innerItem -> bold]");
				bold();
				if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.USEB) ||
						MyCompiler.currentToken.equalsIgnoreCase(Tokens.BOLD) ||
						MyCompiler.currentToken.equalsIgnoreCase(Tokens.ITALICS) ||
						MyCompiler.currentToken.equalsIgnoreCase(Tokens.LINKB) ||
						Tokens.isText(MyCompiler.currentToken.substring(0,1))) {
					innerItem();
				}
			}
			else if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.ITALICS)) {
				System.out.println("[innerItem -> italics]");
				italics();
				if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.USEB) ||
						MyCompiler.currentToken.equalsIgnoreCase(Tokens.BOLD) ||
						MyCompiler.currentToken.equalsIgnoreCase(Tokens.ITALICS) ||
						MyCompiler.currentToken.equalsIgnoreCase(Tokens.LINKB) ||
						Tokens.isText(MyCompiler.currentToken.substring(0,1))) {
					innerItem();
				}
			}
			else if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.LINKB)) {
				System.out.println("[innerItem -> link]");
				link();
				if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.USEB) ||
						MyCompiler.currentToken.equalsIgnoreCase(Tokens.BOLD) ||
						MyCompiler.currentToken.equalsIgnoreCase(Tokens.ITALICS) ||
						MyCompiler.currentToken.equalsIgnoreCase(Tokens.LINKB) ||
						Tokens.isText(MyCompiler.currentToken.substring(0,1))) {
					innerItem();
				}
			}
			else if (Tokens.isText(MyCompiler.currentToken.substring(0,1))) {
				System.out.println("[innerItem -> text, innerItem]");
				MyCompiler.parseTree.push(MyCompiler.currentToken);
				System.out.println("PUSHED " + MyCompiler.currentToken + " TO PARSE TREE");				MyCompiler.lexicalAnalyzer.ridWhiteSpace();
				MyCompiler.lexicalAnalyzer.getNextToken();
				if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.USEB) ||
						MyCompiler.currentToken.equalsIgnoreCase(Tokens.BOLD) ||
						MyCompiler.currentToken.equalsIgnoreCase(Tokens.ITALICS) ||
						MyCompiler.currentToken.equalsIgnoreCase(Tokens.LINKB) ||
						Tokens.isText(MyCompiler.currentToken.substring(0,1))) {
					System.out.println("[repeat innerItem]");
					innerItem();
				}
				System.out.println("finish innerItem");
			}
			else {
				throw new CompilerException("Error. Invalid inner item.");
			}
		}
		catch (CompilerException e) {
			System.out.println(e.getErrorMessage());
			System.exit(1);
		}
	}

	//@Override
	public void link() throws CompilerException {
		try {
			System.out.println("[This is link]");
			MyCompiler.parseTree.push(MyCompiler.currentToken);
			System.out.println("PUSHED " + MyCompiler.currentToken + " TO PARSE TREE");
			//get link text
			MyCompiler.lexicalAnalyzer.ridWhiteSpace();
			MyCompiler.lexicalAnalyzer.getNextToken();
			System.out.println("CURRENT TOKEN: " + MyCompiler.currentToken);
			if (Tokens.isText(MyCompiler.currentToken.substring(0,1))) {
				MyCompiler.parseTree.push(MyCompiler.currentToken);
				System.out.println("PUSHED " + MyCompiler.currentToken + " TO PARSE TREE");
				//get link end
				MyCompiler.lexicalAnalyzer.ridWhiteSpace();
				MyCompiler.lexicalAnalyzer.getNextToken();
				System.out.println("CURRENT TOKEN2: " + MyCompiler.currentToken);
				if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.LINKE)) {
					MyCompiler.parseTree.push(MyCompiler.currentToken);
					System.out.println("PUSHED " + MyCompiler.currentToken + " TO PARSE TREE");
					//get address begin
					MyCompiler.lexicalAnalyzer.ridWhiteSpace();
					MyCompiler.lexicalAnalyzer.getNextToken();
					System.out.println("CURRENT TOKEN3: " + MyCompiler.currentToken);
					if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.ADDRESSB)) {
						MyCompiler.parseTree.push(MyCompiler.currentToken);
						System.out.println("PUSHED " + MyCompiler.currentToken + " TO PARSE TREE");
						//get address text
						MyCompiler.lexicalAnalyzer.ridWhiteSpace();
						MyCompiler.lexicalAnalyzer.getNextToken();
						System.out.println("CURRENTTOKEN4: " + MyCompiler.currentToken);
						if(Tokens.isText(MyCompiler.currentToken.substring(0,1))) {
							MyCompiler.parseTree.push(MyCompiler.currentToken);
							System.out.println("PUSHED " + MyCompiler.currentToken + " TO PARSE TREE");
							//get address end
							MyCompiler.lexicalAnalyzer.ridWhiteSpace();
							MyCompiler.lexicalAnalyzer.getNextToken();
							if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.ADDRESSE)) {
								MyCompiler.parseTree.push(MyCompiler.currentToken);
								System.out.println("PUSHED " + MyCompiler.currentToken + " TO PARSE TREE");
								MyCompiler.lexicalAnalyzer.ridWhiteSpace();
								MyCompiler.lexicalAnalyzer.getNextToken();
							}
							else
								throw new CompilerException("Error. A syntax error has occured."
										+ " link address must end with )");
						}
						else
							throw new CompilerException("Error. A syntax error has occured."
									+ " link address must contain text.");
					}
					else
						throw new CompilerException("Error. A syntax error has occured."
								+ " link must contain an address");
				}
				else
					throw new CompilerException("Error. A syntax error has occured."
							+ " link text mustend with ]");
			}
			else
				throw new CompilerException("Error. A syntax error has occured."
						+ " Invalid link syntax. Link must be followed by text, ], (, text, and )");
		}
		catch (CompilerException e) {
			System.out.println(e.getErrorMessage());
			System.exit(1);
		}
	}

	//@Override
	public void audio() throws CompilerException {
		try {
			System.out.println("[This is audio]");
			MyCompiler.parseTree.push(MyCompiler.currentToken);
			System.out.println("PUSHED " + MyCompiler.currentToken + " TO PARSE TREE");
			//get audio address begin
			MyCompiler.lexicalAnalyzer.ridWhiteSpace();
			MyCompiler.lexicalAnalyzer.getNextToken();
			if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.ADDRESSB)) {
				MyCompiler.parseTree.push(MyCompiler.currentToken);
				System.out.println("PUSHED " + MyCompiler.currentToken + " TO PARSE TREE");
				//get audio text
				MyCompiler.lexicalAnalyzer.ridWhiteSpace();
				MyCompiler.lexicalAnalyzer.getNextToken();
				if (Tokens.isText(MyCompiler.currentToken.substring(0,1))) {
					MyCompiler.parseTree.push(MyCompiler.currentToken);
					System.out.println("PUSHED " + MyCompiler.currentToken + " TO PARSE TREE");
					//get audio address end
					MyCompiler.lexicalAnalyzer.ridWhiteSpace();
					MyCompiler.lexicalAnalyzer.getNextToken();
					if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.ADDRESSE)) {
						MyCompiler.parseTree.push(MyCompiler.currentToken);
						System.out.println("PUSHED " + MyCompiler.currentToken + " TO PARSE TREE");
						MyCompiler.lexicalAnalyzer.ridWhiteSpace();
						MyCompiler.lexicalAnalyzer.getNextToken();
					}
					else
						throw new CompilerException("Error. A syntax error has occured."
								+ " audio address must end with )");
					}
				else
					throw new CompilerException("Error. A syntax error has occured."
						+ " audio address must contain text");
			}
			else
				throw new CompilerException("Error. A syntax error has occured."
						+ " audio must contain address");
		}
		catch (CompilerException e) {
			System.out.println(e.getErrorMessage());
			System.exit(1);
		}
	}

	//@Override
	public void video() throws CompilerException {
		try {
			System.out.println("[This is video]");
			MyCompiler.parseTree.push(MyCompiler.currentToken);
			System.out.println("PUSHED " + MyCompiler.currentToken + " TO PARSE TREE");
			//get video address begin
			MyCompiler.lexicalAnalyzer.ridWhiteSpace();
			MyCompiler.lexicalAnalyzer.getNextToken();
			if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.ADDRESSB)) {
				MyCompiler.parseTree.push(MyCompiler.currentToken);
				System.out.println("PUSHED " + MyCompiler.currentToken + " TO PARSE TREE");
				//get audio text
				MyCompiler.lexicalAnalyzer.ridWhiteSpace();
				MyCompiler.lexicalAnalyzer.getNextToken();
				if (Tokens.isText(MyCompiler.currentToken.substring(0,1))) {
					MyCompiler.parseTree.push(MyCompiler.currentToken);
					System.out.println("PUSHED " + MyCompiler.currentToken + " TO PARSE TREE");
					//get audio address end
					MyCompiler.lexicalAnalyzer.ridWhiteSpace();
					MyCompiler.lexicalAnalyzer.getNextToken();
					if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.ADDRESSE)) {
						MyCompiler.parseTree.push(MyCompiler.currentToken);
						System.out.println("PUSHED " + MyCompiler.currentToken + " TO PARSE TREE");
						MyCompiler.lexicalAnalyzer.ridWhiteSpace();
						MyCompiler.lexicalAnalyzer.getNextToken();
					}
					else
						throw new CompilerException("Error. A syntax error has occured."
								+ " video address must end with )");
					}
				else
					throw new CompilerException("Error. A syntax error has occured."
						+ " video address must contain text");
			}
			else
				throw new CompilerException("Error. A syntax error has occured."
						+ " video must contain address");
		}
		catch (CompilerException e) {
			System.out.println(e.getErrorMessage());
			System.exit(1);
		}
	}

	//@Override
	public void newline() throws CompilerException {
		if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.NEWLINE)) {
			MyCompiler.parseTree.push(MyCompiler.currentToken);
		MyCompiler.lexicalAnalyzer.ridWhiteSpace();
		MyCompiler.lexicalAnalyzer.getNextToken();
		}
		else
			throw new CompilerException("Error. A syntax error has occured."
					+ "Invalid newline.");
	}

}
