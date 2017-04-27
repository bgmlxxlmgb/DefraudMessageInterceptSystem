package pers.bgm.dmis.dt01;

import weka.classifiers.lazy.IBk;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ArffLoader;

import java.io.File;

/**
 * Created by Administrator on 2017/4/25.
 */
public class T04 {
    public static void main(String a[]) throws Exception {
        ArffLoader arffLoader = new ArffLoader();
        arffLoader.setFile(new File("F:/mess/dataFiltered.arff"));
        Instances instances = arffLoader.getDataSet();
        instances.setClassIndex(0);
        IBk ibk = new IBk();
        ibk.setKNN(3);
        ibk.buildClassifier(instances);
        SerializationHelper.write("F:/mess/ibk.model", ibk);
    }
}
