package pers.bgm.dmis.dt01;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/27.
 */
public class T07 {
    public static void main(String a[]) {
        List<String> list = new ArrayList<String>();
        list.add("hello1");
        Instances instances = generatePopularInstance(list);
        System.out.println(instances);
    }

    public static Instances generatePopularInstance(List<String> entities) {
        //set attributes
        ArrayList<Attribute> attributes = new ArrayList<Attribute>();
        ArrayList<String> classes = new ArrayList<String>();
        classes.add("a");
        Attribute a1 = new Attribute("text", (ArrayList<String>) null);
        Attribute a2 = new Attribute("@@class@@", classes);

        attributes.add(a1);
        attributes.add(a2);
        //set instances
        Instances instances = new Instances("single_message", attributes, 1);
        instances.setClassIndex(instances.numAttributes() - 1);

        //add instance
        Instance instance = new DenseInstance(attributes.size());
        instance.setDataset(instances);
        instance.setValue(0, "hello");
        instance.setValue(1, "a");
        instances.add(instance);



        /*for (String message: entities) {
            Instance instance = new DenseInstance(attributes.size());
            instance.setValue(0,2);
            instance.setValue(1,3);
            instances.add(instance);
        }*/
        return instances;
    }
}
