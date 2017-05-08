package mt01;

import core.TrainModel;
import pers.bgm.dmis.utils.SplitMessagesIntoFiles;

/**
 * Created by Administrator on 2017/4/29.
 */
public class Mt02 {
    public static void main(String args[]) throws Exception {
        long start = System.currentTimeMillis();

        TrainModel.evaluateTrainedModel();

        long end = System.currentTimeMillis();
        long cost = (end - start) / (1000 * 60 * 60);
        System.out.println(cost);
    }
}
