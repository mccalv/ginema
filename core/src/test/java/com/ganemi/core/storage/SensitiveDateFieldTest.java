/*******************************************************************************
 * Copyright Mirko Calvaresi mccalv@gmail.com 2015 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 *******************************************************************************/
package com.ganemi.core.storage;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class SensitiveDateFieldTest {

	@Test(expected = IllegalArgumentException.class)
	public void testNotSupportedType() {
		new SensitiveDataField<>(Set.class, "a", new HashSet<String>());

	}

	@Test()
	public void testSupportedType() {
		new SensitiveDataField<>(String.class, "a", "a");
		new SensitiveDataField<>(Date.class, "a", new Date());
		new SensitiveDataField<>(Long.class, "a", 1L);
		new SensitiveDataField<>(Integer.class, "a", 1);
		// new SensitiveDataField<>(Short.class, "a", 01x);

	}
}
