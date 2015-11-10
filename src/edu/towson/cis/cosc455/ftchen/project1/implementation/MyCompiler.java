package edu.towson.cis.cosc455.ftchen.project1.implementation;

import java.io.*;
import java.util.*;

public class MyCompiler {

	public static String currentToken = "";
	public static String nextToken = "";
	public static ArrayList <String> tokens = new ArrayList <String>();
	public static ArrayList <String> gatheredTokens = new ArrayList <String>();
	//MyLexicalAnalyzer lexicalAnalyzer = new MyLexicalAnalyzer();
	//MySyntaxAnalyzer syntaxAnalyzer = new MySyntaxAnalyzer();
	//MySemanticAnalyzer semanticAnalyzer = new MySemanticAnalyzer();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("runs");
		
		MyLexicalAnalyzer lexicalAnalyzer = new MyLexicalAnalyzer();
		MySyntaxAnalyzer syntaxAnalyzer = new MySyntaxAnalyzer();
		MySemanticAnalyzer semanticAnalyzer = new MySemanticAnalyzer();
		
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
		
		
		
		
		if (args[0].substring(args[0].length()-4,args[0].length()).equals(".mkd")){
			//VALID FILE - READ FROM FILE
			FileReader fr;
			BufferedReader br;
			try {
				fr = new FileReader(args[0]);
				br = new BufferedReader(fr);
				String sourceLine = null;
				//while ((sourceLine = br.readLine()) != null){
				sourceLine = br.readLine(); //REMOVE
					//Call Lexical Analyzer
					System.out.println("sourceLine: "+ sourceLine);
					lexicalAnalyzer.start(sourceLine);
				//}
				
			} catch (FileNotFoundException e) {
				System.out.println("Error. File not found.");
				System.exit(0);
			} catch (IOException e) {
				System.out.println("Error. I/O Exception occurred. Failed to read from file.");
				System.exit(0);
			}
			
			
			//Call Lexical Analyzer
			lexicalAnalyzer.getNextToken();
			System.out.println("Lexical Analyzer");
		}
		else {
			//INVALID FILE
			System.out.println("Error, invalid file. File extension must be .mkd");
		}
		
		System.out.println(args[0]);
		
		System.out.println("still runs");
	}

}
