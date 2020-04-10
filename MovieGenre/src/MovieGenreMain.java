import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.LinkedList;
import java.util.Comparator;

public class MovieGenreMain {

	public static void main(String[] args) throws FileNotFoundException {

		int rows = 9742;
		int cols = 3;
		int index = 0;
		
		String [][] dataArray = new String [rows][cols];
		String delimiter = "\t";
		String regex = "([0-9]+)";
		Pattern pattern = Pattern.compile(regex);
		String [] releaseYear = new String [9742];
		String [] movieGenres = new String [9742];
		
		boolean asc = true;
		boolean desc = false;
		    
		Scanner sc = new Scanner(new File("src/movies.tsv"));
		PrintStream ps = new PrintStream(new FileOutputStream(new File("src/MovieDataOutput.txt")));
		
		//reads data in from tsv file and puts it into 2D array
		readData(dataArray, sc, index, delimiter, cols);

		//seperates data into two arrays for release year and movie title
		seperateData(dataArray, releaseYear, movieGenres, pattern);
		
		//counts and prints out number of movies in each decade
		countAndPrintDecades(releaseYear, ps);
		
		HashMap<String, Integer> mapCount = new HashMap<>();
		
		//Uses hashmap to count 
		createMap(movieGenres,mapCount);
		
		// Creates and prints out a sorted map by values in descending order
		Map<String, Integer> sortedMapDesc = sortByComparator(mapCount, desc);
		ps.println("Number of Movies in each Genre \n");
        printMap(sortedMapDesc, ps);
        
	}
	
	
	//reads data into a 2d array
	public static void readData(String [][] dataArray, Scanner sc, int index, String delimiter, int cols) {
		 while (sc.hasNextLine()) {
				String temp = sc.nextLine();
				for (int c = 0; c<cols; c++) {
					dataArray[index][c] = temp;
				}
				index++;
		 }
			sc.close(); 
	 }
	
	// separates data into data arrays for release year and genres
	public static void seperateData (String dataArray[][], String releaseYear[], String movieGenres[], Pattern pattern) {
		for (int i =0; i<dataArray.length; i++) {
			Matcher matcher = pattern.matcher(dataArray[i][1]);
			while(matcher.find()) {
				releaseYear [i] = matcher.group();
				
			}
		}
		
		for (int i =0; i<9742; i++) {
			String title = dataArray[i][1];
			int iend = title.lastIndexOf(")");
				if (iend != -1) 
				{
					movieGenres [i]= title.substring(iend+2, title.length()); 
				}
		}
		
	}
	
	// counts up how many movies are in each decade then prints them out
	public static void countAndPrintDecades (String releaseYear [], PrintStream ps) {
		
		int fiftiesCount = 0;
		int sixtiesCount = 0;
		int seventiesCount = 0;
		int eightiesCount = 0;
		int ninetiesCount = 0;
		int twentiethCount = 0;
		int twentyFirstCount = 0;
		
		for (int i=0; i<releaseYear.length; i++) {
        	if (releaseYear[i].contains("195")) {
        		fiftiesCount++;	
        	} if (releaseYear[i].contains("196")) {
        		sixtiesCount++;
        	} if (releaseYear[i].contains("197")) {
        		seventiesCount++;
        	} if (releaseYear[i].contains("198")) {
        		eightiesCount++;
        	} if (releaseYear[i].contains("199")) {
        		ninetiesCount++;
        	} if (releaseYear[i].contains("200")) {
        		twentiethCount++;
        	} if (releaseYear[i].contains("201")) {
        		twentyFirstCount++;
        	}
        }
       
        ps.println("Number of Movies by Decades: \n");
        ps.println("1950's: " +fiftiesCount);
        ps.println("1960's: " +sixtiesCount);
        ps.println("1970's: " +seventiesCount);
        ps.println("1980's: " +eightiesCount);
        ps.println("1990's: " +ninetiesCount);
        ps.println("2000's: " +twentiethCount);
        ps.println("2010's: " +twentyFirstCount + "\n");
	}
	
	//creates a map and counts
	public static void createMap(String [] movieGenres, HashMap<String, Integer> mapCount) {
        for(String names: movieGenres){
            if(mapCount.containsKey(names)){
                Integer value = mapCount.get(names);
                value++;
                mapCount.put(names,value);
            } else {
                mapCount.put(names,1);
            }
        }
    }	
	
	// method to sort a map in ascending or descending order using a list to do so
	public static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order) {

        List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(unsortMap.entrySet());

        // sorting the list based on values
        Collections.sort(list, new Comparator<Entry<String, Integer>>() {
            public int compare(Entry<String, Integer> o1,
                    Entry<String, Integer> o2) {
                if (order) {
                    return o1.getValue().compareTo(o2.getValue());
                }
                else {
                    return o2.getValue().compareTo(o1.getValue());
                }
            }
        });
        
     // keep insertion order using linkedlist
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
	
	// prints out sorted hashmap
	public static void printMap(Map<String, Integer> map, PrintStream ps) {
	        for (Entry<String, Integer> entry : map.entrySet()) {
	            ps.println("Genre: " + entry.getKey() + ": "+ entry.getValue() +" movies");
	        }
	 }

}

	

