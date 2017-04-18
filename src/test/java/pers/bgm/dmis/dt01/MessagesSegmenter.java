package pers.bgm.dmis.dt01;

/**
 * Created by Administrator on 2017/4/16.
 */

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.*;

public class MessagesSegmenter {

    private String sourceDir = "";
    private String targetDir = "";

    public MessagesSegmenter() {
    }

    public MessagesSegmenter(String source, String target) {
        sourceDir = source;
        targetDir = target;
    }

    public void segment() {
        segmentDir(sourceDir, targetDir);
    }

    public void segmentDir(String source, String target) {
        File[] file = (new File(source)).listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                segmentFile(file[i].getAbsolutePath(), target +
                        File.separator + file[i].getName());
            }
            if (file[i].isDirectory()) {
                String _sourceDir = source + File.separator +
                        file[i].getName();
                String _targetDir = target + File.separator +
                        file[i].getName();
                (new File(_targetDir)).mkdirs();
                segmentDir(_sourceDir, _targetDir);
            }
        }
    }

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

    public void segmentFile(String sourceFile, String targetFile) {
        try {
            FileReader fr = new FileReader(sourceFile);
            BufferedReader br = new BufferedReader(fr);

            FileWriter fw = new FileWriter(targetFile);
            BufferedWriter bw = new BufferedWriter(fw);
            String value = "";
            while ((value = br.readLine()) != null) {
                bw.append(getWordsString(value));
                bw.newLine();
            }
            bw.close();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        MessagesSegmenter segmenter = new MessagesSegmenter();
        segmenter.segment();
    }
}

