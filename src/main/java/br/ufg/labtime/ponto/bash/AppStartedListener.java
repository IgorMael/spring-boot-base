package br.ufg.labtime.ponto.bash;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.system.ApplicationPid;
import org.springframework.context.ApplicationListener;

import static java.time.LocalDateTime.now;


public class AppStartedListener implements ApplicationListener<ApplicationReadyEvent> {
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Arquivo.STARTED.criar(new ApplicationPid() + ",8080");
        Runtime.getRuntime()
                .addShutdownHook(new Thread(() -> Arquivo.STOPPED.criar(now().withNano(0).toString())));
    }

}
