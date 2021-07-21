package com.yzm.validation.annotation;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

/**
 * 自定义验证顺序
 * 考虑一种场景：一个bean有1个属性（假如说是attrA），这个属性上添加了3个约束（假如说是@NotNull、@NotEmpty、@NotBlank）。
 * 默认情况下，validation-api对这3个约束的校验顺序是随机的。也就是说，可能先校验@NotNull，再校验@NotEmpty，最后校验@NotBlank，也有可能先校验@NotBlank，再校验@NotEmpty，最后校验@NotNull。
 * <p>
 * 那么，如果我们的需求是先校验@NotNull，再校验@NotBlank，最后校验@NotEmpty。validation-api能否做到这一点呢？答案是：能。这就要用到validation-api提供的 @GroupSequence 注解了。
 */
@GroupSequence({First.class, Second.class, Third.class, Default.class})
public interface GroupOrder {
}
