package Introduction;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

//作业调度算法简介
public class Introduction extends JFrame {

    private String Title;   //传参算法名称
    private MyPanel panel;  //中间容器

    public Introduction(String algorithmName) throws IOException {
        super(algorithmName + "算法简介");
        this.Title = algorithmName;
        initIntroduction();
    }

    public void initIntroduction() throws IOException {
        //根据背景动态地设置窗体大小
        File picture = new File(new File("").getAbsolutePath() + "/Images/" + Title + ".png");
        BufferedImage sourceImg = ImageIO.read(new FileInputStream(picture));
        setSize(sourceImg.getWidth(),sourceImg.getHeight());

        //位置、布局、关闭操作、可见性，加入中间容器
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        panel = new MyPanel();
        setContentPane(panel);

        setVisible(true);
    }

    //该窗体的中间容器类
    class MyPanel extends JPanel {

        public MyPanel() {
            super();
            setVisible(true);
        }

        @Override
        protected void paintComponent(Graphics g) { //把算法简介加入到图片里通过弹窗背景形式展示
            String imagePath = new File("").getAbsolutePath() + "/Images/" + Title + ".png";
            ImageIcon icon = new ImageIcon(imagePath);
            Image image = icon.getImage();
            g.drawImage(image,0,0,this.getWidth(),this.getHeight(),this);
        }
    }
}
