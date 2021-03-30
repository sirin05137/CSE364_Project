package group11;

import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;
import junitparams.mappers.CsvWithHeaderMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

//import org.junit.Ignore;
//import org.junit.Rule;
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

@RunWith(JUnitParamsRunner.class)
public class projectTest extends project {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Test
    @FileParameters(value = "src/test/resources/test_1genre_invalid.csv", mapper = CsvWithHeaderMapper.class)
     public void testwithCSV(String genre, String occupation, String expected) throws IOException {
        String[] args = new String[2];
        args[0] = genre.toLowerCase().trim();
        args[1] = occupation.toLowerCase().trim();
        //System.out.printf("The rating of %s rated by %s : ", args[0], args[1]);

        project.main(args);
        //System.out.printf(" (Exp : %s)\n",expected);

        //System.out.print("hello");
        assertEquals(expected, outContent.toString());
        //System.out.print(project.ResultString());
        //assertEquals(expected, project.ResultString());
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
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
