package id.co.nds.catalogue.schedulers;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import id.co.nds.catalogue.entities.ProductEntity;
import id.co.nds.catalogue.repos.ParamRepo;
import id.co.nds.catalogue.services.ProductService;

@Component
public class CheckQuantityScheduler implements SchedulingConfigurer {
    @Autowired
    private ParamRepo paramRepo;

    @Autowired
    private ProductService productService;

    private static final String PARAM_KEY = "CHECK_QUANTITY";

    Integer counter = 0;

    private static ScheduledTaskRegistrar scheduledTaskRegistrar;
    @SuppressWarnings("rawtypes")
    private static ScheduledFuture future;

    static final Logger logger = LogManager.getLogger(CheckQuantityScheduler.class);
    private static String cronVal = "";
    public static boolean stopScheduler = false;

    @Bean
    public TaskScheduler poolScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix("CheckQuantityScheduler-ThreadPookTaskSchedule - ##");
        scheduler.setPoolSize(2);
        scheduler.initialize();
        return scheduler;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        if(scheduledTaskRegistrar == null) {
            scheduledTaskRegistrar = taskRegistrar;
        }
        if(taskRegistrar.getScheduler() == null) {
            taskRegistrar.setScheduler(poolScheduler());
        }

        reloadParamScheduler();

        CronTrigger cronTrigger = new CronTrigger(cronVal, TimeZone.getDefault());

        future = taskRegistrar.getScheduler().schedule(() -> scheduleCronTask(), cronTrigger);
    }

    public void scheduleCronTask() {
        logger.debug("scheduleCron: run scheduler with configuration -> {" + cronVal + "}...");

        try {
            logger.debug("Start Scheduler at " + Calendar.getInstance().getTime());
            counter++;
            logger.info("executing task......");
            logger.info("task " + counter);

            //business logic below
            String output;
            List<ProductEntity> products = productService.findProductsLessThan5Quantity();
            if(products.size() == 0) {
                output = "No product with less than 5 quantity exists.";
            }
            else {
                output = "\nProducts with less than 5 quantity:\n";
                for(int i = 0; i < products.size(); i++) {
                    output += (i + 1) + ".\n" + 
                    "Name    : " + products.get(i).getName() + "\n" +
                    "Quantity: " + products.get(i).getQuantity() + "\n\n";
                }
            }
            
            logger.info(output);
            //business logic above

            logger.info("finish executing task......");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if(stopScheduler) {
                cancelTasks(true);
                scheduledTaskRegistrar = null;
                logger.debug("Stopping scheduler task...");
            }
        }

        reloadParamScheduler();
    }

    private void reloadParamScheduler() {
        if(cronVal.trim().equalsIgnoreCase("")) {
            cronVal = paramRepo.findById(PARAM_KEY).orElse(null).getParamValue();
        }
        else {
            String newCronFromDb = "";
            newCronFromDb = paramRepo.findById(PARAM_KEY).orElse(null).getParamValue();

            if(!stopScheduler && !newCronFromDb.equalsIgnoreCase(cronVal)) {
                cronVal = newCronFromDb;

                logger.info("scheduleCron: Next execution time of this taken from cron expression -> {" + newCronFromDb + "}");
                cancelTasks(false);
                activateScheduler();
            }
        }
    }

    public void cancelTasks(boolean mayInterruptIfRunning) {
        logger.info("###Cancelling all tasks...");
        future.cancel(mayInterruptIfRunning);
    }

    public void activateScheduler() {
        logger.info("###Reload Scheduler..");
        configureTasks(scheduledTaskRegistrar);
    }

    public static void shutdownScheduler() {
        logger.info("###ShuttingDown Scheduler..");
        stopScheduler = true;
    }
}
