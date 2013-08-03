package zdbcmls;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.text.DecimalFormat;

public class Zdbcmls {
    //根据比例名，map至比值

    static Map<String, Float> map = new HashMap<String, Float>();

    public static void main(String[] args) {
        String c;

        try {
            FileWriter fileWriter = new FileWriter("output.txt");
            BufferedReader br = new BufferedReader(new FileReader("input.txt"));
            //格式要求，注册邮箱
            fileWriter.write("ly6500@163.com\r\n\r\n");
            //读入数据比例信息
            while (!"".equals(c = br.readLine())) {
                read(c);
            }
            //计算结果，输出至文件
            while ((c = br.readLine()) != null) {
                System.out.println(c);
                String a = new DecimalFormat("####.##").format(cal(c));
                fileWriter.write(a + " m\r\n");
            }

            br.close();
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("!!!!!!!!!!!!");
        }
    }

    //读入比例
    public static void read(String s) {
        String[] sArray = s.split(" ");
        map.put(sArray[1], Float.valueOf(sArray[3]));
    }
    //计算结果，返回结果

    public static float cal(String s) {
        String[] sArray;
        if (s.substring(0, 1).equals(" ")) {
            sArray = s.substring(1, s.length()).split(" ");
        } else {
            sArray = s.split(" ");
        }
        
        float result;
        if (sArray[0].indexOf(0) == '-') {
            result = 0 - zhuanhuandanwei(sArray[0].substring(1, sArray[0].length() - 1), sArray[1]);
        } else if (sArray[0].indexOf(0) == '+') {
            result = zhuanhuandanwei(sArray[0].substring(1, sArray[0].length() - 1), sArray[1]);
        } else if (sArray[0].indexOf(0) == ' ') {
            result = zhuanhuandanwei(sArray[0].substring(1, sArray[0].length() - 1), sArray[1]);
        } else {
            result = zhuanhuandanwei(sArray[0], sArray[1]);
        }

        for (int i = 2; i < sArray.length; i += 3) {
            if (sArray[i].endsWith("+")) {
                result += zhuanhuandanwei(sArray[i + 1], sArray[i + 2]);
            } else {
                result -= zhuanhuandanwei(sArray[i + 1], sArray[i + 2]);
            }
        }
        return result;
    }

    //根据读入比例名，获取map中的比值
    private static float zhuanhuandanwei(String number, String type) {
        float bili;
        //英语语境中，miles的特殊处理
        if (type.endsWith("miles")) {
            bili = map.get(type.substring(0, type.length() - 1));
        } //英语语境中，feet的特殊处理 
        else if (type.endsWith("feet")) {
            bili = map.get("foot");
        } else if (type.substring(type.length() - 2, type.length()).endsWith("es")) {
            bili = map.get(type.substring(0, type.length() - 2));
        } else if (type.substring(type.length() - 1, type.length()).endsWith("s")) {
            bili = map.get(type.substring(0, type.length() - 1));
        } else {
            bili = map.get(type);
        }
        return Float.valueOf(number) * bili;
    }
}
