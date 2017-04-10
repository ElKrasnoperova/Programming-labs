public class MoreThanOneException extends Exception {
		
	private static final String message = "This place has already contained this creature";
	
	public String getMessage(){
		return message;
	}
}