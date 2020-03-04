package data_structure;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Comparator;

public class AddFrame extends JFrame implements ActionListener{
    private JTextField[] jtf;
    private ArrayList<Management> stu;
    private DefaultTableModel table;
    private int notNull = 1;
    private Comparator<Management> cmp;

    AddFrame(DefaultTableModel table, ArrayList<Management> stu) {
        super("添加学生信息");
        this.stu = stu;
        this.table = table;
        this.setBounds(700, 260, 500, 480);
        this.setLayout(new GridLayout(6,1));

        cmp = Comparator.comparingInt(s -> Integer.parseInt(s.examno));

        JPanel[] jp = new JPanel[6];
        for(int i = 0; i < jp.length; i++) {
            jp[i] = new JPanel();
        }

        jtf = new JTextField[5];
        for(int i = 0; i < jtf.length; i++) {
            JTextField jtfAdd = new JTextField("", 10);
            jtfAdd.setFont(new Font("宋体",Font.PLAIN,20));
            jtf[i] = jtfAdd;
            String[] columnNames = {"准考证号", "姓名", "性别", "年龄", "成绩"};
            JLabel jl = new JLabel(columnNames[i]);
            jl.setFont(new Font("宋体",Font.PLAIN,20));
            jp[i].add(jl);
            jp[i].add(jtf[i]);
        }

        String[] buttonNames = {"确认", "取消"};
        for (String buttonName : buttonNames) {
            JButton button = new JButton(buttonName);
            button.setFont(new Font("宋体", Font.PLAIN, 20));
            button.addActionListener(this);
            jp[5].add(button);
        }

        for (JPanel jPanel : jp) this.add(jPanel);

        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case "确认":
                for (JTextField jTextField : jtf)
                    if (jTextField.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "添加学生信息不能为空");
                        notNull = 0;
                        this.setVisible(false);
                        break;
                    }
                if(notNull == 1) {
                    Management newstu = new Management(jtf[0].getText(), jtf[1].getText(), jtf[2].getText(),
                            Integer.parseInt(jtf[3].getText().trim()),Double.parseDouble(jtf[4].getText().trim()));
                    stu.add(newstu);
                    stu.sort(cmp);
                    table.setRowCount(0);
                    GUI.showStu(table, stu);
                    this.setVisible(false);
                }
                break;
            case "取消":
                dispose();
                break;
        }
    }
}
