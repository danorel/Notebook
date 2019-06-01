package notebook;

import file.FileManager;

import java.io.File;
import java.util.ArrayList;

public class Database {
    /*
        The default location of the created notes
     */
    private static String PATH = "data";
    /*
        All the notes created are located inside the ArrayList
     */
    private static ArrayList<File> notes;

    public Database(){
        this(PATH);
    }

    public Database(String path){
        Database.PATH = path;
        FileManager.createDirectory(Database.PATH);
        notes = new ArrayList<>();
    }

    public static String getPATH() {
        return PATH;
    }

    public static ArrayList<File> getExistingNotes() {
        return notes.size() == 0 ? null : notes;
    }
}
