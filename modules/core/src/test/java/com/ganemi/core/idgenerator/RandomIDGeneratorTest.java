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
package com.ganemi.core.idgenerator;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.ganemi.core.idgenerator.impl.RandomIDGenerator;
import com.ganemi.core.idgenerator.impl.UUIDGenerator;

public class RandomIDGeneratorTest {

	@Test
	public void shoulGenetate1MDifferentIdWithRandomGenerator() {
		checkCollisions(new RandomIDGenerator());
	}

	@Test
	public void shoulGenetate1MDifferentIdWithUUIDGenerator() {
		checkCollisions(new UUIDGenerator());
	}

	public void checkCollisions(IDGenerator generator) {
		Set<String> s = new HashSet<String>();
		int i = 0;
		while (i < 1000 * 1000) {
			if (s.contains(generator.generate().getId()))
				throw new IllegalArgumentException("Duplication found for a key");
			i++;
		}
		s.clear();

	}

}