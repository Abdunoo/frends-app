package org.acme.ws;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;



import org.acme.dto.NeedUser;
import org.acme.rcd.RcdStory;
import org.acme.srv.SrvStory;

import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.ScheduledExecution;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.inject.Inject;

@ApplicationScoped
@ActivateRequestContext
@NeedUser
public class WsScheduler {

	@Inject
	private SrvStory srvStory;

	// private AtomicInteger counter = new AtomicInteger();

	// public int get() {
	// return counter.get();
	// }

	// @Scheduled(every="10s")
	// void increment() {
	// counter.incrementAndGet();
	// }

	// @Scheduled(cron="0 15 10 * * ?")
	// void cronJob(ScheduledExecution execution) {
	// counter.incrementAndGet();
	// System.out.println(execution.getScheduledFireTime());
	// }

	@Scheduled(cron = "{abdun.cron}")
	void deleteStory() {
		Number hour = LocalDateTime.now().getHour();
		Number minutes = LocalDateTime.now().getMinute();

		List<RcdStory> myStory = srvStory.findAll();
		for (RcdStory rcdStory : myStory) {
			Number hourStr = rcdStory.getEndShow().getHours();
			Number minutesStr = rcdStory.getEndShow().getMinutes();
			System.out.println(hour + " and " + minutes + " = " + hourStr + " and " + minutesStr);
			if (hour == hourStr && minutes == minutesStr) {
				srvStory.delete(rcdStory.getId());
			}

		}

	}
}