package com.style8.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.style8.message.resp.Article;
import com.style8.message.resp.NewsMessage;
import com.style8.message.resp.TextMessage;
import com.style8.util.MessageUtil;

/**
 * 核心服务类
 */
public class CoreService {
	
	private static Log log = LogFactory.getLog(CoreService.class);
	
	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public static String processRequest(HttpServletRequest request) {
		String respMessage = null;
		try {
			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);

			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			log.info("");
			log.info("msgType:"+msgType);
			log.info("fromUserName:"+fromUserName);
			log.info("toUserName:"+toUserName);

			// 默认回复此文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);
			// 由于href属性值必须用双引号引起，这与字符串本身的双引号冲突，所以要转义			
			StringBuffer contentMsg = new StringBuffer();  
			contentMsg.append("欢迎八号格调微信公众平台").append("\n");  
			contentMsg.append("您好，我是机器人小八，请回复数字选择服务：").append("\n\n");  
			contentMsg.append("1  小8淘宝店铺").append("\n");  
			contentMsg.append("2  客厅案例").append("\n");  
			contentMsg.append("3  优美方案").append("\n");  
			contentMsg.append("4 小八手工艺").append("\n");  
			contentMsg.append("5  小八淘宝店").append("\n");  

			textMessage.setContent(contentMsg.toString());
			// 将文本消息对象转换成xml字符串
			respMessage = MessageUtil.textMessageToXml(textMessage);

			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				// 接收用户发送的文本消息内容
				String content = requestMap.get("Content");
				System.out.println("content:"+content);

				// 创建图文消息
				NewsMessage newsMessage = new NewsMessage();
				newsMessage.setToUserName(fromUserName);
				newsMessage.setFromUserName(toUserName);
				newsMessage.setCreateTime(new Date().getTime());
				newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
				newsMessage.setFuncFlag(0);

				List<Article> articleList = new ArrayList<Article>();
				// 单图文消息
				if ("1".equals(content)) {
					Article article = new Article();
					article.setTitle("八号格调官方淘宝店");
					article.setDescription("学生帆布笔袋 时尚设计 多省包邮 帆布单层大容量长铅笔袋文具盒包 男女中小学生创意可爱简约");
					article.setPicUrl("https://img.alicdn.com/imgextra/i2/2824940147/TB2c10lnpXXXXaLXpXXXXXXXXXX_!!2824940147.jpg");
					article.setUrl("https://shop149735363.taobao.com");
					articleList.add(article);
					// 设置图文消息个数
					newsMessage.setArticleCount(articleList.size());
					// 设置图文消息包含的图文集合
					newsMessage.setArticles(articleList);
					// 将图文消息对象转换成xml字符串
					respMessage = MessageUtil.newsMessageToXml(newsMessage);
				}
				// 单图文消息---不含图片
				else if ("2".equals(content)) {
					Article article = new Article();
					article.setTitle("美丽的客厅");
					// 图文消息中可以使用QQ表情、符号表情
					article.setDescription("八号：" + emoji(0x1F6B9)+"漂亮的客厅要搭配些：沙发、茶几、电视柜、墙纸布、窗帘。可以展现出不同的风格，营造不同的幸福空间");
					// 将图片置为空
					article.setPicUrl("");
					article.setUrl("http://mp.weixin.qq.com/s?__biz=MzA4NzU0NzgxNg==&mid=402972694&idx=1&sn=b8d0c0261d40337dc0f3dbdee4033873&scene=1&srcid=0805LaC288HY9azeWIkYeHHe#wechat_redirect");
					articleList.add(article);
					newsMessage.setArticleCount(articleList.size());
					newsMessage.setArticles(articleList);
					respMessage = MessageUtil.newsMessageToXml(newsMessage);
				}
				// 多图文消息
				else if ("3".equals(content)) {
					Article article1 = new Article();
					article1.setTitle("第１篇\n隐藏的美、您们发现了吗\n引言");
					article1.setDescription("");
					article1.setPicUrl("http://mmbiz.qpic.cn/mmbiz/FmzN970IDWU3ib1aEfV1P5GtHgQODn88Q6xFoJ8XMgnsiakfzXlBAzCz28ToImyEIAXG4iavK5vQ2Fp8I03CiaJJGw/640?wx_fmt=jpeg&wxfrom=5&wx_lazy=1");
					article1.setUrl("http://mp.weixin.qq.com/s?__biz=MzA4NzU0NzgxNg==&mid=402385014&idx=1&sn=67b257913ebe1fa727d2aefa063c02d6&scene=4#wechat_redirect");

					Article article2 = new Article();
					article2.setTitle("第2篇\n城市里的泥土香");
					article2.setDescription("");
					article2.setPicUrl("http://mmbiz.qpic.cn/mmbiz/FmzN970IDWVJnVotaIyzHHXxiciaRSOnhxfS3zRxF0Ly6fFbnXkr8Q6ibF89am3JfkbTiaUnUmCINURzmibnu1xcTcQ/640?wx_fmt=jpeg&wxfrom=5&wx_lazy=1");
					article2.setUrl("http://mp.weixin.qq.com/s?__biz=MzA4NzU0NzgxNg==&mid=208880941&idx=1&sn=dadfd7e77dda54da42b2b356e1f53277&scene=4#wechat_redirect");

					Article article3 = new Article();
					article3.setTitle("第3篇\n原来你也匠心独运");
					article3.setDescription("");
					article3.setPicUrl("http://mmbiz.qpic.cn/mmbiz/wIQDEnty0EsY0GEWmic4Swiav6dvJ5JNKFDUNeKe5NTztBibPru9PCcfowan4MbWmZxnr5HGp986YahQ1B0nzcR9g/640?wx_fmt=jpeg&wxfrom=5&wx_lazy=1");
					article3.setUrl("http://mp.weixin.qq.com/s?__biz=MzA4NzU0NzgxNg==&mid=208551566&idx=1&sn=99b35ed62033430416d5569145158484&scene=4#wechat_redirect");

					articleList.add(article1);
					articleList.add(article2);
					articleList.add(article3);
					newsMessage.setArticleCount(articleList.size());
					newsMessage.setArticles(articleList);
					respMessage = MessageUtil.newsMessageToXml(newsMessage);
				}
				// 多图文消息---首条消息不含图片
				else if ("4".equals(content)) {
					Article article1 = new Article();
					article1.setTitle("微信公众帐号开发案例");
					article1.setDescription("");
					// 将图片置为空
					article1.setPicUrl("");
					article1.setUrl("https://shop149735363.taobao.com");

					Article article2 = new Article();
					article2.setTitle("第4篇\nTestTestTest");
					article2.setDescription("");
					article2.setPicUrl("https://img.alicdn.com/imgextra/i2/2824940147/TB2c10lnpXXXXaLXpXXXXXXXXXX_!!2824940147.jpg");
					article2.setUrl("https://shop149735363.taobao.com");

					Article article3 = new Article();
					article3.setTitle("第5篇\n9999999999999");
					article3.setDescription("");
					article3.setPicUrl("https://img.alicdn.com/imgextra/i2/2824940147/TB2c10lnpXXXXaLXpXXXXXXXXXX_!!2824940147.jpg");
					article3.setUrl("https://shop149735363.taobao.com");

					Article article4 = new Article();
					article4.setTitle("第6篇\n文99999996666666");
					article4.setDescription("");
					article4.setPicUrl("https://img.alicdn.com/imgextra/i2/2824940147/TB2c10lnpXXXXaLXpXXXXXXXXXX_!!2824940147.jpg");
					article4.setUrl("https://shop149735363.taobao.com");

					articleList.add(article1);
					articleList.add(article2);
					articleList.add(article3);
					articleList.add(article4);
					newsMessage.setArticleCount(articleList.size());
					newsMessage.setArticles(articleList);
					respMessage = MessageUtil.newsMessageToXml(newsMessage);
				}
				// 多图文消息---最后一条消息不含图片
				else if ("5".equals(content)) {
					Article article1 = new Article();
					article1.setTitle("第7篇\n 000000000000000000");
					article1.setDescription("");
					article1.setPicUrl("https://img.alicdn.com/imgextra/i2/2824940147/TB2c10lnpXXXXaLXpXXXXXXXXXX_!!2824940147.jpg");
					article1.setUrl("https://shop149735363.taobao.com");

					Article article2 = new Article();
					article2.setTitle("第8篇\n3333333333333333");
					article2.setDescription("");
					article2.setPicUrl("https://img.alicdn.com/imgextra/i2/2824940147/TB2c10lnpXXXXaLXpXXXXXXXXXX_!!2824940147.jpg");
					article2.setUrl("https://shop149735363.taobao.com");

					Article article3 = new Article();
					article3.setTitle("公众号开发测试案例，使用说明，测试使用！");
					article3.setDescription("");
					// 将图片置为空
					article3.setPicUrl("");
					article3.setUrl("https://shop149735363.taobao.com");

					articleList.add(article1);
					articleList.add(article2);
					articleList.add(article3);
					newsMessage.setArticleCount(articleList.size());
					newsMessage.setArticles(articleList);
					respMessage = MessageUtil.newsMessageToXml(newsMessage);
				}
			}
			else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)){
				// 接收用户发送的事件请求内容
				String Event = requestMap.get("Event");
				String EventKey = requestMap.get("EventKey");
				log.info("EventKey:"+Event);
				log.info("EventKey:"+EventKey);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respMessage;
	}

	/**
	 * emoji表情转换(hex -> utf-16)
	 * 
	 * @param hexEmoji
	 * @return
	 */
	public static String emoji(int hexEmoji) {
		return String.valueOf(Character.toChars(hexEmoji));
	}
}