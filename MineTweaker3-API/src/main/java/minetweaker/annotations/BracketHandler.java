package minetweaker.annotations;

/**
 * Marks a bracket handler. Bracket handlers are automatically registered.
 * The marked class should have an empty constructor.
 * 
 * @author Stan Hebben
 */
public @interface BracketHandler {
	/**
	 * Indicates priority. A lower value means a higher priority. Only change
	 * if you have issues, default value is 10. Internal minetweaker handlers
	 * have priorities up to 100.
	 * 
	 * @return 
	 */
	public int priority() default 10;
}
