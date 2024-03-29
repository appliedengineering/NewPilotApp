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
package external.org.msgpack.value;

import java.math.BigInteger;

/**
 * The base interface {@code NumberValue} of {@code IntegerValue} and {@code FloatValue}. To extract primitive type values, call toXXX methods, which may lose some information by rounding or truncation.
 *
 * @see external.org.msgpack.value.IntegerValue
 * @see external.org.msgpack.value.FloatValue
 */
public interface NumberValue
        extends Value
{
    /**
     * Represent this value as a byte value, which may involve rounding or truncation of the original value.
     * the value.
     */
    byte toByte();

    /**
     * Represent this value as a short value, which may involve rounding or truncation of the original value.
     */
    short toShort();

    /**
     * Represent this value as an int value, which may involve rounding or truncation of the original value.
     * value.
     */
    int toInt();

    /**
     * Represent this value as a long value, which may involve rounding or truncation of the original value.
     */
    long toLong();

    /**
     * Represent this value as a BigInteger, which may involve rounding or truncation of the original value.
     */
    BigInteger toBigInteger();

    /**
     * Represent this value as a 32-bit float value, which may involve rounding or truncation of the original value.
     */
    float toFloat();

    /**
     * Represent this value as a 64-bit double value, which may involve rounding or truncation of the original value.
     */
    double toDouble();
}
