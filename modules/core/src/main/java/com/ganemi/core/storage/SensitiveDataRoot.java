/**
 * 
 */
package com.ganemi.core.storage;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author mccalv
 *
 */
/**
 * Identifies the root object mantaining Sensitive data.
 * 
 * @author mccalv
 *
 */
@Target({ElementType.TYPE_USE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SensitiveDataRoot {

  String name();


}
