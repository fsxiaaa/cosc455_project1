package edu.towson.cis.cosc455.ftchen.project1.implementation;

import edu.towson.cis.cosc455.ftchen.project1.interfaces.LexicalAnalyzer;

public class MyLexicalAnalyzer implements LexicalAnalyzer {

	private String sourceLine = "";
	private int position = currentPosition;
	private String nextChar = nextCharacter;
	
	public void start(String line)
	{
		sourceLine = line;
		position = 0;
		
		getCharacter();
		while (position < sourceLine.length()) {
			if (isSpecial(nextChar)) {
				getNextToken();
				try {
					if (lookupToken())
						addToken();
					else
						throw new CompilerException("Error! " + MyCompiler.nextToken + " is an invalid token. A lexical error has occured.");
				}
				catch (CompilerException e) {
					System.out.println(e.getErrorMessage());
					System.exit(0);
				}
			}
			else
			{
				getNextText();
				addToken();
			}
		}
	}
	
	//@Override
	public void getNextToken() {
		MyCompiler.nextToken = nextChar;
		getCharacter();
		while (!isSpace(nextChar) && position < sourceLine.length()) {
			MyCompiler.nextToken = MyCompiler.nextToken + nextChar;
			getCharacter();
		}
	}
	
	public void getNextText() {
		MyCompiler.nextToken = nextChar;
		getCharacter();
		while (!isSpecial(nextChar) && position < sourceLine.length()) {
			MyCompiler.nextToken = MyCompiler.nextToken + nextChar;
			getCharacter();
		}
	}

	//@Override
	public void getCharacter() {
		if (position < sourceLine.length()) {
			nextChar = sourceLine.substring(position, position+1);
			position++;
		}
	}

	//@Override
	public void addCharacter() {
		MyCompiler.nextToken = MyCompiler.nextToken + nextChar;
	}

	//@Override
	public boolean isSpace(String c) {
		if (nextChar.equals(" ")){
			return true;
		}
		return false;
	}
	
	public boolean isSpecial(String c) {
		if (c.equals("#") || c.equals("^") || c.equals("<") || c.equals("{") || c.equals("$") || c.equals("*") ||
				c.equals("+") || c.equals("~") || c.equals("[") || c.equals("@") || c.equals("%")) {
			return true;
		}
		else {
			return false;
		}
		
	}

	//@Override
	public boolean lookupToken() {
		for (String s : MyCompiler.tokens)
		{
			if (s.equalsIgnoreCase(MyCompiler.nextToken)) {
				return true;
			}
		}
		return false;
	}
	
	public void addToken (){
		MyCompiler.gatheredTokens.add(MyCompiler.nextToken);
	}
}
