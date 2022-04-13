import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.*;
import javax.swing.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * This class handles all programming logic for constructing and framing the Word Occurrences GUI as well as retrieving the website data and parsing it for entries.
 * <p> This relies heavily on the free JSoup library to efficiently handle parsing HTML documents. </p>
 * @author Jordan Roig
 * @since 15.02+7
 */
public class GUI extends JFrame implements ActionListener{
//these UI elements are created as fields to allow for usage from anywhere within the class.
	private JButton commitButton = new JButton();
	private JTextArea outputField = new JTextArea();
	private static final long serialVersionUID = 1L;

//constructor uses superclass elements and then calls creation of GUI
	/**
	 * This constructor first calls superclass JFrame elements to construct the actual JFrame, and then class the public create() method to customize it.
	 */
	public GUI() {
		super();
		create();
	}
	
	//sets style elements and prepares labels
	
	/**
	 * Upon construction, this method is called. It sets default closing behavior, window naming, layout, element sizes, and adds in elements.
	 * <p>Critically, this method also adds in an actionListener for the button to ensure the program works. Elements are then packed in and setup for their inactive state for the listener to change.</p>
	 * <p>The important elements whose properties are changed later are stored as private fields to ensure they can be accessed within the class outside of this method.</p>
	 */
	public void create() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Raven Frequency Search");
		this.setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
		this.add(new JLabel());
		this.add(new JLabel("Press the button to search the document."));
		//creates action listener reference for button, and sets initial parameters.
		commitButton.addActionListener(this);
		this.add(commitButton);
		commitButton.setMaximumSize(new Dimension(150,50));
		commitButton.setText("Ready to Search");
		
		this.add(new JLabel("Response"));
		//sets up the output field and makes it non-editable.
		this.add(outputField);
		outputField.setMaximumSize(new Dimension(400,500));
		outputField.setEditable(false);
		//sets positions within JPanel and screen and enables the UI
		this.pack();
		this.setBounds(0, 0, 400, 650);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	
	/**
	 *This method overrides the actionPerformed abstract method to set behavior for execution.
	 *<p>In this case, the listener will change the button text to indicate execution has begun, and then call the analyzer() method to retrieve sorted results.
	 *It will then construct an output list of text and append it into the JTextArea from the create() method for viewing.</p>
	 *<p>This method requires a try/catch block due to the need to handle the exceptions thrown by analyzer().</p>
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		//upon button click, sets the button as the source object and changes its text to change upon action completion
		JButton source = (JButton) e.getSource();
		
		source.setText("Output Received");
		//calls analyzer core program, receives its resultSet, and displays it within outputField.
		try {
			SortedSet<Entry<String, Integer>> resultSet = analyzer();
			outputField.append("20 Entries, Sorted Least to Greatest");
			outputField.append("\n");
			outputField.append("\n");
			for (int x = resultSet.size()-20; x < resultSet.size(); x++) {
	        	outputField.append(new ArrayList(resultSet).get(x).toString());
	        	outputField.append("\n");
	        }
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	/**
	 * This method constructs a TreeMap object that handles sorting and equivalence for words in a poem. 
	 * It first establishes a connection to the site hosting the poem, then retrieves the HTML to get text. It then parses it for the words of the poem and uses a BiFunction to match words to frequency.
	 * <p>The method will then call the entriesSortedByValues() method to sort the values and then return the new list for display.</p>
	 * <p>This method uses the JSoup library to handle parsing of an HTML document retrieved from a website.</p>
	 * @return Returns a SortedSet object that contains every unique word in the poem matched to its frequency.
	 * @throws Exception Handles the possible IOException or connection failure that may occur when attempting to retrieve data from a website.
	 */
	public static SortedSet<Entry<String, Integer>> analyzer () throws Exception {
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
		
        SortedSet<Entry<String, Integer>> resultSet = (entriesSortedByValues(results));
        
        return resultSet;
		
	}

	//helper method to create a custom comparator that respects both keys and values in the set
	/**
	 *This method allows for the sorting of TreeMaps by their values rather than their keys, something that normally must be done by manually pulling out every value from the map and then sorting.
	 *This method is critical for the GUI class because the words that are found must be the keys to the map. Keys cannot be repeated, but values can. As such, words will only appear once if set as a value.
	 *<p>By using the comparator within this method and changing the object type to SortedSet, the map now can be read in order of frequencies rather than by alphabetical order for the key word values.</p>
	 * @param <K> A generic variable for TreeMap objects that handles unknown data types for the key.
	 * @param <V> A generic variable for TreeMap objects that handles unknown data types for the value.
	 * @param map A generic map object that is passed for sorting by its values.
	 * @return Returns a sorted TreeMap object as a SortedSet object, where the SortedSet is now sorted by values rather than keys.
	 */
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
