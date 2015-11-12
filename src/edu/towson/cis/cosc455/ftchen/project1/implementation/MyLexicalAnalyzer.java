package edu.towson.cis.cosc455.ftchen.project1.implementation;

import edu.towson.cis.cosc455.ftchen.project1.interfaces.LexicalAnalyzer;

public class MyLexicalAnalyzer implements LexicalAnalyzer {

	private String sourceLine = "";
	private int position = currentPosition;
	private String nextChar = nextCharacter;
	
	/**
	 * Starts lexical and syntax analysis. Calls syntaxAnalyzer.
	 * @param line
	 */
	public void start(String line)
	{
		sourceLine = line;
		position = 0;
		
		getNextToken();
		try {
			MyCompiler.syntaxAnalyzer.markdown();
		}
		catch (CompilerException e)
		{
			System.out.println(e.getErrorMessage());
			System.exit(1);
		}
	}
	
	//@Override
	/**
	 * gets next token, consider if it's a special token or just regular text
	 */
	public void getNextToken() {
		getCharacter();
		if (isSpecial(nextChar)) {
			MyCompiler.nextToken = nextChar;
			if (!nextIsAddress(nextChar)) {
				getCharacter();
				while (!isSpace(nextChar) && position < sourceLine.length()) {
					MyCompiler.nextToken = MyCompiler.nextToken + nextChar;
					getCharacter();
				}
			}
			try {
				if (lookupToken()) {
					addToken();
				}
				else
					throw new CompilerException("Error. " + MyCompiler.nextToken + " is an invalid token."
							+ " A lexical error has occured.");
			}
			catch (CompilerException e) {
				System.out.println(e.getErrorMessage());
				System.exit(1);
			}
		}	
		else{
			getNextText();
			addToken();
		}
	}
	
	/**
	 * method to get regular text
	 */
	public void getNextText() {
		MyCompiler.nextToken = nextChar;
		getCharacter();
		while (!isSpecial(nextChar) && position < sourceLine.length()) {
			MyCompiler.nextToken = MyCompiler.nextToken + nextChar;
			getCharacter();
		}
		
	}

	//@Override
	/**
	 * helper method to get character
	 */
	public void getCharacter() {
		if (position < sourceLine.length()) {
			nextChar = sourceLine.substring(position, position+1);
			position++;
		}
	}

	//@Override
	/**
	 * helper method to add character to token
	 */
	public void addCharacter() {
		MyCompiler.nextToken = MyCompiler.nextToken + nextChar;
	}

	//@Override
	/**
	 * helper method to identify space
	 */
	public boolean isSpace(String c) {
		if (nextChar.equals(" ")){
			return true;
		}
		return false;
	}
	
	/**
	 * helper method to identify special (token) starter characters
	 * @param c
	 * @return
	 */
	public boolean isSpecial(String c) {
		if (c.equals("#") || c.equals("^") || c.equals("<") || c.equals(">") || c.equals("{") || c.equals("}")|| 
				c.equals("$") || c.equals("=") || c.equals("*") || c.equals("+") || c.equals(";") || 
				c.equals("~") || c.equals("[") || c.equals("]") || c.equals("@") || c.equals("%") || 
				c.equals("(") || c.equals(")")) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * helper method to identify address starter/end tokens because they don't need whitespace-rules.
	 * @param c
	 * @return
	 */
	public boolean nextIsAddress(String c) {
		if (c.equals("@") || c.equals("%") || c.equals("[") || c.equals("]") || c.equals("(") )
			return true;
		else
			return false;
	}
	
	/**
	 * helper method to get rid of white space
	 */
	public void ridWhiteSpace() {
		while (isSpace(nextChar)) {
			getCharacter();
		}
		//reset position for other methods
		position--;
	}

	//@Override
	/**
	 * helper method to look up valid tokens
	 */
	public boolean lookupToken() {
		for (String s : MyCompiler.tokens)
		{
			if (s.equalsIgnoreCase(MyCompiler.nextToken)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * helper method to add token
	 */
	public void addToken (){
		MyCompiler.currentToken = MyCompiler.nextToken;
	}
}
