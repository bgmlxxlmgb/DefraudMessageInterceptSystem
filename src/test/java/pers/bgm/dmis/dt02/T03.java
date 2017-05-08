package pers.bgm.dmis.dt02;

import java.io.File;

/**
 * Created by Administrator on 2017/4/30.
 */
public class T03 {
    public static void main(String args[]) {
        File file = new File("F:\\mess\\target01\\0\\0-39119.txt");
        String parentDir = file.getParent();
        file.getParent();
        System.out.println(file.getParentFile());
    }
}
