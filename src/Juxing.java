import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class Juxing extends JFrame {

    public static void main(String[] args) {
        Juxing juxing = new Juxing();
        juxing.count();
        juxing.view();
    }


    int outWidth, outHeight, innerWidth, innerHeight, n, m, lastNum, XSpacing, left,
            YSpacing, top, lastXSpacing, lastLeft;

    public void count() {

        Scanner scanner = new Scanner(System.in);
        //outWidth表示外矩形的宽，outHeight表示外矩形的高，innerScale表示内部矩形的宽高比，num表示放置的矩形个数。
        System.out.println("输入外部矩形的宽：");
        outWidth = scanner.nextInt();
        System.out.println("输入外部矩形的高：");
        outHeight = scanner.nextInt();
        System.out.println("输入内部矩形的宽高比：");
        double innerScale = scanner.nextDouble();
        System.out.println("输入放置的内部矩形个数：");
        int num = scanner.nextInt();



        /**
         * 以下是核心算法,设优先充满的行或列的行数或列数为n，则相应的后充满的列或行的列数或行数为f(n)，
         * 且有a(n-1)<=f(n)<=a(n+1),其中a的值分两种情况：
         * 1.outScale>=innerScale,a=(outScale/innerScale);
         * 2.outScale<innerScale,a=innerScale。
         */
        double outScale = (double) outWidth / outHeight, a;
        a = outScale / innerScale;
        //任何一个数量num，必定小等于n*a(n+1)，大等于n*a(n-1)，可以转化为数学问题进行求解，设n*a(n+1)=num。
        n =  (int)(Math.ceil((Math.sqrt(1 + 4 * num / a) - 1) / 2));
        //m为列的数量，当m=a(n+1)时，n增加一行的排列也成立，这里忽略了这种情况；
        m = (int)(Math.ceil((double) num / n));
        //把列填满
        n=(int)(Math.ceil((double) num / m));

        //下面计算内部矩形的宽高
        if (m > a * n) {
            //n为行数，m为列数，优先考虑列
            innerWidth = outWidth / m;
            innerHeight = (int) (innerWidth / innerScale);
            lastNum = m - (m * n - num);
        } else {
            //n为行数，m为列数，优先考虑行
            innerHeight = outHeight / n;
            innerWidth = (int) (innerHeight * innerScale);
            lastNum = m - (m * n - num);
        }
        System.out.println("可以排列成" + n + "行" + m + "列；内部矩形高为" + innerHeight
                + "，宽为" + innerWidth + "，最后一行有" + lastNum + "个矩形");



        //以下为数据计算，与核心算法无关

        int XBlank = outWidth - m * innerWidth;
        XSpacing = XBlank / (m + 1);
        left = (XBlank - (m + 1) * XSpacing) / 2;
        int YBlank = outHeight - n * innerHeight;
        YSpacing = YBlank / (n + 1);
        top = (YBlank - (n + 1) * YSpacing) / 2;
        int lastXBlank = outWidth - lastNum * innerWidth;
        lastXSpacing = lastXBlank / (lastNum + 1);
        lastLeft = (lastXBlank - (lastNum + 1) * lastXSpacing) / 2;

    }


    public void view() {

        this.setTitle("排列演示");
        MyPanel myPanel=new MyPanel();
        this.add(myPanel);
        this.setLocation(100,100);
        this.setSize(outWidth+250,outHeight+250);
        this.setResizable(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    class MyPanel extends JPanel {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.setColor(Color.pink);
            for (int i = 1; i < n; i++) {
                for (int j = 1; j <= m; j++) {
                    g.fillRect((left+100+XSpacing) + (j - 1) * (innerWidth + XSpacing), (top+100+YSpacing) + (i - 1) * (innerHeight + YSpacing), innerWidth, innerHeight);
                }
            }
            for(int k=1;k<=lastNum;k++){
                g.fillRect((lastLeft+100+lastXSpacing)+(k-1)*(innerWidth+lastXSpacing),(top+100+YSpacing) + (n - 1) * (innerHeight + YSpacing),innerWidth,innerHeight);
            }
            g.setColor(Color.black);
            for (int i = 1; i < n; i++) {
                for (int j = 1; j <= m; j++) {
                    g.drawRect((left+100+XSpacing) + (j - 1) * (innerWidth + XSpacing), (top+100+YSpacing) + (i - 1) * (innerHeight + YSpacing), innerWidth, innerHeight);
                }
            }
            for(int k=1;k<=lastNum;k++){
                g.drawRect((lastLeft+100+lastXSpacing)+(k-1)*(innerWidth+lastXSpacing),(top+100+YSpacing) + (n - 1) * (innerHeight + YSpacing),innerWidth,innerHeight);
            }
            g.setColor(Color.GRAY);
            g.drawRect(100,100,outWidth,outHeight);
        }
    }


}
