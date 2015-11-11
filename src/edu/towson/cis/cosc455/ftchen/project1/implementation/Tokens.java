package edu.towson.cis.cosc455.ftchen.project1.implementation;

import java.util.*;

public class Tokens {
	public static final String DOCB = "#BEGIN";
	public static final String DOCE = "#END";
	public static final String HEAD = "^";
	public static final String TITLEB = "<";
	public static final String TITLEE = ">";
	public static final String PARAB = "{";
	public static final String PARAE = "}";
	public static final String DEFB = "$DEF";
	public static final String DEFUSEE = "$END";
	public static final String EQSIGN = "=";
	public static final String USEB = "$USE";
	public static final String BOLD = "**";
	public static final String ITALICS = "*";
	public static final String LISTITEMB = "+";
	public static final String LISTITEME = ";";
	public static final String NEWLINE = "~";
	public static final String LINKB = "[";
	public static final String LINKE = "]";
	public static final String AUDIO = "@";
	public static final String VIDEO = "%";
	public static final String ADDRESSB = "(";
	public static final String ADDRESSE = ")";
	//TEXT
	
	public static boolean isText(String s) {
		if (s.substring(0,1).equalsIgnoreCase("a") ||
				s.equalsIgnoreCase("b") ||
				s.equalsIgnoreCase("c") ||
				s.equalsIgnoreCase("d") ||
				s.equalsIgnoreCase("e") ||
				s.equalsIgnoreCase("f") ||
				s.equalsIgnoreCase("g") ||
				s.equalsIgnoreCase("h") ||
				s.equalsIgnoreCase("i") ||
				s.equalsIgnoreCase("j") ||
				s.equalsIgnoreCase("k") ||
				s.equalsIgnoreCase("l") ||
				s.equalsIgnoreCase("m") ||
				s.equalsIgnoreCase("n") ||
				s.equalsIgnoreCase("o") ||
				s.equalsIgnoreCase("p") ||
				s.equalsIgnoreCase("q") ||
				s.equalsIgnoreCase("r") ||
				s.equalsIgnoreCase("s") ||
				s.equalsIgnoreCase("t") ||
				s.equalsIgnoreCase("u") ||
				s.equalsIgnoreCase("v") ||
				s.equalsIgnoreCase("w") ||
				s.equalsIgnoreCase("x") ||
				s.equalsIgnoreCase("y") ||
				s.equalsIgnoreCase("z") ||
				s.equalsIgnoreCase("0") ||
				s.equalsIgnoreCase("1") ||
				s.equalsIgnoreCase("2") ||
				s.equalsIgnoreCase("3") ||
				s.equalsIgnoreCase("4") ||
				s.equalsIgnoreCase("5") ||
				s.equalsIgnoreCase("6") ||
				s.equalsIgnoreCase("7") ||
				s.equalsIgnoreCase("8") ||
				s.equalsIgnoreCase("9") ||
				s.equalsIgnoreCase(",") ||
				s.equalsIgnoreCase(".") ||
				s.equalsIgnoreCase("\"") ||
				s.equalsIgnoreCase(":") ||
				s.equalsIgnoreCase("?") ||
				s.equalsIgnoreCase("!") ||
				s.equalsIgnoreCase("/") ||
				s.equalsIgnoreCase("\n") ||
				s.equalsIgnoreCase("\t"))
			return true;
		else
			return false;
	}
}
