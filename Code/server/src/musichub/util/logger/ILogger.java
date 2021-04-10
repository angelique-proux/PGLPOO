package util.logger;

/**
 * Ilogger Interface is the interface representing a base logger
 *
 * Version : 1.0
 *
 * @author Gaël Lejeune (Based on the work of Steve Chauvreau-Manat)
 */
public interface ILogger
{
    /**
     * Write the log with it's level and message
     *
     * @param       l importance level of the log to write
     * @param       message message of the log to write
     *
     * @author   Gaël Lejeune
     */
    public void write(Level l, String message);
}
