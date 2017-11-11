import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Tomas Kovtun on 2/5/2017.
 */
public class BarChart{
    private static final int IMAGE_WIDTH=1000;
    private static final int IMAGE_HEIGHT=500;
    private static final int CHART_HEIGHT=300;
    private static final int CHART_WIDTH=600;
    private int CHART_X=(IMAGE_WIDTH-CHART_WIDTH)/2;
    private int CHART_Y=(IMAGE_HEIGHT-CHART_HEIGHT)/2;
    private int barWidth=CHART_WIDTH/12;
    private int barGap;
    private String[] labels;
    private int[] data;
    private String title;
    private Graphics2D chart;
    private BufferedImage image;

    public BarChart( int[] data,String[] labels, String title)
    {
        if(data.length!=labels.length) throw new BarChartException("Data does not match");
        this.title=title;
        this.labels=labels;
        this.data=data;
        image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        chart = image.createGraphics();
        chart.setBackground(Color.white);
        chart.clearRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
        draw();
    }

    private boolean saveImage()
    {
        try {
            ImageIO.write(image, "png", new File("chart.png"));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public BufferedImage getBufferedImage()
    {
            return image;
    }

    private void draw()
    {
        chart.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color[] colors= new Color[6];
        colors[0]=new Color(6,62,87);
        colors[1]=new Color(69,173,183);
        colors[2]=new Color(250,177,23);
        colors[3]=new Color(242,113,108);
        colors[4]=new Color(188,167,102);
        chart.setPaint(Color.black);
        chart.setFont(new Font("Calibri", Font.PLAIN, 25));
        chart.drawString(title, 25, 25);

        int barX=CHART_X;
        barGap=CHART_WIDTH/data.length-barWidth;
        doThePlotter();
        for(int i=0;i<data.length;i++)
        {
            int barHeight=CHART_HEIGHT*data[i]/100;
            Color color = colors[i%5];
            chart.setPaint(color);
            chart.fillRect(barX, CHART_Y+CHART_HEIGHT-barHeight,barWidth , barHeight);

            chart.setPaint(Color.black);
            chart.drawString(labels[i],barX, CHART_Y+CHART_HEIGHT+23);
            barX+=barGap+barWidth;
        }


    }

    private void doThePlotter()
    {
        chart.setPaint(Color.lightGray);
        chart.setFont(new Font("Calibri", Font.PLAIN, 13));
        chart.drawLine(CHART_X-5,CHART_Y,CHART_X+5+CHART_WIDTH,CHART_Y);
        chart.drawString("100",CHART_X-25, CHART_Y);
        chart.drawLine(CHART_X-5,CHART_Y+CHART_HEIGHT/4,CHART_X+CHART_WIDTH+5,CHART_Y+CHART_HEIGHT/4);
        chart.drawString("75",CHART_X-25, CHART_Y+CHART_HEIGHT/4);
        chart.drawLine(CHART_X-5,CHART_Y+CHART_HEIGHT/2,CHART_X+CHART_WIDTH+5,CHART_Y+CHART_HEIGHT/2);
        chart.drawString("50",CHART_X-25, CHART_Y+CHART_HEIGHT/2);
        chart.drawLine(CHART_X-5,CHART_Y+CHART_HEIGHT/4*3,CHART_X+CHART_WIDTH+5,CHART_Y+CHART_HEIGHT/4*3);
        chart.drawString("25",CHART_X-25, CHART_Y+CHART_HEIGHT/4*3);
        chart.drawLine(CHART_X-5,CHART_Y+CHART_HEIGHT,CHART_X+CHART_WIDTH+5,CHART_Y+CHART_HEIGHT);

        chart.setPaint(Color.red);
        chart.drawLine(CHART_X-5,CHART_Y+CHART_HEIGHT/100*30,CHART_X+CHART_WIDTH+5,CHART_Y+CHART_HEIGHT/100*30);
        chart.drawString("70",CHART_X-25, CHART_Y+CHART_HEIGHT/100*30);
        chart.setFont(new Font("Calibri", Font.PLAIN, 20));
    }

    class BarChartException extends RuntimeException
    {
        BarChartException(String msg)
        {
            super(msg);
        }
    }

    public static void main(String [] args)
    {
        String[] names= new String[10];
        names[0]="CE141";
        names[1]="CE135";
        names[2]="CE654";
        names[3]="CE145";
        names[4]="CE146";
        names[5]="CE325";
        names[6]="CE156";
        names[7]="CE228";
        names[8]="CE322";
        names[9]="CE568";
        int[] num= new int[10];
        num[0]=30;
        num[1]=80;
        num[2]=30;
        num[3]=51;
        num[4]=32;
        num[5]=2;
        num[6]=65;
        num[7]=100;
        num[8]=23;
        num[9]=89;
        BarChart p=new BarChart( num, names,"Pie chart representation of numbers");
        p.saveImage();
    }
}