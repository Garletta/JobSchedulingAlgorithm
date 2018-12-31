package OS;

import Introduction.Introduction;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

//可视化界面主体
public class Windows extends JFrame {

    private JLabel welcome;         //欢迎语
    private JLabel Tip;             //提示输入标签
    private JTextField works_queue; //请输入作业数量
    private JButton commit;         //确认作业数量操作
    private JButton quit;           //退出程序操作

    private JLabel algorithmTitle;  //算法名
    private JTable inputDatas;      //用户输入表
    private JTable outputDatas;     //调度算法结果表
    private JScrollPane scrollPane; //滚动条
    private JButton commitFCFS;     //执行先来先服务作业调度算法
    private JButton commitSJF;      //执行短作业优先作业调度算法
    private JButton commitPSA;      //执行优先级作业调度算法
    private JButton commitHRRN;     //执行高响应比作业调度算法
    private JButton back;           //返回上一层菜单

    public Windows() {
        super("作业调度算法");
        initWindows();
    }

    public void initWindows() {
        //设置窗体大小、位置、布局、关闭操作、可见性
        setSize(600,500);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //加入中间容器（自适应窗体大小），传入主体句柄便于修改主体标题
        MyJPanel panel = new MyJPanel(this);
        setContentPane(panel);

        setVisible(true);
    }

    //该窗体的中间容器类
    class MyJPanel extends JPanel {

        private Windows windows;    //获得当前窗体的句柄

        public MyJPanel(Windows windows) {
            super();
            this.windows = windows; //保存传参句柄
            initMyJPanel();
        }

        //获得表格数据，返回ArrayList<JCB>
        public ArrayList<JCB> getDatas() {
            ArrayList<JCB> jobs = new ArrayList<>();
            for(int i = 0;i < Integer.parseInt(works_queue.getText());i++) {
                int jobSeq = (int) inputDatas.getValueAt(i,0);                                  //作业序号
                String jobName = (String) inputDatas.getValueAt(i,1);                           //作业名
                double arriveTime = Double.parseDouble((String) inputDatas.getValueAt(i,2));    //到达时间
                double serveTime = Double.parseDouble((String) inputDatas.getValueAt(i,3));     //服务时间
                double prior = Double.parseDouble((String) inputDatas.getValueAt(i,4));         //优先级
                jobs.add(new JCB(jobSeq,jobName,arriveTime,serveTime,prior));
            }
            return jobs;
        }

        //获得作业调度算法结果的数据，返回Object[][]
        public Object[][] setDatas(ArrayList<JCB> jobs) {
            Object[][] Datas = new Object[5][jobs.size() + 1];
            //行头
            Datas[0][0] = "到达时间";
            Datas[1][0] = "服务时间";
            Datas[2][0] = "开始时间";
            Datas[3][0] = "完成时间";
            Datas[4][0] = "周转时间";
            //行数据
            for (int j = 1; j <= jobs.size(); j++) {
                Datas[0][j] = jobs.get(j - 1).getArriveTime();
                Datas[1][j] = jobs.get(j - 1).getServeTime();
                Datas[2][j] = jobs.get(j - 1).getStartTime();
                Datas[3][j] = jobs.get(j - 1).getCompleteTime();
                Datas[4][j] = jobs.get(j - 1).getCyclingTime();
            }
            return Datas;
        }

