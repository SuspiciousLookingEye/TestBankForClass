import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ GUITest.class, SorterTest.class })
public class AllTests {
//Nothing is required for this to work except the annotations above, which tell JUnit what classes to test and how to test them.
}
