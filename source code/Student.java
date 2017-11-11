/**
 * Created by Tomas Kovtun on 1/22/2017.
 */
public class Student {
    private int regNo;
    private int exNo;
    private String stage;
    private int[] marks;
    private int averageMark;

    public Student(int regNo, int exNo, String stage, int[] marks, int averageMark)
    {
        this.regNo=regNo;
        this.exNo=exNo;
        this.stage=stage;
        this.marks=marks;
        this.averageMark=averageMark;
    }

    public int getRegNo()
    {
        return regNo;
    }

    public int getExNo()
    {
        return exNo;
    }

    public String getStage()
    {
        return stage;
    }

    public int[] getMarks()
    {
        return marks;
    }

    public int getAverageMark(){ return averageMark;}

    public void setAverageMark(int n){averageMark=n;}

    @Override
    public String toString() {
        return (regNo+" "+averageMark);
    }
}
