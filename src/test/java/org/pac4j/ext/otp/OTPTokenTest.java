/*
 * Copyright (c) 2018-present, easy-4-java (https://github.com/easy-4-java).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.pac4j.ext.otp;

import org.junit.Test;

import java.io.Serializable;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link OTPToken}.
 *
 * <p>Because {@link OTPToken} is an abstract marker, these tests exercise the
 * type through a tiny concrete subclass and assert the base type's
 * extensibility &mdash; protected constructor, {@link Serializable} contract,
 * and null tolerance.</p>
 *
 * @since 3.0.0
 */
public class OTPTokenTest {

    /**
     * Concrete subclass used only by these unit tests; captures whatever raw
     * response was passed in so we can verify it after construction.
     */
    private static class TestToken extends OTPToken {
        @SuppressWarnings("unused")
        private final String rawResponse;

        TestToken(String rawResponse) {
            super(rawResponse);
            this.rawResponse = rawResponse;
        }
    }

    /**
     * The protected constructor of the base marker type must be reachable
     * from a subclass regardless of whether {@code rawResponse} is null.
     */
    @Test
    public void shouldConstructWithNullOrNonNullRawResponse() {
        TestToken fromNull = new TestToken(null);
        TestToken fromValue = new TestToken("123456");
        assertNotNull(fromNull);
        assertNotNull(fromValue);
    }

    /**
     * Subclasses must share the {@link Serializable} contract declared by the
     * base type so they can be sent across the wire by pac4j's profile
     * machinery.
     */
    @Test
    public void shouldBeSerializableThroughBaseType() {
        OTPToken token = new TestToken("123456");
        assertTrue("OTPToken must implement Serializable", token instanceof Serializable);
    }

    /**
     * Markers and credentials share a {@code serialVersionUID}; verify the
     * declared constant exists.
     */
    @Test
    public void shouldExposeStableSerialVersionUid() throws Exception {
        java.lang.reflect.Field f = OTPToken.class.getDeclaredField("serialVersionUID");
        assertNotNull(f);
        assertTrue(ModifierIsStatic(f.getModifiers()));
        assertTrue(ModifierIsFinal(f.getModifiers()));
    }

    private static boolean ModifierIsStatic(int m) {
        return (m & java.lang.reflect.Modifier.STATIC) != 0;
    }

    private static boolean ModifierIsFinal(int m) {
        return (m & java.lang.reflect.Modifier.FINAL) != 0;
    }
}
