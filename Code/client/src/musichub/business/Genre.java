/*
 * Enumeration's name : Genre
 *
 * Description        : Enumeration of the different available song genres
 *
 * Version            : 1.0
 *
 * Date               : 13/04/2021
 *
 * Copyright          : Steve Chauvreau-Manat & Gaël Lejeune & Angélique Proux & Antonin Morcrette
 */

package business;

/** Genre Enumeration of the different available song genres
 *
 * Version : 1.0
 *
 * @author Gaël Lejeune
 */
public enum Genre {
    /**
     * "Jazz" genre
     */
    JAZZ("Jazz"),

    /**
     * "Classical" genre
     */
    CLASSIQUE("Classique"),

    /**
     * "Hip-Hop" genre
     */
    HIPHOP("HipHop"),

    /**
     * "Rock" genre
     */
    ROCK("Rock"),

    /**
     * "Pop" genre
     */
    POP("Pop"),

    /**
     * "Rap" genre
     */
    RAP("Rap"),

    /**
     * "Metal" genre
     */
    METAL("Metal");

    /**
     * Genre of the enum
     */
    private String genre;

    /**
     * Genre constructor
     *
     * @param       genre
     *
     * @author      Gaël Lejeune
     */
    private Genre(String genre) {
        this.genre = genre;
    }

    /**
     * Override of the toString java method
     *
     * @return      String containing the genre
     *
     * @author      Gaël Lejeune
     */
    public String toString() {
        return this.genre;
    }
}
