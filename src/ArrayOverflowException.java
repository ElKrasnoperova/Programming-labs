public class ArrayOverflowException extends RuntimeException {

	private static final String message = "Array is overflow";
	
	public String getMessage(){
		return message;
	}
}