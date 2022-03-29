import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedSet;

import org.junit.Test;

public class GUITest {
	
	@Test
	public void test() {
		try {
			//initializes a resultSet to store values
			SortedSet<Entry<String, Integer>> resultSet;
			//Constructs the full set of values using the static analyzer() program. This call forces the use of exception handlers.
			resultSet = GUI.analyzer();
			//initializes resultList to handle comparisons of values in assertEquals()
			List<String> resultList = new ArrayList<String>();
			//constructs list of outputs using for loop and initial values. This list is designed to match the final outputField after activation as much as possible
			resultList.add("20 Entries, Sorted Least to Greatest\r\n");
			resultList.add("\r\n");
			for (int x = resultSet.size()-20; x < resultSet.size(); x++) {
	        	resultList.add(new ArrayList(resultSet).get(x).toString() + "\r\n"); 
			}
			//compares the values stored within resultList against a preconstructed list of the top 20 values formatted to match the style of resultList
			assertEquals("[20 Entries, Sorted Least to Greatest\r\n"
					+ ", \r\n"
					+ ", more=8\r\n"
					+ ", or=8\r\n"
					+ ", then=8\r\n"
					+ ", with=8\r\n"
					+ ", me=9\r\n"
					+ ", bird=10\r\n"
					+ ", on=10\r\n"
					+ ", chamber=11\r\n"
					+ ", is=11\r\n"
					+ ", nevermore=11\r\n"
					+ ", raven=11\r\n"
					+ ", door=14\r\n"
					+ ", a=15\r\n"
					+ ", this=16\r\n"
					+ ", that=18\r\n"
					+ ", of=22\r\n"
					+ ", my=24\r\n"
					+ ", i=32\r\n"
					+ ", and=38\r\n"
					+ ", the=57\r\n"
					+ "]",
					resultList.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
