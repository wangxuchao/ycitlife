package cn.wangxuchao.ycitz.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SpringTask {
	@Scheduled(cron = "0/2 16 14 * * ?")
	public void myTask() {
		System.out.println("这个任务下午两点16，每两秒执行一次！");
	}
}
