package com.example.homework8.bean;

import com.example.homework8.R;

import java.util.ArrayList;

public class GoodsInfo {
    public long rowid; // 行号
    public int sn; // 序号
    public String name; // 名称
    public String desc; // 描述
    public float price; // 价格
    public String thumb_path; // 小图的保存路径
    public String pic_path; // 大图的保存路径
    public int thumb; // 小图的资源编号
    public int pic; // 大图的资源编号

    public GoodsInfo() {
        rowid = 0L;
        sn = 0;
        name = "";
        desc = "";
        price = 0;
        thumb_path = "";
        pic_path = "";
        thumb = 0;
        pic = 0;
    }

    // 声明一个商品的名称数组
    private static String[] mNameArray = {
            "古驰 GUCCI 510304 CAO0G 1000", "古驰 GUCCI 510314 CAO0G 1000",
            "古驰 GUCCI GUCCI女包 GG MARMONT", "古驰 GUCCI 510304 CAO0G 6420",
            "古驰 GUCCI 510314 CAO0G 1000 GUCCI", "古驰 GUCCI 443497 DTDIT 1000","古驰 GUCCI MAX 97 ","古驰 GUCCI CD04A 0FFA "
    };
    // 声明一个商品的描述数组
    private static String[] mDescArray = new String[]{
            "Gucci是源于意大利的时装奢饰品牌，由古驰奥·古驰在1921年于意大利佛罗伦萨创办",
            "GUCCI品牌时装一向以高档、豪华、性感而闻名于世，以“身份与财富之象征”品牌形象成为富有上流社会的消费宠儿",
            "古驰(Gucci)，1921年 [3]  创立于佛罗伦萨 [3]  ，是全球奢侈品品牌之一 [4]  ，借由其独特的创意和革新，以及精湛的意大利工艺闻名于世。",
            "古驰隶属于开云集团 [5]  (Kering Group)。开云集团旗下拥有众多极具影响力的精品、运动及生活方式品牌，是时装与配饰行业的领军者。",
            "古驰GUCCI的标志设计就如同它的商品一样，奢华高贵。金黄的颜色与设计形式所给人们带来的感觉都无可挑剔的展现了其企业的气质，虽然说这样的奢侈品总是会让很多人望而却步，却在人们心中留下了美好的印象。",
            "古驰的品牌标志整体并且和谐，下方的图案体现出整个标志设计的核心，好的标志设计可以把一个企业推上更高的角度，而古驰在标志上就展现出一个更高的层次，一个很多人都想靠近的层次",
            "华丽的设计感使整个古驰的企业标志设计在众多的品牌设计中更显雄风，也增添了其企业气势。",
            "印着成对字母G的商标图案及醒目的红与绿色作为GUCCI的象征出现在公文包、手提袋、钱夹等GUCCI产品之内，这也是GUCCI最早的经典标志设计。"
    };
    // 声明一个商品的价格数组
    private static float[] mPriceArray = {52, 21, 240, 52, 18, 19,16,49};
    // 声明一个商品的小图数组
    private static int[] mPicArray = {
            R.drawable.a, R.drawable.b, R.drawable.c,
            R.drawable.d, R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h,
    };
    //  mPicArray
    private static int[] mThumbArray = {
            R.drawable.a, R.drawable.b, R.drawable.c,
            R.drawable.d, R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h,
    };

    public static ArrayList<GoodsInfo> getDefaultList() {
        ArrayList<GoodsInfo> goodsList = new ArrayList<GoodsInfo>();
        for (int i = 0; i < mNameArray.length; i++) {
            GoodsInfo info = new GoodsInfo();
            info.name = mNameArray[i];
            info.desc = mDescArray[i];
            info.price = mPriceArray[i];
            info.thumb = mThumbArray[i];
            info.pic = mPicArray[i];
            goodsList.add(info);
        }
        return goodsList;
    }
}