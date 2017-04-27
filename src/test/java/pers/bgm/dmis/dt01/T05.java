package pers.bgm.dmis.dt01;

import pers.bgm.dmis.utils.SplitMessagesIntoFiles;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.OutputStreamWriter;

/**
 * Created by Administrator on 2017/4/25.
 */
public class T05 {
    /*
    *   某公司庆典举办手机抽奖活动，恭喜你中贰等奖，以公证员身份请领取大奖，速回电××号码。
        恭喜你， 您的号码已被李咏砸蛋抽中，请您登陆非常6+1活动网。站http://www.xxxxx领取，验证码9188查询，速回电××号码。
        恭喜您！您的号码已被李咏砸蛋抽中，请您登录非常6+1活动网站领取http://www.xxxxx。
        你的QQ号已被浙江卫视【奔跑吧兄弟】抽选为场外幸运观众，获得奖金168000元及苹果笔记本电脑1部，请登陆http://www.xxxxx和http://www.xxxxx查询领取。
        我是吴彦祖，我正在一个深山里拍戏，有一段武打戏我被打飞，现在我和剧组失去了联系，一个人在山里身无分文，我乱输入的一个号码，就找到了你。实在是缘分，你能给我打1000块钱吗？
        我是房东，换了个号码你记一下，另外这次租金请汇我爱人卡上，工行61222621020114***王鑫，谢了。
    * */
    public static void main(String a[]) throws Exception {
        String value = "某公司庆典举办手机抽奖活动，恭喜你中贰等奖，以公证员身份请领取大奖，速回电××号码。";
        String afterSeg = SplitMessagesIntoFiles.getWordsString(value);
        System.out.println(afterSeg);
        //BufferedWriter bw = new BufferedWriter(new FileWriter("F:/mess/test/testsingle/1.txt"));

    }
}
