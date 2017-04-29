package mt01;

import pers.bgm.dmis.utils.SplitMessagesIntoFiles;

/**
 * Created by Administrator on 2017/4/29.
 */
public class Mt02 {
    public static void main(String args[]) {
        for (int i = 0; i < 200; i++) {
            System.out.println(SplitMessagesIntoFiles.getWordsString("在镇江火车站看到好多兵哥哥还有军车"));
        }
    }
}
