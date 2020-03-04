package data_structure;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import javax.swing.JOptionPane;

class IO {
    static void readFrom(File file, ArrayList<Management> arr) {
        try {
            BufferedReader bfr = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            String line;
            while((line=bfr.readLine())!=null) {
                String[]keys =  line.split(";");
                Management a = new Management(keys[0],keys[1],keys[2],Integer.parseInt(keys[3]),Double.parseDouble(keys[4]));
                arr.add(a);
            }
            bfr.close();
        }

        catch(FileNotFoundException ex)  {
            if(!file.getName().equals(""))
                JOptionPane.showMessageDialog(null, "\""+file.getName()+"\"文件不存在。");
        }
        catch(IOException ex) {
            JOptionPane.showMessageDialog(null, "读取文件时数据错误");
        }
    }

    static void writeTo(File file, ArrayList<Management> arr) {
        try {
            BufferedWriter bufrd = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), "UTF-8"));
            String line = new String("");
            for(Management s:arr) {
                line = s.toString();
                bufrd.write(line + "\r\n");
            }
            bufrd.close();
        }
        catch(FileNotFoundException ex) {
            if (!file.getName().equals(""))
                JOptionPane.showMessageDialog(null, "\"" + file.getName() + "\"文件不存在。");
        }
        catch(IOException ex) {
            JOptionPane.showMessageDialog(null, "写入文件时数据错误");
        }
    }
}
