//imports 
import static org.junit.Assert.assertEquals;
import java.util.Set;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import org.junit.*;

/** 
 * Test Class 
*/
public class Testing {

    //immutable variables to keep track of System output 
    static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    static final PrintStream originalOut = System.out;

    //set up printstream prior to each test to catch system output
    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    //restore stream after each test 
    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    /** 
     * testManyArgs tests when user inputs too many command line arguments
    */
    @Test
    public void testManyArgs(){
        MostActive.main(new String[]{"cookie_log.csv","-d", "2018-12-09", "hello" });
        assertEquals("Incorrect number of arguments\n", outContent.toString());
    }

    /** 
     * testFewArgs tests when user inputs too little command line arguments
    */
    @Test
    public void testFewArgs(){
        MostActive.main(new String[]{"cookie_log.csv"});
        assertEquals("Incorrect number of arguments\n", outContent.toString());
    }

    /** 
     * testinvalidFlag tests when user inputs invalid flag
    */
    @Test
    public void testInvalidFlag(){
        String path = "/Users/navibudhraja/Desktop/quantcast-assessment/Quantcast/empty.csv";
        MostActive.main(new String[]{path, "test", "2018-12-09"});
        assertEquals("Invalid flag\n", outContent.toString());
    }

     /** 
     * testInvalidDateLength tests if user inputs string that is not of correct length for date
     * other functions will account for incorrectly formatted date 
    */
    @Test
    public void testInvalidDateLength(){
        String path = "/Users/navibudhraja/Desktop/quantcast-assessment/Quantcast/cookie_log.csv";
        
        MostActive.main(new String[]{path, "-d", "2018-12-09Xx"});
        assertEquals("Invalid date\n", outContent.toString());

    }

    /** 
     * testInvalidFile tests when user provides an invalid file 
    */
    @Test
    public void testInvalidFile(){
        MostActive.main(new String[]{"xxxx", "-d", "2018-12-09"});
        assertEquals("Invalid File\n", outContent.toString());
    }

    /** 
     * testEmptyFile tests when user provides empty file 
    */
    @Test
    public void testEmptyFile(){
        String path = "/Users/navibudhraja/Desktop/quantcast-assessment/Quantcast/empty.csv";
        MostActive.main(new String[]{path, "-d", "2018-12-09"});
        assertEquals("Empty File\n", outContent.toString());
    }


    /** 
     * testParse() checks if parseFile function correctly parses file 
    */
    @Test
    public void testParse(){
        ArrayList<String> nameList = new ArrayList<String>();
        ArrayList<String> dateList = new ArrayList<String>();
        String path = "/Users/navibudhraja/Desktop/quantcast-assessment/Quantcast/cookie_log.csv";
        Scanner sc;
        
        try {
            sc = new Scanner(new File(path));
            MostActive.parseFile(nameList, dateList, sc);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        assertEquals(8, nameList.size());
        assertEquals(8, dateList.size());
        
        assertEquals("AtY0laUfhglK3lC7", nameList.get(0));
        assertEquals("2018-12-09", dateList.get(0));
    }
    
    /** 
     * testMostActive() checks if mostActive correctly returns most active cookie
     * if user inputs invalid date, mostActive correctly returns empty set
    */
    @Test
    public void testMostActive(){
        ArrayList<String> nameList = new ArrayList<String>();
        ArrayList<String> dateList = new ArrayList<String>();
        nameList.add("AtY0laUfhglK3lC7");
        nameList.add("AtY0laUfhglK3lC7");
        nameList.add("fbcn5UAVanZf6UtG");
        dateList.add("2018-12-09");
        dateList.add("2018-12-08");
        dateList.add("2018-12-09");

        //with valid date 
        Set<String> result = MostActive.mostActive(nameList, dateList, "2018-12-09");

        assertEquals(2, result.size());
        assertEquals(true, result.contains("AtY0laUfhglK3lC7"));
        assertEquals(true, result.contains("fbcn5UAVanZf6UtG"));

        //test with invalid date 
        Set<String> empty = MostActive.mostActive(nameList, dateList, "xxxxxxxxxxx");
        assertEquals(0, empty.size());
    }

    /** 
     * testMainSmallDate1 tests main method if user inputs valid arguments 
     * checks if most active cookies of a small csv file is printed 
    */
    @Test
    public void testMainSmallDate1(){
        String path = "/Users/navibudhraja/Desktop/quantcast-assessment/Quantcast/cookie_log.csv";
        MostActive.main(new String[]{path, "-d", "2018-12-09"});
        assertEquals("AtY0laUfhglK3lC7\n", outContent.toString());
          
    }

    /** 
     * testMainSmallDate2 tests main method if user inputs valid arguments 
     * checks if most active cookies of a small csv file is printed
    */
    @Test
    public void testMainSmallDate2(){
        String path = "/Users/navibudhraja/Desktop/quantcast-assessment/Quantcast/cookie_log.csv";
        MostActive.main(new String[]{path, "-d", "2018-12-08"});
        assertEquals("fbcn5UAVanZf6UtG\nSAZuXPGUrfbcn5UA\n4sMM2LxV07bPJzwf\n", outContent.toString());

    }

    /** 
     * testMainSmallDate3 tests main method if user inputs valid arguments 
     * checks if a most active cookie of a small csv file is printed 
    */
    @Test
    public void testMainSmallDate3(){
        String path = "/Users/navibudhraja/Desktop/quantcast-assessment/Quantcast/cookie_log.csv";
        MostActive.main(new String[]{path, "-d", "2018-12-07"});
        assertEquals("4sMM2LxV07bPJzwf\n", outContent.toString());
          
    }


    /** 
     * testMainLargeDate1 tests main method if user inputs valid arguments 
     * checks if a most active cookie of a large csv file is printed
    */
    @Test
    public void testMainLargeDate1(){
        String path = "/Users/navibudhraja/Desktop/quantcast-assessment/Quantcast/cookie_log_large.csv";
        MostActive.main(new String[]{path, "-d", "2018-12-09"});
        assertEquals("AtY0laUfhglK3lC7\n", outContent.toString());
    }

    /** 
     * testMainLargeDate2 tests main method if user inputs valid arguments 
     * checks if a most active cookie of a large csv file is printed
    */
    @Test
    public void testMainLargeDate2(){
        String path = "/Users/navibudhraja/Desktop/quantcast-assessment/Quantcast/cookie_log_large.csv";
        MostActive.main(new String[]{path, "-d", "2018-12-08"});
        assertEquals("fbcn5UAVanZf6UtG\nSAZuXPGUrfbcn5UA\n4sMM2LxV07bPJzwf\n", outContent.toString());
    }

    /** 
     * testMainLargeDate3 tests main method if user inputs valid arguments 
     * checks if a most active cookie of a large csv file is printed
    */
    @Test
    public void testMainLargeDate3(){
        String path = "/Users/navibudhraja/Desktop/quantcast-assessment/Quantcast/cookie_log_large.csv";
        MostActive.main(new String[]{path, "-d", "2018-12-07"});
        assertEquals("4sMM2LxV07bPJzwf\n", outContent.toString());
          
    }


}
