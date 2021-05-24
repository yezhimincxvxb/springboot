package com.yzm.ehcache.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzm.ehcache.entity.User;
import com.yzm.ehcache.mapper.UserMapper;
import com.yzm.ehcache.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author Yzm
 * @since 2021-02-24
 */
@Service
@CacheConfig(cacheNames = "user_cache")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private CacheManager cacheManager;

    /**
     * condition：true，则存入缓存
     */
    @Override
    @CachePut(key = "'user_'+#result.id", condition = "#result != null")
    public User saveUser(User user) {
        if (user == null) user = new User();
        user.setCreateDate(LocalDateTime.now());
        user.setUpdateDate(LocalDateTime.now());
        user.setCreateDateUnix(String.valueOf(System.currentTimeMillis()));
        if (baseMapper.insert(user) > 0) {
            return user;
        }
        return null;
    }

    /**
     * unless：false，则存入缓存
     */
    @Override
    @CachePut(key = "'user_'+#user.id", unless = "#result le 0")
    public int updateUser(User user) {
        return baseMapper.updateById(user);
    }

    @Override
    @CacheEvict(key = "'user_'+#id", condition = "#result gt 0")
    public int deleteUser(Integer id) {
        return baseMapper.deleteById(id);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void deleteAll() {
    }

    @Override
    @Cacheable(key = "'user_'+#id")
    public User findById(Integer id) {
        return baseMapper.selectById(id);
    }

    @Override
    @Cacheable(key = "'user_'+#p0")
    public User selectById(Integer id) {
        return baseMapper.selectById(id);
    }

    @Override
    @Cacheable(key = "'user_'+#user.id")
    public User getById(User user) {
        return baseMapper.selectById(user.getId());
    }

    @Override
    @Cacheable(key = "#root.methodName")
    public List<User> listAll() {
        return baseMapper.selectList(null);
    }

    @Override
    public void selectCache(Object o) {
        Cache userCache = cacheManager.getCache("user_cache");
        if (userCache != null) {
            System.out.println(userCache.getName());
            User user = userCache.get("user_" + o, User.class);
            System.out.println("user = " + user);
        }
    }
}
