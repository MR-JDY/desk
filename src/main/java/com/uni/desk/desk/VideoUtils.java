package com.uni.desk.desk;



import net.sourceforge.tess4j.TesseractException;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;

import org.springframework.http.HttpRequest;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class VideoUtils implements MultipartFile
{

    private final byte[] imgContent;
    private final String header;
    @Resource
    private RestTemplate restTemplate;
    /**
     * 例子
     * @param args
     */
    public static void main(String[] args) throws TesseractException {
//        String auth = AuthService.getAuth();
//        String s = fetchFrame("https://v6-default.ixigua.com/25c8193b8bc11c3bc5ba8008f836682f/6076b0db/video/tos/cn/tos-cn-ve-51/fe648c848b9d4fa4a1cf3db8b7164e79/?a=0&amp;br=2061&amp;bt=2061&amp;cd=0%7C0%7C1&amp;ch=0&amp;cr=0&amp;cs=0&amp;cv=1&amp;dr=0&amp;ds=4&amp;er=&amp;l=202104141607070102061050872D022F37&amp;lr=ad&amp;mime_type=video_mp4&amp;net=0&amp;pl=0&amp;qs=0&amp;rc=M2pyNGRkOTM2NDMzNDYzM0ApNzo8Njg1ZTs6Nzg6aDc3N2c2NHMxYy9kNWZgLS1kMDBzczU1Yl8uMC1iNS8xNGBeMjU6Yw%3D%3D&amp;vl=&amp;vr=");
//        System.out.println(s);
//        System.out.println(base64ToMultipart(s));
        String s = "base64,abcde";
        int ab = s.indexOf(",");
        String substring = s.substring(s.indexOf(",")+1,s.length());
        System.out.println(substring);

    }

    public VideoUtils(byte[] imgContent, String header) {
        this.imgContent = imgContent;
        this.header = header;
    }

    public static String base64WithoutHead(String base64Str){
        return base64Str.substring(base64Str.indexOf(",")+1,base64Str.length());
    }
    public static String fetchFrame(String videoPath) {
        FFmpegFrameGrabber ff = null;
        byte[] data = null;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ff = new FFmpegFrameGrabber(videoPath);
            ff.start();
            int lenght = ff.getLengthInFrames();
            int i = 0;
            Frame f = null;
            while (i < lenght) {
                // 过滤前5帧，避免出现全黑的图片
                f = ff.grabFrame();
                if ((i > 0) && (f.image != null)) {
                    break;
                }
                i++;
            }
            BufferedImage bi =  new Java2DFrameConverter().getBufferedImage(f);
            String rotate = ff.getVideoMetadata("rotate");
            if (rotate != null) {
                bi = rotate(bi, Integer.parseInt(rotate));
            }
            ImageIO.write(bi, "jpg", os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ff != null) {
                    ff.stop();
                }
            } catch (FrameGrabber.Exception e) {
                e.printStackTrace();
            }
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return "data:image/jpg;base64,"+encoder.encode(os.toByteArray());
    }

    public static BufferedImage rotate(BufferedImage src, int angel) {
        int src_width = src.getWidth(null);
        int src_height = src.getHeight(null);
        int type = src.getColorModel().getTransparency();
        Rectangle rect_des = calcRotatedSize(new Rectangle(new Dimension(src_width, src_height)), angel);
        BufferedImage bi = new BufferedImage(rect_des.width, rect_des.height, type);
        Graphics2D g2 = bi.createGraphics();
        g2.translate((rect_des.width - src_width) / 2, (rect_des.height - src_height) / 2);
        g2.rotate(Math.toRadians(angel), src_width / 2, src_height / 2);
        g2.drawImage(src, 0, 0, null);
        g2.dispose();
        return bi;
    }

    public static Rectangle calcRotatedSize(Rectangle src, int angel) {
        if (angel >= 90) {
            if(angel / 90 % 2 == 1) {
                int temp = src.height;
                src.height = src.width;
                src.width = temp;
            }
            angel = angel % 90;
        }
        double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;
        double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
        double angel_alpha = (Math.PI - Math.toRadians(angel)) / 2;
        double angel_dalta_width = Math.atan((double) src.height / src.width);
        double angel_dalta_height = Math.atan((double) src.width / src.height);
        int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha - angel_dalta_width));
        int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha - angel_dalta_height));
        int des_width = src.width + len_dalta_width * 2;
        int des_height = src.height + len_dalta_height * 2;
        return new Rectangle(new Dimension(des_width, des_height));
    }

    /**
     * 将base64转换成MultipartFile
     * @param base64
     * @return
     */
    public static MultipartFile base64ToMultipart(String base64) {
        try {
            String[] baseStrs = base64.split(",");

            BASE64Decoder decoder = new BASE64Decoder();
            byte[] b = new byte[0];
            b = decoder.decodeBuffer(baseStrs[1]);

            for(int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            return new VideoUtils(b, baseStrs[0]);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public String getName() {
        // TODO - implementation depends on your requirements
        return System.currentTimeMillis() + Math.random() + "." + header.split("/")[1];
    }

    @Override
    public String getOriginalFilename() {
        // TODO - implementation depends on your requirements
        return System.currentTimeMillis() + (int)Math.random() * 10000 + "." + header.split("/")[1];
    }

    @Override
    public String getContentType() {
        // TODO - implementation depends on your requirements
        return header.split(":")[1];
    }

    @Override
    public boolean isEmpty() {
        return imgContent == null || imgContent.length == 0;
    }

    @Override
    public long getSize() {
        return imgContent.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return imgContent;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(imgContent);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        new FileOutputStream(dest).write(imgContent);
    }

}