package group11.restservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class URLChecker {
    /*
    public static void main(String[] args) throws Throwable {
        String link = "https://raw.githubusercontent.com/Crunchify/All-in-One-Webmaster/master/all-in-one-webmaster-premium.php";
        URL inputURL = new URL(link);
        HttpURLConnection crunchifyHttp = (HttpURLConnection) inputURL.openConnection();

        InputStream crunchifyStream = crunchifyHttp.getInputStream();
        String crunchifyResponse = crunchifyGetStringFromStream(crunchifyStream);
        System.out.println(crunchifyResponse);
    }

     */

    // ConvertStreamToString() Utility - we name it as crunchifyGetStringFromStream()
    private static String GetStringFromStream(InputStream crunchifyStream) throws IOException {
        if (crunchifyStream != null) {
            Writer crunchifyWriter = new StringWriter();

            char[] crunchifyBuffer = new char[2048];
            try {
                Reader crunchifyReader = new BufferedReader(new InputStreamReader(crunchifyStream, "UTF-8"));
                int counter;
                while ((counter = crunchifyReader.read(crunchifyBuffer)) != -1) {
                    crunchifyWriter.write(crunchifyBuffer, 0, counter);
                }
            } finally {
                crunchifyStream.close();
            }
            return crunchifyWriter.toString();
        } else {
            return "No Contents";
        }
    }
}