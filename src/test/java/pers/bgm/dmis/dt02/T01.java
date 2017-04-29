package pers.bgm.dmis.dt02;

import pers.bgm.dmis.core.SingleMessageProcessUtil;

/**
 * Created by Administrator on 2017/4/29.
 */
public class T01 {
    public static void main(String args[]) throws Exception {
        /*
        *   某公司庆典举办手机抽奖活动，恭喜你中贰等奖，以公证员身份请领取大奖，速回电××号码。
            恭喜你， 您的号码已被李咏砸蛋抽中，请您登陆非常6+1活动网。站http://www.xxxxx领取，验证码9188查询，速回电××号码。
            恭喜您！您的号码已被李咏砸蛋抽中，请您登录非常6+1活动网站领取http://www.xxxxx。
            你的QQ号已被浙江卫视【奔跑吧兄弟】抽选为场外幸运观众，获得奖金168000元及苹果笔记本电脑1部，请登陆http://www.xxxxx和http://www.xxxxx查询领取。
            我是吴彦祖，我正在一个深山里拍戏，有一段武打戏我被打飞，现在我和剧组失去了联系，一个人在山里身无分文，我乱输入的一个号码，就找到了你。实在是缘分，你能给我打1000块钱吗？
            我是房东，换了个号码你记一下，另外这次租金请汇我爱人卡上，工行61222621020114***王鑫，谢了。
        * */
        String message = "恭喜您！您的号码已被李咏砸蛋抽中，请您登录非常6+1活动网站领取http://www.xxxxx。";
        String message_01 = "中华人民共和国！";
        String message_02 = "张三，明天如果你还不还钱，我就在明晚11点在你家吃饭！";
        String dataFilteredPath = "F:/mess/dataFiltered.arff";
        String category = SingleMessageProcessUtil.estimateSingleMessage(SingleMessageProcessUtil.transMessageToInstances(message), message, dataFilteredPath);
        System.out.println("该短信属于：" + category);

        String category_01 = SingleMessageProcessUtil.estimateSingleMessage(SingleMessageProcessUtil.transMessageToInstances(message), message, dataFilteredPath);
        System.out.println("该短信属于：" + category_01);

        String category_02 = SingleMessageProcessUtil.estimateSingleMessage(SingleMessageProcessUtil.transMessageToInstances(message), message, dataFilteredPath);
        System.out.println("该短信属于：" + category_02);


    }
}
