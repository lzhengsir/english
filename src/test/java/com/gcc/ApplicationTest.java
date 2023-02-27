package com.gcc;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gcc.common.ThreadLocalHolder;
import com.gcc.entity.SystemUser;
import com.gcc.entity.Word;
import com.gcc.mapper.WordMapper;
import com.gcc.service.SystemUserService;
import com.gcc.service.WordService;
import com.gcc.util.ExcelHeadUtils;
import com.gcc.util.Md5PasswordUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * @author: lzhen
 * @since: 2022/11/19 23:54
 * @description:
 */
@SpringBootTest
@Slf4j
public class ApplicationTest {
    @Autowired
    private WordService wordService;
    @Resource
    private WordMapper wordMapper;

    @Test
    void insertWordTest() {
        Word word = new Word();
        word.setSpell("apple");
        word.setEmphasisMean("苹果");
        wordService.save(word);
        System.out.println(word);
    }

    @Test
    void insertBatchWordTest() {
        ArrayList<Word> words = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Word word = new Word();
            word.setSpell("apple" + i);
            word.setEmphasisMean("苹果" + i);
            words.add(word);
        }
        wordService.saveBatch(words);
        System.out.println(words);
    }

    @Test
    void updateWordTest() {
        Word word = wordService.getById("1594001916619714562");
        wordService.updateById(word);
        System.out.println(word);
    }

    @Test
    void deleteWordTest() {
        System.out.println("1594001916619714562".length());
        wordService.removeById("1594001916619714562");
    }

    @Test
    void wordMapperInsertBatchTest() {
        ArrayList<Word> words = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Word word = new Word();
            word.setSpell("apple" + i);
            word.setEmphasisMean("苹果" + i);
            words.add(word);
        }
        wordMapper.insertBatch(words);
        words.forEach(System.out::println);
    }


    @Test
    void wordMapperUpdateBatchTest() {
        QueryWrapper<Word> wrapper = new QueryWrapper<>();
        wrapper.eq("spell", "apple");
        Word word = wordService.getOne(wrapper);
        word.setEmphasisMean("小苹果aaa");
        ArrayList<Word> words = new ArrayList<>();
        words.add(word);
        wordMapper.updateBatch(words);
        words.forEach(System.out::println);
    }

    @Test
    void equalsTest() {
        Word word1 = new Word();
        word1.setSpell("apple");
        word1.setEmphasisMean("苹果");
        Word word2 = new Word();
        word2.setSpell("apple3");
        word2.setEmphasisMean("苹果123");
        System.out.println(word1.equals(word2));

        Word word3 = new Word();
        word3.setSpell("apple1");
        word3.setEmphasisMean("苹果123");
        System.out.println(word1.equals(word2));

        ArrayList<Word> words1 = new ArrayList<>();
        words1.add(word1);
        words1.add(word2);
        words1.add(word3);

        ArrayList<Word> words2 = new ArrayList<>();
        Word word = new Word();
        word.setSpell("apple");
        word.setEmphasisMean("苹果gg");
        words2.add(word);
        words1.removeAll(words2);
        words1.forEach(System.out::println);
    }

    @Test
    void threadLocalTest() {
        ThreadLocalHolder.USERID.set("第一次");
        new Thread() {
            @Override
            public void run() {
                ThreadLocalHolder.USERID.set("第二次");
                ThreadLocalHolder.USERID.remove();
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                ThreadLocalHolder.USERID.set("第三次");
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                ThreadLocalHolder.USERID.set("第四次");
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                ThreadLocalHolder.USERID.set("第五次");
            }
        }.start();
        System.out.println(ThreadLocalHolder.USERID.get());
        System.out.println(ThreadLocalHolder.USERID.get());
        ThreadLocalHolder.USERID.remove();
        System.out.println(ThreadLocalHolder.USERID.get());
    }

    @Autowired
    private SystemUserService systemUserService;

    @Test
    void insertAdmin() {
        SystemUser systemUser = new SystemUser();
        systemUser.setId("SuperAdmin");
        systemUser.setUsername("lzhengsir");
        systemUser.setPwd(Md5PasswordUtils.dynamicEncryption("123456"));
        systemUserService.save(systemUser);
    }

    @Test
    void listTest() {
        ArrayList<Word> words = new ArrayList<>();
        Word word1 = new Word();
        word1.setSpell("a");
        word1.setEmphasisMean("1");
        words.remove(word1);
        words.add(word1);
        Word word2 = new Word();
        word2.setSpell("b");
        word2.setEmphasisMean("2");
        words.remove(word2);
        words.add(word2);
        Word word3 = new Word();
        word3.setSpell("c");
        word3.setEmphasisMean("3");
        words.remove(word3);
        words.add(word3);
        words.forEach(System.out::println);
        System.out.println("---------");
        Word word4 = new Word();
        word4.setSpell("b");
        word4.setEmphasisMean("222");
        words.remove(word4);
        words.add(word4);
        words.forEach(System.out::println);
    }

    @Test
    void downloadTest() {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。关于其他Region对应的Endpoint信息，请参见访问域名和数据中心。
        String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "yourAccessKeyId";
        String accessKeySecret = "yourAccessKeySecret";
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "examplebucket";
        // 填写Object完整路径，例如exampledir/exampleobject.txt。Object完整路径中不能包含Bucket名称。
        String objectName = "exampledir/exampleobject.txt";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            // ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
            OSSObject ossObject = ossClient.getObject(bucketName, objectName);

            // 读取文件内容。
            System.out.println("Object content:");
            BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
            while (true) {
                String line = reader.readLine();
                if (line == null) break;

                System.out.println("\n" + line);
            }
            // 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
            reader.close();
            // ossObject对象使用完毕后必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
            ossObject.close();

        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (Throwable ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    @Test
    void ymlMapTest() {
        HashMap<Integer, String> hashMap = new HashMap<>();
        hashMap.put(0, "单词");
        hashMap.put(1, "意思");
        hashMap.put(2, "音标");
        System.out.println(hashMap.equals(ExcelHeadUtils.COLUMN_MAP));
    }

    @Test
    void simpleDataFormatTest() {
        String format = "2022/6/8";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(format);
        } catch (ParseException e) {
//            e.printStackTrace();
            log.error("格式错误");
        }
        System.out.println(date);
    }
}
