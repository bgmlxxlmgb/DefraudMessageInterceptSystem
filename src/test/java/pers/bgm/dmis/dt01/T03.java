package pers.bgm.dmis.dt01;

import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.io.File;

/**
 * Created by Administrator on 2017/4/24.
 */
public class T03 {
    public static void main(String a[]) throws Exception {
        ArffLoader arffLoader = new ArffLoader();
        arffLoader.setFile(new File(""));
        Instances instances = arffLoader.getDataSet();
        instances.setClassIndex(instances.numAttributes() - 1);
        String value = "却 发现 还在 百度 云 ";
        Instance instance = makeInstance(value, instances);
        StringToWordVector filter = new StringToWordVector();
        Classifier classifier = new IBk();
        System.out.println(instance);

    }

    public static Instance makeInstance(String text, Instances data) throws Exception {
        Instance instance = new DenseInstance(2);
        Attribute messageAtt = data.attribute("text");
        instance.setValue(messageAtt, messageAtt.addStringValue(text));
        instance.setDataset(data);
        return instance;
    }
}
