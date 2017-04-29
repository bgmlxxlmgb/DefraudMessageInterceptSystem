package mt01;

import core.TrainModel;

/**
 * Created by Administrator on 2017/4/29.
 */
public class Mt01 {
    public static void main(String args[]) {
        try {
            TrainModel.train();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
