package pers.bgm.dmis.dt01;

import weka.core.*;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/29.
 */
public class T10 {
    public static void main(String args[]) {
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
        example.setValue(att1, "message");
        example.setValue(att2, Utils.missingValue());
        dataset.add(example);
        System.out.println(dataset);

    }
}
