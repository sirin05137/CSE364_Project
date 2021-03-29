package group11;

import org.junit.Test;
import org.junit.runner.RunWith;
//import org.junit.Ignore;
//import org.junit.Rule;

import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;
/*
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.MockitoJUnit;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
*/
import java.io.IOException;

@RunWith(JUnitParamsRunner.class)
public class projectTest extends project {

    @Test
    @FileParameters(value = "src/test/resources/testCSV.csv")
    public void testwithCSV(String genre, String occupation, String expected) throws IOException {
        String[] args = new String[]{genre, occupation};
        //String[] args = new String[2];
        //args[0] = genre;
        //args[1] = occupation;

        //System.out.println(args[0] + "/" + args[1]);

        project.main(args);
        //System.out.print(project.ResultString());
        //assertEquals(expected, project.ResultString());
    }

    /*
    public class ParameterizedTest {
        /*
        @Rule
        public MockitoRule rule = MockitoJUnit.rule();

        @Mock
        private project projectMock;

        @Test
        public void testMock()  {
            assertNotNull(projectMock);
        }

    }*/

}
