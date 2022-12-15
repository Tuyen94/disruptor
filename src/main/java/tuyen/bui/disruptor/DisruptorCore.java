package tuyen.bui.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class DisruptorCore {

    private Disruptor<DisruptorEvent<TestEvent>> disruptor = new Disruptor<>(
            DisruptorEvent::new,
            16,
            DaemonThreadFactory.INSTANCE,
            ProducerType.SINGLE,
            new BlockingWaitStrategy());
    private RingBuffer<DisruptorEvent<TestEvent>> ringBuffer;

    @PostConstruct
    public void init() {
    }

    public EventHandlerGroup<DisruptorEvent<TestEvent>> setHandler(EventHandler<DisruptorEvent<TestEvent>>... handlers) {
        return disruptor.handleEventsWith(handlers);
    }

    public void start() {
        ringBuffer = disruptor.start();
    }

    public void publish(TestEvent testEvent) {
        long sequenceId = ringBuffer.next();
        DisruptorEvent<TestEvent> valueEvent = ringBuffer.get(sequenceId);
        valueEvent.setData(testEvent);
        ringBuffer.publish(sequenceId);
    }
}
