package com.file.demo;

import org.junit.jupiter.api.Test;

import javax.lang.model.util.Elements;
import javax.swing.text.Document;
import java.io.*;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLSaveDemo {
    public static void main(String[] args) {

        //1.创建存放位置

        File file = new File("E:\\java\\day2\\三国.txt");

        // 2、根据网页结构编写正则，创建pattern对象

        String regex_content = "<p.*?>(.*?)</p>";

        String regex_title = "<title>(.*?)</title>";



        Pattern p_content = Pattern.compile(regex_content);

        Pattern p_title = Pattern.compile(regex_title);



        Matcher m_content;

        Matcher m_title;



        // 3、编写循环，创建向所有小说章节页面发起网络请求的url对象

        for (int i = 1; i <= 120; i++) {

            System.out.println("第" + i + "章开始下载。。。");

            try {

        // 创建每一个页面的url对象

                URL url = new URL("http://www.shicimingju.com/book/sanguoyanyi/" + i + ".html");

        // 创建网络读取流

                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(),"utf8"));



        // 4、读取网络内容网络流BufferReader

                String str = null;



        // 5、创建输入流

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true)));



                while ((str = reader.readLine()) != null) {

                    m_title = p_title.matcher(str.toString());

                    m_content = p_content.matcher(str.toString());



        // 获取小说标题并写入本地文件

                    Boolean isEx = m_title.find();

                    if (isEx) {

                        String title = m_title.group();

        // 清洗得到的数据

                        title = title.replace("<title>", "").replace("</title>", "");

                        System.out.println(title);

                        writer.write("第" + i + "章：" + title + "\n");

                    }



                    while (m_content.find()) {

                        String content = m_content.group();

        // 清洗得到的数据

                        content = content.replace("<p>", "").replace("</p>", "").replace("&nbsp;", "").replace("?", "");

        // 把小说内容写入文件

                        writer.write(content + "\n");

                    }



                }



                System.out.println("第" + i + "章下载完成.........");



                writer.write("\n\n");

                writer.close();

                reader.close();

            } catch (Exception e) {

                System.out.println("下载失败");

                e.printStackTrace();

            }

        }

    }
}
