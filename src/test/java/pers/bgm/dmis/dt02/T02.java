package pers.bgm.dmis.dt02;

import pers.bgm.dmis.core.SingleMessageProcessUtil;

/**
 * Created by Administrator on 2017/4/29.
 */
public class T02 {
    public static void main(String args[]) throws Exception {
        /*
        *   某公司庆典举办手机抽奖活动，恭喜你中贰等奖，以公证员身份请领取大奖，速回电××号码。
            恭喜你， 您的号码已被李咏砸蛋抽中，请您登陆非常6+1活动网。站http://www.xxxxx领取，验证码9188查询，速回电××号码。
            恭喜您！您的号码已被李咏砸蛋抽中，请您登录非常6+1活动网站领取http://www.xxxxx。
            你的QQ号已被浙江卫视【奔跑吧兄弟】抽选为场外幸运观众，获得奖金168000元及苹果笔记本电脑1部，请登陆http://www.xxxxx和http://www.xxxxx查询领取。
            我是吴彦祖，我正在一个深山里拍戏，有一段武打戏我被打飞，现在我和剧组失去了联系，一个人在山里身无分文，我乱输入的一个号码，就找到了你。实在是缘分，你能给我打1000块钱吗？
            我是房东，换了个号码你记一下，另外这次租金请汇我爱人卡上，工行61222621020114***王鑫，谢了。
        * */
        String message = "【龙果学院】金秋十月、感恩回馈，龙果学院精品课程史上最优惠，只需花一套的价钱即可获得两套高端分布式架构课程！赶快去抢购吧！退订回TD";
        String message_01 = "【阿里云】虚拟主机双11大促，新购低至5折，再领50元券，只为建站而生，预装环境、送数据库，仅此一天tb.cn/pmKplOx 回td退订";
        String message_02 = "【招商证券】智讯财富版：周小川称货币政策或已处宽松周期尾部。市场强势上涨累积一定获利盘需要消化，目前位置不宜过度追高操作。仅供参考回A退订。";
        String category = SingleMessageProcessUtil.estimateSingleMessage(SingleMessageProcessUtil.transMessageToInstances(message), message);
        System.out.println("该短信属于：" + category);

        String category_01 = SingleMessageProcessUtil.estimateSingleMessage(SingleMessageProcessUtil.transMessageToInstances(message_01), message_01);
        System.out.println("该短信属于：" + category_01);

        String category_02 = SingleMessageProcessUtil.estimateSingleMessage(SingleMessageProcessUtil.transMessageToInstances(message_02), message_02);
        System.out.println("该短信属于：" + category_02);


    }
}
