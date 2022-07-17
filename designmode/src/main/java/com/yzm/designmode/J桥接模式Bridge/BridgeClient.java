package com.yzm.designmode.J桥接模式Bridge;

/**
 * 桥接模式
 * 桥接（Bridge）是用于把抽象化与实现化解耦，使得二者可以独立变化。这种类型的设计模式属于结构型模式，它通过提供抽象化和实现化之间的桥接结构，来实现二者的解耦。
 * 这种模式涉及到一个作为桥接的接口，使得实体类的功能独立于接口实现类。这两种类型的类可被结构化改变而互不影响。
 *
 * 意图：将抽象部分与实现部分分离，使它们都可以独立的变化。
 * 主要解决：在有多种可能会变化的情况下，用继承会造成类爆炸问题，扩展起来不灵活。
 * 何时使用：实现系统可能有多个角度分类，每一种角度都可能变化。
 * 如何解决：把这种多角度分类分离出来，让它们独立变化，减少它们之间耦合。
 * 关键代码：抽象类依赖实现类。
 * 应用实例： 1、猪八戒从天蓬元帅转世投胎到猪，转世投胎的机制将尘世划分为两个等级，即：灵魂和肉体，前者相当于抽象化，后者相当于实现化。生灵通过功能的委派，调用肉体对象的功能，使得生灵可以动态地选择。
 *          2、墙上的开关，可以看到的开关是抽象的，不用管里面具体怎么实现的。
 * 优点： 1、抽象和实现的分离。 2、优秀的扩展能力。 3、实现细节对客户透明。
 * 缺点：桥接模式的引入会增加系统的理解与设计难度，由于聚合关联关系建立在抽象层，要求开发者针对抽象进行设计与编程。
 * 使用场景： 1、如果一个系统需要在构件的抽象化角色和具体化角色之间增加更多的灵活性，避免在两个层次之间建立静态的继承联系，通过桥接模式可以使它们在抽象层建立一个关联关系。
 *          2、对于那些不希望使用继承或因为多层次继承导致系统类的个数急剧增加的系统，桥接模式尤为适用。
 *          3、一个类存在两个独立变化的维度，且这两个维度都需要进行扩展。
 * 注意事项：对于两个独立变化的维度，使用桥接模式再适合不过了。
 */
public class BridgeClient {
    public static void main(String[] args) {
        //想要改变的现象：抽象类或接口与行为固定绑定
        System.out.println("旧时代的固定绑定---------------");
        Video video = new FlvVideo();
        video.openVideo();
        video.showVideo();

        System.out.println("新时代的动态绑定---------------");
        //想要实现抽象部分与行为分离，不直接像上面那样，而是通过抽象类来动态地绑定抽象与具体实现
        //动态绑定时，可以附加一些额外的功能
        Platform windowsPlatform = new WindowsPlatform(new FlvVideo());
        Video flv = windowsPlatform.createVideo();
        flv.openVideo();
        flv.showVideo();
        System.out.println();
        Platform androidPlatform = new AndroidPlatform(new MP4Video());
        Video mp4 = androidPlatform.createVideo();
        mp4.openVideo();
    }
}

/**
 * 定义接口规范行为
 */
interface Video {
    void openVideo();

    void showVideo();
}

/**
 * 接口的具体实现类，实现行为细节
 */
class FlvVideo implements Video {
    @Override
    public void openVideo() {
        System.out.println("打开 FLV 格式视频");
    }

    @Override
    public void showVideo() {
        System.out.println("当前视频格式是 FLV");
    }
}

class MP4Video implements Video {
    @Override
    public void openVideo() {
        System.out.println("打开 MP4 格式视频");
    }

    @Override
    public void showVideo() {
        System.out.println("当前视频格式是 MP4");
    }
}


abstract class Platform {
    /**
     * 这是桥接模式最核心的代码
     * 在 Platform 中通过组合方式关联 video
     * Platform 的子类也可以关联 video 子类
     */
    protected Video video;

    public Platform(Video video) {
        this.video = video;
    }

    abstract Video createVideo();
}

class WindowsPlatform extends Platform {
    public WindowsPlatform(Video video) {
        super(video);
    }

    @Override
    Video createVideo() {
        System.out.println("在 Windows 平台播放视频");
        return video;
    }
}

class AndroidPlatform extends Platform {
    public AndroidPlatform(Video video) {
        super(video);
    }

    @Override
    Video createVideo() {
        System.out.println("在 Android 平台打开视频");
        return video;
    }
}