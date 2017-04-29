package pers.bgm.dmis.dt01;

import weka.core.*;

/**
 * Created by Administrator on 2017/4/29.
 */
public class T09 {
    public static void main(String a[]) throws Exception {
        //1.ATTRIBUTES
        //numeric
        Attribute attr = new Attribute("my-numeric");
        //nominal
        FastVector myNomVals = new FastVector();
        for (int i = 0; i < 10; i++)
            myNomVals.addElement("value_" + i);
        Attribute attr1 = new Attribute("my-nominal", myNomVals);
        //string
        Attribute attr2 = new Attribute("my-string", (FastVector) null);
        //date
        Attribute attr3 = new Attribute("my-date", "dd-MM-yyyy");
        //whole relation can also be an attr
        //Attribute attr4 = new Attribute("my-relation", new Instances(...));

        //2.create dataset
        FastVector attrs = new FastVector();
        attrs.addElement(attr);
        attrs.addElement(attr1);
        attrs.addElement(attr2);
        attrs.addElement(attr3);
        Instances dataset = new Instances("my_dataset", attrs, 0);

        //3.add instances
        //first instance
        double[] attValues = new double[dataset.numAttributes()];
        attValues[0] = 55;
        attValues[1] = dataset.attribute("my-nominal").indexOfValue("value_5");
        attValues[2] = dataset.attribute("my-string").addStringValue("Slavko");
        attValues[3] = dataset.attribute("my-date").parseDate("7-6-1987");
        dataset.add(new DenseInstance(1.0, attValues));
        //second instance
        attValues = new double[dataset.numAttributes()];

        attValues[0] = Utils.missingValue();
        attValues[1] = dataset.attribute(1).indexOfValue("value_9");
        attValues[2] = dataset.attribute(2).addStringValue("Marinka");
        attValues[3] = dataset.attribute(3).parseDate("23-4-1989");
        dataset.add(new DenseInstance(1.0, attValues));
        //third instance
        Instance example = new DenseInstance(4);
        example.setValue(attr, 16);
        example.setValue(attr1, "value_7");
        example.setValue(attr2, "Mirko");
        example.setValue(attr3, attr3.parseDate("1-1-1988"));
        dataset.add(example);

        System.out.println(dataset);
    }
}
