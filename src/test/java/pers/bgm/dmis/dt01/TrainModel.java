package pers.bgm.dmis.dt01;

/**
 * Created by Administrator on 2017/4/16.
 */

import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.lazy.IBk;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils;
import weka.core.stemmers.NullStemmer;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;
import weka.core.converters.ConverterUtils.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class TrainModel {
    public static void main(String a[]) throws Exception {
        String filename = "H://gmtest_01/";
        /*Instances dataFiltered= DataSource.read("dataFiltered.arff");
        DataSink.write("dataWritten.arff", dataFiltered);*/
        // convert the directory into a dataset
        TextDirectoryLoader loader = new TextDirectoryLoader();
        loader.setDirectory(new File(filename));
        Instances dataRaw = loader.getDataSet();
        //System.out.println("\n\nImported data:\n\n" + dataRaw);
        {
            FileWriter fw = new FileWriter("H://dataRaw.arff");
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(dataRaw.toString());

            bw.close();
            fw.close();
        }

        StringToWordVector filter = new StringToWordVector();
        filter.setStemmer(new NullStemmer());
        filter.setInputFormat(dataRaw);
        Instances dataFiltered = Filter.useFilter(dataRaw, filter);
        {
            FileWriter fw = new FileWriter("H://dataFiltered.arff");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(dataFiltered.toString());
            bw.close();
            fw.close();
        }
        //System.out.println("\n\nFiltered data:\n\n" + dataFiltered);
        dataFiltered.setClassIndex(0);
        // train J48 and output model
        /*J48 model = new J48();

        String[] options = {"-M","5","-R"};
        model.setOptions(options);
        model.buildClassifier(dataFiltered);

        double res = model.classifyInstance(dataFiltered.instance(1));
        for(int i=0;i<dataFiltered.numInstances();i++){
            System.out.println(model.classifyInstance(dataFiltered.instance(i)));
        }
        System.out.println("res:"+res);*/
        NaiveBayes classifier = new NaiveBayes();
        IBk ibk = new IBk();
        ibk.setKNN(3);
        ibk.buildClassifier(dataFiltered);
        SerializationHelper.write("H://ibk.model", ibk);
        /*for(int i=0;i<dataFiltered.numInstances();i++){
            System.out.println(ibk.classifyInstance(dataFiltered.instance(i)));
        }

        System.out.println("\n\nClassifier model:\n\n" + ibk);*/





        /*Evaluation eval = new Evaluation(dataFiltered);
        eval.crossValidateModel(ibk, dataFiltered, 10, new Random(1));
        System.out.println(eval.toClassDetailsString());
        System.out.println(eval.toSummaryString());
        System.out.println(eval.toMatrixString());
        System.out.println(eval.errorRate());*/
    }
}
