/*
 * Enumeration's name : Category
 *
 * Description        : Enumeration of the different available audio book categories
 *
 * Version            : 1.0
 *
 * Date               : 13/04/2021
 *
 * Copyright          : Steve Chauvreau-Manat and Gaël Lejeune and Angélique Proux and Antonin Morcrette
 */

package musichub.business;

/** Category Enumeration of the different available audio book categories
 *
 * Version : 1.0
 *
 * Date : 30/02/2001
 *
 * @author Gaël Lejeune
 */
public enum Category {
    /**
     * "Youth" category
     */
    JEUNESSE("Jeunesse"),

    /**
     * "Novel" category
     */
    ROMAN("Roman"),

    /**
     * "Theatre" category
     */
    THEATRE("Theatre"),

    /**
     * "Speech" category
     */
    DISCOURS("Discours"),

    /**
     * "Documentary" category
     */
    DOCUMENTAIRE("Documentaire");

    /**
     * Category of the enum
     */
    private String category;

    /**
     * Category constructor
     *
     * @param       category Desired category as a string
     *
     * @author      Gaël Lejeune
     */
    private Category(String category) {
        this.category = category;
    }

    /**
     * Override of the toString java method
     * @return      String containing the category
     * @author      Gaël Lejeune
     */
    public String toString() {
        return this.category;
    }
}
