package tuyen.bui.disruptor;

import com.lmax.disruptor.EventFactory;
import lombok.Data;

@Data
public class DisruptorEvent<T> {
    public final EventFactory<DisruptorEvent<T>> EVENT_FACTORY = DisruptorEvent::new;
    private T data;
}
