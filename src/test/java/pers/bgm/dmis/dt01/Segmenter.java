package pers.bgm.dmis.dt01;

/**
 * Created by Administrator on 2017/4/24.
 */

import java.io.BufferedReader;

import java.io.BufferedWriter;

import java.io.File;

import java.io.FileReader;

import java.io.FileWriter;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
//import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class Segmenter {
    private String sourceDir;
    private String targetDir;

    public Segmenter(String source, String target) {
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
                String _sourceDir = source + File.separator + file[i].getName();
                String _targetDir = target + File.separator + file[i].getName();
                (new File(_targetDir)).mkdirs();
                segmentDir(_sourceDir, _targetDir);
            }
        }
    }

    public void segmentFile(String sourceFile, String targetFile) {
        try {
            FileReader fr = new FileReader(sourceFile);
            BufferedReader br = new BufferedReader(fr);
            FileWriter fw = new FileWriter(targetFile);
            BufferedWriter bw = new BufferedWriter(fw);
            Analyzer analyzer = new IKAnalyzer();
            TokenStream tokenStream = analyzer.tokenStream("", br);
            //TermAttribute termAtt = (TermAttribute) tokenStream.getAttribute(TermAttribute.class);
            while (tokenStream.incrementToken()) {
                //bw.write( termAtt.term() );
                bw.write(' ');
            }
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        Segmenter segmenter = new Segmenter("train", "train_segmented");
        segmenter.segment();
    }
}
