package data_structure;

import java.awt.*;
import java.util.Vector;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.io.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class GUI extends JFrame implements ActionListener{
    private JTable jtb;
    private DefaultComboBoxModel<String> combo;
    private JTextField jtf;
    private DefaultTableModel table;
    private ArrayList<Management> stu;
    private JFileChooser jfc;
    private Comparator<Management> cmp;

    public GUI(){
        super("考试报名管理系统");
        this.setBounds(350, 150, 1200, 700);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        this.jfc = new JFileChooser();
        this.jfc.setFileFilter(new Filter("文本文件(*.txt)","txt"));

        //顶部菜单栏
        JMenuBar jmb = new JMenuBar();
        JMenu jm = new JMenu("文件");
        JMenuItem jm1 = new JMenuItem("保存");
        jm1.addActionListener(this);
        JMenuItem jm2 = new JMenuItem("打开");
        jm2.addActionListener(this);
        jm.add(jm1);
        jm.add(jm2);
        jmb.add(jm);
        jmb.setBounds(0,0,1200,30);
        this.add(jmb);

        //按钮面板
        JPanel jp = new JPanel();
        //下拉列表
        combo = new DefaultComboBoxModel<String>();
        JComboBox<String> jcb = new JComboBox<String>(combo);
        combo.addElement("模糊查询");
        combo.addElement("精确查询");
        jcb.setFont(new Font("宋体",Font.PLAIN,20));
        jp.add(jcb);
        //标签
        JLabel jl = new JLabel("输入学生姓名");
        jl.setFont(new Font("宋体",Font.PLAIN,20));
        jp.add(jl);
        //输入框
        jtf = new JTextField("",20);
        jtf.setFont(new Font("宋体",Font.PLAIN,20));
        jp.add(jtf);
        //按钮
        String[] buttonNames = {"查询", "编辑", "添加", "删除"};
        for(int i = 0; i < buttonNames.length; i++) {
            JButton jb = new JButton(buttonNames[i]);
            jb.setFont(new Font("宋体",Font.PLAIN,20));
            jb.addActionListener(this);
            jp.add(jb);
        }
        jp.setBounds(0,30,1200,50);
        this.add(jp);

        //显示信息的表格
        Vector<String> column = new Vector<String>();
        String[] columnNames = {"准考证号", "姓名", "性别", "年龄", "成绩"};
        for(int i = 0; i < columnNames.length; i++)
            column.add(columnNames[i]);

        table = new DefaultTableModel(column, 0);
        jtb = new JTable(table);
        JScrollPane jsp = new JScrollPane(jtb);
        jtb.setRowHeight(30);//设置表格行宽
        jtb.setFont(new Font("宋体",Font.PLAIN,25));//设置表格字体
        JTableHeader head = jtb.getTableHeader();
        head.setPreferredSize(new Dimension(head.getWidth(),40));//设置表头大小
        head.setFont(new Font("微软雅黑",Font.PLAIN,25));//设置表头字体

        jtb.setBounds(10,120,1160,500);
        jsp.setBounds(10,100,1160,500);
        this.add(jsp);

        //排序
        cmp = Comparator.comparingInt(m -> Integer.valueOf(m.examno));

        stu = new ArrayList<Management>();
        this.setVisible(true);//设为可见

    }

    //把stu中的信息显示到表格里
    public static void showStu(DefaultTableModel table, ArrayList<Management> stu) {
        for(Management m:stu) {
            String[] info = new String[]{m.examno, m.name, m.sex, String.valueOf(m.age), String.valueOf(m.grade)};
            table.addRow(info);
        }
    }

    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case "查询":
                switch(combo.getSelectedItem().toString()) {
                    case "模糊查询":
                        table.setRowCount(0);
                        String inputF = jtf.getText().trim();
                        for(Management m: stu) {
                            if(m.name.contains(inputF)) {
                                String[]info = new String[]{m.examno, m.name, m.sex, String.valueOf(m.age), String.valueOf(m.grade)};
                                table.addRow(info);
                            }
                        }
                        break;
                    case "精确查询":
                        table.setRowCount(0);
                        String inputA = jtf.getText().trim();
                        for(Management m: stu) {
                            if(m.name.equals(inputA)) {
                                String[]info = new String[]{m.examno, m.name, m.sex, String.valueOf(m.age),
                                        String.valueOf(m.grade)};
                                table.addRow(info);
                            }
                        }
                        break;
                }
                break;

            case "编辑":
                new EditFrame(table, stu, jtb);
                break;

            case "添加":
                new AddFrame(table, stu);
                break;

            case "删除":
                int row = jtb.getSelectedRow();//获取要删除的行号
                stu.remove(row);//删除所选行
//                refresh(table, stu);
                table.setRowCount(0);
                Collections.sort(stu, cmp);
                showStu(table,stu);//重新打开
                break;

            case "保存":
                if(jfc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                    Filter filter = (Filter) jfc.getFileFilter();
                    String ends = filter.getExt();
                    File newFile= new File(jfc.getSelectedFile().getAbsolutePath() + '.' + ends);
                    if (!newFile.exists()) {
                        try {
                            newFile.createNewFile();
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    }
                    IO.writeTo(newFile, stu);
                }
                break;

            case "打开":
                if(jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                    File file = jfc.getSelectedFile();
                    stu.clear();
                    IO.readFrom(file, stu);
                    table.setRowCount(0);
                    showStu(table, stu);
                }
                break;
        }
    }

    public static void main(String[] args) {
        new GUI();
    }
}
