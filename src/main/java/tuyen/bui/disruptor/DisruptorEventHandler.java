package tuyen.bui.disruptor;

import com.lmax.disruptor.EventHandler;

public class DisruptorEventHandler<T> implements EventHandler<T> {
    @Override
    public void onEvent(T t, long l, boolean b) throws Exception {

    }
}
