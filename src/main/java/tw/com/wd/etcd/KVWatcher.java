package tw.com.wd.etcd;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.Watch;
import io.etcd.jetcd.Watch.Listener;
import io.etcd.jetcd.Watch.Watcher;
import io.etcd.jetcd.options.WatchOption;
import io.etcd.jetcd.watch.WatchEvent;
import io.etcd.jetcd.watch.WatchResponse;

import java.util.List;
import java.util.UUID;


public class KVWatcher {
    private String id;
    private Watch watchClient;


    public KVWatcher(Watch watchClient) {
        super();
        this.watchClient = watchClient;
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public Watcher startWatcher(byte[] watchKey) {
        KVListener kvListener = new KVListener();
        WatchOption watchOption = WatchOption.newBuilder().build();
        Watcher watcher = this.watchClient.watch(ByteSequence.from(watchKey), watchOption, kvListener);

        return watcher;
    }

    public static final class KVListener implements Listener {
        @Override
        public void onNext(WatchResponse response) {
            System.out.printf("-- onNext --\n");

            List<WatchEvent> watchEventList = response.getEvents();
            System.out.printf("Size of WatchEvent: %d\n", watchEventList.size());


            for (WatchEvent watchEvent : watchEventList) {
                System.out.printf("EventType: %s\n", watchEvent.getEventType().toString());
                System.out.printf("Key: %s, Value: %s\n", watchEvent.getKeyValue().getKey().toString(), watchEvent.getKeyValue().getValue().toString());
                System.out.printf("PrevKey: %s, PrevValue: %s\n", watchEvent.getPrevKV().getKey().toString(), watchEvent.getPrevKV().getValue().toString());
            }
        }

        @Override
        public void onError(Throwable throwable) {
            System.out.printf("-- onError --\n");
            throwable.printStackTrace();
        }

        @Override
        public void onCompleted() {
            System.out.printf("-- onCompleted --\n");
        }
    }


    public static void main(String[] args) throws Exception {
        Client client = Client.builder().endpoints("http://127.0.0.1:2369", "http://127.0.0.1:2379", "http://127.0.0.1:2389").build();

        KVWatcher kvWatcher = new KVWatcher(client.getWatchClient());
        kvWatcher.startWatcher("k03".getBytes());
    }
}
