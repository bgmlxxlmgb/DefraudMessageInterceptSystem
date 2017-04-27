package pers.bgm.dmis.dt01;

/**
 * Created by Administrator on 2017/4/24.
 */

import weka.core.*;
import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.converters.ConverterUtils.*;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.io.*;

public class MessageClassifier implements Serializable {
    private Instances m_Data = null;
    private StringToWordVector m_Filter = new StringToWordVector();
    private Classifier m_Classifier = new J48();
    private boolean m_UpToDate;

    /**
     * 构造空的训练集
     */
    public MessageClassifier() throws Exception {
        String nameOfDataset = "MessageClassificationProblem";
        // 创建属性向量
        FastVector attributes = new FastVector(2);
        // Add attribute for holding messages.
        attributes.addElement(new Attribute("Message", (FastVector) null));
        // 添加类别属性
        FastVector classValues = new FastVector(2);
        classValues.addElement("miss");
        classValues.addElement("hit");
        attributes.addElement(new Attribute("Class", classValues));
        // 创建一个容量为100的数据集并设置类别下标。.
        m_Data = new Instances(nameOfDataset, attributes, 100);
        m_Data.setClassIndex(m_Data.numAttributes() - 1);
    }

    /**
     * 用给定的训练数据更新数据
     */
    public void updateData(String message, String classValue) throws Exception {
        // 将数据加入实例
        Instance instance = makeInstance(message, m_Data);
        // 为实例设置类别值
        instance.setClassValue(classValue);
        // 将实例添加进训练数据集
        m_Data.add(instance);
        m_UpToDate = false;
    }

    /**
     * 将给定的数据进行分类
     */
    public void classifyMessage(String message) throws Exception {
        // 检查分类器是否已经建立
        if (m_Data.numInstances() == 0) {
        }
        // 检查分类器和过滤器是否是更新后的
        if (!m_UpToDate) {
            // 初始化过滤器并设置输入的格式
            m_Filter.setInputFormat(m_Data);
            // 从训练集中产生词频数据
            Instances filteredData = Filter.useFilter(m_Data, m_Filter);
            // 重建分类器
            m_Classifier.buildClassifier(filteredData);
            m_UpToDate = true;
        }
        // Make separate little test set so that message
        // does not get added to string attribute in m_Data.
        Instances testset = m_Data.stringFreeStructure();
        // Make message into test instance.
        Instance instance = makeInstance(message, testset);
        // Filter instance.
        m_Filter.input(instance);
        Instance filteredInstance = m_Filter.output();
        // Get index of predicted class value.
        double predicted = m_Classifier.classifyInstance(filteredInstance);
        // Output class value.
        System.err.println("Message classified as : " +
                m_Data.classAttribute().value((int) predicted));
    }

    /**
     * 转换一个文本信息到一个实例中；Method that converts a text message into an instance.
     */
    private Instance makeInstance(String text, Instances data) throws Exception {
        // 创建一个长度为2的实例
        Instance instance = new DenseInstance(2);
        // 为文本属性设置值
        Attribute messageAtt = data.attribute("Message");
        instance.setValue(messageAtt, messageAtt.addStringValue(text));
        // 从数据集中让实例获取属性信息
        instance.setDataset(data);
        return instance;
    }

    public static void main(String[] options) {
        try {
            String messageName = Utils.getOption('m', options);
            if (messageName.length() == 0) {
                throw new Exception("Must provide name of message file.");
            }
            FileReader m = new FileReader(messageName);
            StringBuffer message = new StringBuffer();
            int l;
            while ((l = m.read()) != -1) {
                message.append((char) l);
            }
            m.close();
            // 检查是否给了类别值
            String classValue = Utils.getOption('c', options);
            // 如果模型文件存在，则读取，否则新建。
            String modelName = Utils.getOption('o', options);
            if (modelName.length() == 0) {
                throw new Exception("Must provide name of model file.");
            }
            MessageClassifier messageCl;
            try {
                ObjectInputStream modelInObjectFile = new ObjectInputStream(new FileInputStream(modelName));
                messageCl = (MessageClassifier) modelInObjectFile.readObject();
                modelInObjectFile.close();
            } catch (FileNotFoundException e) {
                messageCl = new MessageClassifier();
            }
            // Check if there are any options left
            Utils.checkForRemainingOptions(options);
            // 梳理文本
            if (classValue.length() != 0) {
                messageCl.updateData(message.toString(), classValue);
            } else {
                messageCl.classifyMessage(message.toString());
            }
            // 保存文本分类器
            ObjectOutputStream modelOutObjectFile = new ObjectOutputStream(new FileOutputStream(modelName));
            modelOutObjectFile.writeObject(messageCl);
            modelOutObjectFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
