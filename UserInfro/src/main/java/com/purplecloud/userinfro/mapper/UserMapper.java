package com.purplecloud.userinfro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.purplecloud.userinfro.bean.UserInform;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<UserInform> {
}
