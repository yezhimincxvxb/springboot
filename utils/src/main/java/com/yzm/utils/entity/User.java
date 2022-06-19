package com.yzm.utils.entity;

import com.yzm.utils.entity.base.BaseEntity;
import lombok.Data;

@Data
public class User extends BaseEntity {
    private static final long serialVersionUID = -5584928398375214989L;

    private Integer id;
    private String username;
    private String password;
}