package unit;

/**
 * Ifenna Ekwenem

 */
import java.util.LinkedList;
import java.util.Queue;

import framework.Token;
import framework.TokenName;

public class Lexer {
	public static void main(String[] args) {
		/** Test file
		 */
		String sentence = "let P = 1, let Q = 1, let R = 1, eval P ^ Q ^ R?";
		Queue<Token> tokens = new Lexer().tokenize(sentence);
		while (!tokens.isEmpty())
			System.out.println(tokens.remove());
	}
	/**
	 * Adequately Test to see if the code works
	 * 
	 * @param input
	 * @return
	 */

	public Queue<Token> tokenize(String input) {
		Queue<Token> tokens = new LinkedList<Token>();

		for (int i = 0; i < input.length(); i++) {

			if (Character.isWhitespace(input.charAt(i)))
				continue;

			if (input.charAt(i) == '=') {
				tokens.add(new Token(TokenName.EQUAL));
			} else if (input.charAt(i) == ',') {
				tokens.add(new Token(TokenName.COMMA));
			} else if (input.charAt(i) == '?') {
				tokens.add(new Token(TokenName.QUESTION));
			} else if (input.charAt(i) == '<') {
				if (input.charAt(i + 1) == '-') {
					if (input.charAt(i + 2) == '>') {
						tokens.add(new Token(TokenName.DOUBLE_ARROW));
						i += 2;
					}
				}
			}

			else if (input.charAt(i) == '-') {
				if (input.charAt(i + 1) == '>') {
					tokens.add(new Token(TokenName.ARROW));

					i += 1;
				}
			}

			else if (input.charAt(i) == (char) 39) {

				tokens.add(new Token(TokenName.APOSTROPHE));

			} else if (input.charAt(i) == '^') {
				tokens.add(new Token(TokenName.CARET));
			}

			else if (input.charAt(i) == '(') {
				tokens.add(new Token(TokenName.OPEN_PAREN));
			} else if (input.charAt(i) == ')') {
				tokens.add(new Token(TokenName.CLOSE_PAREN));
			}
			/**
			 * This ends the checking of the token
			 */

			else if (Character.isLetter(input.charAt(i))) {
				int lenchecker = 1;
				while (i + lenchecker < input.length() && Character.isLetter(input.charAt(i + lenchecker)))

					lenchecker++;
				String value = input.substring(i, i + lenchecker);
				char[] lexemeid = new char[lenchecker + 1];
				for (int j = 0; j < lenchecker; j++)
					lexemeid[j] = input.charAt(i + j);
				i += lenchecker - 1;
				boolean variablegetter = true;

				/**
				 * Iterate through the string
				 * This was done by adequately brainstorming and fixing code to see what failed for the keyword
				 */
				if ((lenchecker == 1) && (lexemeid[0] == 'V' || lexemeid[0] == 'v')) {
					
					tokens.add(new Token(TokenName.VEE));
					lexemeid = null;
					variablegetter = false;

				} else if ((lenchecker == 4) && (lexemeid[0] == 'E' || lexemeid[0] == 'e')
						&& (lexemeid[1] == 'V' || lexemeid[1] == 'v') && (lexemeid[2] == 'A' || lexemeid[2] == 'a')
						&& (lexemeid[3] == 'L' || lexemeid[3] == 'l')) {
					tokens.add(new Token(TokenName.EVAL_KEYWORD));
					lexemeid = null;
					variablegetter = false;
				}

				else if ((lenchecker == 3) && (lexemeid[0] == 'L' || lexemeid[0] == 'l')
						&& (lexemeid[1] == 'E' || lexemeid[1] == 'e') && (lexemeid[2] == 'T' || lexemeid[2] == 't')) {
					tokens.add(new Token(TokenName.LET_KEYWORD));
					lexemeid = null;
					variablegetter = false;

				} else if (variablegetter != false) {
					tokens.add(new Token(TokenName.IDENTIFIER, value));
					lexemeid = null;
				}
			}

			/**
			 * This is simply going to iterate and run to see how the code works
			 */
			else if (Character.isDigit(input.charAt(i))) {
				int reference = 1;
				while (i + reference < input.length() && Character.isDigit(input.charAt(i + reference)))
					reference++;

				String lexeme = input.substring(i, i + reference);

				int value = Integer.parseInt(lexeme);

				boolean booleandigit;
				if (value == 1) {
					booleandigit = true;
				} else {
					booleandigit = false;
				}

				tokens.add(new Token(TokenName.BOOL_LITERAL, booleandigit));

				i += reference - 1;

			} else {
				tokens.add(new Token(TokenName.UNEXPECTED_INPUT));
			}

		}
		tokens.add(new Token(TokenName.END_OF_INPUT));
		return tokens;
	}
}
