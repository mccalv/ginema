/**
 * 
 */
package com.ganemi.core;

/**
 * @author mccalv
 *
 */
public class SensitiveDataTest {

	public static class AnObject {
		@SensitiveData
		private String name;

		@SensitiveData
		private String surnname;
		
		

	}
}
