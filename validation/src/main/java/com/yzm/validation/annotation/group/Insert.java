package com.yzm.validation.annotation.group;

import javax.validation.groups.Default;

/**
 * 继承Default并不是必须的。只是说，
 * 如果继承了Default，那么@Validated(value = Insert.class)的校验范畴就为【Insert】和【Default】；
 * 如果没继承Default，那么@Validated(value = Insert.class)的校验范畴只为【Insert】，
 * 而@Validated(value = {Insert.class, Default.class})的校验范畴才为【Insert】和【Default】。
 */
public interface Insert extends Default {
}
