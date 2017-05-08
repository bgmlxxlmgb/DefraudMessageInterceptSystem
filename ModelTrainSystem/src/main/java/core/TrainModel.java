package core;

import pers.bgm.dmis.utils.GainConfigValue;
import pers.bgm.dmis.utils.SplitMessagesIntoFiles;
import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.GreedyStepwise;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.lazy.IBk;
import weka.classifiers.meta.AttributeSelectedClassifier;
import weka.core.Debug;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ArffLoader;
import weka.core.stemmers.NullStemmer;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * Created by Administrator on 2017/4/29.
 */
public class TrainModel {

    public static void initialFile() {
        SplitMessagesIntoFiles splitMessagesIntoFiles = new SplitMessagesIntoFiles();
        SplitMessagesIntoFiles.initial();
        splitMessagesIntoFiles.segment();
    }

    public static Instances makeDataSet() throws Exception {
        //initialFile();
        TextDirectoryLoader loader = new TextDirectoryLoader();
        loader.setDirectory(new File(GainConfigValue.getValue("target_message_dir")));
        Instances dataRaw = loader.getDataSet();
        FileWriter fw = new FileWriter(GainConfigValue.getValue("data_file_path"));
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(dataRaw.toString());
        bw.close();
        fw.close();
        return dataRaw;
    }

    public static Instances makeFilteredDataSet() throws Exception {
        Instances dataRaw = makeDataSet();
        StringToWordVector filter = new StringToWordVector();
        filter.setStemmer(new NullStemmer());
        filter.setInputFormat(dataRaw);
        filter.setMinTermFreq(10);
        filter.setWordsToKeep(300);

        Instances dataFiltered = Filter.useFilter(dataRaw, filter);
        FileWriter fw = new FileWriter(GainConfigValue.getValue("data_filtered_file_path"));
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(dataFiltered.toString());
        bw.close();
        fw.close();
        dataFiltered.setClassIndex(0);
        return dataFiltered;
    }

    //根据配置好的目录将文件进行拆分、分词、构造数据集合、向量化并训练
    public static void train() throws Exception {
        IBk ibk = new IBk();
        Instances dataFiltered = makeFilteredDataSet();
        /*ArffLoader loader = new ArffLoader();
        loader.setFile(new File(GainConfigValue.getValue("data_filtered_file_path")));
        Instances dataFiltered = loader.getDataSet();*/
        ibk.setKNN(3);
        ibk.buildClassifier(dataFiltered);
        SerializationHelper.write(GainConfigValue.getValue("trained_model_path"), ibk);
    }

    //评估训练模型
    public static void evaluateTrainedModel() throws Exception {
        ArffLoader loader = new ArffLoader();
        loader.setFile(new File(GainConfigValue.getValue("data_filtered_file_path")));
        Instances dataFiltered = loader.getDataSet();
        dataFiltered.setClassIndex(dataFiltered.numAttributes() - 1);
        Classifier classifier = (Classifier) weka.core.SerializationHelper.read(GainConfigValue.getValue("trained_model_path"));
        Evaluation eval = new Evaluation(dataFiltered);
        eval.crossValidateModel(classifier, dataFiltered, 10, new Debug.Random(1));
        System.out.println(eval.toClassDetailsString());
        System.out.println(eval.toSummaryString());
        System.out.println(eval.toMatrixString());
        System.out.println(eval.errorRate());
    }

    //使用过滤器来进行特征选择
    public static Instances selectAttrUseFilter(Instances m_Instances) throws Exception {
        AttributeSelection filter = new AttributeSelection();
        filter.setEvaluator(new CfsSubsetEval());
        filter.setSearch(new GreedyStepwise());
        filter.setInputFormat(m_Instances);
        return Filter.useFilter(m_Instances, filter);
    }

    //
    public static void selectAttrUseMC(Instances m_Instances, Classifier base) throws Exception {
        AttributeSelectedClassifier classifier = new AttributeSelectedClassifier();
        classifier.setClassifier(base);
        classifier.setEvaluator(new CfsSubsetEval());
        classifier.setSearch(new GreedyStepwise());
        Evaluation evaluation = new Evaluation(m_Instances);
        evaluation.crossValidateModel(classifier, m_Instances, 10, new Debug.Random(1));
        System.out.println(evaluation.toSummaryString());
    }

}
