package com.parveendala.newsapp.di.news;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/*****************
 * Parveen Dala
 * News App, July 2019
 */
@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface NewsScope {
}
