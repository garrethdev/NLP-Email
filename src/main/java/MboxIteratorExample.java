/**
 * Created by dottig2-adm on 7/12/2016.
 */


import com.google.common.base.Charsets;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

/**
 * Simple example of how to use MboxIterator. We split one Mbox file into
 * individual email messages.
 *
 * @author Ioan Eugen Stan <stan.ieugen@gmail.com>
 */
public class MboxIteratorExample {

    private final static Charset UTF8_CHARSET = Charset.forName("UTF-8");
    private final static CharsetEncoder ENCODER = UTF8_CHARSET.newEncoder();

    // simple example of how to split an mbox into individual files
    public static void main(String[] args) throws IOException, FileNotFoundException {
        final File mbox = new File("/home/estan/gmail2.mbox");
        long start = System.currentTimeMillis();
        int count = 0;
        for (CharBuffer buf : MboxIterator.fromFile(mbox).charset(Charsets.UTF_8).build()) {
            FileOutputStream fout = new FileOutputStream(new File("target/messages/msg-" + count));
            FileChannel fileChannel = fout.getChannel();
            ByteBuffer buf2 = ENCODER.encode(buf);
            fileChannel.write(buf2);
            fileChannel.close();
            fout.close();
            count++;
        }
        System.out.println("Found " + count + " messages");
        long end = System.currentTimeMillis();
        System.out.println("Done in: " + (end - start) + " milis");
    }
}
