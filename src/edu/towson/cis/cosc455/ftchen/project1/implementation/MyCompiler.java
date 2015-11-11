package edu.towson.cis.cosc455.ftchen.project1.implementation;

import java.io.*;
import java.util.*;

public class MyCompiler {

	public static String currentToken = "";
	public static String nextToken = "";
	public static ArrayList <String> tokens = new ArrayList <String>();
	public static ArrayList <String> gatheredTokens = new ArrayList <String>();
	public static MyLexicalAnalyzer lexicalAnalyzer = new MyLexicalAnalyzer();
	public static MySyntaxAnalyzer syntaxAnalyzer = new MySyntaxAnalyzer();
	public static MySemanticAnalyzer semanticAnalyzer = new MySemanticAnalyzer();
	public static Stack <String> parseTree = new Stack <String>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("runs");
		
		//Generate ArrayList of valid tokens
		tokens.add(Tokens.DOCB);
		tokens.add(Tokens.DOCE);
		tokens.add(Tokens.HEAD);
		tokens.add(Tokens.TITLEB);
		tokens.add(Tokens.TITLEE);
		tokens.add(Tokens.PARAB);
		tokens.add(Tokens.PARAE);
		tokens.add(Tokens.DEFB);
		tokens.add(Tokens.DEFUSEE);
		tokens.add(Tokens.EQSIGN);
		tokens.add(Tokens.USEB);
		tokens.add(Tokens.BOLD);
		tokens.add(Tokens.ITALICS);
		tokens.add(Tokens.LISTITEMB);
		tokens.add(Tokens.LISTITEME);
		tokens.add(Tokens.NEWLINE);
		tokens.add(Tokens.LINKB);
		tokens.add(Tokens.LINKE);
		tokens.add(Tokens.AUDIO);
		tokens.add(Tokens.VIDEO);
		tokens.add(Tokens.ADDRESSB);
		tokens.add(Tokens.ADDRESSE);
		
		//Check file extension and if possible, read from file
		if (args[0].substring(args[0].length()-4,args[0].length()).equals(".mkd")){
			//VALID FILE - READ FROM FILE
			FileReader fr;
			BufferedReader br;
			try {
				fr = new FileReader(args[0]);
				br = new BufferedReader(fr);
				String sourceLine = "";
				String line = "";
				while ( (line = br.readLine()) != null){
					//generate all sourceLine from document for easy parsing
					sourceLine = sourceLine + line;
				}
				//Call Lexical Analyzer
				lexicalAnalyzer.start(sourceLine);
				
			} catch (FileNotFoundException e) {
				System.out.println("Error. File not found.");
				System.exit(1);
			} catch (IOException e) {
				System.out.println("Error. I/O Exception occurred. Failed to read from file.");
				System.exit(1);
			}
			
			
			//Call Lexical Analyzer
			System.out.println();
			System.out.println();
			System.out.println("The gathered tokens are: ");
			for(String s : gatheredTokens) {
				System.out.println("::" + s + "::");
			}
		}
		else {
			//INVALID FILE
			System.out.println("Error, invalid file. File extension must be .mkd");
		}
				
		System.out.println("still runs");
	}

}