        //容器初始化函数
        public void initMyJPanel() {

            //设置中间容器的布局
            setLayout(null);

            //欢迎语
            welcome = new JLabel("欢迎使用模拟作业调度算法",JLabel.CENTER);
            welcome.setBounds(0,50,600,50);
            welcome.setFont(new Font("华文行楷",0,40));
            welcome.setForeground(Color.GREEN);
            add(welcome);

            //提示输入标签
            Tip = new JLabel("请输入作业的数量:");
            Tip.setBounds(30,150,600,40);
            Tip.setFont(new Font("华文行楷",0,35));
            Tip.setForeground(Color.ORANGE);
            add(Tip);

            //请输入作业数量
            works_queue = new JTextField();
            works_queue.setHorizontalAlignment(JTextField.CENTER);
            works_queue.setBounds(360,150,200,40);
            works_queue.setFont(new Font("华文行楷",0,40));
            works_queue.setBackground(Color.GRAY);
            works_queue.setForeground(Color.WHITE);
            works_queue.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
            works_queue.setOpaque(false);
            works_queue.setCaretColor(Color.CYAN);
            works_queue.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if(e.getKeyChar() == KeyEvent.VK_ENTER)
                        commit.doClick();
                }
            });
            add(works_queue);

            //确认作业数量操作
            commit = new JButton(new ImageIcon(new File("").getAbsolutePath() + "/Images/commit.png"));
            commit.setBounds(150,400,100,40);
            commit.setFont(new Font("华文隶书",0,20));
            commit.setForeground(Color.ORANGE);
            commit.setContentAreaFilled(false);
            commit.setFocusPainted(false);
            commit.setBorderPainted(false);
            commit.setHorizontalTextPosition(JLabel.CENTER);
            commit.setVerticalTextPosition(JLabel.CENTER);
            commit.setMargin(new Insets(0,0,0,0));
            commit.setBorder(null);
            commit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int numbers = Integer.parseInt(works_queue.getText());  //用户请求的作业数量
                    Object[][] Info = new Object[numbers][5];               //按照用户需求创建表大小
                    for(int i = 0;i < numbers;i++) {                        //行序号（方便检阅）
                        Info[i][0] = i + 1;
                    }
                    Object[] columnsName = {"序号","作业名","到达时间","服务时间","紧急程度"};   //表头（列名）
                    //用户输入表
                    inputDatas = new JTable(Info,columnsName);
                    inputDatas.setBounds(20,50,560,330);
                    inputDatas.setFont(new Font("微软雅黑",0,20));
                    inputDatas.getTableHeader().setFont(new Font("微软雅黑",1,24));
                    inputDatas.getTableHeader().setBackground(Color.CYAN);
                    inputDatas.setRowHeight(34);
                    //设置列数据居中
                    DefaultTableCellRenderer r = new DefaultTableCellRenderer();
                    r.setHorizontalAlignment(JTextField.CENTER);
                    inputDatas.setDefaultRenderer(Object.class,r);
                    inputDatas.setBackground(Color.YELLOW);

                    //加入滚动条
                    scrollPane = new JScrollPane(inputDatas);
                    scrollPane.setBounds(20,50,560,330);
                    scrollPane.setFont(new Font("微软雅黑",0,20));
                    scrollPane.getViewport().setBackground(Color.GRAY);
                    add(scrollPane);

                    //隐藏没用的控件
                    welcome.setVisible(false);
                    Tip.setVisible(false);
                    works_queue.setVisible(false);
                    commit.setVisible(false);
                    quit.setVisible(false);

                    //显示下一个界面的按钮
                    algorithmTitle.setVisible(true);
                    algorithmTitle.setText("请输入作业信息:");
                    commitFCFS.setVisible(true);
                    commitSJF.setVisible(true);
                    commitPSA.setVisible(true);
                    commitHRRN.setVisible(true);
                    back.setVisible(true);
                }
            });
            add(commit);

            //退出程序操作
            quit = new JButton("退出",new ImageIcon(new File("").getAbsolutePath() + "/Images/anwen.jpg"));
            quit.setBounds(350,400,90,40);
            quit.setFont(new Font("华文行楷",0,25));
            quit.setForeground(Color.ORANGE);
            quit.setContentAreaFilled(false);
            quit.setFocusPainted(false);
            quit.setBorderPainted(false);
            quit.setHorizontalTextPosition(JLabel.CENTER);
            quit.setVerticalTextPosition(JLabel.CENTER);
            quit.setMargin(new Insets(0,0,0,0));
            quit.setBorder(null);
            quit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
            add(quit);

            //显示当前算法名
            algorithmTitle = new JLabel("请输入作业信息:",JLabel.CENTER);
            algorithmTitle.setBounds(0,5,600,40);
            algorithmTitle.setFont(new Font("华文行楷",0,24));
            algorithmTitle.setForeground(Color.ORANGE);
            add(algorithmTitle);
            algorithmTitle.setVisible(false);

            //执行先来先服务作业调度算法
            commitFCFS = new JButton("FCFS",new ImageIcon(new File("").getAbsolutePath() + "/Images/guangwen.jpg"));
            commitFCFS.setBounds(30,400,90,40);
            commitFCFS.setFont(new Font("华文隶书",0,20));
            commitFCFS.setForeground(Color.ORANGE);
            commitFCFS.setContentAreaFilled(false);
            commitFCFS.setFocusPainted(false);
            commitFCFS.setBorderPainted(false);
            commitFCFS.setHorizontalTextPosition(JLabel.CENTER);
            commitFCFS.setVerticalTextPosition(JLabel.CENTER);
            commitFCFS.setMargin(new Insets(0,0,0,0));
            commitFCFS.setBorder(null);
            commitFCFS.setToolTipText("右击显示算法简介");
            commitFCFS.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(e.getButton() == e.BUTTON1) {
                        commitFCFS.doClick();
                    }
                    if(e.getButton() == e.BUTTON3) {
                        new Introduction("FCFS");
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
            commitFCFS.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //FCFS
                    windows.setTitle("FCFS");
                    algorithmTitle.setText("先来先服务作业调度算法");

                    //获取表数据存到jobs
                    ArrayList<JCB> jobs = getDatas();

                    //调用FCFS算法调度作业，返回计算结果
                    FCFS fcfs = new FCFS(jobs);
                    jobs = fcfs.FCFSAlgorithm();

                    //算法结果表
                    Object[][] Datas = setDatas(jobs);
                    Object[] columnsName = new Object[jobs.size() + 1];

                    //表头，用进程名作为表头数据
                    columnsName[0] = "进程名";
                    for(int i = 1;i <= jobs.size();i++) {
                        columnsName[i] = jobs.get(i - 1).getJobName();
                    }

                    //调度算法结果表
                    outputDatas = new JTable(Datas,columnsName);
                    outputDatas.setBounds(20,50,560,330);
                    outputDatas.setFont(new Font("微软雅黑",0,20));
                    outputDatas.getTableHeader().setFont(new Font("微软雅黑",1,24));
                    outputDatas.getTableHeader().setBackground(Color.CYAN);
                    outputDatas.setRowHeight(34);
                    outputDatas.setBackground(Color.YELLOW);
                    //设置第一列数据居中
                    DefaultTableCellRenderer r = new DefaultTableCellRenderer();
                    r.setHorizontalAlignment(JTextField.CENTER);
                    outputDatas.setDefaultRenderer(Object.class,r);
                    //设置列宽
                    for(int i = 0;i < outputDatas.getColumnCount();i++) {
                        outputDatas.getColumnModel().getColumn(i).setMinWidth(100);
                        outputDatas.getColumnModel().getColumn(i).setMaxWidth(300);
                    }
                    outputDatas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

                    //移除用户输入表，并加入调度算法结果表
                    remove(scrollPane);
                    scrollPane = new JScrollPane(outputDatas);
                    scrollPane.setBounds(20,50,560,330);
                    scrollPane.setFont(new Font("微软雅黑",0,20));
                    scrollPane.getViewport().setBackground(Color.GRAY);
                    add(scrollPane);
                }
            });
            add(commitFCFS);
            commitFCFS.setVisible(false);

            //执行短作业优先作业调度算法
            commitSJF = new JButton("SJF",new ImageIcon(new File("").getAbsolutePath() + "/Images/guangwen.jpg"));
            commitSJF.setBounds(140,400,90,40);
            commitSJF.setFont(new Font("华文隶书",0,20));
            commitSJF.setForeground(Color.ORANGE);
            commitSJF.setContentAreaFilled(false);
            commitSJF.setFocusPainted(false);
            commitSJF.setBorderPainted(false);
            commitSJF.setHorizontalTextPosition(JLabel.CENTER);
            commitSJF.setVerticalTextPosition(JLabel.CENTER);
            commitSJF.setMargin(new Insets(0,0,0,0));
            commitSJF.setBorder(null);
            commitSJF.setToolTipText("右击显示算法简介");
            commitSJF.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(e.getButton() == e.BUTTON1) {
                        commitSJF.doClick();
                    }
                    if(e.getButton() == e.BUTTON3) {
                        new Introduction("SJF");
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
            commitSJF.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //SJF
                    windows.setTitle("SJF");
                    algorithmTitle.setText("短作业优先作业调度算法");

                    //获取表数据存到jobs
                    ArrayList<JCB> jobs = getDatas();

                    //调用SJF算法调度作业，返回计算结果
                    SJF sjf = new SJF(jobs);
                    jobs = sjf.SJFAlgorithm();

                    //算法结果表
                    Object[][] Datas = setDatas(jobs);
                    Object[] columnsName = new Object[jobs.size() + 1];

                    //表头，用进程名作为表头数据
                    columnsName[0] = "进程名";
                    for(int i = 1;i <= jobs.size();i++) {
                        columnsName[i] = jobs.get(i - 1).getJobName();
                    }

                    //调度算法结果表
                    outputDatas = new JTable(Datas,columnsName);
                    outputDatas.setBounds(20,50,560,330);
                    outputDatas.setFont(new Font("微软雅黑",0,20));
                    outputDatas.getTableHeader().setFont(new Font("微软雅黑",1,24));
                    outputDatas.getTableHeader().setBackground(Color.CYAN);
                    outputDatas.setRowHeight(34);
                    outputDatas.setBackground(Color.YELLOW);
                    //设置第一列数据居中
                    DefaultTableCellRenderer r = new DefaultTableCellRenderer();
                    r.setHorizontalAlignment(JTextField.CENTER);
                    outputDatas.setDefaultRenderer(Object.class,r);
                    //设置列宽
                    for(int i = 0;i < outputDatas.getColumnCount();i++) {
                        outputDatas.getColumnModel().getColumn(i).setMinWidth(100);
                        outputDatas.getColumnModel().getColumn(i).setMaxWidth(300);
                    }
                    outputDatas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

                    //移除用户输入表，并加入调度算法结果表
                    remove(scrollPane);
                    scrollPane = new JScrollPane(outputDatas);
                    scrollPane.setBounds(20,50,560,330);
                    scrollPane.setFont(new Font("微软雅黑",0,20));
                    scrollPane.getViewport().setBackground(Color.GRAY);
                    add(scrollPane);
                }
            });
            add(commitSJF);
            commitSJF.setVisible(false);

            //执行优先级作业调度算法
            commitPSA = new JButton("PSA",new ImageIcon(new File("").getAbsolutePath() + "/Images/guangwen.jpg"));
            commitPSA.setBounds(250,400,90,40);
            commitPSA.setFont(new Font("华文隶书",0,20));
            commitPSA.setForeground(Color.ORANGE);
            commitPSA.setContentAreaFilled(false);
            commitPSA.setFocusPainted(false);
            commitPSA.setBorderPainted(false);
            commitPSA.setHorizontalTextPosition(JLabel.CENTER);
            commitPSA.setVerticalTextPosition(JLabel.CENTER);
            commitPSA.setMargin(new Insets(0,0,0,0));
            commitPSA.setBorder(null);
            commitPSA.setToolTipText("右击显示算法简介");
            commitPSA.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(e.getButton() == e.BUTTON1) {
                        commitPSA.doClick();
                    }
                    if(e.getButton() == e.BUTTON3) {
                        new Introduction("PSA");
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
            commitPSA.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //PSA
                    windows.setTitle("PSA");
                    algorithmTitle.setText("优先级作业调度算法");

                    //获取表数据存到jobs
                    ArrayList<JCB> jobs = getDatas();

                    //调用PSA算法调度作业，返回计算结果
                    PSA psa = new PSA(jobs);
                    jobs = psa.PSAAlgorithm();

                    //算法结果表
                    Object[][] Datas = setDatas(jobs);
                    Object[] columnsName = new Object[jobs.size() + 1];

                    //表头，用进程名作为表头数据
                    columnsName[0] = "进程名";
                    for(int i = 1;i <= jobs.size();i++) {
                        columnsName[i] = jobs.get(i - 1).getJobName();
                    }

                    //调度算法结果表
                    outputDatas = new JTable(Datas,columnsName);
                    outputDatas.setBounds(20,50,560,330);
                    outputDatas.setFont(new Font("微软雅黑",0,20));
                    outputDatas.getTableHeader().setFont(new Font("微软雅黑",1,24));
                    outputDatas.getTableHeader().setBackground(Color.CYAN);
                    outputDatas.setRowHeight(34);
                    outputDatas.setBackground(Color.YELLOW);
                    //设置第一列数据居中
                    DefaultTableCellRenderer r = new DefaultTableCellRenderer();
                    r.setHorizontalAlignment(JTextField.CENTER);
                    outputDatas.setDefaultRenderer(Object.class,r);
                    //设置列宽
                    for(int i = 0;i < outputDatas.getColumnCount();i++) {
                        outputDatas.getColumnModel().getColumn(i).setMinWidth(100);
                        outputDatas.getColumnModel().getColumn(i).setMaxWidth(300);
                    }
                    outputDatas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

                    //移除用户输入表，并加入调度算法结果表
                    remove(scrollPane);
                    scrollPane = new JScrollPane(outputDatas);
                    scrollPane.setBounds(20,50,560,330);
                    scrollPane.setFont(new Font("微软雅黑",0,20));
                    scrollPane.getViewport().setBackground(Color.GRAY);
                    add(scrollPane);
                }
            });
            add(commitPSA);
            commitPSA.setVisible(false);

            //执行高响应比作业调度算法
            commitHRRN = new JButton("HRRN",new ImageIcon(new File("").getAbsolutePath() + "/Images/guangwen.jpg"));
            commitHRRN.setBounds(360,400,100,40);
            commitHRRN.setFont(new Font("华文隶书",0,20));
            commitHRRN.setForeground(Color.ORANGE);
            commitHRRN.setContentAreaFilled(false);
            commitHRRN.setFocusPainted(false);
            commitHRRN.setBorderPainted(false);
            commitHRRN.setHorizontalTextPosition(JLabel.CENTER);
            commitHRRN.setVerticalTextPosition(JLabel.CENTER);
            commitHRRN.setMargin(new Insets(0,0,0,0));
            commitHRRN.setBorder(null);
            commitHRRN.setToolTipText("右击显示算法简介");
            commitHRRN.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(e.getButton() == e.BUTTON1) {
                        commitHRRN.doClick();
                    }
                    if(e.getButton() == e.BUTTON3) {
                        new Introduction("HRRN");
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
            commitHRRN.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //HRRN
                    windows.setTitle("HRRN");
                    algorithmTitle.setText("高响应比作业调度算法");

                    //获取表数据存到jobs
                    ArrayList<JCB> jobs = getDatas();

                    //调用HRRN算法调度作业，返回计算结果
                    HRRN hrrn = new HRRN(jobs);
                    jobs = hrrn.HRRNAlgorithm();

                    //算法结果表
                    Object[][] Datas = setDatas(jobs);
                    Object[] columnsName = new Object[jobs.size() + 1];

                    //表头，用进程名作为表头数据
                    columnsName[0] = "进程名";
                    for(int i = 1;i <= jobs.size();i++) {
                        columnsName[i] = jobs.get(i - 1).getJobName();
                    }

                    //调度算法结果表
                    outputDatas = new JTable(Datas,columnsName);
                    outputDatas.setBounds(20,50,560,330);
                    outputDatas.setFont(new Font("微软雅黑",0,20));
                    outputDatas.getTableHeader().setFont(new Font("微软雅黑",1,24));
                    outputDatas.getTableHeader().setBackground(Color.CYAN);
                    outputDatas.setRowHeight(34);
                    outputDatas.setBackground(Color.YELLOW);
                    //设置第一列数据居中
                    DefaultTableCellRenderer r = new DefaultTableCellRenderer();
                    r.setHorizontalAlignment(JTextField.CENTER);
                    outputDatas.setDefaultRenderer(Object.class,r);
                    //设置列宽
                    for(int i = 0;i < outputDatas.getColumnCount();i++) {
                        outputDatas.getColumnModel().getColumn(i).setMinWidth(100);
                        outputDatas.getColumnModel().getColumn(i).setMaxWidth(300);
                    }
                    outputDatas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

                    //移除用户输入表，并加入调度算法结果表
                    remove(scrollPane);
                    scrollPane = new JScrollPane(outputDatas);
                    scrollPane.setBounds(20,50,560,330);
                    scrollPane.setFont(new Font("微软雅黑",0,20));
                    scrollPane.getViewport().setBackground(Color.GRAY);
                    add(scrollPane);
                }
            });
            add(commitHRRN);
            commitHRRN.setVisible(false);

            //返回上一层菜单
            back = new JButton("返回",new ImageIcon(new File("").getAbsolutePath() + "/Images/guangwen.jpg"));
            back.setBounds(480,400,90,40);
            back.setFont(new Font("华文行楷",0,24));
            back.setForeground(Color.ORANGE);
            back.setContentAreaFilled(false);
            back.setFocusPainted(false);
            back.setBorderPainted(false);
            back.setHorizontalTextPosition(JLabel.CENTER);
            back.setVerticalTextPosition(JLabel.CENTER);
            back.setMargin(new Insets(0,0,0,0));
            back.setBorder(null);
            back.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //让当前容器内显示的控件隐藏
                    algorithmTitle.setVisible(false);
                    scrollPane.setVisible(false);
                    commitFCFS.setVisible(false);
                    commitSJF.setVisible(false);
                    commitPSA.setVisible(false);
                    commitHRRN.setVisible(false);
                    back.setVisible(false);

                    //显示上一层菜单的控件
                    welcome.setVisible(true);
                    Tip.setVisible(true);
                    works_queue.setVisible(true);
                    works_queue.setText("");
                    works_queue.requestFocus();
                    commit.setVisible(true);
                    quit.setVisible(true);

                    //设置窗体标题
                    windows.setTitle("作业调度算法");
                }
            });
            add(back);
            back.setVisible(false);

            setVisible(true);
        }

        @Override
        protected void paintComponent(Graphics g) { //主体背景
            ImageIcon icon = new ImageIcon(new File("").getAbsolutePath() + "/Images/backgroud.jpg");
            Image image = icon.getImage();
            g.drawImage(image,0,0,this.getWidth(),this.getHeight(),this);
        }
    }
}
