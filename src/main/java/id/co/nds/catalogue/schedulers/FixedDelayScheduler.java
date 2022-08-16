package id.co.nds.catalogue.schedulers;

import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FixedDelayScheduler {
    static final Logger logger = LogManager.getLogger(FixedDelayScheduler.class);
    Integer counterB = 0;

    // @Scheduled(fixedDelay = 10000)
    public void fixedDelaySchedule() throws Exception {
        Integer counterA = 0;

        //call business logic & services here
        
        logger.debug("Start FixedDelayScheduler at " + Calendar.getInstance().getTime());
        logger.info("Counter-A: " + counterA);
        logger.info("Counter-B: " + counterB);
        counterA++;
        counterB++;

        Thread.sleep(5000);
    }

    // @Scheduled(fixedDelayString = "${param.scheduler.fixeddelay.ms}")
    public void fixedDelayParamSchedule() throws Exception {
        Integer counterA = 0;

        //call business logic & services here

        logger.debug("Start FixedDelayScheduler at " + Calendar.getInstance().getTime());
        logger.info("Counter-A: " + counterA);
        logger.info("Counter-B: " + counterB);
        counterA++;
        counterB++;

        Thread.sleep(5000);
    }
}
