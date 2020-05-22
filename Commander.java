import examples.*;
/** The class to manage input and call the right methods accordingly.
 * Also provides a big part of console output.
*/
public class Commander {
    private static String marker = "";
    private static String flag = "";
    static final boolean isCheckingId = true;

    static Integer buffID;
    static LabWork buffLab;

    static String buffLName;
    static int buffX;
    static long buffY;
    static Long buffMP;
    static int buffAP;
    static Difficulty buffDif;

    static String buffDName;
    static long buffLH;

    public static String getMarker() {
        return marker;
    }
    public static String getFlag() {
        return flag;
    }
    public static void stopScriptReading(){
        if(Lab5.getIsScript()){
            Lab5.setIsScript(false);
            flag = "";
            marker = "";
            Lab5.scriptPaths.clear();
            Lab5.bufferScript.clear();
            System.out.println("An issue occured in this file."+"\nExecution is finished.\n#  #  #");
        }
    }
    /**Outputs text only in interactive mode. */
    public static void infoMsg(String s){
        if(!Lab5.getIsScript())
            System.out.println(s);
    }

    /** Distributes different input forms to the right methods.*/
    public static void сheck(String s1, String s2){
        if (marker == "") {
            if (s2 == "") {
                checkSimpleComm(s1);
            }
            else {
                checkSimpleCommArg(s1, s2);
            }
        }
        else {
            if(!Validator.isEmpty(s1)) {
                checkComplexArg(s1);
            }
            else{
                checkComplexEmpty();
            }
        }
    }
    /**Distributes single word to the according command. */
    public static void checkSimpleComm(String s1){
        switch (s1) {
            case "help":
                Commands.help();
                break;
            case "info":
                Commands.info();
                break;
            case "show":
                Commands.show();
                break;
            case "clear":
                Commands.clear();
                break;
            case "save":
                Commands.save(Lab5.getPath());
                break;
            case "average_of_average_point":
                Commands.average_of_average_point();
                break;
            case "exit":
                Commands.exit();
                break;
            case "count_by_discipline":
                marker = "dn";
                flag = "cbd";
                infoMsg("Type in the discipline name for the labwork (not empty):");
                break;
            case "add_if_max":
                if(Lab5.colLab.size() > 0){
                    if(Lab5.colLab.last().getId() < LabWork.findNewId()){
                        marker = "ln";
                        flag = "ama";
                        infoMsg("Type in the labwork name (not empty):");
                    }
                    else{
                        System.out.println("The labwork can not be added.\n  "+
                        "The labwork's value is not higher than the maximal one.");
                        stopScriptReading();
                    }
                }
                else {
                    System.out.println("The labwork can not be added.\n  "+
                    "There are no labworks in the collection yet to compare with. Try to use 'add'.");
                    stopScriptReading();
                }
                break;
            case "add_if_min":
                if(Lab5.colLab.size() > 0){
                    if(Lab5.colLab.last().getId() > LabWork.findNewId()){
                        marker = "ln";
                        flag = "ami";
                        infoMsg("Type in the labwork name (not empty):");
                    }
                    else{
                        System.out.println("The labwork can not be added.\n  "+
                        "The labwork's value is not lower than the minimal one.");
                        stopScriptReading();
                    }
                }
                else {
                    System.out.println("The labwork can not be added.\n  "+
                    "There are no labworks in the collection yet to compare with. Try to use 'add'.");
                    stopScriptReading();
                }
                break;
            case "add":
                marker = "ln";
                flag = "add";
                infoMsg("Type in the labwork name (not empty):");
                break;
            case "remove_all_by_difficulty":
                marker = "dif";
                flag = "rbd";
                infoMsg("Type in the difficulty of the labwork from the list in brackets\n[NORMAL,"+
                    " HARD, VERY_HARD, INSANE, HOPELESS] or empty line:");
                break;
            default:
                System.out.println("It seems like a wrong command. Please, try another one.\n Use 'help' command "+
                "to show available commands.");
                stopScriptReading();
        }
    }
    /**Distributes two words to the according command and argument. */
    public static void checkSimpleCommArg(String s1, String s2){
        switch (s1) {
            case "execute_script":
                Commands.execute_script(s2);
                break;
            case "update":
                if(Validator.isInt(s2)){
                    if(LabWork.getIds().contains(Integer.valueOf(s2))){
                        buffID = Integer.valueOf(s2);
                        marker = "ln";
                        flag = "upd";
                        for(LabWork lab : Lab5.colLab){
                            if(lab.getId() == buffID){
                                buffLab = lab;
                                break;
                            }
                        }
                        infoMsg("Current name: "+buffLab.getName());
                        infoMsg("Type in the labwork name (not empty):");
                    }
                    else{
                        System.out.println("The labwork with id "+s2+" does not exist.");
                    }
                }
                else{
                    System.out.println("The '"+s2+"' is not an id, try again with an integer number.");
                }
                break;
            case "remove_by_id":
                if(Validator.isInt(s2)){
                    buffID = Integer.valueOf(s2);
                    Commands.remove_by_id(buffID);
                }
                else{
                    System.out.println("The '"+s2+"' is not an id, try again with an integer number.");
                }
                break;
            case "remove_lower":
                if(Validator.isInt(s2)){
                    buffID = Integer.valueOf(s2);
                    Commands.remove_lower(buffID);
                }
                else{
                    System.out.println("The '"+s2+"' is not an id, try again with an integer number.");
                }
                break;
            default:
                System.out.println("Your command can't have an argument. Please, try to use an empty "+
                "argument or another command.\n Use 'help' command to show available commands.");
                stopScriptReading();
        }
    }
    /**Distributes single word to the according field and calls commands (collection editors) 
     * at the end of input branch. */
    public static void checkComplexArg(String s1){
        switch (marker) {
            case "ln":
                buffLName = s1;
                marker = "x";
                if(flag=="upd")
                    infoMsg("Current X: "+buffLab.getCoordinates().getX());
                infoMsg("Type in the X coordinate of the labwork (integer number):");
                break;
            case "x":
                if(Validator.isInt(s1)){
                    buffX = Integer.parseInt(s1);
                    marker = "y";
                    if(flag=="upd")
                        infoMsg("Current Y: "+buffLab.getCoordinates().getY());
                    infoMsg("Type in the Y coordinate of the labwork (integer long number):");
                }
                else{
                    System.out.println("Try another input, must be integer number:");
                    stopScriptReading();
                }
                break;
            case "y":
                if(Validator.isLong(s1)){
                    buffY = Long.valueOf(s1);
                    marker = "mp";
                    if(flag=="upd")
                        infoMsg("Current Minimal Point: "+buffLab.getMinimalPoint());
                    infoMsg("Type in the minimal point of the labwork (positive integer long number or empty line):");
                }
                else{
                    System.out.println("Try another input, must be integer long number:");
                    stopScriptReading();
                }
                break;
            case "mp":
                if(Validator.isLong(s1) && Long.valueOf(s1) > 0){
                    buffMP = Long.valueOf(s1);
                    marker = "ap";
                    if(flag=="upd")
                        infoMsg("Current Average Point: "+buffLab.getAveragePoint());
                    infoMsg("Type in the average point of the labwork (positive integer number):");
                }
                else{
                    System.out.println("Try another input, must be positive integer long number or empty line:");
                    stopScriptReading();
                }
                break;
            case "ap":
                if(Validator.isInt(s1) && Integer.parseInt(s1) > 0){
                    buffAP = Integer.parseInt(s1);
                    marker = "dif";
                    if(flag=="upd")
                        infoMsg("Current Difficulty: "+buffLab.getDifficulty());
                    infoMsg("Type in the difficulty of the labwork from the list in brackets\n[NORMAL,"+
                    " HARD, VERY_HARD, INSANE, HOPELESS] or empty line:");
                }
                else{
                    System.out.println("Try another input, must be positive integer number:");
                    stopScriptReading();
                }
                break;
            case "dif":
                if(Validator.isDiff(s1)){
                    buffDif = Difficulty.valueOf(s1);
                    if(flag == "rbd"){
                        Commands.remove_all_by_difficulty(buffDif);
                        flag = "";
                        marker = "";
                    }
                    else {
                        marker = "dn";
                        if(flag=="upd")
                            infoMsg("Current Discipline Name: "+buffLab.getDiscipline().getName());
                        infoMsg("Type in the discipline name for the labwork (not empty):");
                    }
                }
                else{
                    System.out.println("Try another input, must be empty line or word from the list in brackets\n[NORMAL,"+
                    " HARD, VERY_HARD, INSANE, HOPELESS]:");
                    stopScriptReading();
                }
                break;
            case "dn":
                buffDName = s1;
                marker = "lh";
                if(flag=="upd")
                    infoMsg("Current Lecture Hours: "+buffLab.getDiscipline().getLectureHours());
                infoMsg("Type in the number of lecture hours for that discipline (integer long number):");
                break;
            case "lh":
                if(Validator.isLong(s1)){
                    buffLH = Long.parseLong(s1);
                    marker = "";
                    switch (flag) {
                        case "add":
                            Commands.add(buffLName, buffX, buffY, buffMP, buffAP, buffDif, buffDName, buffLH);
                            break;
                        case "upd":
                            Commands.update(buffID, buffLName, buffX, buffY, buffMP, buffAP, buffDif, buffDName, buffLH);
                            break;
                        case "ami":
                            Commands.add(buffLName, buffX, buffY, buffMP, buffAP, buffDif, buffDName, buffLH);
                            break;
                        case "ama":
                            Commands.add(buffLName, buffX, buffY, buffMP, buffAP, buffDif, buffDName, buffLH);
                            break;
                        case "cbd":
                            Commands.count_by_discipline(buffDName, buffLH);
                            break;
                    }
                    flag = "";
                }
                else{
                    System.out.println("Try another input, must be integer long number:");
                    stopScriptReading();
                }
        }
    }
    /**Distributes empty line to the according field as null value. */
    public static void checkComplexEmpty(){
        switch (marker){
            case "mp":
                buffMP = null;
                marker = "ap";
                infoMsg("Type in the average point of the labwork (positive integer number):");
                break;
            case "dif":
                buffDif = null;
                if(flag == "rbd"){
                    Commands.remove_all_by_difficulty(buffDif);
                    flag = "";
                    marker = "";
                }
                else {
                    marker = "dn";
                    infoMsg("Type in the discipline name for the labwork (not empty):");
                }
                break;
            default:
                System.out.println("This field must not be empty(null)! Try another input:");
                stopScriptReading();
        }
    }
}