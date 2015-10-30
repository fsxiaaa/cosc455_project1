package edu.towson.cis.cosc455.ftchen.project1.implementation;

import edu.towson.cis.cosc455.ftchen.project1.interfaces.LexicalAnalyzer;

public class MyLexicalAnalyzer implements LexicalAnalyzer {

	private int position = currentPosition;
	
	public void start(String s)
	{
		System.out.println(position);
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
		while (!isSpace(nextCharacter)) {
			addCharacter();
			//position++
		}
		//currentPosition++ so that it's not a space
	}

	//@Override
	public void addCharacter() {
		// TODO Auto-generated method stub
		MyCompiler.nextToken = MyCompiler.nextToken + nextCharacter;	
	}

	//@Override
	public boolean isSpace(String c) {
		// TODO Auto-generated method stub
		if (nextCharacter.equals(" ")){
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
