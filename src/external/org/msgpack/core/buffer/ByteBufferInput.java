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
import java.nio.ByteBuffer;

import static external.org.msgpack.core.Preconditions.checkNotNull;

/**
 * {@link MessageBufferInput} adapter for {@link java.nio.ByteBuffer}
 */
public class ByteBufferInput
        implements MessageBufferInput
{
    private ByteBuffer input;
    private boolean isRead = false;

    public ByteBufferInput(ByteBuffer input)
    {
        this.input = checkNotNull(input, "input ByteBuffer is null");
    }

    /**
     * Reset buffer. This method doesn't close the old resource.
     *
     * @param input new buffer
     * @return the old resource
     */
    public ByteBuffer reset(ByteBuffer input)
    {
        ByteBuffer old = this.input;
        this.input = input;
        isRead = false;
        return old;
    }

    @Override
    public MessageBuffer next()
            throws IOException
    {
        if (isRead) {
            return null;
        }

        isRead = true;
        return MessageBuffer.wrap(input);
    }

    @Override
    public void close()
            throws IOException
    {
        // Nothing to do
    }
}
