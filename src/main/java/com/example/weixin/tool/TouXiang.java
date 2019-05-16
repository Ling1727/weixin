package com.example.weixin.tool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.example.weixin.R;

import org.litepal.LitePalApplication;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hasee on 2019/3/21.
 */

public class TouXiang {
    private int[] imageIds=new int[]{R.drawable.aobama,R.drawable.anni,R.drawable.bobi,R.drawable.daomei,R.drawable.hanbing
            ,R.drawable.kasa,R.drawable.li,R.drawable.nvjing,R.drawable.nvqiang,R.drawable.ruiwen,R.drawable.timo
            ,R.drawable.xia,R.drawable.xiaopao,R.drawable.xiaoyuren,R.drawable.yasuo,R.drawable.zhaoxin,R.drawable.java,R.drawable.mao};
    private String[] name=new String[]{"aobama","anni","bobi","daomei","hanbing","kasa","li","nvjing",
            "nvqiang","ruiwen","timo","xia","xiaopao","xiaoyuren","yasuo","zhaoxin","java","mao"};
    private static final String PATH="/data"
            + Environment.getDataDirectory().getAbsolutePath()
            + File.separator +"com.example.weixin"+ File.separator + "download";
//    private static Map<String,Bitmap> bitmapMap=new HashMap<>();
    private Context context;
    public TouXiang(Context context){
        this.context=context;
    }

    public void write(){
        File file=new File(PATH);
        Log.d("test",PATH);
        if(!file.exists()){
            file.mkdir();
        }
        for(int i=0;i<imageIds.length;i++){
            File file1=new File(PATH+File.separator+name[i]+".jpg");
            InputStream is =context.getResources().openRawResource(imageIds[i]);
            try {
                FileOutputStream fos=new FileOutputStream(file1);
                int n = 0;// 每次读取的字节长度
                byte[] bb = new byte[1024];// 存储每次读取的内容
                while ((n = is.read(bb)) != -1) {
                    fos.write(bb, 0, n);// 将读取的内容，写入到输出流当中
                }
                //执行完以上后，磁盘下的该文件才完整，大小是实际大小
                fos.close();// 关闭输入输出流
                is.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }


  /*  public static Map<String,Bitmap> getBitmapList(){
        Bitmap bitmap;
        File file=new File(PATH);//+File.separator+touxiang+".jpg"
        File[] fs = file.listFiles();	//遍历path下的文件和目录，放在File数组中
        for(File f:fs){
            //遍历File[]数组
            if(f.getName().endsWith(".jpg")||f.getName().endsWith(".pnf")){
                try {
                    FileInputStream fis=new FileInputStream(f);
                    bitmap= BitmapFactory.decodeStream(fis);
                    bitmapMap.put(f.getName().substring(0,f.getName().lastIndexOf( '.' )),bitmap);
                    Log.d("test",f.getName().substring(0,f.getName().lastIndexOf( '.' )));
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmapMap;
    }*/

    public static Bitmap getBitmap(String name) {
        Bitmap bitmap=null;
        File file=new File(PATH+File.separator+name+".jpg");
        try {
            FileInputStream fis=new FileInputStream(file);
            bitmap= BitmapFactory.decodeStream(fis);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Bitmap bitmap=bitmapMap.get(name);
        return bitmap;
    }
}
