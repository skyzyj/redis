package com.itos.redis_demo;

import java.io.*;

//自定义类加载器,首先要继承classloader,重写findclass方法即可
public class MyCustomClassloader extends ClassLoader{
    private static final  int seed = 0B10110110;

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File file = new File("e:/redis_demo/",name.replaceAll(".","/").concat(".class"));
        FileInputStream fis = null;
        ByteArrayOutputStream baos = null;
        try {
            fis = new FileInputStream(file);
            baos = new ByteArrayOutputStream();
            int b = 0;
            while ((b=fis.read())!=-1){
                baos.write(b);
            }
            byte[] bytes = baos.toByteArray();
            return defineClass(name,bytes,0,bytes.length);//把二进制数据转换成class类对象
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (baos != null) {
                    baos.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return super.findClass(name);
    }
    //findClass加密class文件
    private void encFile(String name) {
        File file = new File("e:/redis_demo/", name.replaceAll(".", "/").concat(".class"));
        FileInputStream fis = null;
        ByteArrayOutputStream baos = null;
        try {
            fis = new FileInputStream(file);
            baos = new ByteArrayOutputStream();
            int b = 0;
            while ((b = fis.read()) != -1) {
                baos.write(b ^ seed); // 每个字节与seed进行位与计算 ,解密再进行一次位与计算
            }
            byte[] bytes = baos.toByteArray();
            // return defineClass(name,bytes,0,bytes.length);//把二进制数据转换成class类对象
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args)throws Exception{
        ClassLoader l = new  MyCustomClassloader();
        Class clzss = l.loadClass("com.itos.redis_demo.Hello");
        Hello h = (Hello)clzss.newInstance();
        h.say();
        System.out.println(l.getClass().getClassLoader());
        System.out.println(l.getParent());
    }
}
