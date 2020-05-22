import java.util.*;
import java.time.LocalDateTime;
import java.io.*;
import examples.*;
import com.google.gson.Gson; 
import com.google.gson.GsonBuilder;
/** 
 * The main class for the app execution. Contains the main collection and some methods
 *  for its loading from file
 * @author Daniil Shvetsov*/
public class Lab5 {
    private static boolean run = true;
    
    static Comparator<LabWork> comp_id = Comparator.comparing(o -> o.getId());
    static TreeSet<LabWork> colLab = new TreeSet<>(comp_id);
    static LocalDateTime crDate = LocalDateTime.now();

    private static boolean isScript = false;
    private static int scriptLineCounter = 0;
    static ArrayList<String> bufferScript = new ArrayList<>();
    static HashSet<String> scriptPaths = new HashSet<>();

    private static String path = System.getenv("SavePath");
    
    /**Returns path to load the collection from and store to.
     * @return path stored in the String field.
    */
    public static String getPath() {
        return path;
    }
    public static int getScriptLineCounter() {
        return scriptLineCounter;
    }
    
    public static void setIsScript(boolean isScript) {
        Lab5.isScript = isScript;
    }
    public static boolean getIsScript() {
        return isScript;
    }
    /**Shuts down the app. */
    public static void terminate(){
        run = false;
    }
    /**Loads collection from path given. 
     * @param fp - filepath.
    */
    public static void loadCollection(String fp){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        try(BufferedReader load = new BufferedReader(new InputStreamReader(
        new FileInputStream(fp)))) {
            String fileLine = load.readLine();
            while(fileLine != null){
                LabWork lab = gson.fromJson(fileLine, LabWork.class);
                colLab.add(lab);
                LabWork.getIds().add(lab.getId());
                fileLine = load.readLine();
            }
            System.out.println("The collection has been loaded from "+fp);
        }
        catch(IOException | SecurityException e){
            System.out.println("The save hasn't been loaded due to denied access or nonexistent path, etc.");
        }
    }

    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line = "";
        
        Commands.add("kek", 3, 12L, 10L, 7, Difficulty.HARD, "Math", 12L);
        Commands.add("kek", 3, 12L, 10L, 8, Difficulty.HARD, "Math", 12L);
        if(path == null || path == "")
            path = "save.json";
        if(!isScript)
            loadCollection(path);

        while(run){
            if(isScript){
                if(scriptLineCounter < bufferScript.size()){
                    line = bufferScript.get(scriptLineCounter);
                    scriptLineCounter++;
                }
                else{
                    scriptLineCounter = 0;
                    isScript = false;
                    bufferScript.clear();
                    System.out.println("Execution is finished.\n#  #  #");
                    scriptPaths.clear();
                    continue;
                }
            }
            else{
                try {
                    line = in.readLine();
                }
                catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
            String[] words = line.split(" ");

            if(words.length == 1)
                Commander.сheck(words[0], "");
            else if(words.length == 2 && Commander.getMarker() == "")
                Commander.сheck(words[0], words[1]);
            else{
                if(Commander.getMarker() == "")
                    System.out.println("The input is not a command, it can't be more than a\nsingle-word"+
                    " command and single-word argument. Try not to use \nspaces at the line's beginning "+
                    "and use only \n1 space between command and argument.\n Use 'help' command to show "+
                    "available commands.");
                else
                    System.out.println("The input is not a field, it can't be more than a\nsingle-word "+
                    "argument. Try not to use \nspaces at the line's beginning:");
                Commander.stopScriptReading();
            }
        }        
    }
}
