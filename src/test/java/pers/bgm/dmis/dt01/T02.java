package pers.bgm.dmis.dt01;

import weka.classifiers.Classifier;
import weka.core.*;
import weka.core.converters.ArffLoader;
import weka.core.stemmers.NullStemmer;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.io.*;

/**
 * Created by Administrator on 2017/4/23.
 */
public class T02 {
    public static void main(String a[]) throws Exception {
        String trainsetfile = "F:/mess/dataFiltered.arff";
        BufferedReader reader = new BufferedReader(new FileReader(trainsetfile));
        ArffLoader.ArffReader arff = new ArffLoader.ArffReader(reader);
        Instances trainheader = arff.getStructure();
        trainheader.setClassIndex(0);
        Classifier classifier = (Classifier) weka.core.SerializationHelper.read("F:/mess/ibk.model");
        TextDirectoryLoader loader = new TextDirectoryLoader();
        loader.setDirectory(new File("F:\\mess\\test\\targetdata"));
        Instances sampleRaw = loader.getDataSet();
        System.out.println(sampleRaw);
       /*StringToWordVector filter = new StringToWordVector();
       filter.setStemmer(new NullStemmer());
       filter.setInputFormat(sampleRaw);
       Instances testFiltered = Filter.useFilter(sampleRaw, filter);
       Instances header = trainheader;
       Instances data = testFiltered;
       long sum = 0;
       int count = 0;
       for( int i = 0; i<data.numInstances();i++) {
           long start = System.currentTimeMillis();
           Instance curr = data.instance(i);
           Instance inst = new DenseInstance(header.numAttributes());
           inst.setDataset(header);
           for (int n = 0; n < header.numAttributes(); n++) {
               Attribute att = data.attribute(header.attribute(n).name());
               if (att != null) {
                   if (att.isNominal()) {
                       if ((header.attribute(n).numValues() > 0) && (att.numValues() > 0)) {
                           String label = curr.stringValue(att);
                           int index = header.attribute(n).indexOfValue(label);
                           if (index != -1)
                               inst.setValue(n, index);
                       }
                   } else if (att.isNumeric()) {
                       inst.setValue(n, curr.value(att));
                   } else {
                       throw new IllegalStateException("Unhandled attribute type!");
                   }
               }
           }
           double prediction = classifier.classifyInstance(inst);
           String category = trainheader.classAttribute().value((int) prediction);
           long end = System.currentTimeMillis();
           System.out.println("the sample[" + i + "] is belong to: " + category+";cost seconds:"+(end-start)+" ms");
           sum+=end-start;
           count++;
       }
       if(testFiltered.numInstances()==0)
           System.out.println("there is no instance found in the arff file.");

       System.out.println("average cost:"+(sum/count)+" ms");*/
    }
}
