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
	public void getNextToken() {
		getCharacter();

		//while (position < sourceLine.length()) {
			if (isSpecial(nextChar)) {
				MyCompiler.nextToken = nextChar;
				if (!nextIsAddress(nextChar)) {
					getCharacter();
					while (!isSpace(nextChar) && position < sourceLine.length()) {
						MyCompiler.nextToken = MyCompiler.nextToken + nextChar;
						getCharacter();
					}
					System.out.println("~~~~~~~~" + MyCompiler.nextToken);
				}
				try {
					if (lookupToken()) {
						System.out.println("LOOKING UP");
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
				System.out.println("not special. getting next text");
				System.out.println("!!!!!!!!!" + MyCompiler.nextToken);
				getNextText();
				addToken();
			}
		//}
	}
	
	public void getNextText() {
		MyCompiler.nextToken = nextChar;
		//System.out.println("next token is::: " + MyCompiler.nextToken);
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
			//System.out.println("nextChar is: " + nextChar);
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
	
	public boolean nextIsAddress(String c) {
		if (c.equals("@") || c.equals("%") || c.equals("[") || c.equals("]") || c.equals("(") )
			return true;
		else
			return false;
	}
	
	public void ridWhiteSpace() {
		while (isSpace(nextChar)) {
			System.out.println("Rid white space.");
			getCharacter();
		}
		//reset position for other methods
		position--;
	}

	//@Override
	public boolean lookupToken() {
		//System.out.println("Check " + MyCompiler.nextToken);
		for (String s : MyCompiler.tokens)
		{
			if (s.equalsIgnoreCase(MyCompiler.nextToken)) {
				//System.out.println(s + " equals " + MyCompiler.nextToken + ". Return true.");
				return true;
			}
		}
		System.out.println(MyCompiler.nextToken + " :: All failed, returning false.");
		return false;
	}
	
	public void addToken (){
		MyCompiler.gatheredTokens.add(MyCompiler.nextToken);
		MyCompiler.currentToken = MyCompiler.nextToken;
		System.out.println("Lex says, currentToken is now: " + MyCompiler.currentToken);
		System.out.println("Lex says, nextChar is: " + nextChar);
	}
}
