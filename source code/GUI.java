import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Tomas Kovtun on 2/19/2017.
 */
public class GUI extends JFrame{
    private ArrayList<Student> studentArrayList;
    private CSVParser parser;
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        JFrame frame = new JFrame("GUI");
        frame.setContentPane(new GUI().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(600,280);
        frame.setResizable(false);
        frame.setTitle("Marks Analyzer");
        frame.setVisible(true);

    }

    private JTextField inputTextField;
    private JTextField outputTextField;
    private JComboBox modeComboBox;
    private JComboBox optionComboBox;
    private JButton generateButton;
    private JPanel mainPanel;
    private JButton openButton;
    private String inputPath;
    private String outputPath;

    public GUI()
    {
        JFileChooser fileChooser = new JFileChooser();

        inputTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                fileChooser.showSaveDialog(GUI.this);
                inputTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        outputTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.showSaveDialog(GUI.this);
                outputTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        inputTextField.addFocusListener(new FocusAdapter() {
        });

        openButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try
                {
                    inputPath=inputTextField.getText();
                    parser=new CSVParser();
                    parser.readFile(inputPath);
                    studentArrayList=parser.getStudents();
                    modeComboBox.setEnabled(true);
                    optionComboBox.setEnabled(true);
                    generateButton.setEnabled(true);
                    optionComboBox.removeAllItems();
                    for(Student s:studentArrayList)
                        optionComboBox.addItem(s.getRegNo());
                }
                catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(null, "Invalid input file. Please choose another");
                }

            }
        });

        modeComboBox.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                System.out.println(String.valueOf(modeComboBox.getSelectedItem()));
                String choice = String.valueOf(modeComboBox.getSelectedItem());
                if(choice.equals("Student"))
                {
                    optionComboBox.removeAllItems();
                    for(Student s:studentArrayList)
                        optionComboBox.addItem(s.getRegNo());
                }
                else if(choice.equals("Module"))
                {
                    optionComboBox.removeAllItems();
                    for(String module:parser.getModules())
                        optionComboBox.addItem(module);
                }
                else
                {
                    optionComboBox.removeAllItems();
                    optionComboBox.addItem("BSc CompSc");
                }
            }
        });

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputPath=outputTextField.getText();
                PDFExporter exporter;
                String mode=String.valueOf(modeComboBox.getSelectedItem());
                try{
                    if(mode.equals("Student"))
                    {
                        exporter=new PDFExporter(studentArrayList,parser.getModules(),Integer.parseInt(String.valueOf(optionComboBox.getSelectedItem())),outputPath);
                        exporter.generateStudentReport();
                    }
                    else if (mode.equals("Module"))
                    {
                        exporter=new PDFExporter(studentArrayList,parser.getModules(),Integer.parseInt(String.valueOf(optionComboBox.getSelectedItem())),outputPath);
                        exporter.generateModuleReport();
                    }
                    else
                    {
                        exporter=new PDFExporter(studentArrayList,parser.getModules(),Integer.parseInt(String.valueOf(optionComboBox.getSelectedItem())),outputPath);
                        exporter.generateCourseReport();
                    }


                }catch (IOException ex)
                {
                    System.out.println(ex.getMessage());
                    JOptionPane.showMessageDialog(null, "Output directory not found");
                }
            }
        });
    }
}
