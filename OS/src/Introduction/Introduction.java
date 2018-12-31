package Introduction;

import javax.swing.*;
import java.awt.*;
import java.io.File;

//算法简介
public class Introduction extends JFrame {

    private String Title;   //传参算法名称

    public Introduction(String algorithmName) {
        super(algorithmName + "算法简介");
        this.Title = algorithmName;
        initIntroduction();
    }

    public void initIntroduction() {
        //设置窗体大小、位置、布局、关闭操作、可见性，加入中间容器
        setSize(500,400);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        MyPanel panel = new MyPanel();
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
