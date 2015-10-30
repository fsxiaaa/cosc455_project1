package edu.towson.cis.cosc455.ftchen.project1.implementation;

import java.io.*;

public class MyCompiler {

	public static String currentToken = "";
	public static String nextToken = "";
	//MyLexicalAnalyzer lexicalAnalyzer = new MyLexicalAnalyzer();
	//MySyntaxAnalyzer syntaxAnalyzer = new MySyntaxAnalyzer();
	//MySemanticAnalyzer semanticAnalyzer = new MySemanticAnalyzer();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("runs");
		
		MyLexicalAnalyzer lexicalAnalyzer = new MyLexicalAnalyzer();
		MySyntaxAnalyzer syntaxAnalyzer = new MySyntaxAnalyzer();
		MySemanticAnalyzer semanticAnalyzer = new MySemanticAnalyzer();
		
		if (args[0].substring(args[0].length()-4,args[0].length()).equals(".mkd")){
			//VALID FILE - READ FROM FILE
			FileReader fr;
			BufferedReader br;
			try {
				fr = new FileReader(args[0]);
				br = new BufferedReader(fr);
				String sourceLine = null;
				while ((sourceLine = br.readLine()) != null){
					//Call Lexical Analyzer
					lexicalAnalyzer.start(sourceLine);
				}
				
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
