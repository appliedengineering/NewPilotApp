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

import java.io.IOException;
import java.io.OutputStream;

import static external.org.msgpack.core.Preconditions.checkNotNull;

/**
 * MessageBufferOutput adapter for {@link java.io.OutputStream}.
 */
public class OutputStreamBufferOutput
        implements MessageBufferOutput
{
    private OutputStream out;
    private MessageBuffer buffer;
    private byte[] tmpBuf;

    public OutputStreamBufferOutput(OutputStream out)
    {
        this.out = checkNotNull(out, "output is null");
    }

    /**
     * Reset Stream. This method doesn't close the old resource.
     *
     * @param out new stream
     * @return the old resource
     */
    public OutputStream reset(OutputStream out)
            throws IOException
    {
        OutputStream old = this.out;
        this.out = out;
        return old;
    }

    @Override
    public MessageBuffer next(int bufferSize)
            throws IOException
    {
        if (buffer == null || buffer.size != bufferSize) {
            buffer = MessageBuffer.newBuffer(bufferSize);
        }
        return buffer;
    }

    @Override
    public void flush(MessageBuffer buf)
            throws IOException
    {
        int writeLen = buf.size();
        if (buf.hasArray()) {
            out.write(buf.getArray(), buf.offset(), writeLen);
        }
        else {
            if (tmpBuf == null || tmpBuf.length < writeLen) {
                tmpBuf = new byte[writeLen];
            }
            buf.getBytes(0, tmpBuf, 0, writeLen);
            out.write(tmpBuf, 0, writeLen);
        }
    }

    @Override
    public void close()
            throws IOException
    {
        try {
            out.flush();
        }
        finally {
            out.close();
        }
    }
}
