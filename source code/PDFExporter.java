import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Tomas Kovtun on 2/21/2017.
 */
public class PDFExporter implements IPDFExporter{
    private final ArrayList<Student> studentArrayList;
    private Student student;
    private final String[] modules;
    private final int BEGIN_X=100;
    private final int BEGIN_Y=700;
    private int pivotY=BEGIN_Y;
    private int exNo;
    private int figures=0;
    private int studentRegNo=0;
    private final String path;
    private PDDocument document = null;
    private PDPage page = null;
    //course
    public PDFExporter(ArrayList<Student> studentArrayList, String[] modules, String path)
    {
        this.studentArrayList=studentArrayList;
        this.modules=modules;
        this.path=path;
    }
    //student
    public PDFExporter (ArrayList<Student> studentArrayList, String[] modules, int regNo, String path)
    {
        this.studentArrayList=studentArrayList;
        this.studentRegNo=regNo;
        this.path=path;
        this.modules=modules;
        for(Student s:studentArrayList)
            if (s.getRegNo()==regNo)
                student=s;
    }
    @Override
    public void generateCourseReport() throws IOException{

    }

    @Override
    public void generateModuleReport() {

    }

    @Override
    public void generateStudentReport() {
        try{
            document = new PDDocument();
            page = new PDPage();
            document.addPage(page);
            PDFont font = PDType1Font.HELVETICA;

            PDPageContentStream content = new PDPageContentStream(document, page);

            BarChart barChart=new BarChart(getStudentMarks(),getFilteredModules(),"Student "+studentRegNo+" performance");
            PDImageXObject barChartImage = JPEGFactory.createFromImage(document, barChart.getBufferedImage());
            content.drawXObject( barChartImage, 30, 30, 600, 300 );

            String [] markGaps={"70%+","60-70%","50-60%","40-50%","40%-"};
            PieChart pieChart=new PieChart(markGaps,getStudentMarksForPieChart(getStudentMarks()),"Student "+studentRegNo+" performance");
            PDImageXObject pieChartImage = JPEGFactory.createFromImage(document, pieChart.getBufferedImage());
            content.drawXObject( pieChartImage, 250, 420, 400, 200 );

            content.beginText();
            content.setFont( font, 20 );
            content.moveTextPositionByAmount( BEGIN_X, BEGIN_Y );
            content.drawString("Student number: "+studentRegNo);
            content.endText();
            content.setFont( font, 12 );
            content.beginText();
            content.moveTextPositionByAmount( BEGIN_X, pivotY-=20);
            content.drawString("Ex number: "+exNo);
            content.endText();

            pivotY-=20;
            int tableY=pivotY-5;
            int[] tableMarks=getStudentMarks();
            String[] tableModules=getFilteredModules();
            content.setStrokingColor(Color.lightGray);
            content.drawLine(BEGIN_X-10,pivotY-5,BEGIN_X+110,pivotY-5);
            pivotY-=18;
            content.beginText();
            content.moveTextPositionByAmount( BEGIN_X, pivotY);
            content.drawString("Modules:");
            content.endText();
            content.beginText();
            content.moveTextPositionByAmount( BEGIN_X+70, pivotY);
            content.drawString("Marks:");
            content.endText();
            for(int i=0;i<tableMarks.length;i++)
            {
                if(getStudentMarks()[i]>-1)
                {
                    content.drawLine(BEGIN_X-10,pivotY-5,BEGIN_X+110,pivotY-5);
                    content.beginText();
                    content.moveTextPositionByAmount( BEGIN_X, pivotY-=18);
                    content.drawString(tableModules[i]);
                    content.endText();
                    content.beginText();
                    content.moveTextPositionByAmount( BEGIN_X+80, pivotY);
                    content.drawString(String.valueOf(tableMarks[i])+"%");
                    content.endText();
                }
                content.drawLine(BEGIN_X-10,pivotY-5,BEGIN_X+110,pivotY-5);
                //vertical lines
                content.drawLine(BEGIN_X-10,tableY,BEGIN_X-10,pivotY-5);
                content.drawLine(BEGIN_X+60,tableY,BEGIN_X+60,pivotY-5);
                content.drawLine(BEGIN_X+110,tableY,BEGIN_X+110,pivotY-5);
            }

            //Average
            content.beginText();
            content.moveTextPositionByAmount( BEGIN_X+20, pivotY-=20);
            content.drawString("Average : ");
            content.endText();
            content.beginText();
            content.moveTextPositionByAmount( BEGIN_X+80, pivotY);
            content.drawString(String.valueOf(student.getAverageMark())+"%");
            content.endText();

            //Expected degree
            content.beginText();
            content.moveTextPositionByAmount( BEGIN_X, pivotY-=35);
            content.drawString("Expected degree: ");
            if (student.getAverageMark()>=70)
                content.drawString("First ");
            else if(student.getAverageMark()>=60)
                content.drawString("2:1");
            else if(student.getAverageMark()>=50)
                content.drawString("2:2");
            else if(student.getAverageMark()>=40)
                content.drawString("Pass");
            else
            {
                content.drawString("Will not pass");
            }
            content.endText();

            //Rank
            content.beginText();
            content.moveTextPositionByAmount( BEGIN_X, pivotY-=18);
            content.drawString("Rank: "+String.valueOf(getRank(student.getAverageMark())));
            /*switch(String.valueOf(getRank(student.getAverageMark())).charAt(String.valueOf(getRank(student.getAverageMark())).length()))
            {
                case '1':
                    {
                        content.drawString("st");
                        break;
                    }
                case '2':
                {
                    content.drawString("nd");
                    break;
                }
                case '3':
                {
                    content.drawString("rd");
                    break;
                }
            }
*/
            content.drawString(" out of "+studentArrayList.size());
            content.endText();


            content.close();
            document.save(path+"\\student report.pdf");
            document.close();


        } catch (Exception e){
            System.out.println(e);
        }

    }
    private int getRank(int mark)
    {
        int rank=1;
        for(Student s:studentArrayList)
        {
            if(s.getAverageMark()>mark)
                rank++;
        }
        return rank;
    }
    private int[] getStudentMarks()
    {
        int[] studentMarks;
        int i=0;
        exNo=student.getExNo();
        for(int mark:student.getMarks())
        {
            if(mark>-1)
            {
                i++;
            }
        }
        studentMarks=new int[i];
        figures=i;
        i=0;
        for(int mark:student.getMarks())
        {
            if(mark>-1)
            {
                studentMarks[i]=mark;
                i++;
            }
        }
        return studentMarks;
    }

    private String[] getFilteredModules()
    {
        int j=0;
        String [] studentModules= new String[figures];
        if(student.getRegNo()==studentRegNo)
        {
            for(int i=0;i<student.getMarks().length;i++)
            {
                if(student.getMarks()[i]>-1)
                {
                    studentModules[j]=modules[i].substring(0,modules[i].length()-5);
                    j++;
                }
            }
        }
        return studentModules;
    }

    private int[] getStudentMarksForPieChart(int [] marks)
    {
        int [] count=new int[5];
        for(int mark : marks)
        {
            if(mark>=70) count[0]++;
            else if (mark>=60) count[1]++;
            else if (mark>=50) count[2]++;
            else if (mark>=40) count[3]++;
            else count[4]++;
        }
        return count;
    }
}


interface IPDFExporter
{
    void generateCourseReport() throws IOException;
    void generateModuleReport() throws IOException;
    void generateStudentReport() throws IOException;
}