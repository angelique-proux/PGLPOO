package musichub.util.logger;

/* Exemple d'utilisation du Singleton */

import org.junit.jupiter.api.Test;

public class SingletonConsoleTest {

    @Test
    void TestSingletonConsole() {
        /* Déroulement normal du programme */
        boolean var = false;

        /* Condition pour vérifier s'il y a une erreur/avertissement... */
        if(var) {
            /* Tout est normal, continuation du programme */
            System.out.println("Le test est fait en sorte que vous ne voyez pas ce message.");
        } else {
            /*
            Erreur ! On le marque dans le log.

            Première étape : créer le SingletonFileLogger
            Deuxième étape : écrire dans le fichier en sélectionnant le niveau d'erreur approprié :
                - Levels.DEBUG (par exemple pour tester une valeur)
                - Levels.INFO (par exemple lors de l'ajout d'une chanson, connexion au serveur...)
                - Levels.WARNING (par exemple une valeur inattendue mais qui fonctionne)
                - Levels.ERROR (erreur devant initialement planter le programme)
            Troisième étape : écrire le message allant avec l'erreur
            */
            IntLogger sfl = SingletonFileLogger.getInstance();
            sfl.write(Levels.WARNING, "Valeur inattendue dans SingletonTest ! ");
            /* Ou même écrire directement la méthode : "SingletonTest.main() : Valeur inattendue/incorrecte, etc." */
        }

        /* S'utilise aussi dans les Try-Catch */
        try {
            String str = "UnTexteNEstPasUnNombre";
            int num = Integer.parseInt(str);
            System.out.println(num);
        } catch (NumberFormatException e) {
            //e.printStackTrace(); // Message d'erreur initial

            /* Pour éviter d'avoir un gros message d'erreur dans la console, on passe par le Singleton */
            IntLogger sfl = SingletonFileLogger.getInstance();
            sfl.write(Levels.ERROR, "SingletonTest.main() : Impossible de convertir le String en Integer");

            /* On peut aussi mettre un petit message à l'utilisateur en utilisant le SingletonConsoleLogger */
            IntLogger scl = SingletonConsoleLogger.getInstance();
            scl.write(Levels.INFO, "Une erreur est survenue. Vos modifications n'ont pas été prises en compte.");
        }
    }
}
