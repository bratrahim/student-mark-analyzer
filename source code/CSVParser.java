import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVParser implements ICSVParser{
    private ArrayList<Student> studentArrayList;
    private String[] modules;
    private int numberOfModules;
    private int numberOfCells;

    @Override
    public ArrayList<Student> getStudents() {
        return studentArrayList;
    }

    @Override
    public void readFile(String path) throws FileNotFoundException,IOException{
        /*if(!(path.substring(path.length()-3,path.length()-1).equals(".csv")))
        {
            throw new InvalidFileFormatException("Please choose .csv format file");
        }*/
        studentArrayList = new ArrayList<>();
        BufferedReader br;
        String separator = ",";
            br = new BufferedReader(new FileReader(path));
            String line = br.readLine();
            String[] head = line.split(separator);
            numberOfCells = head.length;
            numberOfModules = numberOfCells - 5;
            modules = new String[numberOfModules];
            int j = 0;
            for (int i = 3; i < numberOfCells - 2; i++) {
                modules[j] = head[i];
                j++;
            }
            line = br.readLine();
            while (line != null) {
                String[] studentInformation = line.split(separator);
                int[] marks = new int[numberOfModules];
                j = 0;
                for (int i = 3; i < numberOfCells - 2; i++) {
                    if (studentInformation[i].equals(""))
                        marks[j] = -1;
                    else
                        marks[j] = Integer.parseInt(studentInformation[i]);
                    j++;
                }
                studentArrayList.add(new Student(Integer.parseInt(studentInformation[0]), Integer.parseInt(studentInformation[1]), studentInformation[2], marks, Integer.parseInt(studentInformation[studentInformation.length - 1])));
                line = br.readLine();
            }
            br.close();
    }
    public String[] getModules()
    {
        return modules;
    }

}

interface ICSVParser
{
    void readFile(String path) throws FileNotFoundException,IOException; //makes an ArrayList of students
    ArrayList<Student> getStudents();

}


class InvalidFileFormatException extends Exception
{
    public InvalidFileFormatException(String message)
    {
        super(message);
    }
}