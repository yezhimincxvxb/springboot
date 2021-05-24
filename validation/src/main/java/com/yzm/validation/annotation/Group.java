package com.yzm.validation.annotation;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence({Default.class, Insert.class, Update.class, Query.class})
public interface Group {
}

