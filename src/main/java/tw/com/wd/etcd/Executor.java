package tw.com.wd.etcd;

import io.etcd.jetcd.Client;
import io.etcd.jetcd.cluster.Member;

import java.net.URI;
import java.util.List;


public class Executor {
    private static final String ETCD_NODE_0 = "http://127.0.0.1:2370";
    private static final String ETCD_NODE_1 = "http://127.0.0.1:2380";
    private static final String ETCD_NODE_2 = "http://127.0.0.1:2390";

    public static void main(String[] args) throws Exception {
        // create client using endpoints
        Client client = Client
            .builder()
            .endpoints(ETCD_NODE_0, ETCD_NODE_1, ETCD_NODE_2)
            .build();

        KVService kvService = new KVService(client.getKVClient());

        System.out.println("Start");
        for (int cnt = 0; cnt < 10000; cnt++) {
            kvService.put("k01".getBytes(), ("v" + cnt).getBytes());
        }

        Thread.sleep(1000L);
        kvService.put("k02".getBytes(), "v04".getBytes());
        Thread.sleep(1000L);
        kvService.put("k03".getBytes(), "v05".getBytes());


        // client.getKVClient().delete(ByteSequence.from("k01".getBytes())).get();

        List<Member> memberList = client.getClusterClient().listMember().get().getMembers();
        System.out.printf("ClusterMember Size: %d\n", memberList.size());

        for (Member member : memberList) {
            System.out.printf("MemberID: %d\n", member
                .getId());
            System.out.printf("MemberName: %s\n", member
                .getName());

            System.out.printf("--- PeerURIs ---\n");
            for (URI uri : member
                .getPeerURIs()) {
                System.out.printf("URI: %s\n", uri.toString());
            }

            System.out.printf("\n");

            System.out.printf("--- ClientURIs ---\n");
            for (URI uri : member
                .getClientURIs()) {
                System.out.printf("URI: %s\n", uri.toString());
            }
        }
        client.close();
    }
}
