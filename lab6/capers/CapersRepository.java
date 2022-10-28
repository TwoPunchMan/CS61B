package capers;

import java.io.File;
import static capers.Utils.*;

/** A repository for Capers 
 * @author TODO
 * The structure of a Capers Repository is as follows:
 *
 * .capers/ -- top level folder for all persistent data in your lab12 folder
 *    - dogs/ -- folder containing all of the persistent data for dogs
 *    - story -- file containing the current story
 *
 * TODO: change the above structure if you do something different.
 */
public class CapersRepository {
    /** Current Working Directory. */
    static final File CWD = new File(System.getProperty("user.dir"));

    /** Main metadata folder. */
    static final File CAPERS_FOLDER = Utils.join(CWD, "capers");

    /**
     * Does required filesystem operations to allow for persistence.
     * (creates any necessary folders or files)
     * Remember: recommended structure (you do not have to follow):
     *
     * .capers/ -- top level folder for all persistent data in your lab12 folder
     *    - dogs/ -- folder containing all of the persistent data for dogs
     *    - story -- file containing the current story
     */
    public static void setupPersistence() {
        File dogDir = Utils.join(CAPERS_FOLDER, "dogs");
        File storyDir = Utils.join(CAPERS_FOLDER, "story");
        File[] fileDirNames = {dogDir, storyDir};

        try {
            for (File dir : fileDirNames) {
                if (!dir.exists()) {
                    dir.mkdir();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Appends the first non-command argument in args
     * to a file called `story` in the .capers directory.
     * @param text String of the text to be appended to the story
     */
    public static void writeStory(String text) {
        File storyFile = new File("./capers/story/story.txt");
        String savedStory = Utils.readContentsAsString(storyFile);

        if (!storyFile.exists()) {
            try {
                storyFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Utils.writeContents(storyFile, savedStory, text, "\n");
    }

    /**
     * Creates and persistently saves a dog using the first
     * three non-command arguments of args (name, breed, age).
     * Also prints out the dog's information using toString().
     */
    public static void makeDog(String name, String breed, int age) {
        Dog newDog = new Dog(name, breed, age);
        File dogFile = new File("./capers/dogs/" + name + ".txt");

        if (!dogFile.exists()) {
            try {
                dogFile.createNewFile();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Utils.writeObject(dogFile, newDog);
        System.out.println(newDog.toString());
    }

    /**
     * Advances a dog's age persistently and prints out a celebratory message.
     * Also prints out the dog's information using toString().
     * Chooses dog to advance based on the first non-command argument of args.
     * @param name String name of the Dog whose birthday we're celebrating.
     */
    public static void celebrateBirthday(String name) {
        // TODO
    }
}
