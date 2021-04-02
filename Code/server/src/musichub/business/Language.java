package business;

/** Language Enumeration of the different available audio book languages
 *
 * Version : 1.0
 *
 * Date : 30/02/2001
 *
 * @author Gaël Lejeune
 */
public enum Language {
    /**
     * "French" language
     */
    FRANCAIS("Francais"),

    /**
     * "English" language
     */
    ANGLAIS("Anglais"),

    /**
     * "Italian" language
     */
    ITALIEN("Italien"),

    /**
     * "Spanish" language
     */
    ESPAGNOL("Espagnol"),

    /**
     * "German" language
     */
    ALLEMAND("Allemand");

    /**
     * Language of the enum
     */
    private String language;

    /**
     * Language constructor
     *
     * @param       language
     *
     * @author      Gaël Lejeune
     */
    private Language(String language) {
        this.language = language;
    }

    /**
     * Override of the toString java method
     * @return      String containing the language
     * @author      Gaël Lejeune
     */
    public String toString() {
        return this.language;
    }
}
