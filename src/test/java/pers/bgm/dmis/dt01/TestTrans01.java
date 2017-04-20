package pers.bgm.dmis.dt01;

import pers.bgm.dmis.utils.SplitMessagesIntoFiles;

/**
 * Created by Administrator on 2017/4/21.
 */
public class TestTrans01 {
    public static void main(String a[]) {
        SplitMessagesIntoFiles splitMessagesIntoFiles = new SplitMessagesIntoFiles();
        SplitMessagesIntoFiles.initial();
        splitMessagesIntoFiles.segment();
    }
}
