package mas.ssatr.bodea.ciprian;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class OutputFileWriter {

    private static final String fileName = "simulationResults.txt";

    public static void DeletePreviousOutputs() {
        File myObj = new File(fileName);
        if (myObj.delete()) {
            System.out.println("New file printed");
        }
    }

    public static void WriteOneLine(String content) {
        System.out.print(content);

        // https://beginnersbook.com/2014/01/how-to-write-to-file-in-java-using-bufferedwriter/

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true));
            bw.write(content);
            bw.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
