package pers.bgm.dmis.core;

import pers.bgm.dmis.utils.GainConfigValue;
import pers.bgm.dmis.utils.SplitMessagesIntoFiles;
import weka.classifiers.Classifier;
import weka.core.*;
import weka.core.converters.ArffLoader;
import weka.core.stemmers.NullStemmer;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/29.
 */
public class SingleMessageProcessUtil {

    public static Classifier classifier = null;

    public static Instances trainheader = null;

    static {
        try {
            //加载模板数据集文件
            String trainsetfile = GainConfigValue.getValue("data_filtered_file_path");
            BufferedReader reader = new BufferedReader(new FileReader(trainsetfile));
            ArffLoader.ArffReader arff = new ArffLoader.ArffReader(reader);
            trainheader = arff.getStructure();
            trainheader.setClassIndex(0);

            //加载分类器
            classifier = (Classifier) weka.core.SerializationHelper.read("F:/mess/ibk.model");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Instances transMessageToInstances(String message) {
        ArrayList<String> classes = new ArrayList<String>();
        classes.add("0");
        classes.add("1");
        Attribute att1 = new Attribute("text", (FastVector) null);
        Attribute att2 = new Attribute("@@class@@", classes);
        FastVector attrs = new FastVector();
        attrs.addElement(att1);
        attrs.addElement(att2);
        Instances dataset = new Instances("single_message", attrs, 0);
        Instance example = new DenseInstance(2);
        message = "'" + message + "\r\n'";
        example.setValue(att1, message);
        example.setValue(att2, Utils.missingValue());
        dataset.add(example);
        return dataset;
    }

    //预测短信，有模板短信数据集
    public static String estimateSingleMessage(Instances messages, String message, String dataFilteredPath) throws Exception {

        if (classifier == null) {
            throw new Exception("模型未初始化！");
        }

        //初始化向量化条件的头并设置输入格式
        StringToWordVector filter = new StringToWordVector();
        Instances sampleRaw = transMessageToInstances(SplitMessagesIntoFiles.getWordsString(message));
        filter.setStemmer(new NullStemmer());
        filter.setInputFormat(sampleRaw);
        Instances testFiltered = Filter.useFilter(sampleRaw, filter);
        Instances header = trainheader;
        Instances data = testFiltered;

        String trainsetfile = dataFilteredPath;
        BufferedReader reader = new BufferedReader(new FileReader(trainsetfile));
        ArffLoader.ArffReader arff = new ArffLoader.ArffReader(reader);
        Instances trainheader = arff.getStructure();
        trainheader.setClassIndex(0);

        //与现有数据集进行比较，文本向量化
        long sum = 0;
        String category = "";
        long start = System.currentTimeMillis();
        Instance curr = data.instance(0);
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
                    throw new IllegalStateException("未知类型的属性!");
                }
            }
        }

        //预测
        double prediction = classifier.classifyInstance(inst);
        category = trainheader.classAttribute().value((int) prediction);
        long end = System.currentTimeMillis();
        //System.out.println("短信: '"+message+"' 属于: " + category + ";判断耗时: " + (end - start) + " ms");
        sum = end - start;
        System.out.println("预测耗时：" + sum + " ms");
        if (testFiltered.numInstances() == 0)
            System.err.println("数据集中无实例！");
        return category;
    }

    //预测短信，无模板短信数据集
    public static String estimateSingleMessage(Instances messages, String message) throws Exception {

        if (classifier == null) {
            throw new Exception("模型未初始化！");
        }

        //初始化向量化条件的头并设置输入格式
        StringToWordVector filter = new StringToWordVector();
        Instances sampleRaw = transMessageToInstances(SplitMessagesIntoFiles.getWordsString(message));
        filter.setStemmer(new NullStemmer());
        filter.setInputFormat(sampleRaw);
        Instances testFiltered = Filter.useFilter(sampleRaw, filter);
        Instances header = trainheader;
        Instances data = testFiltered;

        //与现有数据集进行比较，文本向量化
        long sum = 0;
        String category = "";
        long start = System.currentTimeMillis();
        Instance curr = data.instance(0);
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
                    throw new IllegalStateException("未知类型的属性!");
                }
            }
        }

        //预测
        double prediction = classifier.classifyInstance(inst);
        category = trainheader.classAttribute().value((int) prediction);
        long end = System.currentTimeMillis();
        //System.out.println("短信: '"+message+"' 属于: " + category + ";判断耗时: " + (end - start) + " ms");
        sum = end - start;
        System.out.println("预测耗时：" + sum + " ms");
        if (testFiltered.numInstances() == 0)
            System.err.println("数据集中无实例！");
        return category;
    }
}
