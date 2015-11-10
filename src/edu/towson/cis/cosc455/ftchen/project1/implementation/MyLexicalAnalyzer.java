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
		System.out.println("line is: " + line);
		
		getCharacter();
		if (isSpecial(nextChar)) {
			System.out.println("is Special");
			getNextToken();
			if(lookupToken()) {
				addToken();
			}
		}
		else
		{
			System.out.println("entered else");
		}
	}
	
	//@Override
	public void getNextToken() {
		// TODO Auto-generated method stub
		MyCompiler.nextToken = nextChar;
		getCharacter();
		while (!isSpace(nextChar)) {
			System.out.println(nextChar);
			MyCompiler.nextToken = MyCompiler.nextToken + nextChar;
			System.out.println("added " + nextChar);
			System.out.println("nextToken looks like " + MyCompiler.nextToken);
			getCharacter();
		}
	}

	//@Override
	public void getCharacter() {
		// TODO Auto-generated method stub
		if (position < sourceLine.length()) {
			nextChar = sourceLine.substring(position, position+1);
			System.out.println("Next character is:" + nextChar);
			position++;
		}
		else {
			nextChar = "\n";
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
		// TODO Auto-generated method stub
		System.out.println("looking up token");
		for (int i = 0; i < MyCompiler.tokens.size(); i++) {
			System.out.println("tokens size is " + MyCompiler.tokens.size() + " i = " + i);
			if (MyCompiler.tokens.get(i).equalsIgnoreCase(MyCompiler.nextToken)) {
				System.out.println("Valid token!");
				return true;
			}
			else {
				System.out.println("Invalid token!");
				return false;
			}
		}
		return false;
	}
	
	public void addToken (){
		MyCompiler.gatheredTokens.add(MyCompiler.nextToken);
	}
}
