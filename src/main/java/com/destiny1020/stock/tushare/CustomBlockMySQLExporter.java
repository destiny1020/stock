package com.destiny1020.stock.tushare;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.destiny1020.stock.model.StockSymbol;
import com.destiny1020.stock.rdb.service.StockSymbolService;

public class CustomBlockMySQLExporter {

  private static List<String> block_disney = Arrays.asList("SH600637", //东方明珠
      "SH601801", //皖新传媒
      "SH600650", //锦江投资
      "SH600741", //华域汽车
      "SZ002615", //哈尔斯
      "SH600009", //上海机场
      "SH600639", //浦东金桥
      "SH600284", //浦东建设
      "SH600846", //同济科技
      "SH600676", //交运股份
      "SH600170", //上海建工
      "SZ002431", //棕榈园林
      "SH600663", //陆家嘴
      "SH600834", //申通地铁
      "SH600611", //大众交通
      "SH600628", //新世界
      "SH600836", //界龙实业
      "SH600278", //东方创业
      "SH600818", //中路股份
      "SH600708", //光明地产
      "SH600630", //龙头股份
      "SH600827", //百联股份
      "SH600692", //亚通股份
      "SH600662", //强生控股
      "SH600675", //中华企业
      "SH600754", //锦江股份
      "SZ000671", //阳 光 城
      "SZ002162", //悦心健康
      "SH600655" //豫园商城
  );

  private static List<String> block_robot = Arrays.asList("SZ002689", //博林特
      "SZ002347", //泰尔重工
      "SH600522", //中天科技
      "SZ002611", //东方精工
      "SZ002184", //海得控制
      "SH600815", //厦工股份
      "SZ000821", //京山轻机
      "SZ000333", //美的集团
      "SZ002472", //双环传动
      "SZ002444", //巨星科技
      "SZ002559", //亚威股份
      "SZ002031", //巨轮智能
      "SZ002073", //软控股份
      "SZ002270", //法因数控
      "SZ002698", //博实股份
      "SZ002248", //华东数控
      "SH603901", //永创智能
      "SH600835", //上海机电
      "SZ000938", //紫光股份
      "SZ000063", //中兴通讯
      "SZ002067", //景兴纸业
      "SZ002527", //新时达
      "SZ002367", //康力电梯
      "SZ002535", //林州重机
      "SH600775", //南京熊猫
      "SZ000410", //沈阳机床
      "SZ002120", //新海股份
      "SZ000837", //秦川机床
      "SH600288", //大恒科技
      "SZ002011", //盾安环境
      "SZ002380", //科远股份
      "SZ002009", //天奇股份
      "SH600701", //工大高新
      "SZ002230", //科大讯飞
      "SH600843", //上工申贝
      "SH600894", //广日股份
      "SZ000922", //佳电股份
      "SZ002747", //埃斯顿
      "SZ002008", //大族激光
      "SZ002334", //英威腾
      "SZ002116", //中国海诚
      "SZ002520", //日发精机
      "SZ002577", //雷柏科技
      "SZ002441", //众业达
      "SH601608", //中信重工
      "SZ002337", //赛象科技
      "SZ002660", //茂硕电源
      "SH600565", //迪马股份
      "SH600560", //金自天正
      "SH600503", //华丽家族
      "SZ002063", //远光软件
      "SH600346", //大橡塑
      "SZ002497", //雅化集团
      "SZ000913", //钱江摩托
      "SZ002026", //山东威达
      "SZ000961", //中南建设
      "SH600699", //均胜电子
      "SZ002147", //方圆支承
      "SZ002209" //达 意 隆
  );

  private static List<String> block_military = Arrays.asList("SH600184", //光电股份
      "SZ002151", //北斗星通
      "SZ000801", //四川九洲
      "SH600893", //中航动力
      "SH600879", //航天电子
      "SZ002297", //博云新材
      "SH600435", //北方导航
      "SH600391", //成发科技
      "SH600150", //中国船舶
      "SZ000768", //中航飞机
      "SH600372", //中航电子
      "SZ002023", //海特高新
      "SH600316", //洪都航空
      "SZ000738", //中航动控
      "SH600677", //航天通信
      "SZ002013", //中航机电
      "SH600038", //中直股份
      "SH600760", //中航黑豹
      "SH600343", //航天动力
      "SH600685", //中船防务
      "SH600118", //中国卫星
      "SH601890", //亚星锚链
      "SH601989", //中国重工
      "SZ002190", //成飞集成
      "SH600072", //钢构工程
      "SH600990" //四创电子
  );

