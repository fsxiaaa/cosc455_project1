package edu.towson.cis.cosc455.ftchen.project1.implementation;

import java.io.*;
import java.util.*;

public class MyCompiler {

	public static String currentToken = "";
	public static String nextToken = "";
	public static ArrayList <String> tokens = new ArrayList <String>();
	public static ArrayList <String> beginTokens = new ArrayList <String>();
	public static ArrayList <String> endTokens = new ArrayList <String>();
	public static ArrayList <String> gatheredTokens = new ArrayList <String>();
	public static MyLexicalAnalyzer lexicalAnalyzer = new MyLexicalAnalyzer();
	public static MySyntaxAnalyzer syntaxAnalyzer = new MySyntaxAnalyzer();
	public static MySemanticAnalyzer semanticAnalyzer = new MySemanticAnalyzer();
	public static ArrayList <String> parseTree = new ArrayList <String>();
	
	public static void main(String[] args) {
		
		/**
		 * Generate ArrayList of valid tokens, ArrayList of valid beginTokens, and ArrayList of end endTokens
		 */
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
		
		/**
		 * PRE-PROCESSING
		 * Check file extension and if possible, read from file.
		 * Throw errors if file not found or if failed to read from file.
		 */
		if (args[0].substring(args[0].length()-4,args[0].length()).equals(".mkd")){
			/**
			 * Valid file - read.
			 */
			FileReader fr;
			BufferedReader br;
			try {
				fr = new FileReader(args[0]);
				br = new BufferedReader(fr);
				String sourceLine = "";
				String line = "";
				while ( (line = br.readLine()) != null){
					/**
					 * generate all sourceLine from document for easy parsing
					 */
					sourceLine = sourceLine + line;
				}
				/**
				 * Call Lexical Analyzer
				 */
				System.out.println(sourceLine);
				lexicalAnalyzer.start(sourceLine);
			}
			catch (FileNotFoundException e) {
				System.out.println("Error. File not found.");
				System.exit(1);
			}
			catch (IOException e) {
				System.out.println("Error. I/O Exception occurred. Failed to read from file.");
				System.exit(1);
			}
			
			/*
			 */
			System.out.println();
			System.out.println("The gathered tokens are: ");
			for(String s : gatheredTokens) {
				System.out.println("::" + s + "::");
			}
			/**
			 * POST-PROCESSING
			 * Use abstract syntax tree (parseTree) and translate to HTML5 file
			 * Generate and write file, then close.
			 */
			String filename = args[0].substring(0,args[0].length()-4) + ".html";
			try {
				FileWriter fileWriter = new FileWriter(filename);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				
				/**
				 * Read from parseTree, add to buffered reader to generate HTML
				 */
				bufferedWriter.write(semanticAnalyzer.generateHTML());
				
				//bufferedWriter.write("~");
				//bufferedWriter.newLine();
				
				/**
				 * close bufferedWriter
				 */
				bufferedWriter.close();
				
			}
			catch (IOException e) {
				System.out.println("Error. I/O Exception occurred. Failed to write to file.");
				System.exit(1);
			}
		}
		else {
			/**
			 * INVALID FILE
			 */
			System.out.println("Error, invalid file. File extension must be .mkd");
		}
	}

}
