package pers.bgm.dmis.dt02;

import pers.bgm.dmis.utils.SplitMessagesIntoFiles;

import java.io.*;

/**
 * Created by Administrator on 2017/4/30.
 */
public class T04 {
    public static void main(String args[]) {
        segmentDir("F:/mess/source", "F:/mess/target02");
    }

    public static void segmentDir(String source, String target) {
        File[] file = (new File(source)).listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                segmentFileInOneFile(file[i].getAbsolutePath(), target + File.separator + file[i].getName());
            }
            if (file[i].isDirectory()) {
                String _sourceDir = source + File.separator + file[i].getName();
                String _targetDir = target + File.separator + file[i].getName();
                (new File(_targetDir)).mkdirs();
                segmentDir(_sourceDir, _targetDir);
            }
        }
    }

    //拆分文件，每个文件每行分词，一个文件分词后还是一个文件
    public static void segmentFileInOneFile(String sourceFile, String targetFile) {
        System.out.println("a:" + sourceFile);
        System.out.println("b:" + targetFile);
        try {
            FileReader fr = new FileReader(sourceFile);
            BufferedReader br = new BufferedReader(fr);
            FileWriter fw = new FileWriter(targetFile);
            BufferedWriter bw = new BufferedWriter(fw);
            String value = "";
            File file = new File(sourceFile);
            String parentDir = file.getParent();
            File messageFile;
            while ((value = br.readLine()) != null) {
                bw.append(SplitMessagesIntoFiles.getWordsString(value));
                bw.newLine();
            }
            bw.close();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