  private static List<String> block_software = Arrays.asList("SZ000555", //神州信息
      //      "SZ002296", //辉煌科技
      //      "SZ002657", //中科金财
      //      "SZ002405", //四维图新
      //      "SZ002331", //皖通科技
      //      "SZ002065", //东华软件
      //      "SZ002373", //千方科技
      //      "SZ002232", //启明信息
      //      "SZ002362", //汉王科技
      //      "SZ002609", //捷顺科技
      //      "SH600571", //信雅达
      //      "SZ002421", //达实智能
      //      "SH600728", //佳都科技
      //      "SH600570", //恒生电子
      //      "SZ002649", //博彦科技
      //      "SZ002766", //索菱股份
      //      "SZ002512", //达华智能
      //      "SH601519", //大智慧
      //      "SH600446", //金证股份
      //      "SZ002380", //科远股份
      //      "SZ002410", //广联达
      //      "SZ002153", //石基信息
      //      "SZ002230", //科大讯飞
      //      "SH600797", //浙大网新
      //      "SH600536", //中国软件
      //      "SH600845", //宝信软件
      "SZ002195", //二三四五
      //      "SH600588", //用友网络
      //      "SZ002439", //启明星辰
      //      "SZ002771", //真视通
      //      "SZ002268", //卫 士 通
      //      "SZ002063", //远光软件
      //      "SH600410", //华胜天成
      //      "SZ002279", //久其软件
      //      "SH600654", //中安消
      //      "SH600756", //浪潮软件
      //      "SZ002280", //联络互动
      //      "SZ002642", //荣之联
      //      "SZ002253", //川大智胜
      //      "SH603636", //南威软件
      //      "SZ002474", //榕基软件
      //      "SH600476", //湘邮科技
      "SH603918" //金桥信息
      //      "SH600271", //航天信息
      //      "SH600455", //博通股份
      //      "SH600718", //东软集团
      //      "SH603508", //思维列控
      //      "SZ002368", //太极股份
      //      "SZ002401", //中海科技
      //      "SZ002777" //久远银海
  );

  private static final Map<String, List<String>> blockToSymbols;

  static {
    blockToSymbols = new HashMap<String, List<String>>();

    blockToSymbols.put("software", block_software);
    blockToSymbols.put("military", block_military);
    blockToSymbols.put("robot", block_robot);
    blockToSymbols.put("disney", block_disney);
  }

  public static void main(String[] args) {
    if (args.length == 1) {
      String block = args[0];
      List<String> targets = blockToSymbols.get(block);
      if (targets == null) {
        System.err.println("Should provide block among (software, military, robot and disney) !");
      } else {
        exportCore(block, targets);
      }
    } else if (args.length == 0) {
      blockToSymbols.forEach(CustomBlockMySQLExporter::exportCore);
    } else {
      System.err
          .println("Should provide block name or not, but should not provide more than 1 parameter !");
    }

    System.exit(0);
  }

  private static void exportCore(String block, List<String> symbolsCodes) {
    List<StockSymbol> symbols = StockSymbolService.INSTANCE.getSymbols(symbolsCodes);

    long startMillis = System.currentTimeMillis();
    symbols.forEach(symbol -> {
      if (symbol != null && symbol.isNotCYB()) {
        try {
          BatchMySQLExporter.exportHistoryToMySQL(symbol);
        } catch (Exception e) {
          System.err.println(e.getMessage());
          e.printStackTrace();
        }
      }
    });
    long endMillis = System.currentTimeMillis();
    System.out.println(String.format("Finished %s Symbols"
        + " execution for importing history data in: %.2f Seconds", block,
        (endMillis - startMillis) / 1000.0));
  }
}
