package com.sms.client.resp;

import com.sms.config.JWTConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chentao
 * @version 创建时间: 2023-11-15 17:30
 */
@Data
public class LoginResp {

    @ApiModelProperty(value="令牌前缀")
    public String tokenPrefix= JWTConfig.tokenPrefix;

    @ApiModelProperty(value="请求头名称")
    public String tokenHeader=JWTConfig.tokenHeader;

    @ApiModelProperty(value="令牌正文")
    public String accessToken;
}
