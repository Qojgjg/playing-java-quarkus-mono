package inveox.poc.producer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import inveox.poc.Specimen;
import io.smallrye.reactive.messaging.kafka.Record;

@ApplicationScoped
public class SpecimenProducer {

    @Inject @Channel("specimens-out")
    Emitter<Record<Integer,String>> emitter;
    /**
     * @param specimen
     */
    public void sendSpecimenToKafka(Specimen specimen){
        emitter.send(Record.of(specimen.getId().intValue(),specimen.toString()));
    }
}
