import java.io.*;
import examples.*;
import com.google.gson.Gson; 
import com.google.gson.GsonBuilder;
import java.util.Iterator;

/** The class which contains commands for the Commander class.
*/
public class Commands {
    static void add(String Ln, int cx, Long cy, Long mp, int ap, Difficulty dif, String Dn, long Lh){
        LabWork lab = new LabWork(Ln, new Coordinates(cx, cy), mp, ap, dif, new Discipline(Dn, Lh));
        Lab5.colLab.add(lab);
        Commander.infoMsg("The labwork has been added successfuly.");
    }
    static void update(Integer ID, String Ln, int cx, Long cy, Long mp, int ap, Difficulty dif, String Dn, long Lh){
        Commander.buffLab.setName(Ln);
        Commander.buffLab.setCoordinates(new Coordinates(cx, cy));
        Commander.buffLab.setMinimalPoint(mp);
        Commander.buffLab.setAveragePoint(ap);
        Commander.buffLab.setDifficulty(dif);
        Commander.buffLab.setDiscipline(new Discipline(Dn, Lh));
        System.out.println("The labwork with id "+ID+" has been updated.");
    }
    static void execute_script(String fp){
        try(BufferedReader inFile = new BufferedReader(new InputStreamReader(new FileInputStream(fp)))){;
            String fline = inFile.readLine();
            File file = new File(fp);
            if(!(file.length() == 0)){
                if(Lab5.scriptPaths.add(fp)){
                    while( !(fline == null) ){
                        Lab5.bufferScript.add(fline);
                        fline = inFile.readLine();
                    }
                    Lab5.setIsScript(true);
                    System.out.println("The script "+fp+" is running.");
                }
                else
                    System.out.println("The file "+fp+" has already been executed in this branch.");
            }
            else
                System.out.println("The file "+fp+" is empty.");
        }
        catch(IOException | SecurityException e){
            System.out.println("The file hasn't been read due to denied access or nonexistent path, etc.");
        }
    }
    static void help(){
        System.out.println("There are two types of commands:\n  Complex (multi-line) - "+
        "can not be aborted until all the fields are completed;\n  Simple (single-line) - only one line to type in.\n"+
        "If the command has an argument, it will be written like\n'command argument', otherwise just 'command'. "+
        "Nomore than 1 argument in 1 line is available.\nHere is the list of available commands below:\n  Simple:\n\t"+
        "info - displays collection info\n\tshow - displays info about each labwork in the collection\n\tclear - "+
        "deletes all the labworks in the collection\n\tsave - stores the collection into a file which is defined in SavePath env variable"+
        "\n\t  or in the current folder in 'save.json' if not defined\n\texit - quits the programm without saving\n\t"+
        "average_of_average_point - displays an average value of the field 'averagePoint' for all the labworks\n\t"+
        "execute_script file_path_and_name - executes a .txt file with the same syntax as in interactive mode\n\t  "+
        "from the file with name (and optionally location) given by user."+
        "\n\tremove_by_id id - deletes a labwork by its id\n\tremove_lower id - deletes all the labworks lower than given"+
        "\n  Complex:\n\tremove_all_by_difficulty - deletes all the labworks with given difficulty (1)\n\t"+
        "count_by_discipline - displays count of labworks with given discipline (2)\n\tadd - adds the given labwork to the"+
        " collection (8)\n\tadd_if_max - adds the given labwork only if it has higher value than the maximal element of"+
        " the collection (8)\n\tadd_if_min - adds the given labwork only if it has lower value than the minimal element of"+
        " the collection (8)\n\tupdate id - allows to overwrite all the fields of the labwork with id given (8)\n"+
        "The number in brackets is the number of extra lines (steps) for a command.\n"+
        "For all complex commands the instructions for the extra lines will be displayed during execution.\n"+
        "IMPORTANT: Labworks in the collection are sorted by id in current version of this app, so all the operations\n"+
        "with comparison will use id to compare labworks.");
        System.out.println("#   #   #");
    }
    static void info(){
        System.out.println("Collection type: TreeSet\nContent type: LabWork\nSize: "+
        Lab5.colLab.size()+"\nCreation date: "+Lab5.crDate+"\nSorting: ascending by id");
        if(Lab5.colLab.size()>0)
            System.out.println("Max element: '"+Lab5.colLab.last().getName()+"' #"+Lab5.colLab.last().getId());
        System.out.println("#   #   #");
    }
    static void show(){
        if(Lab5.colLab.size() == 0)
            System.out.println("There are no labworks in the collection yet.");
        else{
            for(LabWork lab : Lab5.colLab){
                System.out.println(lab);
            }
            System.out.println("#   #   #");
        }
    }
    static void clear(){
        Lab5.colLab.clear();
        System.out.println("The collection has been cleared.");
    }
    static void save(String fp){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try(Writer writer = new BufferedWriter (new OutputStreamWriter(
        new FileOutputStream(fp)))){
            for(LabWork lab : Lab5.colLab){
                writer.write(gson.toJson(lab)+"\n");
            }
            System.out.println("The collection has been saved to "+fp+".");
        }
        catch(IOException | SecurityException e){
            System.out.println("The file hasn't been written due to denied access or nonexistent path, etc.");
        }
    }
    static void average_of_average_point(){
        int apSum = 0;
        for(LabWork lab : Lab5.colLab){
            apSum += lab.getAveragePoint();
        }
        System.out.println("Average of average point for all the labworks: "+ ((double)apSum)/Lab5.colLab.size());
    }
    static void count_by_discipline(String name, long hours){
        int lbcounter = 0;
        for(LabWork lab : Lab5.colLab){
            if(lab.getDiscipline().getName().equals(name) && lab.getDiscipline().getLectureHours() == hours)
                lbcounter++;
        }
        System.out.println("There are "+lbcounter+" labworks for this discipline in the collection.");
    }
    static void remove_by_id(Integer ID){
        if(LabWork.getIds().remove(ID)){
            for(LabWork lab : Lab5.colLab){
                if(lab.getId() == ID){
                    if(Lab5.colLab.remove(lab)){
                        System.out.println("The labwork #"+ID+" has been deleted.");
                        break;
                    }
                }
            }
        }
        else
            System.out.println("The labwork with id "+ID+" does not exist.");
    }
    static void remove_all_by_difficulty(Difficulty df){
        Iterator<LabWork> itr = Lab5.colLab.iterator();
        while(itr.hasNext()) {
            LabWork lab = itr.next();
            if(lab.getDifficulty() == df){
                Commander.buffID = lab.getId();
                LabWork.getIds().remove(Commander.buffID);
                itr.remove();
                System.out.println("The labwork #"+Commander.buffID+" has been deleted.");
            }
        }
        Commander.infoMsg("Deleting by difficulty "+df+" is completed.");
    }
    static void remove_lower(Integer ID){
        if( !Commander.isCheckingId || (Commander.isCheckingId && LabWork.getIds().contains(ID)) ){
            Iterator<LabWork> itr = Lab5.colLab.iterator();
            while(itr.hasNext()) {
                LabWork lab = itr.next();
                if(lab.getId() < ID){
                    Commander.buffID = lab.getId();
                    LabWork.getIds().remove(Commander.buffID);
                    itr.remove();
                    System.out.println("The labwork #"+Commander.buffID+" has been deleted.");
                }
            }
            Commander.infoMsg("Deleting all the labworks lower than #"+ID+" is completed.");
        }
        else
            System.out.println("The labwork with id "+ID+" does not exist.");
    }
    static void exit(){
        System.out.println("Program will be closed now.");
        Lab5.terminate();
    }
}