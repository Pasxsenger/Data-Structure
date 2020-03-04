package data_structure;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class EditFrame extends JFrame implements ActionListener {
    private JTextField[] jtf;
    private ArrayList<Management> stu;
    private DefaultTableModel table;
    private int notNull = 1;
    private Comparator<Management> cmp;
    private int row;

    EditFrame(DefaultTableModel table, ArrayList<Management> stu, JTable jtb) {
        super("Edit Student");
        this.stu = stu;
        this.table = table;
        this.setBounds(700, 260, 500, 480);
        this.setLayout(new GridLayout(6, 1));

        cmp = Comparator.comparingInt(s -> Integer.parseInt(s.examno));

        JPanel[] jp = new JPanel[6];
        for (int i = 0; i < jp.length; i++) {
            jp[i] = new JPanel();
        }

        jtf = new JTextField[5];
        for (int i = 0; i < jtf.length; i++) {
            jtf[i] = new JTextField("", 10);
            jtf[i].setFont(new Font("宋体",Font.PLAIN,20));
            String[] columnNames = {"准考证号", "姓名", "性别", "年龄", "成绩"};
            JLabel jlb = new JLabel(columnNames[i]);
            jlb.setFont(new Font("宋体",Font.PLAIN,20));
            jp[i].add(jlb);
            jp[i].add(jtf[i]);
        }
        row = jtb.getSelectedRow();
        jtf[0].setText(stu.get(row).examno);
        jtf[1].setText(stu.get(row).name);
        jtf[2].setText(stu.get(row).sex);
        jtf[3].setText(String.valueOf(stu.get(row).age));
        jtf[4].setText(String.valueOf(stu.get(row).grade));


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
        switch (e.getActionCommand()) {
            case "确认":
                stu.remove(row);
                for (JTextField jTextField : jtf)
                    if (jTextField.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "添加学生信息不能为空");
                        notNull = 0;
                        this.setVisible(false);
                        break;
                    }
                if (notNull == 1) {
                    Management newstu = new Management(jtf[0].getText(), jtf[1].getText(), jtf[2].getText(),
                            Integer.parseInt(jtf[3].getText().trim()), Double.parseDouble(jtf[4].getText().trim()));
                    stu.add(newstu);
                    stu.sort(cmp);
                    table.setRowCount(0);
                    GUI.showStu(table, stu);
                    this.setVisible(false);
                }
                break;

            case "取消":
                this.setVisible(false);
                break;
        }
    }
}
