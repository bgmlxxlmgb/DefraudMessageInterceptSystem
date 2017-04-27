package pers.bgm.dmis.dt01;

import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.core.converters.ArffLoader.ArffReader;
import weka.core.SerializationHelper;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by Administrator on 2017/4/23.
 */
public class T01 {
    public static void main(String a[]) throws Exception {
        Classifier classifier = (Classifier) SerializationHelper.read("F:/mess/ibk.model");
        //prepare the sample
        String samplefile = "F://mess/test/dataFiltered.arff";
        BufferedReader reader = new BufferedReader(new FileReader(samplefile));
        ArffReader arff = new ArffReader(reader);
        Instances sampledata = arff.getData();
        sampledata.setClassIndex(0);
        //classify
        for (int i = 0; i < 5; i++) {
            double p = classifier.classifyInstance(sampledata.instance(0));
            String category = sampledata.classAttribute().value((int) p);
            System.out.println("the sample is belong to: " + category);
            System.out.println(classifier.classifyInstance(sampledata.instance(i)));
        }
        //double prediction = classifier.classifyInstance(sampledata.instance(0));
    }
}
