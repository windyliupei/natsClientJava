import io.nats.client.*;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.security.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import io.nats.client.ClosedCallback;
import io.nats.client.Connection;
import io.nats.client.ConnectionEvent;
import io.nats.client.ConnectionFactory;
import io.nats.client.ReconnectedCallback;

public class ApplicationStart {

    static ConnectionFactory natsFactory=null;
    static Connection connect;

    public static void main(String[] args)  {
        System.out.printf("Start !");

        try
        {
            //sendNatsWithoutSSL();

            NatsSSLInfo nsi = new NatsSSLInfo();
            nsi.setKeyStoreLocation("C:\\Workspace\\StudyDemo\\natsClient\\src\\main\\resources\\client.jks");
            nsi.setKeyStorePassPhrase("111111");
            nsi.setTrustStoreLocation("C:\\Workspace\\StudyDemo\\natsClient\\src\\main\\resources\\tserver.jks");
            nsi.setTrustStorePassPhrase("111111");
            nsi.setNatsUsername("home");
            nsi.setNatsPassword("1qaz2wsx");

            //sendNatsWithSSLOffical(nsi);
            sendNatsWithSSL("nats://115.159.114.116:4222",nsi);
        }
        catch(Exception ex){
            System.out.print(ex);
        }

        System.out.printf("End !");
    }

    private static void sendNatsWithoutSSL() throws IOException {
        // Connect to default URL ("nats://localhost:4222")
        Connection nc = Nats.connect("nats://localhost:4222");
        nc.subscribe("test");

        Message msg = new Message();
        msg.setSubject("test");
        String message = "Hello";
        msg.setData(message.getBytes());
        nc.publish(msg);
    }

    private static void sendNatsWithSSL(String natsServers,NatsSSLInfo nsi) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {

        String[]  servers = natsServers.split(",");
        for(int i=0;i<servers.length;i++){
            servers[i]=servers[i];
        }

        natsFactory = new ConnectionFactory();
        natsFactory.setServers(servers);

        try {
            final KeyManagerFactory kmf = KeyManagerFactory.getInstance(nsi.getKeyManagerType());
            final char[] keyPassPhrase = nsi.getKeyStorePassPhrase().toCharArray();
            final KeyStore ks = KeyStore.getInstance(nsi.getKeyStoreType());
            ks.load(new FileInputStream(nsi.getKeyStoreLocation()), keyPassPhrase);
            kmf.init(ks, keyPassPhrase);

            // Set up and load the trust store
            final TrustManagerFactory tmf = TrustManagerFactory.getInstance(nsi.getKeyManagerType());
            final char[] trustPassPhrase = nsi.getTrustStorePassPhrase().toCharArray();
            final KeyStore tks = KeyStore.getInstance(nsi.getKeyStoreType());
            tks.load(new FileInputStream(nsi.getTrustStoreLocation()), trustPassPhrase);
            tmf.init(tks);

            // Get and initialize the SSLContext
            SSLContext c = SSLContext.getInstance(nsi.getProtocol());
            c.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            natsFactory.setSSLContext(c);
        }catch(Exception e) {
            e.printStackTrace();
        }

        natsFactory.setUsername(nsi.getNatsUsername());
        natsFactory.setPassword(nsi.getNatsPassword());

        try{
            connect = natsFactory.createConnection();

//            MessageHandler cb = new NatsMessageHandler();
//            connect.subscribe("test",cb);
//
//            Message msg = new Message();
//            msg.setSubject("test");
//            String message = "Hello";
//            msg.setData(message.getBytes());
//            connect.publish(msg);

            // Simple Async Subscriber
            connect.subscribe("foo", m -> {
                System.out.printf("Received a message: %s\n", new String(m.getData()));
            });

            // Simple Publisher
            connect.publish("foo", "Hello World".getBytes());
            Thread.sleep(3000);
            connect.close();
        }
        catch (Exception ex){
            if (connect != null){
                connect.close();
            }
            System.out.print(ex);
        }




    }

    private static void sendNatsWithSSLOffical(NatsSSLInfo nsi) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException, CertificateException, UnrecoverableKeyException {
        // Set up and load the keystore
        final KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        final char[] keyPassPhrase = nsi.getKeyStorePassPhrase().toCharArray();
        final KeyStore ks = KeyStore.getInstance(nsi.getKeyStoreType());
        ks.load(new FileInputStream(nsi.getKeyStoreLocation()), keyPassPhrase);
        kmf.init(ks, keyPassPhrase);

        // Set up and load the trust store
        final char[] trustPassPhrase = nsi.getTrustStorePassPhrase().toCharArray();
        final KeyStore tks = KeyStore.getInstance("JKS");
        tks.load(new FileInputStream(nsi.getTrustStoreLocation()), trustPassPhrase);
        final TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(tks);

        // Get and initialize the SSLContext
        SSLContext c = SSLContext.getInstance(nsi.getProtocol());
        c.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

        // Create NATS options
        Options opts = new Options.Builder()
                .secure()       // Set the secure option, indicating that TLS is required
                .tlsDebug()     // Set TLS debug, which will produce additional console output
                .sslContext(c)  // Set the context for this factory
                .build();

        // Create a new SSL connection
        try (Connection connection = Nats.connect("nats://115.159.114.116:4222", opts)) {
            connection.publish("foo", "Hello".getBytes());
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
