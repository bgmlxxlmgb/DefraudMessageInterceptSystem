package pers.bgm.dmis.dt01;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/29.
 */
public class T08 {
    public static void main(String a[]) {
        ArrayList<Attribute> atts = new ArrayList<Attribute>(2);
        ArrayList<String> classVal = new ArrayList<String>();
        classVal.add("?");
        atts.add(new Attribute("content", (ArrayList<String>) null));
        atts.add(new Attribute("@@class@@", classVal));
        Instances dataRaw = new Instances("single_message", atts, 1);
        dataRaw.setClassIndex(dataRaw.numAttributes() - 1);
        System.out.println(dataRaw);

        Instance inst = new DenseInstance(2);
        ArrayList<String> classVal_01 = new ArrayList<String>();
        classVal_01.add("?");

        inst.setDataset(dataRaw);
        inst.setValue(new Attribute("content"), "message");
        inst.setValue(new Attribute("@@class@@"), "a");

        System.out.println("The instance: " + inst);
        dataRaw.add(inst);
        System.out.println(dataRaw);
    }
}
