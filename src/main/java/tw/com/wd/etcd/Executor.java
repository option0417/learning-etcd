package tw.com.wd.etcd;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.kv.GetResponse;

import java.util.concurrent.CompletableFuture;


public class Executor {
    private static final String ETCD_NODE_0 = "http://127.0.0.1:2379";

    public static void main(String[] args) throws Exception {
        // create client using endpoints
        Client client = Client.builder().endpoints(ETCD_NODE_0).build();

        KV kvClient = client.getKVClient();
        ByteSequence key = ByteSequence.from("test_key".getBytes());
        ByteSequence value = ByteSequence.from("test_value".getBytes());

        // put the key-value
        kvClient.put(key, value).get();

        // get the CompletableFuture
        CompletableFuture<GetResponse> getFuture = kvClient.get(key);

        // get the value from CompletableFuture
        GetResponse response = getFuture.get();

        System.out.printf("KV count: %d\n", response.getCount());
        System.out.printf("Key: %s, Value: %s\n",
                          response.getKvs().get(0).getKey().toString(),
                          response.getKvs().get(0).getValue().toString());
        System.out.printf("ClusterId: %d\n", response.getHeader().getClusterId());
        System.out.printf("MemberId: %d\n", response.getHeader().getMemberId());
        System.out.printf("RaftTerm: %d\n", response.getHeader().getRaftTerm());
        System.out.printf("Revision: %d\n", response.getHeader().getRevision());

        client.close();
    }
}
