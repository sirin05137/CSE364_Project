package group11;

import junitparams.JUnitParamsRunner;
import org.junit.After;
import org.junit.Before;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.runner.RunWith;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class milestone2Test extends milestone2 {

    private String emptyValue = null;

    @Test
    public void test() throws IOException {
        String[] args = new String[3];
        args[0] = emptyValue;
        args[1] = emptyValue;
        args[2] = emptyValue;

        milestone2.main(args);
        //assertEquals(expected, outContent.toString());
    }
}