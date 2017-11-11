import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Tomas Kovtun on 2/5/2017.
 */
public class PieChart{
    private static final int IMAGE_WIDTH=1000;
    private static final int IMAGE_HEIGHT=500;
    private static final int DESCRIPTION_X=600;
    private int DESCRIPTION_Y=200;
    private String[] labels;
    private int[] data;
    private String title;
    private Graphics2D chart;
    private BufferedImage image;

    public PieChart(String[] labels, int[] data, String title)
    {
        if(data.length!=labels.length) throw new PieChartException("Data does not match");
        this.title=title;
        this.labels=labels;
        this.data=data;
        image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        chart = image.createGraphics();
        chart.setBackground(Color.white);
        chart.clearRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
        draw();
    }

    public BufferedImage getBufferedImage()
    {
        return image;
    }

    public boolean saveImage()
    {
        try {
            ImageIO.write(image, "png", new File("chart.png"));
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    public void draw()
    {
        //chart.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        chart.setFont(new Font("Calibri", Font.PLAIN, 25));
        Color[] colors= new Color[6];
        colors[0]=new Color(6,62,87);
        colors[1]=new Color(69,173,183);
        colors[2]=new Color(250,177,23);
        colors[3]=new Color(242,113,108);
        colors[4]=new Color(188,167,102);
        chart.setPaint(Color.black);
        chart.drawString(title, 25, 25);
        double total=0;
        for(int i=0;i<data.length;i++)
        {
            total+=data[i];
        }
        int first=0;
        Double size;
        int circle=360;
        for(int i=0;i<data.length;i++)
        {
            Color color = colors[i];
            chart.setPaint(color);
            size=(data[i]/total)*360;
            circle-=size.intValue();
            System.out.println(circle);
            if(i==data.length-1)
                size+=circle;
            chart.fillArc(300, 100, 250, 250, first, size.intValue());
            first+=size;

            chart.setPaint(Color.black);
            chart.drawString(labels[i],DESCRIPTION_X-5, DESCRIPTION_Y);
            chart.setPaint(colors[i]);
            chart.fillRect(DESCRIPTION_X+90, DESCRIPTION_Y-18, 20, 20);
            DESCRIPTION_Y+=30;
        }
    }

    class PieChartException extends RuntimeException
    {
        PieChartException(String msg)
        {
            super(msg);
        }
    }

    public static void main(String [] args)
    {
        String[] names= new String[5];
        names[0]="odin";
        names[1]="dva";
        names[2]="tri";
        names[3]="cetyre";
        names[4]="cetyre";
        int[] num= new int[5];
        num[0]=30;
        num[1]=80;
        num[2]=30;
        num[3]=30;
        num[4]=30;
        PieChart p=new PieChart(names, num, "Pie chart representation of numbers");
        p.saveImage();
    }

}