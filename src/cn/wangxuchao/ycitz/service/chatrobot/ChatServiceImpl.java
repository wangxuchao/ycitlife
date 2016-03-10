package cn.wangxuchao.ycitz.service.chatrobot;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.lucene.IKAnalyzer;

import cn.wangxuchao.ycitz.dao.chatrobot.ChatLogDao;
import cn.wangxuchao.ycitz.dao.chatrobot.JokeDao;
import cn.wangxuchao.ycitz.dao.chatrobot.KnowledgeDao;
import cn.wangxuchao.ycitz.dao.chatrobot.KnowledgeSubDao;
import cn.wangxuchao.ycitz.model.chatrobot.ChatLog;
import cn.wangxuchao.ycitz.model.chatrobot.Knowledge;

@Service
public class ChatServiceImpl implements ChatService, ApplicationContextAware {
	private static final Log logger = LogFactory.getLog(ChatServiceImpl.class);
	@Autowired
	private KnowledgeDao knowledgeDao;
	@Autowired
	private JokeDao jokeDao;
	@Autowired
	private ChatLogDao chatLogDao;
	@Autowired
	private KnowledgeSubDao knowledgeSubDao;

	@Override
	public String getIndexDir() {
		// 得到.class文件所在路径（WEB-INF/classes/）
		String classpath = ChatService.class.getResource("/").getPath();
		// 将classpath中的%20替换为空格
		classpath = classpath.replaceAll("%20", " ");
		// 索引存储位置：WEB-INF/classes/index/
		return classpath + "index/";
	}

	@Override
	public void createIndex() {
		// 取得问答知识库中的所有记录
		List<Knowledge> knowledgeList = knowledgeDao.findAll();
		Directory directory = null;
		IndexWriter indexWriter = null;
		try {
			directory = FSDirectory.open(new File(getIndexDir()));
			IndexWriterConfig iwConfig = new IndexWriterConfig(
					Version.LUCENE_46, new IKAnalyzer(true));
			indexWriter = new IndexWriter(directory, iwConfig);
			Document doc = null;
			// 遍历问答知识库创建索引
			for (Knowledge knowledge : knowledgeList) {
				doc = new Document();
				// 对question进行分词存储
				doc.add(new TextField("question", knowledge.getQuestion(),
						Store.YES));
				// 对id、answer和category不分词存储
				doc.add(new IntField("id", (int) knowledge.getId(), Store.YES));
				doc.add(new StringField("answer", knowledge.getAnswer(),
						Store.YES));
				doc.add(new IntField("category", knowledge.getCategory(),
						Store.YES));
				indexWriter.addDocument(doc);
			}
			indexWriter.close();
			directory.close();
		} catch (IOException e) {
			logger.info("索引创建失败：" + e.getMessage());
		}

	}

	@Override
	public Knowledge searchIndex(String content) {
		Knowledge knowledge = null;
		try {
			Directory directory = FSDirectory.open(new File(getIndexDir()));
			IndexReader reader = IndexReader.open(directory);
			IndexSearcher searcher = new IndexSearcher(reader);
			// 使用查询解析器创建Query
			QueryParser questParser = new QueryParser(Version.LUCENE_46,
					"question", new IKAnalyzer(true));
			Query query = questParser.parse(QueryParser.escape(content));
			// 检索得分最高的文档
			TopDocs topDocs = searcher.search(query, 1);
			if (topDocs.totalHits > 0) {
				knowledge = new Knowledge();
				ScoreDoc[] scoreDoc = topDocs.scoreDocs;
				for (ScoreDoc sd : scoreDoc) {
					Document doc = searcher.doc(sd.doc);
					knowledge.setId(doc.getField("id").numericValue()
							.intValue());
					knowledge.setQuestion(doc.get("question"));
					knowledge.setAnswer(doc.get("answer"));
					knowledge.setCategory(doc.getField("category")
							.numericValue().intValue());
				}
			}
			reader.close();
			directory.close();
		} catch (IOException e) {
			knowledge = null;
		} catch (Exception e) {
			knowledge = null;
		}
		return knowledge;
	}

	@Override
	public String chat(String openId, String createTime, String question) {
		String answer = null;
		int chatCategory = 0;
		Knowledge knowledge = searchIndex(question);
		// 找到匹配项
		if (null != knowledge) {
			// 笑话
			if (2 == knowledge.getCategory()) {
				answer = jokeDao.getJoke();
				chatCategory = 2;
			} // 上下文
			else if (3 == knowledge.getCategory()) {
				// 判断上一次的聊天类别
				int category = chatLogDao.getLastCategory(openId);
				// 如果是笑话，本次继续回复笑话给用户
				if (2 == category) {
					answer = jokeDao.getJoke();
					chatCategory = 2;
				} else {
					answer = knowledge.getAnswer();
					chatCategory = knowledge.getCategory();
				}
			} // 普通对话
			else {
				answer = knowledge.getAnswer();
				// 如果答案为空，根据知识id从问答知识分表中随机获取一条
				if ("".equals(answer)) {
					answer = knowledgeSubDao.getKnowledgeSub((int) knowledge
							.getId());
				}
				chatCategory = 1;
			}
		} // 未找到匹配项
		else {
			answer = getDefaultAnswer();
			chatCategory = 0;
		}
		// 保存聊天记录
		ChatLog chatLog = new ChatLog();
		chatLog.setOpenId(openId);
		chatLog.setCreateTime(createTime);
		chatLog.setReqMsg(question);
		chatLog.setRespMsg(answer);
		chatLog.setChatCategory(chatCategory);

		chatLogDao.add(chatLog);
		return answer;
	}

	@Override
	public String getDefaultAnswer() {
		String[] answer = { "要不我们聊点别的？", "恩？你到底在说什么呢？", "没有听懂你说的，能否换个说法？",
				"虽然不明白你的意思，但我却能用心去感受", "听的我一头雾水，阁下的知识真是渊博呀，膜拜~",
				"真心听不懂你在说什么，要不你换种表达方式如何？", "哎，我小学语文是体育老师教的，理解起来有点困难哦",
				"是世界变化太快，还是我不够有才？为何你说话我不明白？" };
		return answer[getRandomNumber(answer.length)];
	}

	/**
	 * 随机生成 0~length-1 之间的某个值
	 *
	 * @return int
	 */
	private static int getRandomNumber(int length) {
		Random random = new Random();
		return random.nextInt(length);
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		logger.info("开始创建索引文件");
		File indexDir = new File(this.getIndexDir());
		// 如果索引目录不存在则创建索引
		if (!indexDir.exists()) {
			this.createIndex();
		}
	}

}
