/*
 * Class' name : Level
 *
 * Description : Enumeration of the different levels of log entry
 *
 * Version     : 1.0
 *
 * Date        : 13/04/2021
 *
 * Copyright   : Steve Chauvreau-Manat and Gaël Lejeune and Angélique Proux and Antonin Morcrette
 */

package musichub.util.logger;

/**
 * Level Enumeration of the different levels of log entry
 *
 * Version : 1.0
 *
 * @author Gaël Lejeune (Based on the work of Steve Chauvreau-Manat)
 */
public enum Level
{
	/**
	 * "error" message
	 */
	ERROR ("ERROR"),

	/**
	 * "warning" message
	 */
	WARNING ("WARNING"),

	/**
	 * "info" message
	 */
	INFO ("INFO"),

	/**
	 * "debug" message
	 */
	DEBUG("DEBUG");

	/**
   * value of the log level
   */
	private String levelValue;

	/**
   * Level constructor
   *
   * @param       value value of the level
   *
   * @author      Gaël Lejeune
   */
	private Level (String value)
	{
		levelValue = value;
	}

	/**
	 * Write the log with it's level and message
 	 * @return      return the string value of a level
	 *
	 * @author   	Gaël Lejeune
	 */
	public String getLevelValue()
	{
		return levelValue;
	}
}
