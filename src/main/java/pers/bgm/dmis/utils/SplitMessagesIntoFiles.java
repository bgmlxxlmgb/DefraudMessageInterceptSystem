package pers.bgm.dmis.utils;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.*;

/**
 * Created by Administrator on 2017/4/20.
 * input:源文件夹
 * output:目标文件夹
 * function:将源文件夹里的所有文件及文件夹原样的在目标文件夹里复制一份。不同的是每个类别的文件夹里，一条短信占一个文件并分好词
 */

public class SplitMessagesIntoFiles {

    private static String SOURCE_DIR = "";
    private static String TARGET_DIR = "";
    static Logger logger;

    public static void initial() {
        logger = LoggerUtil.getLogger();
        try {
            SOURCE_DIR = GainConfigValue.getValue("source_message_dir");
            TARGET_DIR = GainConfigValue.getValue("target_message_dir");
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.debug(e.getMessage());
        }
    }

    //设置路径
    public void segment() {
        segmentDir(SOURCE_DIR, TARGET_DIR);
    }

    //遍历目录，如果是文件，就开始读取，否则在目标路径创建文件夹。
    public void segmentDir(String source, String target) {
        File[] file = (new File(source)).listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                segmentFileInMultiFile(file[i].getAbsolutePath(), target);
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
    public void segmentFileInOneFile(String sourceFile, String targetFile, String target) {
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
                bw.append(getWordsString(value));
                bw.newLine();
            }
            bw.close();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug(e.getMessage());
            logger.error(e.getMessage());
        }
    }

    //每行分词，放入一个文件
    public void segmentFileInMultiFile(String sourceFile, String target) {
        try {
            FileReader fr = new FileReader(sourceFile);
            BufferedReader br = new BufferedReader(fr);
            FileWriter fw;
            BufferedWriter bw;
            String value = "";
            File file = new File(target);
            String parentDir = file.getParent();
            File messageFile;
            while ((value = br.readLine()) != null) {
                String message = getWordsString(value);
                if (message != null) {
                    String type = target.split("\\\\")[target.split("\\\\").length - 1];
                    String messageFileName = target + File.separator + type + "-" + (int) (Math.random() * 100000) + ".txt";
                    messageFile = new File(messageFileName);
                    fw = new FileWriter(messageFile);
                    bw = new BufferedWriter(fw);
                    bw.append(message);
                    bw.flush();
                    bw.close();
                    fw.close();
                } else {
                    continue;
                }

            }
        } catch (Exception e) {
            logger.debug(e.getMessage());
            logger.error(e.getMessage());
        }
    }

    //拆分文本行
    public static String getWordsString(String text) {
        String value = "";
        try {
            Analyzer anal = new IKAnalyzer(true);
            StringReader reader = new StringReader(text);
            //分词
            TokenStream ts = anal.tokenStream("", reader);
            CharTermAttribute term = ts.getAttribute(CharTermAttribute.class);
            while (ts.incrementToken()) {
                value += term.toString() + " ";
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }


}
