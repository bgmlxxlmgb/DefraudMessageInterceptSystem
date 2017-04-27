package pers.bgm.dmis.dt01;

import weka.core.*;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/27.
 */
public class T06 {
    public static void main(String a[]) {
        FastVector atts = new FastVector();
        ArrayList<String> classes = new ArrayList<String>();
        classes.add("a");
        Attribute a1 = new Attribute("text", (ArrayList<String>) null);
        Attribute a2 = new Attribute("@@class@@", classes);
        atts.addElement(a1);
        atts.addElement(a2);
        Instances rel_struct = new Instances("single_message", atts, 1);
        System.out.println(rel_struct);
    }
}
