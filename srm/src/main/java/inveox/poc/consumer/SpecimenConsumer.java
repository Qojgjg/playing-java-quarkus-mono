package inveox.poc.consumer;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import io.smallrye.reactive.messaging.kafka.Record;

@ApplicationScoped
public class SpecimenConsumer {

    private final Logger logger = Logger.getLogger(SpecimenConsumer.class);

    @Incoming("specimens-in")
    public void receive(Record<Integer,String> record){
        logger.infof("Got a specimen: %d - %s",record.key(),record.value());
    }
    
}
