package tuyen.bui.disruptor;

import com.lmax.disruptor.EventHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@SpringBootTest
class DisruptorApplicationTests {


    @Autowired
    DisruptorCore disruptorCore;

    @Test
    void contextLoads() throws InterruptedException, ExecutionException {
//        EventHandler<DisruptorEvent<TestEvent>> eventHandler =
//                (testEventDisruptorEvent, l, b) -> System.out.println(testEventDisruptorEvent);
        EventHandler<DisruptorEvent<TestEvent>> eventHandler =
                (testEventDisruptorEvent, l, b) -> {
                    System.out.println(testEventDisruptorEvent.getData());
                    if (testEventDisruptorEvent.getData().getName().equals("Tuyen6")) {
                        Thread.sleep(3000);
                    }
					for (int i = 0; i < 5; i++) {
						System.out.println(testEventDisruptorEvent.getData().getName()  +  " " + i);
						Thread.sleep(100);
					}
                    testEventDisruptorEvent.getData().setName(testEventDisruptorEvent.getData().getName() + "aaa");
                };
        EventHandler<DisruptorEvent<TestEvent>> eventHandler2 =
                (testEventDisruptorEvent, l, b) -> {
                    System.out.println(testEventDisruptorEvent.getData());
					for (int i = 5; i < 10; i++) {
						System.out.println(testEventDisruptorEvent.getData().getName()  +  " " + i);
						Thread.sleep(100);
					}
                };
        System.out.println(disruptorCore.getDisruptor());
        var handlerGroup = disruptorCore.setHandler(eventHandler);
        handlerGroup.then(eventHandler2);
//        disruptorCore.getDisruptor().after(eventHandler2);
        disruptorCore.start();
        CompletableFuture<Void> future = CompletableFuture.runAsync(this::pushEvent);
        future.get();
        Thread.sleep(10000);
    }

    public void pushEvent(){
        disruptorCore.publish(new TestEvent("Tuyen1"));
        disruptorCore.publish(new TestEvent("Tuyen2"));
        disruptorCore.publish(new TestEvent("Tuyen3"));
        disruptorCore.publish(new TestEvent("Tuyen4"));
        disruptorCore.publish(new TestEvent("Tuyen5"));
        disruptorCore.publish(new TestEvent("Tuyen6"));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        disruptorCore.publish(new TestEvent("Tuyen7"));
        disruptorCore.publish(new TestEvent("Tuyen8"));
        disruptorCore.publish(new TestEvent("Tuyen9"));
    }

}
