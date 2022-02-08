import java.util.*;
import java.util.Map.Entry;
import java.util.regex.*;
import org.jsoup.*;
import org.jsoup.nodes.Document;

public class Module2 {

	public static void main(String[] args) throws Exception {
		//run main program
		analyzer();
	}
	
	public static void analyzer () throws Exception {
		//initialize maps and variables
		int frq = 0;
		TreeMap<String,Integer> results = new TreeMap<>();
		//connect to linked page, retrieve html code, parse it, and convert to normal text
		String page = "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm";
		String words = Jsoup.connect(page).get().html();
		Document poemDoc = Jsoup.parseBodyFragment(words);
		//the substring indices are designed to skip everything except the desired poem
		String convertedWords = poemDoc.text().toLowerCase().substring(996, 7241);
		//test to indicate that full poem is located properly
		//System.out.println(convertedWords);
		
		//create pattern to recognize words and matcher to compare frequencies
		Pattern ptn = Pattern.compile("[a-z]+");
		Matcher mtch = ptn.matcher(convertedWords);
		//while a word is found, match it against the document to find duplicates and increment frq
		//This uses BiFunctions to determine equivalence and position
		while (mtch.find()) {
			String word = mtch.group();
			int letters = word.length();
			if (letters > frq) {
				frq = letters;
			}
			if (results.containsKey(word)) {
				results.computeIfPresent(word, (w,c) -> Integer.valueOf(c.intValue() + 1));
			}
			else {
				results.computeIfAbsent(word, (w) -> Integer.valueOf(1));
			}
		}
		//passes results TreeMap to external comparator to organize the data - used as test to determine frq calculation success
		//System.out.println(entriesSortedByValues(results));
		
		
		//heads off list, creates a SortedSet, and retrieves entries by converting to a List.
		//Unfortunately, I could not get the list to orient itself from most frequent to least within the desired range, but the correct 20 values are present.
		System.out.println("20 most frequent words, from least to greatest:");
        SortedSet<Entry<String, Integer>> resultSet = (entriesSortedByValues(results));
        
        for (int x = resultSet.size()-20; x < resultSet.size(); x++) {
        	System.out.println(new ArrayList(resultSet).get(x).toString());
        }
		
	}

	//helper class to create a custom comparator that respects both keys and values in the set
	static <K,V extends Comparable<? super V>> SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
        SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
            new Comparator<Map.Entry<K,V>>() {
                @Override public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) {
                    int res = e1.getValue().compareTo(e2.getValue());
                    return res != 0 ? res : 1; // Special fix to preserve items with equal values
                }
            }
        );
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }

}
