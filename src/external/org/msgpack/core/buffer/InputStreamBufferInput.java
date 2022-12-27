//
// MessagePack for Java
//
//    Licensed under the Apache License, Version 2.0 (the "License");
//    you may not use this file except in compliance with the License.
//    You may obtain a copy of the License at
//
//        http://www.apache.org/licenses/LICENSE-2.0
//
//    Unless required by applicable law or agreed to in writing, software
//    distributed under the License is distributed on an "AS IS" BASIS,
//    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//    See the License for the specific language governing permissions and
//    limitations under the License.
//
package external.org.msgpack.core.buffer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;

import static external.org.msgpack.core.Preconditions.checkNotNull;

/**
 * {@link MessageBufferInput} adapter for {@link InputStream}
 */
public class InputStreamBufferInput
        implements MessageBufferInput
{
    private InputStream in;
    private final int bufferSize;
    private boolean reachedEOF = false;

    public static MessageBufferInput newBufferInput(InputStream in)
    {
        checkNotNull(in, "InputStream is null");
        if (in instanceof FileInputStream) {
            FileChannel channel = ((FileInputStream) in).getChannel();
            if (channel != null) {
                return new ChannelBufferInput(channel);
            }
        }
        return new InputStreamBufferInput(in);
    }

    public InputStreamBufferInput(InputStream in)
    {
        this(in, 8192);
    }

    public InputStreamBufferInput(InputStream in, int bufferSize)
    {
        this.in = checkNotNull(in, "input is null");
        this.bufferSize = bufferSize;
    }

    /**
     * Reset Stream. This method doesn't close the old resource.
     *
     * @param in new stream
     * @return the old resource
     */
    public InputStream reset(InputStream in)
            throws IOException
    {
        InputStream old = this.in;
        this.in = in;
        reachedEOF = false;
        return old;
    }

    @Override
    public MessageBuffer next()
            throws IOException
    {
        if (reachedEOF) {
            return null;
        }

        byte[] buffer = new byte[bufferSize];
        int readLen = in.read(buffer);
        if (readLen == -1) {
            reachedEOF = true;
            return null;
        }
        return MessageBuffer.wrap(buffer).slice(0, readLen);
    }

    @Override
    public void close()
            throws IOException
    {
        in.close();
    }
}
