import java.util.*;
import java.util.Map.Entry;
import java.util.regex.*;
import org.jsoup.*;
import org.jsoup.nodes.Document;

public class Module2 {

	public static void main(String[] args) throws Exception {
		analyzer();
	}
	
	public static void analyzer () throws Exception {
		int frq = 0;
		TreeMap<String,Integer> results = new TreeMap<>();
		String page = "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm";
		String words = Jsoup.connect(page).get().html();
		Document poemDoc = Jsoup.parseBodyFragment(words);
		String convertedWords = poemDoc.text().toLowerCase().substring(996, 7241);
		System.out.println(convertedWords);
		Pattern ptn = Pattern.compile("[a-z]+");
		Matcher mtch = ptn.matcher(convertedWords);
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

		System.out.println(entriesSortedByValues(results));
		System.out.println("20 most frequent words, from least to greatest:");
        SortedSet<Entry<String, Integer>> resultSet = (entriesSortedByValues(results));
        
        for (int x = resultSet.size()-20; x < resultSet.size(); x++) {
        	System.out.println(new ArrayList(resultSet).get(x).toString());
        }
		
	}
	
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
