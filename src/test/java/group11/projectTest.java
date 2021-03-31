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
import static org.junit.jupiter.api.Assertions.assertTrue;

//import org.junit.Ignore;
//import org.junit.Rule;

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

    // --- 1genre 1occupation 모든 조합 결과값 나오나 확인하는 테스트 ---
    // 테스트 결과 pass 기준 : (double)결과값 > 0
    // 총 378 test cases (18 genres * 21 occupation)
    // 테스트 전 에 꼭 project.java 마지막 부분 아래 print 문구를 :
    // System.out.printf("\nThe rating of %s rated by %s : %.2f", genreinput, occupationinput, CalculatedInput);
    // 이로 수정해주세요 :
    // System.out.printf("\n%.2f", CalculatedInput);
    @Test거
    @FileParameters(value = "src/test/resources/test1genre.csv", mapper = CsvWithHeaderMapper.class)
     public void test1genre(String genre, String occupation, String expected) throws IOException {
        String[] args = new String[2];
        args[0] = genre.toLowerCase().trim();
        args[1] = occupation.toLowerCase().trim();
        //System.out.printf("The rating of %s rated by %s : ", args[0], args[1]);

        project.main(args);
        //System.out.printf(" (Exp : %s)\n",expected);
        //assertEquals(expected, outContent.toString());
        double result = Double.parseDouble(String.valueOf(outContent));
        assertTrue(result > 0);
    }

    // --- multiple genres 결과값 맞나 확인하는 테스트 ---
    // 테스트 결과 pass 기준 : (double)결과값 == expected(엑셀손계산)
    // 총 23 test cases
    @Test
    @FileParameters(value = "src/test/resources/testMultiple.csv", mapper = CsvWithHeaderMapper.class)
    public void testMultiplegenre(String genre, String occupation, String expected) throws IOException {
        String[] args = new String[2];
        args[0] = genre.toLowerCase().trim();
        args[1] = occupation.toLowerCase().trim();

        project.main(args);

        assertEquals(expected, outContent.toString());
    }


    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

}
