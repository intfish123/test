package com.example.test.board.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wangxiaolei
 * @since 2020-04-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Board implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * uid
     */
    private String id;

    /**
     * 看板名称
     */
    private String name;

    /**
     * 请求ID
     */
    private String requestId;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 响应数据
     */
    private String responseData;

    /**
     * 用户ID(以后扩展用)
     */
    private String userId;

    /**
     * 用户组ID(以后扩展用)
     */
    private String groupId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


}
