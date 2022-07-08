package com.yzm.designmode.J桥接模式Bridge;

/**
 * 桥接模式
 * 定义 :将抽象和行为划分开来,各自独立,但能动态的结合。
 */
public class BridgeDemo {
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