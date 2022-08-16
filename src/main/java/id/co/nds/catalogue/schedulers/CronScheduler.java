package id.co.nds.catalogue.schedulers;

import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CronScheduler {
    static final Logger logger = LogManager.getLogger(CronScheduler.class);
    Integer counterB = 0;

    // @Scheduled(cron = "0/10 * * * * ?")
    public void cronSchedule() throws Exception {
        Integer counterA = 0;
        logger.debug("Start CronScheduler at " + Calendar.getInstance().getTime());
        logger.info("Counter-A: " + counterA);
        logger.info("Counter-B: " + counterB);
        counterA++;
        counterB++;

        Thread.sleep(15000);
    }

    // @Scheduled(cron = "${param.scheduler.cron}")
    public void cronParamSchedule() throws Exception {
        Integer counterA = 0;
        logger.debug("Start CronScheduler at " + Calendar.getInstance().getTime());
        logger.info("Counter-A: " + counterA);
        logger.info("Counter-B: " + counterB);
        counterA++;
        counterB++;
    }
}
