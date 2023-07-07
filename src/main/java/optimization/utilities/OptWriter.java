package optimization.utilities;

import com.opencsv.CSVWriter;
import org.apache.commons.lang3.ArrayUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

public class OptWriter {
    CSVWriter writer;
    DecimalFormat format = new DecimalFormat("0.00000");
    String fileName;
    String[] lineToWrite;
    public OptWriter(String fileName, DecimalFormat format){
        this.format = format;
        this.fileName = fileName;
        this.lineToWrite = null;
    }

    public void initialize(String... columnNames){
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("output\\");
            sb.append(fileName);
            sb.append('-');
            //sb.append(LocalDateTime.now().toString().replace('.', '-').replace(':', '-'));
            sb.append("latest");
            sb.append(".csv");
            writer = new CSVWriter(new FileWriter(sb.toString()));

            writer.writeNext(columnNames);
            writer.flush();
        } catch (IOException exception){
            System.out.println(exception.toString());
            System.exit(-1);
        }
    }

    public OptWriter append(Point point){
        double[] location = point.getLocation();
        String[] numbersToWrite = new String[location.length];

        for (int i=0; i< location.length; i++) {
            numbersToWrite[i] = (format.format(location[i]).replace('.', ','));
        }
        lineToWrite = ArrayUtils.addAll(lineToWrite, numbersToWrite);

        return this;
    }

    public OptWriter append(double number){
        lineToWrite = ArrayUtils.add(lineToWrite, format.format(number).replace('.', ','));

        return this;
    }
    public OptWriter append(Vector vector){
        double[] components = vector.getComponents();
        String[] numbersToWrite = new String[components.length];

        for (int i=0; i< components.length; i++) {
            numbersToWrite[i] = (format.format(components[i]).replace('.', ','));
        }
        lineToWrite = ArrayUtils.addAll(lineToWrite, numbersToWrite);

        return this;
    }

    public void push(){
        writer.writeNext(lineToWrite);
        lineToWrite = null;
    }

    public void save() {
        try {
            writer.flush();
        }catch (IOException exception) {
            System.out.println(exception.toString());
        };
    }
}
