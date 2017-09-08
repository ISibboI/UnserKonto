package unserkonto.model;

public class OverflowException extends RuntimeException {
	private final String operation;
	private final int operand1, operand2;
	
	public OverflowException(String operation, int operand1, int operand2) {
		super("Overflow occured while performing operation " + operation + " on " + operand1 + " and " + operand2);
		
		this.operation = operation;
		this.operand1 = operand1;
		this.operand2 = operand2;
	}
}
