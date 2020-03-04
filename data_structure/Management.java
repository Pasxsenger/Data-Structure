package data_structure;

public class Management {

    String examno;        //准考证号
    String name;        //姓名
    String sex;        //性别
    int age;        //年龄
    double grade;        //成绩

    //无参构造方法
    public Management() {
        super();
    }

    //有参构造方法
    Management(String examno, String name, String sex, int age, double grade) {
        super();
        this.examno = examno;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.grade = grade;
    }

    public String toString(){
        return this.examno + ";" + this.name + ";" + this.sex + ";" + String.valueOf(this.age) + ";"
                + String.valueOf(this.grade) ;
    }

}
