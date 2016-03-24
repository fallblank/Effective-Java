package CreateAndDestroyInstance;

/**
 * Created by fallb on 2016/3/24
 *  第一条：考虑静态工厂方法代替构造器
 *
 *  优点：
 *  1.静态工厂有确定（有意义）的名字，避免了签名相近的构造器不易区分的尴尬局面。
 *  2.对于单例或不可变类，静态工厂可以减小创建实例所花费的消耗。可以将实例缓存起来，以供下次直接返回
 *  3.可以实现灵活创建对象，静态工厂可以返回声明类型的任何子类型。
 *  4.静态工厂可以保证在创建带泛型参数的实例时，使得代码更加简洁
 *
 *  缺点：
 *  1.类的构造器如果因为使用了静态工厂而全是private的，会造成类不能被继承
 *  2.静态工厂方法与一般静态方法并没有声明区别（形式上）
 *
 *  个人理解：
 *  1.优点第4条其实并算得上优势，jdk允许如下代码：
 *      Class<V,T> instance =  new Class<V,T>();
 *      Class<V,T> instance =  new Class<>();
 *  2.单列模式的实现需要进行检查，防止多线程同时请求实例
 */
public class Rule01 {
    //一下构造器不具备任何实际意义，仅仅论证上面的观点
    public Rule01(int a,String b){}
    public Rule01(String b,int a){}
    /**
     * 假设上面两个构造器对应者不同情况下的对象构造，他们的方法签名
     * 很相似（就是参数换了个位置）。那么该如何有效的区分她们呢？这
     * 时候静态工厂就有用了。
     */
    public static Rule01 integerFirst(int a,String b){
        return new Rule01(a,b);
    }
    public static Rule01 stringFirst(int a,String b){
        return new Rule01(b,a);
    }

    /**
     * 现在来考虑单例模式怎么用工厂方法实现
     * 假设该类只能创造一个实例，那么如何保证任何时刻内存中只有一个一个实例存在呢？如下
     */
    private Rule01(){}
    private static Rule01 sInstance = null;
    //这是线程不安全的，线程安全的单例模式在后面单例规则了会有具体做法
    public static Rule01 getsInstance(){
        if (sInstance==null){
            sInstance=new Rule01();
        }
        return sInstance;
    }

    /**
     * 静态工厂返回类型可以是声明类型的任何子类
     */
    public static class SubRule01 extends Rule01{}

    public static Rule01 newInstance(){
        return new SubRule01();
    }

    /**
     * 静态工厂使得带泛型参数的类实例化更加简洁
     */
    public static class GenClass<V,T>{
        public GenClass(V v,T t){}
    }
    //直接出初始化
    GenClass<String,Integer> genClass = new GenClass<>("gen",5);

    public static <V,T> GenClass<V,T> newInstance(V v,T t){
        return new GenClass<V,T>(v,t);
    }
    //使用静态工厂
    GenClass<String,Integer> instance = newInstance("gen",5);
}
