package unserkonto.model;

public class OverflowException extends RuntimeException {
	private static final long serialVersionUID = 3802264656827971187L;
	
	public OverflowException(String operation, int operand1, int operand2) {
		super("Overflow occured while performing operation " + operation + " on " + operand1 + " and " + operand2);
	}
}
