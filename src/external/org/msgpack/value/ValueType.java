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

/**
 * MessageTypeFamily is a group of {@link external.org.msgpack.core.MessageFormat}s
 */
public enum ValueType
{
    NIL(false, false),
    BOOLEAN(false, false),
    INTEGER(true, false),
    FLOAT(true, false),
    STRING(false, true),
    BINARY(false, true),
    ARRAY(false, false),
    MAP(false, false),
    EXTENSION(false, true);

    private final boolean numberType;
    private final boolean rawType;

    private ValueType(boolean numberType, boolean rawType)
    {
        this.numberType = numberType;
        this.rawType = rawType;
    }

    public boolean isNilType()
    {
        return this == NIL;
    }

    public boolean isBooleanType()
    {
        return this == BOOLEAN;
    }

    public boolean isNumberType()
    {
        return numberType;
    }

    public boolean isIntegerType()
    {
        return this == INTEGER;
    }

    public boolean isFloatType()
    {
        return this == FLOAT;
    }

    public boolean isRawType()
    {
        return rawType;
    }

    public boolean isStringType()
    {
        return this == STRING;
    }

    public boolean isBinaryType()
    {
        return this == BINARY;
    }

    public boolean isArrayType()
    {
        return this == ARRAY;
    }

    public boolean isMapType()
    {
        return this == MAP;
    }

    public boolean isExtensionType()
    {
        return this == EXTENSION;
    }
}
