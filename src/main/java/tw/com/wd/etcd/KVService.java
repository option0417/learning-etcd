package tw.com.wd.etcd;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.KeyValue;
import io.etcd.jetcd.kv.GetResponse;
import io.etcd.jetcd.kv.PutResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;


public class KVService {
    private KV kvClient;

    public KVService(KV kvClient) {
        super();
        this.kvClient = kvClient;
    }

    public void put(byte[] key, byte[] value) throws Exception {
        CompletableFuture<PutResponse> respFuture = kvClient.put(ByteSequence.from(key), ByteSequence.from(value));
        //respFuture.get();
    }

    public byte[] get(byte[] key) throws Exception {

        CompletableFuture<GetResponse> respFuture = kvClient.get(ByteSequence.from(key));
        GetResponse getResp = respFuture.get();

        List<KeyValue> kvlist = getResp.getKvs();
        System.out.printf("Size of KVList: %d\n", kvlist.size());

        return kvlist.get(0).getValue().getBytes();
    }
}
