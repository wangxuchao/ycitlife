ycitlife
====================================
“盐工学生助手” 微信公众号

#### 已实现的功能：
1.歌曲查询(回复 “歌曲“+”歌名”+“歌手”)  
2.历史上的今天（回复“历史上的今天”）  
3.学校官网首页新闻列表（带查询api）  
4.人脸识别（微信界面发送图片）   
5.周边搜索（微信发送附近+地点）  
6.学校新闻列表查询（带查询api）  
7.学校新闻内容查询（带查询api）  

##### 说明
消息加解密方式为 安全模式  
使用了微信官方提供的加密解密示例代码。    
 异常java.security.InvalidKeyException:illegal Key Size的解决方案  
 在官方网站下载JCE无限制权限策略文件（JDK7的下载地址：  
http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html   
下载后解压，可以看到local_policy.jar和US_export_policy.jar以及readme.txt  
如果安装了JRE，将两个jar文件放到%JRE_HOME%\lib\security目录下覆盖原来的文件  
如果安装了JDK，将两个jar文件放到%JDK_HOME%\jre\lib\security目录下覆盖原来文件  
