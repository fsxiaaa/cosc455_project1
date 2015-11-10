package edu.towson.cis.cosc455.ftchen.project1.implementation;

import edu.towson.cis.cosc455.ftchen.project1.interfaces.LexicalAnalyzer;

public class MyLexicalAnalyzer implements LexicalAnalyzer {

	private String sourceLine = "";
	private int position = currentPosition;
	private String nextChar = nextCharacter;
	
	public void start(String line)
	{
		System.out.println("Running");
		sourceLine = line;
		position = 0;
		System.out.println("line is: " + line);
		
		getCharacter();
		while (position < sourceLine.length()) {
			System.out.println("Position is: " + position);
			if (isSpecial(nextChar)) {
				System.out.println("is Special");
				getNextToken();
				if(lookupToken()) {
					addToken();
				}
			}
			else
			{
				System.out.println("entered else. start getting other characters.");
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
			System.out.println(nextChar);
			MyCompiler.nextToken = MyCompiler.nextToken + nextChar;
			System.out.println("added " + nextChar + " as token");
			System.out.println("nextToken looks like " + MyCompiler.nextToken);
			getCharacter();
		}
	}
	
	public void getNextText() {
		MyCompiler.nextToken = nextChar;
		getCharacter();
		while (!isSpecial(nextChar) && position < sourceLine.length()) {
			System.out.println(nextChar);
			MyCompiler.nextToken = MyCompiler.nextToken + nextChar;
			System.out.println("added " + nextChar + " to nextToken");
			System.out.println("nextToken looks like " + MyCompiler.nextToken);
			getCharacter();
		}
	}

	//@Override
	public void getCharacter() {
		if (position < sourceLine.length()) {
			System.out.println("position is: " + position);
			nextChar = sourceLine.substring(position, position+1);
			System.out.println("Next character is:" + nextChar);
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
			System.out.println("IS SPACE!");
			return true;
		}
		return false;
	}
	
	public boolean isSpecial(String c) {
		if (c.equals("#") || c.equals("^") || c.equals("<") || c.equals("{") || c.equals("$") || c.equals("*") ||
				c.equals("+") || c.equals("~") || c.equals("[") || c.equals("@") || c.equals("%")) {
			System.out.println("IS SPECIAL");
			return true;
		}
		else {
			return false;
		}
		
	}

	//@Override
	public boolean lookupToken() {
		System.out.println("looking up: " + MyCompiler.nextToken);
		for (String s : MyCompiler.tokens)
		{
			if (s.equalsIgnoreCase(MyCompiler.nextToken)) {
				System.out.println("Valid token!");
				return true;
			}
		}
		System.out.println("Invalid token!");
		//Error Message -Lexical Error
		return false;
	}
	
	public void addToken (){
		MyCompiler.gatheredTokens.add(MyCompiler.nextToken);
		System.out.println("added " + MyCompiler.nextToken + " to gatheredTokens");
	}
}
