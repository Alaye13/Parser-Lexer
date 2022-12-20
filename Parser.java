package unit;

/***
 * Ifenna Ekwenem
 * Integrity Statement
 * CS 310 Mr Reaser
 */
import java.util.Queue;
import java.util.HashMap;
import framework.Token;
import framework.TokenName;

public class Parser {
	public static void main(String[] args) {
		String sentence = "let P = 1, let Q = 0, eval P -> Q?";
		boolean value = new Parser().analyze(sentence);
		System.out.println(value);
	}

	// New token
	
	private Queue<Token> tokens;

	HashMap <String, Boolean> lookupTable;

	public boolean analyze(String input) {

		tokens = new Lexer().tokenize(input);

		boolean result = expression();
		
		return result;
	}

	private boolean accept(TokenName name) {
		if (tokens.peek().name != name)
			return false;

		tokens.remove();
		return true;
	}

	private boolean peek(TokenName name) {
		return tokens.peek().name == name;
	}

	private Object expect(TokenName name) {
		if (tokens.peek().name != name)
			throw new RuntimeException("Expected: " + name + ", but found: " + tokens.peek().name);

		return tokens.remove().value;
	}

	private boolean expression() {
		boolean value;

		if (accept(TokenName.OPEN_PAREN)) {
			value = equivalence();
			expect(TokenName.CLOSE_PAREN);
		}

		else {
			value = literal();
		}

		return value;
	}

	private boolean evaluation() {
		boolean value = !true;

		if (accept(TokenName.EVAL_KEYWORD)) {
			value = equivalence();
			expect(TokenName.QUESTION);

		}
		return value;
	}

	@SuppressWarnings("unused")
	private boolean program() {

		lookupTable = new HashMap<String, Boolean>();

		while (peek(TokenName.LET_KEYWORD)) {
			assignment();
		}
		return evaluation();
	}

	private void assignment() {
		// TODO Auto-generated method stub
		boolean value;
		while (peek(TokenName.LET_KEYWORD)) {
			if (accept(TokenName.LET_KEYWORD)) {
				
				String str = (String) expect(TokenName.IDENTIFIER);
				expect(TokenName.EQUAL);
				value = equivalence();
				
				// put method
				lookupTable.put(str, value);
				
				expect(TokenName.COMMA);
			}
		}
	}

	// TODO Auto-generated method stub
	private boolean equivalence() {
		boolean value = implication();

		while (accept(TokenName.DOUBLE_ARROW)) {
			value = value == implication();
		}
		

		return value;

	}

	private String variable() {
		boolean value = true;
		String str = (String) expect(TokenName.IDENTIFIER);
		boolean literal = (Boolean) expect(TokenName.IDENTIFIER);
		value = literal;
		return str;

	}

	// Boolean Literal

	private boolean booleanValue() {
		boolean value;
		boolean literal = (Boolean) expect(TokenName.BOOL_LITERAL);
		System.out.println(literal);
		value = literal;
		return value;

	}

	private boolean literal() {
		boolean value;
	if ((Boolean) accept(TokenName.BOOL_LITERAL)) {
			value = literal();

		} else {
			value = accept(TokenName.IDENTIFIER);
		}

		return value;
		
		
		
		
		
	}

	private boolean conjuction() {
		boolean value = negation();
		while (accept(TokenName.CARET))
			value = value & negation();

		return value;

	}

	private boolean disjunction() {
		boolean value = conjuction();

		while (accept(TokenName.VEE))
			value = value | conjuction();

		return value;

	}

	private boolean negation() {
		boolean value = expression();

		if (accept(TokenName.APOSTROPHE))
			value = !value;

		return value;

	}

	private boolean implication() {
		boolean value = disjunction();

		if (accept(TokenName.ARROW))
			// value = implication(); // implication rule is A->B is the same A' v B
		value = !value | disjunction();
		
		return value;
	}

}
