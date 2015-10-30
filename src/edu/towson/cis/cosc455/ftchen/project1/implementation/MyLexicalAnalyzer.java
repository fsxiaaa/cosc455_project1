package edu.towson.cis.cosc455.ftchen.project1.implementation;

import edu.towson.cis.cosc455.ftchen.project1.interfaces.LexicalAnalyzer;

public class MyLexicalAnalyzer implements LexicalAnalyzer {

	private String sourceLine = "";
	private int position = currentPosition;
	private String nextChar = nextCharacter;
	
	public void start(String line)
	{
		sourceLine = line;
		
		getNonBlank();
		getCharacter();
	}
	
	//@Override
	public void getNextToken() {
		// TODO Auto-generated method stub
		getCharacter();
		lookupToken();
	}

	//@Override
	public void getCharacter() {
		// TODO Auto-generated method stub
		if (position < sourceLine.length()) {
			nextChar = sourceLine.substring(position, position++);
		}
		else {
			nextChar = "\n";
		}
	}
	
	public void getNonBlank() {
		while(isSpace(nextChar)) {
			getCharacter();
		}
	}

	//@Override
	public void addCharacter() {
		// TODO Auto-generated method stub
		MyCompiler.nextToken = MyCompiler.nextToken + nextChar;	
	}

	//@Override
	public boolean isSpace(String c) {
		// TODO Auto-generated method stub
		if (nextChar.equals(" ")){
			return true;
		}
		return false;
	}
	
	public boolean isSpecial(String c) {
		if (c.equals("#") || c.equals("^") || c.equals("<") || c.equals("{") || c.equals("$") || c.equals("*") ||
				c.equals("+") || c.equals("~") || c.equals("[") || c.equals("@") || c.equals("%"))
			return true;
		else {
			return false;
		}
		
	}

	//@Override
	public boolean lookupToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
