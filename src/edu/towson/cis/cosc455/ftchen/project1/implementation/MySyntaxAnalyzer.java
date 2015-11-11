package edu.towson.cis.cosc455.ftchen.project1.implementation;

import edu.towson.cis.cosc455.ftchen.project1.interfaces.SyntaxAnalyzer;

public class MySyntaxAnalyzer implements SyntaxAnalyzer {

	//@Override
	public void markdown() throws CompilerException {
		try {
			if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.DOCB)) {
				MyCompiler.parseTree.push(MyCompiler.currentToken);
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
				}
				if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.PARAB)) {
					System.out.println("markdown -> body");
					body();
				}
				//end code
				if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.DOCE)) {
					MyCompiler.parseTree.push(MyCompiler.currentToken);
					MyCompiler.lexicalAnalyzer.getNextToken();
				}
				else {
					System.out.println("The current token is: " + MyCompiler.currentToken);
					throw new CompilerException("Error. The file must end with #END"
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
			if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.HEAD)) {
				MyCompiler.parseTree.push(MyCompiler.currentToken);
				MyCompiler.lexicalAnalyzer.getNextToken();
				title(); //get title
				if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.HEAD)) {
					MyCompiler.parseTree.push(MyCompiler.currentToken);
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
			if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.TITLEB)) {
				MyCompiler.parseTree.push(MyCompiler.currentToken);
				MyCompiler.lexicalAnalyzer.getNextToken();
				//get text
				if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.TITLEE)) {
					MyCompiler.parseTree.push(MyCompiler.currentToken);
				}
				else
					throw new CompilerException("Error. < tag must end with a > tag."
							+ " A syntax error has occured.");
			}
		}
		catch (CompilerException e) {
			System.out.println(e.getErrorMessage());
			System.exit(1);
		}
	}

	//@Override
	public void body() throws CompilerException {
		try {
			//inner text, body | paragraph, body | newline, body | null
			if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.PARAB)) {
				
			}
			else
				throw new CompilerException("Error. Body must start with { and end with }");
		}
		catch (CompilerException e) {
			System.out.println(e.getErrorMessage());
			System.exit(1);
		}
	}

	//@Override
	public void paragraph() throws CompilerException {
		try {
			if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.PARAB)) {
				MyCompiler.parseTree.push(MyCompiler.currentToken);
				MyCompiler.lexicalAnalyzer.getNextToken();
				variableDefine();
				innerText();
				if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.PARAE)) {
					MyCompiler.parseTree.push(MyCompiler.currentToken);
				}
				else
					throw new CompilerException("Error. { tag must end with a } tag."
							+ " A syntax error has occured.");
			}
		}
		catch (CompilerException e) {
			System.out.println(e.getErrorMessage());
			System.exit(1);
		}
	}

	//@Override
	public void innerText() throws CompilerException {
		//<variable-use> <inner-text> | <bold> <inner-text> | <italics> <inner-text>
		//| <listitem> <inner-text> | <audio> <inner-text> | <video> <inner-text>
		//| <link> <inner-text> | <newline> <inner-text> | TEXT <inner-text> |ε
	}

	//@Override
	public void variableDefine() throws CompilerException {
		try {
			if (MyCompiler.currentToken.equalsIgnoreCase(Tokens.DEFB)) {
				MyCompiler.parseTree.push(MyCompiler.currentToken);
				MyCompiler.lexicalAnalyzer.getNextToken();
				//getText
				//
				//
				//
				//
			}
			else
				throw new CompilerException("Error. $DEF must end with $END. "
						+ "A syntax error has occured.");
		}
		catch (CompilerException e) {
			System.out.println(e.getErrorMessage());
			System.exit(1);
		}
	}

	//@Override
	public void variableUse() throws CompilerException {

	}

	//@Override
	public void bold() throws CompilerException {

	}

	//@Override
	public void italics() throws CompilerException {

	}

	//@Override
	public void listitem() throws CompilerException {

	}

	//@Override
	public void innerItem() throws CompilerException {

	}

	//@Override
	public void link() throws CompilerException {

	}

	//@Override
	public void audio() throws CompilerException {

	}

	//@Override
	public void video() throws CompilerException {

	}

	//@Override
	public void newline() throws CompilerException {

	}

}
