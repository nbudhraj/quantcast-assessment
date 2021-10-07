//Import Statements 
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.Set;
import java.util.HashSet;

/** 
 * MostAcvtive class: 
 * checkDate checks the valid date
 * parseFiles parses a valid file 
 * mostActive finds the most active cookie per date
 * main method parses command lines arguments 
*/
public class MostActive{
    //Defining constants
    public static final int DATE_LENGTH = 10; 
    public static final int ARGS = 3; 
    public static final String FLAG = "-d";
    public static final int DATE_INDEX = 2;
    public static final String SPLIT = "T";
    


    /** 
     * parseFiles parses a valid file and puts cookie name and date in corresponding lists
     * 
     * @param nameList list containing names of cookies from file 
     * @param dateList list containing dates of cookies from file
     * @param sc       Scanner initialized with valid file
     * 
    */
    public static void parseFile(List<String> nameList, List<String> dateList, Scanner sc){
        //scan each entry
        sc.useDelimiter("\n");
        while (sc.hasNext()) {
            //for each entry extract cookie name
            Scanner newSc = new Scanner(sc.next());
            newSc.useDelimiter(",");
            nameList.add(newSc.next());
            //extract cookie date (excluding the time)
            dateList.add(newSc.next().split(SPLIT)[0]);
            newSc.close();
        }
        //close the scanner
        sc.close();
    }

  /** 
     * mostActive returns a set of most active cookies on a given date 
     * 
     * @param nameList list containing names of cookies from file 
     * @param dateList list containing dates of cookies from file
     * @param date     date to determine the active cookies
     * 
    */
    public static Set<String> mostActive(List<String> names, List<String> dates, String date){
        //HashMap containing cookies and their frequencies on given date
        HashMap<String, Integer> cookieMap = new HashMap<>();
        //Set to contain the result of most active cookies
        Set<String> result = new HashSet<>();

        //iterate over cookies and their date 
        for(int i = 0; i<names.size(); i++){
            //if date matches given date, add to hashmap
            if(dates.get(i).equals(date)){
                //if cookie already in hashmap, increase its count
                //if cookie not in hashmap, set count to 1
                int count = cookieMap.getOrDefault(names.get(i), 0);
                count++; 
                cookieMap.put(names.get(i), count);
            }
        }

        //keep track of maxCount to track most active cookie
        int maxCount = 0;
        //iterate over all entries of hashmap
        for(Entry<String, Integer> val : cookieMap.entrySet()){

            //if frequency is larger than maxCount, found mostActive
            //remove from resulting set, add new cookie, set maxCount
            if(maxCount < val.getValue()){
                result.clear();
                result.add(val.getKey());
                maxCount = val.getValue();
            }

            //if frequency is equivalent to maxCount, then add cookie to result
            if(maxCount == val.getValue()){
                result.add(val.getKey());
            }
        }


        //holds the mostActive cookies per given date
        return result;
    }


    /** 
     * main checks validity, parses file, and then prints mostActive cookies 
     * 
     * @param args command line arguments
     * 
    */
    public static void main(String[] args){
        //if user inputs incorrect number of command line arguments, terminate
        if(args.length != ARGS){
            System.out.println("Incorrect number of arguments"); 
            return;
        }

        //if user inputs incorrect flag, terminate
        if(!args[1].equals(FLAG)){
            System.out.println("Invalid flag");
            return;
        }

        //check is user inputs valid date 
        String date = args[DATE_INDEX];
        if(date.length() != DATE_LENGTH){
            System.out.println("Invalid date");
            return;
        }

        //initialize lists of names and dates of the cookies 
        List<String> nameList = new ArrayList<String>();
        List<String> dateList = new ArrayList<String>();
        
        //get path to file 
        String path = args[0];

        //check validity of file and initialize scanner for file
        Scanner sc; 
        try {
            sc = new Scanner(new File(path));
        } catch (Exception e) {
            System.out.println("Invalid File");
            return;
        }

        //parse the file to fill list of names and dates
        parseFile(nameList, dateList, sc);

        //if file is empty, terminate
        if(nameList.isEmpty()){
            System.out.println("Empty File");
            return;
        }

        //set containing the names of the most active cookies
        Set<String> result = mostActive(nameList, dateList, date);
       
        //print most active cookies
        for(String cookie: result){
            System.out.println(cookie);
        }

    }

 
}